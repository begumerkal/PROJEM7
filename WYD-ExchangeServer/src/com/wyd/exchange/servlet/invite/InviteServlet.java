package com.wyd.exchange.servlet.invite;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import com.wyd.exchange.bean.InviteAddInfo;
import com.wyd.exchange.bean.InviteInfo;
import com.wyd.exchange.server.factory.ServiceManager;
import com.wyd.exchange.utils.CryptionUtil;
/**
 * 成功邀请玩家
 * @author zgq
 */
public class InviteServlet extends HttpServlet {
    private static final long serialVersionUID = 1911747458628093909L;

    // /private static final String CONTENT_TYPE = "text/html";
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @SuppressWarnings("static-access")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int ret = 0;
        try {
            byte[] data = CryptionUtil.inputStream2byte(req.getInputStream());
            String dataString = new String(CryptionUtil.Decrypt(data, ServiceManager.getManager().getConfiguration().getString("deckey")), "utf-8");
            JSONObject jsonObject = JSONObject.fromObject(dataString);
            InviteAddInfo iai = (InviteAddInfo) jsonObject.toBean(jsonObject, InviteAddInfo.class);
            String s1 = iai.getInviteCode().split("N")[0];
            String s2 = iai.getBindInviteCode().split("N")[0];
            if(s1.equals(s2) || ServiceManager.getManager().getInviteInfoService().checkGroup(s1, s2)){
                InviteInfo inviteInfo = ServiceManager.getManager().getInviteInfoService().getInviteInfoByCode(iai.getInviteCode());
                if(null==inviteInfo){
                    inviteInfo = new InviteInfo();
                    inviteInfo.setInviteNum(0);
                }
                if(null==inviteInfo.getBindInviteCode()){
                    inviteInfo.setBindInviteCode(iai.getBindInviteCode());
                    inviteInfo.setServiceName(iai.getServiceName());
                    inviteInfo.setPlayerName(iai.getPlayerName());
                    inviteInfo.setInviteCode(iai.getInviteCode());
                    inviteInfo.setBindTime(new Date());
                    ServiceManager.getManager().getInviteInfoService().save(inviteInfo);
                    
                    inviteInfo = ServiceManager.getManager().getInviteInfoService().getInviteInfoByCode(iai.getBindInviteCode());
                    if(null==inviteInfo){
                        inviteInfo = new InviteInfo();
                        inviteInfo.setInviteCode(iai.getBindInviteCode());
                        inviteInfo.setInviteNum(0);
                    }
                    inviteInfo.setInviteNum(inviteInfo.getInviteNum()+1);
                    ServiceManager.getManager().getInviteInfoService().save(inviteInfo);
                }
            }
        } catch (Exception e) {
            ret = 500;
            e.printStackTrace();
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(200);
        ServletOutputStream out = resp.getOutputStream();
        OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
        os.write(ret);
        os.flush();
        os.close();
    }
}