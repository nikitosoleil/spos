package com.nikitosoleil;

import java.util.Vector;

public interface SchedulingAlgorithm {
    String RUNNING = " running      ";
    String READY =     " ready        ";
    String COMPLETED = " completed    ";
    String I_O_BLOCKED = " I/O blocked  ";

    Results run(int runtime, int period, Vector<ProcessSimulation> processVector);
}
