package com.wyd.empire.world.server.handler.rebirth;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.rebirth.RebirthResult;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取玩家的转生信息
 * 
 * @since JDK 1.6
 */
public class RebirthHandler implements IDataHandler {
	private Logger log = Logger.getLogger("rebirthlog");

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			RebirthResult rebirthResult = new RebirthResult(data.getSessionId(), data.getSerial());
			int zsLevel = player.getPlayer().getZsLevel();
			if (player.getLevel() >= Common.REBIRTHLOWLEVEL) {
				if (zsLevel < Common.REBIRTHTOPLEVEL) {
					PlayerItemsFromShop rebirthStone = ServiceManager.getManager().getPlayerItemsFromShopService()
							.uniquePlayerItem(player.getId(), Common.REBIRTHLEVEL1ID);
					int rebirthStoneNum = rebirthStone.getPLastNum();
					if (rebirthStoneNum >= Common.REBIRTHLEVEL1NUM) {
						int pLevel = player.getLevel();
						boolean isPerfect = pLevel == 99 ? true : false;
						int needDiamonds = (int) (10 * Math.pow(99 - pLevel, 2));
						if (player.getDiamond() >= needDiamonds) {
							String zsOldLevel = player.getPlayer().getZsOldLevel();
							zsOldLevel += "," + Server.config.getMaxLevel(0);
							if (zsOldLevel.startsWith(","))
								zsOldLevel = zsOldLevel.substring(1);
							player.getPlayer().setZsOldLevel(zsOldLevel);
							player.getPlayer().setLevel(100);
							player.getPlayer().setZsLevel(zsLevel + 1);
							player.getPlayer().setExp(0);
							ServiceManager.getManager().getPlayerService().savePlayerData(player.getPlayer());
							// 更新角色信息- 级别，转生级别
							Map<String, String> info = new HashMap<String, String>();
							info.put("level", player.getPlayer().getLevel() + "");
							info.put("zsLevel", player.getPlayer().getZsLevel() + "");
							ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, player);
							player.updateFight();
							rebirthStone.setPLastNum(rebirthStoneNum - 1);
							ServiceManager.getManager().getPlayerItemsFromShopService().update(rebirthStone);
							ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, rebirthStone);
							ServiceManager.getManager().getPlayerService()
									.useTicket(player, needDiamonds, TradeService.ORIGIN_REBIRTH, null, null, "玩家转生");
							rebirthResult.setMessage(ErrorMessages.REBIRTH_SUCCESS);
							GameLogService.rebirth(player.getId(), pLevel, player.getLevel(), isPerfect, Common.REBIRTHLEVEL1ID,
									needDiamonds);
							ServiceManager.getManager().getTitleService().rebirth(player);
						} else {
							rebirthResult.setStatus(4);
							rebirthResult.setMessage(ErrorMessages.COMMUNITY_NEEDDEMOND_MESSAGE);
						}

					} else {
						rebirthResult.setStatus(3);
						rebirthResult.setMessage(ErrorMessages.REBIRTH_ITEMNUM_FAIL);
					}
				} else {
					rebirthResult.setStatus(2);
					rebirthResult.setMessage(ErrorMessages.REBIRTH_TOPLEVEL_FAIL);
				}
			} else {
				rebirthResult.setStatus(1);
				rebirthResult.setMessage(ErrorMessages.REBIRTH_LOWLEVEL_FAIL);
			}

			session.write(rebirthResult);
			log.info("playerid：" + player.getId() + "-----转生前等级：" + zsLevel + "-----转生结果:" + rebirthResult.getStatus());
		} catch (Exception ex) {
			log.error(ex, ex);
			log.info("playerid：" + player.getId() + "-----转生异常");
		}
	}
}