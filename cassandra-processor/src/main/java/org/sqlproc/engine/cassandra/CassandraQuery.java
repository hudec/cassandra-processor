package org.sqlproc.engine.cassandra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlproc.engine.SqlFeature;
import org.sqlproc.engine.SqlProcessorException;
import org.sqlproc.engine.SqlQuery;
import org.sqlproc.engine.SqlRuntimeContext;
import org.sqlproc.engine.cassandra.type.CassandraSqlType;
import org.sqlproc.engine.plugin.SqlFromToPlugin;
import org.sqlproc.engine.type.IdentitySetter;
import org.sqlproc.engine.type.OutValueSetter;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

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
     * The collection of all parameters types for output values.
     */
    Map<String, CassandraSqlType> parameterOutValueTypes = new HashMap<String, CassandraSqlType>();
    /**
     * The collection of all parameters output value setters.
     */
    Map<String, OutValueSetter> parameterOutValueSetters = new HashMap<String, OutValueSetter>();
    /**
     * The collection of all parameters, which have to be picked-up.
     */
    Map<Integer, Integer> parameterOutValuesToPickup = new LinkedHashMap<Integer, Integer>();
    /**
     * The collection of all (auto-generated) identities.
     */
    List<String> identities = new ArrayList<String>();
    /**
     * The collection of all identities setters.
     */
    Map<String, IdentitySetter> identitySetters = new HashMap<String, IdentitySetter>();
    /**
     * The collection of all identities types.
     */
    Map<String, CassandraSqlType> identityTypes = new HashMap<String, CassandraSqlType>();
    /**
     * A timeout for the underlying query.
     */
    Integer timeout;
    /**
     * The first row to retrieve.
     */
    Integer firstResult;
    /**
     * The maximum number of rows to retrieve.
     */
    Integer maxResults;
    /**
     * The fetch size of rows to retrieve in one SQL.
     */
    Integer fetchSize;
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
    public SqlQuery setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlQuery setFirstResult(int firstResult) {
        this.firstResult = firstResult;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlQuery setMaxResults(int maxResults) {
        this.maxResults = maxResults;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlQuery setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
        return this;
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
        StringBuilder queryResult = (maxResults != null) ? new StringBuilder(queryString.length() + 100) : null;
        final SqlFromToPlugin.LimitType limitType = (maxResults != null) ? runtimeCtx.getPluginFactory()
                .getSqlFromToPlugin().limitQuery(runtimeCtx, queryString, queryResult, firstResult, maxResults, ordered)
                : null;
        final String query = limitType != null ? queryResult.toString() : queryString;
        if (logger.isDebugEnabled()) {
            logger.debug("list, query=" + query);
        }

        PreparedStatement ps = null;
        // TODO BatchStatement
        BoundStatement bs = null;
        ResultSet rs = null;
        try {
            ps = session.prepare(query);
            // TODO setRoutingKey, set bs
            // TODO setConsistencyLevel, also bs
            // TODO setSerialConsistencyLevel, also bs
            // TODO enableTracing, disableTracing, also bs
            // TODO setRetryPolicy, also bs
            // TODO setIdempotent, also bs
            // TODO setOutgoingPayload, also bs
            bs = ps.bind();
            // TODO setPagingState
            if (timeout != null)
                bs.setReadTimeoutMillis(timeout);
            if (fetchSize != null)
                bs.setFetchSize(fetchSize);
            setParameters(bs, limitType, 1);
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
        StringBuilder queryResult = (maxResults != null) ? new StringBuilder(queryString.length() + 100) : null;
        final SqlFromToPlugin.LimitType limitType = (maxResults != null) ? runtimeCtx.getPluginFactory()
                .getSqlFromToPlugin().limitQuery(runtimeCtx, queryString, queryResult, firstResult, maxResults, ordered)
                : null;
        final String query = limitType != null ? queryResult.toString() : queryString;
        if (logger.isDebugEnabled()) {
            logger.debug("list, query=" + query);
        }

        PreparedStatement ps = null;
        // TODO BatchStatement
        BoundStatement bs = null;
        ResultSet rs = null;
        int rownum = 0;
        try {
            ps = session.prepare(query);
            // TODO setRoutingKey, set bs
            // TODO setConsistencyLevel, also bs
            // TODO setSerialConsistencyLevel, also bs
            // TODO enableTracing, disableTracing, also bs
            // TODO setRetryPolicy, also bs
            // TODO setIdempotent, also bs
            // TODO setOutgoingPayload, also bs
            bs = ps.bind();
            // TODO setPagingState
            if (timeout != null)
                bs.setReadTimeoutMillis(timeout);
            if (fetchSize != null)
                bs.setFetchSize(fetchSize);
            setParameters(bs, limitType, 1);
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
        // if (logger.isDebugEnabled()) {
        // logger.debug("update, query=" + queryString);
        // }
        // PreparedStatement ps = null;
        // try {
        // final boolean retrieveIdentityFromStatement = isSetJDBCIdentity();
        //
        // if (retrieveIdentityFromStatement) {
        // ps = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);
        // } else {
        // ps = connection.prepareStatement(queryString);
        // }
        // if (timeout != null)
        // ps.setQueryTimeout(timeout);
        // setParameters(ps, null, 1);
        // int updated = ps.executeUpdate();
        // if (logger.isDebugEnabled()) {
        // logger.debug("update, number of updated rows=" + updated);
        // }
        // if (!identities.isEmpty()) {
        // String identityName = identities.get(0);
        // if (retrieveIdentityFromStatement) {
        // getGeneratedKeys(identityName, ps);
        // } else {
        // doIdentitySelect(identityName);
        // }
        // }
        // return updated;
        // } catch (SQLException ex) {
        // throw newSqlProcessorException(ex, queryString);
        // } finally {
        // if (ps != null) {
        // try {
        // ps.close();
        // } catch (SQLException ignore) {
        // }
        // }
        // }
        return 0;
    }

    private boolean isSetJDBCIdentity() {
        for (String identityName : identities) {
            IdentitySetter identitySetter = identitySetters.get(identityName);
            if (identitySetter.getIdentitySelect().equals(SqlFeature.JDBC.name())) {
                return true;
            }
        }
        return false;
    }

    static final Pattern CALL = Pattern.compile("\\s*\\{?\\s*(\\?)?\\s*=?\\s*call\\s*(.*?)\\s*}?\\s*");

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
        List list = callList(runtimeCtx);
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
        scalars.add(columnAlias);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlQuery addScalar(String columnAlias, Object type) {
        // TODO, right now just a workaround
        if (type == null || !(type instanceof CassandraSqlType))
            throw new IllegalArgumentException();
        scalars.add(columnAlias);
        scalarTypes.put(columnAlias, (CassandraSqlType) type);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlQuery setParameter(String name, Object val) throws SqlProcessorException {
        parameters.add(name);
        parameterValues.put(name, val);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlQuery setParameter(String name, Object val, Object type) throws SqlProcessorException {
        // TODO, right now just a workaround
        if (type == null || !(type instanceof CassandraSqlType))
            throw new IllegalArgumentException();
        if (val != null && val instanceof IdentitySetter) {
            identities.add(name);
            identitySetters.put(name, (IdentitySetter) val);
            identityTypes.put(name, (CassandraSqlType) type);
        } else if (val != null && val instanceof OutValueSetter) {
            if (!parameterTypes.containsKey(name)) {
                parameters.add(name);
                parameterTypes.put(name, (CassandraSqlType) type);
            }
            parameterOutValueTypes.put(name, (CassandraSqlType) type);
            parameterOutValueSetters.put(name, (OutValueSetter) val);
        } else {
            parameters.add(name);
            parameterValues.put(name, val);
            parameterTypes.put(name, (CassandraSqlType) type);
        }
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
    public SqlQuery setParameterList(String name, Object[] vals, Object type) throws SqlProcessorException {
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
    protected void setParameters(BoundStatement bs, SqlFromToPlugin.LimitType limitType, int start)
            throws SqlProcessorException {
        int ix = start;
        ix = setLimits(bs, limitType, ix, false);
        for (int i = 0, n = parameters.size(); i < n; i++) {
            String name = parameters.get(i);
            CassandraSqlType type = parameterTypes.get(name);
            if (parameterValues.containsKey(name)) {
                Object value = parameterValues.get(name);
                if (type != null) {
                    try {
                        if (value == null) {
                            if (logger.isTraceEnabled()) {
                                logger.trace("setNull, ix=" + (ix + i) + ", type=" + type);
                            }
                            bs.setToNull(name);
                        } else {
                            if (logger.isTraceEnabled()) {
                                logger.trace("setParameters, ix=" + (ix + i) + ", value=" + value);
                            }
                            type.set(bs, name, value);
                        }
                    } catch (ClassCastException cce) {
                        StringBuilder sb = new StringBuilder("Not compatible input value of type ")
                                .append((value != null) ? value.getClass() : "null");
                        sb.append(". The JDBC type for ").append(name).append(" is ")
                                .append((type != null) ? type.getClass() : "null");
                        sb.append(".");
                        throw new SqlProcessorException(sb.toString(), cce);
                    }
                } else {
                    if (logger.isTraceEnabled()) {
                        logger.trace("setObject, ix=" + (ix + i) + ", type=" + type);
                    }
                    // TODO
                    StringBuilder sb = new StringBuilder("Not supported input value of type ")
                            .append((value != null) ? value.getClass() : "null").append(".");
                    throw new SqlProcessorException(sb.toString());
                }
            }
            if (parameterOutValueSetters.containsKey(name)) {
                // CallableStatement cs = (CallableStatement) bs;
                // if (type != null) {
                // TODO
                // if (type instanceof SqlMetaType) {
                // cs.registerOutParameter(ix + i, (Integer) ((SqlMetaType) type).getDatabaseSqlType());
                // } else {
                // cs.registerOutParameter(ix + i, (Integer) type);
                // }
                // } else {
                // throw new SqlProcessorException("OUT parameter type for callable statement is null");
                // }
                // parameterOutValuesToPickup.put(i, ix + i);
            }
        }
        ix = setLimits(bs, limitType, ix + parameters.size(), true);
    }

    /**
     * Sets the limit related parameters.
     * 
     * @param bs
     *            an instance of BoundStatement
     * @param limitType
     *            the limit type to restrict the number of rows in the result set
     * @param ix
     *            a column index
     * @param afterSql
     *            an indicator it's done after the main SQL statement execution
     * @return the updated column index
     */
    protected int setLimits(BoundStatement bs, SqlFromToPlugin.LimitType limitType, int ix, boolean afterSql) {
        if (limitType == null)
            return ix;
        if (afterSql && !limitType.afterSql)
            return ix;
        if (!afterSql && limitType.afterSql)
            return ix;
        if (limitType.maxBeforeFirst) {
            if (limitType.rowidBasedMax && limitType.alsoFirst)
                bs.setInt(ix++, firstResult + maxResults);
            else
                bs.setInt(ix++, maxResults);
        }
        if (limitType.alsoFirst) {
            if (limitType.zeroBasedFirst)
                bs.setInt(ix++, firstResult);
            else
                bs.setInt(ix++, firstResult);
        }
        if (!limitType.maxBeforeFirst) {
            if (limitType.rowidBasedMax && limitType.alsoFirst)
                bs.setInt(ix++, firstResult + maxResults);
            else
                bs.setInt(ix++, maxResults);
        }
        return ix;
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
                Object type = scalarTypes.get(name);
                Object value = null;
                if (type != null && type instanceof CassandraSqlType) {
                    value = ((CassandraSqlType) type).get(data, name);
                } else {
                    // TODO
                    StringBuilder sb = new StringBuilder("Not supported output value of type " + type).append(".");
                    throw new SqlProcessorException(sb.toString());
                }
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
        throw new UnsupportedOperationException();
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
