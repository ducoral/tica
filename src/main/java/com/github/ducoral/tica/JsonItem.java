package com.github.ducoral.tica;

import java.util.Map;

public class JsonItem implements QueryItem {

    final String expression;

    public JsonItem(String expression) {
        this.expression = expression;
    }

    public Object evaluate(Map<Object, Object> scope) {
        return null;
    }
}
