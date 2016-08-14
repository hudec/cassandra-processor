package org.sqlproc.engine.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.net.UnknownHostException;
import java.util.List;

import org.cassandraunit.CassandraCQLUnit;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.junit.Rule;
import org.junit.Test;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.model.Types;

public class TestList extends TestDatabase {

    @Rule
    public CassandraCQLUnit basicCQLUnit = new CassandraCQLUnit(new ClassPathCQLDataSet("simple.cql", "basic"));

    @Test
    public void testListFull() throws UnknownHostException {
        registerTypes(basicCQLUnit);
        SqlSession session = getSession(basicCQLUnit);

        SqlQueryEngine sqlEngine = getQueryEngine("LIST_TYPES");
        Types types = new Types(1);
        String sql = sqlEngine.getSql(types, null, SqlQueryEngine.NO_ORDER);
        System.out.println(sql);

        List<Types> list = sqlEngine.query(session, Types.class, types);
        System.out.println(list);
        assertThat(list, hasSize(1));
        Types.assertTypes(list.get(0), Types.getDefaultTypes(basicCQLUnit.cluster));
    }

    @Test
    public void testListNull() {
        registerTypes(basicCQLUnit);
        SqlSession session = getSession(basicCQLUnit);

        SqlQueryEngine sqlEngine = getQueryEngine("LIST_TYPES");
        Types types = new Types(2);
        String sql = sqlEngine.getSql(types, null, SqlQueryEngine.NO_ORDER);
        System.out.println(sql);

        List<Types> list = sqlEngine.query(session, Types.class, types);
        System.out.println(list);
        assertThat(list, hasSize(1));
        Types.assertTypes(list.get(0), Types.getNullTypes(2));
    }
}
