package org.sqlproc.engine.cassandra.type;

import java.time.Instant;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type TIMESTAMP.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraTimestampType extends CassandraDefaultType implements CassandraSqlType {

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
    public Object get(Row row, String columnLabel, Class<?>... moreTypes) {
        if (Character.isDigit(columnLabel.charAt(0)))
            return row.get(Integer.parseInt(columnLabel), Instant.class);
        else
            return row.get(columnLabel, Instant.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BoundStatement st, int index, Object value, Class<?>... moreTypes) {
        // Date date;
        // if (value instanceof Timestamp) {
        // date = new Date(((Timestamp) value).getTime());
        // } else if (value instanceof java.sql.Date) {
        // date = new Date(((java.sql.Date) value).getTime());
        // } else {
        // date = (Date) value;
        // }
        // st.setTimestamp(index, date);
        st.set(index, (Instant) value, Instant.class);
    }
}
