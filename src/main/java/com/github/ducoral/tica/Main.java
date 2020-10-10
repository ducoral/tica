package com.github.ducoral.tica;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedInputStream;

public class Main {

    static class Imprime implements Evaluator {

        final String margin;

        Imprime(String margin) {
            this.margin = margin;
        }

        @Override
        public Object accept(Tree.Query query) {
            println("query");
            query.sql.accept(ident());
            query.content.accept(ident());
            return null;
        }

        @Override
        public Object accept(Tree.Sql sql) {
            println("sql: " + sql.sql);
            return null;
        }

        @Override
        public Object accept(Tree.Content content) {
            println("content");
            for (Tree item : content.list)
                item.accept(ident());
            return null;
        }

        @Override
        public Object accept(Tree.Property property) {
            println(property.type.toString() + ": " + property.expression);
            return null;
        }

        private void println(String str) {
            System.out.println(margin + str);
        }

        private Imprime ident() {
            return new Imprime(margin + "  ");
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedInputStream input = new BufferedInputStream(Main.class.getResourceAsStream("/tica.xml"));
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
        Tree tree = Parser.parse(doc);
        tree.accept(new Imprime(""));
    }


}
