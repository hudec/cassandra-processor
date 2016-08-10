package org.sqlproc.engine.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.cassandraunit.CassandraCQLUnit;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.junit.Rule;
import org.junit.Test;

import com.datastax.driver.core.ResultSet;

public class TestCQL extends TestDatabase {

    @Rule
    public CassandraCQLUnit basicCQLUnit = new CassandraCQLUnit(new ClassPathCQLDataSet("simple.cql", "basic"));

    @Test
    public void testCQL() {
        ResultSet result = basicCQLUnit.session.execute("select * from types WHERE id=1");
        assertThat(result.iterator().next().getString("t_ascii"), is("ascii"));
    }
}
