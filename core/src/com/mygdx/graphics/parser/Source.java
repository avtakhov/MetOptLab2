package com.mygdx.graphics.parser;

public interface Source {
    boolean hasNext();

    char next();

    int getPos();
}
