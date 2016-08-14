package org.sqlproc.engine.impl;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
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
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.impl.SqlMetaStatement.Type;
import org.sqlproc.engine.model.Type1;
import org.sqlproc.engine.model.Types;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.TupleType;
import com.datastax.driver.core.TupleValue;

public class TestInsert extends TestDatabase {

    @Rule
    public CassandraCQLUnit basicCQLUnit = new CassandraCQLUnit(new ClassPathCQLDataSet("simple.cql", "basic"));

    @Test
    public void testInsertFull() throws UnknownHostException {
        registerTypes(basicCQLUnit);
        SqlSession session = getSession(basicCQLUnit);

        SqlCrudEngine sqlEngine = getCrudEngine("INSERT_TYPES");
        Types types = new Types();
        types.setId(101);
        types.setT_ascii("ascii");
        types.setT_bigint(Long.MAX_VALUE);
        types.setT_blob(ByteBuffer.wrap("{\"blob1\": \"blob2\"}".getBytes()));
        types.setT_boolean(true);
        types.setT_date(LocalDate.now());
        types.setT_decimal(BigDecimal.ONE);
        types.setT_double(Double.MAX_VALUE);
        types.setT_float(Float.MAX_VALUE);
        types.setT_inet(InetAddress.getByName("1.2.3.4"));
        types.setT_int(Integer.MAX_VALUE);
        types.setT_list_int(Arrays.asList(101, 102));
        types.setT_list_text(Arrays.asList("value1", "value2"));
        Map<Integer, String> map = new HashMap<>();
        map.put(201, "map1");
        map.put(202, "map2");
        types.setT_map(map);
        types.setT_set_int(new HashSet<>(Arrays.asList(301, 302)));
        types.setT_set_text(new HashSet<>(Arrays.asList("set1", "set2")));
        types.setT_smallint(Short.MAX_VALUE);
        types.setT_text("ěščřžýáíéúů");
        types.setT_time(LocalTime.now());
        types.setT_timestamp(Instant.now());
        types.setT_timeuuid(UUID.fromString("e12229de-5eda-11e6-a6a7-cc3d827302bc"));
        types.setT_tinyint(Byte.MAX_VALUE);
        types.setT_varchar("ěščřžýáíéúů");
        types.setT_varint(BigInteger.ONE);
        TupleType tupleType = basicCQLUnit.cluster.getMetadata().newTupleType(DataType.cint(), DataType.text(),
                DataType.cfloat());
        TupleValue tuple = tupleType.newValue();
        tuple.setInt(0, 401);
        tuple.setString(1, "tuple");
        tuple.setFloat(2, 402.0f);
        types.setT_tuple(tuple);
        types.setT_uuid(UUID.fromString("a9c9b8ae-4911-4bf4-a855-4b5f634d0664"));
        types.setT_type1(new Type1("varchar", 501));

        String sql = sqlEngine.getSql(types, null, Type.CREATE);
        System.out.println(sql);
        int count = sqlEngine.insert(session, types);
        System.out.println(types);
        assertThat(count, is(1));

        SqlQueryEngine sqlQueryEngine = getQueryEngine("LIST_TYPES");

        List<Types> list = sqlQueryEngine.query(session, Types.class, new Types(types.getId()));
        assertThat(list.size(), is(1));
        assertThat(list.get(0), notNullValue());
        Types.assertTypes(list.get(0), types);
    }

    @Test
    public void testInsertNull() throws UnknownHostException {
        registerTypes(basicCQLUnit);
        SqlSession session = getSession(basicCQLUnit);

        SqlCrudEngine sqlEngine = getCrudEngine("INSERT_TYPES");
        Types types = new Types();
        types.setId(102);

        String sql = sqlEngine.getSql(types, null, Type.CREATE);
        System.out.println(sql);
        int count = sqlEngine.insert(session, types);
        System.out.println(types);
        assertThat(count, is(1));

        SqlQueryEngine sqlQueryEngine = getQueryEngine("LIST_TYPES");
        List<Types> list = sqlQueryEngine.query(session, Types.class, new Types(types.getId()));
        assertThat(list.size(), is(1));
        assertThat(list.get(0), notNullValue());
        Types.assertTypes(list.get(0), Types.getNullTypes(102));
    }
}
