package com.wyd.rolequery.servlet;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import net.sf.json.JSONArray;
import com.wyd.rolequery.DataCaptureServer;
import com.wyd.rolequery.bean.Account;
import com.wyd.rolequery.bean.Empireaccount;
import com.wyd.rolequery.bean.Player;
import com.wyd.rolequery.bean.PlayerOnline;
import com.wyd.rolequery.server.factory.ServiceManager;
import com.wyd.rolequery.util.DateUtil;
import com.wyd.rolequery.util.SqlStringUtil;
import java.util.TreeMap;
/**
 * 定期抓取用户登录数据
 * 
 * @author 陈杰
 */
public class GetLoginInfoServlet extends HttpServlet {
    private static final long   serialVersionUID = 1911747458628093909L;
    private static final Logger log              = Logger.getLogger(GetLoginInfoServlet.class);

    // /private static final String CONTENT_TYPE = "text/html";
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @SuppressWarnings("static-access")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long a = System.currentTimeMillis();
        JSONArray json = new JSONArray();
        String typeStr = req.getParameter("type");
        int type = 0;
        if (typeStr != null && !typeStr.equals("") && !typeStr.equals(null)) {
            type = Integer.parseInt(typeStr);
        }
        boolean success = false;
        String startTime = req.getParameter("startTime");
        String limitNumberStr = req.getParameter("limitNumber");
        int limitNumber = Integer.parseInt(ServiceManager.getManager().getConfiguration().getString("limitNumber"));
        List<Properties> data = new ArrayList<Properties>();
        if (limitNumberStr != null && !limitNumberStr.equals("")) {
            limitNumber = Integer.parseInt(limitNumberStr);
        }
        String exMessage = "LoginServlet起始playerId-->" + ServiceManager.getManager().getLoginStartId() + "--type=" + type + "---当前Time-->" + DateUtil.getCurrentDateTime() + "--参数Time--->" + startTime;
        try {
            String accountIds = "";
            List<PlayerOnline> playerOnlineList = null;
            List<Integer> eIdList = null;
            switch (type) {
            case 0:
                playerOnlineList = ServiceManager.getManager().getWorldService().getPlayerOnlineListAll(ServiceManager.getManager().getLoginStartId(), limitNumber);
                eIdList = ServiceManager.getManager().getWorldService().getPlayerOnlineEIDListAll(ServiceManager.getManager().getLoginStartId(), limitNumber);
//                long c = System.currentTimeMillis();
//                System.out.println("c-a---->" + (c - a) / 1000);
                success = true;
                break;
            case 1:
                playerOnlineList = ServiceManager.getManager().getWorldService().getPlayerOnlineListByTime(DateUtil.getOneHoursAgoTime(Integer.parseInt(ServiceManager.getManager().getConfiguration().getString("timeIntervalNumber"))), DateUtil.getCurrentDateTime(), 1);
                eIdList = ServiceManager.getManager().getWorldService().getPlayerOnlineEIDListByTime(DateUtil.getOneHoursAgoTime(Integer.parseInt(ServiceManager.getManager().getConfiguration().getString("timeIntervalNumber"))), DateUtil.getCurrentDateTime(), 1);
                success = true;
                break;
            case 2:
                if (!startTime.equals(null) && !startTime.equals("")) {
                    success = true;
                    playerOnlineList = ServiceManager.getManager().getWorldService().getPlayerOnlineListByTime(startTime, DateUtil.getCurrentDateTime(), 2);
                    eIdList = ServiceManager.getManager().getWorldService().getPlayerOnlineEIDListByTime(startTime, DateUtil.getCurrentDateTime(), 2);
                }
                break;
            default:
                break;
            }
            if (playerOnlineList != null && playerOnlineList.size() > 0) {
                long f = System.currentTimeMillis();
                accountIds = SqlStringUtil.getTSQLIN(eIdList);//, eIdList.size(), " e.id ");
//                System.out.println(accountIds);
//                long d = System.currentTimeMillis();
//                System.out.println("sql---->" + (f - d) / 1000);
                List<Object[]> accountList = ServiceManager.getManager().getAccountService().getEmpireaccountByAccountId(accountIds);
//                long e = System.currentTimeMillis();
//                System.out.println("e-d---->" + (e - d) / 1000);
                Map<Integer, Object[]> empireaccountMap = new TreeMap<Integer, Object[]>();
                if (accountList != null) {
                    for (Object[] empireaccount : accountList) {
                        empireaccountMap.put(Integer.parseInt(empireaccount[0].toString()), empireaccount);
                    }
                    StringBuffer content = new StringBuffer();
                    for (PlayerOnline playerOnline : playerOnlineList) {
                        if (content.length() > 0) {
                            content.append("|");
                        }
                        Object[] account = empireaccountMap.get(playerOnline.getPlayer().getAccountId());
                        if (account != null && account[2] != null) {
                            content.append(playerOnline.getId());
                            content.append(",");
                            content.append(2);
                            content.append(",");
                            content.append(account[2]);
                            content.append(",");
                            content.append(playerOnline.getPlayer().getName());
                            content.append(",");
                            content.append(playerOnline.getOnTime());
                            content.append(",");
                            content.append(playerOnline.getOffTime());
                            content.append(",");
                            content.append(playerOnline.getRep());
                            content.append(",");
                            content.append(playerOnline.getIpSource() == null ? "" : playerOnline.getIpSource());
                            content.append(",");
                            content.append(playerOnline.getAreaId());
                        } else {
                            continue;
                        }
                    }
                    Properties properties = new Properties();
                    // System.out.println(content.toString());
                    properties.put("data", content.toString());
                    properties.put("success", true);
                    data.add(properties);
                    ServiceManager.getManager().setLoginStartId(playerOnlineList.get(playerOnlineList.size() - 1).getId());
                } else {
                    log.info("accountList为空-->" + exMessage);
                    Properties properties = new Properties();
                    properties.put("info", "查询登录数据异常！");
                    properties.put("success", false);
                    data.add(properties);
                }
            } else {
                System.out.println("未找到新登录数据，起始playerId-->" + ServiceManager.getManager().getLoginStartId());
                Properties properties = new Properties();
                if (success) {
                    log.info("未找到新登录数据-->" + exMessage);
                    properties.put("info", "未找到新登录数据！");
                } else {
                    log.info("参数错误-->" + exMessage);
                    properties.put("info", "对不起参数错误！");
                }
                properties.put("data", "");
                properties.put("success", true);
                data.add(properties);
                log.info(exMessage);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("未知错误" + exMessage + "错误详细信息\r\n" + ex.getMessage());
            System.out.println("查询登录数据异常，未知错误。type-->" + type);
            Properties properties = new Properties();
            properties.put("info", "服务器繁忙，请稍后再试！");
            properties.put("success", false);
            data.add(properties);
        }
        json = json.fromObject(data);
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(200);
        ServletOutputStream out = resp.getOutputStream();
        OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
        os.write(json.toString());
        long b = System.currentTimeMillis();
        System.out.println("抓取登录数据执行时间--->" + ((b-a) / 1000) + "秒");
        os.flush();
        os.close();
    }
}
