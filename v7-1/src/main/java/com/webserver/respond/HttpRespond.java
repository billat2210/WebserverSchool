package com.webserver.respond;

import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRespond {
    private File file;

    private OutputStream out;
    private Socket socket;



    public HttpRespond(File file ,Socket socket) {
        this.file=file;
        this.socket=socket;




    }
    public void flush(){
        gettype();
        getLength();
        getbody();
    }
    public void gettype(){
        try {
            System.out.println("gettype输出");
            OutputStream out = socket.getOutputStream();
            //1:发送状态行
            String line = "HTTP/1.1 200 OK";
            byte[] data = line.getBytes("ISO8859-1");
            out.write(data);
            writebody(out);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getLength(){
       try {
           System.out.println("getlength输出");
         String  line = "Content-Type: text/html";
          byte[] data = line.getBytes("ISO8859-1");
           out = socket.getOutputStream();
           out.write(data);
           writebody(out);
           writebody(out);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void getbody(){

        try {
            System.out.println("getbody输出");
            FileInputStream fis  = new FileInputStream(file);
            int len;//每次读取的字节数
            byte[] buf = new byte[1024*10];//10kb字节数组
            while((len = fis.read(buf))!=-1){
                out.write(buf,0,len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void writebody(OutputStream out){
        try {
            this.out=out;
            out.write(13);
           out.write(10);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void getwrongtype(){

        try {
            file = new File("./webapps/root/404.html");
            OutputStream out = socket.getOutputStream();
            String line = "HTTP/1.1 404 NotFound";
            byte[] data = line.getBytes("ISO8859-1");
            out.write(data);
           writebody(out);
            getLength();
            writebody(out);
            writebody(out);
            getbody();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
