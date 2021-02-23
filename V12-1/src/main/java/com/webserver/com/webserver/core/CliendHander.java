package com.webserver.com.webserver.core;

import com.webserver.com.webserver.http.HttpRequest;

import java.net.Socket;

public class CliendHander implements Runnable{
    private Socket socket;
    public CliendHander(Socket socket) {

    }

    @Override
    public void run() {
        //请求行
        HttpRequest request=new HttpRequest(socket);
        //消息头

        //消息正文

    }
}
