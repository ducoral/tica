package com.github.ducoral.tica;

import java.util.Map;

class JsonValue implements Evaluable {

    final String expression;

    JsonValue(String expression) {
        this.expression = expression;
    }

    public Object evaluate(Evaluator evaluator, Map<String, Object> scope) {
        return evaluator.evaluate(expression, scope);
    }
}
