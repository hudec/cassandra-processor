package org.sqlproc.engine.cassandra.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.net.UnknownHostException;
import java.util.List;

import org.junit.Test;
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.cassandra.model.EnumInt;
import org.sqlproc.engine.cassandra.model.EnumTypes;
import org.sqlproc.engine.cassandra.model.EnumVarchar;

public class TestEnum extends TestDatabase {

    @Test
    public void testEnumAll() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);

        SqlQueryEngine sqlEngine = getQueryEngine("LIST_ENUM_TYPES");
        List<EnumTypes> list = sqlEngine.query(session, EnumTypes.class);
        System.out.println(list);
        assertThat(list, hasSize(3));
        assertThat(list.get(0).getT_int(), is(EnumInt.ENUM1));
        assertThat(list.get(1).getT_int(), is(EnumInt.ENUM2));
        assertThat(list.get(2).getT_int(), is(EnumInt.ENUM3));
        assertThat(list.get(0).getT_varchar(), is(EnumVarchar.ENUM1));
        assertThat(list.get(1).getT_varchar(), is(EnumVarchar.ENUM2));
        assertThat(list.get(2).getT_varchar(), is(EnumVarchar.ENUM3));
    }

    @Test
    public void testInsertFull() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);

        SqlCrudEngine sqlEngine = getCrudEngine("INSERT_ENUM_TYPES");

        EnumTypes types = new EnumTypes(201);
        types.setT_int(EnumInt.ENUM1);
        types.setT_varchar(EnumVarchar.ENUM1);
        int count = sqlEngine.insert(session, types);
        System.out.println(types);
        assertThat(count, is(1));

        SqlQueryEngine sqlQueryEngine = getQueryEngine("LIST_ENUM_TYPES");

        List<EnumTypes> list = sqlQueryEngine.query(session, EnumTypes.class, new EnumTypes(201));
        assertThat(list.size(), is(1));
        assertThat(list.get(0), notNullValue());
        assertThat(list.get(0).getT_int(), is(EnumInt.ENUM1));
        assertThat(list.get(0).getT_varchar(), is(EnumVarchar.ENUM1));
    }

    @Test
    public void testInsertNull() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);

        SqlCrudEngine sqlEngine = getCrudEngine("INSERT_ENUM_TYPES");

        EnumTypes types = new EnumTypes(201);
        int count = sqlEngine.insert(session, types);
        System.out.println(types);
        assertThat(count, is(1));

        SqlQueryEngine sqlQueryEngine = getQueryEngine("LIST_ENUM_TYPES");

        List<EnumTypes> list = sqlQueryEngine.query(session, EnumTypes.class, new EnumTypes(201));
        assertThat(list.size(), is(1));
        assertThat(list.get(0), notNullValue());
        assertThat(list.get(0).getT_int(), nullValue());
        assertThat(list.get(0).getT_varchar(), nullValue());
    }
}
