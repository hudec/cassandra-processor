package org.sqlproc.engine.cassandra.type;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.TupleValue;

/**
 * The JDBC META type CHARACTER.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraTupleType extends CassandraDefaultType implements CassandraSqlType {

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
            return row.getTupleValue(Integer.parseInt(columnLabel));
        else
            return row.getTupleValue(columnLabel);
    }

    @Override
    public void set(BoundStatement st, String columnLabel, Object value, Class<?>... moreTypes) {
        st.setTupleValue(columnLabel, (TupleValue) value);
    }
}
