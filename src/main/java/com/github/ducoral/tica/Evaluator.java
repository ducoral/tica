package com.github.ducoral.tica;

interface Evaluator {
    Object accept(Tree.Query query);
    Object accept(Tree.Sql sql);
    Object accept(Tree.Content content);
    Object accept(Tree.Property property);
}
