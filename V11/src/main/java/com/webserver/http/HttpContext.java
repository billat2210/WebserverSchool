package com.webserver.http;

import java.util.HashMap;
import java.util.Map;

/**
 * 当前类用于保存所有与Http协议相关的规定内容以便重用
 */
public class HttpContext {
    /**
     * 资源后缀名与响应头Content-Type值得对应关系
     * key：资源后缀名
     * value：Content-Type对应的值
     */
    private static Map<String,String > mimeMaping=new HashMap<>();
    static{
        initMimeMapping();
    }
    private static void initMimeMapping(){
        mimeMaping.put("html","text/html") ;
        mimeMaping.put("css","text/css") ;
        mimeMaping.put("js","application/javascript") ;
        mimeMaping.put("png","image/png") ;
        mimeMaping.put("gif","image/gif") ;
        mimeMaping.put("jpg","image/jpeg") ;


    }
    public static String getMimeType(String ext){
       return mimeMaping.get(ext);
    }
}
