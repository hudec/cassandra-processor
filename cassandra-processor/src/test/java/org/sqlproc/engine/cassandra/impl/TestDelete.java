package org.sqlproc.engine.cassandra.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.cassandra.model.Types;
import org.sqlproc.engine.impl.SqlMetaStatement.Type;

public class TestDelete extends TestDatabase {

    @Test
    public void testDeleteFull() {
        SqlSession session = getSession(basicCQLUnit);
        SqlCrudEngine sqlEngine = getCrudEngine("DELETE_TYPES");

        Types types = new Types();
        types.setId(1);
        String sql = sqlEngine.getSql(types, null, Type.DELETE);
        System.out.println(sql);
        int count = sqlEngine.delete(session, types);
        System.out.println(types);
        assertThat(count, is(1));
    }

    @Test
    public void testDeleteNotExisting() {
        SqlSession session = getSession(basicCQLUnit);
        SqlCrudEngine sqlEngine = getCrudEngine("DELETE_TYPES");

        Types types = new Types();
        types.setId(9999);
        String sql = sqlEngine.getSql(types, null, Type.DELETE);
        System.out.println(sql);
        int count = sqlEngine.delete(session, types);
        System.out.println(types);
        assertThat(count, is(1));
    }

    @Test
    public void testDeleteConditional() {
        SqlSession session = getSession(basicCQLUnit);
        SqlCrudEngine sqlEngine = getCrudEngine("DELETE_TYPES_IF_EXISTS");

        Types types = new Types();
        types.setId(9999);
        String sql = sqlEngine.getSql(types, null, Type.DELETE);
        System.out.println(sql);
        int count = sqlEngine.delete(session, types);
        System.out.println(types);
        assertThat(count, is(0));
    }
}
