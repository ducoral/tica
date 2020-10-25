package com.github.ducoral.tica;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class JsonObject implements QueryItem {

    final List<Property> properties;

    public JsonObject(List<Property> properties) {
        this.properties = properties;
    }

    public Object evaluate(Map<String, Object> scope) {
        return new HashMap<Object, Object>() {{
            for (Property property : properties)
                put(property.key(), property.evaluate(scope));
        }};
    }
}
