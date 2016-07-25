package org.sqlproc.engine.cassandra;

import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlSessionFactory;

import com.datastax.driver.core.Session;

/**
 * The simple implementation of the factory {@link SqlSessionFactory} for the JDBC stack.
 * 
 * <p>
 * For more info please see the <a href="https://github.com/hudec/sql-processor/wiki">Tutorials</a>.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class JdbcSessionFactory implements SqlSessionFactory {

    /**
     * The Cassandra session. It holds connections to a Cassandra cluster.
     */
    private Session session;

    /**
     * Creates a new instance.
     * 
     * @param session
     *            the Cassandra session
     */
    public JdbcSessionFactory(Session session) {
        super();
        this.session = session;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlSession getSqlSession() {
        return JdbcSession.generateProxy(session);
    }
}
