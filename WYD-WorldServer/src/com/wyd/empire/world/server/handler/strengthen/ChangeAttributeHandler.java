package com.wyd.empire.world.server.handler.strengthen;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.strengthen.ChangeAttribute;
import com.wyd.empire.protocol.data.strengthen.ChangeAttributeOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.StrongeRecord;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 属性转移
 * 
 * @author qiang
 */
public class ChangeAttributeHandler implements IDataHandler {
	Logger log = Logger.getLogger(ChangeAttributeHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		ChangeAttribute changeAttribute = (ChangeAttribute) data;
		PlayerItemsFromShop sourceItem = null;
		PlayerItemsFromShop targetItem = null;
		int useAmount = 0, playerId = player.getId();
		try {
			if (changeAttribute.getFirstItemid() == changeAttribute.getSecondItemId())
				return;
			sourceItem = ServiceManager.getManager().getPlayerItemsFromShopService()
					.uniquePlayerItem(playerId, changeAttribute.getFirstItemid());
			targetItem = ServiceManager.getManager().getPlayerItemsFromShopService()
					.uniquePlayerItem(playerId, changeAttribute.getSecondItemId());
			// 非装备
			if (!sourceItem.getShopItem().isEquipment() || !targetItem.getShopItem().isEquipment()) {
				return;
			}
			// 物品不一样
			if (!isSameType(sourceItem, targetItem)) {
				return;
			}
			if (sourceItem.getShopItem().getMove() == Common.CHANGE_ATTRIBUTE_N
					|| targetItem.getShopItem().getMove() == Common.CHANGE_ATTRIBUTE_N) {
				throw new ProtocolException(ErrorMessages.CHANGE_ATTRIBUTE_TIP_MSG, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			UseInfo useInfo = new UseInfo();
			int payresult = pay(player, sourceItem, useInfo);
			if (payresult == -2) {
				throw new ProtocolException(data, ErrorMessages.COMMUNITY_NEEDDEMOND_MESSAGE);
			} else if (payresult == -1) {
				throw new ProtocolException(data, ErrorMessages.COMMUNITY_NEEDGOLD_MESSAGE);
			}
			int sStar = 0;
			String sSkill = "", sGem = "";
			int strongLevel = sourceItem.getStrongLevel();
			// 强化等级单方迁移
			targetItem.updateStrongLevel(sourceItem.getStrongLevel());
			sourceItem.updateStrongLevel(0);
			// 技能单方面迁移
			targetItem.setWeapSkill1(sourceItem.getWeapSkill1());
			targetItem.setWeapSkill2(sourceItem.getWeapSkill2());
			targetItem.setSkillLock(sourceItem.getSkillLock());
			sourceItem.setWeapSkill1(0);
			sourceItem.setWeapSkill2(0);
			sourceItem.setSkillLock(0);
			// 拼接日志字段
			if (targetItem.getWeapSkill1() != 0) {
				sSkill += targetItem.getWeapSkill1();
			}
			if (targetItem.getWeapSkill2() != 0) {
				sSkill += "," + targetItem.getWeapSkill2();
			}
			// 星级单方面迁移
			targetItem.updateStars(sourceItem.getStars());
			targetItem.setStarExp(sourceItem.getStarExp());
			sourceItem.setStarExp(0);
			sourceItem.updateStars(0);
			log.info("装备转移记录：-玩家Id：" + player.getId() + "-装备Id：" + sourceItem.getShopItem().getId() + "，"
					+ targetItem.getShopItem().getId() + "-名称：" + sourceItem.getShopItem().getName() + "，"
					+ targetItem.getShopItem().getName() + "-时间：" + DateUtil.getCurrentDateTime() + "-消耗钻石数：" + useAmount + "-装备转移成功！");
			ServiceManager.getManager().getPlayerItemsFromShopService().update(sourceItem);
			ServiceManager.getManager().getPlayerItemsFromShopService().update(targetItem);
			ServiceManager.getManager().getPlayerItemsFromShopService().changeAttribute(player, sourceItem, targetItem);
			if (targetItem.getIsInUsed() || sourceItem.getIsInUsed()) {
				player.updateFight();
			}

			// 装备日志
			StrongeRecord strongeRecord = new StrongeRecord();
			strongeRecord.setPlayerId(player.getId());
			strongeRecord.setType(StrongeRecord.CHANGE_ATTRIBUTE);
			strongeRecord.setLevel(strongLevel);
			strongeRecord.setItemId(targetItem.getShopItem().getId());
			strongeRecord.setCreateTime(new Date());
			strongeRecord.setRemark("转化:" + sourceItem.getShopItem().getId() + " " + targetItem.getShopItem().getId());
			ServiceManager.getManager().getPlayerItemsFromShopService().save(strongeRecord);
			ChangeAttributeOk changeAttributeOk = new ChangeAttributeOk(data.getSessionId(), data.getSerial());
			session.write(changeAttributeOk);
			GameLogService.shift(player.getId(), player.getLevel(), sourceItem.getShopItem().getId(), targetItem.getShopItem().getId(),
					strongLevel, sSkill, sStar, sGem, 0, useInfo.getUseGold());
			ServiceManager.getManager().getTaskService().zywp(player, targetItem.getShopItem().getId());
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.info("装备转移记录：-玩家Id：" + player.getId() + "-装备Id：" + sourceItem.getShopItem().getId() + "，"
					+ targetItem.getShopItem().getId() + "-名称：" + sourceItem.getShopItem().getName() + "，"
					+ targetItem.getShopItem().getName() + "-时间：" + DateUtil.getCurrentDateTime() + "-消耗钻石数：" + useAmount + "-装备转移失败！");
			log.error(ex, ex);
			throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
					data.getType(), data.getSubType());
		}
	}

	private boolean isSameType(PlayerItemsFromShop sourceItem, PlayerItemsFromShop targetItem) {
		if (sourceItem.getShopItem().isWeapon() && targetItem.getShopItem().isWeapon()) {
			return true;
		}
		if (sourceItem.getShopItem().isRing() && targetItem.getShopItem().isRing()) {
			return true;
		}
		if (sourceItem.getShopItem().getType() == targetItem.getShopItem().getType()) {
			return true;
		}
		return false;
	}

	/**
	 * 支付
	 * 
	 * @param player
	 * @param sourceItem
	 * @return -1金币不够，－2钻石不够
	 * @throws Exception
	 */
	private int pay(WorldPlayer player, PlayerItemsFromShop sourceItem, UseInfo useInfo) throws Exception {
		Map<String, Integer> map = ServiceManager.getManager().getVersionService().getSpecialMark();
		Integer discount = map.get("discount");
		discount = discount == null ? 0 : discount;
		double gold = 1000;
		// 打折和向上取整
		gold = discount(gold, discount);
		if (player.getMoney() < gold) {
			return -1;
		} else {
			ServiceManager.getManager().getPlayerService().updatePlayerGold(player, -(int) gold, "装备属性转移", "");
			useInfo.setUseGold((int) gold);
		}
		return 0;
	}

	/**
	 * 打折，向上取整
	 * 
	 * @param v
	 *            打折前
	 * @param d
	 *            折扣（万份比）
	 * @return
	 */
	private double discount(double v, double d) {
		if (d > 0) {
			return Math.ceil(v * d / 10000d);
		}
		return Math.ceil(v);
	}

	private class UseInfo {
		int useGold = 0;

		public int getUseGold() {
			return useGold;
		}

		public void setUseGold(int useGold) {
			this.useGold = useGold;
		}
	}
}
