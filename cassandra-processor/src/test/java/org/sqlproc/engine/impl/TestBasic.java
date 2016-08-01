package org.sqlproc.engine.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
        String sql = sqlEngine.getSql(null, null, SqlQueryEngine.NO_ORDER);
        System.out.println(sql);
        List<Types> list = sqlEngine.query(session, Types.class);
        System.out.println(list);
    }
}
