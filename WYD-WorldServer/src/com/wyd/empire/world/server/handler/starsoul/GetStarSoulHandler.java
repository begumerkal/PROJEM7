package com.wyd.empire.world.server.handler.starsoul;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.starsoul.GetStarSoul;
import com.wyd.empire.protocol.data.starsoul.GetStarSoulOk;
import com.wyd.empire.world.bean.PlayerInfo;
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
 * 获取星魂图
 * 
 * @author 陈杰
 * @since JDK 1.6
 */
public class GetStarSoulHandler implements IDataHandler {
	private static Logger log = Logger.getLogger(GetStarSoulHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		StarSoulService manager = ServiceManager.getManager().getStarSoulService();
		GetStarSoul getStarSoul = (GetStarSoul) data;
		try {
			WorldPlayer player = session.getPlayer(data.getSessionId());
			if (getStarSoul.getPlayerId() != 0 && player.getId() != getStarSoul.getPlayerId()) {
				player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(getStarSoul.getPlayerId());
				if (player == null) {
					// 参数错误!
					throw new ProtocolException(TipMessages.STAR_SOUL_PARAMETER_ERROR, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
			}
			// 玩家当前星图等级
			int mapLeve = player.getPlayerInfo().getStarSoulLeve() == 0 ? 1 : player.getPlayerInfo().getStarSoulLeve();
			// 当前星图星点等级
			int starLeve = player.getPlayerInfo().getSoulDot();
			// 玩家所需等级
			int playerLeve = manager.getPlayerLeveByMapLeve(mapLeve);
			// 星点显示的加成属性（服务端传key，客户端显示value）
			String[] bonusAttribute = manager.getBonusAttributeByMapLeve(mapLeve);
			// 属性加成值
			int[] bonusValue = manager.getBonusValueByMapLeve(mapLeve);
			// 消耗金币数
			int consumptionGold = manager.getConsumptionValueByMapLeveAndSoulDot(mapLeve, starLeve)[1];
			// 玩家拥有的金币总数
			int goldTotalNumber = player.getMoney();
			// 消耗星魂碎片数
			int consumptionDebris = manager.getConsumptionValueByMapLeveAndSoulDot(mapLeve, starLeve)[0];
			// 玩家拥有的星魂碎片总数
			int debrisTotalNumber = player.getStarSoulDebrisNum();
			// 玩家自身属性
			int[] playerBonus = getPlayerBonus(bonusAttribute, player.getPlayerInfo());
			// X坐标
			int[] coordinateX = manager.getCoordinateXByMapLeve(mapLeve);
			// Y坐标
			int[] coordinateY = manager.getCoordinateYByMapLeve(mapLeve);
			// 属性索引
			int[] bonusIndex = manager.getMapIndexByMapLeve(mapLeve);
			// 获取当前星图星点最高等级
			int soulDotleve = manager.getSoulDotMaxLeveByMapLeve(mapLeve);
			// 获取星图最高等级
			int MapMaxLeve = manager.getMapMaxLeve();
			// 是否顶级
			boolean highestLeve = false;
			if (mapLeve == MapMaxLeve && soulDotleve == starLeve + 1) {
				highestLeve = true;
				starLeve++;
			}
			GetStarSoulOk getOk = new GetStarSoulOk(data.getSessionId(), data.getSerial());
			getOk.setBonusAttribute(bonusAttribute);
			getOk.setBonusIndex(bonusIndex);
			getOk.setBonusValue(bonusValue);
			getOk.setConsumptionDebris(consumptionDebris);
			getOk.setConsumptionGold(consumptionGold);
			getOk.setCoordinateX(coordinateX);
			getOk.setCoordinateY(coordinateY);
			getOk.setDebrisTotalNumber(debrisTotalNumber);
			getOk.setGoldTotalNumber(goldTotalNumber);
			getOk.setMapLeve(mapLeve);
			getOk.setPlayerBonus(playerBonus);
			getOk.setPlayerLeve(playerLeve);
			getOk.setStarLeve(starLeve);
			getOk.setHighestLeve(highestLeve);
			getOk.setPlayerId(player.getId());
			session.write(getOk);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	/**
	 * 根据属性key获取玩家属性值
	 * 
	 * @param bonusAttribute
	 * @param player
	 * @return
	 */
	private int[] getPlayerBonus(String[] bonusAttribute, PlayerInfo player) {
		int[] bonus = new int[bonusAttribute.length];
		for (int i = 0; i < bonus.length; i++) {
			if (bonusAttribute[i].equals(Common.DEFENSE)) {
				bonus[i] = player.getDefense();
			} else if (bonusAttribute[i].equals(Common.ATTACK)) {
				bonus[i] = player.getAttack();
			} else if (bonusAttribute[i].equals(Common.DEFEND)) {
				bonus[i] = player.getDefend();
			} else if (bonusAttribute[i].equals(Common.HP)) {
				bonus[i] = player.getHp();
			} else if (bonusAttribute[i].equals(Common.INJURYFREE)) {
				bonus[i] = player.getInjuryFree();
			} else if (bonusAttribute[i].equals(Common.WRECK_DEFENSE)) {
				bonus[i] = player.getWreckDefense();
			} else if (bonusAttribute[i].equals(Common.REDUCE_CRIT)) {
				bonus[i] = player.getReduceCrit();
			} else if (bonusAttribute[i].equals(Common.REDUCE_BURY)) {
				bonus[i] = player.getReduceBury();
			} else if (bonusAttribute[i].equals(Common.FORCE)) {
				bonus[i] = player.getForce();
			} else if (bonusAttribute[i].equals(Common.ARMOR)) {
				bonus[i] = player.getArmor();
			} else if (bonusAttribute[i].equals(Common.AGILITY)) {
				bonus[i] = player.getAgility();
			} else if (bonusAttribute[i].equals(Common.PHYSIQUE)) {
				bonus[i] = player.getPhysique();
			} else if (bonusAttribute[i].equals(Common.LUCK)) {
				bonus[i] = player.getLuck();
			}
		}
		return bonus;
	}

}