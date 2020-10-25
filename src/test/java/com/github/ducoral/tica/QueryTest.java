package com.github.ducoral.tica;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.github.ducoral.jutils.Core.map;
import static com.github.ducoral.jutils.XML.root;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QueryTest extends TestCase {

    @Before
    public void before() {
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
        String xml =
                "<query xmlns=\"tica\">\n" +
                "    <sql>select * from estado</sql>\n" +
                "    <object>\n" +
                "        <property key=\"id\"/>\n" +
                "        <property key=\"nome\"/>\n" +
                "        <property key=\"sigla\"/>\n" +
                "    </object>\n" +
                "</query>";

        Query query = new Parser(connection).parseQuery(root(xml));

        Object result = query.evaluate(map().done());

        assertTrue(result instanceof List);

        List<?> list = (List<?>) result;

        assertEquals(4, list.size());
        assertTrue(list.get(0) instanceof Map);
        assertTrue(list.get(1) instanceof Map);
        assertTrue(list.get(2) instanceof Map);
        assertTrue(list.get(3) instanceof Map);

        Map<?, ?> map = (Map<?, ?>) list.get(0);
        assertEquals(1, map.get("id"));
        assertEquals("Paraná", map.get("nome"));
        assertEquals("PR", map.get("sigla"));

        map = (Map<?, ?>) list.get(1);
        assertEquals(2, map.get("id"));
        assertEquals("São Paulo", map.get("nome"));
        assertEquals("SP", map.get("sigla"));

        map = (Map<?, ?>) list.get(2);
        assertEquals(3, map.get("id"));
        assertEquals("Santa Catarina", map.get("nome"));
        assertEquals("SC", map.get("sigla"));

        map = (Map<?, ?>) list.get(3);
        assertEquals(4, map.get("id"));
        assertEquals("Mato Grosso", map.get("nome"));
        assertEquals("MG", map.get("sigla"));
    }

}
