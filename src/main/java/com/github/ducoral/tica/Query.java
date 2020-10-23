package com.github.ducoral.tica;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.ducoral.jutils.Core.*;
import static com.github.ducoral.tica.Consts.*;

class Query implements Property {

    final String key;

    final Sql sql;

    final QueryItem item;

    Query(String key, Sql sql, QueryItem item) {
        this.key = key;
        this.sql = sql;
        this.item = item;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public Object evaluate(Map<String, Object> scope) {
        return new ArrayList<Object>() {{
            ResultSet rs = sql.execute(CONNECTION.get(), scope);
            while (next(rs))
                add(item.evaluate(merge(scope, map(rs, sql.alias))));
        }};
    }
}
