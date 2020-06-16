package com.nikitosoleil.processors;

import com.nikitosoleil.Buff;
import com.nikitosoleil.Token;

import java.io.IOException;

public class LetterProcessor extends EndProcessor {
    private StringProcessor stringProcessor;
    private IdentifierProcessor identifierProcessor;

    public LetterProcessor(Buff bf) {
        super(bf);
        stringProcessor = new StringProcessor(bf);
        identifierProcessor = new IdentifierProcessor(bf);
    }

    public Token process(String value) throws IOException {
        while (true) {
            char ch = bf.next();
            if(Python.isStringEnclosure(ch) && Python.isStringPrefix(value))
                return stringProcessor.process(value + ch);
            if(!Python.isPartOfStringPrefix(value + ch))
            {
                bf.back(ch);
                return identifierProcessor.process(value);
            }
            else
                value += ch;
        }
    }
}