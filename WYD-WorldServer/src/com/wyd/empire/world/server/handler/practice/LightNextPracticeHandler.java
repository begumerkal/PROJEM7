package com.wyd.empire.world.server.handler.practice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.practice.LightNextPractice;
import com.wyd.empire.protocol.data.practice.LightNextPracticeOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
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
 * 属性修炼加经验
 * 
 * @since JDK 1.6
 */
public class LightNextPracticeHandler implements IDataHandler {
	private static Logger log = Logger.getLogger(LightNextPracticeHandler.class);

	@SuppressWarnings("static-access")
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		PracticeService manager = ServiceManager.getManager().getPracticeService();
		LightNextPractice lightNextPractice = (LightNextPractice) data;
		try {
			WorldPlayer player = session.getPlayer(data.getSessionId());
			// 非正常操作或非正常玩家给予失败提示
			if (lightNextPractice.getBonusAttribute().equals(null) || lightNextPractice.getBonusAttribute().equals("")) {
				// 操作失败!
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			if (lightNextPractice.getUseNumber() < 1) {
				// 使用个数不可小于1!
				throw new ProtocolException(ErrorMessages.USE_NUMBER_IS_TOO_SMALL, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			// 玩家修炼属性对应的等级
			int mapLeve = player.getPlayerInfo().getPracticeLeve() == 0 ? 1 : player.getPlayerInfo().getPracticeLeve();
			// 玩家所需等级
			int playerLevel = manager.getPlayerLeveByMapLeve(mapLeve);
			if (playerLevel > player.getLevel()) {
				// 等级不足!
				throw new ProtocolException(TipMessages.LEVEL_INSUFFICIENT, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			// 玩家拥有的低级勋章总数
			int lowMedalNumber = player.getMedalNum();
			// 玩家拥有的高级勋章总数
			int seniorMedlNumber = player.getSeniorMedalNum();
			// 判断消耗勋章的数量是否足够
			switch (lightNextPractice.getItemId()) {
				case Common.BADGEID :
					if (lowMedalNumber < 1 || lowMedalNumber < lightNextPractice.getUseNumber()) {
						// 勋章不足!
						throw new ProtocolException(TipMessages.MEDAL_INSUFFICIENT, data.getSerial(), data.getSessionId(), data.getType(),
								data.getSubType());
					}
					break;
				case Common.SENIORBADGEID :
					if (seniorMedlNumber < 1 || seniorMedlNumber < lightNextPractice.getUseNumber()) {
						// 勋章不足!
						throw new ProtocolException(TipMessages.MEDAL_INSUFFICIENT, data.getSerial(), data.getSessionId(), data.getType(),
								data.getSubType());
					}
					break;
				default :// 参数错误
					// 操作失败!
					throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
			}
			// 满经验的属性字段","分割
			StringBuilder expFullAttribute = new StringBuilder(player.getPlayerInfo().getExpFullAttribute() == null ? "" : player
					.getPlayerInfo().getExpFullAttribute());
			// 该等级属性经验是否已满
			if (expFullAttribute.length() > 0) {
				// 避免某些单词重复
				String expFullStr = "," + expFullAttribute.toString();
				if (expFullStr.indexOf("," + lightNextPractice.getBonusAttribute() + ",") != -1) {
					// 该属性经验已满!
					throw new ProtocolException(TipMessages.PRACTICE_FULL_EXP, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
			}
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
			// 每日重置今日可勋章用数
			if (player.getPlayerInfo().getLastPracticeTime() == null
					|| !DateUtil.isSameDate(player.getPlayerInfo().getLastPracticeTime(), new Date())) {
				useTodayNumber = useLimitNumber;
			} else {
				useTodayNumber = player.getPlayerInfo().getUseTodayNumber();
			}
			// 是否已达今日勋章使用数上限
			if (useTodayNumber < 1) {
				// 今日勋章使用数已达上限!
				throw new ProtocolException(TipMessages.MEDAL_USE_LIMIT, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			// 是否升级
			boolean upgrade = false;
			// 获取修炼属性个数
			int AttributeNuber = manager.getAttributeNuberByMapLeve(mapLeve);
			// 勋章兑换经验值
			double medalExchangeExp = lightNextPractice.getItemId() == Common.BADGEID
					? manager.getLowMedalExchangeExpByMapLeve(mapLeve)
					: manager.getSeniorMedalExchangeExpByMapLeve(mapLeve);
			// 当前等级需要总的经验（进度条中的总数）
			int needExp = manager.getExpByMapLeve(mapLeve);
			// 修炼属性索引值
			int bonusIndex = getBonusIndex(lightNextPractice.getBonusAttribute(), mapLeve);
			// 当前修炼属性的经验值
			int exp = getExpValue(bonusIndex, player, mapLeve);
			// 当前修炼属性还需经验值
			double requiredExp = needExp - exp;
			// ------------【公式大致思路】----------
			// 如果 单个勋章经验值 × 玩家使用个数 <= 所需经验值 直接减去使用个数并加上获得的经验值
			// 否则 所需经验值 ÷ 单个勋章经验值 = 向上取整得到所需扣除勋章个数
			// 最终获得的经验
			int obtainExp = (int) medalExchangeExp * lightNextPractice.getUseNumber();
			// 玩家属性经验
			List<Integer> expList = player.getPracticeBonusExp();
			// 成功使用个数
			int useNubmer;
			// 最终获得的经验 <= 当前修炼属性还需经验值
			if (obtainExp <= requiredExp) {
				useNubmer = lightNextPractice.getUseNumber();
			} else {// 所需经验值 ÷ 单个勋章经验值 向上取整得到所需扣除勋章个数
				double num = requiredExp / medalExchangeExp;
				useNubmer = (int) Math.ceil(num);
				obtainExp = (int) requiredExp;
			}
			// 消耗勋章一系列操作
			PlayerItemsFromShop playerItem = ServiceManager.getManager().getPlayerItemsFromShopService()
					.uniquePlayerItem(player.getId(), lightNextPractice.getItemId());
			if (null == playerItem || useNubmer > playerItem.getPLastNum()) {
				throw new Exception(ErrorMessages.TRATE_ITEMENOUGH_MESSAGE);
			}
			playerItem.setPLastNum(playerItem.getPLastNum() - useNubmer);
			ServiceManager.getManager().getPlayerItemsFromShopService().update(playerItem);
			switch (lightNextPractice.getItemId()) {
				case Common.BADGEID :
					player.setMedalNum(player.getMedalNum() - useNubmer);
					break;
				case Common.SENIORBADGEID :
					player.setSeniorMedalNum(player.getSeniorMedalNum() - useNubmer);
					break;
			}
			useTodayNumber = useTodayNumber - useNubmer;
			player.getPlayerInfo().setUseTodayNumber(useTodayNumber);
			// TODO 更新玩家拥有的物品 数据暂未下发,待缓存优化完相关模块人员加上去
			ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, playerItem);
			ServiceManager.getManager().getPlayerItemsFromShopService()
					.saveGetItemRecord(player.getPlayer().getId(), lightNextPractice.getItemId(), -1, useNubmer, 33, 1, null);
			if (expList.size() == 0) {
				expList.add(0);
				expList.add(0);
				expList.add(0);
				expList.add(0);
				expList.add(0);
			}
			// 更改属性的经验值
			expList.set(bonusIndex, exp += obtainExp);
			// 属性加成值
			int bonusValue = 0;
			// 如果属性经验已满
			if (obtainExp == requiredExp) {
				// 记录已满经验的属性
				expFullAttribute.append(lightNextPractice.getBonusAttribute()).append(",");
				bonusValue = manager.getBonusValueByMapLeve(mapLeve, bonusIndex);
				// 本等级全部属性都为满经验
				if (expFullAttribute.toString().split(",").length == AttributeNuber && mapLeve + 1 <= manager.getMapMaxLeve()) {
					mapLeve++;
					AttributeNuber = manager.getAttributeNuberByMapLeve(mapLeve);
					expFullAttribute = new StringBuilder("");
					expList = new ArrayList<Integer>();
					for (int i = 0; i < AttributeNuber; i++) {
						expList.add(0);
					}

					upgrade = true;
				}
			}
			// //修炼系统激活状态下每天扣取相应的勋章数
			// if (!player.getPlayerInfo().getPracticeStatus()) {
			// //当前等级激活每日扣除勋章数
			// int consumeMedalNumber =
			// ServiceManager.getManager().getPracticeService().getDayConsumptionNumberByMapLeve(mapLeve);
			// if (player.getMedalNum() > consumeMedalNumber) {
			// //消耗勋章一系列操作
			// if (null==playerItem || consumeMedalNumber >
			// playerItem.getPLastNum()) {
			// //更新状态
			// player.getPlayerInfo().setPracticeStatus(false);
			// }
			// playerItem.setPLastNum(playerItem.getPLastNum() -
			// consumeMedalNumber);
			// ServiceManager.getManager().getPlayerItemsFromShopService().update(playerItem);
			// player.setMedalNum(player.getMedalNum() - consumeMedalNumber);
			// //TODO 更新玩家拥有的物品 数据暂未下发,待缓存优化完相关模块人员加上去
			// ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player,
			// playerItem);
			// ServiceManager.getManager().getPlayerItemsFromShopService().saveGetItemRecord(player.getPlayer().getId(),
			// Common.BADGEID, -1, consumeMedalNumber, 34, 1, null);
			// //更新状态
			// player.getPlayerInfo().setPracticeStatus(true);
			// }
			// }
			player.setPracticeBonusExp(expList);
			player.getPlayerInfo().setExpFullAttribute(expFullAttribute.toString());
			String expStr = expList.toString();
			// System.out.println(expStr.substring(1, expStr.length() -
			// 1).replace(" ", ""));
			player.getPlayerInfo().setPracticeAttributeExp(expStr.substring(1, expStr.length() - 1).replace(" ", ""));
			updatePlayerBonus(lightNextPractice.getBonusAttribute(), player, bonusValue, mapLeve);
			// if (upgrade) {
			// GetPractice getPractice = new GetPractice(data.getSessionId(),
			// data.getSerial());
			// getPractice.setPlayerId(player.getId());
			// getPractice.setHandlerSource(session);
			// //结束后，重新返回修炼列表
			// new GetPracticeHandler().handle(getPractice);
			// } else {
			LightNextPracticeOk lightNextPracticeOk = new LightNextPracticeOk(data.getSessionId(), data.getSerial());
			lightNextPracticeOk.setLowMedalnumber(player.getMedalNum());
			lightNextPracticeOk.setSeniorMedlNumber(player.getSeniorMedalNum());
			lightNextPracticeOk.setUseTodayNumber(useTodayNumber);
			lightNextPracticeOk.setPlayerBonusExp(exp);
			lightNextPracticeOk.setUpgrade(upgrade);
			// lightNextPracticeOk.setStatus(player.getPlayerInfo().getPracticeStatus());
			updateMedlNumber(player);
			session.write(lightNextPracticeOk);
			// }
			ServiceManager.getManager().getTaskService().practice(player);
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
	private void updatePlayerBonus(String bonusAttribute, WorldPlayer player, int value, int mapLeve) {
		if (bonusAttribute.equals(Common.DEFENSE)) {
			player.getPlayerInfo().setPracticeDefense(player.getPlayerInfo().getPracticeDefense() + value);
		} else if (bonusAttribute.equals(Common.ATTACK)) {
			player.getPlayerInfo().setPracticeAttack(player.getPlayerInfo().getPracticeAttack() + value);
		} else if (bonusAttribute.equals(Common.DEFEND)) {
			player.getPlayerInfo().setPracticeDefend(player.getPlayerInfo().getPracticeDefend() + value);
		} else if (bonusAttribute.equals(Common.HP)) {
			player.getPlayerInfo().setPracticeHp(player.getPlayerInfo().getPracticeHp() + value);
		} else if (bonusAttribute.equals(Common.INJURYFREE)) {
			player.getPlayerInfo().setPracticeInjuryFree(player.getPlayerInfo().getPracticeInjuryFree() + value);
		} else if (bonusAttribute.equals(Common.WRECK_DEFENSE)) {
			player.getPlayerInfo().setPracticeWreckDefense(player.getPlayerInfo().getPracticeWreckDefense() + value);
		} else if (bonusAttribute.equals(Common.REDUCE_CRIT)) {
			player.getPlayerInfo().setPracticeReduceCrit(player.getPlayerInfo().getPracticeReduceCrit() + value);
		} else if (bonusAttribute.equals(Common.REDUCE_BURY)) {
			player.getPlayerInfo().setPracticeReduceBury(player.getPlayerInfo().getPracticeReduceBury() + value);
		} else if (bonusAttribute.equals(Common.FORCE)) {
			player.getPlayerInfo().setPracticeForce(player.getPlayerInfo().getPracticeForce() + value);
		} else if (bonusAttribute.equals(Common.ARMOR)) {
			player.getPlayerInfo().setPracticeArmor(player.getPlayerInfo().getPracticeArmor() + value);
		} else if (bonusAttribute.equals(Common.AGILITY)) {
			player.getPlayerInfo().setPracticeAgility(player.getPlayerInfo().getPracticeAgility() + value);
		} else if (bonusAttribute.equals(Common.PHYSIQUE)) {
			player.getPlayerInfo().setPracticePhysique(player.getPlayerInfo().getPracticePhysique() + value);
		} else if (bonusAttribute.equals(Common.LUCK)) {
			player.getPlayerInfo().setPracticeLuck(player.getPlayerInfo().getPracticeLuck() + value);
		}
		// player.getPlayerInfo().setExpFullAttribute(expFullAttribute);
		// player.getPlayerInfo().setPracticeAttributeExp(exps);
		player.getPlayerInfo().setPracticeLeve(mapLeve);
		player.getPlayerInfo().setLastPracticeTime(new Date());
		player.updatePlayerInfo();
		player.updateFight();
	}

	/**
	 * 获取属性索引值
	 * 
	 * @param bonusAttribute
	 * @param mapLeve
	 * @return
	 */
	@SuppressWarnings("static-access")
	private int getBonusIndex(String bonusAttribute, int mapLeve) {
		String[] bonus = ServiceManager.getManager().getPracticeService().getBonusAttributeByMapLeve(mapLeve);
		int length = bonus.length;
		for (int i = 0; i < length; i++) {
			if (bonusAttribute.equals(bonus[i])) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * 根据索引获取相应的属性经验值
	 * 
	 * @param index
	 * @param player
	 * @param mapLeve
	 * @return
	 */
	private int getExpValue(int index, WorldPlayer player, int mapLeve) {
		List<Integer> list = player.getPracticeBonusExp();
		if (list.size() == 0) {
			list.add(0);
			list.add(0);
			list.add(0);
			list.add(0);
			list.add(0);
			player.setPracticeBonusExp(list);
			return 0;
		} else {
			return list.get(index);
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
		info.put("practiceAttributeExp", player.getPlayerInfo().getPracticeAttributeExp() + "");
		info.put("practiceLeve", player.getPlayerInfo().getPracticeLeve() + "");
		info.put("seniorMedlNumber", player.getSeniorMedalNum() + "");
		ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, player);
	}
}