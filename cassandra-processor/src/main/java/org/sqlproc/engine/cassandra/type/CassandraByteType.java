package org.sqlproc.engine.cassandra.type;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type BYTE.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraByteType extends CassandraDefaultType implements CassandraSqlType {

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
            return new Byte(row.getByte(Integer.parseInt(columnLabel)));
        else
            return new Byte(row.getByte(columnLabel));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BoundStatement st, String columnLabel, Object value) {
        st.setByte(columnLabel, ((Byte) value).byteValue());
    }
}
