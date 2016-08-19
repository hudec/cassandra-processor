package org.sqlproc.engine.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.net.UnknownHostException;
import java.util.List;

import org.junit.Test;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.model.EnumTypes;

public class TestEnum extends TestDatabase {

    @Test
    public void testEnumAll() throws UnknownHostException {
        SqlSession session = getSession(basicCQLUnit);

        SqlQueryEngine sqlEngine = getQueryEngine("LIST_ENUM_TYPES");
        List<EnumTypes> list = sqlEngine.query(session, EnumTypes.class);
        System.out.println(list);
        assertThat(list, hasSize(3));
        // Types.assertTypes(list.get(0), Types.getDefaultTypes(basicCQLUnit.cluster));
    }
}
