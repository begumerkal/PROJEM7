package com.wyd.exchange.servlet.integral;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import com.wyd.exchange.bean.GroupInfo;
import com.wyd.exchange.bean.GroupIntegral;
import com.wyd.exchange.server.factory.ServiceManager;
import com.wyd.exchange.utils.CryptionUtil;
/**
 * 编辑弹王分组
 * @author zguoqiu
 */
public class UpdateIntegralGrpupServlet extends HttpServlet {
    private static final long serialVersionUID = 1911747458628093909L;

    // /private static final String CONTENT_TYPE = "text/html";
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @SuppressWarnings("static-access")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ret = "更新失败!";
        try {
            byte[] data = CryptionUtil.inputStream2byte(req.getInputStream());
            String dataString = new String(CryptionUtil.Decrypt(data, ServiceManager.getManager().getConfiguration().getString("deckey")), "utf-8");
            JSONObject jsonObject = JSONObject.fromObject(dataString);
            GroupInfo groupInfo = (GroupInfo) jsonObject.toBean(jsonObject, GroupInfo.class);
            GroupIntegral groupIntegral = (GroupIntegral) ServiceManager.getManager().getIntegralService().get(GroupIntegral.class, groupInfo.getId());
            if (null != groupIntegral) {
                String[] services = groupInfo.getServices().split(",");
                StringBuffer sb = new StringBuffer();
                for (String service : services) {
                    GroupIntegral groud = ServiceManager.getManager().getIntegralService().getGroupByServiceId(service);
                    if (null != groud && groud.getId().intValue() != groupIntegral.getId().intValue()) {
                        sb.append("游戏:");
                        sb.append(service);
                        sb.append(" 已分配至组“");
                        sb.append(groud.getRemark());
                        sb.append("”，");
                    }
                }
                if (sb.length() > 0) {
                    sb.append("请重新分配！");
                    ret = sb.toString();
                } else {
                    groupIntegral.setServices(groupInfo.getServices());
                    groupIntegral.setRemark(groupInfo.getRemark());
                    groupIntegral.setInfo(groupInfo.getInfo());
                    ServiceManager.getManager().getIntegralService().update(groupIntegral);
                    ServiceManager.getManager().getIntegralService().initData();
                    ret = "success";
                }
            }
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
