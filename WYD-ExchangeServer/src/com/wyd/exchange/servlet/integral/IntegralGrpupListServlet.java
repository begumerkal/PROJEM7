package com.wyd.exchange.servlet.integral;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import com.wyd.exchange.bean.GroupIntegral;
import com.wyd.exchange.server.factory.ServiceManager;

/**
 * 获取弹王分组列表
 * @author zguoqiu
 */
public class IntegralGrpupListServlet extends HttpServlet {
    private static final long serialVersionUID = 1911747458628093909L;

    // /private static final String CONTENT_TYPE = "text/html";
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @SuppressWarnings({ "unchecked"})
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<GroupIntegral> groupList = ServiceManager.getManager().getIntegralService().getAll(GroupIntegral.class);
            JSONArray jsonObject = JSONArray.fromObject(groupList);
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
