package com.wyd.rolequery.servlet;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import com.wyd.rolequery.bean.Empireaccount;
import com.wyd.rolequery.bean.Player;
import com.wyd.rolequery.server.factory.ServiceManager;
/**
 * 查询角色信息
 * 
 * @author zgq
 */
public class GetRoleInfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1911747458628093909L;

    // /private static final String CONTENT_TYPE = "text/html";
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @SuppressWarnings("static-access")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONArray json = new JSONArray();
        String userId = req.getParameter("userId");
        String serviceId = req.getParameter("serviceId");
        List<Properties> data = new ArrayList<Properties>();
        try {
            String area = ServiceManager.getManager().getConfiguration().getString("area");
            if ("HK".equals(area)) userId = "EFHK_" + userId;
            List<Empireaccount> accountList = ServiceManager.getManager().getAccountService().getEmpireaccount(userId, area + "_" + serviceId);
            if (null != accountList && accountList.size() > 0) {
                StringBuffer accountIds = new StringBuffer();
                for (Empireaccount account : accountList) {
                    accountIds.append(account.getId());
                    accountIds.append(",");
                }
                accountIds.delete(accountIds.length() - 1, accountIds.length());
                List<Player> playerList = ServiceManager.getManager().getWorldService().getPlayerList(accountIds.toString());
                if (null != playerList && playerList.size() > 0) {
                    for (Player player : playerList) {
                        Properties properties = new Properties();
                        properties.put("roleId", player.getId());
                        properties.put("roleName", player.getName());
                        properties.put("level", player.getLevel());
                        properties.put("status", player.getStatus());
                        properties.put("mac", player.getMac());
                        data.add(properties);
                    }
                } else {
                    System.out.println("未找到对应的角色：" + accountIds.toString());
                    Properties properties = new Properties();
                    properties.put("info", "未找到对应的角色！");
                    properties.put("success", false);
                    data.add(properties);
                }
            } else {
                System.out.println("未找到对应的帐号：" + userId);
                Properties properties = new Properties();
                properties.put("info", "用户id错误！");
                properties.put("success", false);
                data.add(properties);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("未找到对应的帐号：" + userId);
            Properties properties = new Properties();
            properties.put("info", "用户id错误！");
            properties.put("success", false);
            data.add(properties);
        }
        json = json.fromObject(data);
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(200);
        ServletOutputStream out = resp.getOutputStream();
        OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
        os.write(json.toString());
        os.flush();
        os.close();
    }
}
