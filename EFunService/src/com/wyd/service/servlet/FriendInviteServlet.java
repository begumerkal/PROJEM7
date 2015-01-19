package com.wyd.service.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;

import com.wyd.service.bean.Empireaccount;
import com.wyd.service.bean.Player;
import com.wyd.service.server.factory.ServiceManager;
import com.wyd.service.utils.HexBin;
import com.wyd.service.utils.HttpClientUtil;



/**
 * Efun好友邀请
 * 
 * @author zengxc
 * 
 */
public class FriendInviteServlet extends HttpServlet {
	private static final long serialVersionUID = 1911747458628093909L;
	Logger log = Logger.getLogger(FriendInviteServlet.class);

	//private static final String CONTENT_TYPE = "text/html";
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	private final String key = "5573b76c9a5297ff7e8757279d2a4c59";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		ServletOutputStream out = response.getOutputStream();		
		OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
		String dataStr = request.getParameter("data");
		String sign = request.getParameter("sign");
		if (dataStr == null) {
			os.write("接口准备就绪！");
		} else {
			try {
				if (validate(dataStr, sign)) {
					JSONObject data = JSONObject.fromObject(dataStr);
					int playerId = data.getInt("roleid");
					String requestid = data.getString("requestid");
					if (validateRequestId(requestid)) {
						JSONArray packages = data.getJSONArray("packages");
						for (int i = 0; i < packages.size(); i++) {
							JSONObject pack = (JSONObject) packages.get(i);
							int packageid = pack.getInt("packageid");
							int packetnum = pack.getInt("packetnum");
							playerGetItem(playerId, packageid, packetnum, requestid);
							reloadPlayerItem(playerId, packageid);
						}
						os.write("1000");
					} else {
						os.write("100");
					}
					response.setStatus(200);

				} else {
					log.error("验签不通过!data:" + dataStr + ",sign:" + sign);
					response.setStatus(403);
				}
			} catch (net.sf.json.JSONException e) {
				e.printStackTrace();
				response.setStatus(500);
				log.error("程序解释出错！请检查data格式:"+dataStr);
			}
		}

		os.flush();
		os.close();
	}
	/**
	 * 重新加载玩家物品
	 * @param playerId
	 * @param itemId
	 */
	private void reloadPlayerItem(int playerId,int itemId){
		Player player=ServiceManager.getManager().getPlayerService().getById(playerId);
		int areaId = player.getAreaId();
		String url = ServiceManager.getManager().getConfiguration().getString("S"+areaId);
		if (url==null){
			System.out.println("S"+areaId+" URL为空");
			return ;
		}
		url = url+"/ReloadData/PlayerItem?playerId="+playerId+"&itemId="+itemId;
		System.out.println(url);
		try {
			HttpClientUtil.GetData(url);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
		String sign_content = data + key;
		String checkSign = HexBin.HashToMD5Hex(sign_content);
		if (sign == null)
			return false;
		sign = sign.toUpperCase();
		if(!sign.equals(checkSign)){
			System.out.println("验签不通过："+checkSign);
			return false;
		}
		//验证角色ID,分区，帐号是否正确
		JSONObject json = JSONObject.fromObject(data);
		String userid = json.getString("userid");
		String roleid = json.getString("roleid");
		String serverid = json.getString("serverid");
		// 验证 areaId
		Player player=ServiceManager.getManager().getPlayerService().getById(Integer.parseInt(roleid));
		int areaId = player.getAreaId();
		if(!serverid.equals(""+areaId)){
			System.out.println("分区不一致:传过来的分区 "+serverid+" 玩家实际分区 "+areaId);
			return false;
		}
		// 验证accountId
		Empireaccount empireaccount = ServiceManager.getManager().getAccountService().getById(player.getAccountId());
		String accountName =empireaccount.getName(); 
		if(!userid.equals(getAccountId(accountName))){
			System.out.println("帐号不一致");
			return false;
		}
	
		return true;

	}

	/**
	 * 检查requestId是否可以执行（防止已经执行过）
	 * 
	 * @param requestId
	 * @return
	 */
	private boolean validateRequestId(String requestId) {
		return !ServiceManager.getManager().getPlayerItemsFromShopService().isCodeInGiftRecord(requestId);
	}

	private void playerGetItem(int playerId, int itemId, int num, String code) {
		ServiceManager.getManager().getPlayerItemsFromShopService().playerGetItem(playerId, itemId, -1, -1, num, 2, null);
		ServiceManager.getManager().getPlayerItemsFromShopService().saveGiftRecord(playerId, itemId,num, code);
	}
	private String getAccountId(String accountId){
		int index_=accountId.indexOf("_");
		if(accountId!=null && index_!=-1){
			return accountId.substring(index_+1);
		}
		return 	accountId;
	}
}
