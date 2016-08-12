package org.sqlproc.engine.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cassandraunit.CassandraCQLUnit;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.junit.Rule;
import org.junit.Test;
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.cassandra.CassandraSimpleSession;
import org.sqlproc.engine.impl.SqlMetaStatement.Type;
import org.sqlproc.engine.model.Type1;
import org.sqlproc.engine.model.Type1Codec;
import org.sqlproc.engine.model.Types;

import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.UserType;
import com.datastax.driver.extras.codecs.jdk8.InstantCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalDateCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalTimeCodec;

public class TestInsert extends TestDatabase {

    @Rule
    public CassandraCQLUnit basicCQLUnit = new CassandraCQLUnit(new ClassPathCQLDataSet("simple.cql", "basic"));

    @Test
    public void testInsertFull() {
        UserType type1Type = basicCQLUnit.cluster.getMetadata().getKeyspace("basic").getUserType("type1");
        Type1Codec type1Codec = new Type1Codec(TypeCodec.userType(type1Type), Type1.class);
        basicCQLUnit.cluster.getConfiguration().getCodecRegistry().register(InstantCodec.instance,
                LocalTimeCodec.instance, LocalDateCodec.instance, type1Codec);

        CassandraSimpleSession session = new CassandraSimpleSession(basicCQLUnit.session);
        SqlCrudEngine sqlEngine = getCrudEngine("INSERT_TYPES");
        Types types = new Types();
        types.setId(2);
        types.setT_ascii("ascii");
        types.setT_bigint(Long.MAX_VALUE);
        types.setT_boolean(true);
        types.setT_date(LocalDate.now());
        types.setT_decimal(BigDecimal.ONE);
        types.setT_double(Double.MAX_VALUE);
        types.setT_float(Float.MAX_VALUE);
        types.setT_int(Integer.MAX_VALUE);
        types.setT_list_text(Arrays.asList("value1", "value2"));
        Map<Integer, String> map = new HashMap<>();
        map.put(201, "map1");
        map.put(202, "map2");
        types.setT_map(map);
        types.setT_smallint(Short.MAX_VALUE);
        types.setT_text("ěščřžýáíéúů");
        types.setT_time(LocalTime.now());
        types.setT_timestamp(Instant.now());
        types.setT_tinyint(Byte.MAX_VALUE);
        types.setT_varchar("ěščřžýáíéúů");
        types.setT_varint(BigInteger.ONE);
        String sql = sqlEngine.getSql(types, null, Type.CREATE);
        System.out.println(sql);
        int count = sqlEngine.insert(session, types);
        System.out.println(types);
        assertThat(count, is(1));

        SqlQueryEngine sqlQueryEngine = getQueryEngine("SIMPLE_TYPES");

        Types types2 = new Types();
        types2.setId(types.getId());
        List<Types> list = sqlQueryEngine.query(session, Types.class, types2);
        assertThat(list.size(), is(1));
        Types types3 = list.get(0);
        assertThat(types3.getT_varchar(), is("ěščřžýáíéúů"));
        assertThat(types.getT_ascii(), is("ascii"));
    }

}
