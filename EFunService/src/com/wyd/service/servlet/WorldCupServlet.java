package com.wyd.service.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.wyd.service.server.factory.ServiceManager;
import com.wyd.service.utils.HexBin;

/**
 * 世界杯活动接口
 * 
 * @author zengxc
 * 
 */
public class WorldCupServlet extends HttpServlet {
	private static final long serialVersionUID = 1911747458628093909L;
	Logger log = Logger.getLogger(WorldCupServlet.class);
	private static Logger worldcupLog = Logger.getLogger("worldcupLog");
	private ServiceManager manager;

	// private static final String CONTENT_TYPE = "text/html";
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	private final String key = ".ac-yidu";
	private final int ADDPOINTS = 1, GETPOINTS = 2, USEPOINTS = 3, GETSERIAL = 4, GETUSEDIAMNUM=5 ,ADDDIAMNUM=6;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		ServletOutputStream out = response.getOutputStream();
		OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
		manager = ServiceManager.getManager();
		switch (method(request)) {
		case ADDPOINTS:
			AddPoints(request, response, os);
			break;
		case GETPOINTS:
			GetPoints(request, response, os);
			break;
		case USEPOINTS:
			UsePoints(request, response, os);
			break;
		case GETSERIAL:
			GetSerial(request, response, os);
			break;
		case GETUSEDIAMNUM:
			GetUseDiamNum(request, response, os);
			break;
		case ADDDIAMNUM:
			AddDiamNum(request, response, os);
			break;
		}
		os.flush();
		os.close();
	}

	private int method(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		if (requestURI.indexOf("AddPoints") != -1) {
			return 0;
		} else if (requestURI.indexOf("GetPoints") != -1) {
			return 0;
		} else if (requestURI.indexOf("UsePoints") != -1) {
			return 0;
		} else if (requestURI.indexOf("UsePoints") != -1) {
			return 0;
		} else if (requestURI.indexOf("GetSerial") != -1) {
			return 0;
		} else if (requestURI.indexOf("GetUseDiamNum") != -1) {
			return 0;
		} else if (requestURI.indexOf("AddUseDiamNum") != -1) {
			return 0;
		}
		
		return -1;
	}
	private String getAccountId(HttpServletRequest request){
		String accountId = request.getParameter("accountId");
		int index_=accountId.indexOf("_");
		if(accountId!=null && index_!=-1){
			return accountId.substring(index_+1);
		}
		return 	accountId;
	}
	/**
	 * 增加积分
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void AddPoints(HttpServletRequest request, HttpServletResponse response, OutputStreamWriter os) throws IOException {
		String accountId = getAccountId(request);
		String addpoints = request.getParameter("points");
		String sign = request.getParameter("sign");
		if(!StringUtils.hasText(accountId)){
			return ;
		}
		if (validate(accountId + addpoints, sign)) {
			int points = manager.getWorldCupPointsService().addPoints(accountId, addpoints);
			worldcupLog.info("POINTS "+accountId + " " + addpoints + " A");
			os.write(points + "");
		}else {
			os.write("-2");
			log.error("验签失败:" + sign);
		}
	}
	/**
	 * 增加消耗钻石数
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void AddDiamNum(HttpServletRequest request, HttpServletResponse response, OutputStreamWriter os) throws IOException {
		String accountId = getAccountId(request);
		String adddiam = request.getParameter("diam");
		String sign = request.getParameter("sign");
		if(!StringUtils.hasText(accountId)){
			return ;
		}
		if (validate(accountId + adddiam, sign)) {
			int diam = manager.getWorldCupPointsService().addDiamond(accountId, adddiam);
			worldcupLog.info("DIAM "+accountId + " " + adddiam + " A");
			os.write(diam + "");
		}else {
			os.write("-2");
			log.error("验签失败:" + sign);
		}
	}

	/**
	 * 获得积分
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void GetPoints(HttpServletRequest request, HttpServletResponse response, OutputStreamWriter os) throws IOException {
		String accountId = getAccountId(request);		
		if (StringUtils.hasText(accountId)) {
			int points = manager.getWorldCupPointsService().getPoints(accountId);
			os.write(points + "");
		}
	}
	/**
	 * 获得消耗钻石数
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void GetUseDiamNum(HttpServletRequest request, HttpServletResponse response, OutputStreamWriter os) throws IOException {
		String accountId = getAccountId(request);
		if (StringUtils.hasText(accountId)) {
			int diam = manager.getWorldCupPointsService().getUseDiam(accountId);
			os.write(diam + "");
		}
	}

	/**
	 * 使用积分 -1 不够 积分 -2 验签失败
	 * 
	 * @param request
	 * @param response
	 * @param os
	 * @throws IOException
	 */
	private void UsePoints(HttpServletRequest request, HttpServletResponse response, OutputStreamWriter os) throws IOException {
		String accountId = getAccountId(request);
		String usepoints = request.getParameter("points");
		String sign = request.getParameter("sign");
		if(!StringUtils.hasText(accountId)){
			return ;
		}
		if (validate(accountId + usepoints, sign)) {
			int points = manager.getWorldCupPointsService().usePoints(accountId, Integer.parseInt(usepoints));
			os.write(points + "");
			worldcupLog.info("POINTS "+accountId + " " + usepoints + " U");
		} else {
			os.write("-2");
			log.error("验签失败:" + sign);
		}
	}

	/**
	 * 获取兑换码 -1 没有相应的兑换码 -2 验签失败
	 * 
	 * @param request
	 * @param response
	 * @param os
	 * @throws IOException
	 */
	private void GetSerial(HttpServletRequest request, HttpServletResponse response, OutputStreamWriter os) throws IOException {
		String accountId = getAccountId(request);
		String goals = request.getParameter("goals");
		String sign = request.getParameter("sign");
		if(!StringUtils.hasText(accountId)){
			return ;
		}
		if (validate(accountId + goals, sign)) {
			String code = manager.getWorldCupPointsService().exchangeCode(accountId, Integer.parseInt(goals));
			os.write(code + "");
			worldcupLog.info("CODE "+accountId + " " + code + " S");
		} else {
			os.write("-2");
			log.error("验签失败:" + sign);
		}
	}

	/**
	 * 数据有效性验证
	 * 
	 * @param data
	 * @param sign
	 * @return
	 */
	private boolean validate(String data, String sign) {
		if (sign == null)
			return false;
		String sign_content = data + key;
		String checkSign = HexBin.HashToMD5Hex(sign_content);
		sign = sign.toUpperCase();
		return sign.equals(checkSign);
	}

}
