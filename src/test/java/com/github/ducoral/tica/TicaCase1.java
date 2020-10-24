package com.github.ducoral.tica;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;

import static com.github.ducoral.jutils.JDBC.*;

public class TicaCase1 extends TestCase {

    @Before
    public void setUp() {
        drop("estado");
        create("estado", "id int", "nome varchar(50)", "sigla varchar(2)");
        insert("estado", 1, "Paraná", "PR");
        insert("estado", 2, "São Paulo", "SP");
        insert("estado", 3, "Santa Catarina", "SC");
        insert("estado", 4, "Mato Grosso", "MG");
    }

    @After
    public void tearDown() {
        drop("estado");
    }

    @Test
    public void test() {

        ResultSet rs = select(connection, "select * from estado");
        while (next(rs))
            System.out.printf(
                    "id: %s, nome: %s, sigla: %s\n",
                    getString(rs, "id"),
                    getString(rs, "nome"),
                    getString(rs, "sigla"));

    }

}
