package com.github.ducoral.tica;

import com.github.ducoral.jutils.XML;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.github.ducoral.jutils.XML.Element;
import static com.github.ducoral.tica.Consts.*;

class Parser {

    static Query parse(Element element) {
        Element query = accept(element, TAG_QUERY);
        return parseQuery(query);
    }

    private static Query parseQuery(Element query) {
        Iterator<Element> iterator = query.children.iterator();
        Query.Output output = parseOutput(query);
        Property sql = parseSql(iterator);
        List<?> content = parseContent(iterator);
        return new Query(output, sql, content);
    }

    private static Query.Output parseOutput(Element query) {
        return OUTPUT_OBJECT.equals(query.attributes.get(ATTR_OUTPUT))
                ? Query.Output.OBJECT
                : Query.Output.ARRAY;
    }

    private static Property parseSql(Iterator<Element> iterator) {
        Element element = Parser.accept(iterator, TAG_SQL);
        if (element.value.isEmpty())
            throw new Oops(MSG_EMPTY_TAG, TAG_SQL);
        return new Property(element);
    }

    private static List<?> parseContent(Iterator<Element> iterator) {
        Element element = Parser.accept(iterator, TAG_CONTENT);
        if (element.children.isEmpty())
            throw new Oops(MSG_EMPTY_TAG, TAG_CONTENT);
        List<Object> content = new ArrayList<>();
        for (Element child : element.children)
            content.add(parseContentItem(child));
        return content;
    }

    private static Object parseContentItem(Element item) {
        if (TAG_QUERY.equals(item.name))
            return parseQuery(item);
        if (item.value.isEmpty())
            throw new Oops(MSG_EMPTY_TAG, item.name);
        item.attributes.put(ATTR_TYPE, type(item));
        return new Property(item);
    }
    
    private static String type(Element item) {
        switch (item.name) {
            case TAG_INTEGER:
            case TAG_DECIMAL:
            case TAG_STRING:
            case TAG_BOOLEAN:
            case TAG_DATETIME: return item.name;
            default: throw new Oops(MSG_INVALID_TAG, item.name);
        }
    }

    private static XML.Element accept(Iterator<XML.Element> iterator, String tagName) {
        if (iterator.hasNext())
            return accept(iterator.next(), tagName);
        else
            throw new Oops(MSG_MISSING_TAG, tagName);
    }

    private static XML.Element accept(XML.Element element, String tagName) {
        if (tagName.equals(element.name))
            return element;
        else
            throw new Oops(MSG_EXPECTED_TAG, element.name, tagName);
    }
}
