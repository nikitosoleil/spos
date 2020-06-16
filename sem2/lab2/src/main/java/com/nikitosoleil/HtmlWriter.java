package com.nikitosoleil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HtmlWriter {
    private final Map<Token.Type, String> map = new HashMap<>();
    {
        map.put(Token.Type.NUMBER, "color: #80A0F0;");
        map.put(Token.Type.OPERATOR, "color: #F000F0; font-weight: bold;");
        map.put(Token.Type.LITERAL, "color: #80F0A0;");
        map.put(Token.Type.PUNCTUATION, "color: #F08000;");
        map.put(Token.Type.IDENTIFIER, "color: #F0F0F0;");
        map.put(Token.Type.KEYWORD, "color: #4080F0; font-weight: bold;");
        map.put(Token.Type.COMMENT, "color: #40A080;");
        map.put(Token.Type.INDENTATION, "background-color: #808000;");
        map.put(Token.Type.ERROR, "color: #F0F0F0; text-decoration: underline; text-decoration-color:red");
    }
    private final BufferedWriter writer;

    public HtmlWriter(String outputPath) throws IOException {
        writer = new BufferedWriter(new FileWriter(outputPath));
        writer.write("<html><head> <meta charset=\"utf-8\">" +
                "<title>Lexer result</title>" +
                "<style type=\"text/css\">" +
                "html, body {margin: 5px; padding: 5px; background: #404040;}" +
                "span {font-size: 110%;margin: 1px; padding: 1px}" +
                "</style>" +
                "</head>" +
                "<body>");
    }

    private String tokenToHtml(Token t) {
        if(t.getTokenType() == Token.Type.COMMENT)
            return "";
        if (t.getTokenType().equals(Token.Type.PUNCTUATION) && t.getAttribute().equals("\n"))
            return "<br>";

        String rs = t.getAttribute();
        rs = rs.replace("<", "&#60;");
        rs = rs.replace(">", "&#62;");

        rs = rs.replace("\t", "&nbsp;");
        rs = rs.replace("  ", "&nbsp;&nbsp;&nbsp;");
        return "<span style='" + map.get(t.getTokenType()) + "'>" + rs + "</span>";
    }

    public void writeToken(Token t) throws IOException {
        writer.write(tokenToHtml(t));
    }
    public void finish() throws IOException {
        writer.write("</body>");
        writer.close();
    }
}
