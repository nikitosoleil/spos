package com.nikitosoleil.client;

import spos.lab1.demo.IntOps;

public class G implements Function {
    @Override
    public int run(int x) throws Exception {
        return IntOps.funcG(x);
    }
}
