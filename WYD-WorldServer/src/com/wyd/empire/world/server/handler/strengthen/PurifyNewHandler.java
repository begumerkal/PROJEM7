package com.wyd.empire.world.server.handler.strengthen;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.strengthen.PurifyNew;
import com.wyd.empire.protocol.data.strengthen.PurifyOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.StrongeRecord;
import com.wyd.empire.world.bean.WeapSkill;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerItemsFromShopService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 新重铸 1，旧锁在重铸时候锁定状态不会消失（只有在拆锁时候锁定状态才会消失） 2，新锁在重铸时候会被消耗掉一个锁，并锁定状态在重铸完后会消失。
 * 3，与旧协议一大区别是，新协议锁定的技能是由客户端传递过来的。
 * 
 * @author zengxc 2013-10-10
 */
public class PurifyNewHandler implements IDataHandler {
	Logger log = Logger.getLogger(PurifyNewHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		PurifyNew purify = (PurifyNew) data;
		IPlayerItemsFromShopService playerItemsFromShopService = ServiceManager.getManager().getPlayerItemsFromShopService();
		try {
			PurifyOk purifyOk = new PurifyOk(data.getSessionId(), data.getSerial());
			int stoneId = purify.getItemId()[0];// 淬焰
			int lockItemId = purify.getItemId()[1];// 技能锁
			int lockSkillId = purify.getLockId(); // 锁上的技能ID
			PlayerItemsFromShop pifs = playerItemsFromShopService.uniquePlayerItem(player.getId(), purify.getWapenId());
			if (pifs == null || pifs.getShopItem().getMove() == 1) {
				throw new ProtocolException(TipMessages.STRENGTHEN_CANNOTAGAIN_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			String bSkill = pifs.getWeapSkill1() + "," + pifs.getWeapSkill2();
			int useDiamond = 0, useGold = 0;
			PlayerItemsFromShop stone = playerItemsFromShopService.uniquePlayerItem(player.getId(), stoneId);
			PlayerItemsFromShop lock = null;
			Map<String, Integer> map = ServiceManager.getManager().getVersionService().getSpecialMark();
			int startLevel1 = map.get("strongeLevel1");
			if (null == stone || stone.getPLastNum() <= 0) {
				throw new ProtocolException(TipMessages.ITEMNOTENOUGH, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			if (pifs.getStrongLevel() < startLevel1) {
				throw new ProtocolException(ErrorMessages.PURIFY_TOO_LOW, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			// 是否有锁定的技能
			if (lockSkillId > 0) {
				// 只有新锁才会传锁的物品ID过来，没传物品ID说明使用的是旧锁
				if (lockItemId > 0) {
					lock = playerItemsFromShopService.uniquePlayerItem(player.getId(), lockItemId);
					if (lock == null || lock.getPLastNum() <= 0) {
						throw new ProtocolException(TipMessages.ITEMNOTENOUGH, data.getSerial(), data.getSessionId(), data.getType(),
								data.getSubType());
					}
				} else if (pifs.getSkillLock() == 0) {// 如果没有被锁定的技能说明是用旧锁第一次重铸，所以需要消耗掉一把旧锁
					lockItemId = 381;
					lock = playerItemsFromShopService.uniquePlayerItem(player.getId(), lockItemId);
					if (lock == null || lock.getPLastNum() <= 0) {
						throw new ProtocolException(TipMessages.ITEMNOTENOUGH, data.getSerial(), data.getSessionId(), data.getType(),
								data.getSubType());
					}
				}
			}
			// 要锁上技能就一定要消耗掉锁
			if (lock != null) {
				lock.setPLastNum(lock.getPLastNum() - 1 < 0 ? 0 : lock.getPLastNum() - 1);
				playerItemsFromShopService.update(lock);
				ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, lock);
				playerItemsFromShopService.saveGetItemRecord(player.getPlayer().getId(), lock.getShopItem().getId(), -1, -1, -6, 1, null);
				pifs.setSkillLock(lockSkillId);
			} else {
				lockSkillId = pifs.getSkillLock();
			}
			// 随机获得2个技能
			int randomNum = ServiceUtils.getRandomNum(0, 10000);
			WeapSkill weapSkill1 = null;
			WeapSkill weapSkill2 = null;
			// 判断技能一是否被锁定了 ，没锁定则重新取一个技能
			if (pifs.getWeapSkill1() != 0 && lockSkillId != pifs.getWeapSkill1()) {
				weapSkill1 = ServiceManager.getManager().getStrengthenService().getWeapSkillByRandom(randomNum);
				pifs.setWeapSkill1(weapSkill1.getId());
			} else {
				weapSkill1 = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill1());
			}
			// 判断技能二是否被锁定了 ，没锁定则重新取一个技能
			if (pifs.getWeapSkill2() != 0 && lockSkillId != pifs.getWeapSkill2()) {
				do {
					randomNum = ServiceUtils.getRandomNum(0, 10000);
					weapSkill2 = ServiceManager.getManager().getStrengthenService().getWeapSkillByRandom(randomNum);
					pifs.setWeapSkill2(weapSkill2.getId());
				} while (weapSkill1.getType().intValue() == weapSkill2.getType().intValue());
			} else {
				weapSkill2 = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill2());
				// 技能一和技能二不能一样
				if (null != weapSkill2 && weapSkill1.getType().intValue() == weapSkill2.getType().intValue()) {
					do {
						randomNum = ServiceUtils.getRandomNum(0, 10000);
						weapSkill1 = ServiceManager.getManager().getStrengthenService().getWeapSkillByRandom(randomNum);
						pifs.setWeapSkill1(weapSkill1.getId());
					} while (weapSkill1.getType().intValue() == weapSkill2.getType().intValue());
				}
			}

			purifyOk.setSkillId1(weapSkill1 != null ? weapSkill1.getId() : 0);
			purifyOk.setSkillId2(weapSkill2 != null ? weapSkill2.getId() : 0);
			session.write(purifyOk);
			if (player.getHead().getId().intValue() == pifs.getId() || player.getFace().getId().intValue() == pifs.getId()
					|| player.getBody().getId().intValue() == pifs.getId() || player.getWeapon().getId().intValue() == pifs.getId()
					|| (player.getWing() != null && player.getWing().getId().intValue() == pifs.getId())
					|| (player.getRing_L() != null && player.getRing_L().getId().intValue() == pifs.getId())
					|| (player.getRing_R() != null && player.getRing_R().getId().intValue() == pifs.getId())
					|| (player.getNecklace() != null && player.getNecklace().getId().intValue() == pifs.getId())) {
				player.updateFight();
			}
			// ===消耗材料===
			stone.setPLastNum(stone.getPLastNum() - 1 < 0 ? 0 : stone.getPLastNum() - 1);
			playerItemsFromShopService.update(stone);
			ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, stone);
			playerItemsFromShopService.saveGetItemRecord(player.getPlayer().getId(), stone.getShopItem().getId(), -1, -1, -6, 1, null);
			// lockItemId等于381表是是旧锁，旧锁锁定状态不会消失
			if (lockItemId != 381 && lockItemId != 0) {
				pifs.setSkillLock(0);
			}
			// ===end===
			playerItemsFromShopService.update(pifs);
			ServiceManager.getManager().getPlayerItemsFromShopService().StrongItem(player, pifs);
			StrongeRecord strongeRecord = new StrongeRecord();
			strongeRecord.setPlayerId(player.getId());
			strongeRecord.setType(2);
			strongeRecord.setLevel(weapSkill1.getLevel());
			strongeRecord.setCreateTime(new Date());
			strongeRecord.setItemId(pifs.getShopItem().getId());
			strongeRecord.setRemark("洗练技能1：" + weapSkill1.getId());
			playerItemsFromShopService.save(strongeRecord);
			if (null != weapSkill2) {
				StrongeRecord strongeRecord2 = new StrongeRecord();
				strongeRecord2.setPlayerId(player.getId());
				strongeRecord2.setType(2);
				strongeRecord2.setLevel(weapSkill2.getLevel());
				strongeRecord2.setCreateTime(new Date());
				strongeRecord.setItemId(pifs.getShopItem().getId());
				strongeRecord2.setRemark("洗练技能2：" + weapSkill2.getId());
				playerItemsFromShopService.save(strongeRecord2);
			}
			ServiceManager.getManager().getTaskService().czwp(player, pifs.getShopItem().getId());
			String aSkill = pifs.getWeapSkill1() + "," + pifs.getWeapSkill2();
			GameLogService.rehab(player.getId(), player.getLevel(), purify.getWapenId(), bSkill, aSkill, stoneId, useDiamond, useGold);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
