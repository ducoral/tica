package com.github.ducoral.tica;

import com.github.ducoral.tiziu.Tiziu;

import java.util.Map;

public class JsonItem implements QueryItem {

    final String expression;

    public JsonItem(String expression) {
        this.expression = expression;
    }

    public Object evaluate(Map<String, Object> scope) {
        return new Tiziu().evaluate(expression, scope);
    }
}
