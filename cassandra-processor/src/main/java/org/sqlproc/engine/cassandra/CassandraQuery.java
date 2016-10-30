package org.sqlproc.engine.cassandra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlProcessorException;
import org.sqlproc.engine.SqlQuery;
import org.sqlproc.engine.SqlRuntimeContext;
import org.sqlproc.engine.cassandra.type.CassandraSqlType;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;

/**
 * The JDBC stack implementation of the SQL Engine query contract. In fact it's an adapter the internal JDBC stuff.
 * 
 * <p>
 * For more info please see the <a href="https://github.com/hudec/sql-processor/wiki">Tutorials</a>.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraQuery implements SqlQuery {

    /**
     * The internal slf4j logger.
     */
    final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * The Cassandra session. It holds connections to a Cassandra cluster.
     */
    Session session;
    /**
     * The SQL query/statement command.
     */
    String queryString;
    /**
     * The collection of all scalars (output values declarations).
     */
    List<String> scalars = new ArrayList<String>();
    /**
     * The collection of all scalars types.
     */
    Map<String, CassandraSqlType> scalarTypes = new HashMap<String, CassandraSqlType>();
    /**
     * Class types including parameterized types for output attributes.
     */
    Map<String, Class<?>[]> scalarMoreTypes = new HashMap<String, Class<?>[]>();
    /**
     * The collection of all parameters (input value declarations).
     */
    List<String> parameters = new ArrayList<String>();
    /**
     * The collection of all parameters values.
     */
    Map<String, Object> parameterValues = new HashMap<String, Object>();
    /**
     * The collection of all parameters types.
     */
    Map<String, CassandraSqlType> parameterTypes = new HashMap<String, CassandraSqlType>();
    /**
     * Class types including parameterized types for input attributes.
     */
    Map<String, Class<?>[]> parameterMoreTypes = new HashMap<String, Class<?>[]>();
    /**
     * The compound parameters controlling the META SQL execution.
     */
    SqlControl sqlControl;
    /**
     * The SQL output is sorted.
     */
    boolean ordered;
    /**
     * The failed SQL command should be logged.
     */
    boolean logError;

    /**
     * The indicator there are no more data in ResultSet.
     */
    private static final Object NO_MORE_DATA = new Object();

    /**
     * Creates a new instance of this adapter.
     * 
     * @param session
     *            the Cassandra session
     * @param queryString
     *            the SQL query/statement command
     */
    public CassandraQuery(Session session, String queryString) {
        this.session = session;
        this.queryString = queryString;
        // logger.info("query: " + queryString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getQuery() {
        return session;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlQuery setSqlControl(SqlControl sqlControl) {
        this.sqlControl = sqlControl;
        if (sqlControl != null) {

        }
        return this;
    }

    private String getMaxResults() {
        return sqlControl != null ? " limit " + sqlControl.getMaxResults() : "";
    }

    private void controlStatement(BoundStatement st, SqlControl sqlControl) {
        // TODO setRoutingKey, set bs
        // TODO setPagingState
        if (sqlControl != null) {
            if (sqlControl.getMaxTimeout() != null)
                st.setReadTimeoutMillis(sqlControl.getMaxTimeout());
            if (sqlControl.getFetchSize() != null)
                st.setFetchSize(sqlControl.getFetchSize());
        }
    }

    private void controlStatement(PreparedStatement st, SqlControl sqlControl) {
        if (sqlControl != null && sqlControl instanceof CassandraStandardControl) {
            if (((CassandraStandardControl) sqlControl).getConsistencyLevel() != null)
                st.setConsistencyLevel(((CassandraStandardControl) sqlControl).getConsistencyLevel());
            if (((CassandraStandardControl) sqlControl).getSerialConsistencyLevel() != null)
                st.setSerialConsistencyLevel(((CassandraStandardControl) sqlControl).getSerialConsistencyLevel());
            if (((CassandraStandardControl) sqlControl).getTracing() != null) {
                if (((CassandraStandardControl) sqlControl).getTracing())
                    st.enableTracing();
                else
                    st.disableTracing();
            }
            if (((CassandraStandardControl) sqlControl).getRetryPolicy() != null)
                st.setRetryPolicy(((CassandraStandardControl) sqlControl).getRetryPolicy());
            if (((CassandraStandardControl) sqlControl).getIdempotent() != null)
                st.setIdempotent(((CassandraStandardControl) sqlControl).getIdempotent());
            if (((CassandraStandardControl) sqlControl).getOutgoingPayload() != null)
                st.setOutgoingPayload(((CassandraStandardControl) sqlControl).getOutgoingPayload());
        }
    }

    private void controlStatement(BatchStatement st, SqlControl sqlControl) {
        if (sqlControl != null) {
            if (sqlControl.getMaxTimeout() != null)
                st.setReadTimeoutMillis(sqlControl.getMaxTimeout());
            if (sqlControl.getFetchSize() != null)
                st.setFetchSize(sqlControl.getFetchSize());
        }
        if (sqlControl != null && sqlControl instanceof CassandraStandardControl) {
            if (((CassandraStandardControl) sqlControl).getConsistencyLevel() != null)
                st.setConsistencyLevel(((CassandraStandardControl) sqlControl).getConsistencyLevel());
            if (((CassandraStandardControl) sqlControl).getSerialConsistencyLevel() != null)
                st.setSerialConsistencyLevel(((CassandraStandardControl) sqlControl).getSerialConsistencyLevel());
            if (((CassandraStandardControl) sqlControl).getTracing() != null) {
                if (((CassandraStandardControl) sqlControl).getTracing())
                    st.enableTracing();
                else
                    st.disableTracing();
            }
            if (((CassandraStandardControl) sqlControl).getRetryPolicy() != null)
                st.setRetryPolicy(((CassandraStandardControl) sqlControl).getRetryPolicy());
            if (((CassandraStandardControl) sqlControl).getIdempotent() != null)
                st.setIdempotent(((CassandraStandardControl) sqlControl).getIdempotent());
            if (((CassandraStandardControl) sqlControl).getOutgoingPayload() != null)
                st.setOutgoingPayload(((CassandraStandardControl) sqlControl).getOutgoingPayload());
        }
    }

    private BatchStatement getBatchStatement() {
        return sqlControl != null && sqlControl instanceof CassandraStandardControl
                ? ((CassandraStandardControl) sqlControl).getBatchStatement() : null;
    }

    private PreparedStatement getPreparedStatement(String query) {
        return sqlControl != null && sqlControl instanceof CassandraStandardControl
                && ((CassandraStandardControl) sqlControl).getPreparedStatements() != null
                        ? ((CassandraStandardControl) sqlControl).getPreparedStatements().get(query) : null;
    }

    private PreparedStatement setPreparedStatement(String query, PreparedStatement ps) {
        return sqlControl != null && sqlControl instanceof CassandraStandardControl
                && ((CassandraStandardControl) sqlControl).getPreparedStatements() != null
                        ? ((CassandraStandardControl) sqlControl).getPreparedStatements().put(query, ps) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlQuery setOrdered(boolean ordered) {
        this.ordered = ordered;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List list(final SqlRuntimeContext runtimeCtx) throws SqlProcessorException {
        final String query = queryString + getMaxResults();
        if (logger.isDebugEnabled()) {
            logger.debug("list, query=" + query);
        }

        PreparedStatement ps = getPreparedStatement(query);
        BoundStatement bs = null;
        ResultSet rs = null;
        try {
            if (ps == null) {
                ps = session.prepare(query);
                setPreparedStatement(query, ps);
            }
            controlStatement(ps, sqlControl);
            bs = ps.bind();
            controlStatement(bs, sqlControl);
            setParameters(bs);
            rs = session.execute(bs);
            List list = getResults(rs);
            if (logger.isDebugEnabled()) {
                logger.debug("list, number of returned rows=" + ((list != null) ? list.size() : "null"));
            }
            return list;
        } catch (Exception ex) {
            throw newSqlProcessorException(ex, query);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object unique(final SqlRuntimeContext runtimeCtx) throws SqlProcessorException {
        List list = list(runtimeCtx);
        int size = list.size();
        if (size == 0)
            return null;
        Object first = list.get(0);
        for (int i = 1; i < size; i++) {
            if (list.get(i) != first) {
                throw new SqlProcessorException(
                        "There's no unique result, the number of returned rows is " + list.size());
            }
        }
        return first;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int query(final SqlRuntimeContext runtimeCtx, SqlQueryRowProcessor sqlQueryRowProcessor)
            throws SqlProcessorException {
        final String query = queryString + getMaxResults();
        if (logger.isDebugEnabled()) {
            logger.debug("list, query=" + query);
        }

        PreparedStatement ps = getPreparedStatement(query);
        BoundStatement bs = null;
        ResultSet rs = null;
        int rownum = 0;
        try {
            if (ps == null) {
                ps = session.prepare(query);
                setPreparedStatement(query, ps);
            }
            controlStatement(ps, sqlControl);
            bs = ps.bind();
            controlStatement(bs, sqlControl);
            setParameters(bs);
            rs = session.execute(bs);
            for (Object oo = getOneResult(rs); oo != NO_MORE_DATA; oo = getOneResult(rs)) {
                ++rownum;
                if (!sqlQueryRowProcessor.processRow(oo, rownum))
                    break;
            }
            if (logger.isDebugEnabled()) {
                logger.debug("list, number of returned rows=" + rownum);
            }
            return rownum;
        } catch (Exception ex) {
            throw newSqlProcessorException(ex, query);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int update(final SqlRuntimeContext runtimeCtx) throws SqlProcessorException {
        if (logger.isDebugEnabled()) {
            logger.debug("update, query=" + queryString);
        }
        PreparedStatement ps = getPreparedStatement(queryString);
        BoundStatement bs = null;
        BatchStatement batch = getBatchStatement();
        ResultSet rs = null;
        try {
            if (ps == null) {
                ps = session.prepare(queryString);
                setPreparedStatement(queryString, ps);
            }
            controlStatement(ps, sqlControl);
            bs = ps.bind();
            controlStatement(bs, sqlControl);
            setParameters(bs);
            int updated;
            if (batch != null) {
                batch.add(bs);
                updated = 0;
            } else {
                rs = session.execute(bs);
                updated = rs.wasApplied() ? 1 : 0;
            }
            if (logger.isDebugEnabled()) {
                logger.debug("update, number of updated rows=" + updated);
            }
            return updated;
        } catch (Exception ex) {
            throw newSqlProcessorException(ex, queryString);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List callList(final SqlRuntimeContext runtimeCtx) throws SqlProcessorException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object callUnique(final SqlRuntimeContext runtimeCtx) throws SqlProcessorException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int callUpdate(final SqlRuntimeContext runtimeCtx) throws SqlProcessorException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object callFunction() throws SqlProcessorException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlQuery addScalar(String columnAlias) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlQuery addScalar(String columnAlias, Object type, Class<?>... moreTypes) {
        // TODO, right now just a workaround
        if (type == null || !(type instanceof CassandraSqlType))
            throw new IllegalArgumentException();
        scalars.add(columnAlias);
        scalarTypes.put(columnAlias, (CassandraSqlType) type);
        scalarMoreTypes.put(columnAlias, (moreTypes != null) ? moreTypes : new Class<?>[0]);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlQuery setParameter(String name, Object val) throws SqlProcessorException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlQuery setParameter(String name, Object val, Object type, Class<?>... moreTypes)
            throws SqlProcessorException {
        // TODO, right now just a workaround
        if (type == null || !(type instanceof CassandraSqlType))
            throw new IllegalArgumentException();
        parameters.add(name);
        parameterValues.put(name, val);
        parameterTypes.put(name, (CassandraSqlType) type);
        parameterMoreTypes.put(name, (moreTypes != null) ? moreTypes : new Class<?>[0]);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlQuery setParameterList(String name, Object[] vals) throws SqlProcessorException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlQuery setParameterList(String name, Object[] vals, Object type, Class<?>... moreTypes)
            throws SqlProcessorException {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the value of the designated parameters.
     * 
     * @param bs
     *            an instance of BoundStatement
     * @param limitType
     *            the limit type to restrict the number of rows in the result set
     * @param start
     *            the index of the first parameter to bind to prepared statement
     */
    protected void setParameters(BoundStatement bs) throws SqlProcessorException {
        for (int i = 0, n = parameters.size(); i < n; i++) {
            String name = parameters.get(i);
            CassandraSqlType type = parameterTypes.get(name);
            if (parameterValues.containsKey(name)) {
                Object value = parameterValues.get(name);
                if (type != null) {
                    try {
                        if (value == null) {
                            if (logger.isTraceEnabled()) {
                                logger.trace("setNull, name=" + name + ", type=" + type);
                            }
                            bs.setToNull(i);
                        } else {
                            if (logger.isTraceEnabled()) {
                                logger.trace("setParameter, name=" + name + ", value=" + value);
                            }
                            type.set(bs, i, value, parameterMoreTypes.get(name));
                        }
                    } catch (ClassCastException cce) {
                        throw new SqlProcessorException("Not compatible input value of type " + type + " for " + name,
                                cce);
                    }
                } else {
                    throw new SqlProcessorException("Not supported input value of null type for " + name);
                }
            }
        }
    }

    /**
     * Gets the value of the designated columns as the objects in the Java programming language.
     * 
     * @param rs
     *            an instance of ResultSet
     * @return the result list
     */
    protected List getResults(ResultSet rs) {
        List result = new ArrayList();
        if (rs == null)
            return result;
        for (Object oo = getOneResult(rs); oo != NO_MORE_DATA; oo = getOneResult(rs))
            result.add(oo);
        return result;
    }

    /**
     * Gets the value of the designated columns for one database row as the object in the Java programming language.
     * 
     * @param rs
     *            an instance of ResultSet
     * @return the result object for one row
     */
    protected Object getOneResult(ResultSet rs) {
        if (rs == null)
            return NO_MORE_DATA;
        if (!rs.isExhausted()) {
            Row data = rs.one();
            List<Object> row = new ArrayList<Object>();
            for (int i = 0, n = scalars.size(); i < n; i++) {
                String name = scalars.get(i);
                CassandraSqlType type = scalarTypes.get(name);
                Object value = null;
                if (type != null)
                    value = ((CassandraSqlType) type).get(data, "" + i, scalarMoreTypes.get(name));
                else
                    throw new SqlProcessorException("Not supported output value of null type for " + name);
                row.add(value);
            }
            Object[] oo = row.toArray();
            if (oo.length == 1)
                return oo[0];
            else
                return oo;
        }
        return NO_MORE_DATA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] executeBatch(String[] statements) throws SqlProcessorException {
        BatchStatement batch = new BatchStatement();
        for (String query : statements)
            batch.add(new SimpleStatement(query));
        controlStatement(batch, sqlControl);
        ResultSet rs = null;
        try {
            rs = session.execute(batch);
            int updated = rs.wasApplied() ? 1 : 0;
            if (logger.isDebugEnabled()) {
                logger.debug("update, number of updated rows=" + updated);
            }
            return new int[] { updated };
        } catch (Exception ex) {
            throw newSqlProcessorException(ex, "batch");
        }
    }

    public int executeBatch(BatchStatement batch, SqlControl sqlControl) throws SqlProcessorException {
        controlStatement(batch, sqlControl);
        ResultSet rs = null;
        try {
            rs = session.execute(batch);
            int updated = rs.wasApplied() ? 1 : 0;
            if (logger.isDebugEnabled()) {
                logger.debug("update, number of updated rows=" + updated);
            }
            return updated;
        } catch (Exception ex) {
            throw newSqlProcessorException(ex, "batch");
        }
    }

    protected SqlProcessorException newSqlProcessorException(Exception ex, String query) {
        if (logError) {
            logger.error("Failed SQL command '" + query + "': " + ex.getMessage());
            return new SqlProcessorException(ex);
        } else {
            return new SqlProcessorException(ex, query);
        }
    }

    /**
     * Sets an indicator the failed SQL command should be logged
     * 
     * @param logError
     *            an indicator the failed SQL command should be logged
     */
    public void setLogError(boolean logError) {
        this.logError = logError;
    }
}
