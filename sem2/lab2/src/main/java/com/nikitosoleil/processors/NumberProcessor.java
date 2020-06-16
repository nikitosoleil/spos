package com.nikitosoleil.processors;

import com.nikitosoleil.Buff;
import com.nikitosoleil.Token;

import java.io.IOException;

public class NumberProcessor extends EndProcessor {
    enum State {
        START,
        FLOAT,
        EXP_START,
        EXP,
        SUFFIX,
        ERROR,
    }

    public NumberProcessor(Buff bf) {
        super(bf);
    }

    public Token process(String value) throws IOException {
        State state = State.START;
        while (true) {
            char ch = bf.next();
            switch (state) {
                case START:
                    if (Python.isDigit(ch))
                        value += ch;
                    else if (ch == '.') {
                        value += ch;
                        state = State.FLOAT;
                    } else if (Python.isExponent(ch)) {
                        value += ch;
                        state = State.EXP_START;
                    } else if (Python.isNumberSuffix(ch)) {
                        value += ch;
                        state = State.SUFFIX;
                    } else if (Python.isIdentifierStart(ch)) {
                        value += ch;
                        state = State.ERROR;
                    } else {
                        bf.back(ch);
                        return new Token(Token.Type.NUMBER, value);
                    }
                    break;
                case FLOAT:
                    System.out.println(value + " " + ch);
                    if (Python.isDigit(ch))
                        value += ch;
                    else if (value.length() > 1) {
                        if (Python.isExponent(ch)) {
                            value += ch;
                            state = State.EXP_START;
                        } else if (Python.isImaginarySuffix(ch)) {
                            value += ch;
                            state = State.SUFFIX;
                        } else if (Python.isIdentifierStart(ch)) {
                            value += ch;
                            state = State.ERROR;
                        } else {
                            bf.back(ch);
                            return new Token(Token.Type.NUMBER, value);
                        }
                    } else {
                        bf.back(ch);
                        return new Token(Token.Type.PUNCTUATION, value);
                    }
                    break;
                case EXP_START:
                    if (Python.isSign(ch) || Python.isDigit(ch)) {
                        value += ch;
                        state = State.EXP;
                    } else {
                        value += ch;
                        state = State.ERROR;
                    }
                    break;
                case EXP:
                    if (Python.isDigit(ch))
                        value += ch;
                    else if (Python.isImaginarySuffix(ch)) {
                        value += ch;
                        state = State.SUFFIX;
                    } else if (Python.isIdentifierStart(ch)) {
                        value += ch;
                        state = State.ERROR;
                    } else {
                        bf.back(ch);
                        return new Token(Token.Type.NUMBER, value);
                    }
                    break;
                case SUFFIX:
                    if (Python.isIdentifierSymbol(ch)) {
                        value += ch;
                        state = State.ERROR;
                    } else {
                        bf.back(ch);
                        return new Token(Token.Type.NUMBER, value);
                    }
                case ERROR:
                    if (Python.isIdentifierSymbol(ch))
                        value += ch;
                    else {
                        bf.back(ch);
                        return new Token(Token.Type.ERROR, value);
                    }
            }
        }
    }
}