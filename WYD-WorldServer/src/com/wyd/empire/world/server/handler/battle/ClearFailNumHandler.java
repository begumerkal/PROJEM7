package com.wyd.empire.world.server.handler.battle;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.ClearFailNumOk;
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
 * 失败清零
 * 
 * @author Administrator
 */
public class ClearFailNumHandler implements IDataHandler {
	Logger log = Logger.getLogger(ClearFailNumHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService()
					.uniquePlayerItem(player.getId(), Common.RESETZEROID);
			if (pifs == null || pifs.getPLastNum() < 1) {// 清零劵数量不足
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			player.getPlayer().setWinTimes1v1Athletics(player.getPlayer().getPlayTimes1v1Athletics());
			player.getPlayer().setWinTimes1v1Champion(player.getPlayer().getPlayTimes1v1Champion());
			player.getPlayer().setWinTimes1v1Relive(player.getPlayer().getPlayTimes1v1Relive());
			player.getPlayer().setWinTimes2v2Athletics(player.getPlayer().getPlayTimes2v2Athletics());
			player.getPlayer().setWinTimes2v2Champion(player.getPlayer().getPlayTimes2v2Champion());
			player.getPlayer().setWinTimes2v2Relive(player.getPlayer().getPlayTimes2v2Relive());
			player.getPlayer().setWinTimes3v3Athletics(player.getPlayer().getPlayTimes3v3Athletics());
			player.getPlayer().setWinTimes3v3Champion(player.getPlayer().getPlayTimes3v3Champion());
			player.getPlayer().setWinTimes3v3Relive(player.getPlayer().getPlayTimes3v3Relive());
			ServiceManager.getManager().getPlayerService().savePlayerData(player.getPlayer());
			// 消耗道具
			pifs.setPLastNum(pifs.getPLastNum() - 1);
			ServiceManager.getManager().getPlayerItemsFromShopService().update(pifs);
			ClearFailNumOk clearFailNumOk = new ClearFailNumOk(data.getSessionId(), data.getSerial());
			// 更新玩家拥有的物品
			ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, pifs);
			session.write(clearFailNumOk);
			// 更新角色信息- 胜次数,战斗数
			Map<String, String> info = new HashMap<String, String>();
			info.put("playNum", player.getPlayer().getPlayNum() + "");
			info.put("winNum", info.get("playNum"));
			ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, player);

			ServiceManager.getManager().getPlayerItemsFromShopService()
					.saveGetItemRecord(player.getPlayer().getId(), Common.RESETZEROID, -1, -1, -1, 1, null);
			ServiceManager.getManager().getTaskService().useSomething(player, pifs.getShopItem().getId(), 1);
			ServiceManager.getManager().getTitleService().useSomething(player, pifs.getShopItem().getId());
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
