package com.nikitosoleil;

public class sProcess {
    private int cputime;
    private int ioblocking;
    private int cpudone;
    private int ionext;
    private int numblocked;

    public sProcess(int cputime, int ioblocking, int cpudone, int ionext, int numblocked) {
        this.setCputime(cputime);
        this.setIoblocking(ioblocking);
        this.setCpudone(cpudone);
        this.setIonext(ionext);
        this.setNumblocked(numblocked);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(getCputime());
        if (getCputime() < 100) {
            res.append(" (ms)\t\t");
        } else {
            res.append(" (ms)\t");
        }
        res.append(getIoblocking());
        if (getIoblocking() < 100) {
            res.append(" (ms)\t\t");
        } else {
            res.append(" (ms)\t");
        }
        res.append(getCpudone());
        if (getCpudone() < 100) {
            res.append(" (ms)\t\t");
        } else {
            res.append(" (ms)\t");
        }
        res.append(getNumblocked()).append(" times");

        return res.toString();
    }

    public int getCputime() {
        return cputime;
    }

    public void setCputime(int cputime) {
        this.cputime = cputime;
    }

    public int getIoblocking() {
        return ioblocking;
    }

    public void setIoblocking(int ioblocking) {
        this.ioblocking = ioblocking;
    }

    public int getCpudone() {
        return cpudone;
    }

    public void setCpudone(int cpudone) {
        this.cpudone = cpudone;
    }

    public int getIonext() {
        return ionext;
    }

    public void setIonext(int ionext) {
        this.ionext = ionext;
    }

    public int getNumblocked() {
        return numblocked;
    }

    public void setNumblocked(int numblocked) {
        this.numblocked = numblocked;
    }
}
