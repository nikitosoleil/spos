package com.nikitosoleil;

import java.util.Vector;

public interface SchedulingAlgorithm {
    String REGISTERED =  " registered   ";
    String COMPLETED =   " completed    ";
    String I_O_BLOCKED = " I/O blocked  ";

    Results run(int runtime, Vector<ProcessSimulation> processVector);
}
