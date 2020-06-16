package com.nikitosoleil.processors;

import com.nikitosoleil.Token;

import java.io.IOException;

public interface Processor {
    Token process(String value) throws IOException;
}
