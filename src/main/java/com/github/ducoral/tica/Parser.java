package com.github.ducoral.tica;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.github.ducoral.jutils.XML.*;
import static com.github.ducoral.tica.Base.*;

class Parser {

    final Connection connection;

    Parser(Connection connection) {
        this.connection = connection;
    }

    Query parseQuery(Element element) {
        accept(element, TAG_QUERY);
        Iterator<Element> iterator = element.children.iterator();
        return new Query(connection, element.attribute(ATTR_KEY), parseSql(iterator), parseQueryItem(iterator));
    }

    Sql parseSql(Iterator<Element> iterator) {
        Element sql = accept(iterator, TAG_SQL);
        return new Sql(sql.attribute(ATTR_ALIAS), sql.value);
    }

    QueryItem parseQueryItem(Iterator<Element> iterator) {
        Element item = accept(iterator, TAG_OBJECT, TAG_ITEM);
        return item.name.equals(TAG_ITEM)
                ? new JsonItem(item.value)
                : parseObject(item.children.iterator());
    }

    JsonObject parseObject(Iterator<Element> iterator) {
        List<Property> properties = new ArrayList<>();
        while (iterator.hasNext()) {
            Element item = accept(iterator, TAG_QUERY, TAG_PROPERTY);
            properties.add(item.name.equals(TAG_QUERY)
                    ? parseQuery(item)
                    : new JsonProperty(item));
        }
        return new JsonObject(properties);
    }
}
