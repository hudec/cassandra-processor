package org.sqlproc.engine.cassandra.type;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type ENUMSTRING.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraEnumStringType extends CassandraDefaultType implements CassandraSqlType {

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
            return row.getString(Integer.parseInt(columnLabel));
        else
            return row.getString(columnLabel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BoundStatement st, int index, Object value, Class<?>... moreTypes) {
        st.setString(index, (String) value);
    }
}
