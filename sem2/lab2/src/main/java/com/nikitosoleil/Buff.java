package com.nikitosoleil;

import java.io.BufferedReader;
import java.io.IOException;

public class Buff {
    private String value = " ";
    private int ptr = 0;

    private static final int bsize = 64;

    private BufferedReader br;

    public Buff(BufferedReader br) {
        this.br = br;
    }

    public Character next() throws IOException {
        char ch = value.charAt(ptr);
        ptr++;

        if (ptr >= value.length()) {
            char[] cbuff = new char[bsize];
            br.read(cbuff, 0, bsize);
            value = String.valueOf(cbuff);
            ptr = 0;
        }

        return ch;
    }

    public void back(char ch) {
        value = value.substring(0, ptr) + ch + value.substring(ptr);
    }
}
