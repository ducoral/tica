package com.github.ducoral.tica;

import java.util.Map;

public class JsonItem implements QueryItem {

    final String expression;

    public JsonItem(String expression) {
        this.expression = expression;
    }

    @Override
    public Object evaluate(Map<String, Object> scope) {
        return null;
    }
}
