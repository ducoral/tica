package com.github.ducoral.tica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.github.ducoral.jutils.Core.*;
import static com.github.ducoral.tica.Consts.ATTR_ALIAS;

class QueryOld {

    enum Output { ARRAY, OBJECT };

    final Output output;

    final Property sql;

    final List<?> content;

    QueryOld(Output output, Property sql, List<?> content) {
        this.output = output;
        this.sql = sql;
        this.content = content;
    }

    String toJson(Connection connection, Scope scope) {
        try {
            List<String> params = new ArrayList<>();
            String select = params(sql.value, params);
            ResultSet rs = prepare(connection.prepareStatement(select), params, scope).executeQuery();
            return output == Output.ARRAY ? toArray(rs, scope) : toObject(rs, scope);
        } catch (Exception e) {
            throw new Oops(e.getMessage(), e);
        }
    }

    private String toArray(ResultSet rs, Scope scope) {
        try {
            StringBuilder array = new StringBuilder("[");
            Comma comma = new Comma();
            while (rs.next()) {
                Scope local = populate(duplicate(scope, alias()), rs);
                array.append(comma).append(contentToJson(local));
            }
            return array.append(']').toString();
        } catch (Exception e) {
            throw new Oops(e.getMessage(), e);
        }
    }

    private String toObject(ResultSet rs, Scope scope) {
        try {
            if (rs.next())
                return contentToJson(populate(duplicate(scope, alias()), rs));
            else
                return "{}";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String contentToJson(Scope scope) {

        return "";
    }

    private String alias() {
        return safe(sql.attributes.get(ATTR_ALIAS));
    }

    public static void main(String[] args) {
        List<String> params = new ArrayList<>();
        String sql = "select * ${estado} * ${estado} cliente ${cidade} opa ${estado} where ${cidade.id}";
        sql = params(sql, params);
        System.out.println("sql: " + sql + ", params: " + params);
    }

}
