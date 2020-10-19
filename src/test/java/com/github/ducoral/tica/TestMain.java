package com.github.ducoral.tica;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class TestMain {

    Connection conn;

    @Before
    public void before() throws Exception {
        conn = DriverManager.getConnection("jdbc:hsqldb:file:target/ticadb", "SA", "");
        conn.prepareStatement("create table cidade(id int, nome varchar(100))").execute();
        PreparedStatement st = conn.prepareStatement("insert into cidade(id, nome) values(?, ?)");
        exec(st, 1, "marinpá");
        exec(st, 2, "invatuba");
        exec(st, 3, "camargo");
        conn.commit();
    }

    void exec(PreparedStatement st, int id, String nome) throws Exception {
        st.setInt(1, id);
        st.setString(2, nome);
        st.execute();
    }

    @After
    public void after() throws Exception {
        conn.prepareStatement("drop table cidade").execute();
        conn.close();
    }

    @Test
    public void testDB() throws Exception {
        ResultSet rs = conn.prepareStatement("select * from cidade").executeQuery();
        while (rs.next())
            System.out.println(String.format("id: %d, nome: %s", rs.getInt("id"), rs.getString("nome")));
    }

}
