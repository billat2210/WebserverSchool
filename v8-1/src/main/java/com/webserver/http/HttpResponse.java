package com.webserver.http;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private  int statusCode=200;//状态代码默认值200，因为绝大多数请求实际应用中都能正确处理
    private  String statusReason="ok";
    private File entity;
    private Socket socket;
    private String key;
    private String value;
    private String str;
    private Map<String ,String >headlers=new HashMap<>();
    public HttpResponse(Socket socket) {
        this.socket=socket;
    }
    public void flush(){
        setStatusLine();
        setHeaders();
        setContent();
    }
    private void setStatusLine(){



        try {
            String line = "HTTP/1.1 "+statusCode+" "+statusReason;
            println(line);
        } catch (IOException e) {
            e.fillInStackTrace();
        }


    }
    private void setHeaders(){







        try {

            headlers.forEach(

                    (k,v)->{
                        String line=k+": "+v;
                        System.out.println("响应头："+line);
                        try {
                            println(line);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
            println("");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void setContent(){

        try( FileInputStream fis  = new FileInputStream(entity);
        ) {

            OutputStream out=socket.getOutputStream();
            int len;//每次读取的字节数
            byte[] buf = new byte[1024*10];//10kb字节数组
            while((len = fis.read(buf))!=-1){
                out.write(buf,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void println(String line) throws IOException {



            OutputStream out=socket.getOutputStream();

           byte[] data = line.getBytes("ISO8859-1");
            out.write(data);
            out.write(13);//单独发送回车符
            out.write(10);//单独发送换行符



    }
    public void putHeader(String key,String value){
        this.key=key;
        this.value=value;
        headlers.put(key,value);
    }



    public File getEntity() {
        return entity;
    }

    public void setEntity(File entity) {
        this.entity = entity;
        String [] str1=entity.getName().split("\\.");
        str=str1[str1.length-1];
        //String str1=file.getName().substring(file.getName().lastIndexOf(".")+1);
        String type= HttpContext.getMimetype(str);

        putHeader("Content-Type",type);
        putHeader("Content-Length",entity.length()+"");
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

}
