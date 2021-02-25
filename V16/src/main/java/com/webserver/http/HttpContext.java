package com.webserver.http;



import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;
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


        try {

            SAXReader reader=new SAXReader();
            Document doc=reader.read("./webapps/config/web.xml");
            Element root=doc.getRootElement();
            List<Element> list= root.elements("mime-mapping");

               for (Element empEle : list){
                    //Element extensionEle=empEle.element("extension");
                    //Element mimetypeEle=empEle.element("mime-type");
                    //String extension=extensionEle.getText();
                    //String mimetype=mimetypeELE.getText();
                   String extension=empEle.elementText("extension");
                   String mimetype= empEle.elementText("mime-type");
                   mimeMaping.put(extension,mimetype);




           }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
       // System.out.println("输出："+mimeMaping.size());
    }
    public static String getMimeType(String ext){
       return mimeMaping.get(ext);
    }
}
