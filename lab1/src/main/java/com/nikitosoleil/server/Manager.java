package com.nikitosoleil.server;

import com.nikitosoleil.Logger;
import com.nikitosoleil.client.FunctionProcess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Manager {
    private volatile boolean isUp = true;
    private Process fProcess, gProcess;
    private int x;
    private Server server;

    public void start() {
        inputX();
        server = new Server(x);

        try {
            startProcesses(server.getPort());
            server.establishConnection();
            server.sendFunctionsInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            server.startListening(fProcess, gProcess);
            isUp = false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void inputX() {
        System.out.println("Input x: ");
        Scanner scanner = new Scanner(System.in);
        x = scanner.nextInt();
    }

    private void startProcesses(int port) throws Exception {
        ProcessBuilder fProcessBuilder = createFunctionProcessBuilder(port);
        ProcessBuilder gProcessBuilder = createFunctionProcessBuilder(port);

        fProcess = fProcessBuilder.start();
        gProcess = gProcessBuilder.start();
    }

    private ProcessBuilder createFunctionProcessBuilder(int port) {
        ArrayList<String> startOptions = new ArrayList<>();

        //startOptions.addAll(Arrays.asList("cmd", "/c", "start", "cmd", "/k")); // for console in processes

        startOptions.addAll(Arrays.asList("java",
                "-cp", System.getProperty("java.class.path", "."), FunctionProcess.class.getName(),
                Integer.toString(port), "END"));

        return new ProcessBuilder(startOptions);
    }

    public void currentState() {
        if (server == null)
        {
            System.out.println("Functions f, g has not finished");
            return;
        }
        server.printState();
    }

    public boolean isUp() {
        return isUp;
    }

    public void finish() {
        if (isUp && server != null) {
            Logger.log("finishing");
            server.finish();
        }
        isUp = false;
    }
}
