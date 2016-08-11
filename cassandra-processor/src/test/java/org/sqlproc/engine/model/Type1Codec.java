package org.sqlproc.engine.model;

import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import com.datastax.driver.extras.codecs.MappingCodec;

public class Type1Codec extends MappingCodec<Type1, UDTValue> {

    private final UserType userType;

    public Type1Codec(TypeCodec<UDTValue> innerCodec, Class<Type1> javaType) {
        super(innerCodec, javaType);
        userType = (UserType) innerCodec.getCqlType();
    }

    @Override
    protected Type1 deserialize(UDTValue value) {
        return value == null ? null : new Type1(value.getString("t_varchar"), value.getInt("t_int"));
    }

    @Override
    protected UDTValue serialize(Type1 udtType) {
        return udtType == null ? null
                : userType.newValue().setString("t_varchar", udtType.getT_varchar()).setInt("t_int",
                        udtType.getT_int());
    }
}
