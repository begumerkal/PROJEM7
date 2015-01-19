package com.wyd.empire.world.server.handler.starsoul;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.starsoul.LightNextStarOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.StarSoulService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 点亮星魂图
 * 
 * @author 陈杰
 * @since JDK 1.6
 */
public class LightNextStarHandler implements IDataHandler {
	private static Logger log = Logger.getLogger(LightNextStarHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		StarSoulService manager = ServiceManager.getManager().getStarSoulService();
		try {
			WorldPlayer player = session.getPlayer(data.getSessionId());
			// 玩家当前星图等级
			int mapLeve = player.getPlayerInfo().getStarSoulLeve() == 0 ? 1 : player.getPlayerInfo().getStarSoulLeve();
			int mapMaxLeve = manager.getMapMaxLeve();
			// 当前星图星点下标
			int starLeve = player.getPlayerInfo().getSoulDot();
			// 玩家所需等级
			int playerLevel = manager.getPlayerLeveByMapLeve(mapLeve);
			if (playerLevel > player.getLevel()) {
				// 等级不足!
				throw new ProtocolException(TipMessages.LEVEL_INSUFFICIENT, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			// 获取当前星图星点最高等级
			int soulDotleve = manager.getSoulDotMaxLeveByMapLeve(mapLeve);
			if (mapMaxLeve == mapLeve && soulDotleve == starLeve + 1) {
				// 已达最高等级!
				throw new ProtocolException(TipMessages.STAR_SOUL_REACHED_MAX_LEVEL, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			// 消耗金币数
			int consumptionGold = manager.getConsumptionValueByMapLeveAndSoulDot(mapLeve, starLeve)[1];
			if (consumptionGold > player.getMoney()) {
				// 金币不足!
				throw new ProtocolException(TipMessages.GOLD_LACK, data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
			}
			// 消耗星魂碎片数
			int consumptionDebris = manager.getConsumptionValueByMapLeveAndSoulDot(mapLeve, starLeve)[0];
			if (consumptionDebris > player.getStarSoulDebrisNum()) {
				// 星魂碎片不足!
				throw new ProtocolException(TipMessages.STAR_SOUL_SHARDS_LACK, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			// 消耗金币
			ServiceManager.getManager().getPlayerService().updatePlayerGold(player, -consumptionGold, "星魂升级", "点亮星点");
			// 消耗星魂碎片一系列操作
			PlayerItemsFromShop playerItem = ServiceManager.getManager().getPlayerItemsFromShopService()
					.uniquePlayerItem(player.getId(), Common.STARSOULDEBRISID);
			if (null == playerItem || consumptionDebris > playerItem.getPLastNum()) {
				throw new Exception(ErrorMessages.TRATE_ITEMENOUGH_MESSAGE);
			}
			playerItem.setPLastNum(playerItem.getPLastNum() - consumptionDebris);
			ServiceManager.getManager().getPlayerItemsFromShopService().update(playerItem);
			player.setStarSoulDebrisNum(player.getStarSoulDebrisNum() - consumptionDebris);
			// TODO 更新玩家拥有的物品 数据暂未下发,待缓存优化完相关模块人员加上去
			ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, playerItem);
			ServiceManager.getManager().getPlayerItemsFromShopService()
					.saveGetItemRecord(player.getPlayer().getId(), Common.STARSOULDEBRISID, -1, consumptionDebris, 30, 1, null);
			// 获取星点显示的属性加成key和value
			String[] bonusValue = manager.getBonusValueByMapLeveAndSoulDot(mapLeve, starLeve);
			// 获取星图最高等级
			int getMapMaxLeve = manager.getMapMaxLeve();
			// 是否升级
			boolean upgrade = false;
			// 时候顶级
			boolean highestLeve = false;
			if (soulDotleve == starLeve + 1) {
				if (mapLeve < getMapMaxLeve) {// 需要升级
					upgrade = true;
					mapLeve++;// 星图等级增长
					starLeve = 0;// 星点归零
				} else { // 已经达到顶级
					highestLeve = true;
				}
			} else {
				starLeve++;// 星点升级
			}
			// 加成属性并更新玩家自身属性
			updatePlayerBonus(bonusValue[0], player, Integer.parseInt(bonusValue[1]), mapLeve, starLeve);
			// if (upgrade) {
			// GetStarSoul getStarSoul = new GetStarSoul(data.getSessionId(),
			// data.getSerial());
			// getStarSoul.setPlayerId(player.getId());
			// getStarSoul.setHandlerSource(session);
			// //结束后，重新返回星魂列表
			// new GetStarSoulHandler().handle(getStarSoul);
			// } else {
			LightNextStarOk lightNextStarlOk = new LightNextStarOk(data.getSessionId(), data.getSerial());
			lightNextStarlOk.setConsumptionDebris(manager.getConsumptionValueByMapLeveAndSoulDot(mapLeve, starLeve)[0]);
			lightNextStarlOk.setConsumptionGold(manager.getConsumptionValueByMapLeveAndSoulDot(mapLeve, starLeve)[1]);
			lightNextStarlOk.setDebrisTotalNumber(player.getStarSoulDebrisNum());
			lightNextStarlOk.setGoldTotalNumber(player.getMoney());
			lightNextStarlOk.setHighestLeve(highestLeve);
			lightNextStarlOk.setUpgrade(upgrade);
			updateStarSoulInfo(player);
			session.write(lightNextStarlOk);
			// }
			ServiceManager.getManager().getTaskService().soul(player);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	/**
	 * 根据属性key更新玩家属性值
	 * 
	 * @param bonusAttribute
	 * @param player
	 * @return
	 */
	private void updatePlayerBonus(String bonusAttribute, WorldPlayer player, int value, int mapLeve, int starLeve) {
		if (bonusAttribute.equals(Common.DEFENSE)) {
			player.getPlayerInfo().setDefense(player.getPlayerInfo().getDefense() + value);
		} else if (bonusAttribute.equals(Common.ATTACK)) {
			player.getPlayerInfo().setAttack(player.getPlayerInfo().getAttack() + value);
		} else if (bonusAttribute.equals(Common.DEFEND)) {
			player.getPlayerInfo().setDefend(player.getPlayerInfo().getDefend() + value);
		} else if (bonusAttribute.equals(Common.HP)) {
			player.getPlayerInfo().setHp(player.getPlayerInfo().getHp() + value);
		} else if (bonusAttribute.equals(Common.INJURYFREE)) {
			player.getPlayerInfo().setInjuryFree(player.getPlayerInfo().getInjuryFree() + value);
		} else if (bonusAttribute.equals(Common.WRECK_DEFENSE)) {
			player.getPlayerInfo().setWreckDefense(player.getPlayerInfo().getWreckDefense() + value);
		} else if (bonusAttribute.equals(Common.REDUCE_CRIT)) {
			player.getPlayerInfo().setReduceCrit(player.getPlayerInfo().getReduceCrit() + value);
		} else if (bonusAttribute.equals(Common.REDUCE_BURY)) {
			player.getPlayerInfo().setReduceBury(player.getPlayerInfo().getReduceBury() + value);
		} else if (bonusAttribute.equals(Common.FORCE)) {
			player.getPlayerInfo().setForce(player.getPlayerInfo().getForce() + value);
		} else if (bonusAttribute.equals(Common.ARMOR)) {
			player.getPlayerInfo().setArmor(player.getPlayerInfo().getArmor() + value);
		} else if (bonusAttribute.equals(Common.AGILITY)) {
			player.getPlayerInfo().setAgility(player.getPlayerInfo().getAgility() + value);
		} else if (bonusAttribute.equals(Common.PHYSIQUE)) {
			player.getPlayerInfo().setPhysique(player.getPlayerInfo().getPhysique() + value);
		} else if (bonusAttribute.equals(Common.LUCK)) {
			player.getPlayerInfo().setLuck(player.getPlayerInfo().getLuck() + value);
		}
		player.getPlayerInfo().setStarSoulLeve(mapLeve);
		player.getPlayerInfo().setSoulDot(starLeve);
		player.updatePlayerInfo();
		player.updateFight();
	}

	/**
	 * 更新玩家星魂等信息
	 * 
	 * @param player
	 */
	public void updateStarSoulInfo(WorldPlayer player) {
		Map<String, String> info = new HashMap<String, String>();
		info.put("starSoulLeve", player.getPlayerInfo().getStarSoulLeve() + "");
		info.put("soulDot", player.getPlayerInfo().getSoulDot() + "");
		ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, player);
	}
}