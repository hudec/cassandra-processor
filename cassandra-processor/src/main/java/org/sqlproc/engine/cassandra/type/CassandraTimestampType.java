package org.sqlproc.engine.cassandra.type;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import org.sqlproc.engine.type.SqlTimestampType;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type TIMESTAMP.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraTimestampType extends SqlTimestampType implements CassandraSqlType {

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
        return Types.TIMESTAMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(Row row, String columnLabel) throws SQLException {
        if (Character.isDigit(columnLabel.charAt(0)))
            return row.getTimestamp(Integer.parseInt(columnLabel));
        else
            return row.getTimestamp(columnLabel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BoundStatement st, int index, Object value) throws SQLException {
        Date date;
        if (value instanceof Timestamp) {
            date = new Date(((Timestamp) value).getTime());
        } else if (value instanceof java.sql.Date) {
            date = new Date(((java.sql.Date) value).getTime());
        } else {
            date = (Date) value;
        }
        st.setTimestamp(index, date);
    }
}
