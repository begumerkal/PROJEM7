package com.wyd.relay.server.payment.egame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.mortbay.jetty.Response;

import com.wyd.relay.server.service.factory.ServiceManager;

/**
 * 提供爱游戏平台第三方支付回调服务。
 * <P>
 * 使用爱游戏平台的第三方支付回调处理。
 * 
 * @author guoxuantao
 *	2012-8-1  下午03:49:03
 */
public class EgameThirdChargeCallbackServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger("egameLog");
	
	//请求参数定义。
	private static final String SERIALNO = "serialno";
	private static final String RESULTCODE = "resultCode";
	private static final String RESULTMSG = "resultMsg";
	private static final String GAMEUSERID = "gameUserId";
	private static final String VALIDATECODE = "validatecode";
	private static final String PAYTYPE = "payType";
	private static final String GAMEGOLD = "gameGold";
	private static final String GAMEID = "gameId";
	private static final String REQUESTTIME = "requestTime";
	private static final String RESPONSETIME = "responseTime";
	
	//中转模块加入分区帐户ID信息
	private static final String GAMEACCOUNTID = "gameAccountId";

	public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException{
		/**
		 *其他计费方式,包括爱豆支付,神州付,支付宝,掌中付,需CP提供接口接收回执.
		  http://合作方约定通信地址?serialno=xxx&resultCode=120&resultMsg=success&gameUserId=xxx&gameGold=5
		  &validatecode=dad3a37aa9d50688b5157698acfd7aee&payType=aidou&gameId=xxx
		  &requestTime=2012-07-30 18:25:29&responseTime=2012-07-30 18:25:29
		
		其中:
		序号	参数			名称			描述
		01	serialno	业务流水号	
		02	resultCode	结果代码		120=充值成功 121=充值失败 122=预留非成功返回代码
		03	resultMsg	结果消息		提示信息
		04	gameUserId	游戏侧用户编号	
		05	gameGold	充值金额		人民币，单位：元
		06	validatecode校验码			MD5(serialNo + gameUserId)
		07	payType		充值类型		aidou:爱豆支付; szf:神州付; alipay:支付宝;xmobo:掌中付
		08	gameId		游戏编号	
		09	requestTime	请求时间		yyyy-mm-dd hh24:mi:ss
		10	responseTime反馈时间		yyyy-mm-dd hh24:mi:ss
		
		收到反馈报文后将serialno的值返回到爱游戏平台，表示已收到反馈消息，爱游戏平台若未收到返回值，将会多次反馈结果报文（次数可配置）。
		返回serialno 格式如下：
		response.setHeader(“serialno”,serialno);

		 */
		ServletInputStream in = req.getInputStream();
		String reqParam="";
        if (in != null) {
        	reqParam = getStringFromStream(in);
            System.out.println("EgameThird Notify info:  " + reqParam);
        }
        // reqParam="correlator=201408121612013553003307&cp_order_id=218106868&result_code=00&result_desc=%E6%89%A3%E8%B4%B9%E6%88%90%E5%8A%9F&fee=5&pay_type=ipay&method=callback&sign=f8373c79e3b374127a15d459bb0318ee";
        if(reqParam.length()>50){
        	log.info("EgameThird received info:  " + reqParam);
        	//爱游戏SDK V4.0.1.0 author zengxc 2014-08-12
        	String result = ServiceManager.getManager().getEgameChargeService().sendThirdChargeSDK4(reqParam);
        	PrintWriter out =resp.getWriter();
        	out.write(result);
        	out.flush();
        	out.close();
        	log.info("EgameThird response info:  " + result);
        	return ;
        }
		log.info("爱游戏第三方充值回调处理：＝＝＝＝＝＝＝＝＝＝");
		//输出所有的参数。
		Enumeration params = req.getParameterNames();
		log.info("包含的所有参数：====");
		while(params.hasMoreElements()){
			Object object = (Object) params.nextElement();
			log.info(object);
		}
		log.info("以上是所有的参数====");
		
		//开始具体处理
		String serialStr = req.getParameter(SERIALNO);
		String resultCodeStr = req.getParameter(RESULTCODE);
		String resMsg = req.getParameter(RESULTMSG);
		String gameUserIdStr = req.getParameter(GAMEUSERID);
		String validatecode = req.getParameter(VALIDATECODE);
		String payType = req.getParameter(PAYTYPE);
		String gameGold = req.getParameter(GAMEGOLD);
		String gameIdStr = req.getParameter(GAMEID);
		String requestTimeStr = req.getParameter(REQUESTTIME);
		String responseTimeStr = req.getParameter(RESPONSETIME);
		
		//log输出
		StringBuffer logBuf = new StringBuffer();
		logBuf.append(SERIALNO).append("[").append(serialStr).append("] ");
		logBuf.append(RESULTCODE).append("[").append(resultCodeStr).append("] ");
		logBuf.append(RESULTMSG).append("[").append(resMsg).append("] ");
		logBuf.append(GAMEUSERID).append("[").append(gameUserIdStr).append("] ");
		logBuf.append(VALIDATECODE).append("[").append(validatecode).append("] ");
		logBuf.append(PAYTYPE).append("[").append(payType).append("] ");
		logBuf.append(GAMEGOLD).append("[").append(gameGold).append("] ");
		logBuf.append(GAMEID).append("[").append(gameIdStr).append("] ");
		logBuf.append(REQUESTTIME).append("[").append(requestTimeStr).append("] ");
		logBuf.append(RESPONSETIME).append("[").append(responseTimeStr).append("] ");
		log.info(logBuf.toString());
		//debug
		System.out.println(logBuf.toString());
		//clear
		logBuf.delete(0, logBuf.length());
		
		//中转处理。
		int playerId = -1;
		try{
			if(gameUserIdStr != null){
				playerId = Integer.valueOf(gameUserIdStr);
			}
		}catch (NumberFormatException e){
			log.info(e.getMessage());
			e.printStackTrace();
		}
		if(playerId != -1 && resultCodeStr.equals("120")){
            HashMap<String, Object> infoMap = new HashMap<String, Object>();
            int type = 0;
            if (playerId > 0xffffff) {
                type = 1;
                infoMap.put(SERIALNO, serialStr);
                infoMap.put(RESULTCODE, resultCodeStr);
                infoMap.put(RESULTMSG, resMsg);
                infoMap.put(GAMEUSERID, gameUserIdStr);
                infoMap.put(VALIDATECODE, validatecode);
                infoMap.put(PAYTYPE, payType);
                infoMap.put(GAMEGOLD, gameGold);
                infoMap.put(GAMEID, gameIdStr);
                infoMap.put(REQUESTTIME, requestTimeStr);
                infoMap.put(RESPONSETIME, responseTimeStr);
            } else {
                infoMap.put("playerId", playerId);
                infoMap.put("amount", gameGold);
            }
			String ret = ServiceManager.getManager().getEgameChargeService().sendThirdChargeInfo(infoMap, type);
			if("success".equals(ret)){
              if(resp instanceof Response){
                  ((Response) resp).setHeader(SERIALNO, serialStr);
              }
			}
		}else{
			log.info("解析所得数据错误。");
		}
		
		log.info("爱游戏第三方充值回调处理结束：＝＝＝＝＝＝＝＝＝＝");
	}
	
	public static String URLGet(String strUrl, Map<String,String> map) throws IOException {
        String strtTotalURL = "";
        StringBuffer result = new StringBuffer();
        if (strtTotalURL.indexOf("?") == -1)
            strtTotalURL = strUrl + "?" + getUrl(map);
        else {
            strtTotalURL = strUrl + "&" + getUrl(map);
        }
        log.debug("strtTotalURL:" + strtTotalURL);
        URL url = new URL(strtTotalURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        HttpURLConnection.setFollowRedirects(true);
        /*BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        while (true) {
            String line = in.readLine();
            if (line == null) {
                break;
            }
            result.append(line);
        }
        in.close();*/
        
        //按照电信的文档约束，我们需要在处理后按照要求返回流水号。
        //System.out.println("========= " + con.getHeaderField(SERIALNO));
        String header = con.getHeaderField(SERIALNO);
        result.append(header);
        con.disconnect();
        return result.toString();
    }
	
	 /**
     * 使用UTF-8编码格式。
     * 
     * @param map
     * @return
     */
    private static String getUrl(Map<String, String> map) {
        if ((null == map) || (map.keySet().size() == 0)) {
            return "";
        }
        StringBuffer url = new StringBuffer();
        Set<String> keys = map.keySet();
        for (Iterator<String> i = keys.iterator(); i.hasNext();) {
            String key = i.next();
            if (map.containsKey(key)) {
                Object val = map.get(key);
                String str = (val != null) ? val.toString() : "";
                try {
                    str = URLEncoder.encode(str, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                url.append(key).append("=").append(str).append("&");
            }
        }
        String strURL = "";
        strURL = url.toString();
        if ("&".equals("" + strURL.charAt(strURL.length() - 1))) {
            strURL = strURL.substring(0, strURL.length() - 1);
        }
        return strURL;
    }
  
    
    
    
    /**
     * 读取请求内容字符串
     * @param in    输入流
     * @return      参数内容
     */
    public String getStringFromStream(ServletInputStream in) {
        StringBuilder sb = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
