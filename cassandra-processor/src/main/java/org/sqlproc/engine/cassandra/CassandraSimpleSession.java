package org.sqlproc.engine.cassandra;

import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlProcessorException;
import org.sqlproc.engine.SqlQuery;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.SqlSession;

import com.datastax.driver.core.Session;

/**
 * The JDBC stack implementation of the SQL Engine session contract. In fact it's a simple wrapper for the
 * {@link java.sql.Connection}.
 * 
 * It's the first parameter to all primary methods in the {@link SqlQueryEngine} and {@link SqlCrudEngine}.
 * 
 * <p>
 * The primary contract is the method for the {@link SqlQuery} instance creation.
 * 
 * <p>
 * For more info please see the <a href="https://github.com/hudec/sql-processor/wiki">Tutorials</a>.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraSimpleSession implements SqlSession {

    /**
     * The Cassandra session. It holds connections to a Cassandra cluster.
     */
    private Session session;

    /**
     * The name of the database related to this session. It's usage is implementation specific.
     */
    private String name;

    /**
     * Creates a new instance.
     * 
     * @param session
     *            the Cassandra session
     */
    public CassandraSimpleSession(Session session) {
        super();
        this.session = session;
    }

    /**
     * Creates a new instance of this dynamic proxy.
     * 
     * @param session
     *            the Cassandra session
     * @param name
     *            the name of the database
     */
    public CassandraSimpleSession(Session session, String name) {
        this(session);
        this.name = name;
    }

    /**
     * Returns the Cassandra session.
     * 
     * @return the Cassandra session
     */
    public Session getSession() {
        return session;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlQuery createSqlQuery(String queryString) throws SqlProcessorException {
        return new CassandraQuery(session, queryString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] executeBatch(String[] statements) throws SqlProcessorException {
        SqlQuery sqlQuery = createSqlQuery(null);
        return sqlQuery.executeBatch(statements);
    }

    /**
     * @return the name of the database related to this session. It's usage is implementation specific.
     */
    public String getName() {
        return name;
    }
}
