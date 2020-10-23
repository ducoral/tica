package com.github.ducoral.tica;

import com.github.ducoral.tiziu.Tiziu;

import java.util.HashMap;
import java.util.Map;

import static com.github.ducoral.jutils.XML.Element;
import static com.github.ducoral.jutils.XML.root;

public class Main {

    public static void main(String[] args) throws Exception {
        Element element = root(Main.class.getResourceAsStream("/tica.xml"));
        Query query = Parser.parseQuery(element);
        System.out.println(query);

        Map<String, Object> variaveis = new HashMap<>();
        variaveis.put("x", 10);
        variaveis.put("idade", 50);

        Object resultado =
                Tiziu.builder().identifiers(variaveis).build().evaluate("x + 1 * (idade - 3)");

        System.out.println(resultado);

    }


}
