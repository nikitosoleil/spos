package com.nikitosoleil.client;

import java.io.*;
import java.net.Socket;

public class FunctionProcess {
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        try {
            Socket socket = new Socket("localhost", port);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String info;
            while ((info = bufferedReader.readLine()) == null) ;
            System.out.println(info);
            String[] mas = info.split(" ");
            Function function;
            if (mas[0].equals("f"))
                function = new F();
            else
                function = new G();

            System.out.println(function);
            int x = Integer.parseInt(mas[1]);

            String message = "";

            try {
                int res = function.run(x);
                message = "1 " + res;
            } catch (Exception e) {
                message = "0 0";
            }
            System.out.println("RES: " + message);
            while (!socket.isClosed()) {
                Thread.sleep(10);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedWriter.write(message + "\n");
                bufferedWriter.flush();
            }

            System.out.println("FIN");
        } catch (Exception ignored) {
        }
    }
}
