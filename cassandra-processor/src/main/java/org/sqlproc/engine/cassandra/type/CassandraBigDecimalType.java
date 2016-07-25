package org.sqlproc.engine.cassandra.type;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;

import org.sqlproc.engine.type.SqlBigDecimalType;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type BIGDECIMAL.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraBigDecimalType extends SqlBigDecimalType implements CassandraSqlType {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getProviderSqlType() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getProviderSqlNullType() {
        return Types.NUMERIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(Row row, String columnLabel) throws SQLException {
        if (Character.isDigit(columnLabel.charAt(0)))
            return row.getDecimal(Integer.parseInt(columnLabel));
        else
            return row.getDecimal(columnLabel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BoundStatement st, int index, Object value) throws SQLException {
        st.setDecimal(index, (BigDecimal) value);
    }
}
