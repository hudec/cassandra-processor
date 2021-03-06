package org.sqlproc.engine.cassandra.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlproc.engine.SqlQuery;
import org.sqlproc.engine.SqlRuntimeContext;
import org.sqlproc.engine.SqlRuntimeException;
import org.sqlproc.engine.cassandra.model.PhoneNumber;
import org.sqlproc.engine.cassandra.type.CassandraDefaultType.CassandraClassType;
import org.sqlproc.engine.type.SqlTaggedMetaType;
import org.sqlproc.engine.type.SqlTypeFactory;

public class PhoneNumberType implements SqlTaggedMetaType {

    /**
     * The internal slf4j logger.
     */
    final Logger logger = LoggerFactory.getLogger(getClass());

    static Pattern pattern = Pattern.compile("^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$");

    @Override
    public Class<?>[] getClassTypes() {
        return new Class[] { PhoneNumber.class };
    }

    @Override
    public String[] getMetaTypes() {
        return new String[] { "phone" };
    }

    @Override
    public Object getProviderSqlType() {
        return this;
    }

    public static final CassandraClassType CASSANDRA_TYPE = new CassandraClassType(String.class);

    @Override
    public void addScalar(SqlTypeFactory typeFactory, SqlQuery query, String dbName, Class<?>... attributeTypes) {
        query.addScalar(dbName, CASSANDRA_TYPE, attributeTypes);
    }

    @Override
    public void setResult(SqlRuntimeContext runtimeCtx, Object resultInstance, String attributeName, Object resultValue,
            boolean ingoreError) throws SqlRuntimeException {

        if (resultValue == null) {
            if (runtimeCtx.simpleSetAttribute(resultInstance, attributeName, null, PhoneNumber.class))
                return;
            if (ingoreError) {
                logger.error("There's no getter for " + attributeName + " in " + resultInstance
                        + ", META type is PhoneNumberType");
                return;
            } else {
                throw new SqlRuntimeException("There's no setter for " + attributeName + " in " + resultInstance
                        + ", META type is PhoneNumberType");
            }
        }

        if (!(resultValue instanceof String)) {
            if (ingoreError) {
                logger.error("Incorrect result value type " + resultValue + ", it should be a string");
                return;
            } else {
                throw new SqlRuntimeException("Incorrect result value type " + resultValue + ", it should be a string");
            }
        }

        String sPhoneNumber = (String) resultValue;
        Matcher matcher = pattern.matcher(sPhoneNumber);
        if (!matcher.matches()) {
            if (ingoreError) {
                logger.error("Incorrect result phone number format '" + sPhoneNumber + "'");
                return;
            } else {
                throw new SqlRuntimeException("Incorrect result phone number format '" + sPhoneNumber + "'");
            }
        }
        int area = Integer.parseInt(matcher.group(1));
        int exch = Integer.parseInt(matcher.group(2));
        int ext = Integer.parseInt(matcher.group(3));

        if (runtimeCtx.simpleSetAttribute(resultInstance, attributeName, new PhoneNumber(area, exch, ext),
                PhoneNumber.class))
            return;
        if (ingoreError) {
            logger.error("There's no getter for " + attributeName + " in " + resultInstance
                    + ", META type is PhoneNumberType");
            return;
        } else {
            throw new SqlRuntimeException("There's no setter for " + attributeName + " in " + resultInstance
                    + ", META type is PhoneNumberType");
        }
    }

    @Override
    public void setParameter(SqlRuntimeContext runtimeCtx, SqlQuery query, String paramName, Object inputValue,
            boolean ingoreError, Class<?>... inputTypes) throws SqlRuntimeException {

        if (inputValue == null) {
            query.setParameter(paramName, inputValue, CASSANDRA_TYPE, inputTypes);
        } else {
            if (!(inputValue instanceof PhoneNumber)) {
                if (ingoreError) {
                    logger.error("Incorrect input value type " + inputValue + ", it should be a PhoneNumber");
                    return;
                } else {
                    throw new SqlRuntimeException(
                            "Incorrect input value type " + inputValue + ", it should be a PhoneNumber");
                }
            }
            PhoneNumber phoneNumber = (PhoneNumber) inputValue;
            String sPhoneNumber = String.format("%03d-%03d-%04d", phoneNumber.getArea(), phoneNumber.getExch(),
                    phoneNumber.getExt());
            query.setParameter(paramName, sPhoneNumber, CASSANDRA_TYPE, inputTypes);
        }
    }
}
