package org.sqlproc.engine.cassandra.type;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type TIMESTAMP.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraListType extends CassandraDefaultType implements CassandraSqlType {

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
        if (moreTypes == null || moreTypes.length < 1)
            throw new IllegalArgumentException("Missing generic type for Cassandra list");
        if (Character.isDigit(columnLabel.charAt(0)))
            return row.getList(Integer.parseInt(columnLabel), moreTypes[1]);
        else
            return row.getList(columnLabel, moreTypes[1]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BoundStatement st, int index, Object value, Class<?>... moreTypes) {
        if (moreTypes == null || moreTypes.length < 1)
            throw new IllegalArgumentException("Missing generic type for Cassandra list");
        st.setList(index, (java.util.List) value, moreTypes[1]);
    }
}
