package org.sqlproc.engine.impl;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.net.UnknownHostException;

import org.junit.Test;
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.impl.SqlMetaStatement.Type;
import org.sqlproc.engine.model.Types;

public class TestGet extends TestDatabase {

    @Test
    public void testGetFull() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);
        SqlCrudEngine sqlEngine = getCrudEngine("GET_TYPES");

        Types types = new Types(1);
        String sql = sqlEngine.getSql(types, null, Type.QUERY);
        System.out.println(sql);
        Types typesDb = sqlEngine.get(session, Types.class, types);
        System.out.println(typesDb);
        Types.assertTypes(typesDb, Types.getDefaultTypes(basicCQLUnit.cluster));
    }

    @Test
    public void testGetNotExisting() {
        SqlSession session = getSession(basicCQLUnit);
        SqlCrudEngine sqlEngine = getCrudEngine("GET_TYPES");

        Types types = new Types(9999);
        String sql = sqlEngine.getSql(types, null, Type.QUERY);
        System.out.println(sql);
        Types typesDb = sqlEngine.get(session, Types.class, types);
        System.out.println(typesDb);
        assertThat(typesDb, nullValue());
    }
}
