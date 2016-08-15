package org.sqlproc.engine.impl;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.junit.Test;
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.impl.SqlMetaStatement.Type;
import org.sqlproc.engine.model.Type1;
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
        assertThat(typesDb, notNullValue());
        assertThat(typesDb.getT_ascii(), is("ascii"));
        assertThat(typesDb.getT_bigint(), is(2L));
        assertThat(new String(typesDb.getT_blob().array()), is("{\"blob1\": \"blob2\"}"));
        assertThat(typesDb.getT_boolean(), is(true));
        assertThat(typesDb.getT_date(), is(LocalDate.of(2016, 7, 26)));
        assertThat(typesDb.getT_decimal(), is(new BigDecimal(3)));
        assertThat(typesDb.getT_double(), is(4.0));
        assertThat(typesDb.getT_float(), is(5.0F));
        assertThat(typesDb.getT_inet(), is(InetAddress.getByName("1.2.3.4")));
        assertThat(typesDb.getT_int(), is(6));
        assertThat(typesDb.getT_list_int(), is(Arrays.asList(101, 102)));
        assertThat(typesDb.getT_map().size(), is(2));
        assertThat(typesDb.getT_map().get(201), is("map1"));
        assertThat(typesDb.getT_map().get(202), is("map2"));
        assertThat(typesDb.getT_set_int(), is(new HashSet<>(Arrays.asList(301, 302))));
        assertThat(typesDb.getT_smallint(), is((short) 7));
        assertThat(typesDb.getT_text(), is("text"));
        assertThat(typesDb.getT_timestamp(), is(Instant.parse("2016-07-26T08:11:12Z")));
        assertThat(typesDb.getT_timeuuid(), is(UUID.fromString("e12229de-5eda-11e6-a6a7-cc3d827302bc")));
        assertThat(typesDb.getT_tinyint(), is(new Byte((byte) 8)));
        assertThat(typesDb.getT_tuple().getInt(0), is(401));
        assertThat(typesDb.getT_tuple().getInt(0), is(401));
        assertThat(typesDb.getT_tuple().getString(1), is("tuple"));
        assertThat(typesDb.getT_tuple().getFloat(2), is(402.0F));
        assertThat(typesDb.getT_type1(), is(new Type1("varchar", 501)));
        assertThat(typesDb.getT_text(), is("text"));
        assertThat(typesDb.getT_uuid(), is(UUID.fromString("a9c9b8ae-4911-4bf4-a855-4b5f634d0664")));
        assertThat(typesDb.getT_varchar(), is("varchar"));
        assertThat(typesDb.getT_varint(), is(new BigInteger("9")));
    }

    @Test
    public void testGetNull() {
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
