package org.casp.simple.cassandra.dao;

import java.util.List;
import org.casp.simple.cassandra.model.Dividend;
import org.slf4j.Logger;
import org.sqlproc.engine.SqlControl;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlRowProcessor;
import org.sqlproc.engine.SqlSession;
import org.sqlproc.engine.SqlSessionFactory;

@SuppressWarnings("all")
public class DividendDao {
  protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
  
  public DividendDao() {
  }
  
  public DividendDao(final SqlEngineFactory sqlEngineFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
  }
  
  public DividendDao(final SqlEngineFactory sqlEngineFactory, final SqlSessionFactory sqlSessionFactory) {
    this.sqlEngineFactory = sqlEngineFactory;
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  protected SqlEngineFactory sqlEngineFactory;
  
  protected SqlSessionFactory sqlSessionFactory;
  
  public Dividend insert(final SqlSession sqlSession, final Dividend dividend, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert dividend: " + dividend + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlInsertDividend = sqlEngineFactory.getCheckedCrudEngine("INSERT_DIVIDEND");
    int count = sqlInsertDividend.insert(sqlSession, dividend, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql insert dividend result: " + count + " " + dividend);
    }
    return (count > 0) ? dividend : null;
  }
  
  public Dividend insert(final Dividend dividend, SqlControl sqlControl) {
    return insert(sqlSessionFactory.getSqlSession(), dividend, sqlControl);
  }
  
  public Dividend insert(final SqlSession sqlSession, final Dividend dividend) {
    return insert(sqlSession, dividend, null);
  }
  
  public Dividend insert(final Dividend dividend) {
    return insert(dividend, null);
  }
  
  public Dividend get(final SqlSession sqlSession, final Dividend dividend, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get: " + dividend + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlGetEngineDividend = sqlEngineFactory.getCheckedCrudEngine("GET_DIVIDEND");
    //sqlControl = getMoreResultClasses(dividend, sqlControl);
    Dividend dividendGot = sqlGetEngineDividend.get(sqlSession, Dividend.class, dividend, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql get dividend result: " + dividendGot);
    }
    return dividendGot;
  }
  
  public Dividend get(final Dividend dividend, SqlControl sqlControl) {
    return get(sqlSessionFactory.getSqlSession(), dividend, sqlControl);
  }
  
  public Dividend get(final SqlSession sqlSession, final Dividend dividend) {
    return get(sqlSession, dividend, null);
  }
  
  public Dividend get(final Dividend dividend) {
    return get(dividend, null);
  }
  
  public int update(final SqlSession sqlSession, final Dividend dividend, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update dividend: " + dividend + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlUpdateEngineDividend = sqlEngineFactory.getCheckedCrudEngine("UPDATE_DIVIDEND");
    int count = sqlUpdateEngineDividend.update(sqlSession, dividend, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql update dividend result count: " + count);
    }
    return count;
  }
  
  public int update(final Dividend dividend, SqlControl sqlControl) {
    return update(sqlSessionFactory.getSqlSession(), dividend, sqlControl);
  }
  
  public int update(final SqlSession sqlSession, final Dividend dividend) {
    return update(sqlSession, dividend, null);
  }
  
  public int update(final Dividend dividend) {
    return update(dividend, null);
  }
  
  public int delete(final SqlSession sqlSession, final Dividend dividend, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete dividend: " + dividend + " " + sqlControl);
    }
    org.sqlproc.engine.SqlCrudEngine sqlDeleteEngineDividend = sqlEngineFactory.getCheckedCrudEngine("DELETE_DIVIDEND");
    int count = sqlDeleteEngineDividend.delete(sqlSession, dividend, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql delete dividend result count: " + count);
    }
    return count;
  }
  
  public int delete(final Dividend dividend, SqlControl sqlControl) {
    return delete(sqlSessionFactory.getSqlSession(), dividend, sqlControl);
  }
  
  public int delete(final SqlSession sqlSession, final Dividend dividend) {
    return delete(sqlSession, dividend, null);
  }
  
  public int delete(final Dividend dividend) {
    return delete(dividend, null);
  }
  
  public List<Dividend> list(final SqlSession sqlSession, final Dividend dividend, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list dividend: " + dividend + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineDividend = sqlEngineFactory.getCheckedQueryEngine("SELECT_DIVIDEND");
    //sqlControl = getMoreResultClasses(dividend, sqlControl);
    List<Dividend> dividendList = sqlEngineDividend.query(sqlSession, Dividend.class, dividend, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql list dividend size: " + ((dividendList != null) ? dividendList.size() : "null"));
    }
    return dividendList;
  }
  
  public List<Dividend> list(final Dividend dividend, SqlControl sqlControl) {
    return list(sqlSessionFactory.getSqlSession(), dividend, sqlControl);
  }
  
  public List<Dividend> list(final SqlSession sqlSession, final Dividend dividend) {
    return list(sqlSession, dividend, null);
  }
  
  public List<Dividend> list(final Dividend dividend) {
    return list(dividend, null);
  }
  
  public int query(final SqlSession sqlSession, final Dividend dividend, SqlControl sqlControl, final SqlRowProcessor<Dividend> sqlRowProcessor) {
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query dividend: " + dividend + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineDividend = sqlEngineFactory.getCheckedQueryEngine("SELECT_DIVIDEND");
    //sqlControl = getMoreResultClasses(dividend, sqlControl);
    int rownums = sqlEngineDividend.query(sqlSession, Dividend.class, dividend, sqlControl, sqlRowProcessor);
    if (logger.isTraceEnabled()) {
    	logger.trace("sql query dividend size: " + rownums);
    }
    return rownums;
  }
  
  public int query(final Dividend dividend, SqlControl sqlControl, final SqlRowProcessor<Dividend> sqlRowProcessor) {
    return query(sqlSessionFactory.getSqlSession(), dividend, sqlControl, sqlRowProcessor);
  }
  
  public int query(final SqlSession sqlSession, final Dividend dividend, final SqlRowProcessor<Dividend> sqlRowProcessor) {
    return query(sqlSession, dividend, null, sqlRowProcessor);
  }
  
  public int query(final Dividend dividend, final SqlRowProcessor<Dividend> sqlRowProcessor) {
    return query(dividend, null, sqlRowProcessor);
  }
  
  public int count(final SqlSession sqlSession, final Dividend dividend, SqlControl sqlControl) {
    if (logger.isTraceEnabled()) {
    	logger.trace("count dividend: " + dividend + " " + sqlControl);
    }
    org.sqlproc.engine.SqlQueryEngine sqlEngineDividend = sqlEngineFactory.getCheckedQueryEngine("SELECT_DIVIDEND");
    //sqlControl = getMoreResultClasses(dividend, sqlControl);
    int count = sqlEngineDividend.queryCount(sqlSession, dividend, sqlControl);
    if (logger.isTraceEnabled()) {
    	logger.trace("count: " + count);
    }
    return count;
  }
  
  public int count(final Dividend dividend, SqlControl sqlControl) {
    return count(sqlSessionFactory.getSqlSession(), dividend, sqlControl);
  }
  
  public int count(final SqlSession sqlSession, final Dividend dividend) {
    return count(sqlSession, dividend, null);
  }
  
  public int count(final Dividend dividend) {
    return count(dividend, null);
  }
}
