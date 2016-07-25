package org.sqlproc.engine.cassandra.type;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.sqlproc.engine.SqlQuery;
import org.sqlproc.engine.SqlRuntimeContext;
import org.sqlproc.engine.SqlRuntimeException;
import org.sqlproc.engine.impl.SqlUtils;
import org.sqlproc.engine.type.SqlMetaType;

/**
 * The default META type for the JDBC stack. It's used in the case there's no explicit META type declaration in the META
 * SQL statements.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraDefaultType extends SqlMetaType {

    /**
     * The map between the Java types and the META types.
     */
    static Map<Class<?>, Object> jdbcTypes = new HashMap<Class<?>, Object>();
    /**
     * Static initialization.
     */
    static {
        jdbcTypes.put(int.class, CassandraTypeFactory.INTEGER);
        jdbcTypes.put(Integer.class, CassandraTypeFactory.INTEGER);
        jdbcTypes.put(long.class, CassandraTypeFactory.LONG);
        jdbcTypes.put(Long.class, CassandraTypeFactory.LONG);
        jdbcTypes.put(short.class, CassandraTypeFactory.SHORT);
        jdbcTypes.put(Short.class, CassandraTypeFactory.SHORT);
        jdbcTypes.put(byte.class, CassandraTypeFactory.BYTE);
        jdbcTypes.put(Byte.class, CassandraTypeFactory.BYTE);
        jdbcTypes.put(float.class, CassandraTypeFactory.FLOAT);
        jdbcTypes.put(Float.class, CassandraTypeFactory.FLOAT);
        jdbcTypes.put(double.class, CassandraTypeFactory.DOUBLE);
        jdbcTypes.put(Double.class, CassandraTypeFactory.DOUBLE);
        jdbcTypes.put(char.class, CassandraTypeFactory.CHAR);
        jdbcTypes.put(Character.class, CassandraTypeFactory.CHAR);
        jdbcTypes.put(String.class, CassandraTypeFactory.STRING);
        jdbcTypes.put(java.util.Date.class, CassandraTypeFactory.TIMESTAMP);
        jdbcTypes.put(java.sql.Timestamp.class, CassandraTypeFactory.TIMESTAMP);
        jdbcTypes.put(java.sql.Date.class, CassandraTypeFactory.DATE);
        jdbcTypes.put(java.sql.Time.class, CassandraTypeFactory.TIME);
        jdbcTypes.put(boolean.class, CassandraTypeFactory.BOOLEAN);
        jdbcTypes.put(Boolean.class, CassandraTypeFactory.BOOLEAN);
        jdbcTypes.put(BigInteger.class, CassandraTypeFactory.BIG_INTEGER);
        jdbcTypes.put(BigDecimal.class, CassandraTypeFactory.BIG_DECIMAL);
        jdbcTypes.put(byte[].class, CassandraTypeFactory.BYTE_ARRAY);
        jdbcTypes.put(Byte[].class, CassandraTypeFactory.BYTE_ARRAY);
    }

    /**
     * {@inheritDoc}
     */
    public void addScalar(SqlQuery query, String dbName, Class<?> attributeType) {
        Object type = jdbcTypes.get(attributeType);
        if (type != null)
            query.addScalar(dbName, type);
        else
            query.addScalar(dbName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setResult(SqlRuntimeContext runtimeCtx, Object resultInstance, String attributeName, Object resultValue,
            boolean ingoreError) throws SqlRuntimeException {
        if (logger.isTraceEnabled()) {
            logger.trace(">>> setResult DEFAULT: resultInstance=" + resultInstance + ", attributeName=" + attributeName
                    + ", resultValue=" + resultValue + ", resultType"
                    + ((resultValue != null) ? resultValue.getClass() : null));
        }
        Class<?> attributeType = runtimeCtx.getAttributeType(resultInstance.getClass(), attributeName);
        if (attributeType == null) {
            if (ingoreError) {
                logger.error("There's problem with attribute type for '" + attributeName + "' in " + resultInstance
                        + ", META type is DEFAULT");
                return;
            } else {
                throw new SqlRuntimeException("There's problem with attribute type for '" + attributeName + "' in "
                        + resultInstance + ", META type is DEFAULT");
            }
        }
        if (attributeType.isEnum()) {
            if (resultValue != null && resultValue instanceof BigDecimal)
                resultValue = (Integer) ((BigDecimal) resultValue).intValue();
            else if (resultValue != null && resultValue instanceof BigInteger)
                resultValue = (Integer) ((BigInteger) resultValue).intValue();
            Object enumInstance = runtimeCtx.getValueToEnum(attributeType, resultValue);

            if (runtimeCtx.simpleSetAttribute(resultInstance, attributeName, enumInstance, attributeType))
                return;
            else if (ingoreError) {
                logger.error("There's no getter for '" + attributeName + "' in " + resultInstance
                        + ", META type is DEFAULT");
            } else {
                throw new SqlRuntimeException("There's no setter for '" + attributeName + "' in " + resultInstance
                        + ", META type is DEFAULT");
            }
        } else {
            if (resultValue != null) {
                if (resultValue instanceof BigDecimal)
                    resultValue = SqlUtils.convertBigDecimal(attributeType, resultValue);
                else if (resultValue instanceof BigInteger)
                    resultValue = SqlUtils.convertBigInteger(attributeType, resultValue);
            }

            if (runtimeCtx.simpleSetAttribute(resultInstance, attributeName, resultValue, attributeType))
                return;
            if (ingoreError) {
                logger.error("There's no getter for '" + attributeName + "' in " + resultInstance
                        + ", META type is DEFAULT");
            } else {
                throw new SqlRuntimeException("There's no setter for '" + attributeName + "' in " + resultInstance
                        + ", META type is DEFAULT");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParameter(SqlRuntimeContext runtimeCtx, SqlQuery query, String paramName, Object inputValue,
            Class<?> inputType, boolean ingoreError) throws SqlRuntimeException {
        if (logger.isTraceEnabled()) {
            logger.trace(">>> setParameter DEFAULT: paramName=" + paramName + ", inputValue=" + inputValue
                    + ", inputType=" + inputType);
        }
        if (!(inputValue instanceof Collection)) {
            if (inputType.isEnum()) {
                Class clazz = runtimeCtx.getEnumToClass(inputType);
                if (clazz == String.class) {
                    CassandraTypeFactory.ENUM_STRING.setParameter(runtimeCtx, query, paramName, inputValue, inputType,
                            ingoreError);
                } else if (clazz == Integer.class) {
                    CassandraTypeFactory.ENUM_INT.setParameter(runtimeCtx, query, paramName, inputValue, inputType,
                            ingoreError);
                } else {
                    if (ingoreError) {
                        logger.error("Incorrect type based enum " + inputValue + " for " + paramName);
                    } else {
                        throw new SqlRuntimeException("Incorrect type based enum " + inputValue + " for " + paramName);
                    }
                }
            } else {
                SqlMetaType type = runtimeCtx.getTypeFactory().getMetaType(inputType);
                if (type != null) {
                    type.setParameter(runtimeCtx, query, paramName, inputValue, inputType, ingoreError);
                } else {
                    if (ingoreError) {
                        logger.error("Incorrect default type " + inputValue + " for " + paramName);
                    } else {
                        throw new SqlRuntimeException("Incorrect default type " + inputValue + " for " + paramName);
                    }
                }
            }
        } else {
            List<Object> vals = new ArrayList<Object>();
            boolean isEnum = false;
            for (Iterator iter = ((Collection) inputValue).iterator(); iter.hasNext();) {
                Object val = iter.next();
                if (!val.getClass().isEnum())
                    break;
                else
                    isEnum = true;
                Object o = runtimeCtx.getEnumToValue(val);
                if (o != null) {
                    vals.add(o);
                } else {
                    if (ingoreError) {
                        logger.error("Incorrect type based enum item value " + o + " for " + paramName);
                    } else {
                        throw new SqlRuntimeException(
                                "Incorrect type based enum item value " + o + " for " + paramName);
                    }
                }
            }
            if (isEnum) {
                query.setParameterList(paramName, vals.toArray());
            } else {
                query.setParameterList(paramName, ((Collection) inputValue).toArray());
            }
        }
    }
}
