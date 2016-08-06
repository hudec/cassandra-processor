package org.sqlproc.engine.cassandra.type;

import java.nio.ByteBuffer;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type BYTEARRAY.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraByteArrayType extends CassandraDefaultType implements CassandraSqlType {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getProviderSqlType() {
        return this;
    }

    @Override
    public Object get(Row row, String columnLabel) {
        if (Character.isDigit(columnLabel.charAt(0)))
            return row.getBytes(Integer.parseInt(columnLabel));
        else
            return row.getBytes(columnLabel);
    }

    @Override
    public void set(BoundStatement st, String columnLabel, Object value) {
        if (value instanceof byte[]) {
            ByteBuffer bf = ByteBuffer.wrap((byte[]) value);
            st.setBytes(columnLabel, bf);
        } else if (value instanceof Byte[]) {
            byte[] b = new byte[((Byte[]) value).length];
            for (int i = 0; i < b.length; i++)
                b[i] = ((Byte[]) value)[i].byteValue();
            ByteBuffer bf = ByteBuffer.wrap(b);
            st.setBytes(columnLabel, bf);
        } else {
            st.setBytes(columnLabel, (ByteBuffer) value);
        }
    }
}
