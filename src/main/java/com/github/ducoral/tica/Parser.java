package com.github.ducoral.tica;

import com.github.ducoral.jutils.XML;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.github.ducoral.jutils.XML.Element;
import static com.github.ducoral.jutils.XML.root;
import static com.github.ducoral.tica.Consts.*;

class Parser {

    static QueryOld parse(InputStream xml) {
        try {
            byte[] bytes = new byte[xml.available()];
            xml.read(bytes);
            SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(new StreamSource(Parser.class.getResourceAsStream("/tica.xsd")))
                    .newValidator()
                    .validate(new StreamSource(new ByteArrayInputStream(bytes)));
            return parseQuery(accept(root(new ByteArrayInputStream(bytes)), TAG_QUERY));
        } catch (Exception e) {
            throw new Oops(e.getMessage(), e);
        }
    }

    private static QueryOld parseQuery(Element query) {
        Iterator<Element> iterator = query.children.iterator();
        QueryOld.Output output = parseOutput(query);
        Property sql = parseSql(iterator);
        List<?> content = parseContent(iterator);
        return new QueryOld(output, sql, content);
    }

    private static QueryOld.Output parseOutput(Element query) {
        return OUTPUT_OBJECT.equals(query.attributes.get(ATTR_OUTPUT))
                ? QueryOld.Output.OBJECT
                : QueryOld.Output.ARRAY;
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
