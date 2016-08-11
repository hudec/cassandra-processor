package org.sqlproc.engine.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.cassandraunit.CassandraCQLUnit;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.junit.Rule;
import org.junit.Test;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.cassandra.CassandraSimpleSession;
import org.sqlproc.engine.model.Type1;
import org.sqlproc.engine.model.Type1Codec;
import org.sqlproc.engine.model.Types;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.TupleType;
import com.datastax.driver.core.TupleValue;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.UserType;
import com.datastax.driver.extras.codecs.jdk8.InstantCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalDateCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalTimeCodec;

public class TestBasic extends TestDatabase {

    @Rule
    public CassandraCQLUnit basicCQLUnit = new CassandraCQLUnit(new ClassPathCQLDataSet("simple.cql", "basic"));

    @Test
    public void testBasic() throws UnknownHostException {
        UserType type1Type = basicCQLUnit.cluster.getMetadata().getKeyspace("basic").getUserType("type1");
        Type1Codec type1Codec = new Type1Codec(TypeCodec.userType(type1Type), Type1.class);
        basicCQLUnit.cluster.getConfiguration().getCodecRegistry().register(InstantCodec.instance,
                LocalTimeCodec.instance, LocalDateCodec.instance, type1Codec);

        CassandraSimpleSession session = new CassandraSimpleSession(basicCQLUnit.session);
        SqlQueryEngine sqlEngine = getQueryEngine("SIMPLE_TYPES");
        Types types = new Types(1);
        String sql = sqlEngine.getSql(types, null, SqlQueryEngine.NO_ORDER);
        System.out.println(sql);
        List<Types> list = sqlEngine.query(session, Types.class, types);
        System.out.println(list);
        assertThat(list, hasSize(1));
        assertThat(list.get(0), notNullValue());
        assertThat(list.get(0).getId(), is(1));
        assertThat(list.get(0).getT_ascii(), is("ascii"));
        assertThat(list.get(0).getT_bigint(), is(2L));
        assertThat(list.get(0).getT_blob().array(), equalTo("{\"blob1\": \"blob2\"}".getBytes()));
        assertThat(list.get(0).getT_boolean(), is(true));
        assertThat(list.get(0).getT_date(), is(LocalDate.of(2016, 7, 26)));
        assertThat(list.get(0).getT_decimal(), is(BigDecimal.valueOf(3)));
        assertThat(list.get(0).getT_double(), is(4.0));
        assertThat(list.get(0).getT_float(), is(5.0F));
        assertThat(list.get(0).getT_inet(), is(InetAddress.getByName("1.2.3.4")));
        assertThat(list.get(0).getT_int(), is(6));
        assertThat(list.get(0).getT_list_int(), equalTo(Arrays.asList(101, 102)));
        assertThat(list.get(0).getT_list_text(), equalTo(Arrays.asList("list1", "list2")));
        Map<Integer, String> map = new HashMap<>();
        map.put(201, "map1");
        map.put(202, "map2");
        assertThat(list.get(0).getT_map(), equalTo(map));
        assertThat(list.get(0).getT_set_int(), equalTo(new HashSet<>(Arrays.asList(301, 302))));
        assertThat(list.get(0).getT_set_text(), equalTo(new HashSet<>(Arrays.asList("set1", "set2"))));
        assertThat(list.get(0).getT_smallint(), is((short) 7));
        assertThat(list.get(0).getT_text(), is("text"));
        assertThat(list.get(0).getT_time(), is(LocalTime.of(10, 11, 12)));
        // TODO zone?
        assertThat(list.get(0).getT_timestamp(), is(Instant.parse("2016-07-26T08:11:12Z")));
        assertThat(list.get(0).getT_timeuuid(), is(UUID.fromString("e12229de-5eda-11e6-a6a7-cc3d827302bc")));
        assertThat(list.get(0).getT_tinyint(), is((byte) 8));
        assertThat(list.get(0).getT_varchar(), is("varchar"));
        assertThat(list.get(0).getT_varint(), is(BigInteger.valueOf(9)));
        TupleType tupleType = basicCQLUnit.cluster.getMetadata().newTupleType(DataType.cint(), DataType.text(),
                DataType.cfloat());
        TupleValue tuple = tupleType.newValue();
        tuple.setInt(0, 401);
        tuple.setString(1, "tuple");
        tuple.setFloat(2, 402.0f);
        assertThat(list.get(0).getT_tuple(), equalTo(tuple));
        assertThat(list.get(0).getT_uuid(), is(UUID.fromString("a9c9b8ae-4911-4bf4-a855-4b5f634d0664")));
    }

    @Test
    public void testBasicNull() {
        basicCQLUnit.cluster.getConfiguration().getCodecRegistry().register(InstantCodec.instance,
                LocalTimeCodec.instance, LocalDateCodec.instance);
        CassandraSimpleSession session = new CassandraSimpleSession(basicCQLUnit.session);
        SqlQueryEngine sqlEngine = getQueryEngine("SIMPLE_TYPES");
        Types types = new Types(2);
        String sql = sqlEngine.getSql(types, null, SqlQueryEngine.NO_ORDER);
        System.out.println(sql);
        List<Types> list = sqlEngine.query(session, Types.class, types);
        System.out.println(list);
        assertThat(list, hasSize(1));
        assertThat(list.get(0), notNullValue());
        assertThat(list.get(0).getId(), is(2));
        assertThat(list.get(0).getT_ascii(), nullValue());
        assertThat(list.get(0).getT_bigint(), is(0L));
        assertThat(list.get(0).getT_blob(), nullValue());
        assertThat(list.get(0).getT_boolean(), is(false));
        assertThat(list.get(0).getT_date(), nullValue());
        assertThat(list.get(0).getT_decimal(), nullValue());
        assertThat(list.get(0).getT_double(), is(0.0));
        assertThat(list.get(0).getT_float(), is(0.0F));
        assertThat(list.get(0).getT_inet(), nullValue());
        assertThat(list.get(0).getT_int(), is(0));
        assertThat(list.get(0).getT_list_int(), hasSize(0));
        assertThat(list.get(0).getT_list_text(), hasSize(0));
        assertThat(list.get(0).getT_map().size(), is(0));
        assertThat(list.get(0).getT_set_int(), hasSize(0));
        assertThat(list.get(0).getT_set_text(), hasSize(0));
        assertThat(list.get(0).getT_smallint(), is((short) 0));
        assertThat(list.get(0).getT_text(), nullValue());
        assertThat(list.get(0).getT_time(), nullValue());
        assertThat(list.get(0).getT_timestamp(), nullValue());
        assertThat(list.get(0).getT_timeuuid(), nullValue());
        assertThat(list.get(0).getT_tinyint(), is((byte) 0));
        assertThat(list.get(0).getT_varchar(), nullValue());
        assertThat(list.get(0).getT_varint(), nullValue());
        assertThat(list.get(0).getT_tuple(), nullValue());
        assertThat(list.get(0).getT_uuid(), nullValue());
    }
}
