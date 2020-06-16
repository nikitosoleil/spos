package com.nikitosoleil.processors;

import com.nikitosoleil.Buff;
import com.nikitosoleil.Token;

import java.io.IOException;

public class CentralProcessor extends EndProcessor {
    enum State {
        COMMA,
        BACKSLASH,
        NEWLINE,
        OTHER
    }

    private CommentProcessor commentProcessor;
    private SymbolicProcessor symbolicProcessor;
    private StringProcessor stringProcessor;
    private NumberProcessor numberProcessor;
    private IdentifierProcessor identifierProcessor;
    private LetterProcessor letterProcessor;
    private State state = State.OTHER;

    public CentralProcessor(Buff bf) {
        super(bf);
        commentProcessor = new CommentProcessor(bf);
        symbolicProcessor = new SymbolicProcessor(bf);
        stringProcessor = new StringProcessor(bf);
        numberProcessor = new NumberProcessor(bf);
        identifierProcessor = new IdentifierProcessor(bf);
        letterProcessor = new LetterProcessor(bf);
    }

    public Token process(String value) throws IOException {
        while (true) {
            Character ch = bf.next();
            if (ch == (char) 0) {
                return null;
            } else if (ch == '\\') {
                state = State.BACKSLASH;
            } else if(ch == '\r') {

            }
            else if (ch == '\n') {
                if (state == State.OTHER) {
                    state = State.NEWLINE;
                    return new Token(Token.Type.PUNCTUATION, "\n");
                }
            } else if (Python.isSpacer(ch)) {
                if (state == State.NEWLINE)
                    return new Token(Token.Type.INDENTATION, Character.toString(ch));
            } else if (Python.isProhibited(ch)) {
                return new Token(Token.Type.ERROR, value + ch);
            } else if (ch == '#') {
                return commentProcessor.process(value + ch);
            } else {
                state = State.OTHER;
                if (Python.isLetter(ch)) {
                    return letterProcessor.process(value + ch);
                } else if (Python.isStringEnclosure(ch)) {
                    return stringProcessor.process(value + ch);
                } else if (Python.isIdentifierStart(ch)) {
                    return identifierProcessor.process(value + ch);
                } else if (Python.isPartOfSymbolics(Character.toString(ch))) {
                    if (ch == ',')
                        state = State.COMMA;
                    return symbolicProcessor.process(value + ch);
                } else if (Python.isDigit(ch)) {
                    return numberProcessor.process(value + ch);
                }
            }
        }
    }
}