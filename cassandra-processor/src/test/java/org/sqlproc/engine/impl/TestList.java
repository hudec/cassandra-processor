package org.sqlproc.engine.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.net.UnknownHostException;
import java.util.List;

import org.cassandraunit.CassandraCQLUnit;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.junit.Rule;
import org.junit.Test;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.cassandra.CassandraSimpleSession;
import org.sqlproc.engine.model.Type1;
import org.sqlproc.engine.model.Type1Codec;
import org.sqlproc.engine.model.Types;

import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.UserType;
import com.datastax.driver.extras.codecs.jdk8.InstantCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalDateCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalTimeCodec;

public class TestList extends TestDatabase {

    @Rule
    public CassandraCQLUnit basicCQLUnit = new CassandraCQLUnit(new ClassPathCQLDataSet("simple.cql", "basic"));

    @Test
    public void testListFull() throws UnknownHostException {
        UserType type1Type = basicCQLUnit.cluster.getMetadata().getKeyspace("basic").getUserType("type1");
        Type1Codec type1Codec = new Type1Codec(TypeCodec.userType(type1Type), Type1.class);
        basicCQLUnit.cluster.getConfiguration().getCodecRegistry().register(InstantCodec.instance,
                LocalTimeCodec.instance, LocalDateCodec.instance, type1Codec);

        CassandraSimpleSession session = new CassandraSimpleSession(basicCQLUnit.session);
        SqlQueryEngine sqlEngine = getQueryEngine("LIST_TYPES");
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
        UserType type1Type = basicCQLUnit.cluster.getMetadata().getKeyspace("basic").getUserType("type1");
        Type1Codec type1Codec = new Type1Codec(TypeCodec.userType(type1Type), Type1.class);
        basicCQLUnit.cluster.getConfiguration().getCodecRegistry().register(InstantCodec.instance,
                LocalTimeCodec.instance, LocalDateCodec.instance, type1Codec);

        CassandraSimpleSession session = new CassandraSimpleSession(basicCQLUnit.session);
        SqlQueryEngine sqlEngine = getQueryEngine("LIST_TYPES");
        Types types = new Types(2);
        String sql = sqlEngine.getSql(types, null, SqlQueryEngine.NO_ORDER);
        System.out.println(sql);

        List<Types> list = sqlEngine.query(session, Types.class, types);
        System.out.println(list);
        assertThat(list, hasSize(1));
        Types.assertTypes(list.get(0), Types.getNullTypes(2));
    }
}
