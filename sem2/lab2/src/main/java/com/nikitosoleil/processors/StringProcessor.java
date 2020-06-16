package com.nikitosoleil.processors;

import com.nikitosoleil.Buff;
import com.nikitosoleil.Token;

import java.io.IOException;

public class StringProcessor extends EndProcessor {
    enum State {
        BACKSLASH,
        OTHER
    }

    public StringProcessor(Buff bf) {
        super(bf);
    }

    public Token process(String value) throws IOException {
        boolean raw = false;
        if (Python.isLetter(value.charAt(0))) {
            String prefix = value.substring(0, 2);
            if (prefix.contains("r") || prefix.contains("R"))
                raw = true;
        }

        State state = State.OTHER;
        char opener = value.charAt(value.length() - 1);
        StringBuilder valueBuilder = new StringBuilder(value);
        while (true) {
            char ch = bf.next();
            switch (state) {
                case OTHER:
                    if (ch == '\\' && !raw)
                        state = State.BACKSLASH;
                    else if (ch == '\n') {
                        bf.back(ch);
                        return new Token(Token.Type.ERROR, valueBuilder.toString());
                    } else {
                        valueBuilder.append(ch);
                        if (ch == opener)
                            return new Token(Token.Type.LITERAL, valueBuilder.toString());
                    }
                    break;
                case BACKSLASH:
                    switch (ch) { // idk how to better implement this
                        case 'n':
                            valueBuilder.append('\n');
                            break;
                        case 't':
                            valueBuilder.append('\t');
                            break;
                        case 'b':
                            valueBuilder.append('\b');
                            break;
                        case 'r':
                            valueBuilder.append('\r');
                            break;
                        default:
                            valueBuilder.append(ch);
                    }
                    state = State.OTHER;
                    break;
            }
        }
    }
}
