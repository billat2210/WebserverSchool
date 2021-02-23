重构HttpContext 中初始化mimeMapping的工作


通过解析config/web.xml文件将所有资源文件后缀名与对应的Content-type值存入HttpContext的静态属性
nimeMapping这个map。这样一来服务器就支持了所有类型。


实现：
重构HttpContext的方法：initMimeMapping
使用Dom4j读取config/web.xml文件
将根标签下所<mime-mapping>获取到，并将其中的子标签：
<extension>中间的文本作为key
<mime-type>中间的文本作为value
存入mimeMapping这个Map完成初始化，初始化后mimeMapping应该有1011个元素