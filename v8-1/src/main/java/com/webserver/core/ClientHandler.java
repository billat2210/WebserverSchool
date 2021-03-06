package com.webserver.core;

import com.webserver.http.EmptyRequestException;
import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

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
            HttpResponse response=new HttpResponse(socket);

            //2处理请求
              String str=  request.getUri();

            File file = new File("./webapps"+str);
            if ((!file.exists())||file.isDirectory()) {
                File notFoundPage = new File("./webapps/root/404.html");

                response.setStatusCode(404);
                response.setStatusReason("NotFound");
                response.putHeader("Content-Type","text/html");
                response.putHeader("Content-Length",file.length()+"");
                response.setEntity(notFoundPage);




            }else {

                response.setEntity(file);


            }
            response.putHeader("server","WebServer");//server头时告知浏览器服务端是谁
            response.flush();
            System.out.println("响应发送完毕!");
        }catch(EmptyRequestException e){

        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            //处理完毕后与客户端断开连接
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
