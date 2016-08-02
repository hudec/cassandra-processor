package org.sqlproc.engine.cassandra.type;

import java.sql.SQLException;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type BOOLEAN.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraBooleanType extends CassandraDefaultType implements CassandraSqlType {

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
    public Object get(Row row, String columnLabel) throws SQLException {
        if (Character.isDigit(columnLabel.charAt(0)))
            return row.getBool(Integer.parseInt(columnLabel)) ? Boolean.TRUE : Boolean.FALSE;
        else
            return row.getBool(columnLabel) ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BoundStatement st, int index, Object value) throws SQLException {
        st.setBool(index, ((Boolean) value).booleanValue());
    }
}
