package com.github.ducoral.tica;

import java.util.Map;

interface Evaluable {

    Object evaluate(Evaluator evaluator, Map<String, Object> scope);
}
