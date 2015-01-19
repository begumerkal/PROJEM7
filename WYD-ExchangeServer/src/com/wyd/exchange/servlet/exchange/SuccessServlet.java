package com.wyd.exchange.servlet.exchange;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import com.wyd.exchange.bean.CodeExchange;
import com.wyd.exchange.bean.ExchangeInfo;
import com.wyd.exchange.bean.ExchangeResult;
import com.wyd.exchange.server.factory.ServiceManager;
import com.wyd.exchange.utils.CryptionUtil;
import com.wyd.exchange.utils.DateUtil;
/**
 * 兑换奖励成功
 * 
 * @author zgq
 */
public class SuccessServlet extends HttpServlet {
    private static final long serialVersionUID = 1911747458628093909L;

    // /private static final String CONTENT_TYPE = "text/html";
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @SuppressWarnings("static-access")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExchangeResult exchangeResult = new ExchangeResult();
        try {
            byte[] data = CryptionUtil.inputStream2byte(req.getInputStream());
            String dataString = new String(CryptionUtil.Decrypt(data, ServiceManager.getManager().getConfiguration().getString("deckey")), "utf-8");
            JSONObject jsonObject = JSONObject.fromObject(dataString);
            ExchangeInfo exchangeInfo = (ExchangeInfo) jsonObject.toBean(jsonObject, ExchangeInfo.class);
            byte[] codeByte = CryptionUtil.codeStringTobytes(exchangeInfo.getCode());
            codeByte = CryptionUtil.Decrypt(codeByte, ServiceManager.getManager().getConfiguration().getString("key2"));
            codeByte = CryptionUtil.Decrypt(codeByte, ServiceManager.getManager().getConfiguration().getString("key1"));
            DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(codeByte));
            int id = inputStream.readInt();
            // int serviceid = inputStream.readShort();
            inputStream.close();
            // if (serviceid == Integer.parseInt(exchangeInfo.getServiceId())) {
            CodeExchange codeExchange = (CodeExchange) ServiceManager.getManager().getExchangeService().get(CodeExchange.class, id);
            if (null != codeExchange) {
                if (codeExchange.getChannel_id().equals("-1") || codeExchange.getChannel_id().equals(String.valueOf(exchangeInfo.getChannel()))) {
                    if (DateUtil.compareDateOnDay(new Date(), codeExchange.getExtendtime()) <= codeExchange.getUseful_life()) {
                        if (codeExchange.getUsed() == 1) {
                            if (codeExchange.getExtended() == 1) {
                                exchangeResult.setCode(0);
                                exchangeResult.setMessage(codeExchange.getContent());
                                codeExchange.setUsed(2);
                                // codeExchange.setGetter_playerid(exchangeInfo.getPlayerId());
                                // codeExchange.setGetter_severid(exchangeInfo.getServiceId());
                                codeExchange.setGettime(new Date());
                                ServiceManager.getManager().getExchangeService().update(codeExchange);
                            } else {
                                exchangeResult.setCode(1);
                                exchangeResult.setMessage("该激活码未发放");
                            }
                        } else {
                            exchangeResult.setCode(2);
                            exchangeResult.setMessage("该激活码已被使用");
                        }
                    } else {
                        exchangeResult.setCode(3);
                        exchangeResult.setMessage("该激活码已过期");
                    }
                } else {
                    exchangeResult.setCode(7);
                    exchangeResult.setMessage("渠道号不匹配");
                }
            } else {
                exchangeResult.setCode(4);
                exchangeResult.setMessage("未找到对应的激活码");
            }
            // } else {
            // exchangeResult.setCode(5);
            // exchangeResult.setMessage("激活服务器码错误");
            // }
        } catch (Exception e) {
            e.printStackTrace();
            exchangeResult.setCode(10);
            exchangeResult.setMessage("服务器内部出错");
        }
        JSONObject jsonObject = JSONObject.fromObject(exchangeResult);
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(200);
        ServletOutputStream out = resp.getOutputStream();
        OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
        os.write(jsonObject.toString());
        os.flush();
        os.close();
    }
}