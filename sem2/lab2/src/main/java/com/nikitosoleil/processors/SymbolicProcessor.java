package com.nikitosoleil.processors;

import com.nikitosoleil.Buff;
import com.nikitosoleil.Token;

import java.io.IOException;

public class SymbolicProcessor extends EndProcessor {
    public SymbolicProcessor(Buff bf) {
        super(bf);
    }

    public Token process(String value) throws IOException {
        while(true) {
            char ch = bf.next();
            if(Python.isPartOfSymbolics(value + ch))
                value += ch;
            else {
                bf.back(ch);
                if(Python.isOperator(value))
                    return new Token(Token.Type.OPERATOR, value);
                else if(Python.isDelimiter(value))
                    return new Token(Token.Type.PUNCTUATION, value);
                else
                    return new Token(Token.Type.ERROR, value);
            }
        }
    }
}
