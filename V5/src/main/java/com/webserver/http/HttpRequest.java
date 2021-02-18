package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求对象
 * 该类的每一个实例用于表示客户端发送过来的一个HTTP请求内容
 * 每个请求由三部分构成:
 * 请求行，消息头，消息正文
 */
public class HttpRequest {
    //请求行相关信息
    private String method;//请求方式
    private String uri;//抽象路径
    private String protocol;//协议版本

    //消息头相关信息
    private Map<String,String> headers = new HashMap<>();

    //消息正文相关信息

    private Socket socket;
    /**
     * HttpRequest的实例化过程就是解析请求的过程
     * @param socket
     */
    public HttpRequest(Socket socket){
        this.socket = socket;

    }
    //解析一个请求的三步骤:
    //1:解析请求行
    private void parseRequestLine(){
        System.out.println("HttpRequest:开始解析请求行...");

        System.out.println("HttpRequest:请求行解析完毕!");
    }
    //2:解析消息头
    private void parseHeaders(){
        System.out.println("HttpRequest:开始解析消息头...");

        System.out.println("HttpRequest:消息头解析完毕!");
    }
    //3:解析消息正文
    private void parseContent(){
        System.out.println("HttpRequest:开始解析消息正文...");

        System.out.println("HttpRequest:消息正文解析完毕!");
    }


    private String readLine() throws IOException {
        /*
            当socket对象相同时，无论调用多少次getInputStream方法，获取回来的输入流
            总是同一个流。输出流也是一样的。
         */
        InputStream in = socket.getInputStream();
        int d;
        char cur=' ';//表示本次读取到的字符
        char pre=' ';//表示上次读取到的字符
        StringBuilder builder = new StringBuilder();//保存读取到的所有字符
        while ((d = in.read())!=-1){
            cur = (char)d;//本次读取到的字符
            //如果上次读取的是回车符，本次读取的是换行符则停止读取
            if(pre==13 && cur==10){
                break;
            }
            builder.append(cur);
            pre = cur;
        }
        return builder.toString().trim();
    }
}







