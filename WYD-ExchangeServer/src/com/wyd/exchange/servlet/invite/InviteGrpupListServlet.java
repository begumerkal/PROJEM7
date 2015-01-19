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
import net.sf.json.JSONArray;
import com.wyd.exchange.bean.GroupInfo;
import com.wyd.exchange.bean.GroupInvite;
import com.wyd.exchange.server.factory.ServiceManager;

/**
 * 获取激活码组列表
 * @author zgq
 */
public class InviteGrpupListServlet extends HttpServlet {
    private static final long serialVersionUID = 1911747458628093909L;

    // /private static final String CONTENT_TYPE = "text/html";
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @SuppressWarnings({"unchecked"})
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<GroupInvite> groupList = ServiceManager.getManager().getInviteInfoService().getAll(GroupInvite.class);
            List<GroupInfo> giList = new ArrayList<GroupInfo>();
            for (GroupInvite ge : groupList) {
                GroupInfo gi = new GroupInfo();
                giList.add(gi);
                gi.setId(ge.getId());
                gi.setServices(ge.getServices());
                gi.setRemark(ge.getRemark());
            }
            JSONArray jsonObject = JSONArray.fromObject(giList);
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(200);
            ServletOutputStream out = resp.getOutputStream();
            OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
            os.write(jsonObject.toString());
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
