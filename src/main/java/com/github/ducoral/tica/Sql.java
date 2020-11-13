package com.github.ducoral.tica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import static com.github.ducoral.jutils.Core.*;
import static com.github.ducoral.jutils.JDBC.select;

class Sql {

    final String alias;

    final String select;

    final List<String> parameters;

    Sql(String alias, String select) {
        this.alias = alias;
        parameters = parameters(select);
        this.select = select.replaceAll(PARAM_PATTERN.pattern(), "?");
    }

    String alias(String identifier) {
        return safe(alias).isEmpty() ? identifier : alias + "." + identifier;
    }

    ResultSet execute(Connection connection, Map<String, Object> scope) {
        return select(connection, select, values(parameters, scope).toArray());
    }
}
