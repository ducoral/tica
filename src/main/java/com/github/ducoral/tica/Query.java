package com.github.ducoral.tica;

import java.util.List;

class Query {

    enum Output { ARRAY, OBJECT };

    final Output output;

    final Property sql;

    final List<?> content;

    public Query(Output output, Property sql, List<?> content) {
        this.output = output;
        this.sql = sql;
        this.content = content;
    }

}
