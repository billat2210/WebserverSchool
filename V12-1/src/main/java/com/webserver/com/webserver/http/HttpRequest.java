package com.webserver.com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class HttpRequest {
    private Socket socket;

    public HttpRequest(Socket socket) {
        this.socket = socket;
        parseRequestLine();
        parseHeaders();
        parseContext();

    }

    private void parseRequestLine() {
        try {
            String line=readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseHeaders() {
    }

    private void parseContext() {
    }

    private String readLine() throws IOException {
        InputStream in = socket.getInputStream();
        int len;
        char cur = ' ';
        char bur = ' ';
        StringBuilder builder = new StringBuilder();
        byte[] data = new byte[1024 * 10];
        while ((len = in.read(data)) != -1) {
            cur = (char) len;
           if (bur==13&&cur==10){
               break;
           }
            builder.append(cur);
            bur=cur;
        }

        return builder.toString().trim();
    }

}