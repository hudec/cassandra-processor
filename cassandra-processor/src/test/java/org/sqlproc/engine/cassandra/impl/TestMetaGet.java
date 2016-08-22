package org.sqlproc.engine.cassandra.impl;

import java.net.UnknownHostException;

import org.junit.Test;
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.cassandra.model.ClusteringTypes;
import org.sqlproc.engine.cassandra.model.NativeClusteringTypes;
import org.sqlproc.engine.impl.SqlMetaStatement.Type;

public class TestMetaGet extends TestDatabase {

    @Test
    public void testGetFullWhere() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);
        SqlCrudEngine sqlEngine = getCrudEngine("GET_CLUSTERING_META_TYPES");

        ClusteringTypes types = ClusteringTypes.getDefaultTypes(basicCQLUnit.cluster);
        types.setId(null);
        String sql = sqlEngine.getSql(types, null, Type.QUERY);
        System.out.println(sql);
        ClusteringTypes typesDb = sqlEngine.get(session, ClusteringTypes.class, types);
        System.out.println(typesDb);
        ClusteringTypes.assertClusteringTypes(typesDb, ClusteringTypes.getDefaultTypes(basicCQLUnit.cluster));
    }

    @Test
    public void testNativeGetFullWhere() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);
        SqlCrudEngine sqlEngine = getCrudEngine("GET_CLUSTERING_META_TYPES");

        NativeClusteringTypes types = NativeClusteringTypes.getDefaultTypes(basicCQLUnit.cluster);
        String sql = sqlEngine.getSql(types, null, Type.QUERY);
        System.out.println(sql);
        NativeClusteringTypes typesDb = sqlEngine.get(session, NativeClusteringTypes.class, types);
        System.out.println(typesDb);
        NativeClusteringTypes.assertClusteringTypes(typesDb,
                NativeClusteringTypes.getDefaultTypes(basicCQLUnit.cluster));
    }
}
