package org.sqlproc.engine.impl;

import org.cassandraunit.CassandraCQLUnit;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.junit.Before;
import org.junit.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlFilesLoader;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.cassandra.CassandraEngineFactory;
import org.sqlproc.engine.cassandra.CassandraSimpleSession;
import org.sqlproc.engine.model.Type1;
import org.sqlproc.engine.model.Type1Codec;

import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.UserType;
import com.datastax.driver.extras.codecs.jdk8.InstantCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalDateCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalTimeCodec;

public class TestDatabase {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected CassandraEngineFactory factory;

    @Rule
    public CassandraCQLUnit basicCQLUnit = new CassandraCQLUnit(new ClassPathCQLDataSet("simple.cql", "basic"));

    @Before
    public void setup() {
        registerTypes(basicCQLUnit);
    }

    public TestDatabase() {
        StringBuilder metaStatements = SqlFilesLoader.getStatements(TestDatabase.class, "simple.meta");
        factory = new CassandraEngineFactory();
        factory.setMetaStatements(metaStatements);
        factory.init();
    }

    protected SqlQueryEngine getQueryEngine(String name) {
        return factory.getQueryEngine(name);
    }

    protected SqlCrudEngine getCrudEngine(String name) {
        return factory.getCrudEngine(name);
    }

    protected void registerTypes(CassandraCQLUnit basicCQLUnit) {
        UserType type1Type = basicCQLUnit.cluster.getMetadata().getKeyspace("basic").getUserType("type1");
        Type1Codec type1Codec = new Type1Codec(TypeCodec.userType(type1Type), Type1.class);
        basicCQLUnit.cluster.getConfiguration().getCodecRegistry().register(InstantCodec.instance,
                LocalTimeCodec.instance, LocalDateCodec.instance, type1Codec);
    }

    protected CassandraSimpleSession getSession(CassandraCQLUnit basicCQLUnit) {
        return new CassandraSimpleSession(basicCQLUnit.session);
    }
}
