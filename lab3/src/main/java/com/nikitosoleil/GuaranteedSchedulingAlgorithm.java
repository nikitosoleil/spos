package com.nikitosoleil;

// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.text.DecimalFormat;
import java.util.Vector;
import java.io.*;

public class GuaranteedSchedulingAlgorithm implements SchedulingAlgorithm {
    private AutoSortedList<ProcessSimulation> processes;
    private PrintStream out;
    private int comptime = 0;
    private int completedCount = 0;
    private int runtime;

    public GuaranteedSchedulingAlgorithm() { // Further initialisation via run method
    }

    @Override
    public Results run(int runtime, Vector<ProcessSimulation> processVector) {
        processes = new AutoSortedList<>(processVector);
        Results result = new Results("Interactive", "Guaranteed", 0);
        this.runtime = runtime;

        String resultsFile = "Summary-Processes";

        try {
            File procFile = new File("res/" + resultsFile);
            procFile.createNewFile();
            FileOutputStream procOut = new FileOutputStream(procFile, false);
            out = new PrintStream(procOut);

            result.compuTime = guaranteedImplementation();

            procOut.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Returns total used CPU time
    private int guaranteedImplementation() {
        ProcessSimulation prev = null;

        while (comptime < runtime && !processes.isEmpty()) {
            ProcessSimulation proc = nextAvailable(comptime);
            if (proc != null)// && !proc.equals(prev))
                logState(out, proc, REGISTERED);
            if (proc != null) {
                proc.setCpuDone(proc.getCpuDone() + 1);
                if (proc.getCpuDone() - proc.getLastTimeBlockedLocal() >= proc.getBlockAfter()) {
                    proc.setLastTimeBlockedGlobal(comptime);
                    proc.setLastTimeBlockedLocal(proc.getCpuDone());

                    proc.setNumBlocked(proc.getNumBlocked() + 1);
                    logState(out, proc, I_O_BLOCKED);
                } else {
                    logState(out, proc, READY);
                }

                if (proc.isDone()) {
                    completedCount++;
                    logState(out, proc, COMPLETED);
                    processes.remove(proc);
                }
            }

            if (processes.isEmpty()) {
                logFinished(out);
                return comptime;
            }
            comptime++;
            prev = proc;
            updateRatios();
        }
        if (processes.isEmpty()) {
            logFinished(out);
        }
        return comptime;
    }

    private void updateRatios() {
        double eachProcessShouldReceive = (double) (comptime) / (double) (processes.size());
        for (ProcessSimulation process : processes) {
            process.setRatio((double) (process.getCpuDone()) / eachProcessShouldReceive);
        }
        processes.resort();
    }

    private ProcessSimulation nextAvailable(int currentTime) {
        for (ProcessSimulation process : processes) {
            if (process.isAvailable(currentTime))
                return process;
        }
        return null;
    }

    private static DecimalFormat df3 = new DecimalFormat("#.###");

    private static String format(String str) {
        int len = 12;
        StringBuilder strBuilder = new StringBuilder(str);
        while (strBuilder.length() < len)
            strBuilder.append(" ");
        str = strBuilder.toString();
        return str;
    }

    private static String format(Integer val) {
        return format(Integer.toString(val));
    }

    private static void logState(PrintStream out, ProcessSimulation process, String state) {
        out.println("Process: "
                + format(process.getPID())
                + format(state)
                + format(process.getCpuTime())
                + format(process.getBlockAfter())
                + format(process.getCpuDone())
                + format(df3.format(process.getRatio())));
    }

    private static void logFinished(PrintStream out) {
        out.println("All processes finished");
    }
}
