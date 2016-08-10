package org.sqlproc.engine.cassandra.type;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type TIMESTAMP.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraMapType extends CassandraDefaultType implements CassandraSqlType {

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
        if (moreTypes == null || moreTypes.length < 2)
            throw new IllegalArgumentException("Missing generic type for Cassandra map");
        if (Character.isDigit(columnLabel.charAt(0)))
            return row.getMap(Integer.parseInt(columnLabel), moreTypes[1], moreTypes[2]);
        else
            return row.getMap(columnLabel, moreTypes[1], moreTypes[2]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BoundStatement st, String columnLabel, Object value, Class<?>... moreTypes) {
        if (moreTypes == null || moreTypes.length < 2)
            throw new IllegalArgumentException("Missing generic type for Cassandra map");
        st.setMap(columnLabel, (java.util.Map) value, moreTypes[1], moreTypes[2]);
    }
}
