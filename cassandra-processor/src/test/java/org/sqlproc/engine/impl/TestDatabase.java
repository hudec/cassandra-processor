package org.sqlproc.engine.impl;

import org.cassandraunit.CassandraCQLUnit;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.junit.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDatabase {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Rule
    public CassandraCQLUnit cassandraCQLUnit = new CassandraCQLUnit(
            new ClassPathCQLDataSet("simple.cql", "keyspaceSimple"));

    public TestDatabase() {
    }

}
