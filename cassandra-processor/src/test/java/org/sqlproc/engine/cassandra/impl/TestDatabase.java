package org.sqlproc.engine.cassandra.impl;

import java.io.IOException;
import java.util.List;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlFilesLoader;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.cassandra.CassandraEngineFactory;
import org.sqlproc.engine.cassandra.CassandraSimpleSession;
import org.sqlproc.engine.cassandra.model.Type1;
import org.sqlproc.engine.cassandra.model.Type1Codec;
import org.sqlproc.engine.util.DDLLoader;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SocketOptions;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.UserType;
import com.datastax.driver.extras.codecs.jdk8.InstantCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalDateCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalTimeCodec;

public class TestDatabase {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected static CassandraEngineFactory factory;

    protected Cluster cluster;
    protected Session session;

    @BeforeClass
    public static void startCassabdra()
            throws ConfigurationException, TTransportException, IOException, InterruptedException {
        EmbeddedCassandraServer.startEmbeddedCassandra(30000);

        Cluster cluster = getCluster();
        executeCQL(cluster.connect(), "simple.ddl");
        registerTypes(cluster);

        factory = getEngineFactory("simple.meta");
    }

    @Before
    public void setupData() {
        cluster = getCluster();
        session = cluster.connect("simple");
        executeCQL(session, "simple.cql");
    }

    public TestDatabase() {
    }

    protected SqlQueryEngine getQueryEngine(String name) {
        return factory.getQueryEngine(name);
    }

    protected SqlCrudEngine getCrudEngine(String name) {
        return factory.getCrudEngine(name);
    }

    protected static CassandraEngineFactory getEngineFactory(String metafile) {
        StringBuilder metaStatements = SqlFilesLoader.getStatements(TestDatabase.class, "simple.meta");
        CassandraEngineFactory factory = new CassandraEngineFactory();
        factory.setMetaStatements(metaStatements);
        factory.addCustomType(new PhoneNumberType());
        factory.init();
        return factory;
    }

    protected static void executeCQL(Session session, String cqlfile) {
        List<String> ddlCreateDb = DDLLoader.getDDLs(TestDatabase.class, cqlfile);
        for (String ddl : ddlCreateDb) {
            // System.out.println(ddl);
            session.execute(ddl);
        }
    }

    protected static void registerTypes(Cluster cluster) {
        UserType type1Type = cluster.getMetadata().getKeyspace("simple").getUserType("type1");
        Type1Codec type1Codec = new Type1Codec(TypeCodec.userType(type1Type), Type1.class);
        cluster.getConfiguration().getCodecRegistry().register(InstantCodec.instance, LocalTimeCodec.instance,
                LocalDateCodec.instance, type1Codec);
    }

    protected static Cluster getCluster() {
        SocketOptions socketOptions = new SocketOptions();
        socketOptions.setReadTimeoutMillis(30000);
        return new Cluster.Builder().addContactPoints(EmbeddedCassandraServer.getHost())
                .withPort(EmbeddedCassandraServer.getNativeTransportPort()).withSocketOptions(socketOptions).build();
    }

    protected CassandraSimpleSession getSession() {
        return new CassandraSimpleSession(session);
    }
}
