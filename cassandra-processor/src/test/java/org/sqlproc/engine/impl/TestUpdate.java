package org.sqlproc.engine.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.impl.SqlMetaStatement.Type;
import org.sqlproc.engine.model.Types;

public class TestUpdate extends TestDatabase {

    @Test
    public void testUpdateFull() {
        SqlSession session = getSession(basicCQLUnit);

        SqlCrudEngine sqlEngine = getCrudEngine("UPDATE_TYPES");
        Types types = new Types();
        types.setId(1);

        String sql = sqlEngine.getSql(types, null, Type.UPDATE);
        System.out.println(sql);
        int count = sqlEngine.update(session, types);
        System.out.println(types);
        assertThat(count, is(1));
    }

    @Test
    public void testUpdateNull() {
        SqlSession session = getSession(basicCQLUnit);

        SqlCrudEngine sqlEngine = getCrudEngine("UPDATE_TYPES");
        Types types = new Types();
        types.setId(9999);

        String sql = sqlEngine.getSql(types, null, Type.UPDATE);
        System.out.println(sql);
        int count = sqlEngine.update(session, types);
        System.out.println(types);
        assertThat(count, is(0));
    }

}
