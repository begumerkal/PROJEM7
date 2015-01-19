package com.wyd.exchange;
 
import org.apache.log4j.Logger;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import com.wyd.exchange.server.factory.ServiceManager;
import com.wyd.exchange.servlet.exchange.AddGrpupServlet;
import com.wyd.exchange.servlet.exchange.CreateServlet;
import com.wyd.exchange.servlet.exchange.DeleperGrpupServlet;
import com.wyd.exchange.servlet.exchange.ExchangeServlet;
import com.wyd.exchange.servlet.exchange.ExtendServlet;
import com.wyd.exchange.servlet.exchange.GrpupListServlet;
import com.wyd.exchange.servlet.exchange.SelectServlet;
import com.wyd.exchange.servlet.exchange.SuccessServlet;
import com.wyd.exchange.servlet.exchange.UpdateGrpupServlet;
import com.wyd.exchange.servlet.exchange.UpdateServlet;
import com.wyd.exchange.servlet.integral.AddIntegralGrpupServlet;
import com.wyd.exchange.servlet.integral.CleanUpIntegralServlet;
import com.wyd.exchange.servlet.integral.DeleteIntegralGrpupServlet;
import com.wyd.exchange.servlet.integral.GetGrpupInfoServlet;
import com.wyd.exchange.servlet.integral.GetIntegralInfoServlet;
import com.wyd.exchange.servlet.integral.GetRankByServiceServlet;
import com.wyd.exchange.servlet.integral.GetTopThirtyListServlet;
import com.wyd.exchange.servlet.integral.GetTopThreeListServlet;
import com.wyd.exchange.servlet.integral.IntegralGrpupListServlet;
import com.wyd.exchange.servlet.integral.UpdateIntegralGrpupServlet;
import com.wyd.exchange.servlet.integral.UpdateIntegralServlet;
import com.wyd.exchange.servlet.invite.AddInviteGrpupServlet;
import com.wyd.exchange.servlet.invite.DeleteInviteGrpupServlet;
import com.wyd.exchange.servlet.invite.InviteGrpupListServlet;
import com.wyd.exchange.servlet.invite.InviteInfoServlet;
import com.wyd.exchange.servlet.invite.InviteListServlet;
import com.wyd.exchange.servlet.invite.InviteServlet;
import com.wyd.exchange.servlet.invite.UpdateInviteGrpupServlet;
import com.wyd.exchange.servlet.recharge.CheckRechargeServlet;

public class ExchangeServer {
    public static ExchangeServer   instance       = null;
    private static final Logger log            = Logger.getLogger(ExchangeServer.class);
    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            ServiceManager.getManager().initData();
            instance = new ExchangeServer();
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
        log.info("数据中转服务器启动...");
        openManagerServlet();
        long ne = System.currentTimeMillis() - ns;
        System.out.println("数据中转服务器启动...启动耗时: " + ne + " ms");
    }
    
    /**
     * 后台管理服务
     * @throws Exception
     */
    private void openManagerServlet() throws Exception {
        org.mortbay.jetty.Server server = new org.mortbay.jetty.Server();
        // // 设置jetty线程池
        // BoundedThreadPool threadPool = new BoundedThreadPool();
        // // 设置连接参数
        // threadPool.setMinThreads(10);
        // threadPool.setMaxThreads(100);
        // 设置监听端口，ip地址
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(ServiceManager.getManager().getConfiguration().getInt("port"));
        connector.setHost(ServiceManager.getManager().getConfiguration().getString("localip"));
        server.addConnector(connector);
        // 访问项目地址
        Context root = new Context(server, "/", 1);
        root.addServlet(new ServletHolder(new CreateServlet()), "/create/*");
        root.addServlet(new ServletHolder(new ExchangeServlet()), "/exchange/*");
        root.addServlet(new ServletHolder(new SelectServlet()), "/select/*");
        root.addServlet(new ServletHolder(new SuccessServlet()), "/success/*");
        root.addServlet(new ServletHolder(new ExtendServlet()), "/extend/*");
        root.addServlet(new ServletHolder(new UpdateServlet()), "/update/*");
        root.addServlet(new ServletHolder(new AddGrpupServlet()), "/addgroup/*");
        root.addServlet(new ServletHolder(new DeleperGrpupServlet()), "/delgroup/*");
        root.addServlet(new ServletHolder(new UpdateGrpupServlet()), "/updgroup/*");
        root.addServlet(new ServletHolder(new GrpupListServlet()), "/selgroup/*");
        
        //邀请码相关接口
        root.addServlet(new ServletHolder(new AddInviteGrpupServlet()), "/addig/*");
        root.addServlet(new ServletHolder(new DeleteInviteGrpupServlet()), "/delig/*");
        root.addServlet(new ServletHolder(new UpdateInviteGrpupServlet()), "/updig/*");
        root.addServlet(new ServletHolder(new InviteGrpupListServlet()), "/selig/*");
        root.addServlet(new ServletHolder(new InviteInfoServlet()), "/invitenum/*");
        root.addServlet(new ServletHolder(new InviteListServlet()), "/invitelist/*");
        root.addServlet(new ServletHolder(new InviteServlet()), "/invite/*");
        
        // 挑战赛相关接口
        root.addServlet(new ServletHolder(new GetIntegralInfoServlet()), "/integralInfo/*");
        root.addServlet(new ServletHolder(new UpdateIntegralServlet()), "/updateintegral/*");
        root.addServlet(new ServletHolder(new GetTopThirtyListServlet()), "/topthirtylist/*");
        root.addServlet(new ServletHolder(new GetTopThreeListServlet()), "/topthreelist/*");
        root.addServlet(new ServletHolder(new GetRankByServiceServlet()), "/rankbyservice/*");
        root.addServlet(new ServletHolder(new CleanUpIntegralServlet()), "/cleanupintegral/*");
        root.addServlet(new ServletHolder(new GetGrpupInfoServlet()), "/getintegralgroup/*");
        root.addServlet(new ServletHolder(new AddIntegralGrpupServlet()), "/addintegralgroup/*");
        root.addServlet(new ServletHolder(new DeleteIntegralGrpupServlet()), "/delintegralgroup/*");
        root.addServlet(new ServletHolder(new UpdateIntegralGrpupServlet()), "/updintegralgroup/*");
        root.addServlet(new ServletHolder(new IntegralGrpupListServlet()), "/selintegralgroup/*");
        
        //充值对账
        root.addServlet(new ServletHolder(new CheckRechargeServlet()), "/checkRecharge/*");
        
        server.start();
    }
}
