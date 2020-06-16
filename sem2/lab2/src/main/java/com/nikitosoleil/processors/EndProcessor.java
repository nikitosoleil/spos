package com.nikitosoleil.processors;

import com.nikitosoleil.Buff;

public abstract class EndProcessor implements Processor {
    protected Buff bf;
    public EndProcessor(Buff bf) {
        this.bf = bf;
    }
}