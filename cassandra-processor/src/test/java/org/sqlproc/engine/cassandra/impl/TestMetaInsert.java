package org.sqlproc.engine.cassandra.impl;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.net.UnknownHostException;
import java.util.List;

import org.junit.Test;
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.cassandra.model.NativeTypes;
import org.sqlproc.engine.cassandra.model.Types;
import org.sqlproc.engine.impl.SqlMetaStatement.Type;

public class TestMetaInsert extends TestDatabase {

    @Test
    public void testInsertFull() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);

        SqlCrudEngine sqlEngine = getCrudEngine("INSERT_META_TYPES");

        Types types = Types.getNewTypes(basicCQLUnit.cluster, 101);
        String sql = sqlEngine.getSql(types, null, Type.CREATE);
        System.out.println(sql);
        int count = sqlEngine.insert(session, types);
        System.out.println(types);
        assertThat(count, is(1));

        SqlQueryEngine sqlQueryEngine = getQueryEngine("LIST_TYPES");

        List<Types> list = sqlQueryEngine.query(session, Types.class, new Types(types.getId()));
        assertThat(list.size(), is(1));
        assertThat(list.get(0), notNullValue());
        Types.assertTypes(list.get(0), types);
    }

    @Test
    public void testInsertNull() {
        SqlSession session = getSession(basicCQLUnit);

        SqlCrudEngine sqlEngine = getCrudEngine("INSERT_META_TYPES");

        Types types = new Types(102);
        String sql = sqlEngine.getSql(types, null, Type.CREATE);
        System.out.println(sql);
        int count = sqlEngine.insert(session, types);
        System.out.println(types);
        assertThat(count, is(1));

        SqlQueryEngine sqlQueryEngine = getQueryEngine("LIST_TYPES");
        List<Types> list = sqlQueryEngine.query(session, Types.class, new Types(types.getId()));
        assertThat(list.size(), is(1));
        assertThat(list.get(0), notNullValue());
        Types.assertTypes(list.get(0), Types.getNullTypes(102));
    }

    @Test
    public void testInsertExistingRow() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);

        SqlCrudEngine sqlEngine = getCrudEngine("INSERT_META_TYPES");

        Types types = Types.getNewTypes(basicCQLUnit.cluster, 2);
        String sql = sqlEngine.getSql(types, null, Type.CREATE);
        System.out.println(sql);
        int count = sqlEngine.insert(session, types);
        System.out.println(types);
        assertThat(count, is(1));

        SqlQueryEngine sqlQueryEngine = getQueryEngine("LIST_TYPES");

        List<Types> list = sqlQueryEngine.query(session, Types.class, new Types(types.getId()));
        assertThat(list.size(), is(1));
        assertThat(list.get(0), notNullValue());
        Types.assertTypes(list.get(0), types);
    }

    @Test
    public void testNativeInsertFull() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);

        SqlCrudEngine sqlEngine = getCrudEngine("INSERT_META_TYPES");

        NativeTypes types = NativeTypes.getNewTypes(basicCQLUnit.cluster, 101);
        String sql = sqlEngine.getSql(types, null, Type.CREATE);
        System.out.println(sql);
        int count = sqlEngine.insert(session, types);
        System.out.println(types);
        assertThat(count, is(1));

        SqlQueryEngine sqlQueryEngine = getQueryEngine("LIST_TYPES");

        List<NativeTypes> list = sqlQueryEngine.query(session, NativeTypes.class, new NativeTypes(types.getId()));
        assertThat(list.size(), is(1));
        assertThat(list.get(0), notNullValue());
        NativeTypes.assertTypes(list.get(0), types);
    }

    @Test
    public void testNativeInsertNull() {
        SqlSession session = getSession(basicCQLUnit);

        SqlCrudEngine sqlEngine = getCrudEngine("INSERT_META_TYPES");

        NativeTypes types = new NativeTypes(102);
        String sql = sqlEngine.getSql(types, null, Type.CREATE);
        System.out.println(sql);
        int count = sqlEngine.insert(session, types);
        System.out.println(types);
        assertThat(count, is(1));

        SqlQueryEngine sqlQueryEngine = getQueryEngine("LIST_TYPES");
        List<NativeTypes> list = sqlQueryEngine.query(session, NativeTypes.class, new NativeTypes(types.getId()));
        assertThat(list.size(), is(1));
        assertThat(list.get(0), notNullValue());
        NativeTypes.assertTypes(list.get(0), NativeTypes.getNullTypes(102));
    }

    @Test
    public void testNativeInsertExistingRow() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);

        SqlCrudEngine sqlEngine = getCrudEngine("INSERT_META_TYPES");

        NativeTypes types = NativeTypes.getNewTypes(basicCQLUnit.cluster, 2);
        String sql = sqlEngine.getSql(types, null, Type.CREATE);
        System.out.println(sql);
        int count = sqlEngine.insert(session, types);
        System.out.println(types);
        assertThat(count, is(1));

        SqlQueryEngine sqlQueryEngine = getQueryEngine("LIST_TYPES");

        List<NativeTypes> list = sqlQueryEngine.query(session, NativeTypes.class, new NativeTypes(types.getId()));
        assertThat(list.size(), is(1));
        assertThat(list.get(0), notNullValue());
        NativeTypes.assertTypes(list.get(0), types);
    }
}