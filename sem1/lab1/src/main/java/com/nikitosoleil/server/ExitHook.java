package com.nikitosoleil.server;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class ExitHook {
    private static GlobalKeyboardHook keyboardHook;

    private ExitHook() {

    }

    public static void run() {
        if (keyboardHook == null)
            keyboardHook = new GlobalKeyboardHook(true);

        keyboardHook.addKeyListener(new GlobalKeyAdapter() {

            @Override
            public void keyPressed(GlobalKeyEvent event) {
                // Restarts manager
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_F4) {
                    System.out.println("F4 pressed");
                    Main.currentState();
                    Main.finish();
                }
                // Ends program work with closing processes
                else if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_F5) {
                    Main.finish();
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(GlobalKeyEvent event) {
            }
        });
    }
}
