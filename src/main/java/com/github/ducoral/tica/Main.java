package com.github.ducoral.tica;

public class Main {

    public static void main(String[] args) throws Exception {


        QueryOld queryOld = Parser.parse(Main.class.getResourceAsStream("/tica.xml"));

        System.out.println(queryOld);

    }


}
