package com.wyd.empire.world.server.handler.practice;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.practice.ActivatePracticeOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.PracticeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 激活修炼加经验
 * 
 * @since JDK 1.6
 */
public class ActivatePracticeHandler implements IDataHandler {
	private static Logger log = Logger.getLogger(ActivatePracticeHandler.class);

	@SuppressWarnings("static-access")
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		PracticeService manager = ServiceManager.getManager().getPracticeService();
		try {
			WorldPlayer player = session.getPlayer(data.getSessionId());
			// 非正常玩家
			// if (player.getPlayerInfo().getPracticeStatus()) {
			// throw new
			// ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE,
			// data.getSerial(), data.getSessionId(), data.getType(),
			// data.getSubType());
			// }
			// 玩家修炼属性对应的等级
			int mapLeve = player.getPlayerInfo().getPracticeLeve() == 0 ? 1 : player.getPlayerInfo().getPracticeLeve();
			// 当前等级激活每日扣除勋章数
			int consumeMedalNumber = manager.getDayConsumptionNumberByMapLeve(mapLeve);
			if (player.getMedalNum() < consumeMedalNumber) {
				// 勋章不足!
				throw new ProtocolException(TipMessages.MEDAL_INSUFFICIENT, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			// 消耗勋章一系列操作
			PlayerItemsFromShop playerItem = ServiceManager.getManager().getPlayerItemsFromShopService()
					.uniquePlayerItem(player.getId(), Common.BADGEID);
			if (null == playerItem || consumeMedalNumber > playerItem.getPLastNum()) {
				throw new Exception(ErrorMessages.TRATE_ITEMENOUGH_MESSAGE);
			}
			playerItem.setPLastNum(playerItem.getPLastNum() - consumeMedalNumber);
			ServiceManager.getManager().getPlayerItemsFromShopService().update(playerItem);
			player.setMedalNum(player.getMedalNum() - consumeMedalNumber);
			// TODO 更新玩家拥有的物品 数据暂未下发,待缓存优化完相关模块人员加上去
			ServiceManager.getManager().getPlayerItemsFromShopService()
					.saveGetItemRecord(player.getPlayer().getId(), Common.BADGEID, -1, consumeMedalNumber, 34, 1, null);
			// 更新状态
			// player.getPlayerInfo().setPracticeStatus(true);
			player.updatePlayerInfo();
			player.updateFight();
			ActivatePracticeOk activatePracticeOk = new ActivatePracticeOk(data.getSessionId(), data.getSerial());
			activatePracticeOk.setLowMedalnumber(player.getMedalNum());
			activatePracticeOk.setSeniorMedlNumber(player.getSeniorMedalNum());
			// activatePracticeOk.setStatus(true);
			updateMedlNumber(player);
			session.write(activatePracticeOk);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	/**
	 * 更新玩家徽章数量等信息
	 * 
	 * @param player
	 */
	public void updateMedlNumber(WorldPlayer player) {
		Map<String, String> info = new HashMap<String, String>();
		info.put("medal", player.getMedalNum() + "");
		info.put("useTodayNumber", player.getPlayerInfo().getUseTodayNumber() + "");
		// info.put("practiceStatus", player.getPlayerInfo().getPracticeStatus()
		// + "");
		info.put("seniorMedlNumber", player.getSeniorMedalNum() + "");
		ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, player);
	}

}