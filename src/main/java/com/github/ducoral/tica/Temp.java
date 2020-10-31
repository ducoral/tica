package com.github.ducoral.tica;

import java.util.PropertyResourceBundle;

import static com.github.ducoral.jutils.Core.*;

public class Temp {

    public static void main(String[] args) {

        PropertyResourceBundle properties =  properties("strs");

        System.out.println(properties.getString("temp"));

    }
}
