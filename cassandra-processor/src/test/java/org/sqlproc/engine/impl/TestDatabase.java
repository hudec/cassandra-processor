package org.sqlproc.engine.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlFilesLoader;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.cassandra.CassandraEngineFactory;
import org.sqlproc.engine.model.Types;

public class TestDatabase {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected CassandraEngineFactory factory;

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

    protected void assertTypes(Types t1, Types t2) {
        assertThat(t1.getId(), is(t2.getId()));
        assertThat(t1.getT_ascii(), is(t2.getT_ascii()));
        assertThat(t1.getT_bigint(), is(t2.getT_bigint()));
        assertThat(t1.getT_blob().array(), equalTo(t2.getT_blob().array()));
        assertThat(t1.getT_boolean(), is(t2.getT_boolean()));
        assertThat(t1.getT_date(), is(t2.getT_date()));
        assertThat(t1.getT_decimal(), is(t2.getT_decimal()));
        assertThat(t1.getT_double(), is(t2.getT_double()));
        assertThat(t1.getT_float(), is(t2.getT_float()));
        assertThat(t1.getT_inet(), is(t2.getT_inet()));
        assertThat(t1.getT_int(), is(t2.getT_int()));
        assertThat(t1.getT_list_int(), equalTo(t2.getT_list_int()));
        assertThat(t1.getT_list_text(), equalTo(t2.getT_list_text()));
        assertThat(t1.getT_map(), equalTo(t2.getT_map()));
        assertThat(t1.getT_set_int(), equalTo(t2.getT_set_int()));
        assertThat(t1.getT_set_text(), equalTo(t2.getT_set_text()));
        assertThat(t1.getT_smallint(), is(t2.getT_smallint()));
        assertThat(t1.getT_text(), is(t2.getT_text()));
        assertThat(t1.getT_time(), is(t2.getT_time()));
        assertThat(t1.getT_timestamp(), is(t2.getT_timestamp()));
        assertThat(t1.getT_timeuuid(), is(t2.getT_timeuuid()));
        assertThat(t1.getT_tinyint(), is(t2.getT_tinyint()));
        assertThat(t1.getT_varchar(), is(t2.getT_varchar()));
        assertThat(t1.getT_varint(), is(t2.getT_varint()));
        assertThat(t1.getT_tuple(), equalTo(t2.getT_tuple()));
        assertThat(t1.getT_uuid(), is(t2.getT_uuid()));
        assertThat(t1.getT_type1().getT_varchar(), is(t2.getT_type1().getT_varchar()));
        assertThat(t1.getT_type1().getT_int(), is(t2.getT_type1().getT_int()));
    }
}
