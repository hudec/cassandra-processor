package org.casp.simple.cassandra.dao;

import org.casp.simple.cassandra.model.HistoricData;
import org.slf4j.Logger;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.cassandra.CassandraControl;
import org.sqlproc.engine.cassandra.CassandraEngineFactory;
import org.sqlproc.engine.cassandra.CassandraSessionFactory;

public class HistoricDataDao {

    protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    public HistoricDataDao() {
    }

    public HistoricDataDao(final CassandraEngineFactory casEngineFactory) {
        this.casEngineFactory = casEngineFactory;
    }

    public HistoricDataDao(final CassandraEngineFactory casEngineFactory,
            final CassandraSessionFactory casSessionFactory) {
        this.casEngineFactory = casEngineFactory;
        this.casSessionFactory = casSessionFactory;
    }

    protected CassandraEngineFactory casEngineFactory;

    protected CassandraSessionFactory casSessionFactory;

    public HistoricData insert(final SqlSession casSession, final HistoricData historicData, CassandraControl casControl) {
        if (logger.isTraceEnabled()) {
            logger.trace("sql insert contact: " + historicData + " " + casControl);
        }
        org.sqlproc.engine.SqlCrudEngine sqlInsertHistoricData = casEngineFactory
                .getCheckedCrudEngine("INSERT_HISTORIC_DATA");
        int count = sqlInsertHistoricData.insert(casSession, historicData, casControl);
        if (logger.isTraceEnabled()) {
            logger.trace("sql insert contact result: " + count + " " + historicData);
        }
        return (count > 0) ? historicData : null;
    }
}
