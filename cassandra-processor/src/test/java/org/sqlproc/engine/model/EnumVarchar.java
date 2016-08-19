package org.sqlproc.engine.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum EnumVarchar implements Serializable {
    ENUM1("e1"), ENUM2("e2"), ENUM3("e3");

    private static Map<String, EnumVarchar> identifierMap = new HashMap<String, EnumVarchar>();

    static {
        for (EnumVarchar value : EnumVarchar.values()) {
            identifierMap.put(value.getValue(), value);
        }
    }

    private String value;

    /**
     */
    private EnumVarchar(String value) {
        this.value = value;
    }

    public static EnumVarchar fromValue(String value) {
        EnumVarchar result = identifierMap.get(value);

        if (result == null) {
            throw new IllegalArgumentException("No EnumVarchar for value: " + value);
        }

        return result;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name();
    }
}
