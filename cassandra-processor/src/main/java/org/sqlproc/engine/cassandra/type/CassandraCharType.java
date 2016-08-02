package org.sqlproc.engine.cassandra.type;

import java.sql.SQLException;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The JDBC META type CHARACTER.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraCharType extends CassandraDefaultType implements CassandraSqlType {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getProviderSqlType() {
        return this;
    }

    @Override
    public Object get(Row row, String columnLabel) throws SQLException {
        String str;
        if (Character.isDigit(columnLabel.charAt(0)))
            str = row.getString(Integer.parseInt(columnLabel));
        else
            str = row.getString(columnLabel);
        if (str == null) {
            return null;
        } else {
            return new Character(str.charAt(0));
        }
    }

    @Override
    public void set(BoundStatement st, int index, Object value) throws SQLException {
        st.setString(index, (value).toString());
    }
}
