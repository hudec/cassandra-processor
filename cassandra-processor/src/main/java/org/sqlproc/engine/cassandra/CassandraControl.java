package org.sqlproc.engine.cassandra;

import java.nio.ByteBuffer;
import java.util.Map;

import org.sqlproc.engine.SqlControl;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.policies.RetryPolicy;

/**
 * The compound parameters controlling the META SQL execution.
 * 
 * <p>
 * For more info please see the <a href="https://github.com/hudec/sql-processor/wiki">Tutorials</a>.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public interface CassandraControl extends SqlControl {

    /**
     * The consistency level for this query.
     *
     * @return the consistency level for this query, or {@code null} if no consistency level has been specified (through
     *         {@code setConsistencyLevel}). In the latter case, the default consistency level will be used.
     */
    public ConsistencyLevel getConsistencyLevel();

    /**
     * The serial consistency level for this query.
     *
     * @return the serial consistency level for this query, or {@code null} if no serial consistency level has been
     *         specified (through
     *         {@link com.datastax.driver.core.Statement#setSerialConsistencyLevel(ConsistencyLevel)}). In the latter
     *         case, the default serial consistency level will be used.
     */
    public ConsistencyLevel getSerialConsistencyLevel();

    /**
     * Returns whether tracing is enabled for this prepared statement, i.e. if BoundStatement created from it will use
     * tracing by default.
     *
     * @return {@code true} if this prepared statement has tracing enabled, {@code false} otherwise.
     */
    public Boolean getTracing();

    /**
     * Returns the retry policy sets for this query, if any.
     *
     * @return the retry policy sets specifically for this query or {@code null} if no query specific retry policy has
     *         been set through {@link com.datastax.driver.core.Statement#setRetryPolicy} (in which case the Cluster
     *         retry policy will apply if necessary).
     */
    public RetryPolicy getRetryPolicy();

    /**
     * Whether this statement is idempotent, i.e. whether it can be applied multiple times without changing the result
     * beyond the initial application.
     * <p/>
     * Idempotence plays a role in {@link com.datastax.driver.core.policies.SpeculativeExecutionPolicy speculative
     * executions}. If a statement is <em>not idempotent</em>, the driver will not schedule speculative executions for
     * it.
     * <p/>
     * Note that this method can return {@code null}, in which case the driver will default to
     * {@link com.datastax.driver.core.QueryOptions#getDefaultIdempotence()}.
     * <p/>
     * By default, this method returns {@code null} for all statements, except for
     * {@link com.datastax.driver.core.BuiltStatement}s, where the value will be inferred from the query: if it updates
     * counters, prepends/appends to a list, or uses a function call or
     * {@link com.datastax.driver.core.querybuilder.QueryBuilder#raw(String)} anywhere in an inserted value, the result
     * will be {@code false}; otherwise it will be {@code true}. In all cases, calling
     * {@link com.datastax.driver.core.Statement#setIdempotent(boolean)} forces a value that overrides every other
     * mechanism.
     * <p/>
     * Note that when a statement is prepared ({@link com.datastax.driver.core.PreparedStatement#prepare(String)}), its
     * idempotence flag will be propagated to all {@link com.datastax.driver.core.PreparedStatement}s created from it.
     *
     * @return whether this statement is idempotent, or {@code null} to use
     *         {@link com.datastax.driver.core.QueryOptions#getDefaultIdempotence()}.
     */
    public Boolean getIdempotent();

    /**
     * Return the incoming payload, that is, the payload that the server sent back with its {@code PREPARED} response,
     * if any, or {@code null}, if the server did not include any custom payload.
     * <p/>
     * Note that if an incoming payload is present, and if no outgoing payload has been
     * {@link com.datastax.driver.core.PreparedStatement#setOutgoingPayload(Map) explicitly set}, then each time a
     * {@link com.datastax.driver.core.BoundStatement} is created (with either {@link #bind()} or
     * {@link #bind(Object...)}), the resulting {@link com.datastax.driver.core.BoundStatement} will inherit from this
     * value as its default outgoing payload.
     * <p/>
     * Implementors should return a read-only view of the original map, but even though, its values would remain
     * inherently mutable. Callers should take care not to modify the returned map in any way.
     * <p/>
     * This feature is only available with {@link com.datastax.driver.core.ProtocolVersion#V4} or above; with lower
     * versions, this method will always return {@code null}.
     *
     * @return the custom payload that the server sent back with its response, if any, or {@code null}, if the server
     *         did not include any custom payload.
     * @since 2.2
     */
    public Map<String, ByteBuffer> getIncomingPayload();

    /**
     * Returns this statement's outgoing payload. Each time this statement is executed, this payload will be included in
     * the query request.
     * <p/>
     * This method returns {@code null} if no payload has been set, otherwise it always returns immutable maps.
     * <p/>
     * This feature is only available with {@link com.datastax.driver.core.ProtocolVersion#V4} or above. Trying to
     * include custom payloads in requests sent by the driver under lower protocol versions will result in an
     * {@link com.datastax.driver.core.exceptions.UnsupportedFeatureException} (wrapped in a
     * {@link com.datastax.driver.core.exceptions.NoHostAvailableException}).
     *
     * @return the outgoing payload to include with this statement, or {@code null} if no payload has been set.
     * @since 2.2
     */
    public Map<String, ByteBuffer> getOutgoingPayload();
}
