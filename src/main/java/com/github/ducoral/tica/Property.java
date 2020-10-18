package com.github.ducoral.tica;

import com.github.ducoral.jutils.XML;

import java.util.Map;

class Property {

    final String name;

    final String value;

    final Map<String, String> attributes;

    Property(XML.Element element) {
        name = element.name;
        value = element.value;
        attributes = element.attributes;
    }
}
