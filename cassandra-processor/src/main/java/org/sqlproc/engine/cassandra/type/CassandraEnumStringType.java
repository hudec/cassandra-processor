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
    public Object get(Row row, String columnLabel) {
        if (Character.isDigit(columnLabel.charAt(0)))
            return row.getString(Integer.parseInt(columnLabel));
        else
            return row.getString(columnLabel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BoundStatement st, String columnLabel, Object value) {
        st.setString(columnLabel, (String) value);
    }
}
