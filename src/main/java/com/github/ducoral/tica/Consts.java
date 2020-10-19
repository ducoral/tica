package com.github.ducoral.tica;

final class Consts {

    static final String TAG_QUERY = "query";
    static final String TAG_SQL = "sql";
    static final String TAG_CONTENT = "content";
    static final String TAG_INTEGER = "integer";
    static final String TAG_DECIMAL = "decimal";
    static final String TAG_STRING = "string";
    static final String TAG_BOOLEAN = "boolean";
    static final String TAG_DATETIME = "datetime";

    static final String ATTR_TYPE = "type";
    static final String ATTR_KEY = "key";
    static final String ATTR_OUTPUT = "output";
    static final String ATTR_ALIAS = "alias";
    static final String ATTR_DIGITS = "format";
    static final String ATTR_FORMAT = "digits";

    static final String OUTPUT_OBJECT = "object";
    static final String OUTPUT_ARRAY = "array";

    static final String MSG_INVALID_TAG = "Query inválida: <%s>.";
    static final String MSG_EXPECTED_TAG = MSG_INVALID_TAG + " Era esperada <%s>.";
    static final String MSG_MISSING_TAG = "Está faltando a tag <%s>.";
    static final String MSG_EMPTY_TAG = "A tag <%s> está vazia.";

    private Consts() {
    }
}