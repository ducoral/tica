package com.github.ducoral.tica;

import java.util.Map;

interface Property {

    Object evaluate(Map<Object, Object> scope);

    String key();
}
