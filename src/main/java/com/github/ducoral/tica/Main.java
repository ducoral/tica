package com.github.ducoral.tica;

import com.github.ducoral.tiziu.Tiziu;

import java.util.Map;

import static com.github.ducoral.jutils.Core.map;
import static com.github.ducoral.jutils.XML.Element;
import static com.github.ducoral.jutils.XML.root;

public class Main {

    public static void main(String[] args) throws Exception {
//        Element element = root(Main.class.getResourceAsStream("/tica.xml"));
//        Query query = Parser.parseQuery(element);
//        System.out.println(query);
//
//        Object json = query.evaluate(map().done());
//
//        System.out.println(json);


        Object value = new Tiziu().evaluate(
                "populacao + ' (' + porte + ')'",
                map().pair("populacao", 200).pair("porte", "Médio I").done());

        System.out.println(value);

    }

}
