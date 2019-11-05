package com.nikitosoleil.server;

import com.nikitosoleil.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private int x;
    private int port = 10000;
    private Stack<String> functions;
    private List<FunctionSocket> sockets;
    private ServerSocket serverSocket;
    private volatile Map<String, String> results;
    private boolean isUp = true;
    private Process fProcess, gProcess;
    private boolean wasPrompted = false;
    private int currentMode = 1;

    private long startingTime;

    private final Object notifier = new Object();

    public Server(int x) {
        this.x = x;
        functions = new Stack<>();
        functions.add("f");
        functions.add("g");

        try {
            serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(1337));
            port = serverSocket.getLocalPort();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sockets = new ArrayList<>();
        results = new HashMap<>();
    }

    public void establishConnection() throws Exception {
        // Connect to processes
        while (sockets.size() < 2) {
            Socket s = serverSocket.accept();
            String fName = functions.pop();
            sockets.add(new FunctionSocket(fName, s));

            Logger.log("Socket " + fName + " connected");
        }
        serverSocket.close();
    }

    public void sendFunctionsInfo() throws Exception {
        // Send function and argument info
        for (FunctionSocket functionSocket : sockets) {
            PrintWriter printWriter = new PrintWriter(functionSocket.socket.getOutputStream());
            printWriter.write(functionSocket.name + " " + x + "\n");
            printWriter.flush();

            Logger.log("Function " + functionSocket.name + " info sent");
        }
    }

    public void startListening(Process fProcess, Process gProcess) throws InterruptedException {
        startingTime = System.currentTimeMillis();

        this.fProcess = fProcess;
        this.gProcess = gProcess;

        // Receive info
        List<Thread> threads = new ArrayList<>();
        createListeningThreads(threads);
        if (Main.usePrompts) {
            createPromptTimerThread(threads);
        }


        while (isUp && !analyze(results)) {
            if (currentMode == 3) {
                currentState();
                endProcesses();
                isUp = false;
                continue;
            }

            try {
                synchronized (notifier) {
                    notifier.wait();
                }
                if (wasPrompted) {
                    doPrompt(threads);
                    createPromptTimerThread(threads);
                    wasPrompted = false;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }

        if (isUp) {
            System.out.println("Result " + operation(results));
            System.out.println("Time " + (System.currentTimeMillis() - startingTime) / 1000. + " seconds");
        }

        for (Thread thread : threads) {
            thread.interrupt();
        }
        isUp = false;
    }

    private void doPrompt(List<Thread> threads) {
        System.out.println("Press (1) to continue, (2) to continue without prompts and (3) to cancel");
        Scanner scanner = new Scanner(System.in);
        int action = scanner.nextInt();
        if (action == 3) {
            currentMode = 3;
        } else if (action == 2) {
            currentMode = 2;
            threads.get(2).interrupt();
        }
    }

    private void createPromptTimerThread(List<Thread> threads) {
        Thread promptsThread = new Thread(() -> {
            try {
                if (!wasPrompted) {
                    Thread.sleep(2000);
                    Logger.log("PROMPT");
                    wasPrompted = true;
                    synchronized (notifier) {
                        notifier.notify();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        promptsThread.start();
        threads.add(promptsThread);
    }

    private void createListeningThreads(List<Thread> threads) {
        try {
            for (FunctionSocket functionSocket : sockets) {
                Thread f = new Thread(() -> {
                    Logger.log("Listening " + functionSocket.name);
                    listenSocket(functionSocket);
                });

                f.start();
                threads.add(f);
            }
        } catch (Exception ignored) {
        }
    }

    private void listenSocket(FunctionSocket functionSocket) {

        if (functionSocket.socket.isClosed() || !isUp)
            return;
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(functionSocket.socket.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) == null) ;
            bufferedReader.close();
            functionSocket.socket.close();
            String[] s = line.split(" ");
            String res;
            if (s[0].equals("0"))
                res = "NaN";
            else
                res = s[1];
            synchronized (notifier) {
                results.put(functionSocket.name, res);
                Logger.log("NOTIFY " + Thread.currentThread());
                notifier.notify();
            }
        } catch (IOException ignored) {
        }
    }


    public void currentState() {
        String state = "";
        for (FunctionSocket functionSocket : sockets) {
            if (results.get(functionSocket.name) == null) {
                System.out.println(functionSocket.name + " not finished");
            }
        }
    }

    private boolean analyze(Map<String, String> res) {

        Logger.log("Analyze called");
        return !operation(res).equals("UNFINISHED");
    }

    private String operation(Map<String, String> res) {
        boolean hasNan = false;
        int operationResult = 1;
        for (Map.Entry<String, String> entry : res.entrySet()) {
            if (entry.getValue().equals("NaN"))
                hasNan = true;
            else
                operationResult = operationResult * Integer.parseInt(entry.getValue());
        }
        if (operationResult == 0)
            return "0";
        if (res.size() == 2) {
            if (hasNan)
                return "NaN";
            return Integer.toString(operationResult);
        }
        return "UNFINISHED";
    }

    public int getPort() {
        return port;
    }

    public void finish() {
        try {
            serverSocket.close();
            endProcesses();
            isUp = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void endProcesses() {
        killProcess(fProcess);
        killProcess(gProcess);
    }

    private void killProcess(Process process) {
        if (process == null)
            return;
        if (process.isAlive()) {
            process.destroyForcibly();
        }
    }
}
