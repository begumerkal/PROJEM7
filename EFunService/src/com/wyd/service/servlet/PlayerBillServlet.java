package com.wyd.service.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.wyd.service.bean.Player;
import com.wyd.service.bean.PlayerBill;
import com.wyd.service.server.factory.ServiceManager;
import com.wyd.service.utils.HexBin;



/**
 * Efun帐单查询
 * 
 * @author zengxc
 * 
 */
public class PlayerBillServlet extends HttpServlet {
	private static final long serialVersionUID = 1911747458628093909L;
	Logger log = Logger.getLogger(PlayerBillServlet.class);
	private static Map<String,List<Result>> data=new HashMap<String,List<Result>>();//key:starttime+endtime val:results
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//private static final String CONTENT_TYPE = "text/html";
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	private final String key = ".ac-yidu";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		ServletOutputStream out = response.getOutputStream();		
		OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
		String startTime = request.getParameter("orderStartTime");
		String endTime = request.getParameter("orderEndTime");
		String sign = request.getParameter("signature");
		String gameCode = request.getParameter("gameCode");
		if (sign == null) {
			os.write("接口准备就绪！");
		} else {
			try {
				if (validate(startTime+endTime, sign)) {					
					JSONArray jsonArray = JSONArray.fromObject(find(gameCode,startTime,endTime));
					os.write(jsonArray.toString());
					response.setStatus(200);
				} else {
					log.error("验签不通过!sign:" + sign);
					response.setStatus(403);
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(500);
				log.error(e,e);
			}
		}
		os.flush();
		os.close();
	}

	/**
	 * 数据有效性验证
	 * 
	 * @param data
	 * @param sign
	 * @return
	 */
	private boolean validate(String data, String sign) {
		String sign_content = data + key;
		String checkSign = HexBin.HashToMD5Hex(sign_content);
		if (sign == null)
			return false;
		return sign.equalsIgnoreCase(checkSign);
	}
	/**
	 * 遇到相同的开始和结束时间就返回上次的结果（防止接口频繁查询导致资源浪费）
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private List<Result> find(String gameCode,String startTime,String endTime){
		List<Result> results=data.get(startTime+endTime);
		if(results!=null) return results;
		List<PlayerBill> bills=ServiceManager.getManager().getPlayerBillService().findPaymentBy(startTime, endTime);
		results = toResults(gameCode,bills);
		data.put(startTime+endTime,results);
		return results;
	}
	private List<Result> toResults(String gameCode,List<PlayerBill> bills){
		List<Result> results=new ArrayList<Result>();
		for(PlayerBill bill:bills){
			Result result =  new Result(gameCode);
			result.setOrderId(bill.getOrderNum());
			result.setMoney((bill.getChargePrice()/100)+"");//美分转美元
			result.setOrderTime(sf.format(bill.getCreateTime()));
			Player player = bill.getPlayer();
			result.setRoleId(player.getId());
			result.setServerCode(player.getAreaId()+"");
			results.add(result);
		}
		return results;
	}
	// 如果不是public在JSONArray.fromObject时会报错
	public class Result{
		private int roleId;//角色id
		private String gameCode;//游戏编码（唯一代表游戏的编码）
		private String serverCode;//服务器编码（唯一代表改游戏的服务器编码）
		private String orderId;//订单id
		private String payType;//支付渠道
		private String money;//支付金额（美元）
		private String orderTime;//订单成功时间
		public Result(String gameCode){
			this.gameCode=gameCode;
			this.payType = "appstore";			
		}
		public int getRoleId() {
			return roleId;
		}
		public void setRoleId(int roleId) {
			this.roleId = roleId;
		}
		public String getGameCode() {
			return gameCode;
		}
		
		public String getServerCode() {
			return serverCode;
		}
		public void setServerCode(String serverCode) {
			this.serverCode = serverCode;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public String getPayType() {
			return payType;
		}		
		public String getMoney() {
			return money;
		}
		public void setMoney(String money) {
			this.money = money;
		}
		public String getOrderTime() {
			return orderTime;
		}
		public void setOrderTime(String orderTime) {
			this.orderTime = orderTime;
		}
		
	}
}
