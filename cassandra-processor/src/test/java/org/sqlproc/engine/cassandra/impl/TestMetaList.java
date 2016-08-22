package org.sqlproc.engine.cassandra.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.net.UnknownHostException;
import java.util.List;

import org.junit.Test;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.cassandra.model.NativeTypes;
import org.sqlproc.engine.cassandra.model.Types;

public class TestMetaList extends TestDatabase {

    @Test
    public void testListFull() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);

        SqlQueryEngine sqlEngine = getQueryEngine("LIST_META_TYPES");
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
        SqlSession session = getSession(basicCQLUnit);

        SqlQueryEngine sqlEngine = getQueryEngine("LIST_META_TYPES");
        Types types = new Types(2);
        String sql = sqlEngine.getSql(types, null, SqlQueryEngine.NO_ORDER);
        System.out.println(sql);

        List<Types> list = sqlEngine.query(session, Types.class, types);
        System.out.println(list);
        assertThat(list, hasSize(1));
        Types.assertTypes(list.get(0), Types.getNativeNullTypes(2));
    }

    @Test
    public void testNativeListFull() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);

        SqlQueryEngine sqlEngine = getQueryEngine("LIST_META_TYPES");
        NativeTypes types = new NativeTypes(1);
        String sql = sqlEngine.getSql(types, null, SqlQueryEngine.NO_ORDER);
        System.out.println(sql);

        List<NativeTypes> list = sqlEngine.query(session, NativeTypes.class, types);
        System.out.println(list);
        assertThat(list, hasSize(1));
        NativeTypes.assertTypes(list.get(0), NativeTypes.getDefaultTypes(basicCQLUnit.cluster));
    }

    @Test
    public void testNativeListNull() {
        SqlSession session = getSession(basicCQLUnit);

        SqlQueryEngine sqlEngine = getQueryEngine("LIST_META_TYPES");
        NativeTypes types = new NativeTypes(2);
        String sql = sqlEngine.getSql(types, null, SqlQueryEngine.NO_ORDER);
        System.out.println(sql);

        List<NativeTypes> list = sqlEngine.query(session, NativeTypes.class, types);
        System.out.println(list);
        assertThat(list, hasSize(1));
        NativeTypes.assertTypes(list.get(0), NativeTypes.getNullTypes(2));
    }
}
