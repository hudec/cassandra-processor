package org.sqlproc.engine.cassandra.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlproc.engine.SqlQuery;
import org.sqlproc.engine.SqlRuntimeContext;
import org.sqlproc.engine.SqlRuntimeException;
import org.sqlproc.engine.type.SqlMetaType;
import org.sqlproc.engine.type.SqlTaggedMetaType;
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
            query.addScalar(dbName, getProviderSqlType(), attributeTypes);
        } else {
            Object type = (attributeTypes.length > 0) ? typeFactory.getMetaType(attributeTypes[0]) : null;
            if (type != null) {
                if (type instanceof SqlTaggedMetaType)
                    ((SqlTaggedMetaType) type).addScalar(typeFactory, query, dbName, attributeTypes);
                else
                    query.addScalar(dbName, type, attributeTypes);
            } else {
                query.addScalar(dbName, new CassandraClassType(attributeTypes));
            }
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

        Object type = runtimeCtx.getTypeFactory().getMetaType(attributeType);
        if (type != null && type instanceof SqlTaggedMetaType) {
            ((SqlTaggedMetaType) type).setResult(runtimeCtx, resultInstance, attributeName, resultValue, ingoreError);
            return;
        }

        if (resultValue != null && attributeType.isEnum()) {
            Object enumInstance = null;
            try {
                enumInstance = runtimeCtx.getValueToEnum(attributeType, resultValue);
            } catch (SqlRuntimeException e) {
                // workaround - int the case of int - 0 = null
                if (!(resultValue instanceof Integer) || ((Integer) resultValue).intValue() != 0)
                    throw e;
            }
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
            boolean ingoreError, Class<?>... inputTypes) throws SqlRuntimeException {
        if (logger.isTraceEnabled()) {
            logger.trace(">>> setParameter for META type " + this + ": paramName=" + paramName + ", inputValue="
                    + inputValue + ", inputTypes=" + inputTypes);
        }

        if (inputValue != null && inputTypes[0].isEnum())
            inputValue = runtimeCtx.getEnumToValue(inputValue);

        if (getProviderSqlType() != null) {
            query.setParameter(paramName, inputValue, getProviderSqlType(), inputTypes);
        } else {
            Object type = (inputTypes.length > 0) ? runtimeCtx.getTypeFactory().getMetaType(inputTypes[0]) : null;
            if (type != null) {
                if (type instanceof SqlTaggedMetaType)
                    ((SqlTaggedMetaType) type).setParameter(runtimeCtx, query, paramName, inputValue, ingoreError,
                            inputTypes);
                else
                    query.setParameter(paramName, inputValue, type, inputTypes);
            } else {
                query.setParameter(paramName, inputValue, new CassandraClassType(inputTypes));
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
        public void set(BoundStatement st, int index, Object value, Class<?>... moreTypes) {
            st.set(index, (V) value, inputTypes[0]);
        }
    }
}
