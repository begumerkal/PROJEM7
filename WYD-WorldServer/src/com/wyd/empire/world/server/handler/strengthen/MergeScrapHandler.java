package com.wyd.empire.world.server.handler.strengthen;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.strengthen.Merge;
import com.wyd.empire.protocol.data.strengthen.MergeOk;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.StrongeRecord;
import com.wyd.empire.world.bean.VipRate;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerItemsFromShopService;
import com.wyd.empire.world.server.service.base.IShopItemService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class MergeScrapHandler implements IDataHandler {
	/** 合成所需金币 */
	public static final int MERGEGOLD = 2000;
	/** 合成成功率 */
	public static final int MERGERATE = 5000;
	Logger log = Logger.getLogger(MergeScrapHandler.class);

	/**
	 * 碎片合成
	 */
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		Merge merge = (Merge) data;
		boolean mark = false;
		try {
			MergeOk mergeOk = new MergeOk(data.getSessionId(), data.getSerial());
			Consortia cs = ServiceManager.getManager().getConsortiaService().getConsortiaById(player.getGuildId());
			int addCsRate = cs == null ? 0 : cs.getLevel() * 50;
			int addVipRate = 0;
			if (player.isVip()) {
				VipRate vr = ServiceManager.getManager().getPlayerItemsFromShopService().getVipRateById(player.getPlayer().getVipLevel());
				if (null != vr) {
					addVipRate = vr.getStrongRate() * 100;
				}
			}
			int scrap[] = merge.getItemId();
			StrongeRecord strongeRecord = new StrongeRecord();
			strongeRecord.setPlayerId(player.getId());
			strongeRecord.setType(-8);// 碎片合成失败
			strongeRecord.setLevel(0);
			strongeRecord.setCreateTime(new Date());
			mark = true;
			ShopItem newWeapon = null;
			ServiceManager manager = ServiceManager.getManager();
			IShopItemService shopItemService = manager.getShopItemService();
			IPlayerItemsFromShopService playerItemsFromShopService = manager.getPlayerItemsFromShopService();
			List<ShopItem> scrapList = shopItemService.getEntityByIds(scrap);
			// 判断是否有粘合剂
			ShopItem nhj = null;
			if (scrap[0] == Common.GETIN) {
				nhj = scrapList.get(0);
				scrapList.remove(0);
			}
			// 设置一个默认值
			newWeapon = getNewWeapon(scrapList.get(0));
			strongeRecord.setRemark("合成装备:" + newWeapon.getName());
			strongeRecord.setItemId(newWeapon.getId());
			// 检查传过来的碎片能否合成
			checkMergeScrap(scrapList);
			// 计算合成概率
			int synRate = getSynRate(player, scrapList, nhj, addCsRate, addVipRate);
			int randomNum = ServiceUtils.getRandomNum(0, 10000);
			if (randomNum > synRate) {// 失败
				mergeOk.setResult(false);
				mergeOk.setContent(ErrorMessages.STRENGTHEN_GETINFAIL_MESSAGE);
			} else {// 成功
				newWeapon = getNewWeapon(scrapList.get(0));
				mergeOk.setResult(true);
				// 合成成功返回成功强化石的Icon
				mergeOk.setContent(newWeapon.getIcon());
				// 发放物品给玩家
				int useDays = scrapList.get(0).StrengthenToObj().hcts();// 武器使用天数
				playerItemsFromShopService.playerGetItem(player.getId(), newWeapon.getId(), -1, useDays, -1, 6, null, 0, 0, 0);
				strongeRecord.setType(8);// 碎片合成装备成功
			}
			// 返回结果
			PlayerItemsFromShop pifs = playerItemsFromShopService.uniquePlayerItem(player.getId(), newWeapon.getId());
			if (null != pifs) {
				mergeOk.setItemNum(pifs.getPLastNum());
				mergeOk.setAttack(newWeapon.getAddAttack() + pifs.getPAddAttack());
				mergeOk.setAddHP(newWeapon.getAddHp() + pifs.getPAddHp());
				mergeOk.setDefend(newWeapon.getAddDefend() + pifs.getPAddDefend());
				mergeOk.setStrengthoneLevel(pifs.getStrongLevel());
			} else {
				mergeOk.setItemNum(0);// pifs为空表示没有此商品，则此处设置0
				mergeOk.setAttack(newWeapon.getAddAttack());
				mergeOk.setAddHP(newWeapon.getAddHp());
				mergeOk.setDefend(newWeapon.getAddDefend());
				mergeOk.setStrengthoneLevel(newWeapon.getLevel());
			}
			mergeOk.setItemId(newWeapon.getId());
			mergeOk.setName(newWeapon.getName());
			mergeOk.setIcon(newWeapon.getIcon());
			mergeOk.setItemMainType(newWeapon.getType());
			mergeOk.setItemSubType(newWeapon.getSubtype());
			mergeOk.setExpired(false);
			mergeOk.setAttackArea(newWeapon.getAttackArea());
			mergeOk.setPower(newWeapon.getAddPower());
			ServiceManager.getManager().getPlayerItemsFromShopService().save(strongeRecord);
			PlayerItemsFromShop pifsUsed = null;
			// 消耗材料
			for (int i = 0; i < scrap.length; i++) {
				pifsUsed = playerItemsFromShopService.uniquePlayerItem(player.getId(), scrap[i]);
				pifsUsed.setPLastNum(pifsUsed.getPLastNum() - 1 < 0 ? 0 : pifsUsed.getPLastNum() - 1);
				playerItemsFromShopService.update(pifsUsed);
				// 更新玩家拥有的物品
				ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, pifsUsed);
				playerItemsFromShopService.saveGetItemRecord(player.getPlayer().getId(), pifsUsed.getShopItem().getId(), -1, -1, -5, 1,
						null);
			}
			session.write(mergeOk);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (mark) {
				if (null != ex.getMessage())
					throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
			} else {
				if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
					this.log.error(ex, ex);
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
		}
	}

	/**
	 * 检查传过来的碎片是否可以合成 1，必须是4片碎片且不能重复 2，碎片必须是同一件武器的
	 */
	private void checkMergeScrap(List<ShopItem> scraps) throws Exception {
		// 合成必须是四片
		if (scraps.size() != 4) {
			throw new Exception(ErrorMessages.STRENGTHEN_MUST4_MESSAGE);
		}
		// 不能有重复的碎片
		for (int i = 0; i < scraps.size(); i++) {
			ShopItem scrap = scraps.get(i);
			for (int y = i + 1; y < scraps.size(); y++) {
				ShopItem tmp = scraps.get(y);
				if (tmp.getId().intValue() == scrap.getId().intValue()) {
					throw new Exception(ErrorMessages.STRENGTHEN_CANTREPEATED_MESSAGE);
				}
			}
		}
		// 碎片来自同一装备
		for (int i = 0, y = 1; i < scraps.size(); i++, y++) {
			if (y == scraps.size())
				y = i;
			ShopItem stone = scraps.get(i);
			ShopItem stone2 = scraps.get(y);
			// 合成道具不一样的碎片无法合成
			if (stone.StrengthenToObj().hcdj() != stone2.StrengthenToObj().hcdj()) {
				throw new Exception(ErrorMessages.STRENGTHEN_GETINSAME_MESSAGE);
			}
		}
	}

	/**
	 * 计算成功概率
	 * 
	 * @param player
	 * @param scrap
	 * @param nhj
	 * @param addCsRate
	 * @param addVipRate
	 * @return
	 */
	private int getSynRate(WorldPlayer player, List<ShopItem> scrap, ShopItem nhj, int csAdd, int vipAdd) {
		ServiceManager manager = ServiceManager.getManager();
		ShopItem lhSi = manager.getShopItemService().getShopItemById(Common.GETIN);
		int nhAdd = 0;
		// 粘合剂
		if (nhj != null) {
			nhAdd = Integer.parseInt(lhSi.getStrengthen().split("=")[1]);
		}
		// 计算成功概率
		int inrate = scrap.get(0).StrengthenToObj().cggl();
		inrate = inrate == 0 ? MERGERATE : inrate;
		int synRate = (inrate * (10000 + nhAdd + csAdd + vipAdd) / 10000);
		return (int) manager.getBuffService().getAddition(player, synRate, Buff.CGETIN);
	}

	/**
	 * 得到合成的武器
	 * 
	 * @param scrapList
	 * @return
	 */
	private ShopItem getNewWeapon(ShopItem scrap) {
		return ServiceManager.getManager().getShopItemService().getShopItemById(scrap.StrengthenToObj().hcdj());
	}
}
