package com.github.ducoral.tica;

import org.junit.Test;

import java.sql.ResultSet;

public class TestMain extends DefaultTest {

    @Test
    public void testDB() throws Exception {
        ResultSet rs = newConnection().prepareStatement("select * from cidade").executeQuery();
        while (rs.next())
            System.out.printf("ibge: %d, nome: %s%n", rs.getInt("ibge"), rs.getString("nome"));
        rs.close();
    }
}
