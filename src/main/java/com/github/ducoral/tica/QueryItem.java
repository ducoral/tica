package com.github.ducoral.tica;

import java.util.Map;

interface QueryItem {

    Object evaluate(Map<String, Object> scope);
}
