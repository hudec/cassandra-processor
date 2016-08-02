package org.sqlproc.engine.cassandra.type;

import org.sqlproc.engine.type.SqlDefaultType;

/**
 * The default META type for the JDBC stack. It's used in the case there's no explicit META type declaration in the META
 * SQL statements.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraDefaultType extends SqlDefaultType {

    @Override
    public Object getProviderSqlType() {
        return null;
    }

    @Override
    public Object getDatabaseSqlType() {
        return null;
    }
}
