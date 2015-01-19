package com.wyd.empire.world.server.handler.account;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.account.SetNewToken;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.common.util.CryptionUtil;
import com.wyd.empire.world.common.util.HttpClientUtil;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 设置安卓/IOS帐号关联的token
 * 
 * @since JDK 1.6
 */
public class SetNewTokenHandler implements IDataHandler {
	private static Logger log = Logger.getLogger(SetNewTokenHandler.class);
	public String postUrl;

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		SetNewToken setToken = (SetNewToken) data;
		try {
			if (null == player || ServiceManager.getManager().getConfiguration().getString("setTokenUrl") == null)
				return;
			if (!setToken.getToken().equals("")
					&& !setToken.getToken().equals(null)
					&& !setToken.getUdid().equals("")
					&& !setToken.getUdid().equals(null)
					&& (!setToken.getUdid().equals(player.getPlayer().getUdid()) || !setToken.getToken().equals(
							player.getPlayer().getToken()))) {
				ServiceManager
						.getManager()
						.getHttpThreadPool()
						.execute(
								new setTokenThread(setToken.getUdid(), setToken.getToken(), Server.config.getAreaId(), player.getId(),
										setToken.getDeviceType()));
			}
		} catch (Exception ex) {
			log.error(ex, ex);
			ex.printStackTrace();
		}
	}

	// 127.0.0.1:9107/push/token/setToken.action?accessId=2100035286&token=2c6b29f8c53607bb4f12b6c465dcf502a8fa8356&areaId=CN_0&playerId=10024
	public class setTokenThread implements Runnable {
		private String areaId;
		private String accessId;
		private int playerId;
		private String token;
		private int deviceType;

		public setTokenThread(String accessId, String token, String areaId, int playerId, int deviceType) {
			this.areaId = areaId;
			this.accessId = accessId;
			this.playerId = playerId;
			this.token = token;
			this.deviceType = deviceType;
		}

		public void run() {
			try {
				if (null == postUrl) {
					postUrl = ServiceManager.getManager().getConfiguration().getString("setTokenUrl");
				}
				JSONObject object = JSONObject.fromObject(this);
				String url;
				byte[] data;
				url = postUrl + "/token/setToken.action";
				data = CryptionUtil.Encrypt(object.toString(), ServiceManager.getManager().getConfiguration().getString("deckey"));
				String result = HttpClientUtil.PostData(url, data);
				// System.out.println("result:" + result);
				if (!result.equals("")) {
					object = JSONObject.fromObject(result);
					if (object.getBoolean("success")) {
						WorldPlayer player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
						// System.out.println("save:" + player.getId());
						player.getPlayer().setToken(token);// 设备的token
						player.getPlayer().setUdid(accessId);// 信鸽注册accessId
						player.getPlayer().setGp(deviceType);// 设备类型: 0为IOS 1为安卓
						ServiceManager.getManager().getPlayerService().savePlayerData(player.getPlayer());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e, e);
			}
		}

		public String getAreaId() {
			return areaId;
		}

		public void setAreaId(String areaId) {
			this.areaId = areaId;
		}

		public String getAccessId() {
			return accessId;
		}

		public void setAccessId(String accessId) {
			this.accessId = accessId;
		}

		public int getPlayerId() {
			return playerId;
		}

		public void setPlayerId(int playerId) {
			this.playerId = playerId;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}
	}
}