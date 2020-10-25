package com.github.ducoral.tica;

import java.util.Map;

import static com.github.ducoral.tica.Base.tiziu;

class JsonItem implements QueryItem {

    final String expression;

    JsonItem(String expression) {
        this.expression = expression;
    }

    public Object evaluate(Map<String, Object> scope) {
        return tiziu().evaluate(expression, scope);
    }
}
