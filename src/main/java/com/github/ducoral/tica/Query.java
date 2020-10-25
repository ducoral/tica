package com.github.ducoral.tica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import static com.github.ducoral.jutils.Core.*;
import static com.github.ducoral.jutils.JDBC.*;

class Query implements Property {

    final Connection connection;

    final String key;

    final Sql sql;

    final QueryItem item;

    Query(Connection connection, String key, Sql sql, QueryItem item) {
        this.connection = connection;
        this.key = key;
        this.sql = sql;
        this.item = item;
    }

    public String key() {
        return key;
    }

    public Object evaluate(Map<Object, Object> scope) {
        return new ArrayList<Object>() {{
            ResultSet rs = sql.execute(connection, scope);
            while (next(rs))
                add(item.evaluate(merge(scope, map(rs))));
        }};
    }
}
