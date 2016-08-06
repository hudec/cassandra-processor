package org.sqlproc.engine.impl;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.cassandraunit.CassandraCQLUnit;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.junit.Rule;
import org.junit.Test;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.cassandra.CassandraSimpleSession;
import org.sqlproc.engine.model.Types;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.extras.codecs.jdk8.InstantCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalDateCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalTimeCodec;

public class TestBasic extends TestDatabase {

    @Rule
    public CassandraCQLUnit basicCQLUnit = new CassandraCQLUnit(new ClassPathCQLDataSet("simple.cql", "basic"));

    @Test
    public void testCQL() {
        ResultSet result = basicCQLUnit.session.execute("select * from types WHERE id=1");
        assertThat(result.iterator().next().getString("t_ascii"), is("ascii"));
    }

    @Test
    public void testBasic() {
        basicCQLUnit.cluster.getConfiguration().getCodecRegistry().register(InstantCodec.instance,
                LocalTimeCodec.instance, LocalDateCodec.instance);
        CassandraSimpleSession session = new CassandraSimpleSession(basicCQLUnit.session);
        SqlQueryEngine sqlEngine = getQueryEngine("SIMPLE_TYPES");
        Types types = new Types(1);
        String sql = sqlEngine.getSql(types, null, SqlQueryEngine.NO_ORDER);
        System.out.println(sql);
        List<Types> list = sqlEngine.query(session, Types.class, types);
        System.out.println(list);
        assertThat(list.size(), is(1));
        assertThat(list.get(0), notNullValue());
        assertThat(list.get(0).getT_ascii(), is("ascii"));
        assertThat(list.get(0).getT_bigint(), is(2L));
        assertThat(list.get(0).getT_boolean(), is(true));
        assertThat(list.get(0).getT_date().toString(), is("2016-07-26"));
        assertThat(list.get(0).getT_decimal(), is(BigDecimal.valueOf(3)));
        assertThat(list.get(0).getT_double(), is(4.0));
        assertThat(list.get(0).getT_float(), is(5.0F));
        assertThat(list.get(0).getT_int(), is(6));
        assertThat(list.get(0).getT_smallint(), is((short) 7));
        assertThat(list.get(0).getT_text(), is("text"));
        assertThat(list.get(0).getT_time().toString(), is("10:11:12"));
        assertThat(list.get(0).getT_timestamp().toString(), is("2016-07-26T08:11:12Z"));
        assertThat(list.get(0).getT_tinyint(), is((byte) 8));
        assertThat(list.get(0).getT_varchar(), is("varchar"));
        assertThat(list.get(0).getT_varint(), is(BigInteger.valueOf(9)));
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
    }
}
