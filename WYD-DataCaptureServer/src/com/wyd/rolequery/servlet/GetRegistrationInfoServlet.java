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
import com.wyd.rolequery.server.factory.ServiceManager;
import com.wyd.rolequery.util.DateUtil;
import com.wyd.rolequery.util.SqlStringUtil;
import java.util.TreeMap;
/**
 * 定期抓取注册用户数据
 * 
 * @author 陈杰
 */
public class GetRegistrationInfoServlet extends HttpServlet {
    private static final long   serialVersionUID = 1911747458628093909L;
    private static final Logger log              = Logger.getLogger(GetRegistrationInfoServlet.class);

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
        if (typeStr != null && !typeStr.equals("")) {
            type = Integer.parseInt(typeStr);
        }
        boolean success = false;
        String startTime = req.getParameter("startTime");
        String limitNumberStr = req.getParameter("limitNumber");
        int limitNumber = Integer.parseInt(ServiceManager.getManager().getConfiguration().getString("limitNumber"));
        List<Properties> data = new ArrayList<Properties>();
        String exMessage = "RegistrationServlet起始playerId-->" + ServiceManager.getManager().getRegistrationStartId() + "type=" + type + "---当前Time-->" + DateUtil.getCurrentDateTime() + "参数Time--->" + startTime;
        try {
            if (limitNumberStr != null && !limitNumberStr.equals("")) {
                limitNumber = Integer.parseInt(limitNumberStr);
            }
            String accountIds = "";
            List<Player> playerList = null;
            List<Integer> eIdList = null;
            switch (type) {
            case 0:
                playerList = ServiceManager.getManager().getWorldService().getPlayerListAll(ServiceManager.getManager().getRegistrationStartId(), limitNumber);
                eIdList = ServiceManager.getManager().getWorldService().getPlayerListIdAll(ServiceManager.getManager().getRegistrationStartId(), limitNumber);
//                long c = System.currentTimeMillis();
//              System.out.println("c-a---->" + (c - a) / 1000);
                success = true;
                break;
            case 1:
                playerList = ServiceManager.getManager().getWorldService().getPlayerListByTime(DateUtil.getOneHoursAgoTime(Integer.parseInt(ServiceManager.getManager().getConfiguration().getString("timeIntervalNumber"))), DateUtil.getCurrentDateTime(), 1);
                eIdList = ServiceManager.getManager().getWorldService().getPlayerEIDListByTime(DateUtil.getOneHoursAgoTime(Integer.parseInt(ServiceManager.getManager().getConfiguration().getString("timeIntervalNumber"))), DateUtil.getCurrentDateTime(), 1);
                success = true;
                break;
            case 2:
                if (!startTime.equals(null) && !startTime.equals("")) {
                    success = true;
                    playerList = ServiceManager.getManager().getWorldService().getPlayerListByTime(startTime, DateUtil.getCurrentDateTime(), 2);
                    eIdList = ServiceManager.getManager().getWorldService().getPlayerEIDListByTime(startTime, DateUtil.getCurrentDateTime(), 2);
                }
                break;
            default:
                break;
            }
            if (playerList != null && playerList.size() > 0) {
                accountIds = SqlStringUtil.getTSQLIN(eIdList);//, eIdList.size(), " e.id ");
//              long d = System.currentTimeMillis();
                List<Object[]> accountList = ServiceManager.getManager().getAccountService().getEmpireaccountByAccountId(accountIds);
//              long e = System.currentTimeMillis();
//              System.out.println("e-d---->" + (e - d) / 1000);
                Map<Integer, Object[]> empireaccountMap = new TreeMap<Integer, Object[]>();
                if (accountList != null) {
                    for (Object[] empireaccount : accountList) {
                        empireaccountMap.put(Integer.parseInt(empireaccount[0].toString()), empireaccount);
                    }
                    StringBuffer content = new StringBuffer();
                    for (Player player : playerList) {
                        if (content.length() > 0) {
                            content.append("|");
                        }
                        Object[] account = empireaccountMap.get(player.getAccountId());
                        if (account != null && account[2] != null) {
                            content.append(player.getId());
                            content.append(",");
                            content.append(1);
                            content.append(",");
                            content.append(account[2]);
                            content.append(",");
                            content.append(player.getName());
                            content.append(",");
                            if (account[3] != account[2]) {
                                content.append(0);
                            } else {
                                content.append(1);
                            }
                            content.append(",");
                            content.append(player.getChannel());
                            content.append(",");
                            content.append(player.getCreateTime());
                        } else {
                            continue;
                        }
                    }
                    Properties properties = new Properties();
                    // System.out.println(content.toString());
                    properties.put("data", content.toString());
                    properties.put("success", true);
                    data.add(properties);
                    ServiceManager.getManager().setRegistrationStartId(playerList.get(playerList.size() - 1).getId());
                } else {
                    log.info("accountList为空-->" + exMessage);
                    Properties properties = new Properties();
                    properties.put("info", "查询注册数据异常！");
                    properties.put("success", false);
                    data.add(properties);
                }
            } else {
                System.out.println("未找到新注册数据，起始playerId-->" + ServiceManager.getManager().getRegistrationStartId());
                Properties properties = new Properties();
                if (success) {
                    log.info("未找到新注册数据-->" + exMessage);
                    properties.put("info", "未找到新注册数据！");
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
            System.out.println("查询注册数据异常，未知错误。type-->" + typeStr);
            Properties properties = new Properties();
            properties.put("info", "服务器繁忙，请售后再试！");
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
        System.out.println("抓取注册数据执行时间--->" + ((b-a) / 1000) + "秒");
        os.flush();
        os.close();
    }
}
