package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import org.dom4j.CDATA;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class Loginservlet extends Httpservlet{

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {

    }

    public void doPost(HttpRequest request, HttpResponse response){
        String name=request.getParameter("username");
        String password=request.getParameter("password");
        if(name==null||password==null){
            File file=new File("./webapps/myweb/login_fail.html");
            response.setEntity(file);
            return;
        }
        try ( RandomAccessFile raf=new RandomAccessFile("user.dat","rw");
        ){


            for (int i=0;i<raf.length()/100;i++){
                raf.seek(i*100);
                byte [] data =new byte[32];
                boolean login=false;
                raf.read(data);
                String len=new String (data,"utf-8").trim();
                if (len.equals(name) ){
                        raf.read(data);
                        String len1=new String (data,"utf-8").trim();
                        if (len1.equals(password)){
                            File file =new File ("./webapps/myweb/login_success.html");
                            response.setEntity(file);
                            break;
                        }else {
                            File file = new File("./webapps/myweb/login_fail.html");
                            response.setEntity(file);


                        }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
