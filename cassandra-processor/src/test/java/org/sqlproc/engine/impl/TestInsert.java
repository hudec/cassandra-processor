package org.sqlproc.engine.impl;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.net.UnknownHostException;
import java.util.List;

import org.junit.Test;
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.impl.SqlMetaStatement.Type;
import org.sqlproc.engine.model.Types;

public class TestInsert extends TestDatabase {

    @Test
    public void testInsertFull() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);

        SqlCrudEngine sqlEngine = getCrudEngine("INSERT_TYPES");

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

        SqlCrudEngine sqlEngine = getCrudEngine("INSERT_TYPES");
        Types types = new Types();
        types.setId(102);

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

        SqlCrudEngine sqlEngine = getCrudEngine("INSERT_TYPES");

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
    public void testInsertConditional() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);

        SqlCrudEngine sqlEngine = getCrudEngine("INSERT_TYPES_IF_NOT_EXISTS");

        Types types = Types.getNewTypes(basicCQLUnit.cluster, 1);
        String sql = sqlEngine.getSql(types, null, Type.CREATE);
        System.out.println(sql);
        int count = sqlEngine.insert(session, types);
        System.out.println(types);
        assertThat(count, is(0));

        SqlQueryEngine sqlQueryEngine = getQueryEngine("LIST_TYPES");

        List<Types> list = sqlQueryEngine.query(session, Types.class, new Types(types.getId()));
        assertThat(list.size(), is(1));
        assertThat(list.get(0), notNullValue());
        Types.assertTypes(list.get(0), Types.getDefaultTypes(basicCQLUnit.cluster));
    }
}
