package org.sqlproc.engine.cassandra;

import org.sqlproc.engine.SqlDefaultFactory;
import org.sqlproc.engine.SqlEngineFactory;
import org.sqlproc.engine.SqlFeature;
import org.sqlproc.engine.SqlProcessorLoader;
import org.sqlproc.engine.cassandra.type.CassandraTypeFactory;

/**
 * The implementation of the {@link SqlEngineFactory} optimized for the JDBC stack.
 * 
 * <p>
 * It's suitable mainly for the Spring DI based configuration, like the next one for the new loader
 * {@link SqlProcessorLoader}:<br>
 * 
 * <pre>
 * &lt;beans ...&gt;
 *   ...
 *   &lt;bean id="sqlFactory" class="org.sqlproc.engine.jdbc.JdbcEngineFactory" init-method="init"&gt;
 *     &lt;property name="metaFilesNames">
 *       &lt;list>
 *         &lt;value>statements.qry&lt;/value>
 *       &lt;/list>
 *     &lt;/property>
 *   &lt;/bean&gt;
 * &lt;/beans&gt;
 * </pre>
 * 
 * and use the next code to obtain an instance of the SQL Query engine
 * 
 * <pre>
 * SqlQueryEngine sqlEngine = sqlFactory.getQueryEngine(&quot;ALL&quot;);
 * </pre>
 * 
 * or the next code to obtain an instance of the SQL CRUD engine
 * 
 * <pre>
 * SqlCrudEngine sqlEngine = sqlFactory.getCrudEngine(&quot;ALL&quot;);
 * </pre>
 * 
 * <p>
 * For more info please see the <a href="https://github.com/hudec/sql-processor/wiki">Tutorials</a>.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraEngineFactory extends SqlDefaultFactory {

    /**
     * Creates a new instance with default values for the JDBC stack.
     */
    public CassandraEngineFactory() {
        this(false);
    }

    /**
     * Creates a new instance with default values for the JDBC stack.
     * 
     * @param lazyInit
     *            this flag indicates to speed up the initialization process.
     */
    public CassandraEngineFactory(boolean lazyInit) {
        super(lazyInit);
        setJdbc(true);
        setFilter(SqlFeature.CASSANDRA);
        setTypeFactory(CassandraTypeFactory.getInstance());
    }

    /**
     * Creates a new instance with no default values.
     * 
     * @param asyncInitThreads
     *            number of threads used for asynchronous initialization
     */
    public CassandraEngineFactory(int asyncInitThreads) {
        super(asyncInitThreads);
        setJdbc(true);
        setTypeFactory(CassandraTypeFactory.getInstance());
    }

    /**
     * Creates a new instance with no default values.
     * 
     * @param lazyInit
     *            this flag indicates to speed up the initialization process.
     * @param asyncInitThreads
     *            number of threads used for asynchronous initialization
     */
    public CassandraEngineFactory(boolean lazyInit, int asyncInitThreads) {
        super(lazyInit, asyncInitThreads);
        setJdbc(true);
        setTypeFactory(CassandraTypeFactory.getInstance());
    }
}
