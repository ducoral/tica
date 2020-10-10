package com.github.ducoral.tica;

import java.util.*;

class Json {

    final Object value;

    Json(Object value) {
        this.value = value;
    }

    static Json json(Object value) {
        return new Json(value);
    }

    @Override
    public String toString() {
        if (value instanceof Map)
            return toObjectString();
        else if (value instanceof List)
            return toArrayString();
        else if (value instanceof String)
            return "\"" + value + "\"";
        else
            return String.valueOf(value);
    }

    private String toObjectString() {
        StringBuilder object = new StringBuilder("{");
        String comma = "";
        for (Map.Entry<?, ?> entry : ((Map<?, ?>) value).entrySet()) {
            object.append(comma)
                    .append('"')
                    .append(entry.getKey())
                    .append("\":")
                    .append(entry.getValue());
            comma = ",";
        }
        return object.append('}').toString();
    }

    private String toArrayString() {
        StringBuilder array = new StringBuilder("[");
        String comma = "";
        for (Object value : (List<?>) value) {
            array.append(comma).append(value);
            comma = ",";
        }
        return array.append(']').toString();
    }
}
