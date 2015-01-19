package com.wyd.rolequery;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.thread.BoundedThreadPool;
import com.wyd.rolequery.server.factory.ServiceManager;
import com.wyd.rolequery.servlet.GetLoginInfoServlet;
import com.wyd.rolequery.servlet.GetRechargeInfoServlet;
import com.wyd.rolequery.servlet.GetRegistrationInfoServlet;
/**
 * @author zguoqiu
 * @version 创建时间：2013-4-16 下午2:36:29 类说明
 */
public class DataCaptureServer {
    public static DataCaptureServer   instance       = null;
    private static final Logger log            = Logger.getLogger(DataCaptureServer.class);
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        try {
            instance = new DataCaptureServer();
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
        log.info("gameData抓取服务启动...");
        openManagerServlet();
        long ne = System.currentTimeMillis() - ns;
        System.out.println("gameData抓取服务器启动...启动耗时: " + ne + " ms");
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
        //抓取注册用户数据接口
        root.addServlet(new ServletHolder(new GetRegistrationInfoServlet()), "/getRegistrationInfo/*");
        //抓取用户登录数据接口
        root.addServlet(new ServletHolder(new GetLoginInfoServlet()), "/getLoginInfo/*");
        //抓取用户充值数据接口
        root.addServlet(new ServletHolder(new GetRechargeInfoServlet()), "/getRechargeInfo/*");

        server.start();
    }
}
