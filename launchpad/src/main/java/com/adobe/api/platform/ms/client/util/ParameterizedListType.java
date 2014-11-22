package com.adobe.api.platform.ms.client.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Utility class used for de-serializing lists of JSON object.
 * <p>
 * This is an internal class and it is not meant to be used directly.
 * Please use {@link com.adobe.api.platform.ms.client.RestClient#getList(Class)} instead.
 * <p>
 * User: ccristia
 * Date: 8/23/13
 * Time: 11:15 AM
 */
public class ParameterizedListType implements ParameterizedType {

    private Type type;

    public ParameterizedListType(Type type) {
        this.type = type;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[]{type};
    }

    @Override
    public Type getRawType() {
        return List.class;
    }

    @Override
    public Type getOwnerType() {
        return List.class;
    }
}
