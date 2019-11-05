package com.nikitosoleil.server;

import com.nikitosoleil.Logger;

import java.util.Scanner;

public class Main {
    private static final boolean USE_LOGGER = true;

    private static Manager manager;
    public static boolean usePrompts;


    public static void main(String[] args) {
        Logger.turnLogger(USE_LOGGER);

        promptVariant();
        if (!usePrompts)
            ExitHook.run();
        while (true) {
            manager = new Manager();
            Thread managerThread = new Thread(() -> manager.start());
            managerThread.start();
            while (manager.isUp()) ;
            if (managerThread.isAlive())
                managerThread.interrupt();
        }

    }

    public static void restartManager() {
        manager.finish();
    }

    private static void promptVariant() {
        System.out.println("Press (1) to stop via F4 or (2) to use prompts");
        Scanner scanner = new Scanner(System.in);
        int type = scanner.nextInt();
        usePrompts = type == 2;
        Logger.log("Prompts: " + usePrompts);
    }

    public static void currentState() {
        manager.currentState();
    }

    public static void finish() {
        manager.finish();
    }
}
