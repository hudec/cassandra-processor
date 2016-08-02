package org.sqlproc.engine.cassandra.type;

import java.sql.SQLException;
import java.sql.Types;

import org.sqlproc.engine.type.SqlDoubleType;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type DOUBLE.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraDoubleType extends SqlDoubleType implements CassandraSqlType {

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
        return Types.DOUBLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(Row row, String columnLabel) throws SQLException {
        if (Character.isDigit(columnLabel.charAt(0)))
            return new Double(row.getDouble(Integer.parseInt(columnLabel)));
        else
            return new Double(row.getDouble(columnLabel));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BoundStatement st, int index, Object value) throws SQLException {
        st.setDouble(index, ((Double) value).doubleValue());
    }
}
