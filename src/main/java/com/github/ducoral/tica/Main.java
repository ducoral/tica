package com.github.ducoral.tica;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedInputStream;

import static com.github.ducoral.jutils.Core.*;
import static com.github.ducoral.jutils.XML.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Object j = new JsonMap(){{ put("chave", list("opa", 12, true)); }};
        System.out.println(json(j));
        System.out.println();

        BufferedInputStream input = new BufferedInputStream(Main.class.getResourceAsStream("/tica.xml"));
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
        Query query = Parser.parse(root(doc));
    }


}
