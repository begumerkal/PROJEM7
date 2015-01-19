package com.wyd.serial;
 
import org.apache.log4j.Logger;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.thread.BoundedThreadPool;
import com.wyd.serial.server.factory.ServiceManager;
import com.wyd.serial.servlet.SerialServlet;

public class SerialServer {
    public static SerialServer   instance       = null;
    private static final Logger log            = Logger.getLogger(SerialServer.class);
    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            ServiceManager.getManager().init();
            instance = new SerialServer();
            instance.launch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 描述：启动服务
     * 
     * @throws Exception
     */
    public void launch() throws Exception {
        long ns = System.currentTimeMillis();
        log.info("订单序列服务器启动...");
        openManagerServlet();
        long ne = System.currentTimeMillis() - ns;
        System.out.println("订单序列服务器启动...启动耗时: " + ne + " ms");
    }
    
    /**
     * 后台管理服务
     * @throws Exception
     */
    private void openManagerServlet() throws Exception {
        org.mortbay.jetty.Server server = new org.mortbay.jetty.Server();
        // 设置jetty线程池
        BoundedThreadPool threadPool = new BoundedThreadPool();
        // 设置连接参数
        
        threadPool.setMinThreads(10);
        threadPool.setMaxThreads(100);
        // 设置监听端口，ip地址
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(ServiceManager.getManager().getConfiguration().getInt("port"));
        connector.setHost(ServiceManager.getManager().getConfiguration().getString("localip"));
        server.addConnector(connector);
        // 访问项目地址
        Context root = new Context(server, "/", 1);
        root.addServlet(new ServletHolder(new SerialServlet()), "/serial/*");
        server.start();
    }
}
