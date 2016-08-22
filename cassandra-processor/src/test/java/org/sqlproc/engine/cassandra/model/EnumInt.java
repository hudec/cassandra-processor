package org.sqlproc.engine.cassandra.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum EnumInt implements Serializable {
    ENUM1(1), ENUM2(2), ENUM3(3);

    private static Map<Integer, EnumInt> identifierMap = new HashMap<Integer, EnumInt>();

    static {
        for (EnumInt value : EnumInt.values()) {
            identifierMap.put(value.getValue(), value);
        }
    }

    private Integer value;

    private EnumInt(Integer value) {
        this.value = value;
    }

    public static EnumInt fromValue(Integer value) {
        EnumInt result = identifierMap.get(value);
        if (result == null) {
            throw new IllegalArgumentException("No EnumInt for value: " + value);
        }
        return result;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name();
    }
}
