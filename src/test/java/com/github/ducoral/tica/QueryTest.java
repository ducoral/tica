package com.github.ducoral.tica;

import com.github.ducoral.tiziu.Tiziu;
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

    final Evaluator evaluator = (expression, scope) -> new Tiziu().evaluate(expression, scope);

    @Before
    public void before() {
        drop("estado");
        create("estado", "id int", "nome varchar(50)", "sigla char(2)");
        insert("estado", 1, "Paraná", "PR");
        insert("estado", 2, "São Paulo", "SP");
        insert("estado", 3, "Santa Catarina", "SC");
        insert("estado", 4, "Mato Grosso", "MT");
        drop("cidade");
        create("cidade", "id int", "nome varchar(50)", "estado char(2)");
        insert("cidade", 1, "Ivatuba", "PR");
        insert("cidade", 2, "Doutor Camargo", "PR");
        insert("cidade", 3, "Maringá", "PR");
        insert("cidade", 4, "São Paulo", "SP");
        insert("cidade", 5, "Osasco", "SP");
        insert("cidade", 6, "Americana", "SP");
        insert("cidade", 7, "Blumenau", "SC");
        insert("cidade", 8, "Joinvile", "SC");
        insert("cidade", 9, "Floripa", "SC");
        insert("cidade", 10, "Cuiabá", "MT");
        insert("cidade", 11, "Sinop", "MT");
        insert("cidade", 12, "Sorriso", "MT");
        insert("cidade", 13, "Tangará da Serra", "MT");
        insert("cidade", 14, "Cáceres", "MT");
    }

    @After
    public void tearDown() {
        drop("estado");
    }

    @Test
    public void testObjectOnlyKeyPresent() {
        String xml =
                "<query xmlns=\"tica\">\n" +
                "    <sql>select * from estado order by id</sql>\n" +
                "    <object>\n" +
                "        <property key=\"Id\"/>\n" +
                "        <property key=\"nOmE\"/>\n" +
                "        <property key=\"SIGLA\"/>\n" +
                "    </object>\n" +
                "</query>";

        Query query = new Parser(connection).parseQuery(root(xml));


        Object result = query.evaluate(evaluator, map().done());

        assertTrue(result instanceof List);

        List<?> list = (List<?>) result;

        assertEquals(4, list.size());
        assertTrue(list.get(0) instanceof Map);
        assertTrue(list.get(1) instanceof Map);
        assertTrue(list.get(2) instanceof Map);
        assertTrue(list.get(3) instanceof Map);

        Map<?, ?> map = (Map<?, ?>) list.get(0);
        assertEquals(1, map.get("Id"));
        assertEquals("Paraná", map.get("nOmE"));
        assertEquals("PR", map.get("SIGLA"));

        map = (Map<?, ?>) list.get(1);
        assertEquals(2, map.get("Id"));
        assertEquals("São Paulo", map.get("nOmE"));
        assertEquals("SP", map.get("SIGLA"));

        map = (Map<?, ?>) list.get(2);
        assertEquals(3, map.get("Id"));
        assertEquals("Santa Catarina", map.get("nOmE"));
        assertEquals("SC", map.get("SIGLA"));

        map = (Map<?, ?>) list.get(3);
        assertEquals(4, map.get("Id"));
        assertEquals("Mato Grosso", map.get("nOmE"));
        assertEquals("MT", map.get("SIGLA"));
    }

    @Test
    public void testObjectWithExpression() {
        String xml =
                "<query xmlns=\"tica\">\n" +
                "    <sql>select * from estado order by id</sql>\n" +
                "    <object>\n" +
                "        <property key=\"codigo\">ID</property>\n" +
                "        <property key=\"descricao\">Nome</property>\n" +
                "        <property key=\"uf\">sigLA</property>\n" +
                "    </object>\n" +
                "</query>";

        Query query = new Parser(connection).parseQuery(root(xml));

        Object result = query.evaluate(evaluator, map().done());

        assertTrue(result instanceof List);

        List<?> list = (List<?>) result;

        assertEquals(4, list.size());
        assertTrue(list.get(0) instanceof Map);
        assertTrue(list.get(1) instanceof Map);
        assertTrue(list.get(2) instanceof Map);
        assertTrue(list.get(3) instanceof Map);

        Map<?, ?> map = (Map<?, ?>) list.get(0);
        assertEquals(1, map.get("codigo"));
        assertEquals("Paraná", map.get("descricao"));
        assertEquals("PR", map.get("uf"));

        map = (Map<?, ?>) list.get(1);
        assertEquals(2, map.get("codigo"));
        assertEquals("São Paulo", map.get("descricao"));
        assertEquals("SP", map.get("uf"));

        map = (Map<?, ?>) list.get(2);
        assertEquals(3, map.get("codigo"));
        assertEquals("Santa Catarina", map.get("descricao"));
        assertEquals("SC", map.get("uf"));

        map = (Map<?, ?>) list.get(3);
        assertEquals(4, map.get("codigo"));
        assertEquals("Mato Grosso", map.get("descricao"));
        assertEquals("MT", map.get("uf"));
    }

    @Test
    public void testSubQuery() {
        String xml = 
                "<query xmlns=\"tica\">\n" +
                "    <sql alias=\"estado\">select * from estado order by id</sql>\n" +
                "    <object>\n" +
                "        <property key=\"id\"/>\n" +
                "        <property key=\"nome\"/>\n" +
                "        <property key=\"sigla\"/>\n" +
                "        <query key=\"cidades\">\n" +
                "            <sql>select * from cidade where estado = ${estado.sigla} order by id</sql>\n" +
                "            <object>\n" +
                "                <property key=\"id\"/>\n" +
                "                <property key=\"nome\"/>\n" +
                "            </object>\n" +
                "        </query>\n" +
                "    </object>\n" +
                "</query>";

        Query query = new Parser(connection).parseQuery(root(xml));

        Object result = query.evaluate(evaluator, map().done());

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
        assertTrue(map.get("cidades") instanceof List);
        List<?> cidades = (List<?>) map.get("cidades");
        assertEquals(3, cidades.size());
        assertTrue(cidades.get(0) instanceof Map);
        assertTrue(cidades.get(1) instanceof Map);
        assertTrue(cidades.get(2) instanceof Map);
        Map<?, ?> cidade = (Map<?, ?>) cidades.get(0);
        assertEquals(1, cidade.get("id"));
        assertEquals("Ivatuba", cidade.get("nome"));
        cidade = (Map<?, ?>) cidades.get(1);
        assertEquals(2, cidade.get("id"));
        assertEquals("Doutor Camargo", cidade.get("nome"));
        cidade = (Map<?, ?>) cidades.get(2);
        assertEquals(3, cidade.get("id"));
        assertEquals("Maringá", cidade.get("nome"));

        map = (Map<?, ?>) list.get(1);
        assertEquals(2, map.get("id"));
        assertEquals("São Paulo", map.get("nome"));
        assertEquals("SP", map.get("sigla"));
        assertTrue(map.get("cidades") instanceof List);
        cidades = (List<?>) map.get("cidades");
        assertEquals(3, cidades.size());
        assertTrue(cidades.get(0) instanceof Map);
        assertTrue(cidades.get(1) instanceof Map);
        assertTrue(cidades.get(2) instanceof Map);
        cidade = (Map<?, ?>) cidades.get(0);
        assertEquals(4, cidade.get("id"));
        assertEquals("São Paulo", cidade.get("nome"));
        cidade = (Map<?, ?>) cidades.get(1);
        assertEquals(5, cidade.get("id"));
        assertEquals("Osasco", cidade.get("nome"));
        cidade = (Map<?, ?>) cidades.get(2);
        assertEquals(6, cidade.get("id"));
        assertEquals("Americana", cidade.get("nome"));

        map = (Map<?, ?>) list.get(2);
        assertEquals(3, map.get("id"));
        assertEquals("Santa Catarina", map.get("nome"));
        assertEquals("SC", map.get("sigla"));
        assertTrue(map.get("cidades") instanceof List);
        cidades = (List<?>) map.get("cidades");
        assertEquals(3, cidades.size());
        assertTrue(cidades.get(0) instanceof Map);
        assertTrue(cidades.get(1) instanceof Map);
        assertTrue(cidades.get(2) instanceof Map);
        cidade = (Map<?, ?>) cidades.get(0);
        assertEquals(7, cidade.get("id"));
        assertEquals("Blumenau", cidade.get("nome"));
        cidade = (Map<?, ?>) cidades.get(1);
        assertEquals(8, cidade.get("id"));
        assertEquals("Joinvile", cidade.get("nome"));
        cidade = (Map<?, ?>) cidades.get(2);
        assertEquals(9, cidade.get("id"));
        assertEquals("Floripa", cidade.get("nome"));

        map = (Map<?, ?>) list.get(3);
        assertEquals(4, map.get("id"));
        assertEquals("Mato Grosso", map.get("nome"));
        assertEquals("MT", map.get("sigla"));
        assertTrue(map.get("cidades") instanceof List);
        cidades = (List<?>) map.get("cidades");
        assertEquals(5, cidades.size());
        assertTrue(cidades.get(0) instanceof Map);
        assertTrue(cidades.get(1) instanceof Map);
        assertTrue(cidades.get(2) instanceof Map);
        assertTrue(cidades.get(3) instanceof Map);
        assertTrue(cidades.get(4) instanceof Map);
        cidade = (Map<?, ?>) cidades.get(0);
        assertEquals(10, cidade.get("id"));
        assertEquals("Cuiabá", cidade.get("nome"));
        cidade = (Map<?, ?>) cidades.get(1);
        assertEquals(11, cidade.get("id"));
        assertEquals("Sinop", cidade.get("nome"));
        cidade = (Map<?, ?>) cidades.get(2);
        assertEquals(12, cidade.get("id"));
        assertEquals("Sorriso", cidade.get("nome"));
        cidade = (Map<?, ?>) cidades.get(3);
        assertEquals(13, cidade.get("id"));
        assertEquals("Tangará da Serra", cidade.get("nome"));
        cidade = (Map<?, ?>) cidades.get(4);
        assertEquals(14, cidade.get("id"));
        assertEquals("Cáceres", cidade.get("nome"));
    }

}
