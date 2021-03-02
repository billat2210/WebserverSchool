package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import com.webserver.vo.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class AmendServlet  extends Httpservlet{


    public void doGet(HttpRequest request, HttpResponse response){
                 String username=  request.getParameter("username");
        System.out.println(username);
        try(
                RandomAccessFile raf=new RandomAccessFile("user.dat","r");


                ){
                        for(int i=0;i<raf.length()/100;i++) {
                            raf.seek(i * 100);
                            byte[] data = new byte[32];

                            raf.read(data);
                            String name = new String(data, "UTF-8").trim();
                            if (name.equals(username)){
                                System.out.println("1111111-");
                                raf.read(data);
                                String password = new String(data, "UTF-8").trim();

                                raf.read(data);
                                String nickname = new String(data, "UTF-8").trim();

                                int age = raf.readInt();
                                response.setContentType("text/html");

                                User user = new User(name, password, nickname, age);



                                Context context = new Context();
                                context.setVariable("user", user);
                                FileTemplateResolver resolver = new FileTemplateResolver();
                                resolver.setTemplateMode("html");
                                resolver.setCharacterEncoding("utf-8");
                                TemplateEngine te = new TemplateEngine();
                                te.setTemplateResolver(resolver);

                                String html = te.process("./webapps/myweb/update.html", context);
                                PrintWriter pw = response.getWriter();
                                pw.println(html);


                                break;
                            }
                        }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        System.out.println("ToUpdateServlet:开始处理修改用户....");
        System.out.println("ToUpdateServlet:修改用户处理完毕");

    }
}
