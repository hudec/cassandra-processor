package org.sqlproc.engine.cassandra.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlproc.engine.SqlQuery;
import org.sqlproc.engine.SqlRuntimeContext;
import org.sqlproc.engine.SqlRuntimeException;
import org.sqlproc.engine.type.SqlMetaType;
import org.sqlproc.engine.type.SqlTypeFactory;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The default META type for the JDBC stack. It's used in the case there's no explicit META type declaration in the META
 * SQL statements.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraDefaultType implements SqlMetaType {

    /**
     * The internal slf4j logger.
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getProviderSqlType() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void addScalar(SqlTypeFactory typeFactory, SqlQuery query, String dbName, Class<?>... attributeTypes) {
        if (getProviderSqlType() != null) {
            query.addScalar(dbName, getProviderSqlType());
        } else {
            Object type = (attributeTypes.length > 0) ? typeFactory.getMetaType(attributeTypes[0]) : null;
            if (type != null)
                query.addScalar(dbName, type, attributeTypes);
            else
                query.addScalar(dbName, new CassandraClassType(attributeTypes));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setResult(SqlRuntimeContext runtimeCtx, Object resultInstance, String attributeName, Object resultValue,
            boolean ingoreError) throws SqlRuntimeException {
        if (logger.isTraceEnabled()) {
            logger.trace(">>> setResult for META type " + this + ": resultInstance=" + resultInstance
                    + ", attributeName=" + attributeName + ", resultValue=" + resultValue + ", resultType"
                    + ((resultValue != null) ? resultValue.getClass() : null));
        }

        Class<?> attributeType = runtimeCtx.getAttributeType(resultInstance.getClass(), attributeName);
        if (attributeType == null) {
            error(ingoreError, "There's problem with attribute type for '" + attributeName + "' in " + resultInstance
                    + ", META type is " + this);
            return;
        }

        if (attributeType.isEnum()) {
            Object enumInstance = runtimeCtx.getValueToEnum(attributeType, resultValue);
            if (runtimeCtx.simpleSetAttribute(resultInstance, attributeName, enumInstance, attributeType))
                return;
            else {
                error(ingoreError, "There's no getter for '" + attributeName + "' in " + resultInstance
                        + ", META type is " + this);
                return;
            }
        } else {
            if (runtimeCtx.simpleSetAttribute(resultInstance, attributeName, resultValue, attributeType))
                return;
            else {
                error(ingoreError, "There's no getter for '" + attributeName + "' in " + resultInstance
                        + ", META type is " + this);
                return;
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
            logger.trace(">>> setParameter for META type " + this + ": paramName=" + paramName + ", inputValue="
                    + inputValue + ", inputType=" + inputType);
        }

        if (getProviderSqlType() != null) {
            if (inputValue == null) {
                query.setParameter(paramName, inputValue, getProviderSqlType());
            } else if (inputValue instanceof Collection) {
                query.setParameterList(paramName, ((Collection) inputValue).toArray(), getProviderSqlType());
            } else {
                query.setParameter(paramName, inputValue, getProviderSqlType());
            }
            return;
        }

        if (!(inputValue instanceof Collection)) {
            if (inputType.isEnum()) {
                Class<?> clazz = runtimeCtx.getEnumToClass(inputType);
                if (clazz == String.class) {
                    runtimeCtx.getTypeFactory().getEnumStringType().setParameter(runtimeCtx, query, paramName,
                            inputValue, inputType, ingoreError);
                } else if (clazz == Integer.class) {
                    runtimeCtx.getTypeFactory().getEnumIntegerType().setParameter(runtimeCtx, query, paramName,
                            inputValue, inputType, ingoreError);
                } else {
                    error(ingoreError, "Incorrect class enum type for " + paramName + ": " + clazz);
                }
            } else {
                SqlMetaType type = runtimeCtx.getTypeFactory().getMetaType(inputType);
                if (type != null) {
                    type.setParameter(runtimeCtx, query, paramName, inputValue, inputType, ingoreError);
                } else {
                    query.setParameter(paramName, inputValue, new CassandraClassType(inputType));
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
                    error(ingoreError, "Null enum value for " + paramName + ": " + vals);
                    return;
                }
            }
            if (isEnum) {
                query.setParameter(paramName, vals, new CassandraClassType(inputType));
            } else {
                query.setParameter(paramName, inputValue, new CassandraClassType(inputType));
            }
        }
    }

    protected void error(boolean ingoreError, String msg) {
        if (ingoreError) {
            logger.error(msg);
        } else {
            throw new SqlRuntimeException(msg);
        }
    }

    public static class CassandraClassType<V> implements CassandraSqlType {

        Class<V>[] inputTypes;

        public CassandraClassType(Class<V>... inputTypes) {
            super();
            this.inputTypes = inputTypes;
        }

        @Override
        public Object get(Row row, String columnLabel, Class<?>... moreTypes) {
            return row.get(columnLabel, inputTypes[0]);
        }

        @Override
        public void set(BoundStatement st, String columnLabel, Object value, Class<?>... moreTypes) {
            st.set(columnLabel, (V) value, inputTypes[0]);
        }
    }
}
