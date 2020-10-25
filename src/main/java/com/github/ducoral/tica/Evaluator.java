package com.github.ducoral.tica;

import java.util.Map;

interface Evaluator {

    Object evaluate(String expression, Map<String, Object> scope);
}
