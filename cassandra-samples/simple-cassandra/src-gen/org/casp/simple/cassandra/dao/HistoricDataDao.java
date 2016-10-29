package org.casp.simple.cassandra.dao;

import java.util.List;
import org.casp.simple.cassandra.model.HistoricData;
import org.slf4j.Logger;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlSessionFactory;

@SuppressWarnings("all")
public class HistoricDataDao {
  protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
  
  public HistoricDataDao() {
  }
  
  public HistoricDataDao(final SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
  
  public HistoricDataDao(final SqlEngineFactory sqlEngineFactory, final SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  protected SqlEngineFactory sqlEngineFactory;
  
  protected SqlSessionFactory sqlSessionFactory;
  
  public HistoricData insert(final SqlSession sqlSession, final HistoricData historicData, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert historicData: " + historicData + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlInsertHistoricData = sqlEngineFactory.getCheckedCrudEngine("INSERT_HISTORIC_DATA");
    int count = sqlInsertHistoricData.insert(sqlSession, historicData, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert historicData result: " + count + " " + historicData);
    }
    return (count > 0) ? historicData : null;
  }
  
  public HistoricData insert(final HistoricData historicData, SqlControl sqlControl) {
    return insert(sqlSessionFactory.getSqlSession(), historicData, sqlControl);
  }
  
  public HistoricData insert(final SqlSession sqlSession, final HistoricData historicData) {
    return insert(sqlSession, historicData, null);
  }
  
  public HistoricData insert(final HistoricData historicData) {
    return insert(historicData, null);
  }
  
  public HistoricData get(final SqlSession sqlSession, final HistoricData historicData, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get: " + historicData + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlGetEngineHistoricData = sqlEngineFactory.getCheckedCrudEngine("GET_HISTORIC_DATA");
    //sqlControl = getMoreResultClasses(historicData, sqlControl);
    HistoricData historicDataGot = sqlGetEngineHistoricData.get(sqlSession, HistoricData.class, historicData, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get historicData result: " + historicDataGot);
    }
    return historicDataGot;
  }
  
  public HistoricData get(final HistoricData historicData, SqlControl sqlControl) {
    return get(sqlSessionFactory.getSqlSession(), historicData, sqlControl);
  }
  
  public HistoricData get(final SqlSession sqlSession, final HistoricData historicData) {
    return get(sqlSession, historicData, null);
  }
  
  public HistoricData get(final HistoricData historicData) {
    return get(historicData, null);
  }
  
  public int update(final SqlSession sqlSession, final HistoricData historicData, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update historicData: " + historicData + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlUpdateEngineHistoricData = sqlEngineFactory.getCheckedCrudEngine("UPDATE_HISTORIC_DATA");
    int count = sqlUpdateEngineHistoricData.update(sqlSession, historicData, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update historicData result count: " + count);
    }
    return count;
  }
  
  public int update(final HistoricData historicData, SqlControl sqlControl) {
    return update(sqlSessionFactory.getSqlSession(), historicData, sqlControl);
  }
  
  public int update(final SqlSession sqlSession, final HistoricData historicData) {
    return update(sqlSession, historicData, null);
  }
  
  public int update(final HistoricData historicData) {
    return update(historicData, null);
  }
  
  public int delete(final SqlSession sqlSession, final HistoricData historicData, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete historicData: " + historicData + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlDeleteEngineHistoricData = sqlEngineFactory.getCheckedCrudEngine("DELETE_HISTORIC_DATA");
    int count = sqlDeleteEngineHistoricData.delete(sqlSession, historicData, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete historicData result count: " + count);
    }
    return count;
  }
  
  public int delete(final HistoricData historicData, SqlControl sqlControl) {
    return delete(sqlSessionFactory.getSqlSession(), historicData, sqlControl);
  }
  
  public int delete(final SqlSession sqlSession, final HistoricData historicData) {
    return delete(sqlSession, historicData, null);
  }
  
  public int delete(final HistoricData historicData) {
    return delete(historicData, null);
  }
  
  public List<HistoricData> list(final SqlSession sqlSession, final HistoricData historicData, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list historicData: " + historicData + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineHistoricData = sqlEngineFactory.getCheckedQueryEngine("SELECT_HISTORIC_DATA");
    //sqlControl = getMoreResultClasses(historicData, sqlControl);
    List<HistoricData> historicDataList = sqlEngineHistoricData.query(sqlSession, HistoricData.class, historicData, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list historicData size: " + ((historicDataList != null) ? historicDataList.size() : "null"));
    }
    return historicDataList;
  }
  
  public List<HistoricData> list(final HistoricData historicData, SqlControl sqlControl) {
    return list(sqlSessionFactory.getSqlSession(), historicData, sqlControl);
  }
  
  public List<HistoricData> list(final SqlSession sqlSession, final HistoricData historicData) {
    return list(sqlSession, historicData, null);
  }
  
  public List<HistoricData> list(final HistoricData historicData) {
    return list(historicData, null);
  }
  
  public int query(final SqlSession sqlSession, final HistoricData historicData, SqlControl sqlControl, final SqlRowProcessor<HistoricData> sqlRowProcessor) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query historicData: " + historicData + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineHistoricData = sqlEngineFactory.getCheckedQueryEngine("SELECT_HISTORIC_DATA");
    //sqlControl = getMoreResultClasses(historicData, sqlControl);
    int rownums = sqlEngineHistoricData.query(sqlSession, HistoricData.class, historicData, sqlControl, sqlRowProcessor);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query historicData size: " + rownums);
    }
    return rownums;
  }
  
  public int query(final HistoricData historicData, SqlControl sqlControl, final SqlRowProcessor<HistoricData> sqlRowProcessor) {
    return query(sqlSessionFactory.getSqlSession(), historicData, sqlControl, sqlRowProcessor);
  }
  
  public int query(final SqlSession sqlSession, final HistoricData historicData, final SqlRowProcessor<HistoricData> sqlRowProcessor) {
    return query(sqlSession, historicData, null, sqlRowProcessor);
  }
  
  public int query(final HistoricData historicData, final SqlRowProcessor<HistoricData> sqlRowProcessor) {
    return query(historicData, null, sqlRowProcessor);
  }
  
  public int count(final SqlSession sqlSession, final HistoricData historicData, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("count historicData: " + historicData + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineHistoricData = sqlEngineFactory.getCheckedQueryEngine("SELECT_HISTORIC_DATA");
    //sqlControl = getMoreResultClasses(historicData, sqlControl);
    int count = sqlEngineHistoricData.queryCount(sqlSession, historicData, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("count: " + count);
    }
    return count;
  }
  
  public int count(final HistoricData historicData, SqlControl sqlControl) {
    return count(sqlSessionFactory.getSqlSession(), historicData, sqlControl);
  }
  
  public int count(final SqlSession sqlSession, final HistoricData historicData) {
    return count(sqlSession, historicData, null);
  }
  
  public int count(final HistoricData historicData) {
    return count(historicData, null);
  }
}
