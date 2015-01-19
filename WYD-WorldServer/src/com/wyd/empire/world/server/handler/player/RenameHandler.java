package com.wyd.empire.world.server.handler.player;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.player.Rename;
import com.wyd.empire.protocol.data.player.RenameOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 角色改名
 * 
 * @author Administrator
 */
public class RenameHandler implements IDataHandler {
	Logger log = Logger.getLogger(RenameHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		Rename rename = (Rename) data;
		try {
			PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService()
					.uniquePlayerItem(player.getId(), Common.RENAMEPENID);
			if (pifs == null || pifs.getPLastNum() < 1) {// 改名劵数量不足
				throw new Exception(Common.ERRORKEY + ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE);
			}
			ServiceManager.getManager().getPlayerService().modifyName(player, rename.getNewName());
			RenameOk renameOk = new RenameOk(data.getSessionId(), data.getSerial());
			renameOk.setNewName(player.getName());
			// 消耗改名劵
			pifs.setPLastNum(pifs.getPLastNum() - 1);
			ServiceManager.getManager().getPlayerItemsFromShopService().update(pifs);
			// 更新角色信息-名称
			Map<String, String> info = new HashMap<String, String>();
			info.put("name", rename.getNewName());
			ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, player);
			// 更新玩家拥有的物品
			ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, pifs);
			session.write(renameOk);

			ServiceManager.getManager().getPlayerItemsFromShopService()
					.saveGetItemRecord(player.getPlayer().getId(), Common.RENAMEPENID, -1, -1, -3, 1, null);
			ServiceManager.getManager().getTaskService().useSomething(player, pifs.getShopItem().getId(), 1);
			ServiceManager.getManager().getTitleService().useSomething(player, pifs.getShopItem().getId());
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}
}
