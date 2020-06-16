package com.nikitosoleil.processors;

import com.nikitosoleil.Buff;
import com.nikitosoleil.Token;

import java.io.IOException;

public class IdentifierProcessor extends EndProcessor {
    public IdentifierProcessor(Buff bf) {
        super(bf);
    }

    public Token process(String value) throws IOException {
        while (true) {
            char ch = bf.next();
            if(Python.isIdentifierSymbol(ch))
                value += ch;
            else
            {
                bf.back(ch);
                if(Python.isKeyword(value))
                    return new Token(Token.Type.KEYWORD, value);
                else if(Python.isLiteral(value))
                    return new Token(Token.Type.LITERAL, value);
                else
                    return new Token(Token.Type.IDENTIFIER, value);
            }
        }
    }
}
