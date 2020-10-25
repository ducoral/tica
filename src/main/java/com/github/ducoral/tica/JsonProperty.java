package com.github.ducoral.tica;

import com.github.ducoral.jutils.XML;

import java.util.Map;

class JsonProperty implements Property {

    final String key;

    final String expression;

    public JsonProperty(XML.Element element) {
        this.key = element.attribute(Consts.ATTR_KEY);
        this.expression = element.value;
    }

    public String key() {
        return key;
    }

    public Object evaluate(Map<Object, Object> scope) {
        return scope.get(key);
    }
}
