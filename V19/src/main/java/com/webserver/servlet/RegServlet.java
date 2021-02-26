package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * Servlet是JAVAEE标准中的一个借口，意思是运行在服务端的小程序
 * 我们用它来处理某个具体的请求
 *
 * 当前Servlet用于处理用户注册业务
 */
public class RegServlet extends Httpservlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {

    }

    public void doPost(HttpRequest request, HttpResponse response){
        System.out.println("RegServlet:开始处理用户注册..");
        /*
            1:通过request获取用户在注册页面上输入的注册信息（表单上的信息）
            2：将用户的注册信息写入文件user.dat中
            3：设置response给客户端响应注册结果页面
         */
        //1  注意，这里getParameter方法传入的参数要与注册页面上对应输入框的name属性的值一致！
        String username=request.getParameter("Username");
        String password=request.getParameter("password");
        String nickname=request.getParameter("nikename");
        String ageStr=request.getParameter("age");
        /*
            必要的验证工作，如果上述四项有空的，或者年龄不是一个数字时，直接响应给客户端一个注册错误的提示页面:
            reg_info_error.html,里面居中显示一行字：注册信息输入有误，请重新注册
            注：该页面也放在webapps/myweb这个网络应用中。
         */if (username==null||password==null||nickname==null||ageStr==null||!ageStr.matches("[0-9]+")) {
            System.out.println("username"+username);
            System.out.println("password"+password);
            System.out.println("nickname"+nickname);
            System.out.println("ageStr"+ageStr);
            File file1 = new File("./webapps/myweb/reg_info_error.html");

            response.setEntity(file1);
                return;

        }
        int age=Integer.parseInt(ageStr);//将年龄转换为int值
        System.out.println(username+","+password+","+nickname+","+age);
        /*
            2
            每条用户信息占用100字节，其中用户名，密码，昵称为字符串各占32字节，年龄为int值占4字节
         */
        try(
                RandomAccessFile raf=new RandomAccessFile("user.dat","rw");
                ){
                /*
                    验证是否为重复用户
                    先读取user.dat文件中现有的所有用户的名字，并与本次注册的用户名比对，如果存在则直接
                    相应页面：have_user.html ,居中显示一行字：该用户已存在，请重新注册
                 */




                     for(int i=0;i<raf.length()/100;i++){
                         raf.seek(100*i);
                         byte[]data=new byte[32];
                        raf.read(data);

                        String line=new String (data,"utf-8").trim();
                        if(username.equals(line)){
                            File File =new File("./webapps/myweb/have_user.html");
                                response.setEntity(File);
                                    return;
                        }

                     }


                            //写用户名
                        raf.seek(raf.length());
                        byte[]data=username.getBytes("UTF-8");
                        data= Arrays.copyOf(data,32);
                        raf.write(data);

                        //写密码
                        data=password.getBytes("utf-8");
                        data=Arrays.copyOf(data,32);
                        raf.write(data);
                        //写昵称
                        data=nickname.getBytes("utf-8");
                        data=Arrays.copyOf(data,32);
                        raf.write(data);
                        //写年龄
                        raf.writeInt(age);
                        System.out.println("注册完毕");
                        //3
                File file=new File("./webapps/myweb/reg_success.html");
                response.setEntity(file);

        }catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("RegServlet:用户注册处理完毕！");
    }
}
