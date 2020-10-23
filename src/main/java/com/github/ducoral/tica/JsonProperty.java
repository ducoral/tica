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

    @Override
    public String key() {
        return key;
    }

    @Override
    public Object evaluate(Map<String, Object> scope) {
        return null;
    }
}
