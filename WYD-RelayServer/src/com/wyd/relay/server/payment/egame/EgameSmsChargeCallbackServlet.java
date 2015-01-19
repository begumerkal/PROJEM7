package com.wyd.relay.server.payment.egame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.wyd.relay.server.service.factory.ServiceManager;

/**
 * 提供爱游戏短代充值回调服务。
 * <P>
 * 接收爱游戏充值平台同步过来的短代充值信息。然后加入部分游戏信息后中转到帐户模块。
 * 
 * @author guoxuantao
 *	2012-8-1  下午03:43:41
 */
public class EgameSmsChargeCallbackServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger("egameLog");
	
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		/**
		http://合作方约定通信地址?resultcode=xxxx&resultmsg=xxxx&smscontent=xxxx
		resultcode：14位16进制【合作方游戏用户编号+合作方日业务流水号+结果标识】
	    resultmsg：结果描述信息 
	    smscontent：短代串内容
		合作方游戏用户编号：长度：7位（1-7），采用16进制进行编码
		合作方日业务流水号：长度：5位（8-12），采用16进制进行编码
		结果表示:长度:2位（12-14）,详细如下:
		标识代码	描述信息（resultmsg）
		00		订购成功
		01		短代格式错误
		02		合作方代码错误
		03		订购产品代码错误
		04		合作方游戏用户格式错误
		05		合作方游戏流水号格式错误
		06		接入码扩展位数值与实际道具金额不符
		07		短代限额约束:详情说明
		*/
		ServletInputStream in = req.getInputStream();
		String reqParam="";
        if (in != null) {
        	reqParam = getStringFromStream(in);
            System.out.println("EgameSms Notify info:  " + reqParam);
        }
       // reqParam="cp_order_id=251659105&correlator=20140814102944034583373&order_time=20140814102944&method=check&sign=c7ba08ae3a7aecc3c20f769e93050ea3";
        if(reqParam.length()>50){
        	log.info("EgameSms received info:  " + reqParam);
        	//爱游戏SDK V4.0.1.0 author zengxc 2014-08-14
        	String result = ServiceManager.getManager().getEgameChargeService().sendCheck(reqParam);
        	PrintWriter out =resp.getWriter();
        	out.write(result);
        	out.flush();
        	out.close();
        	log.info("EgameSms response info:  " + result);
        	return ;
        }
		log.info("爱游戏短代充值回调处理：＝＝＝＝＝＝＝＝＝＝");
		//输出所有参数
		Enumeration params = req.getParameterNames();
		log.info("包含的所有参数：====");
		while(params.hasMoreElements()){
			Object object = (Object) params.nextElement();
			log.info(object);
		}
		log.info("以上是所有的参数====");
		// 判断新旧接口
		if(req.getParameter("cpparam") != null){
			process2(req, resp);
		}else{
			process1(req, resp);
		}
	}
	
	/**
	 * 旧sdk充值回调
	 * @throws IOException 
	 */
	private void process1(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String resultcode = req.getParameter("resultcode");
		String resultmsg = req.getParameter("resultmsg");
		String smscontent = req.getParameter("smscontent");
		StringBuffer logBuf = new StringBuffer();
		logBuf.append("resultcode:[").append(resultcode).append("] ");
		logBuf.append("resultmsg:[").append(resultmsg).append("] ");
		logBuf.append("smscontent:[").append(smscontent).append("] ");
		log.info(logBuf.toString());
		//clear
		logBuf.delete(0, logBuf.length());
		
		if(resultcode == null || resultmsg == null || smscontent == null){
			log.info("回调结果中有参数为空！");
			return;
		}
		int playerId = -1;
		int serialNum = -1;
		String flag = "";
		try{
			playerId = hexStrToInt(resultcode.substring(0, 7));
			serialNum = hexStrToInt(resultcode.substring(7, 12));
			flag = resultcode.substring(12, 14);
			//log输出
			logBuf.append("playerId:[").append(playerId).append("] ");
			logBuf.append("serialNum:[").append(serialNum).append("] ");
			logBuf.append("flag:[").append(flag).append("] ");
			log.info(logBuf.toString());
			//clear
			logBuf.delete(0, logBuf.length());
		}catch (Exception e){
			log.info(e.getMessage());
			return;
		}
		
		//中转，resultcode其中包含的有角色ID，我们转发到帐户模块，需要知道其对应的分区帐号ID。
		if(playerId != -1 && flag.equals("00")){
            HashMap<String, Object> infoMap = new HashMap<String, Object>();
            int type = 0;
            if (playerId > 0xffffff) {
                type = 1;
                infoMap.put("resultcode", resultcode);
                infoMap.put("resultmsg", resultmsg);
                infoMap.put("smscontent", smscontent);
            } else {
                infoMap.put("playerId", playerId);
                infoMap.put("amount", getAmountFromSmsContent(smscontent));
            }
			ServiceManager.getManager().getEgameChargeService().sendSmsChargeInfo(infoMap, type);
		}else{
			log.info("解析所得数据错误。");
		}
		log.info("爱游戏短代充值回调处理结束：＝＝＝＝＝＝＝＝＝＝");
	}
	
	/**
	 * 新sdk充值回调
	 */
	private void process2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String resultCode = req.getParameter("resultCode");
		String cpparam = req.getParameter("cpparam");
		String resultMsg = req.getParameter("resultMsg");
		String cost = req.getParameter("cost");
		String payType = req.getParameter("payType");
		String validatecode = req.getParameter("validatecode");
		String requestTime = req.getParameter("requestTime");
		StringBuffer logBuf = new StringBuffer();
		logBuf.append("resultCode:[").append(resultCode).append("] ");
		logBuf.append("cpparam:[").append(cpparam).append("] ");
		logBuf.append("resultMsg:[").append(resultMsg).append("] ");
		logBuf.append("Cost:[").append(cost).append("] ");
		logBuf.append("payType:[").append(payType).append("] ");
		logBuf.append("validatecode:[").append(validatecode).append("] ");
		logBuf.append("requestTime:[").append(requestTime).append("] ");
		log.info(logBuf.toString());
		//clear
		logBuf.delete(0, logBuf.length());

		//中转，resultcode其中包含的有角色ID，我们转发到帐户模块，需要知道其对应的分区帐号ID。
		if("00".equals(resultCode)){
            HashMap<String, Object> infoMap = new HashMap<String, Object>();
            infoMap.put("resultCode", resultCode);
            infoMap.put("cpparam", cpparam);
            infoMap.put("resultMsg", resultMsg);
            infoMap.put("cost", cost);
            infoMap.put("payType", payType);
            infoMap.put("validatecode", validatecode);
            infoMap.put("requestTime", requestTime);
			if(ServiceManager.getManager().getEgameChargeService().sendSmsChargeInfo(infoMap)){
				resp.setContentType("text/html");
				resp.setHeader("cpparam", cpparam);
			}
		}else{
			log.info("解析所得数据错误。");
			resp.setContentType("text/html");
			resp.setHeader("cpparam", cpparam);
		}
		log.info("爱游戏短代充值回调处理结束：＝＝＝＝＝＝＝＝＝＝");
	}
	
	/**
	 * 从16进制的字符串中读取出具体的数字数值接口。
	 * 
	 * @return
	 */
	public int hexStrToInt(String hexStr) throws NumberFormatException{
		int value = Integer.valueOf(hexStr, 16);
		return value;
	}
	
	/**
     * 获取短代充值中包含的金额，根据约定文档，其短代串内容的前两位为充值金额，并且使用十进制计数。
     * 
     * @param smsContent
     * @return
     */
    public int getAmountFromSmsContent(String smsContent){
        int amount = 0;
        if(smsContent != null){
            try{
                String amStr = smsContent.substring(0, 2);
                amount = Integer.valueOf(amStr);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return amount;
    }
    /**
     * 返回读取内容字符串
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
