package com.github.ducoral.tica;

import com.github.ducoral.jutils.XML;

import java.util.Map;

import static com.github.ducoral.jutils.Core.safe;
import static com.github.ducoral.tica.Base.tiziu;

class JsonProperty implements Property {

    final String key;

    final String expression;

    public JsonProperty(XML.Element element) {
        this.key = element.attribute(Base.ATTR_KEY);
        this.expression = safe(element.value);
    }

    public String key() {
        return key;
    }

    public Object evaluate(Map<String, Object> scope) {
        return expression.isEmpty()
                ? scope.get(key)
                : tiziu().evaluate(expression, scope);
    }
}
