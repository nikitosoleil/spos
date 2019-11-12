package com.nikitosoleil.server;

import com.nikitosoleil.Logger;

import java.util.HashMap;
import java.util.Map;

public class State {

    private Map<String, String> results;
    private boolean isUp = true;
    private final Object mutex = new Object();

    public State() {
        results = new HashMap<>();
    }

    public void interrupt() {
        synchronized (mutex) {
            isUp = false;
        }
    }

    public boolean computed() {
        synchronized (mutex) {
            return isUp;
        }
    }

    public boolean waiting() {
        synchronized (mutex) {
            return isUp && !analyze();
        }
    }

    private boolean analyze() {

        Logger.log("Analyze called");
        return !operation().equals("UNFINISHED");
    }

    private String operation() {
        boolean hasNan = false;
        int operationResult = 1;
        for (Map.Entry<String, String> entry : results.entrySet()) {
            if (entry.getValue().equals("NaN"))
                hasNan = true;
            else
                operationResult = operationResult * Integer.parseInt(entry.getValue());
        }
        if (operationResult == 0)
            return "0";
        if (results.size() == 2) {
            if (hasNan)
                return "NaN";
            return Integer.toString(operationResult);
        }
        return "UNFINISHED";
    }

    public String result() {
        synchronized (mutex) {
            return operation();
        }
    }

    public void submit(String name, String result) {
        synchronized (mutex) {
            results.put(name, result);
        }
    }

    public void printState() {
        for (String name : results.keySet()) {
            System.out.println(name + " finished");
        }
    }
}
