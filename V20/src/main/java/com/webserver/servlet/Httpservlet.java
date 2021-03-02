package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import org.apache.log4j.Logger;

/**
 * 这个类时所有servlet的超类
 */
public  abstract class Httpservlet {
    private static Logger log=Logger.getLogger(Loginservlet.class);
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
