package org.sqlproc.engine.cassandra.type;

import java.time.LocalDate;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type DATE.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraDateType extends CassandraDefaultType implements CassandraSqlType {

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
            return row.get(Integer.parseInt(columnLabel), LocalDate.class);
        else
            return row.get(columnLabel, LocalDate.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BoundStatement st, int index, Object value, Class<?>... moreTypes) {
        // if (value instanceof java.sql.Date) {
        // st.setDate(index, LocalDate.fromMillisSinceEpoch(((java.sql.Date) value).getTime()));
        // } else if (value instanceof Date) {
        // st.setDate(index, LocalDate.fromMillisSinceEpoch(((java.util.Date) value).getTime()));
        // } else {
        // st.setDate(index, (LocalDate) value);
        // }
        st.set(index, (LocalDate) value, LocalDate.class);
    }
}
