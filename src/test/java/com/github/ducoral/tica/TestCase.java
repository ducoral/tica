package com.github.ducoral.tica;

import java.sql.Connection;
import java.util.Map;

import com.github.ducoral.jutils.JDBC;

class TestCase {

    protected final Connection connection =
            JDBC.connection("jdbc:hsqldb:file:target/ticadb", "SA", "");

    public boolean create(String table, String... columns) {
        return JDBC.create(connection, table, columns);
    }

    public boolean drop(String table) {
        return JDBC.drop(connection, table);
    }

    public boolean insert(String table, Object... values) {
        return JDBC.insert(connection, table, values);
    }

    public boolean update(String table, String condition, Map<Object, Object> values) {
        return JDBC.update(connection, table, condition, values);
    }

}
