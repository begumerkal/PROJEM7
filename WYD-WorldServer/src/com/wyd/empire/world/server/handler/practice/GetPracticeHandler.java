package com.wyd.empire.world.server.handler.practice;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.practice.GetPractice;
import com.wyd.empire.protocol.data.practice.GetPracticeOk;
import com.wyd.empire.world.bean.PlayerInfo;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
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
 * 获取星魂图
 * 
 * @author 陈杰
 * @since JDK 1.6
 */
public class GetPracticeHandler implements IDataHandler {
	private static Logger log = Logger.getLogger(GetPracticeHandler.class);

	@SuppressWarnings("static-access")
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		PracticeService manager = ServiceManager.getManager().getPracticeService();
		GetPractice getPractice = (GetPractice) data;
		try {
			WorldPlayer player = session.getPlayer(data.getSessionId());
			if (getPractice.getPlayerId() != 0 && player.getId() != getPractice.getPlayerId()) {
				player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(getPractice.getPlayerId());
				if (player == null) {
					// 参数错误!
					throw new ProtocolException(TipMessages.STAR_SOUL_PARAMETER_ERROR, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
			}
			// 玩家修炼属性对应的等级
			int mapLeve = player.getPlayerInfo().getPracticeLeve();
			// 玩家拥有的低级勋章总数
			int lowMedalNumber = player.getMedalNum();
			// 玩家拥有的高级勋章总数
			int seniorMedlNumber = player.getSeniorMedalNum();
			// 玩家所需等级
			int playerLeve = manager.getPlayerLeveByMapLeve(mapLeve == 0 ? 1 : mapLeve);
			// 修炼点显示的加成属性（服务端传key，客户端显示value）
			String[] bonusAttribute = manager.getBonusAttributeByMapLeve(mapLeve);
			// 当前等级激活每日扣除勋章数
			int consumeMedalNumber = manager.getDayConsumptionNumberByMapLeve(mapLeve);
			// 当前等级属性加成值
			int[] playerBonus = manager.getBonusValueByMapLeve(mapLeve);
			// 属性下一等级加成值
			int[] bonusValue = manager.getBonusValueByMapLeve(mapLeve + 1);
			// 获得特殊标示
			Map<String, Integer> map = ServiceManager.getManager().getVersionService().getSpecialMark();
			// 使用勋章上限数
			int useLimitNumber;
			// 如果玩家配置每日使用勋章上限数默认为玩家当前等级
			if (map.get("useLimitNumber") == null) {
				useLimitNumber = player.getLevel();
			} else {
				useLimitNumber = map.get("useLimitNumber");
			}
			// 今日还可以使用勋章数
			int useTodayNumber;
			// 每日重置今日勋章可用数
			if (player.getPlayerInfo().getLastPracticeTime() == null
					|| DateUtil.isSameDate(player.getPlayerInfo().getLastPracticeTime(), new Date())) {
				useTodayNumber = useLimitNumber;
			} else {
				useTodayNumber = player.getPlayerInfo().getUseTodayNumber();
			}
			// 是否激活
			// boolean status = player.getPlayerInfo().getPracticeStatus();
			// 当前等级需要总的经验（进度条中的总数）
			int needExp = manager.getExpByMapLeve(mapLeve == 0 ? 1 : mapLeve);
			// // 玩家自身属性
			// int[] playerBonus = getPlayerBonus(bonusAttribute,
			// player.getPlayerInfo());
			// 属性索引
			int[] bonusIndex = manager.getMapIndexByMapLeve(mapLeve);
			// 获取当前修炼图最高等级
			int canLightLeve = manager.getMapMaxLeve();
			int index = bonusAttribute.length;
			// 玩家自身属性exp
			int[] playerBonusExp = new int[bonusAttribute.length];
			for (int i = 0; i < index; i++) {
				playerBonusExp[i] = player.getPracticeBonusExp().get(i);
			}
			GetPracticeOk getOk = new GetPracticeOk(data.getSessionId(), data.getSerial());
			getOk.setBonusAttribute(bonusAttribute);
			getOk.setBonusIndex(bonusIndex);
			getOk.setBonusValue(bonusValue);
			getOk.setPlayerBonus(playerBonus);
			getOk.setPlayerLeve(playerLeve);
			getOk.setBonusLeve(mapLeve == 0 ? 1 : mapLeve);
			getOk.setCanLightLeve(canLightLeve);
			getOk.setConsumeMedalNumber(consumeMedalNumber);
			getOk.setLowMedalNumber(lowMedalNumber);
			getOk.setNeedExp(needExp);
			getOk.setPlayerBonusExp(playerBonusExp);
			getOk.setSeniorMedlNumber(seniorMedlNumber);
			// getOk.setStatus(status);
			getOk.setUseLimitNumber(useLimitNumber);
			getOk.setUseTodayNumber(useTodayNumber);
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
	@SuppressWarnings("unused")
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