package org.sqlproc.engine.cassandra;

import java.nio.ByteBuffer;
import java.util.Map;

import org.sqlproc.engine.impl.SqlStandardControl;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.policies.RetryPolicy;

public class CassandraStandardControl extends SqlStandardControl implements CassandraControl {

    private ConsistencyLevel consistencyLevel;
    private ConsistencyLevel serialConsistencyLevel;
    private Boolean tracing;
    private RetryPolicy retryPolicy;
    private Boolean idempotent;
    private Map<String, ByteBuffer> incomingPayload;
    private Map<String, ByteBuffer> outgoingPayload;
    private BatchStatement batchStatement;

    /**
     * {@inheritDoc}
     */
    @Override
    public ConsistencyLevel getConsistencyLevel() {
        return consistencyLevel;
    }

    public CassandraStandardControl setConsistencyLevel(ConsistencyLevel consistencyLevel) {
        this.consistencyLevel = consistencyLevel;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConsistencyLevel getSerialConsistencyLevel() {
        return serialConsistencyLevel;
    }

    public CassandraStandardControl setSerialConsistencyLevel(ConsistencyLevel serialConsistencyLevel) {
        this.serialConsistencyLevel = serialConsistencyLevel;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getTracing() {
        return tracing;
    }

    public CassandraStandardControl setTracing(Boolean tracing) {
        this.tracing = tracing;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public CassandraStandardControl setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getIdempotent() {
        return idempotent;
    }

    public CassandraStandardControl setIdempotent(Boolean idempotent) {
        this.idempotent = idempotent;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, ByteBuffer> getIncomingPayload() {
        return incomingPayload;
    }

    public CassandraStandardControl setIncomingPayload(Map<String, ByteBuffer> incomingPayload) {
        this.incomingPayload = incomingPayload;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, ByteBuffer> getOutgoingPayload() {
        return outgoingPayload;
    }

    public CassandraStandardControl setOutgoingPayload(Map<String, ByteBuffer> outgoingPayload) {
        this.outgoingPayload = outgoingPayload;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BatchStatement getBatchStatement() {
        return batchStatement;
    }

    public void setBatchStatement(BatchStatement batchStatement) {
        this.batchStatement = batchStatement;
    }

    @Override
    public String toString() {
        return "CassandraStandardControl [consistencyLevel=" + consistencyLevel + ", serialConsistencyLevel="
                + serialConsistencyLevel + ", tracing=" + tracing + ", retryPolicy=" + retryPolicy + ", idempotent="
                + idempotent + ", incomingPayload=" + incomingPayload + ", outgoingPayload=" + outgoingPayload
                + ", batchStatement=" + batchStatement + ", super=" + super.toString() + "]";
    }
}
