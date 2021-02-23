package com.webserver.http;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 响应对象，当前类的每一个实力用于表示给客户端发送的一个HTTP响应
 * 每个响应由三部分构成
 * 状态行，响应头，响应正文（正文部分可以没有）
 */
public class HttpResponse {
    //状态行相关信息
private  int statusCode=200;//状态代码默认值200，因为绝大多数请求实际应用中都能正确处理
private  String statusReason="ok";
    //响应头相关信息
    private String key;
     private String value;
    private String str;


   private Map<String, String> header=new HashMap<String, String>();

    //响应正文相关信息


    private Socket socket;
    private File entity;//响应正文对应的实体文件


    public HttpResponse(Socket socket) {
        this.socket = socket;

    }

    /**
     * 将当前响应对象内容以标准的HTTP响应格式发送给客户端
     */
    public void flush(){
        /*
            发送一个响应时，按顺序发送状态行，响应头，响应正文
         */
        sendStatusLine();
        sendHeaders();
        sendContent();
    }
    //发送衣蛾响应的三个步骤
    //1：发送状态行
    private void sendStatusLine(){
        System.out.println("开始发送状态行....");

            try {

                String line = "HTTP/1.1"+" "+statusCode+" "+statusReason;
                System.out.println("状态行："+line);
               println(line);

            }catch (IOException e){
                e.fillInStackTrace();
            }
        System.out.println("状态行发送完毕！");
    }
    //1：发送响应头
    private void sendHeaders(){
        System.out.println("开始发送响应头....");

        try {
//            Set<String > set=header.keySet();
//            for(String str: set){
//
//                System.out.println(str+": "+header.get(key));
//
//            }

//                Set<Map.Entry<String ,String>> set=header.entrySet();
//                for (Map.Entry<String ,String> e:set){
//                    String key=e.getKey();
//                    String value=e.getValue();
//                    String line=key+":"+value;
//                    System.out.println("响应头："+line);
//                    println(line);
//
//                }
            //JDK8之后Map也支持foreach，使用lambda表达式遍历
            header.forEach(
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

            //单独发送CRLF表示响应头部分发送完毕!
           println("");//空字符串  println方法中的data长度为0，只发送了一个回车符和一个换行符
        }catch (IOException e){
            e.fillInStackTrace();
        }
        System.out.println("响应头发送完毕！");
    }
    //1：发送响应正文
    private void sendContent(){
        System.out.println("开始发送响应正文....");

        try(
                FileInputStream fis = new FileInputStream(entity);
        ) {
            OutputStream out = socket.getOutputStream();

            int len;//每次读取的字节数
            byte[] buf = new byte[1024*10];//10kb字节数组
            while((len = fis.read(buf))!=-1){
                out.write(buf,0,len);
            }
        }catch (IOException e){
                e.fillInStackTrace();
        }
        System.out.println("响应正文发送完毕！");
    }
    public void println(String line) throws IOException {
        OutputStream out=socket.getOutputStream();
        byte []data = line.getBytes("ISO8859-1");
        out.write(data);
        out.write(13);//单独发送回车符
        out.write(10);//单独发送换行符
    }

    /**
     * 添加一个响应头
     * @param key
     * @param value
     */
    public void putHeader(String key,String value){
        this.key=key;
        this.value=value;

        header.put(key,value);



    }

    public File getEntity() {
        return entity;
    }

    public void setEntity(File entity) {
        this.entity = entity;
        String [] str1=entity.getName().split("\\.");
        str=str1[str1.length-1];
        //String str1=file.getName().substring(file.getName().lastIndexOf(".")+1);
        String type= HttpContext.getMimeType(str);

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
