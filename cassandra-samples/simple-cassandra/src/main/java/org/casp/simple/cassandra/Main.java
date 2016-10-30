package org.casp.simple.cassandra;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.casp.simple.cassandra.dao.DividendDao;
import org.casp.simple.cassandra.dao.ExchangeMetadataDao;
import org.casp.simple.cassandra.dao.HistoricDataDao;
import org.casp.simple.cassandra.model.Dividend;
import org.casp.simple.cassandra.model.ExchangeMetadata;
import org.casp.simple.cassandra.model.HistoricData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlproc.engine.cassandra.CassandraControl.UpdateFuture;
import org.sqlproc.engine.cassandra.CassandraEngineFactory;
import org.sqlproc.engine.cassandra.CassandraSessionFactory;
import org.sqlproc.engine.cassandra.CassandraStandardControl;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.SocketOptions;
import com.datastax.driver.extras.codecs.jdk8.InstantCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalDateCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalTimeCodec;

public class Main {

    private static final String KEYSPACE = "datastax_referencedata_demo";
    private static final String CONTACT_POINTS = "localhost";
    private static final Integer CONTACT_PORT = 9042;
    private static final Integer NO_OF_THREADS = 5;
    private static final Integer STARTUP_TIMEOUT = 12000;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Cluster cluster;
    private CassandraEngineFactory engineFactory;
    private CassandraSessionFactory sessionFactory;

    public Main() {
        SocketOptions socketOptions = new SocketOptions();
        socketOptions.setReadTimeoutMillis(STARTUP_TIMEOUT);
        cluster = new Cluster.Builder().addContactPoints(CONTACT_POINTS).withPort(CONTACT_PORT)
                .withSocketOptions(socketOptions).build();
        cluster.getConfiguration().getCodecRegistry().register(InstantCodec.instance, LocalTimeCodec.instance,
                LocalDateCodec.instance);

        sessionFactory = new CassandraSessionFactory(cluster.connect(KEYSPACE));

        engineFactory = new CassandraEngineFactory();
        engineFactory.setMetaFilesNames("statements.meta");
        engineFactory.init();

        historicDataDao = new HistoricDataDao(engineFactory, sessionFactory);
        dividendDao = new DividendDao(engineFactory, sessionFactory);
        exchangeMetadataDao = new ExchangeMetadataDao(engineFactory, sessionFactory);
    }

    private HistoricDataDao historicDataDao;
    private DividendDao dividendDao;
    private ExchangeMetadataDao exchangeMetadataDao;

    public void setupDb() {
        com.datastax.driver.core.Session session = ((com.datastax.driver.core.Session) sessionFactory.getSqlSession());
        session.execute("truncate historic_data");
        session.execute("truncate dividends");
        session.execute("truncate exchange_metadata");
    }

    public void run() {
        setupDb();

        Instant now = Instant.now();
        HistoricData hd = new HistoricData()._setExchange("AMEX")._setSymbol("BOI")._setDate(now)._setOpen(40.83)
                ._setHigh(41.10)._setLow(40.59)._setClose(40.59)._setVolume(15100)._setAdjClose(40.59);
        historicDataDao.insert(hd);
        Dividend di = new Dividend()._setExchange("AMEX")._setSymbol("BOI")._setDate(now)._setDividend(40.83);
        dividendDao.insert(di);
        ExchangeMetadata em = new ExchangeMetadata()._setExchange("AMEX")._setSymbol("BOI")._setLastUpdatedDate(now);
        exchangeMetadataDao.insert(em);

        List<HistoricData> lhd = historicDataDao.list(null);
        assert lhd.size() == 1;
        List<Dividend> ldi = dividendDao.list(null);
        assert ldi.size() == 1;
        List<ExchangeMetadata> lem = exchangeMetadataDao.list(null);
        assert lem.size() == 1;

        CassandraStandardControl csc = new CassandraStandardControl();
        csc.setUpdateCounts(new ArrayList<>());
        hd = new HistoricData()._setExchange("AMEX")._setSymbol("BOI1")._setDate(now)._setOpen(40.83)._setHigh(41.10)
                ._setLow(40.59)._setClose(40.59)._setVolume(15100)._setAdjClose(40.59);
        historicDataDao.insert(hd, csc);
        hd = new HistoricData()._setExchange("AMEX")._setSymbol("BOI2")._setDate(now)._setOpen(40.83)._setHigh(41.10)
                ._setLow(40.59)._setClose(40.59)._setVolume(15100)._setAdjClose(40.59);
        historicDataDao.insert(hd, csc);
        hd = new HistoricData()._setExchange("AMEX")._setSymbol("BOI3")._setDate(now)._setOpen(40.83)._setHigh(41.10)
                ._setLow(40.59)._setClose(40.59)._setVolume(15100)._setAdjClose(40.59);
        historicDataDao.insert(hd, csc);
        int i = 1;
        for (UpdateFuture fuc : csc.getUpdateCounts()) {
            System.out.println("BOI" + (i++) + " -> " + fuc.updateCount());
        }

    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Main main = new Main();
        main.run();
        long end = System.currentTimeMillis();
        System.out.println("\nDuration : " + (end - start));
    }
}
