package com.wyd.empire.world.server.handler.player;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.player.SetPlayerWeiboId;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 保存玩家微博ID
 * 
 * @author Administrator
 */
public class SetPlayerWeiboIdHandler implements IDataHandler {
	Logger log = Logger.getLogger(SetPlayerWeiboIdHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		SetPlayerWeiboId changeProp = (SetPlayerWeiboId) data;
		try {
			String[] markStrUid = player.getPlayer().getWbUserId().split(",");
			String[] markStrIcon = player.getPlayer().getWbUserIcon().split(",");
			Map<String, String> mapUid = new HashMap<String, String>();
			Map<String, String> mapIcon = new HashMap<String, String>();
			String[] str;
			for (String s : markStrUid) {
				str = s.split("=");
				str[1] = str[1].length() < 1 ? "0" : str[1];
				mapUid.put(str[0], str[1]);
			}
			for (String s : markStrIcon) {
				str = s.split("=");
				str[1] = str[1].length() < 1 ? "0" : str[1];
				mapIcon.put(str[0], str[1]);
			}
			switch (changeProp.getWeiboType()) {
				case Common.WB_XL :
					mapUid.put(Common.XLWB, changeProp.getWeiboID().length() < 1 ? "0" : changeProp.getWeiboID());
					mapIcon.put(Common.XLWB, changeProp.getWeiboIcon().length() < 1 ? "0" : changeProp.getWeiboIcon());
					break;
				case Common.WB_TX :
					mapUid.put(Common.TXWB, changeProp.getWeiboID().length() < 1 ? "0" : changeProp.getWeiboID());
					mapIcon.put(Common.TXWB, changeProp.getWeiboIcon().length() < 1 ? "0" : changeProp.getWeiboIcon());
					break;
				case Common.WB_TT :
					mapUid.put(Common.TTWB, changeProp.getWeiboID().length() < 1 ? "0" : changeProp.getWeiboID());
					mapIcon.put(Common.TTWB, changeProp.getWeiboIcon().length() < 1 ? "0" : changeProp.getWeiboIcon());
					break;
				default :
					break;
			}
			StringBuilder sbUid = new StringBuilder();
			sbUid.append(Common.XLWB).append("=").append(mapUid.get(Common.XLWB));
			sbUid.append(",");
			sbUid.append(Common.TXWB).append("=").append(mapUid.get(Common.TXWB));
			sbUid.append(",");
			sbUid.append(Common.TTWB).append("=").append(mapUid.get(Common.TTWB));
			StringBuilder sbIcon = new StringBuilder();
			sbIcon.append(Common.XLWB).append("=").append(mapIcon.get(Common.XLWB));
			sbIcon.append(",");
			sbIcon.append(Common.TXWB).append("=").append(mapIcon.get(Common.TXWB));
			sbIcon.append(",");
			sbIcon.append(Common.TTWB).append("=").append(mapIcon.get(Common.TTWB));
			player.getPlayer().setWbUserId(sbUid.toString());
			player.getPlayer().setWbUserIcon(sbIcon.toString());
			ServiceManager.getManager().getPlayerService().savePlayerData(player.getPlayer());
			GameLogService.bindWeibo(player.getId(), player.getLevel());
		} catch (Exception ex) {
			this.log.error(ex, ex);
		}
	}
}
