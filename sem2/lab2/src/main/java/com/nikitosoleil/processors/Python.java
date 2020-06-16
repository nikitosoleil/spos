package com.nikitosoleil.processors;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Python {
    private static Set<String> keywords = new HashSet<>(Arrays.asList(
            "and", "del", "from", "not", "while",
            "as", "elif", "global", "or", "with",
            "assert", "else", "if", "pass", "yield",
            "break", "except", "import", "print",
            "class", "exec", "in", "raise",
            "continue", "finally", "is", "return",
            "def", "for", "lambda", "try"));

    private static Set<String> operators = new HashSet<>(Arrays.asList(
            "+", "-", "*", "**", "/", "//", "%",
            "<<", ">>", "&", "|", "^", "~",
            "<", ">", "<=", ">=", "==", "!=", "<>"));

    private static Set<String> punctuation = new HashSet<>(Arrays.asList(
            "(", ")", "[", "]", "{", "}", "@",
            ",", ":", ".", "`", "=", ";",
            "+=", "-=", "*=", "/=", "//=", "%=",
            "&=", "|=", "^=", ">>=", "<<=", "**="));

    private static Set<String> literals = new HashSet<>(Arrays.asList(
            "true", "false", "null"));

    private static Set<Character> spacers = new HashSet<>(Arrays.asList(
            ' ', '\t', '\r', '\n'));

    private static Set<Character> prohibited = new HashSet<>(Arrays.asList(
            '$', '?'));

    private static Set<Character> stringEnclosures = new HashSet<>(Arrays.asList(
            '\'', '\"'));

    private static Set<Character> exponents = new HashSet<>(Arrays.asList(
            'e', 'E'));

    private static Set<Character> signs = new HashSet<>(Arrays.asList(
            '-', '+'));

    private static Set<Character> longSuffix = new HashSet<>(Arrays.asList(
            'l', 'L'));

    private static Set<Character> imaginarySuffix = new HashSet<>(Arrays.asList(
            'j', 'J'));

    private static Set<String> stringPrefixes = new HashSet<>(Arrays.asList(
            "r", "u", "ur", "R", "U", "UR", "Ur", "uR"
            , "b", "B", "br", "Br", "bR", "BR"));

    public static boolean isKeyword(String s) {
        return keywords.contains(s);
    }

    public static boolean isOperator(String s) {
        return operators.contains(s);
    }

    public static boolean isDelimiter(String s) {
        return punctuation.contains(s);
    }

    public static boolean isLiteral(String s) {
        return literals.contains(s);
    }

    public static boolean isSpacer(char ch) {
        return spacers.contains(ch);
    }

    public static boolean isProhibited(char ch) {
        return prohibited.contains(ch);
    }

    public static boolean isDigit(char ch) {
        return '0' <= ch && ch <= '9';
    }

    public static boolean isLowercase(char ch) {
        return 'a' <= ch && ch <= 'z';
    }

    public static boolean isUppercase(char ch) {
        return 'A' <= ch && ch <= 'Z';
    }

    public static boolean isLetter(char ch) {
        return isLowercase(ch) || isUppercase(ch);
    }

    public static boolean isIdentifierStart(char ch) {
        return isLetter(ch) || ch == '_';
    }

    public static boolean isIdentifierSymbol(char ch) {
        return isIdentifierStart(ch) || isDigit(ch);
    }

    public static boolean isStringEnclosure(char ch) {
        return stringEnclosures.contains(ch);
    }

    public static boolean isPartOfOperators(String s) {
        for (String op : operators) {
            if (op.startsWith(s))
                return true;
        }
        return false;
    }

    public static boolean isPartOfPunctuation(String s) {
        for (String del : punctuation) {
            if (del.startsWith(s))
                return true;
        }
        return false;
    }

    public static boolean isPartOfSymbolics(String s) {
        return isPartOfPunctuation(s) || isPartOfOperators(s);
    }

    public static boolean isOperator(char ch) {
        return operators.contains(ch);
    }

    public static boolean isPunctuation(char ch) {
        return punctuation.contains(ch);
    }

    public static boolean isPartOfStringPrefix(String s) {
        for (String prefix : stringPrefixes) {
            if (prefix.startsWith(s))
                return true;
        }
        return false;
    }

    public static boolean isStringPrefix(String s) {
        return stringPrefixes.contains(s);
    }

    public static boolean isExponent(char ch) {
        return exponents.contains(ch);
    }

    public static boolean isSign(char ch) {
        return signs.contains(ch);
    }

    public static boolean isLongSuffix(char ch) {
        return longSuffix.contains(ch);
    }

    public static boolean isImaginarySuffix(char ch) {
        return imaginarySuffix.contains(ch);
    }

    public static boolean isNumberSuffix(char ch) {
        return isLongSuffix(ch) || isImaginarySuffix(ch);
    }
}
