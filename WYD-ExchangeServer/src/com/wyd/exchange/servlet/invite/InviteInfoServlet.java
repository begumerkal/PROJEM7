package com.wyd.exchange.servlet.invite;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wyd.exchange.bean.InviteInfo;
import com.wyd.exchange.server.factory.ServiceManager;
import com.wyd.exchange.utils.CryptionUtil;
/**
 * 获取玩家已邀请的人数
 * @author zgq
 */
public class InviteInfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1911747458628093909L;

    // /private static final String CONTENT_TYPE = "text/html";
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String inviteNum = "0";
        try {
            byte[] data = CryptionUtil.inputStream2byte(req.getInputStream());
            String inviteCode = new String(CryptionUtil.Decrypt(data, ServiceManager.getManager().getConfiguration().getString("deckey")), "utf-8");
            InviteInfo inviteInfo = ServiceManager.getManager().getInviteInfoService().getInviteInfoByCode(inviteCode);
            if (null != inviteInfo) {
                inviteNum = inviteInfo.getInviteNum()+"";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(200);
        ServletOutputStream out = resp.getOutputStream();
        OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
        os.write(inviteNum);
        os.flush();
        os.close();
    }
}