package com.github.ducoral.tica;

import java.sql.Connection;

final class Consts {

    static final ThreadLocal<Connection> CONNECTION = new ThreadLocal<>();

    static final String TAG_QUERY = "query";
    static final String TAG_SQL = "sql";
    static final String TAG_OBJECT = "object";
    static final String TAG_PROPERTY = "property";
    static final String TAG_ITEM = "item";

    static final String ATTR_TYPE = "type";
    static final String ATTR_KEY = "key";
    static final String ATTR_OUTPUT = "output";
    static final String ATTR_ALIAS = "alias";

    static final String MSG_INVALID_TAG = "Query inválida: <%s>.";
    static final String MSG_EXPECTED_TAG = MSG_INVALID_TAG + " Era esperada <%s>.";
    static final String MSG_MISSING_TAG = "Está faltando a tag <%s>.";
    static final String MSG_EMPTY_TAG = "A tag <%s> está vazia.";

    private Consts() {
    }
}
