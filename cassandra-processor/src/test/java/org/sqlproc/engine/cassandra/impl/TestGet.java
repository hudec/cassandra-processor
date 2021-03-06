package org.sqlproc.engine.cassandra.impl;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.net.UnknownHostException;

import org.junit.Test;
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.cassandra.model.ClusteringTypes;
import org.sqlproc.engine.cassandra.model.NativeClusteringTypes;
import org.sqlproc.engine.cassandra.model.NativeTypes;
import org.sqlproc.engine.cassandra.model.Types;
import org.sqlproc.engine.impl.SqlMetaStatement.Type;

public class TestGet extends TestDatabase {

    @Test
    public void testGetFull() throws UnknownHostException {
        SqlSession session = getSession();
        SqlCrudEngine sqlEngine = getCrudEngine("GET_TYPES");

        Types types = new Types(1);
        String sql = sqlEngine.getSql(types, null, Type.QUERY);
        System.out.println(sql);
        Types typesDb = sqlEngine.get(session, Types.class, types);
        System.out.println(typesDb);
        Types.assertTypes(typesDb, Types.getDefaultTypes(cluster));
    }

    @Test
    public void testGetNotExisting() {
        SqlSession session = getSession();
        SqlCrudEngine sqlEngine = getCrudEngine("GET_TYPES");

        Types types = new Types(9999);
        String sql = sqlEngine.getSql(types, null, Type.QUERY);
        System.out.println(sql);
        Types typesDb = sqlEngine.get(session, Types.class, types);
        System.out.println(typesDb);
        assertThat(typesDb, nullValue());
    }

    @Test
    public void testGetFullWhere() throws UnknownHostException {
        SqlSession session = getSession();
        SqlCrudEngine sqlEngine = getCrudEngine("GET_CLUSTERING_TYPES");

        ClusteringTypes types = ClusteringTypes.getDefaultTypes(cluster);
        types.setId(null);
        String sql = sqlEngine.getSql(types, null, Type.QUERY);
        System.out.println(sql);
        ClusteringTypes typesDb = sqlEngine.get(session, ClusteringTypes.class, types);
        System.out.println(typesDb);
        ClusteringTypes.assertClusteringTypes(typesDb, ClusteringTypes.getDefaultTypes(cluster));
    }

    @Test
    public void testNativeGetFull() throws UnknownHostException {
        SqlSession session = getSession();
        SqlCrudEngine sqlEngine = getCrudEngine("GET_TYPES");

        NativeTypes types = new NativeTypes(1);
        String sql = sqlEngine.getSql(types, null, Type.QUERY);
        System.out.println(sql);
        NativeTypes typesDb = sqlEngine.get(session, NativeTypes.class, types);
        System.out.println(typesDb);
        NativeTypes.assertTypes(typesDb, NativeTypes.getDefaultTypes(cluster));
    }

    @Test
    public void testNativeGetNotExisting() {
        SqlSession session = getSession();
        SqlCrudEngine sqlEngine = getCrudEngine("GET_TYPES");

        NativeTypes types = new NativeTypes(9999);
        String sql = sqlEngine.getSql(types, null, Type.QUERY);
        System.out.println(sql);
        NativeTypes typesDb = sqlEngine.get(session, NativeTypes.class, types);
        System.out.println(typesDb);
        assertThat(typesDb, nullValue());
    }

    @Test
    public void testNativeGetFullWhere() throws UnknownHostException {
        SqlSession session = getSession();
        SqlCrudEngine sqlEngine = getCrudEngine("GET_CLUSTERING_TYPES");

        NativeClusteringTypes types = NativeClusteringTypes.getDefaultTypes(cluster);
        String sql = sqlEngine.getSql(types, null, Type.QUERY);
        System.out.println(sql);
        NativeClusteringTypes typesDb = sqlEngine.get(session, NativeClusteringTypes.class, types);
        System.out.println(typesDb);
        NativeClusteringTypes.assertClusteringTypes(typesDb, NativeClusteringTypes.getDefaultTypes(cluster));
    }
}
