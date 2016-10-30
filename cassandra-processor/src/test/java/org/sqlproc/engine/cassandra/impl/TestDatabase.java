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
    protected Cluster cluster;
    protected CassandraEngineFactory factory;
    protected Session session;

    @BeforeClass
    public static void startCassabdra()
            throws ConfigurationException, TTransportException, IOException, InterruptedException {
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        EmbeddedCassandraServer.startEmbeddedCassandra(30000);

        SocketOptions socketOptions = new SocketOptions();
        socketOptions.setReadTimeoutMillis(30000);
        Cluster cluster = new Cluster.Builder().addContactPoints(EmbeddedCassandraServer.getHost())
                .withPort(EmbeddedCassandraServer.getNativeTransportPort()).withSocketOptions(socketOptions).build();
        System.out.println(cluster);
        Session session = cluster.connect();
        System.out.println(session);
        List<String> ddlCreateDb = DDLLoader.getDDLs(TestDatabase.class, "simple.cql");
        for (String ddl : ddlCreateDb) {
            System.out.println(ddl);
            session.execute(ddl);
        }
        registerTypes(cluster);
    }

    @Before
    public void setup() {
        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
        SocketOptions socketOptions = new SocketOptions();
        socketOptions.setReadTimeoutMillis(30000);
        cluster = new Cluster.Builder().addContactPoints(EmbeddedCassandraServer.getHost())
                .withPort(EmbeddedCassandraServer.getNativeTransportPort()).withSocketOptions(socketOptions).build();
        System.out.println(cluster);
        session = cluster.connect("basic");
        System.out.println(session);
    }

    public TestDatabase() {
        System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");

        StringBuilder metaStatements = SqlFilesLoader.getStatements(TestDatabase.class, "simple.meta");
        factory = new CassandraEngineFactory();
        factory.setMetaStatements(metaStatements);
        factory.addCustomType(new PhoneNumberType());
        factory.init();
    }

    protected SqlQueryEngine getQueryEngine(String name) {
        return factory.getQueryEngine(name);
    }

    protected SqlCrudEngine getCrudEngine(String name) {
        return factory.getCrudEngine(name);
    }

    protected static void registerTypes(Cluster cluster) {
        UserType type1Type = cluster.getMetadata().getKeyspace("basic").getUserType("type1");
        Type1Codec type1Codec = new Type1Codec(TypeCodec.userType(type1Type), Type1.class);
        cluster.getConfiguration().getCodecRegistry().register(InstantCodec.instance, LocalTimeCodec.instance,
                LocalDateCodec.instance, type1Codec);
    }

    protected CassandraSimpleSession getSession() {
        return new CassandraSimpleSession(session);
    }
}
