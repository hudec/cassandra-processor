package org.sqlproc.engine.cassandra.type;

import java.sql.SQLException;
import java.sql.Types;

import org.sqlproc.engine.type.SqlEnumIntegerType;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type ENUMINTEGER.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraEnumIntegerType extends SqlEnumIntegerType implements CassandraSqlType {

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
        return Types.INTEGER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(Row row, String columnLabel) throws SQLException {
        if (Character.isDigit(columnLabel.charAt(0)))
            return new Integer(row.getInt(Integer.parseInt(columnLabel)));
        else
            return new Integer(row.getInt(columnLabel));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BoundStatement st, int index, Object value) throws SQLException {
        st.setInt(index, ((Integer) value).intValue());
    }
}
