package com.nikitosoleil;

public class Logger {
    public final boolean UP = true;
    public final boolean DOWN = true;

    private static boolean useLogger = true;

    public static void turnLogger(boolean value) {
        useLogger = value;
    }

    public static void log(String str) {
        if (useLogger)
            System.out.println("   " + str);
    }
}
