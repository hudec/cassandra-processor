package org.sqlproc.engine.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlFilesLoader;
import org.sqlproc.engine.SqlProcessorLoader;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.cassandra.type.CassandraTypeFactory;
import org.sqlproc.engine.plugin.SimpleSqlPluginFactory;

public class TestDatabase {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected SqlProcessorLoader loader;

    public TestDatabase() {
        StringBuilder metaStatements = SqlFilesLoader.getStatements(TestDatabase.class, "simple.meta");
        loader = new SqlProcessorLoader(metaStatements, CassandraTypeFactory.getInstance(),
                SimpleSqlPluginFactory.getInstance());
    }

    protected SqlQueryEngine getQueryEngine(String name) {
        return (SqlQueryEngine) loader.getEngine(name, SqlProcessorLoader.EngineType.Query);
    }

    protected SqlCrudEngine getCrudEngine(String name) {
        return (SqlCrudEngine) loader.getEngine(name, SqlProcessorLoader.EngineType.Crud);
    }

}
