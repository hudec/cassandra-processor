package org.casp.simple.cassandra.dao;

import org.casp.simple.cassandra.model.ExchangeMetadata;
import org.slf4j.Logger;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.cassandra.CassandraControl;
import org.sqlproc.engine.cassandra.CassandraEngineFactory;
import org.sqlproc.engine.cassandra.CassandraSessionFactory;

public class ExchangeMetadataDao {

    protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    public ExchangeMetadataDao() {
    }

    public ExchangeMetadataDao(final CassandraEngineFactory casEngineFactory) {
        this.casEngineFactory = casEngineFactory;
    }

    public ExchangeMetadataDao(final CassandraEngineFactory casEngineFactory,
            final CassandraSessionFactory casSessionFactory) {
        this.casEngineFactory = casEngineFactory;
        this.casSessionFactory = casSessionFactory;
    }

    protected CassandraEngineFactory casEngineFactory;

    protected CassandraSessionFactory casSessionFactory;

    public ExchangeMetadata insert(final SqlSession casSession, final ExchangeMetadata exchangeMetadata,
            CassandraControl casControl) {
        if (logger.isTraceEnabled()) {
            logger.trace("sql insert contact: " + exchangeMetadata + " " + casControl);
        }
        org.sqlproc.engine.SqlCrudEngine sqlInsertExchangeMetadata = casEngineFactory
                .getCheckedCrudEngine("INSERT_EXCHANGE_METADATA");
        int count = sqlInsertExchangeMetadata.insert(casSession, exchangeMetadata, casControl);
        if (logger.isTraceEnabled()) {
            logger.trace("sql insert contact result: " + count + " " + exchangeMetadata);
        }
        return (count > 0) ? exchangeMetadata : null;
    }

}
