package com.wyd.serial.servlet;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import com.wyd.serial.info.SerialInfo;
import com.wyd.serial.server.factory.ServiceManager;
import com.wyd.serial.utils.CryptionUtil;

/**
 * 获取序列号接口
 * @author zgq
 */
public class SerialServlet extends HttpServlet {
    private static final long serialVersionUID = 1911747458628093909L;

    // /private static final String CONTENT_TYPE = "text/html";
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @SuppressWarnings("static-access")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ret = "fail";
        try {
            byte[] data = CryptionUtil.inputStream2byte(req.getInputStream());
            String dataString = new String(CryptionUtil.Decrypt(data, ServiceManager.getManager().getConfiguration().getString("deckey")), "utf-8");
            JSONObject jsonObject = JSONObject.fromObject(dataString);
            SerialInfo serialInfo = (SerialInfo)jsonObject.toBean(jsonObject, SerialInfo.class);
            ServiceManager.getManager().getManager().getSerialService().addQueue(serialInfo);
            int i = 0;
            while (i++ < 100 && (null == serialInfo.getSerialNum() || serialInfo.getSerialNum().length() < 1 || null == serialInfo.getOrderNum() || serialInfo.getOrderNum().length() < 1)) {
                Thread.sleep(10);
            }
//            serialInfo.wait();
            if (null != serialInfo.getSerialNum() && serialInfo.getSerialNum().length() > 0 && null != serialInfo.getOrderNum() && serialInfo.getOrderNum().length() > 0) {
                jsonObject = JSONObject.fromObject(serialInfo);
                ret = jsonObject.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(200);
        ServletOutputStream out = resp.getOutputStream();
        OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
//        System.out.println(ret);
        os.write(ret);
        os.flush();
        os.close();
    }
}
