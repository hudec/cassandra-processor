package org.sqlproc.engine.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.net.UnknownHostException;

import org.junit.Test;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.model.Types;

public class TestCount extends TestDatabase {

    @Test
    public void testListOne() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);

        SqlQueryEngine sqlEngine = getQueryEngine("LIST_TYPES");
        Types types = new Types(1);
        String sql = sqlEngine.getSql(types, null, SqlQueryEngine.NO_ORDER);
        System.out.println(sql);

        int count = sqlEngine.queryCount(session, types);
        System.out.println(count);
        assertThat(count, is(1));
    }

    @Test
    public void testListAll() {
        SqlSession session = getSession(basicCQLUnit);

        SqlQueryEngine sqlEngine = getQueryEngine("LIST_TYPES");
        int count = sqlEngine.queryCount(session);
        System.out.println(count);
        assertThat(count, is(2));
    }

    @Test
    public void testListNone() {
        SqlSession session = getSession(basicCQLUnit);

        SqlQueryEngine sqlEngine = getQueryEngine("LIST_TYPES");
        int count = sqlEngine.queryCount(session, new Types(99999));
        System.out.println(count);
        assertThat(count, is(0));
    }
}
