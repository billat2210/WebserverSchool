package com.webserver.servlet;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 将user.dat文件中所有用户信息读取出来并输出到控制台
 */
public class ShowAllUserDemo {
    public static void main(String[] args) throws IOException {
        RandomAccessFile raf=new RandomAccessFile("user.dat","r");
        byte[] data=new byte[32];

        for(int i=0;i<raf.length()/100;i++){
           raf.read(data);
            String username=new String(data,"utf-8").trim();
            raf.read(data);
            String password=new String(data,"utf-8").trim();
            raf.read(data);
            String nickname=new String(data,"utf-8").trim();
            int age=raf.readInt();
            System.out.println(username+","+password+","+nickname+","+age);



        }
        raf.close();
    }
}
