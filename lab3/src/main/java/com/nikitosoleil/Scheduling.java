package com.nikitosoleil;
// This file contains the main() function for the Scheduling
// simulation.  Init() initializes most of the variables by
// reading from a provided file.  SchedulingAlgorithm.Run() is
// called from main() to run the simulation.  Summary-Results
// is where the summary results are written, and Summary-Processes
// is where the process scheduling summary is written.

// Created by Alexander Reeder, 2001 January 06

import javax.xml.validation.Validator;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Scheduling {
    public static int DEFAULT_TIME_IN_BLOCK = 50;
    private static int processNum;
    private static int meanDev;
    private static int standardDev;
    private static int runTime;
    private static Vector<ProcessSimulation> processVector = new Vector<>();
    private static Results result;
    private static String resultsFile = "Summary-Results";

    public static void main(String[] args) {
        canBeRead(args);
        init(args[0]);
        if (processVector.size() < processNum) {
            addProcessesUpToProcessNum();
        }
        setPIDs();
        SchedulingAlgorithm algorithm = new GuaranteedSchedulingAlgorithm();

        Logger.getLogger("main").log(Level.INFO, "Working...");
        result = algorithm.run(runTime, processVector);
        printResultsToFile();
        Logger.getLogger("main").log(Level.INFO, "Completed");
    }

    private static void setPIDs() {
        for (int i = 0; i < processVector.size(); i++) {
            processVector.get(i).setPID(i);
        }
    }

    private static String format(String str) {
        int len = 16;
        StringBuilder strBuilder = new StringBuilder(str);
        while (strBuilder.length() < len)
            strBuilder.append(" ");
        str = strBuilder.toString();
        return str;
    }

    private static String format(Integer val) {
        return format(Integer.toString(val));
    }

    private static void printResultsToFile() {
        try {
            File resFile = new File("res/" + resultsFile);
            resFile.createNewFile();
            PrintStream out = new PrintStream(resFile);

            out.println("Scheduling Type: " + result.schedulingType);
            out.println("Scheduling Name: " + result.schedulingName);
            out.println("Simulation Available Time: " + runTime);
            out.println("Simulation Run Time: " + result.compuTime);
            out.println("Mean: " + meanDev);
            out.println("Standard Deviation: " + standardDev);

            out.print(format("Process"));
            out.print(format("CPU Time"));
            out.print(format("Block after"));
            out.print(format("Time in block"));
            out.print(format("CPU Completed"));
            out.print(format("CPU Blocked"));
            out.print(format("Final state"));
            out.print(format("Last ratio"));
            out.println();
            for (int i = 0; i < processVector.size(); i++) {
                ProcessSimulation process = processVector.elementAt(i);
                out.println(process);
            }

            out.println();
            out.println();
            out.println("Total CPU Needed " + totalCpuNeeded(processVector));
            out.close();
        } catch (IOException e) { /* Handle exceptions */ }
    }

    private static void addProcessesUpToProcessNum() {
        int i = 0;
        while (processVector.size() < processNum) {
            double X = Common.R1();
            while (X == -1.0) {
                X = Common.R1();
            }
            X = X * standardDev;
            int cputime = (int) X + meanDev;
            processVector.addElement(new ProcessSimulation(cputime, i * 100, 0, -10000000, 0, DEFAULT_TIME_IN_BLOCK));
            i++;
        }
    }

    private static int totalCpuNeeded(Vector<ProcessSimulation> processSimulations) {
        int res = 0;
        for (ProcessSimulation processSimulation : processSimulations) {
            res += processSimulation.getCpuTime();
        }
        return res;
    }

    private static void init(String fileName) {
        File f = new File(fileName);
        try {
            initFromFile(f);
        } catch (IOException e) { /* Handle exceptions */ }
    }

    private static void initFromFile(File f) throws IOException {
        String line;
        FileInputStream fileStream = new FileInputStream(f);
        InputStreamReader inputStreamReader = new InputStreamReader(fileStream);
        BufferedReader in = new BufferedReader(inputStreamReader);
        while ((line = in.readLine()) != null) {
            if (line.startsWith("numprocess")) {
                StringTokenizer st = new StringTokenizer(line);
                st.nextToken();
                processNum = Common.s2i(st.nextToken());
            }
            if (line.startsWith("meandev")) {
                StringTokenizer st = new StringTokenizer(line);
                st.nextToken();
                meanDev = Common.s2i(st.nextToken());
            }
            if (line.startsWith("standdev")) {
                StringTokenizer st = new StringTokenizer(line);
                st.nextToken();
                standardDev = Common.s2i(st.nextToken());
            }
            if (line.startsWith("process")) {
                ProcessSimulation newProcess = generateProcessFromLine(line);
                processVector.addElement(newProcess);
            }
            if (line.startsWith("runtime")) {
                StringTokenizer st = new StringTokenizer(line);
                st.nextToken();
                runTime = Common.s2i(st.nextToken());
            }
        }
        in.close();
    }

    private static ProcessSimulation generateProcessFromLine(String line) {
        int ioblocking;
        double X;
        int cputime;
        StringTokenizer st = new StringTokenizer(line);
        st.nextToken();
        ioblocking = Common.s2i(st.nextToken());
        X = Common.R1();
        while (X == -1.0) {
            X = Common.R1();
        }
        X = X * standardDev;
        cputime = (int) X + meanDev;
        int timeInBlock = Integer.parseInt(st.nextToken());
        return new ProcessSimulation(cputime, ioblocking, 0, 0, 0, timeInBlock);
    }


    private static void canBeRead(String[] args) {
        if (args.length != 1) {
            Logger.getLogger("main").
                    log(Level.WARNING, "Usage: 'java Scheduling <INIT FILE>'");
            System.exit(-1);
        }
        File f = new File(args[0]);
        if (!(f.exists())) {
            Logger.getLogger("main").
                    log(Level.WARNING, "Scheduling: error, file '" + f.getName() + "' does not exist.");
            System.exit(-1);
        }
        if (!(f.canRead())) {
            Logger.getLogger("main").
                    log(Level.WARNING, "Scheduling: error, read of " + f.getName() + " failed.");
            System.exit(-1);
        }
    }
}

