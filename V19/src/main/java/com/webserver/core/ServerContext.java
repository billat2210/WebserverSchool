package com.webserver.core;

import com.webserver.servlet.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerContext {
    private static Map<String , Httpservlet> servletMapping=new HashMap<>();
    static {
        initServletMapping();
    }
    private static void initServletMapping(){
        /*
            解析config/servlets.xml文件，将根据标签下所有名为<servlet>的标签获取到，并将其中属性path的值作为key
            className的值利用反射实例化对应的类并作为value
            保存到servletMapping这个Map完成初始化操作。
         */
//

        try {
            SAXReader reader=new SAXReader();
            Document doc=reader.read("./config/servlets.xml");
            Element root=doc.getRootElement();
            List<Element> list=root.elements("servlet");//将根标签中所有的元素存到list集合中

            for(Element e:list){
                String key=e.attributeValue("path");//  获取xml中标签servlet的属性

                String value=e.attributeValue("className");

                Class cls=Class.forName(value);
               Httpservlet servlet =(Httpservlet) cls.newInstance();

                servletMapping.put(key, servlet);

            }

        } catch (Exception  e) {
            e.printStackTrace();
        }
    }
    public static Httpservlet getServlet(String path){
        return  servletMapping.get(path);
    }
}
