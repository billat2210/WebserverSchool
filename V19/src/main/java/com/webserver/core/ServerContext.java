package com.webserver.core;

import com.webserver.servlet.*;

import java.util.HashMap;
import java.util.Map;

public class ServerContext {
    private static Map<String , Httpservlet> servletMapping=new HashMap<>();
    static {
        initServletMapping();
    }
    private static void initServletMapping(){
        servletMapping.put("/myweb/regUser",new RegServlet());
        servletMapping.put("/myweb/loginUser",new Loginservlet());
        servletMapping.put("/myweb/showAllUser",new ShowAllUserServlet());
        servletMapping.put("/myweb/toUpdate",new AmendServlet());
    }
    public static Httpservlet getServlet(String path){
        return  servletMapping.get(path);
    }
}
