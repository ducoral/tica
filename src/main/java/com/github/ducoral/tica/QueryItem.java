package com.github.ducoral.tica;

import java.util.Map;

interface QueryItem {

    Object evaluate(Map<Object, Object> scope);

}
