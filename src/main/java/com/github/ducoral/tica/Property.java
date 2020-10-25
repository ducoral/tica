package com.github.ducoral.tica;

import java.util.Map;

interface Property {

    Object evaluate(Map<String, Object> scope);

    String key();
}
