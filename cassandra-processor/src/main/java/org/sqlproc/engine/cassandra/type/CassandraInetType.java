package org.sqlproc.engine.cassandra.type;

import java.net.InetAddress;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type LONG.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraInetType extends CassandraDefaultType implements CassandraSqlType {

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
            return row.getInet(Integer.parseInt(columnLabel));
        } else {
            return row.getInet(columnLabel);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BoundStatement st, String columnLabel, Object value, Class<?>... moreTypes) {
        st.setInet(columnLabel, (InetAddress) value);
    }
}
