package org.sqlproc.engine.cassandra.type;

import java.sql.SQLException;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;

/**
 * The contract which has to be implemented by all JDBC META types.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public interface CassandraSqlType {

    /**
     * Retrieves the value of the designated column in the current row of this <code>ResultSet</code> object as a Java
     * type value.
     * 
     * @param rs
     *            a ResultSet instance
     * @param columnLabel
     *            the label for the column specified with the SQL AS clause. If the SQL AS clause was not specified,
     *            then the label is the name of the column
     * @return the column value; if the value is SQL <code>NULL</code>, the value returned is <code>null</code>
     * @exception SQLException
     *                if the columnLabel is not valid; if a database access error occurs or this method is called on a
     *                closed result set
     */
    public abstract Object get(Row row, String columnLabel, Class<?>... moreTypes);

    /**
     * Sets the designated parameter to the given Java value. The JDBC driver converts this to the appropriate SQL type
     * value.
     * 
     * @param st
     *            a PreparedStatement instance
     * @param index
     *            the first parameter is 1, the second is 2, ...
     * @param value
     *            the parameter value
     * @throws SQLException
     *             if parameterIndex does not correspond to a parameter marker in the SQL statement; if a database
     *             access error occurs or this method is called on a closed <code>PreparedStatement</code>
     */
    public abstract void set(BoundStatement st, String columnLabel, Object value, Class<?>... moreTypes);
}
