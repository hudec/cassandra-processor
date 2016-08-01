package org.sqlproc.engine.cassandra.type;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalTime;
import java.util.Date;

import org.sqlproc.engine.type.SqlTimeType;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type TIME.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraTimeType extends SqlTimeType implements CassandraSqlType {

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
        return Types.TIME;
    }

    @Override
    public Object get(Row row, String columnLabel) throws SQLException {
        if (Character.isDigit(columnLabel.charAt(0)))
            return new Time(row.getTime(Integer.parseInt(columnLabel)));
        else {
            LocalTime lt = row.get(columnLabel, LocalTime.class);
            return new Time(row.getTime(columnLabel));
        }
    }

    @Override
    public void set(BoundStatement st, int index, Object value) throws SQLException {
        Long time;
        if (value instanceof Time) {
            time = ((Time) value).getTime();
        } else if (value instanceof java.sql.Date) {
            time = ((java.sql.Date) value).getTime();
        } else {
            time = ((Date) value).getTime();
        }
        st.setTime(index, time);
    }
}
