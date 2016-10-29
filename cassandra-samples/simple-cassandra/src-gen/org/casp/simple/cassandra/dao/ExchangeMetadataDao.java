package org.casp.simple.cassandra.dao;

import java.util.List;
import org.casp.simple.cassandra.model.ExchangeMetadata;
import org.slf4j.Logger;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlSessionFactory;

@SuppressWarnings("all")
public class ExchangeMetadataDao {
  protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
  
  public ExchangeMetadataDao() {
  }
  
  public ExchangeMetadataDao(final SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
  
  public ExchangeMetadataDao(final SqlEngineFactory sqlEngineFactory, final SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  protected SqlEngineFactory sqlEngineFactory;
  
  protected SqlSessionFactory sqlSessionFactory;
  
  public ExchangeMetadata insert(final SqlSession sqlSession, final ExchangeMetadata exchangeMetadata, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert exchangeMetadata: " + exchangeMetadata + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlInsertExchangeMetadata = sqlEngineFactory.getCheckedCrudEngine("INSERT_EXCHANGE_METADATA");
    int count = sqlInsertExchangeMetadata.insert(sqlSession, exchangeMetadata, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert exchangeMetadata result: " + count + " " + exchangeMetadata);
    }
    return (count > 0) ? exchangeMetadata : null;
  }
  
  public ExchangeMetadata insert(final ExchangeMetadata exchangeMetadata, SqlControl sqlControl) {
    return insert(sqlSessionFactory.getSqlSession(), exchangeMetadata, sqlControl);
  }
  
  public ExchangeMetadata insert(final SqlSession sqlSession, final ExchangeMetadata exchangeMetadata) {
    return insert(sqlSession, exchangeMetadata, null);
  }
  
  public ExchangeMetadata insert(final ExchangeMetadata exchangeMetadata) {
    return insert(exchangeMetadata, null);
  }
  
  public ExchangeMetadata get(final SqlSession sqlSession, final ExchangeMetadata exchangeMetadata, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get: " + exchangeMetadata + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlGetEngineExchangeMetadata = sqlEngineFactory.getCheckedCrudEngine("GET_EXCHANGE_METADATA");
    //sqlControl = getMoreResultClasses(exchangeMetadata, sqlControl);
    ExchangeMetadata exchangeMetadataGot = sqlGetEngineExchangeMetadata.get(sqlSession, ExchangeMetadata.class, exchangeMetadata, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get exchangeMetadata result: " + exchangeMetadataGot);
    }
    return exchangeMetadataGot;
  }
  
  public ExchangeMetadata get(final ExchangeMetadata exchangeMetadata, SqlControl sqlControl) {
    return get(sqlSessionFactory.getSqlSession(), exchangeMetadata, sqlControl);
  }
  
  public ExchangeMetadata get(final SqlSession sqlSession, final ExchangeMetadata exchangeMetadata) {
    return get(sqlSession, exchangeMetadata, null);
  }
  
  public ExchangeMetadata get(final ExchangeMetadata exchangeMetadata) {
    return get(exchangeMetadata, null);
  }
  
  public int update(final SqlSession sqlSession, final ExchangeMetadata exchangeMetadata, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update exchangeMetadata: " + exchangeMetadata + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlUpdateEngineExchangeMetadata = sqlEngineFactory.getCheckedCrudEngine("UPDATE_EXCHANGE_METADATA");
    int count = sqlUpdateEngineExchangeMetadata.update(sqlSession, exchangeMetadata, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update exchangeMetadata result count: " + count);
    }
    return count;
  }
  
  public int update(final ExchangeMetadata exchangeMetadata, SqlControl sqlControl) {
    return update(sqlSessionFactory.getSqlSession(), exchangeMetadata, sqlControl);
  }
  
  public int update(final SqlSession sqlSession, final ExchangeMetadata exchangeMetadata) {
    return update(sqlSession, exchangeMetadata, null);
  }
  
  public int update(final ExchangeMetadata exchangeMetadata) {
    return update(exchangeMetadata, null);
  }
  
  public int delete(final SqlSession sqlSession, final ExchangeMetadata exchangeMetadata, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete exchangeMetadata: " + exchangeMetadata + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlDeleteEngineExchangeMetadata = sqlEngineFactory.getCheckedCrudEngine("DELETE_EXCHANGE_METADATA");
    int count = sqlDeleteEngineExchangeMetadata.delete(sqlSession, exchangeMetadata, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete exchangeMetadata result count: " + count);
    }
    return count;
  }
  
  public int delete(final ExchangeMetadata exchangeMetadata, SqlControl sqlControl) {
    return delete(sqlSessionFactory.getSqlSession(), exchangeMetadata, sqlControl);
  }
  
  public int delete(final SqlSession sqlSession, final ExchangeMetadata exchangeMetadata) {
    return delete(sqlSession, exchangeMetadata, null);
  }
  
  public int delete(final ExchangeMetadata exchangeMetadata) {
    return delete(exchangeMetadata, null);
  }
  
  public List<ExchangeMetadata> list(final SqlSession sqlSession, final ExchangeMetadata exchangeMetadata, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list exchangeMetadata: " + exchangeMetadata + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineExchangeMetadata = sqlEngineFactory.getCheckedQueryEngine("SELECT_EXCHANGE_METADATA");
    //sqlControl = getMoreResultClasses(exchangeMetadata, sqlControl);
    List<ExchangeMetadata> exchangeMetadataList = sqlEngineExchangeMetadata.query(sqlSession, ExchangeMetadata.class, exchangeMetadata, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list exchangeMetadata size: " + ((exchangeMetadataList != null) ? exchangeMetadataList.size() : "null"));
    }
    return exchangeMetadataList;
  }
  
  public List<ExchangeMetadata> list(final ExchangeMetadata exchangeMetadata, SqlControl sqlControl) {
    return list(sqlSessionFactory.getSqlSession(), exchangeMetadata, sqlControl);
  }
  
  public List<ExchangeMetadata> list(final SqlSession sqlSession, final ExchangeMetadata exchangeMetadata) {
    return list(sqlSession, exchangeMetadata, null);
  }
  
  public List<ExchangeMetadata> list(final ExchangeMetadata exchangeMetadata) {
    return list(exchangeMetadata, null);
  }
  
  public int query(final SqlSession sqlSession, final ExchangeMetadata exchangeMetadata, SqlControl sqlControl, final SqlRowProcessor<ExchangeMetadata> sqlRowProcessor) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query exchangeMetadata: " + exchangeMetadata + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineExchangeMetadata = sqlEngineFactory.getCheckedQueryEngine("SELECT_EXCHANGE_METADATA");
    //sqlControl = getMoreResultClasses(exchangeMetadata, sqlControl);
    int rownums = sqlEngineExchangeMetadata.query(sqlSession, ExchangeMetadata.class, exchangeMetadata, sqlControl, sqlRowProcessor);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query exchangeMetadata size: " + rownums);
    }
    return rownums;
  }
  
  public int query(final ExchangeMetadata exchangeMetadata, SqlControl sqlControl, final SqlRowProcessor<ExchangeMetadata> sqlRowProcessor) {
    return query(sqlSessionFactory.getSqlSession(), exchangeMetadata, sqlControl, sqlRowProcessor);
  }
  
  public int query(final SqlSession sqlSession, final ExchangeMetadata exchangeMetadata, final SqlRowProcessor<ExchangeMetadata> sqlRowProcessor) {
    return query(sqlSession, exchangeMetadata, null, sqlRowProcessor);
  }
  
  public int query(final ExchangeMetadata exchangeMetadata, final SqlRowProcessor<ExchangeMetadata> sqlRowProcessor) {
    return query(exchangeMetadata, null, sqlRowProcessor);
  }
  
  public int count(final SqlSession sqlSession, final ExchangeMetadata exchangeMetadata, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("count exchangeMetadata: " + exchangeMetadata + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineExchangeMetadata = sqlEngineFactory.getCheckedQueryEngine("SELECT_EXCHANGE_METADATA");
    //sqlControl = getMoreResultClasses(exchangeMetadata, sqlControl);
    int count = sqlEngineExchangeMetadata.queryCount(sqlSession, exchangeMetadata, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("count: " + count);
    }
    return count;
  }
  
  public int count(final ExchangeMetadata exchangeMetadata, SqlControl sqlControl) {
    return count(sqlSessionFactory.getSqlSession(), exchangeMetadata, sqlControl);
  }
  
  public int count(final SqlSession sqlSession, final ExchangeMetadata exchangeMetadata) {
    return count(sqlSession, exchangeMetadata, null);
  }
  
  public int count(final ExchangeMetadata exchangeMetadata) {
    return count(exchangeMetadata, null);
  }
}
