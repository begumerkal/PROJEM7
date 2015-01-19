package com.wyd.exchange.servlet.integral;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wyd.exchange.bean.GroupIntegral;
import com.wyd.exchange.server.factory.ServiceManager;
import com.wyd.exchange.utils.CryptionUtil;

/**
 * 删除弹王分组
 * @author zguoqiu
 */
public class DeleteIntegralGrpupServlet extends HttpServlet {
    private static final long serialVersionUID = 1911747458628093909L;

    // /private static final String CONTENT_TYPE = "text/html";
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ret = "删除失败！";
        try {
            byte[] data = CryptionUtil.inputStream2byte(req.getInputStream());
            String dataString = new String(CryptionUtil.Decrypt(data, ServiceManager.getManager().getConfiguration().getString("deckey")), "utf-8");
            int gid = Integer.parseInt(dataString);
            GroupIntegral group = (GroupIntegral) ServiceManager.getManager().getIntegralService().get(GroupIntegral.class, gid);
            if (null != group) {
                String[] services = group.getServices().split(",");
                for (String service : services) {
                    ServiceManager.getManager().getIntegralService().deleteIntegralOverSeason(Integer.parseInt(service));
                }
                ServiceManager.getManager().getIntegralService().remove(GroupIntegral.class, gid);
                ServiceManager.getManager().getIntegralService().initData();
            }
            ret = "success";
        } catch (Exception e) {
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
