package com.github.ducoral.tica;

import org.junit.After;
import org.junit.Before;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

class DefaultTest {

    static Connection newConnection() {
        try {
            Connection connection =
                    DriverManager.getConnection("jdbc:hsqldb:file:target/ticadb", "SA", "");
            connection.setAutoCommit(false);
            return connection;
        } catch (Exception e) {
            throw new Oops(e.getMessage(), e);
        }
    }

    @Before
    public void setUp() {
        try {
            Connection connection = newConnection();
            Statement st = connection.createStatement();
            BufferedReader br = new BufferedReader(new InputStreamReader(DefaultTest.class.getResourceAsStream("/source.sql")));
            String sql = br.readLine();
            while (sql != null) {
                st.execute(sql);
                sql = br.readLine();
            }
            connection.commit();
            connection.close();
        } catch (Exception e) {
            throw new Oops(e.getMessage(), e);
        }
    }

    @After
    public void tearDown() {
        try {
            Connection connection = newConnection();
            Statement st = connection.createStatement();
            st.execute("drop table cidade");
            st.execute("drop table estado");
            connection.close();
        } catch (Exception e) {
            throw new Oops(e.getMessage(), e);
        }
    }

}
