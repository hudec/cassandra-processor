package org.sqlproc.engine.cassandra.type;

import java.sql.SQLException;
import java.sql.Types;

import org.sqlproc.engine.type.SqlByteType;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type BYTE.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraByteType extends SqlByteType implements CassandraSqlType {

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
    public Object getDatabaseSqlType() {
        return Types.TINYINT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(Row row, String columnLabel) throws SQLException {
        if (Character.isDigit(columnLabel.charAt(0)))
            return new Byte(row.getByte(Integer.parseInt(columnLabel)));
        else
            return new Byte(row.getByte(columnLabel));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BoundStatement st, int index, Object value) throws SQLException {
        st.setByte(index, ((Byte) value).byteValue());
    }
}
