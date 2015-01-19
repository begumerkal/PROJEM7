package com.wyd.exchange.servlet.invite;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import com.wyd.db.page.PageList;
import com.wyd.exchange.bean.InviteInfo;
import com.wyd.exchange.bean.InviteListResult;
import com.wyd.exchange.bean.SelectInviteListInfo;
import com.wyd.exchange.server.factory.ServiceManager;
import com.wyd.exchange.utils.CryptionUtil;
/**
 * 获取玩家已邀请的玩家列表
 * @author zgq
 */
public class InviteListServlet extends HttpServlet {
    private static final long serialVersionUID = 1911747458628093909L;

    // /private static final String CONTENT_TYPE = "text/html";
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @SuppressWarnings("static-access")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InviteListResult inviteListResult = new InviteListResult();
        try {
            byte[] data = CryptionUtil.inputStream2byte(req.getInputStream());
            String dataString = new String(CryptionUtil.Decrypt(data, ServiceManager.getManager().getConfiguration().getString("deckey")), "utf-8");
            JSONObject jsonObject = JSONObject.fromObject(dataString);
            SelectInviteListInfo sili = (SelectInviteListInfo) jsonObject.toBean(jsonObject, SelectInviteListInfo.class);
            PageList pageList = ServiceManager.getManager().getInviteInfoService().getInviteListByCode(sili.getInviteCode(), sili.getPageIndex()-1, sili.getPageSize());
            List<String> serviceName = new ArrayList<String>();
            List<String> playerName = new ArrayList<String>();
            InviteInfo inviteInfo;
            for(Object obj:pageList.getList()){
                inviteInfo = (InviteInfo)obj;
                serviceName.add(inviteInfo.getServiceName());
                playerName.add(inviteInfo.getPlayerName());
            }
            inviteListResult.setPageCount(pageList.getTotalSize());
            inviteListResult.setPageIndex(pageList.getPageIndex());
            inviteListResult.setServiceName(serviceName);
            inviteListResult.setPlayerName(playerName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.fromObject(inviteListResult);
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(200);
        ServletOutputStream out = resp.getOutputStream();
        OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
        os.write(jsonObject.toString());
        os.flush();
        os.close();
    }
}