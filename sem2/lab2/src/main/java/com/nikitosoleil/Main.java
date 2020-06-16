package com.nikitosoleil;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        analyze("./input/test.py", "./output/index.html");
    }

    public static void analyze(String inputPath, String outputPath) throws IOException {
        Lexer lexer = new Lexer(inputPath);
        ArrayList<Token> tokens = new ArrayList<Token>();

        Token token = lexer.getNextToken();
        while (token != null)
        {
            tokens.add(token);
            token = lexer.getNextToken();
        }

        HtmlWriter writer = new HtmlWriter(outputPath);
        for(Token t: tokens) {
            System.out.println(t.getTokenType() + " " + t.getAttribute());
            writer.writeToken(t);
        }
        writer.finish();
    }
}

