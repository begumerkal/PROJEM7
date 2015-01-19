package com.wyd.relay.server.payment.alipay;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import com.wyd.relay.server.service.factory.ServiceManager;
import com.wyd.relay.server.service.impl.AlipayChargeService;

/**
 * 提供支付宝充值后平台回调服务。
 * 
 * @author guoxuantao
 *	2012-9-20  下午06:12:20
 */
public class AlipayChargeCallbackServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private static final String ORDERNUM = "orderNum";//订单号
	private static final String PLAYERID = "playerId";//充值的角色ID
	private static final String KEY = "key";//加密串
	private static final String CHANNEL = "channel";
	
	public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException{
		AlipayChargeService.log.info("支付宝充值回调处理：＝＝＝＝＝＝＝＝＝＝");
		//开始具体处理
		String orderNumStr = req.getParameter(ORDERNUM);
		String playerIdStr = req.getParameter(PLAYERID);
		String channelStr = req.getParameter(CHANNEL);
		String keyStr = req.getParameter(KEY);
		
		//log输出
		StringBuffer logBuf = new StringBuffer();
		logBuf.append(ORDERNUM).append("[").append(orderNumStr).append("] ");
		logBuf.append(PLAYERID).append("[").append(playerIdStr).append("] ");
		logBuf.append(CHANNEL).append("[").append(channelStr).append("] ");
		logBuf.append(KEY).append("[").append(keyStr).append("] ");
		AlipayChargeService.log.info(logBuf.toString());
		//debug
		System.out.println(logBuf.toString());
		//clear
		logBuf.delete(0, logBuf.length());
		
		//中转处理。
		int playerId = -1;
		try{
			if(playerIdStr != null){
				playerId = Integer.valueOf(playerIdStr);
			}
		}catch (NumberFormatException e){
			AlipayChargeService.log.info(e.getMessage());
			e.printStackTrace();
		}
		if(playerId != -1){
			HashMap<String, Object> infoMap = new HashMap<String, Object>();
			infoMap.put(ORDERNUM, orderNumStr);
			infoMap.put(PLAYERID, playerIdStr);
			infoMap.put(CHANNEL, channelStr);
			infoMap.put(KEY, keyStr);
			//将信息发送到中转目标模块。
			ServiceManager.getManager().getAlipayChargeService().sendAlipayChargeInfo(infoMap);
		}else{
			AlipayChargeService.log.info("解析所得数据错误。");
		}
		
		AlipayChargeService.log.info("支付宝充值回调处理结束：＝＝＝＝＝＝＝＝＝＝");
	}
	
	
}
