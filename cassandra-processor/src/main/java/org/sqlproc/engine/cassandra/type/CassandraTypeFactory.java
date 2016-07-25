package org.sqlproc.engine.cassandra.type;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.sqlproc.engine.SqlRuntimeException;
import org.sqlproc.engine.type.SqlIdentityType;
import org.sqlproc.engine.type.SqlInternalType;
import org.sqlproc.engine.type.SqlMetaType;
import org.sqlproc.engine.type.SqlTypeFactory;

/**
 * The factory definition for the JDBC stack, which can be used to construct the {@link SqlMetaType} instances.
 * 
 * In the process of the META SQL statements and mapping rules parsing the input/output values META types have to be
 * established. For this purpose a factory class responsible for these META types construction has to be supplied.<br>
 * 
 * The implementation is based on the Singleton design pattern.
 * 
 * @author <a href="mailto:Vladimir.Hudec@gmail.com">Vladimir Hudec</a>
 */
public class CassandraTypeFactory implements SqlTypeFactory {

    /**
     * The private static instance of this factory.
     */
    private static CassandraTypeFactory factory = new CassandraTypeFactory();

    /**
     * The private constructor.
     */
    private CassandraTypeFactory() {
    }

    /**
     * The main method to obtain the singleton instance of this factory.
     * 
     * @return the META types factory for the JDBC stack
     */
    public static CassandraTypeFactory getInstance() {
        return factory;
    }

    /**
     * Singleton instance of Integer based enumeration type.
     */
    static final SqlMetaType ENUM_INT = new CassandraEnumIntegerType();
    /**
     * Singleton instance of String based enumeration type.
     */
    static final SqlMetaType ENUM_STRING = new CassandraEnumStringType();
    /**
     * Singleton instance of String based enumeration type.
     */
    static final SqlMetaType BIG_DECIMAL = new CassandraBigDecimalType();
    /**
     * Singleton instance of String based enumeration type.
     */
    static final SqlMetaType BIG_INTEGER = new CassandraBigIntegerType();
    /**
     * Singleton instance of String based enumeration type.
     */
    static final SqlMetaType BOOLEAN = new CassandraBooleanType();
    /**
     * Singleton instance of String based enumeration type.
     */
    static final SqlMetaType BYTE_ARRAY = new CassandraByteArrayType();
    /**
     * Singleton instance of String based enumeration type.
     */
    static final SqlMetaType BYTE = new CassandraByteType();
    /**
     * Singleton instance of String based enumeration type.
     */
    static final SqlMetaType CHAR = new CassandraCharType();
    /**
     * Singleton instance of String based enumeration type.
     */
    static final SqlMetaType DATE = new CassandraDateType();
    /**
     * Singleton instance of String based enumeration type.
     */
    static final SqlMetaType DOUBLE = new CassandraDoubleType();
    /**
     * Singleton instance of String based enumeration type.
     */
    static final SqlMetaType FLOAT = new CassandraFloatType();
    /**
     * Singleton instance of String based enumeration type.
     */
    static final SqlMetaType INTEGER = new CassandraIntegerType();
    /**
     * Singleton instance of String based enumeration type.
     */
    static final SqlMetaType LONG = new CassandraLongType();
    /**
     * Singleton instance of String based enumeration type.
     */
    static final SqlMetaType SHORT = new CassandraShortType();
    /**
     * Singleton instance of String based enumeration type.
     */
    static final SqlMetaType STRING = new CassandraStringType();
    /**
     * Singleton instance of String based enumeration type.
     */
    static final SqlMetaType TIMESTAMP = new CassandraTimestampType();
    /**
     * Singleton instance of String based enumeration type.
     */
    static final SqlMetaType TIME = new CassandraTimeType();
    /**
     * Singleton instance of auto-generated identity type.
     */
    static final SqlMetaType IDENTITY = new SqlIdentityType() {
        @Override
        public Object getProviderSqlType() {
            return null;
        }

        @Override
        public Object getProviderSqlNullType() {
            return null;
        }
    };
    /**
     * Singleton instance of default type.
     */
    static final SqlMetaType DEFAULT = new CassandraDefaultType();
    /**
     * Singleton instances of generic types.
     */
    static final SqlMetaType[] TYPES = { BIG_DECIMAL, BIG_INTEGER, BOOLEAN, BYTE_ARRAY, BYTE, CHAR, DATE, DOUBLE,
            ENUM_INT, ENUM_STRING, FLOAT, INTEGER, LONG, SHORT, STRING, TIMESTAMP, TIME };

    /**
     * The immutable map between the Java class types and the internal types.
     */
    static Map<Class<?>, SqlMetaType> CLASS_TO_TYPE_MAP = new HashMap<Class<?>, SqlMetaType>();
    /**
     * The immutable map between the META types name and the internal types.
     */
    static Map<String, SqlMetaType> META_TO_TYPE_MAP = new HashMap<String, SqlMetaType>();

    /**
     * Static init.
     */
    static {
        for (SqlMetaType type : TYPES) {
            if (type instanceof SqlInternalType) {
                for (Class<?> classType : ((SqlInternalType) type).getClassTypes())
                    CLASS_TO_TYPE_MAP.put(classType, type);
                for (String metaType : ((SqlInternalType) type).getMetaTypes())
                    META_TO_TYPE_MAP.put(metaType.toUpperCase(), type);
            }
        }
        CLASS_TO_TYPE_MAP = Collections.unmodifiableMap(CLASS_TO_TYPE_MAP);
        META_TO_TYPE_MAP = Collections.unmodifiableMap(META_TO_TYPE_MAP);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlMetaType getDefaultType() {
        return DEFAULT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlMetaType getEnumIntegerType() {
        return ENUM_INT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlMetaType getEnumStringType() {
        return ENUM_STRING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlMetaType getIdentityType() {
        return IDENTITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlMetaType[] getAllTypes() {
        return TYPES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlMetaType getMetaType(Class<?> clazz) {
        return CLASS_TO_TYPE_MAP.get(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SqlMetaType getMetaType(String name) {

        if (name.startsWith("h_")) {
            throw new UnsupportedOperationException(); // new SqlHibernateType(name.substring(2));
        } else {
            SqlMetaType metaType = META_TO_TYPE_MAP.get(name.toUpperCase());
            if (metaType == null)
                throw new SqlRuntimeException("Unsupported Meta Type " + name);
            return metaType;
        }
    }
}
