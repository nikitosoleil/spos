package com.nikitosoleil.server;

import java.net.Socket;

public class FunctionSocket {
    public FunctionSocket(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }

    public final Socket socket;
    public final String name;
}
