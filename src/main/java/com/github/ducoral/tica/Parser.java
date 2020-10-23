package com.github.ducoral.tica;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.github.ducoral.jutils.XML.*;
import static com.github.ducoral.tica.Consts.*;

class Parser {

    static Query parseQuery(Element element) {
        accept(element, TAG_QUERY);
        Iterator<Element> iterator = element.children.iterator();
        return new Query(element.attribute(ATTR_KEY), parseSql(iterator), parseQueryItem(iterator));
    }

    static Sql parseSql(Iterator<Element> iterator) {
        Element sql = accept(iterator, TAG_SQL);
        return new Sql(sql.attribute(ATTR_ALIAS), sql.value);
    }

    static QueryItem parseQueryItem(Iterator<Element> iterator) {
        Element item = accept(iterator, TAG_OBJECT, TAG_ITEM);
        return item.name.equals(TAG_ITEM)
                ? new JsonItem(item.value)
                : parseObject(item.children.iterator());
    }

    static JsonObject parseObject(Iterator<Element> iterator) {
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
