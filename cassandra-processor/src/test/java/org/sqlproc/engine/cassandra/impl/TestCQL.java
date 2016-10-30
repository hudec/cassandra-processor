package org.sqlproc.engine.cassandra.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.datastax.driver.core.ResultSet;

public class TestCQL extends TestDatabase {

    @Test
    public void testCQL() {
        ResultSet result = getSession().getSession().execute("select * from types WHERE id=1");
        assertThat(result.iterator().next().getString("t_ascii"), is("ascii"));
    }
}
