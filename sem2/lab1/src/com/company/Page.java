package com.company;

public class Page {
    public int id;
    public int physical;
    public byte R;
    public byte M;
    public int inMemTime;
    public int sinceTouchTime;
    public long high;
    public long low;

    public Page(int id, int physical, byte R, byte M, int inMemTime, int sinceTouchTime, long high, long low) {
        this.id = id;
        this.physical = physical;
        this.R = R;
        this.M = M;
        this.inMemTime = inMemTime;
        this.sinceTouchTime = sinceTouchTime;
        this.high = high;
        this.low = low;
    }

}
