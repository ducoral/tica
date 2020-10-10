package com.github.ducoral.tica;

import java.util.HashMap;
import java.util.List;

abstract class Tree {

    static class Attrs extends HashMap<String, String> { };

    final Attrs attrs;

    static class Query extends Tree {
        final Sql sql;
        final Content content;

        Query(Attrs attrs, Sql sql, Content content) {
            super(attrs);
            this.sql = sql;
            this.content = content;
        }

        @Override
        void accept(Evaluator evaluator) {
            evaluator.accept(this);
        }
    }

    static class Sql extends Tree {
        final String sql;

        Sql(Attrs attrs, String sql) {
            super(attrs);
            this.sql = sql;
        }

        @Override
        void accept(Evaluator evaluator) {
            evaluator.accept(this);
        }
    }

    static class Content extends Tree {
        final List<Tree> list;

        Content(List<Tree> list) {
            super(new Attrs());
            this.list = list;
        }

        @Override
        void accept(Evaluator evaluator) {
            evaluator.accept(this);
        }
    }

    static class Property extends Tree {
        enum Type {
            INTEGER,
            DECIMAL,
            STRING,
            BOOLEAN,
            DATETIME,
        }

        final Type type;
        final String expression;

        Property(Attrs attrs, Type type, String expression) {
            super(attrs);
            this.type = type;
            this.expression = expression;
        }

        @Override
        void accept(Evaluator evaluator) {
            evaluator.accept(this);
        }
    }

    abstract void accept(Evaluator evaluator);

    private Tree(Attrs attrs) {
        this.attrs = attrs;
    }
}
