package org.sqlproc.engine.cassandra.type;

import java.sql.SQLException;
import java.sql.Types;

import org.sqlproc.engine.type.SqlShortType;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type SHORT.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraShortType extends SqlShortType implements CassandraSqlType {

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
        return Types.SMALLINT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(Row row, String columnLabel) throws SQLException {
        if (Character.isDigit(columnLabel.charAt(0)))
            return new Short(row.getShort(Integer.parseInt(columnLabel)));
        else
            return new Short(row.getShort(columnLabel));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BoundStatement st, int index, Object value) throws SQLException {
        st.setShort(index, ((Short) value).shortValue());
    }
}
