package org.sample;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlproc.engine.SqlFilesLoader;
import org.sqlproc.engine.SqlSessionFactory;
import org.sqlproc.engine.cassandra.CassandraEngineFactory;
import org.sqlproc.engine.cassandra.CassandraSessionFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.SocketOptions;
import com.datastax.driver.extras.codecs.jdk8.InstantCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalDateCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalTimeCodec;

public class Main {

    public static final int STARTUP_TIMEOUT = 12000;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Cluster cluster;
    private CassandraEngineFactory factory;
    private SqlSessionFactory sessionFactory;

    public Main() throws SQLException, ConfigurationException, TTransportException, IOException, InterruptedException {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra(STARTUP_TIMEOUT);

        String hostIp = EmbeddedCassandraServerHelper.getHost();
        int port = EmbeddedCassandraServerHelper.getNativeTransportPort();
        SocketOptions socketOptions = new SocketOptions();
        socketOptions.setReadTimeoutMillis(STARTUP_TIMEOUT);
        cluster = new Cluster.Builder().addContactPoints(hostIp).withPort(port).withSocketOptions(socketOptions)
                .build();
        cluster.getConfiguration().getCodecRegistry().register(InstantCodec.instance, LocalTimeCodec.instance,
                LocalDateCodec.instance);

        sessionFactory = new CassandraSessionFactory(cluster.connect());

        StringBuilder metaStatements = SqlFilesLoader.getStatements(Main.class, "cassandra.ddl");
        factory = new CassandraEngineFactory();
        factory.setMetaStatements(metaStatements);
        factory.init();
    }

    public void setupDb(boolean clear) throws SQLException {
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        Main main = new Main();
        long end = System.currentTimeMillis();
        System.out.println("\nDuration : " + (end - start));
    }
}
