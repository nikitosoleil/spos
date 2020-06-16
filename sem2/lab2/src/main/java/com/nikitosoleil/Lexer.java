package com.nikitosoleil;

import com.nikitosoleil.processors.CentralProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Lexer {
    private final Buff buff;
    private final CentralProcessor centralProcessor;
    private Token lastToken = null;

    public Lexer(String inputPath) throws IOException {
        buff = new Buff(Files.newBufferedReader(Paths.get(inputPath)));
        centralProcessor = new CentralProcessor(buff);
    }

    public Token getNextToken() throws IOException {
        return centralProcessor.process("");
    }
}
