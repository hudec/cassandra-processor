package org.sqlproc.engine.cassandra.plugin;

import org.sqlproc.engine.SqlProcessorException;
import org.sqlproc.engine.plugin.DefaultSqlPlugins;

/**
 * The Cassandra Processor plugins standard implementation.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraPlugins extends DefaultSqlPlugins {

    private static final String COUNT_COLNAME = "count";

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] sqlCount(String name, StringBuilder sql) {
        String sqlStr = sql.toString();
        String sqlUpper = sqlStr.toUpperCase();
        int ixSELECT = sqlUpper.indexOf(SELECT);
        int ixFROM = sqlUpper.indexOf(FROM);
        if (ixFROM < 0 || ixSELECT < 0 || ixSELECT > ixFROM)
            throw new SqlProcessorException("Can't derive select count from " + sql);

        StringBuilder sb = new StringBuilder();
        sb.append(sql.substring(0, ixSELECT + L_SELECT));
        sb.append(" count (*) ");
        sb.append(sql.substring(ixFROM));
        return new String[] { sb.toString(), COUNT_COLNAME };
    }
}
