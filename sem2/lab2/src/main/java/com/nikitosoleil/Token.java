package com.nikitosoleil;

public class Token {
    public enum Type {
        NUMBER,
        LITERAL,
        COMMENT,
        KEYWORD,
        IDENTIFIER,
        OPERATOR,
        PUNCTUATION,
        INDENTATION,

        ERROR
    }

    private Type tokenType;
    private String attribute;

    public Token() {
    }

    public Token(Type tokenType, String attribute) {
        this.tokenType = tokenType;
        this.attribute = attribute;

    }

    public void setTokenType(Type tokenType) {
        this.tokenType = tokenType;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Type getTokenType() {
        return this.tokenType;
    }

    public String getAttribute() {
        return this.attribute;
    }
}
