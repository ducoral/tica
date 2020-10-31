package com.github.ducoral.tica;

import com.github.ducoral.jutils.XML;

import java.util.Map;

import static com.github.ducoral.jutils.Core.safe;

class JsonProperty implements Property {

    final String key;

    final String expression;

    public JsonProperty(XML.Element element) {
        this.key = element.attribute(Consts.ATTR_KEY);
        this.expression = safe(element.value);
    }

    public String key() {
        return key;
    }

    public Object evaluate(Evaluator evaluator, Map<String, Object> scope) {
        return expression.isEmpty()
                ? scope.get(key)
                : evaluator.evaluate(expression, scope);
    }
}
