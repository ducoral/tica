package com.github.ducoral.tica;

import com.github.ducoral.tiziu.Function;
import com.github.ducoral.tiziu.Tiziu;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

public class Tica {

    private final Tiziu tiziu = new Tiziu();

    public Tica configure(Map<String, Object> functions) {
        tiziu.configure(functions);
        return this;
    }

    public Tica configure(String name, Function function) {
        tiziu.functions().put(name, function);
        return this;
    }

    String generate(Connection connection, InputStream xml) {

        return null;
    }
}
