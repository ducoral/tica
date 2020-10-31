package com.github.ducoral.tica;

import com.github.ducoral.jutils.JDBC;

import java.sql.Connection;
import java.util.Map;

class TestCase {

    protected final Connection connection;

    protected TestCase() {
        connection = JDBC.connection("jdbc:hsqldb:file:target/ticadb", "SA", "");
        try {
            connection.setAutoCommit(false);
        } catch (Exception e) {
            throw new Oops(e.getMessage(), e);
        }
    }

    protected void create(String table, String... columns) {
        JDBC.create(connection, table, columns);
    }

    protected void drop(String table) {
        JDBC.drop(connection, table);
    }

    protected void insert(String table, Object... values) {
        JDBC.insert(connection, table, values);
    }
}
