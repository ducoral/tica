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

    final Evaluable item;

    Query(Connection connection, String key, Sql sql, Evaluable item) {
        this.connection = connection;
        this.key = key;
        this.sql = sql;
        this.item = item;
    }

    public String key() {
        return key;
    }

    public Object evaluate(Evaluator evaluator, Map<String, Object> scope) {
        return new ArrayList<Object>() {{
            ResultSet rs = sql.execute(connection, scope);
            while (next(rs)) {
                Map<String, Object> localScope = map(rs)
                        .rename(sql::alias)
                        .ignore()
                        .done();
                localScope = map(localScope)
                        .merge(scope)
                        .merge(map(rs).ignore().done())
                        .ignore()
                        .done();
                add(item.evaluate(evaluator, localScope));
            }
        }};
    }
}
