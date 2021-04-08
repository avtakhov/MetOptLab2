package com.mygdx.graphics.parser;

public class BaseParser {
    final int BUFFER_SIZE = 5;
    protected final char[] buffer = new char[BUFFER_SIZE];
    protected char ch;
    private StringSource source;

    public void setSource(StringSource source) {
        this.source = source;
        skipChars(BUFFER_SIZE);
    }

    protected int getPos() {
        int m = 1;
        for (int i = BUFFER_SIZE - 2; i >= 0 && buffer[i] == '\0'; i--) {
            m++;
        }
        return source.getPos() - BUFFER_SIZE + m - 1;
    }

    protected void skipChars(int count) {
        while (count-- > 0) {
            nextChar();
        }
    }

    protected void nextChar() {
        System.arraycopy(buffer, 1, buffer, 0, BUFFER_SIZE - 1);
        buffer[BUFFER_SIZE - 1] = source.hasNext() ? source.next() : '\0';
        ch = buffer[0];
    }

    protected boolean hasNext() {
        return ch != '\0';
    }

    protected boolean test(char expected) {
        if (ch == expected) {
            nextChar();
            return true;
        }
        return false;
    }

    protected void expect(final char c) {
        if (ch != c) {
            throw new IllegalStateException(getPos() + ": Expected '" + c + "', found " + (ch == '\0' ? "EOF" : "'" + ch + "'"));
        }
        nextChar();
    }

    protected void expect(final String value) {
        for (char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }
}
