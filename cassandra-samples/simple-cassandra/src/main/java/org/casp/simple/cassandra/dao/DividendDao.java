package org.casp.simple.cassandra.dao;

import org.casp.simple.cassandra.model.Dividend;
import org.slf4j.Logger;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.cassandra.CassandraControl;
import org.sqlproc.engine.cassandra.CassandraEngineFactory;
import org.sqlproc.engine.cassandra.CassandraSessionFactory;

public class DividendDao {

    protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    public DividendDao() {
    }

    public DividendDao(final CassandraEngineFactory casEngineFactory) {
        this.casEngineFactory = casEngineFactory;
    }

    public DividendDao(final CassandraEngineFactory casEngineFactory, final CassandraSessionFactory casSessionFactory) {
        this.casEngineFactory = casEngineFactory;
        this.casSessionFactory = casSessionFactory;
    }

    protected CassandraEngineFactory casEngineFactory;

    protected CassandraSessionFactory casSessionFactory;

    public Dividend insert(final SqlSession casSession, final Dividend dividend, CassandraControl casControl) {
        if (logger.isTraceEnabled()) {
            logger.trace("sql insert contact: " + dividend + " " + casControl);
        }
        org.sqlproc.engine.SqlCrudEngine sqlInsertDividend = casEngineFactory.getCheckedCrudEngine("INSERT_DIVIDEND");
        int count = sqlInsertDividend.insert(casSession, dividend, casControl);
        if (logger.isTraceEnabled()) {
            logger.trace("sql insert contact result: " + count + " " + dividend);
        }
        return (count > 0) ? dividend : null;
    }

}
