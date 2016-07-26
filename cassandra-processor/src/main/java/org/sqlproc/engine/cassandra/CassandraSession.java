package org.sqlproc.engine.cassandra;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.sqlproc.engine.SqlCrudEngine;
import org.sqlproc.engine.SqlQuery;
import org.sqlproc.engine.SqlQueryEngine;
import org.sqlproc.engine.SqlSession;

import com.datastax.driver.core.Session;

/**
 * The JDBC stack implementation of the SQL Engine session contract. In fact it's a proxy to the
 * {@link java.sql.Connection}.
 * 
 * It's the first parameter to all primary methods in the {@link SqlQueryEngine} and {@link SqlCrudEngine}.
 * 
 * <p>
 * The implementation is based on the dynamic proxy design pattern provided by the Java API.
 * <p>
 * The primary contract is the method for the {@link SqlQuery} instance creation.
 * 
 * <p>
 * For more info please see the <a href="https://github.com/hudec/sql-processor/wiki">Tutorials</a>.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraSession implements InvocationHandler {

    /**
     * The contracts implemented by this dynamic proxy.
     */
    private static final Class[] PROXY_INTERFACES = new Class[] { Session.class, SqlSession.class };

    /**
     * The Cassandra session. It holds connections to a Cassandra cluster.
     */
    private final Session session;

    /**
     * The name of the database related to this session. It's usage is implementation specific.
     */
    private String name;

    /**
     * Creates a new instance of this dynamic proxy.
     * 
     * @param session
     *            the Cassandra session
     */
    private CassandraSession(Session session) {
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
    public CassandraSession(Session session, String name) {
        this(session);
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("close".equals(method.getName())) {
            session.close();
            return null;
        }

        if ("createSqlQuery".equals(method.getName())) {
            String queryString = (String) args[0];
            return new CassandraQuery(session, queryString);
        }

        if ("executeBatch".equals(method.getName())) {
            String[] statements = (String[]) args[0];
            CassandraQuery jdbcQuery = new CassandraQuery(session, null);
            return jdbcQuery.executeBatch(statements);
        }

        if ("getName".equals(method.getName())) {
            return name;
        }

        try {
            return method.invoke(session, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    /**
     * The factory method to obtain this dynamic proxy.
     * 
     * @param session
     *            the Cassandra session
     * @return the JDBC stack implementation of the SQL Engine session contract
     */
    public static SqlSession generateProxy(Session session) {
        CassandraSession handler = new CassandraSession(session);
        return (SqlSession) Proxy.newProxyInstance(getProxyClassLoader(), PROXY_INTERFACES, handler);
    }

    /**
     * The factory method to obtain this dynamic proxy.
     * 
     * @param session
     *            the Cassandra session
     * @param name
     *            the name of the database
     * @return the JDBC stack implementation of the SQL Engine session contract
     */
    public static SqlSession generateProxy(Session session, String name) {
        CassandraSession handler = new CassandraSession(session, name);
        return (SqlSession) Proxy.newProxyInstance(getProxyClassLoader(), PROXY_INTERFACES, handler);
    }

    /**
     * Returns the class loader instance.
     * 
     * @return the class loader instance
     */
    public static ClassLoader getProxyClassLoader() {
        return SqlSession.class.getClassLoader();
    }
}
