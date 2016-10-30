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
    public Object get(Row row, String columnLabel, Class<?>... moreTypes) {
        if (Character.isDigit(columnLabel.charAt(0)))
            return new Byte(row.getByte(Integer.parseInt(columnLabel)));
        else
            return new Byte(row.getByte(columnLabel));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BoundStatement st, int index, Object value, Class<?>... moreTypes) {
        st.setByte(index, ((Byte) value).byteValue());
    }
}
