package org.sqlproc.engine.cassandra.type;

import java.time.LocalTime;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type TIME.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraTimeType extends CassandraDefaultType implements CassandraSqlType {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getProviderSqlType() {
        return this;
    }

    @Override
    public Object get(Row row, String columnLabel, Class<?>... moreTypes) {
        if (Character.isDigit(columnLabel.charAt(0)))
            return row.get(Integer.parseInt(columnLabel), LocalTime.class);
        else {
            LocalTime lt = row.get(columnLabel, LocalTime.class);
            return row.get(columnLabel, LocalTime.class);
        }
    }

    @Override
    public void set(BoundStatement st, String columnLabel, Object value, Class<?>... moreTypes) {
        // Long time;
        // if (value instanceof Time) {
        // time = ((Time) value).getTime();
        // } else if (value instanceof java.sql.Date) {
        // time = ((java.sql.Date) value).getTime();
        // } else {
        // time = ((Date) value).getTime();
        // }
        // st.setTime(index, time);
        st.set(columnLabel, (LocalTime) value, LocalTime.class);
    }
}
