package com.wyd.empire.world.server.handler.strengthen;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.strengthen.GetItemListOk;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.VipRate;
import com.wyd.empire.world.bean.WeapSkill;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * @author Administrator
 */
public class GetItemListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetItemListHandler.class);

	/**
	 * 获取玩家可用于强化或合成的物品列表
	 */
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			// long t1 = System.currentTimeMillis();
			List<PlayerItemsFromShop> playerItemsFromShopList = ServiceManager.getManager().getPlayerItemsFromShopService()
					.getStrengthenShopItemList(player.getId());
			int vipLevel = player.getPlayer().getVipLevel(); // Vip等级加成
			int vipAddition = 0;
			if (player.isVip()) {
				VipRate vr = ServiceManager.getManager().getPlayerItemsFromShopService().getVipRateById(vipLevel);
				if (null != vr) {
					vipAddition = vr.getStrongRate() * 100;
				}
			}
			int guildLevel = 0;
			int guildAddition = 0;
			int guildGetInAddition = 0;
			if (player.getGuildId() != 0) {
				Consortia cs = ServiceManager.getManager().getConsortiaService().getConsortiaById(player.getGuildId());
				guildLevel = cs.getLevel();
				guildAddition = (int) ServiceManager.getManager().getBuffService().getAddition(player, cs.getLevel() * 50, Buff.CSTRONG);
				guildGetInAddition = (int) ServiceManager.getManager().getBuffService()
						.getAddition(player, cs.getLevel() * 50, Buff.CGETIN);
			}
			int count = playerItemsFromShopList.size();
			int[] itemId = new int[count];
			String[] name = new String[count];
			String[] icon = new String[count];
			int[] itemMainType = new int[count];
			int[] itemSubType = new int[count];
			int[] itemNum = new int[count];
			boolean[] isExpired = new boolean[count];
			int[] strengthoneLevel = new int[count];
			int[] attack = new int[count];
			int[] attackArea = new int[count];
			int[] addHP = new int[count];
			int[] power = new int[count];
			int[] defend = new int[count];
			int[] luckRate = new int[count];
			String[] weaponSkillDetail1 = new String[count];
			String[] weaponSkillDetail2 = new String[count];
			int[] weaponSkillLevel1 = new int[count];
			int[] weaponSkillLevel2 = new int[count];
			int[] attackOpen = new int[count];
			String[] attackIcon = new String[count];
			int[] defendOpen = new int[count];
			String[] defendIcon = new String[count];
			int[] specialOpen = new int[count];
			String[] specialIcon = new String[count];
			int[] attackStoneLevel = new int[count];
			int[] defendStoneLevel = new int[count];
			int[] specailStoneLevel = new int[count];
			int[] parms = new int[count];
			int[] attackStoneType = new int[count];
			int[] defendStoneType = new int[count];
			int[] specailStoneType = new int[count];
			int[] attackStoneSubType = new int[count];
			int[] defendStoneSubType = new int[count];
			int[] specailStoneSubType = new int[count];
			int[] attackStoneParm = new int[count];
			int[] defendStoneParm = new int[count];
			int[] specailStoneParm = new int[count];
			int[] starlevel = new int[count];
			int[] pAttack = new int[count];
			int[] pAddHP = new int[count];
			int[] pDefend = new int[count];
			int[] getInRate = new int[count];
			int[] hcdj = new int[count];
			int[] costAmount = new int[5];
			int[] skillLock = new int[count];
			int[] weaponSkillId1 = new int[count];
			int[] weaponSkillId2 = new int[count];
			PlayerItemsFromShop playerItemsFromShop = null;
			ShopItem si = null;
			// long t2 = System.currentTimeMillis();
			// System.out.println("-------------" + (t2 - t1));
			for (int i = 0; i < count; i++) {
				playerItemsFromShop = playerItemsFromShopList.get(i);
				itemId[i] = playerItemsFromShop.getShopItem().getId();
				name[i] = playerItemsFromShop.getShopItem().getName();
				icon[i] = playerItemsFromShop.getShopItem().getIcon();
				itemMainType[i] = playerItemsFromShop.getShopItem().getType();
				itemSubType[i] = playerItemsFromShop.getShopItem().getSubtype();
				itemNum[i] = playerItemsFromShop.getPLastNum();
				if (playerItemsFromShop.getPLastTime() == 0) {
					isExpired[i] = true;
				} else {
					isExpired[i] = false;
				}
				strengthoneLevel[i] = playerItemsFromShop.getStrongLevel();
				attack[i] = playerItemsFromShop.getShopItem().getAddAttack();
				attackArea[i] = playerItemsFromShop.getShopItem().getAttackArea();
				addHP[i] = playerItemsFromShop.getShopItem().getAddHp();
				power[i] = playerItemsFromShop.getShopItem().getAddPower();
				defend[i] = playerItemsFromShop.getShopItem().getAddDefend();
				starlevel[i] = playerItemsFromShop.getStars();
				pAttack[i] = attack[i] + playerItemsFromShop.getPAddAttack();
				pAddHP[i] = addHP[i] + playerItemsFromShop.getPAddHp();
				pDefend[i] = defend[i] + playerItemsFromShop.getPAddDefend();
				ShopItem shopItem = playerItemsFromShop.getShopItem();
				if ((shopItem.getType() == Common.SHOP_ITEM_TYPE_ARMS_FUSION && playerItemsFromShop.getShopItem().getSubtype() == 1)
						|| shopItem.getType() == Common.SHOP_ITEM_TYPE_ARMS_MOSAIC1
						|| shopItem.getType() == Common.SHOP_ITEM_TYPE_ARMS_MOSAIC2
						|| shopItem.getType() == Common.SHOP_ITEM_TYPE_ARMS_MOSAIC3) {
					strengthoneLevel[i] = shopItem.getLevel();
					luckRate[i] = 0;
					getInRate[i] = shopItem.StrengthenToObj().cggl();
				} else if (shopItem.getType() == Common.SHOP_ITEM_TYPE_ARMS_FUSION && playerItemsFromShop.getShopItem().getSubtype() == 2) {
					strengthoneLevel[i] = shopItem.getLevel();
					luckRate[i] = Integer.parseInt(shopItem.getStrengthen().split("=")[4].split(",")[0]);
					getInRate[i] = Integer.parseInt(shopItem.getStrengthen().split("=")[1].split(",")[0]);
				} else {
					luckRate[i] = 0;
					getInRate[i] = shopItem.StrengthenToObj().cggl();
				}
				getInRate[i] = (int) ServiceManager.getManager().getBuffService().getAddition(player, getInRate[i], Buff.CGETIN);
				// 可能合成物品
				hcdj[i] = shopItem.StrengthenToObj().hcdj();
				// 被锁定的技能
				skillLock[i] = playerItemsFromShop.getSkillLock();
				WeapSkill ws = new WeapSkill();
				if (playerItemsFromShop.getWeapSkill1() == 0) {
					weaponSkillDetail1[i] = TipMessages.SKILL1;
					weaponSkillLevel1[i] = 0;
					weaponSkillId1[i] = 0;
				} else {
					ws = (WeapSkill) ServiceManager.getManager().getStrengthenService()
							.get(WeapSkill.class, playerItemsFromShop.getWeapSkill1());
					weaponSkillDetail1[i] = TipMessages.WEAPSKILLINFO + "1：" + ws.getSkillName();
					weaponSkillDetail1[i] += "\n";
					weaponSkillDetail1[i] += ws.getRemark();
					weaponSkillLevel1[i] = ws.getLevel();
					weaponSkillId1[i] = playerItemsFromShop.getWeapSkill1();
				}

				if (playerItemsFromShop.getWeapSkill2() == 0) {
					weaponSkillDetail2[i] = TipMessages.SKILL2;
					weaponSkillLevel2[i] = 0;
					weaponSkillId2[i] = 0;
				} else {
					ws = (WeapSkill) ServiceManager.getManager().getStrengthenService()
							.get(WeapSkill.class, playerItemsFromShop.getWeapSkill2());
					weaponSkillDetail2[i] = TipMessages.WEAPSKILLINFO + "2：" + ws.getSkillName();
					weaponSkillDetail2[i] += "\n";
					weaponSkillDetail2[i] += ws.getRemark();
					weaponSkillLevel2[i] = ws.getLevel();
					weaponSkillId2[i] = playerItemsFromShop.getWeapSkill2();
				}
				// 镶嵌第一批加的参数
				if (playerItemsFromShop.getAttackStone() > 0) {
					attackOpen[i] = 1;
					si = ServiceManager.getManager().getShopItemService().getShopItemById(playerItemsFromShop.getAttackStone());
					attackIcon[i] = si.getIcon();
					attackStoneLevel[i] = si.getLevel();
					attackStoneType[i] = si.getType();
					attackStoneSubType[i] = si.getSubtype();
					attackStoneParm[i] = ServiceManager.getManager().getShopItemService().getParmById(si.getId());
				} else {
					attackOpen[i] = playerItemsFromShop.getAttackStone();
					attackIcon[i] = "";
					attackStoneLevel[i] = 0;
					attackStoneType[i] = 0;
					attackStoneSubType[i] = 0;
					attackStoneParm[i] = 0;
				}
				if (playerItemsFromShop.getDefenseStone() > 0) {
					defendOpen[i] = 1;
					si = ServiceManager.getManager().getShopItemService().getShopItemById(playerItemsFromShop.getDefenseStone());
					defendIcon[i] = si.getIcon();
					defendStoneLevel[i] = si.getLevel();
					defendStoneType[i] = si.getType();
					defendStoneSubType[i] = si.getSubtype();
					defendStoneParm[i] = ServiceManager.getManager().getShopItemService().getParmById(si.getId());
				} else {
					defendOpen[i] = playerItemsFromShop.getDefenseStone();
					defendIcon[i] = "";
					defendStoneLevel[i] = 0;
					defendStoneType[i] = 0;
					defendStoneSubType[i] = 0;
					defendStoneParm[i] = 0;
				}
				if (playerItemsFromShop.getSpecialStone() > 0) {
					specialOpen[i] = 1;
					si = ServiceManager.getManager().getShopItemService().getShopItemById(playerItemsFromShop.getSpecialStone());
					specialIcon[i] = si.getIcon();
					specailStoneLevel[i] = si.getLevel();
					specailStoneType[i] = si.getType();
					specailStoneSubType[i] = si.getSubtype();
					specailStoneParm[i] = ServiceManager.getManager().getShopItemService().getParmById(si.getId());
				} else {
					specialOpen[i] = playerItemsFromShop.getSpecialStone();
					specialIcon[i] = "";
					specailStoneLevel[i] = 0;
					specailStoneType[i] = 0;
					specailStoneSubType[i] = 0;
					specailStoneParm[i] = 0;
				}
				// 镶嵌第二批加的参数
				parms[i] = ServiceManager.getManager().getShopItemService().getParmById(playerItemsFromShop.getShopItem().getId());
			}

			String punchAmount = ServiceManager.getManager().getVersionService().getVersion().getPunchAmount();
			String[] punchAmountStr = punchAmount.split(",");
			int index = 0;
			for (String s : punchAmountStr) {
				costAmount[index] = Integer.parseInt(s);
				index++;
			}

			GetItemListOk getItemListOk = new GetItemListOk(data.getSessionId(), data.getSerial());
			getItemListOk.setVipLevel(vipLevel);
			getItemListOk.setVipAddition(vipAddition);
			getItemListOk.setGuildLevel(guildLevel);
			getItemListOk.setGuildAddition(guildAddition);
			getItemListOk.setItemId(itemId);
			getItemListOk.setName(name);
			getItemListOk.setIcon(icon);
			getItemListOk.setItemMainType(itemMainType);
			getItemListOk.setItemSubType(itemSubType);
			getItemListOk.setItemNum(itemNum);
			getItemListOk.setIsExpired(isExpired);
			getItemListOk.setStrengthoneLevel(strengthoneLevel);
			getItemListOk.setAttack(attack);
			getItemListOk.setAttackArea(attackArea);
			getItemListOk.setAddHP(addHP);
			getItemListOk.setPower(power);
			getItemListOk.setDefend(defend);
			getItemListOk.setLuckRate(luckRate);
			getItemListOk.setWeaponSkillDetail1(weaponSkillDetail1);
			getItemListOk.setWeaponSkillDetail2(weaponSkillDetail2);
			getItemListOk.setWeaponSkillLevel1(weaponSkillLevel1);
			getItemListOk.setWeaponSkillLevel2(weaponSkillLevel2);
			getItemListOk.setAttackOpen(attackOpen);
			getItemListOk.setAttackIcon(attackIcon);
			getItemListOk.setDefendOpen(defendOpen);
			getItemListOk.setDefendIcon(defendIcon);
			getItemListOk.setSpecialOpen(specialOpen);
			getItemListOk.setSpecialIcon(specialIcon);
			getItemListOk.setAttackStoneLevel(attackStoneLevel);
			getItemListOk.setDefendStoneLevel(defendStoneLevel);
			getItemListOk.setSpecailStoneLevel(specailStoneLevel);
			getItemListOk.setParms(parms);
			getItemListOk.setAttackStoneType(attackStoneType);
			getItemListOk.setAttackStoneSubType(attackStoneSubType);
			getItemListOk.setAttackStoneParm(attackStoneParm);
			getItemListOk.setDefendStoneType(defendStoneType);
			getItemListOk.setDefendStoneSubType(defendStoneSubType);
			getItemListOk.setDefendStoneParm(defendStoneParm);
			getItemListOk.setSpecailStoneType(specailStoneType);
			getItemListOk.setSpecailStoneSubType(specailStoneSubType);
			getItemListOk.setSpecailStoneParm(specailStoneParm);
			getItemListOk.setStarlevel(starlevel);
			getItemListOk.setpAttack(pAttack);
			getItemListOk.setpAddHP(pAddHP);
			getItemListOk.setpDefend(pDefend);
			getItemListOk.setGetInRate(getInRate);
			getItemListOk.setHcdj(hcdj);
			getItemListOk.setCostAmount(costAmount);
			getItemListOk.setWeaponSkillLock(skillLock);
			getItemListOk.setWeaponSkillId1(weaponSkillId1);
			getItemListOk.setWeaponSkillId2(weaponSkillId2);
			getItemListOk.setGuildGetInAddition(guildGetInAddition);

			session.write(getItemListOk);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.STRENGTHEN_FAIL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
