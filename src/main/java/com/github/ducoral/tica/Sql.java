package com.github.ducoral.tica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.ducoral.jutils.Core.*;

class Sql {

    final String alias;

    final String select;

    final List<String> parameters = new ArrayList<>();

    Sql(String alias, String select) {
        this.alias = alias;
        this.select = extract(select, parameters);
    }

    ResultSet execute(Connection connection, Map<Object, Object> scope) {

        return null;
    }
}
