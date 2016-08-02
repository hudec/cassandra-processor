package org.sqlproc.engine.cassandra.type;

import java.sql.SQLException;
import java.sql.Types;

import org.sqlproc.engine.type.SqlStringType;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type STRING.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraStringType extends SqlStringType implements CassandraSqlType {

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
        return Types.VARCHAR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(Row row, String columnLabel) throws SQLException {
        if (Character.isDigit(columnLabel.charAt(0)))
            return row.getString(Integer.parseInt(columnLabel));
        else
            return row.getString(columnLabel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BoundStatement st, int index, Object value) throws SQLException {
        st.setString(index, (String) value);
    }
}
