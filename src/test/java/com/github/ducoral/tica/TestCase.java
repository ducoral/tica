package com.github.ducoral.tica;

import com.github.ducoral.jutils.JDBC;

import java.sql.Connection;
import java.util.Map;

public class TestCase {

    protected final Connection connection;

    public TestCase() {
        connection = JDBC.connection("jdbc:hsqldb:file:target/ticadb", "SA", "");
        try {
            connection.setAutoCommit(false);
        } catch (Exception e) {
            throw new Oops(e.getMessage(), e);
        }
    }

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

    public void commit() {
        JDBC.commit(connection);
    }
}
