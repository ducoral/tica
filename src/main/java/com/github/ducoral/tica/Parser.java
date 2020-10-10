package com.github.ducoral.tica;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Parser {

    static final String TAG_QUERY = "query";
    static final String TAG_SQL = "sql";
    static final String TAG_CONTENT = "content";
    static final String TAG_INTEGER = "integer";
    static final String TAG_DECIMAL = "decimal";
    static final String TAG_STRING = "string";
    static final String TAG_BOOLEAN = "boolean";
    static final String TAG_DATETIME = "datetime";

    static final String ATTR_KEY = "key";
    static final String ATTR_OUTPUT = "output";
    static final String ATTR_ALIAS = "alias";
    static final String ATTR_DIGITS = "format";
    static final String ATTR_FORMAT = "digits";

    static final String MSG_INVALID_TAG = "Tag inválida: <%s>.";
    static final String MSG_EXPECTED_TAG = MSG_INVALID_TAG + " Era esperada <%s>.";
    static final String MSG_MISSING_TAG = "Está faltando a tag <%s>.";
    static final String MSG_EMPTY_TAG = "A tag <%s> está vazia.";

    static Tree parse(Document xml) {
        Element query = accept(xml.getDocumentElement(), "query");
        return parseQuery(query);
    }

    private static Tree parseQuery(Element query) {
        Iterator<Element> iterator = children(query).iterator();
        Tree.Sql sql = parseSql(iterator);
        Tree.Content content = parseContent(iterator);
        Tree.Attrs attrs = attrs(query, ATTR_OUTPUT, ATTR_KEY);
        return new Tree.Query(attrs, sql, content);
    }

    private static Tree.Sql parseSql(Iterator<Element> iterator) {
        Element element = accept(iterator, TAG_SQL);
        if (value(element).isEmpty())
            throw new Oops(MSG_EMPTY_TAG, TAG_SQL);
        Tree.Attrs attrs = attrs(element, ATTR_ALIAS);
        return new Tree.Sql(attrs, value(element));
    }

    private static Tree.Content parseContent(Iterator<Element> iterator) {
        Element element = accept(iterator, TAG_CONTENT);
        List<Element> items = children(element);
        if (items.isEmpty())
            throw new Oops(MSG_EMPTY_TAG, TAG_CONTENT);
        List<Tree> list = new ArrayList<>();
        for (Element item : items)
            list.add(parseContentItem(item));
        return new Tree.Content(list);
    }

    private static Tree parseContentItem(Element item) {
        String name = item.getTagName();
        if (TAG_QUERY.equals(name))
            return parseQuery(item);
        String value = value(item);
        if (value.isEmpty())
            throw new Oops(MSG_EMPTY_TAG, name);
        Tree.Property.Type type = null;
        switch (name) {
            case TAG_INTEGER: type = Tree.Property.Type.INTEGER; break;
            case TAG_DECIMAL: type = Tree.Property.Type.DECIMAL; break;
            case TAG_STRING: type = Tree.Property.Type.STRING; break;
            case TAG_BOOLEAN: type = Tree.Property.Type.BOOLEAN; break;
            case TAG_DATETIME: type = Tree.Property.Type.DATETIME; break;
            default: throw new Oops(MSG_INVALID_TAG, name);
        }
        Tree.Attrs attrs = attrs(item, ATTR_DIGITS, ATTR_FORMAT);
        return new Tree.Property(attrs, type, value);
    }

    private static Tree.Attrs attrs(Element element, String... names) {
        Tree.Attrs attrs = new Tree.Attrs();
        for (String name : names)
            if (element.hasAttribute(name))
                attrs.put(name, element.getAttribute(name));
        return attrs;
    }

    private static List<Element> children(Node node) {
        List<Element> list = new ArrayList<Element>();
        NodeList nodeList = node.getChildNodes();
        for (int index = 0; index < nodeList.getLength(); index++)
            if (nodeList.item(index).getNodeType() == Node.ELEMENT_NODE)
                list.add((Element) nodeList.item(index));
        return list;
    }

    private static String value(Node node) {
        return node.hasChildNodes() ? node.getFirstChild().getNodeValue() : "";
    }

    private static Element accept(Iterator<Element> iterator, String tagName) {
        if (iterator.hasNext())
            return accept(iterator.next(), tagName);
        else
            throw new Oops(MSG_MISSING_TAG, tagName);
    }

    private static Element accept(Element element, String tagName) {
        if (tagName.equals(element.getTagName()))
            return element;
        else
            throw new Oops(MSG_EXPECTED_TAG, element.getTagName(), tagName);
    }
}
