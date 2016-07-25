package org.sqlproc.engine.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.datastax.driver.core.ResultSet;

public class TestBasic extends TestDatabase {

    @Test
    public void testSimple() {
        ResultSet result = cassandraCQLUnit.session
                .execute("select * from testCQLTable WHERE id=1690e8da-5bf8-49e8-9583-4dff8a570737");
        assertThat(result.iterator().next().getString("value"), is("Cql loaded string"));
    }
}
