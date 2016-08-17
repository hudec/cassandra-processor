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

public class TestUpdate extends TestDatabase {

    @Test
    public void testUpdateFull() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);

        SqlCrudEngine sqlEngine = getCrudEngine("UPDATE_TYPES");

        Types types = Types.getNewTypes(basicCQLUnit.cluster, 1);
        types.setId(1);
        String sql = sqlEngine.getSql(types, null, Type.UPDATE);
        System.out.println(sql);
        int count = sqlEngine.update(session, types);
        System.out.println(types);
        assertThat(count, is(1));

        SqlQueryEngine sqlQueryEngine = getQueryEngine("LIST_TYPES");

        List<Types> list = sqlQueryEngine.query(session, Types.class, new Types(types.getId()));
        assertThat(list.size(), is(1));
        assertThat(list.get(0), notNullValue());
        Types.assertTypes(list.get(0), types);
    }

    @Test
    public void testUpdateNull() {
        SqlSession session = getSession(basicCQLUnit);

        SqlCrudEngine sqlEngine = getCrudEngine("UPDATE_TYPES");
        Types types = new Types();
        types.setId(1);

        String sql = sqlEngine.getSql(types, null, Type.UPDATE);
        System.out.println(sql);
        int count = sqlEngine.update(session, types);
        System.out.println(types);
        assertThat(count, is(1));

        SqlQueryEngine sqlQueryEngine = getQueryEngine("LIST_TYPES");

        List<Types> list = sqlQueryEngine.query(session, Types.class, new Types(types.getId()));
        assertThat(list.size(), is(1));
        assertThat(list.get(0), notNullValue());
        Types.assertTypes(list.get(0), Types.getNullTypes(1));
    }

    @Test
    public void testUpdateNotExistingRow() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);

        SqlCrudEngine sqlEngine = getCrudEngine("UPDATE_TYPES");

        Types types = Types.getNewTypes(basicCQLUnit.cluster, 9999);
        String sql = sqlEngine.getSql(types, null, Type.UPDATE);
        System.out.println(sql);
        int count = sqlEngine.update(session, types);
        System.out.println(types);
        assertThat(count, is(1));

        SqlQueryEngine sqlQueryEngine = getQueryEngine("LIST_TYPES");

        List<Types> list = sqlQueryEngine.query(session, Types.class, new Types(9999));
        System.out.println(list);
        assertThat(list.size(), is(1));
        assertThat(list.get(0), notNullValue());
        Types.assertTypes(list.get(0), types);
    }

    @Test
    public void testUpdateConditional() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);

        SqlCrudEngine sqlEngine = getCrudEngine("UPDATE_TYPES_IF_EXISTS");

        Types types = Types.getNewTypes(basicCQLUnit.cluster, 9999);
        String sql = sqlEngine.getSql(types, null, Type.UPDATE);
        System.out.println(sql);
        int count = sqlEngine.update(session, types);
        System.out.println(types);
        assertThat(count, is(0));

        SqlQueryEngine sqlQueryEngine = getQueryEngine("LIST_TYPES");

        List<Types> list = sqlQueryEngine.query(session, Types.class, new Types(9999));
        System.out.println(list);
        assertThat(list.size(), is(0));
    }
}
