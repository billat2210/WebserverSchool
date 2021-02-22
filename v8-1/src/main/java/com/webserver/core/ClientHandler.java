package com.webserver.core;

import com.webserver.http.HttpRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 *  负责与指定客户端进行HTTP交互
 *  HTTP协议要求与客户端的交互规则采取一问一答的方式。因此，处理客户端交互以3步形式完成:
 *  1:解析请求(一问)
 *  2:处理请求
 *  3:发送响应(一答)
 */
public class ClientHandler implements Runnable{
    private Socket socket;
    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    public void run() {
        try{
            //1解析请求
            HttpRequest request = new HttpRequest(socket);

            //2处理请求
              String str=  request.getUri();

            File file = new File("./webapps"+str);
            if ((!file.exists())||file.isDirectory()) {
                file = new File("./webapps/root/404.html");
                OutputStream out = socket.getOutputStream();
                String line = "HTTP/1.1 404 NotFound";
                byte[] data = line.getBytes("ISO8859-1");
                out.write(data);
                out.write(13);
                out.write(10);
                line = "Content-type: text/html";
                data = line.getBytes("ISO8859-1");
                    out.write(data);
                    out.write(13);
                    out.write(10);

                    line="content-Length"+file.length();
                    data=line.getBytes("ISO8859-1");
                    out.write(data);
                    out.write(13);
                    out.write(10);
                    out.write(13);
                    out.write(10);

                    FileInputStream fis=new FileInputStream(file);
                    int len;
                    byte[]bur=new byte[1024*10];
                    while((len=fis.read(bur))!=-1){
                        out.write(bur,0,len);

                    }




            }else {
        //3发送响应
            //先发送一个固定的页面，测试浏览器是否可以正常接收

            /*
                一个响应的大致内容:
                HTTP/1.1 200 OK(CRLF)
                Content-Type: text/html(CRLF)
                Content-Length: 2546(CRLF)(CRLF)
                1011101010101010101......
             */
            OutputStream out = socket.getOutputStream();
            //1:发送状态行
            String line = "HTTP/1.1 200 OK";
            byte[] data = line.getBytes("ISO8859-1");
            out.write(data);
            out.write(13);//单独发送回车符
            out.write(10);//单独发送换行符

            //2:发送响应头
            line = "Content-Type: text/html";
            data = line.getBytes("ISO8859-1");
            out.write(data);
            out.write(13);//单独发送回车符
            out.write(10);//单独发送换行符

            line = "Content-Length: " + file.length();
            data = line.getBytes("ISO8859-1");
            out.write(data);
            out.write(13);//单独发送回车符
            out.write(10);//单独发送换行符

            //单独发送CRLF表示响应头部分发送完毕!
            out.write(13);//单独发送回车符
            out.write(10);//单独发送换行符

            //3:发送响应正文(文件内容)
            //创建文件输入流读取要发送的文件数据
            FileInputStream fis = new FileInputStream(file);
            int len;//每次读取的字节数
            byte[] buf = new byte[1024*10];//10kb字节数组
            while((len = fis.read(buf))!=-1){
                out.write(buf,0,len);
            }
            System.out.println("响应发送完毕!");}
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //处理完毕后与客户端断开连接
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
