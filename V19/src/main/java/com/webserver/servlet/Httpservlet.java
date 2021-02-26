package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * 这个类时所有servlet的超类
 */
public  abstract class Httpservlet {
    public void service(HttpRequest request, HttpResponse response){
        String mehtod=request.getMethod();
        if("GET".equalsIgnoreCase(mehtod)){
            doGet(request,response);

        }else if ("POST".equalsIgnoreCase(mehtod)){
            doPost(request,response);
        }
    }

    /**
     * 用来处理GET请求
     * @param request
     * @param response
     */
    public abstract  void doGet(HttpRequest request,HttpResponse response);

    /**
     * 用来处理POST请求
     * @param request
     * @param response
     */
    public abstract  void doPost(HttpRequest request,HttpResponse response);
}
