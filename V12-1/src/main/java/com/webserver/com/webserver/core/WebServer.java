package com.webserver.com.webserver.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private ServerSocket serverSocket;
    public WebServer() {

        try {
            System.out.println("服务器启动了...");
            serverSocket=new ServerSocket(9000);
            System.out.println("服务器启动完毕");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void start(){

        try {
            System.out.println("等待客户端连接");
            Socket socket=serverSocket.accept();
            System.out.println("一个客户端连接了");
            CliendHander hander=new CliendHander(socket);
            Thread t=new Thread(hander);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        WebServer webServer=new WebServer();
        webServer.start();
    }
}
