package com.wyd.empire.world.server.handler.strengthen;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.strengthen.GuideMerge;
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
import com.wyd.empire.world.server.service.impl.BuffService;
import com.wyd.empire.world.server.service.impl.PlayerService;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class GuideMergeHandler implements IDataHandler {
	/** 合成所需金币 */
	public static final int MERGEGOLD = 2000;
	/** 单颗石头合成成功率 */
	public static final int MERGERATE = 2000;
	Logger log = Logger.getLogger(GuideMergeHandler.class);

	/**
	 * 强化、合成
	 */
	@SuppressWarnings("unchecked")
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		GuideMerge merge = (GuideMerge) data;
		try {
			MergeOk mergeOk = new MergeOk(data.getSessionId(), data.getSerial());
			int stone[] = merge.getItemId();
			ShopItem newStone = null;
			ServiceManager manager = ServiceManager.getManager();
			BuffService buffService = manager.getBuffService();
			IShopItemService shopItemService = manager.getShopItemService();
			IPlayerItemsFromShopService playerItemsFromShopService = manager.getPlayerItemsFromShopService();
			PlayerService playerService = manager.getPlayerService();
			List<ShopItem> stoneList = shopItemService.getEntityByIds(stone);
			if (isScrapMerge(stoneList)) {// 碎片合成
				MergeScrapHandler scrapHandler = new MergeScrapHandler();
				scrapHandler.handle(data);
				return;
			}
			PlayerItemsFromShop pifsUsed = null;
			String stoneStr = " ";
			for (int i = 0; i < stone.length; i++) {
				if (null != pifsUsed && pifsUsed.getShopItem().getId() == stone[i]) {
					pifsUsed.setPLastNum(pifsUsed.getPLastNum() - 1);
				} else {
					pifsUsed = ServiceManager.getManager().getPlayerItemsFromShopService().uniquePlayerItem(player.getId(), stone[i]);
				}
				if (pifsUsed == null || pifsUsed.getPLastNum() < 0) {
					throw new Exception(TipMessages.ITEMNOTENOUGH);
				}
				stoneStr += " " + stone[i];
			}
			Consortia cs = ServiceManager.getManager().getConsortiaService().getConsortiaById(player.getGuildId());
			int addCsRate = cs == null ? 0 : (int) ServiceManager.getManager().getBuffService()
					.getAddition(player, cs.getLevel() * 50, Buff.CSTRONG);
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
				// 获得特殊标示
				Map<String, Integer> map = ServiceManager.getManager().getVersionService().getSpecialMark();
				int startLevel1 = map.get("strongeLevel1");
				int startLevel2 = map.get("strongeLevel2");
				PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService()
						.uniquePlayerItem(player.getId(), merge.getWapenId());
				if (pifs == null || pifs.getShopItem().getMove() == Common.CHANGE_ATTRIBUTE_N) {
					throw new ProtocolException(ErrorMessages.CANNOT_STRONG, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
				Successrate sr = (Successrate) ServiceManager.getManager().getStrengthenService()
						.get(Successrate.class, pifs.getStrongLevel() + 1);
				int needMoney = (int) ServiceManager.getManager().getBuffService().getAddition(player, sr.getGold(), Buff.CGOLDLOW);
				int diamondNum = 0;
				if (player.getMoney() < needMoney) {
					throw new ProtocolException(ErrorMessages.PLAYER_INOC_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}

				List<Successrate> list = ServiceManager.getManager().getStrengthenService().getAll(Successrate.class);
				Map<String, Integer> scMap = new HashMap<String, Integer>();
				for (Successrate s : list) {
					scMap.put(s.getId() + "-1", s.getStone1Used());
					scMap.put(s.getId() + "-2", s.getStone2Used());
					scMap.put(s.getId() + "-3", s.getStone3Used());
					scMap.put(s.getId() + "-4", s.getStone4Used());
					scMap.put(s.getId() + "-5", s.getStone5Used());
					scMap.put(s.getId() + "-r1", s.getStone1realUsed());
					scMap.put(s.getId() + "-r2", s.getStone2realUsed());
					scMap.put(s.getId() + "-r3", s.getStone3realUsed());
					scMap.put(s.getId() + "-r4", s.getStone4realUsed());
					scMap.put(s.getId() + "-r5", s.getStone5realUsed());
					// 客户端的计算概率
					scMap.put(s.getId() + "-1f", s.getStone1());
					scMap.put(s.getId() + "-2f", s.getStone2());
					scMap.put(s.getId() + "-3f", s.getStone3());
					scMap.put(s.getId() + "-4f", s.getStone4());
					scMap.put(s.getId() + "-5f", s.getStone5());
					scMap.put(s.getId() + "-r1f", s.getStone1real());
					scMap.put(s.getId() + "-r2f", s.getStone2real());
					scMap.put(s.getId() + "-r3f", s.getStone3real());
					scMap.put(s.getId() + "-r4f", s.getStone4real());
					scMap.put(s.getId() + "-r5f", s.getStone5real());
				}
				int sumRate = 0;
				int luckyRate = 0;
				int protectMark = 0;
				int realMark = 0;
				int stoneNum = 0;
				int clientSumRate = 0;
				if (pifs.getStrongLevel() == 12) {
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
				// 工会技能加成
				// strengthenRate = (int)
				// ServiceManager.getManager().getBuffService().getAddition(player,
				// strengthenRate, Buff.CSTRONG);
				int randomNum = ServiceUtils.getRandomNum(0, 10000);
				int pifsLevel = pifs.getStrongLevel() + 1;// 强化的目标等级
				// 客户端计算成功率
				int lt = clientSumRate > 309 ? 309 : clientSumRate;
				int clientRate = (int) (clientSumRate + (10000 - clientSumRate) * 0.05 * (lt / 309));
				// TODO 不知是否还需要判断教学进度
				// if (guideComplete) {
				// 扣除相应金币或钻石
				if (merge.getMark() == 1) {
					ServiceManager.getManager().getPlayerService()
							.useTicket(player, diamondNum, TradeService.ORIGIN_SHORTMAIL, new int[]{26}, null, "强化购买金币");
				} else {
					ServiceManager.getManager().getPlayerService().updatePlayerGold(player, -needMoney, "强化", "");
				}
				// }
				if (randomNum > strengthenRate && (sr.getMissTimes() == -1 || sr.getMissTimes() > pifs.getMissTimes())
						&& clientRate < 10000) {// 失败
					mergeOk.setResult(false);
					mergeOk.setContent(ErrorMessages.STRENGTHEN_FAIL_MESSAGE);
					if ((!(pifs.getStrongLevel() < Common.STRFAILLVL)) && protectMark == 0) {
						pifs.updateStrongLevel(pifs.getStrongLevel() - 1);
					}
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
					strongeRecord.setType(-1);
					strongeRecord.setLevel(pifsLevel);
					strongeRecord.setItemId(pifs.getShopItem().getId());
					strongeRecord.setCreateTime(new Date());
					strongeRecord.setRemark("强化:" + stoneStr);
					ServiceManager.getManager().getPlayerItemsFromShopService().save(strongeRecord);
				} else {// 成功
					mergeOk.setResult(true);
					mergeOk.setContent(ErrorMessages.STRENGTHEN_SUCCESS_MESSAGE);
					pifs.updateStrongLevel(pifs.getStrongLevel() + 1);
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
					strongeRecord.setType(0);
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
				ServiceManager.getManager().getTitleService().strengthen(player, pifsLevel);
				ServiceManager.getManager().getTaskService().strengthen(player, pifs.getShopItem().getId(), pifsLevel);
				GameLogService.strengthen(player.getId(), player.getLevel(), merge.getWapenId(), mergeOk.isResult(), pifs.getStrongLevel(),
						stoneStr, 0, 0);
			} else {// 强化石合成、碎片合成
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
				// 计算合成需要的金币,如果不够则抛出异常
				// 当merge.getMark()==1时pay为钻石数，否则是金币
				int pay = getPay(player, merge, buffService);
				// 计算合成概率
				int synRate = getSynRate(player, stoneList, nhj, addCsRate, addVipRate);
				// 扣除相应金币或钻石
				if (merge.getMark() == 1) {
					playerService.useTicket(player, pay, TradeService.ORIGIN_SHORTMAIL, new int[]{26}, null, "合成购买金币");
				} else {
					playerService.updatePlayerGold(player, -pay, "合成", "");
				}
				int randomNum = ServiceUtils.getRandomNum(0, 10000);
				// System.out.println("合成成功概率：randomNum/synRate:"+randomNum
				// +"/"+ synRate);
				if (randomNum > synRate) {// 失败
					mergeOk.setResult(false);
					mergeOk.setContent(ErrorMessages.STRENGTHEN_GETINFAIL_MESSAGE);
					strongeRecord.setType(-2);
					strongeRecord.setLevel(0);
				} else {// 成功
					newStone = getNewStone(stoneList);
					mergeOk.setResult(true);
					// 合成成功返回成功强化石的Icon
					mergeOk.setContent(newStone.getIcon());
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
			ex.printStackTrace();
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	/**
	 * 计算玩家需要支付的钻石或金币 如果不够支付抛出异常
	 * 
	 * @param player
	 * @param merge
	 * @param buffService
	 * @return
	 * @throws Exception
	 */
	private int getPay(WorldPlayer player, GuideMerge merge, BuffService buffService) throws Exception {
		int needMoney = (int) buffService.getAddition(player, MERGEGOLD, Buff.CGOLDLOW);
		if (player.getMoney() < needMoney) {
			throw new Exception(ErrorMessages.PLAYER_INOC_MESSAGE);
		}
		return needMoney;
	}

	/**
	 * 检查传过来的石头是否可以合成 1,最高级的石头不能再合成(hcdj==id为最高级) 2,合成下一级石头不一致不能合成 3,必须大于两颗
	 */
	private void checkMergeStone(List<ShopItem> stones) throws Exception {
		// 合成必须大于两颗石头
		if (stones.size() < 2) {
			throw new Exception(ErrorMessages.STRENGTHEN_LESS2_MESSAGE);
		}
		for (int i = 0, y = 1; i < stones.size(); i++, y++) {
			if (y == stones.size())
				y = i;
			ShopItem stone = stones.get(i);
			ShopItem stone2 = stones.get(y);
			// 合成等级等于自己表示不能再合成了
			if (stone.getId() == stone.StrengthenToObj().hcdj()) {
				throw new Exception(ErrorMessages.CANNOTDOTIS);
			}
			// 合成等级不一样的石头无法合成
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
			// System.out.println("真强石合成概率：randomNum/cggl:"+randomNum +"/"+
			// cggl);
			if (randomNum <= cggl) {
				return shopItemService.getShopItemById(zqdj);
			}
		}
		return shopItemService.getShopItemById(stones.get(0).StrengthenToObj().hcdj());
	}

	/**
	 * 计算真强化石个数
	 * 
	 * @param stones
	 * @return
	 */
	private int countZqhs(List<ShopItem> stones) {
		int count = 0;
		for (ShopItem stone : stones) {
			String name = stone.getName();
			String regex = "真\\W+强化石";
			if (name.matches(regex)) {
				count++;
			}
		}
		return count;
	}

	private int getLevel(ShopItem stone) {
		String name = stone.getName();
		if (name.indexOf("一级") != -1 || name.indexOf("L1") != -1) {
			return 1;
		} else if (name.indexOf("二级") != -1 || name.indexOf("L2") != -1) {
			return 2;
		} else if (name.indexOf("三级") != -1 || name.indexOf("L3") != -1) {
			return 3;
		} else if (name.indexOf("四级") != -1 || name.indexOf("L4") != -1) {
			return 4;
		} else if (name.indexOf("五级") != -1 || name.indexOf("L5") != -1) {
			return 5;
		} else {
			log.error("强化石合成未能正确识别合成级别:" + name);
			return 0;
		}
	}

	private boolean isScrapMerge(List<ShopItem> items) {
		return items.get(0).getSubtype().intValue() == 7;
	}
}