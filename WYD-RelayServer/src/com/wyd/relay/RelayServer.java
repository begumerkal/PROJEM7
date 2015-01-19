package com.wyd.relay;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.wyd.relay.server.service.factory.ServiceManager;
 
/**
 * 类 <code>RelayServer</code>数据中转服务入口类。<br>
 * 创建数据中转各种服务，属于一个相对独立的模块。
 * 
 * @since JDK 1.6
 */
public class RelayServer {
	public static RelayServer  	instance       = null;
	private static final Logger log            = Logger.getLogger(RelayServer.class);

    public RelayServer() {
    	
    }

    /**
     * 描述：启动服务
     * 
     * @throws Exception
     */
    public void launch() throws Exception {
    	long ns = System.currentTimeMillis();
        
    	ServiceManager.getManager();
        
    	log.info("数据中转服务器启动...");
        long ne = System.currentTimeMillis() - ns;
        System.out.println("数据中转服务器启动...启动耗时: " + ne + " ms");
    }

    public static void main(String[] args) {
        try {
            instance = new RelayServer();
            instance.launch();
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            try {
                System.in.read();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
