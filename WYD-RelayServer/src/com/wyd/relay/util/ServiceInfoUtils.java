package com.wyd.relay.util;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.wyd.relay.bean.Service;
/**
 * 管理random.xml文件
 * 
 * @author Administrator
 */
public class ServiceInfoUtils {
    private static final String fileName = System.getProperty("user.dir") + "/serviceinfo.xml";

    /**
     * 获取国家地区配置，新玩家分配机率配置
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Service> getInfoList() {
        File inputXml = new File(fileName);
        if (!inputXml.exists()) {
            return null;
        }
        List<Service> serviceList = new ArrayList<Service>();
        Service service;
        try {
            SAXReader saxReader = new SAXReader();
            saxReader.setEncoding("UTF-8");
            Document document = saxReader.read(inputXml);
            Element employees = document.getRootElement();
            for (Iterator areaIterator = employees.elementIterator(); areaIterator.hasNext();) {
                Element infoEmployee = (Element) areaIterator.next();
                service = new Service();
                serviceList.add(service);
                service.setIp(infoEmployee.attributeValue("ip"));
                service.setPort(Integer.parseInt(infoEmployee.attributeValue("port")));
                service.setType(Integer.parseInt(infoEmployee.attributeValue("type")));
                service.setApp(infoEmployee.attributeValue("app"));
            }
            document = null;
            inputXml = null;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return serviceList;
    }
    public static Map<String, Object> createMap(String result){
        Map<String, Object> map = null;
        String paramKey="";
        String paramValue="";
        if (result != null && !result.equals("")) {
            map = new HashMap<String, Object>();
            if (result.contains("&")) {
                String[] strResult = result.split("&");
                for (String str : strResult) {
                    int index = str.indexOf("=");
                    if (index != -1) {
                        paramKey = str.substring(0, index);
                        paramValue = str.substring(index + 1, str.length());
                        map.put(paramKey, paramValue);
                    }
                }
            }else if (result.contains("=")) {
            	int index = result.indexOf("=");
                if (index != -1) {
                    paramKey = result.substring(0, index);
                    paramValue = result.substring(index + 1, result.length());
                    map.put(paramKey, paramValue);
                }
            }
        }
        return map;
    }
}
