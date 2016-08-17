package org.sqlproc.engine.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.net.UnknownHostException;
import java.util.List;

import org.junit.Test;
import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.cassandra.CassandraSimpleSession;
import org.sqlproc.engine.cassandra.CassandraStandardControl;
import org.sqlproc.engine.model.Types;

import com.datastax.driver.core.BatchStatement;

public class TestBatch extends TestDatabase {

    @Test
    public void testInsertFull() throws UnknownHostException {
        CassandraSimpleSession session = getSession(basicCQLUnit);

        SqlQueryEngine sqlQueryEngine = getQueryEngine("LIST_TYPES");
        List<Types> list = sqlQueryEngine.query(session, Types.class);

        SqlCrudEngine sqlEngine = getCrudEngine("INSERT_TYPES");
        CassandraStandardControl csc = new CassandraStandardControl();
        csc.setBatchStatement(new BatchStatement());

        Types types101 = Types.getNewTypes(basicCQLUnit.cluster, 101);
        int count = sqlEngine.insert(session, types101, csc);
        assertThat(count, is(0));
        Types types102 = Types.getNewTypes(basicCQLUnit.cluster, 102);
        count = sqlEngine.insert(session, types102, csc);
        assertThat(count, is(0));
        Types types103 = Types.getNewTypes(basicCQLUnit.cluster, 103);
        count = sqlEngine.insert(session, types103, csc);
        assertThat(count, is(0));
        count = session.executeBatch(csc.getBatchStatement(), null);
        assertThat(count, is(1));

        List<Types> list2 = sqlQueryEngine.query(session, Types.class);
        assertThat(list2.size(), is(list.size() + 3));
    }
}
