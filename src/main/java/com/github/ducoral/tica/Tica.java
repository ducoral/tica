package com.github.ducoral.tica;

import com.github.ducoral.tiziu.Function;
import com.github.ducoral.tiziu.Tiziu;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import static com.github.ducoral.jutils.Core.*;
import static com.github.ducoral.jutils.XML.root;
import static java.util.Objects.requireNonNull;

public class Tica {

    private final Tiziu tiziu = new Tiziu();

    private final Map<String, Object> parameters = new HashMap<>();

    public Tica functions(Map<String, Object> functions) {
        tiziu.configure(functions);
        return this;
    }

    public Tica function(String name, Function function) {
        tiziu.functions().put(name, function);
        return this;
    }

    public Tica parameters(Map<String, Object> parameters) {
        this.parameters.putAll(parameters);
        return this;
    }

    void validate(InputStream xml) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(Tica.class.getResource("/tica.xsd").getPath()));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
        } catch (Exception e) {
            throw new Oops(e.getMessage(), e);
        }
    }

    public String generate(Connection connection, InputStream xml) {
        return generate(connection, xml, parameters);
    }

    public String generate(Connection connection, InputStream xml, Map<String, Object> parameters) {
        requireNonNull(connection);
        requireNonNull(xml);
        requireNonNull(parameters);
        byte[] array = bytes(xml);
        validate(stream(array));
        Query query = new Parser(connection).parseQuery(root(stream(array)));
        Object result = query.evaluate(tiziu::evaluate, parameters);
        return json(result);
    }
}
