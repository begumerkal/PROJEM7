package com.wyd.empire.world.server.handler.strengthen;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.strengthen.Merge;
import com.wyd.empire.protocol.data.strengthen.MergeOk;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.StrongeRecord;
import com.wyd.empire.world.bean.Successrate;
import com.wyd.empire.world.bean.VipRate;
import com.wyd.empire.world.bean.WeapSkill;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerItemsFromShopService;
import com.wyd.empire.world.server.service.base.IShopItemService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class MergeHandler implements IDataHandler {
	/** 合成所需金币 */
	public static final int MERGEGOLD = 2000;
	Logger log = Logger.getLogger(MergeHandler.class);

	/**
	 * 强化、合成
	 */
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		Merge merge = (Merge) data;
		try {
			MergeOk mergeOk = new MergeOk(data.getSessionId(), data.getSerial());
			int stone[] = merge.getItemId();
			ShopItem newStone = null;
			ServiceManager manager = ServiceManager.getManager();
			IShopItemService shopItemService = manager.getShopItemService();
			IPlayerItemsFromShopService playerItemsFromShopService = manager.getPlayerItemsFromShopService();
			List<ShopItem> stoneList = shopItemService.getEntityByIds(stone);
			PlayerItemsFromShop pifsUsed = null;
			String stoneStr = " ";
			// 检查玩家物品是否足够
			Map<Integer, Integer> stoneMap = new HashMap<Integer, Integer>();
			for (int i = 0; i < stone.length; i++) {
				int count = 0;
				if (stoneMap.containsKey(stone[i])) {
					count = stoneMap.get(stone[i]);
				}
				count++;
				stoneMap.put(stone[i], count);
				stoneStr += " " + stone[i];
			}
			Set<Integer> stoneSet = stoneMap.keySet();
			for (Integer stoneId : stoneSet) {
				int count = stoneMap.get(stoneId);
				pifsUsed = ServiceManager.getManager().getPlayerItemsFromShopService().uniquePlayerItem(player.getId(), stoneId);
				if (pifsUsed == null || pifsUsed.getPLastNum() < count) {
					throw new ProtocolException(TipMessages.ITEMNOTENOUGH, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
			}
			// ==================================
			// *************碎片合成****************
			// ==================================
			if (isScrapMerge(stoneList)) {
				MergeScrapHandler scrapHandler = new MergeScrapHandler();
				scrapHandler.handle(data);
				return;
			}
			// ===================================
			// ===================================
			int addVipRate = 0;
			if (player.isVip()) {
				VipRate vr = ServiceManager.getManager().getPlayerItemsFromShopService().getVipRateById(player.getPlayer().getVipLevel());
				if (null != vr) {
					addVipRate = vr.getStrongRate() * 100;
				}
			}
			StrongeRecord strongeRecord = new StrongeRecord();
			// 判断本次操作是强化还是合成
			if (merge.getWapenId() != -1) {// 强化
				Consortia cs = ServiceManager.getManager().getConsortiaService().getConsortiaById(player.getGuildId());
				int addCsRate = cs == null ? 0 : (int) ServiceManager.getManager().getBuffService()
						.getAddition(player, cs.getLevel() * 50, Buff.CSTRONG);
				// 获得特殊标示
				int startLevel1 = ServiceManager.getManager().getVersionService().getStrongeLevel1();
				int startLevel2 = ServiceManager.getManager().getVersionService().getStrongeLevel2();
				int topLevel = ServiceManager.getManager().getVersionService().getStrongeTopLevel();
				PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService()
						.uniquePlayerItem(player.getId(), merge.getWapenId());
				if (pifs == null || pifs.getShopItem().getMove() == Common.CHANGE_ATTRIBUTE_N) {
					throw new ProtocolException(ErrorMessages.CANNOT_STRONG, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
				Successrate sr = (Successrate) ServiceManager.getManager().getStrengthenService()
						.get(Successrate.class, pifs.getStrongLevel() + 1);
				int needMoney = (int) ServiceManager.getManager().getBuffService().getAddition(player, sr.getGold(), Buff.CGOLDLOW);
				if (player.getMoney() < needMoney) {
					throw new ProtocolException(ErrorMessages.PLAYER_INOC_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
				// 获得强化成功率
				Map<String, Integer> scMap = Common.getSuccessMap();
				int sumRate = 0;
				int luckyRate = 0;
				int protectMark = 0;
				int realMark = 0;
				int stoneNum = 0;
				int clientSumRate = 0;
				if (pifs.getStrongLevel() >= topLevel) {
					throw new ProtocolException(ErrorMessages.STRENGTHEN_LEVEL_MESSAGE, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
				for (int i = 0; i < stone.length; i++) {
					switch (stone[i]) {
						case Common.LVL1STONE :
							sumRate += scMap.get((pifs.getStrongLevel() + 1) + "-1");
							clientSumRate += scMap.get((pifs.getStrongLevel() + 1) + "-1f");
							break;
						case Common.LVL2STONE :
							sumRate += scMap.get((pifs.getStrongLevel() + 1) + "-2");
							clientSumRate += scMap.get((pifs.getStrongLevel() + 1) + "-2f");
							break;
						case Common.LVL3STONE :
							sumRate += scMap.get((pifs.getStrongLevel() + 1) + "-3");
							clientSumRate += scMap.get((pifs.getStrongLevel() + 1) + "-3f");
							break;
						case Common.LVL4STONE :
							sumRate += scMap.get((pifs.getStrongLevel() + 1) + "-4");
							clientSumRate += scMap.get((pifs.getStrongLevel() + 1) + "-4f");
							break;
						case Common.LVL5STONE :
							sumRate += scMap.get((pifs.getStrongLevel() + 1) + "-5");
							clientSumRate += scMap.get((pifs.getStrongLevel() + 1) + "-5f");
							realMark = 1;
							++stoneNum;
							break;
						case Common.LVL1REALSTONE :
							sumRate += scMap.get((pifs.getStrongLevel() + 1) + "-r1");
							clientSumRate += scMap.get((pifs.getStrongLevel() + 1) + "-r1f");
							break;
						case Common.LVL2REALSTONE :
							sumRate += scMap.get((pifs.getStrongLevel() + 1) + "-r2");
							clientSumRate += scMap.get((pifs.getStrongLevel() + 1) + "-r2f");
							break;
						case Common.LVL3REALSTONE :
							sumRate += scMap.get((pifs.getStrongLevel() + 1) + "-r3");
							clientSumRate += scMap.get((pifs.getStrongLevel() + 1) + "-r3f");
							break;
						case Common.LVL4REALSTONE :
							sumRate += scMap.get((pifs.getStrongLevel() + 1) + "-r4");
							clientSumRate += scMap.get((pifs.getStrongLevel() + 1) + "-r4f");
							break;
						case Common.LVL5REALSTONE :
							sumRate += scMap.get((pifs.getStrongLevel() + 1) + "-r5");
							clientSumRate += scMap.get((pifs.getStrongLevel() + 1) + "-r5f");
							realMark = 1;
							++stoneNum;
							break;
						case Common.LVL1LUCK :
						case Common.LVL2LUCK :
							ShopItem si = ServiceManager.getManager().getShopItemService().getShopItemById(stone[i]);
							luckyRate = Integer.parseInt(si.getStrengthen().split("=")[4].split(",")[0]);
							break;
						case Common.PROTECTSTONE :
							protectMark = 1;
							break;
					}
				}
				double successAddition = ((10000.0f + luckyRate + addCsRate + addVipRate) / 10000.0f);
				int strengthenRate = (int) (sumRate * successAddition);
				int randomNum = ServiceUtils.getRandomNum(0, 10000);
				int pifsLevel = pifs.getStrongLevel() + 1;// 强化的目标等级
				// 客户端计算成功率
				int lt = clientSumRate > 309 ? 309 : clientSumRate;
				int clientRate = (int) (clientSumRate + (10000 - clientSumRate) * 0.05 * (lt / 309));
				clientRate *= ((10000.0f + luckyRate) / 10000.0f);// 幸运石加成
				clientRate *= ((10000.0f + addCsRate + addVipRate) / 10000.0f);// 公会和VIP加成
				ServiceManager.getManager().getPlayerService().updatePlayerGold(player, -needMoney, "强化", "");
				if (randomNum > strengthenRate && (sr.getMissTimes() == -1 || sr.getMissTimes() > pifs.getMissTimes())
						&& clientRate < 10000) {// 失败
					mergeOk.setResult(false);
					mergeOk.setContent(ErrorMessages.STRENGTHEN_FAIL_MESSAGE);
					if ((!(pifs.getStrongLevel() < Common.STRFAILLVL)) && protectMark == 0) {
						pifs.updateStrongLevel(pifs.getStrongLevel() - 1);
					}
					pifsLevel = pifs.getStrongLevel();
					// 清除洗练技能
					if (pifs.getWeapSkill2() != 0 && pifs.getStrongLevel() < startLevel2 && pifs.getStrongLevel() >= startLevel1) {
						if (pifs.getSkillLock() == pifs.getWeapSkill1()) {
							pifs.setWeapSkill2(0);
							pifs.setSkillLock(0);
						} else if (pifs.getSkillLock() == pifs.getWeapSkill2()) {
							pifs.setWeapSkill1(pifs.getWeapSkill2());
							pifs.setWeapSkill2(0);
							pifs.setSkillLock(0);
						} else {
							WeapSkill ws1 = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill1());
							WeapSkill ws2 = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill2());
							if (ws1.getLevel() >= ws2.getLevel()) {
								pifs.setWeapSkill2(0);
								pifs.setSkillLock(0);
							} else {
								pifs.setWeapSkill1(pifs.getWeapSkill2());
								pifs.setWeapSkill2(0);
								pifs.setSkillLock(0);
							}
						}
					} else if (pifs.getStrongLevel() < startLevel1) {
						pifs.setWeapSkill1(0);
					}
					if (realMark == 1 && stoneNum > 2 && sr.getMissTimes() != -1) {
						pifs.setMissTimes(pifs.getMissTimes() + 1);
					}
					ServiceManager.getManager().getPlayerItemsFromShopService().update(pifs);
					// 更新玩家拥有的物品
					ServiceManager.getManager().getPlayerItemsFromShopService().StrongItem(player, pifs);
					log.info("武器强化记录：强化等级：" + pifsLevel + "-玩家Id：" + player.getId() + "-装备Id：" + pifs.getShopItem().getId() + "-名称："
							+ pifs.getShopItem().getName() + "-时间：" + DateUtil.getCurrentDateTime() + "-状态：0-额外道具：" + stoneStr);
					strongeRecord.setPlayerId(player.getId());
					strongeRecord.setType(StrongeRecord.STRONGE_FAIL);
					strongeRecord.setLevel(pifsLevel);
					strongeRecord.setItemId(pifs.getShopItem().getId());
					strongeRecord.setCreateTime(new Date());
					strongeRecord.setRemark("强化:" + stoneStr);
					ServiceManager.getManager().getPlayerItemsFromShopService().save(strongeRecord);
				} else {// 成功
					mergeOk.setResult(true);
					mergeOk.setContent(ErrorMessages.STRENGTHEN_SUCCESS_MESSAGE);
					pifs.updateStrongLevel(pifs.getStrongLevel() + 1);
					pifsLevel = pifs.getStrongLevel();
					if (pifs.getShopItem().isWeapon()) {
						// 获得洗练技能
						ServiceManager.getManager().getStrengthenService().giveWeapSkillToPlayer(pifs.getStrongLevel(), pifs);
					}
					pifs.setMissTimes(0);
					ServiceManager.getManager().getPlayerItemsFromShopService().update(pifs);
					// 更新玩家拥有的物品
					ServiceManager.getManager().getPlayerItemsFromShopService().StrongItem(player, pifs);
					if (pifsLevel > ServiceManager.getManager().getVersionService().getVersion().getStrengthenLevel()) {
						ServiceManager
								.getManager()
								.getChatService()
								.sendBulletinToWorld(
										TipMessages.STRENGTHENNOTICE1.replace("***", player.getName())
												.replace("###", pifs.getShopItem().getName()).replace("@@", pifsLevel + ""),
										player.getName(), false);
					}
					log.info("武器强化记录：强化等级：" + pifsLevel + "-玩家Id：" + player.getId() + "-装备Id：" + pifs.getShopItem().getId() + "-名称："
							+ pifs.getShopItem().getName() + "-时间：" + DateUtil.getCurrentDateTime() + "-状态：1-额外道具：" + stoneStr);
					strongeRecord.setPlayerId(player.getId());
					strongeRecord.setType(StrongeRecord.STRONGE_SUCCESS);
					strongeRecord.setLevel(pifsLevel);
					strongeRecord.setItemId(pifs.getShopItem().getId());
					strongeRecord.setCreateTime(new Date());
					strongeRecord.setRemark("强化:" + stoneStr);
					ServiceManager.getManager().getPlayerItemsFromShopService().save(strongeRecord);
				}
				mergeOk.setItemId(pifs.getShopItem().getId());
				mergeOk.setName(pifs.getShopItem().getName());
				mergeOk.setIcon(pifs.getShopItem().getIcon());
				mergeOk.setItemMainType(pifs.getShopItem().getType());
				mergeOk.setItemSubType(pifs.getShopItem().getSubtype());
				mergeOk.setItemNum(1);
				mergeOk.setExpired(false);
				mergeOk.setStrengthoneLevel(pifs.getStrongLevel());
				mergeOk.setAttack(pifs.getShopItem().getAddAttack() + pifs.getPAddAttack());
				mergeOk.setAttackArea(pifs.getShopItem().getAttackArea());
				mergeOk.setAddHP(pifs.getShopItem().getAddHp() + pifs.getPAddHp());
				mergeOk.setPower(pifs.getShopItem().getAddPower());
				mergeOk.setDefend(pifs.getShopItem().getAddDefend() + pifs.getPAddDefend());
				if (player.getHead().getId().intValue() == pifs.getId() || player.getFace().getId().intValue() == pifs.getId()
						|| player.getBody().getId().intValue() == pifs.getId() || player.getWeapon().getId().intValue() == pifs.getId()
						|| (player.getWing() != null && player.getWing().getId().intValue() == pifs.getId())
						|| (player.getRing_L() != null && player.getRing_L().getId().intValue() == pifs.getId())
						|| (player.getRing_R() != null && player.getRing_R().getId().intValue() == pifs.getId())
						|| (player.getNecklace() != null && player.getNecklace().getId().intValue() == pifs.getId())) {
					player.updateFight();
				}
				if (mergeOk.isResult()) {// 成功才记录成就
					ServiceManager.getManager().getTitleService().strengthen(player, pifs.getStrongLevel());
				}
				ServiceManager.getManager().getTaskService().strengthen(player, pifs.getShopItem().getId(), pifs.getStrongLevel());
				GameLogService.strengthen(player.getId(), player.getLevel(), merge.getWapenId(), mergeOk.isResult(), pifs.getStrongLevel(),
						stoneStr, 0, needMoney);
			} else {// 强化石合成、碎片合成
				Consortia cs = ServiceManager.getManager().getConsortiaService().getConsortiaById(player.getGuildId());
				int addCsRate = cs == null ? 0 : (int) ServiceManager.getManager().getBuffService()
						.getAddition(player, cs.getLevel() * 50, Buff.CGETIN);
				// 无论合成成功还是失败都计算一次合成任务
				manager.getTaskService().synthesis(player, 0);
				// 判断是否有粘合剂
				ShopItem nhj = null;
				if (stone[0] == Common.GETIN) {
					nhj = stoneList.get(0);
					stoneList.remove(0);
				}
				// 设置一个默认值
				newStone = shopItemService.getShopItemById(stoneList.get(0).StrengthenToObj().hcdj());
				// 检查传过来的石头能否合成
				checkMergeStone(stoneList);
				// 计算合成概率
				int synRate = getSynRate(player, stoneList, nhj, addCsRate, addVipRate);
				int randomNum = ServiceUtils.getRandomNum(0, 10000);
				if (randomNum > synRate) {// 失败
					mergeOk.setResult(false);
					mergeOk.setContent(ErrorMessages.STRENGTHEN_GETINFAIL_MESSAGE);
					strongeRecord.setType(StrongeRecord.MERGE_FAIL);
					strongeRecord.setLevel(0);
				} else {// 成功
					newStone = getNewStone(stoneList);
					mergeOk.setResult(true);
					// 合成成功返回成功强化石的Icon
					mergeOk.setContent(newStone.getIcon());
					int newStoneType = newStone.getType();
					// 防止合成武器等其它物品(type = 6,11,12,13 才是合成宝石)
					if (!typeIsStone(newStoneType)) {
						log.error("非法合成：" + newStone.getName());
						throw new ProtocolException(ErrorMessages.STRENGTHEN_GETINFAIL_MESSAGE, data.getSerial(), data.getSessionId(),
								data.getType(), data.getSubType());
					}
					// 发放物品给玩家
					playerItemsFromShopService.playerGetItem(player.getId(), newStone.getId(), -1, -1, 1, 6, null, 0, 0, 0);
					manager.getTitleService().synthesis(player);
					int level = getLevel(newStone);
					manager.getTaskService().synthesis(player, level);
					strongeRecord.setType(1);
					strongeRecord.setLevel(level);
				}
				strongeRecord.setPlayerId(player.getId());
				strongeRecord.setCreateTime(new Date());
				strongeRecord.setRemark("合成:" + stoneStr);
				strongeRecord.setItemId(-1);
				playerItemsFromShopService.save(strongeRecord);
				// 返回结果
				PlayerItemsFromShop pifs = playerItemsFromShopService.uniquePlayerItem(player.getId(), newStone.getId());
				if (null != pifs) {
					mergeOk.setItemNum(pifs.getPLastNum());
					mergeOk.setAttack(newStone.getAddAttack() + pifs.getPAddAttack());
					mergeOk.setAddHP(newStone.getAddHp() + pifs.getPAddHp());
					mergeOk.setDefend(newStone.getAddDefend() + pifs.getPAddDefend());
					mergeOk.setStrengthoneLevel(pifs.getStrongLevel());
				} else {
					mergeOk.setItemNum(0);// pifs为空表示没有此商品，则此处设置0
					mergeOk.setAttack(newStone.getAddAttack());
					mergeOk.setAddHP(newStone.getAddHp());
					mergeOk.setDefend(newStone.getAddDefend());
					mergeOk.setStrengthoneLevel(newStone.getLevel());
				}
				mergeOk.setItemId(newStone.getId());
				mergeOk.setName(newStone.getName());
				mergeOk.setIcon(newStone.getIcon());
				mergeOk.setItemMainType(newStone.getType());
				mergeOk.setItemSubType(newStone.getSubtype());
				mergeOk.setExpired(false);
				mergeOk.setAttackArea(newStone.getAttackArea());
				mergeOk.setPower(newStone.getAddPower());
			}
			// 消耗材料
			for (int i = 0; i < stone.length; i++) {
				pifsUsed = playerItemsFromShopService.uniquePlayerItem(player.getId(), stone[i]);
				pifsUsed.setPLastNum(pifsUsed.getPLastNum() - 1 < 0 ? 0 : pifsUsed.getPLastNum() - 1);
				playerItemsFromShopService.update(pifsUsed);
				// 更新玩家拥有的物品
				ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, pifsUsed);
				playerItemsFromShopService.saveGetItemRecord(player.getPlayer().getId(), pifsUsed.getShopItem().getId(), -1, -1, -5, 1,
						null);
			}
			session.write(mergeOk);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	/**
	 * 检查传过来的石头是否可以合成 1,最高级的石头不能再合成(hcdj==id为最高级) 2,合成下一级石头不一致不能合成 3,必须大于三颗
	 */
	private void checkMergeStone(List<ShopItem> stones) throws Exception {
		// 合成必须大于三颗石头
		if (stones.size() < 4) {
			throw new Exception(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE);
		}
		for (int i = 0, y = 1; i < stones.size(); i++, y++) {
			if (y == stones.size())
				y = i;
			ShopItem stone = stones.get(i);
			ShopItem stone2 = stones.get(y);
			// 合成目标道具等于自己表示不能再合成了
			if (stone.getId() == stone.StrengthenToObj().hcdj()) {
				throw new Exception(ErrorMessages.CANNOTDOTIS);
			}
			// 合成目标道具不一样的石头无法合成
			if (stone.StrengthenToObj().hcdj() != stone2.StrengthenToObj().hcdj()) {
				throw new Exception(ErrorMessages.STRENGTHEN_GETINSAME_MESSAGE);
			}
		}
	}

	/**
	 * 计算成功概率
	 * 
	 * @param player
	 * @param stones
	 * @param nhj
	 * @param addCsRate
	 * @param addVipRate
	 * @return
	 */
	private int getSynRate(WorldPlayer player, List<ShopItem> stones, ShopItem nhj, int csAdd, int vipAdd) {
		ServiceManager manager = ServiceManager.getManager();
		ShopItem lhSi = manager.getShopItemService().getShopItemById(Common.GETIN);
		int nhAdd = 0;
		// 粘合剂
		if (nhj != null) {
			nhAdd = Integer.parseInt(lhSi.getStrengthen().split("=")[1]);
		}
		int cggl = 0;
		for (ShopItem stone : stones) {
			cggl += stone.StrengthenToObj().cggl();
		}
		// 计算成功概率
		int synRate = (cggl * (10000 + nhAdd + csAdd + vipAdd) / 10000);
		return (int) manager.getBuffService().getAddition(player, synRate, Buff.CGETIN);
	}

	/**
	 * 取新的石头(合成后的产物)
	 */
	private ShopItem getNewStone(List<ShopItem> stones) {
		IShopItemService shopItemService = ServiceManager.getManager().getShopItemService();
		// 是否能合成真强化石
		int zqdj = stones.get(0).StrengthenToObj().zqdj();
		int cggl = 0;
		if (zqdj != 0) {
			// 根据真强化石个数取对应的概率
			switch (countZqhs(stones)) {
				case 0 :
					cggl = 500;
					break;
				case 1 :
					cggl = 1000;
					break;
				case 2 :
					cggl = 1500;
					break;
				case 3 :
					cggl = 2000;
					break;
				case 4 :
					cggl = 2500;
					break;
			}
			int randomNum = ServiceUtils.getRandomNum(0, 10000);
			if (randomNum <= cggl) {
				return shopItemService.getShopItemById(zqdj);
			}
		}
		return shopItemService.getShopItemById(stones.get(0).StrengthenToObj().hcdj());
	}

	/**
	 * 计算真.强化石个数
	 * 
	 * @param stones
	 * @return
	 */
	private int countZqhs(List<ShopItem> stones) {
		int count = 0;
		for (ShopItem stone : stones) {
			if (isZqhs(stone)) {
				count++;
			}
		}
		return count;
	}

	private boolean isZqhs(ShopItem stone) {
		int id = stone.getId().intValue();
		return (id == 375 || id == 376 || id == 377 || id == 378 || id == 379);
	}

	private int getLevel(ShopItem stone) {
		int id = stone.getId();
		int level = 0;
		switch (id) {
			case 9 :
			case 382 :
			case 387 :
			case 392 :
			case 397 :
			case 402 :
			case 407 :
			case 412 :
			case 417 :
			case 422 :
			case 427 :
			case 432 :
				level = 1;
				break;
			case 10 :
			case 383 :
			case 388 :
			case 393 :
			case 398 :
			case 403 :
			case 408 :
			case 413 :
			case 418 :
			case 423 :
			case 428 :
			case 433 :
				level = 2;
				break;
			case 11 :
			case 384 :
			case 389 :
			case 394 :
			case 399 :
			case 404 :
			case 409 :
			case 414 :
			case 419 :
			case 424 :
			case 429 :
			case 434 :
				level = 3;
				break;
			case 12 :
			case 385 :
			case 390 :
			case 395 :
			case 400 :
			case 405 :
			case 410 :
			case 415 :
			case 420 :
			case 425 :
			case 430 :
			case 435 :
				level = 4;
				break;
			case 13 :
			case 386 :
			case 391 :
			case 396 :
			case 401 :
			case 406 :
			case 411 :
			case 416 :
			case 421 :
			case 426 :
			case 431 :
			case 436 :
				level = 5;
				break;
		}
		if (level == 0) {
			log.info("未能正确识别合成级别: " + id + " " + stone.getName());
			return 0;
		}
		return level;
	}

	/**
	 * subtype 为 7 是碎片
	 * 
	 * @param items
	 * @return
	 */
	private boolean isScrapMerge(List<ShopItem> items) {
		for (ShopItem item : items) {
			// 粘合剂
			if (item.getId() == Common.GETIN) {
				continue;
			}
			// 只要有一个是属于碎片都认为是碎片合成
			if (item.getSubtype().intValue() == 7 && item.getType() == 6) {
				return true;
			}
		}
		return false;
	}

	private boolean typeIsStone(int type) {
		return type == 6 || type == 11 || type == 12 || type == 13;
	}
}