package com.nikitosoleil;// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.Vector;
import java.io.*;

public class SchedulingAlgorithm {

    private static final String REGISTERED = " registered... (";
    private static final String COMPLETED = " completed... (";
    private static final String I_O_BLOCKED = " I/O blocked... (";

    public static Results run(int runtime, Vector<sProcess> processVector, Results result) {
        int i = 0;
        int comptime = 0;
        int currentProcess = 0;
        int previousProcess = 0;
        int size = processVector.size();
        int completed = 0;
        String resultsFile = "Summary-Processes";

        result.schedulingType = "Group share";
        result.schedulingName = "Fair share";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
            sProcess process = processVector.elementAt(currentProcess);
            logState(currentProcess, out, process, REGISTERED);
            while (comptime < runtime) {
                if (process.getCpudone() == process.getCputime()) {
                    completed++;
                    logState(currentProcess, out, process, COMPLETED);
                    if (completed == size) {
                        result.compuTime = comptime;
                        out.close();
                        return result;
                    }
                    for (i = size - 1; i >= 0; i--) {
                        process = (sProcess) processVector.elementAt(i);
                        if (process.getCpudone() < process.getCputime()) {
                            currentProcess = i;
                        }
                    }
                    process = (sProcess) processVector.elementAt(currentProcess);
                    logState(currentProcess, out, process, REGISTERED);
                }
                if (process.getIoblocking() == process.getIonext()) {
                    logState(currentProcess, out, process, I_O_BLOCKED);
                    process.setNumblocked(process.getNumblocked() + 1);
                    process.setIonext(0);
                    previousProcess = currentProcess;
                    for (i = size - 1; i >= 0; i--) {
                        process = (sProcess) processVector.elementAt(i);
                        if (process.getCpudone() < process.getCputime() && previousProcess != i) {
                            currentProcess = i;
                        }
                    }
                    process = (sProcess) processVector.elementAt(currentProcess);
                    logState(currentProcess, out, process, REGISTERED);
                }
                process.setCpudone(process.getCpudone() + 1);
                if (process.getIoblocking() > 0) {
                    process.setIonext(process.getIonext() + 1);
                }
                comptime++;
            }
            out.close();
        } catch (IOException e) { /* Handle exceptions */ }
        result.compuTime = comptime;
        return result;
    }

    private static void logState(int currentProcess, PrintStream out, sProcess process, String state) {
        out.println("Process: " + currentProcess + state +
                process.getCputime() + " " + process.getIoblocking() + " " + process.getCpudone() + " " + process.getCpudone() + ")");
    }
}
