package com.nikitosoleil;

import java.text.DecimalFormat;
import java.util.Objects;

public class ProcessSimulation implements Comparable<ProcessSimulation> {
    private int PID;
    private int cpuTime;
    private int blockAfter;
    private int timeInBlock;
    private int cpuDone;
    private int lastTimeBlockedGlobal;
    private int lastTimeBlockedLocal;
    private int numBlocked;
    private double ratio;

    public ProcessSimulation(int cpuTime, int blockAfter, int cpuDone, int lastTimeBlockedGlobal, int numBlocked, int timeInBlock) {
        this.setCpuTime(cpuTime);
        this.setBlockAfter(blockAfter);
        this.setCpuDone(cpuDone);
        this.setLastTimeBlockedGlobal(lastTimeBlockedGlobal);
        this.setNumBlocked(numBlocked);
        this.setTimeInBlock(timeInBlock);
        this.lastTimeBlockedLocal = 0;
        this.PID = 0;
        ratio = 0.0;
    }

    public boolean isAvailable(int globalTime) {
        if (lastTimeBlockedGlobal == 0)
            return true;
        return lastTimeBlockedGlobal + timeInBlock <= globalTime;
    }

    public boolean isDone() {
        return cpuDone >= cpuTime;
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

    private static DecimalFormat df3 = new DecimalFormat("#.###");

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(format(getPID()));
        res.append(format(getCpuTime()));
        res.append(format(getBlockAfter()));
        res.append(format(getTimeInBlock()));
        res.append(format(getCpuDone()));
        res.append(format(getNumBlocked() + " times "));
        String state;
        if (isDone())
            state = "Done";
        else
            state = "Not done";
        res.append(format(state));
        res.append(format(df3.format(ratio)));

        return res.toString();
    }

    public int getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(int cpuTime) {
        this.cpuTime = cpuTime;
    }

    public int getBlockAfter() {
        return blockAfter;
    }

    public void setBlockAfter(int blockAfter) {
        this.blockAfter = blockAfter;
    }

    public int getCpuDone() {
        return cpuDone;
    }

    public void setCpuDone(int cpuDone) {
        this.cpuDone = cpuDone;
    }

    public int getLastTimeBlockedGlobal() {
        return lastTimeBlockedGlobal;
    }

    public void setLastTimeBlockedGlobal(int lastTimeBlockedGlobal) {
        this.lastTimeBlockedGlobal = lastTimeBlockedGlobal;
    }

    public int getNumBlocked() {
        return numBlocked;
    }

    public void setNumBlocked(int numBlocked) {
        this.numBlocked = numBlocked;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProcessSimulation))
            return false;
        if (o.hashCode() == this.hashCode())
            return true;
        return this.compareTo((ProcessSimulation) (o)) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpuTime, blockAfter, cpuDone, lastTimeBlockedGlobal, numBlocked, ratio);
    }

    @Override
    public int compareTo(ProcessSimulation other) {
        if (ratio < other.ratio)
            return -1;
        else return 1;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public int getTimeInBlock() {
        return timeInBlock;
    }

    public void setTimeInBlock(int timeInBlock) {
        this.timeInBlock = timeInBlock;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public int getLastTimeBlockedLocal() {
        return lastTimeBlockedLocal;
    }

    public void setLastTimeBlockedLocal(int lastTimeBlockedLocal) {
        this.lastTimeBlockedLocal = lastTimeBlockedLocal;
    }
}
