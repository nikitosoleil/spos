package com.nikitosoleil.processors;

import com.nikitosoleil.Buff;
import com.nikitosoleil.Token;

import java.io.IOException;

public class CommentProcessor extends EndProcessor {
    public CommentProcessor(Buff bf) {
        super(bf);
    }

    public Token process(String value) throws IOException {
        StringBuilder valueBuilder = new StringBuilder(value);
        while (true) {
            char ch = bf.next();
            if (ch == '\n')
            {
                bf.back(ch);
                return new Token(Token.Type.COMMENT, valueBuilder.toString());
            }
            else
                valueBuilder.append(ch);
        }
    }
}