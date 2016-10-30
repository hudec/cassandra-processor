package org.sqlproc.engine.cassandra.type;

import java.util.UUID;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type LONG.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraUUIDType extends CassandraDefaultType implements CassandraSqlType {

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
        if (Character.isDigit(columnLabel.charAt(0))) {
            return row.getUUID(Integer.parseInt(columnLabel));
        } else {
            return row.getUUID(columnLabel);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BoundStatement st, int index, Object value, Class<?>... moreTypes) {
        st.setUUID(index, (UUID) value);
    }
}
