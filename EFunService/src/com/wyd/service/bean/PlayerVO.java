package com.wyd.service.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wyd.empire.world.title.PlayerTitleVo;
import com.wyd.service.server.factory.ServiceManager;
import com.wyd.service.utils.SerializeUtil;

public class PlayerVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Player player;
	// 玩家身上的BUFF
	List<Buff> buffList = new ArrayList<Buff>();
	// 玩家身上的BUFF的Map<String,Buff>
	Map<String, Buff> buffMap = new HashMap<String, Buff>();
	/** 一天的时间long值 */
	public final long ONEDAYLONG = 24 * 60 * 60 * 1000;
	/** 双倍经验卡ID */
	public final int DOUBLEEXPID = 5;
	/** 财富卡ID */
	public final int WEALTHID = 6;

	private PlayerItemsFromShop item_head; // 头部装备
	private PlayerItemsFromShop item_face; // 脸部装备
	private PlayerItemsFromShop item_body; // 身体装备
	private PlayerItemsFromShop item_weapon; // 武器装备
	private PlayerItemsFromShop item_wing; // 翅膀装备
	private PlayerItemsFromShop item_ring1; // 戒指1装备
	private PlayerItemsFromShop item_ring2; // 戒指2装备
	private PlayerItemsFromShop item_necklace;
	private int attack;
	private int defend;
	private int critRate;
	private Consortia guild;
	// 计算玩家力量
	private int force;
	// 计算玩家护甲
	private int armor;
	// 计算玩家敏捷
	private int agility;
	// 计算玩家暴击攻击比率
	private int critAttackRate;
	// 计算玩家体质
	private int physique;
	// 计算玩家爆破范围
	private int explodeRadius;
	// 计算玩家破防率
	private int wreckDefense;
	// 计算玩家免暴率
	private int reduceCrit;
	// 计算玩家的幸运值
	private int luck;
	// 计算玩家免坑率
	private int reduceBury;
	// 当前最大体力值
	private int maxPF;
	private int maxHP;
	// 计算玩家免伤率
	private int injuryFree;
	private int fighting;
	private PlayerPet playerPet;
	private String communityName;
	private String communityPosition;

	// 称号
	private PlayerTitleVo playerTitle;

	public PlayerVO(Player player) {
		this.player = player;
		try {
			initialBuff();
			initPlayerPet();
			initCommunity();
			initPlayerTitle();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		List<Integer> itemIds = new ArrayList<Integer>();
		itemIds.add(player.getSuitHeadId());
		itemIds.add(player.getSuitFaceId());
		itemIds.add(player.getSuitBodyId());
		itemIds.add(player.getSuitWeaponId());
		if (null != player.getSuitWingId() && player.getSuitWingId() > 0) {
			itemIds.add(player.getSuitWingId());
		}
		if (null != player.getSuitRing1Id() && player.getSuitRing1Id() > 0) {
			itemIds.add(player.getSuitRing1Id());
		}
		if (null != player.getSuitRing2Id() && player.getSuitRing2Id() > 0) {
			itemIds.add(player.getSuitRing2Id());
		}
		if (null != player.getSuitNecklaceId() && player.getSuitNecklaceId() > 0) {
			itemIds.add(player.getSuitNecklaceId());
		}
		List<PlayerItemsFromShop> playerItems = ServiceManager.getManager().getPlayerItemsFromShopService().playerFindItem(player.getId(), itemIds, player.getSex());
		for (PlayerItemsFromShop item : playerItems) {
			if (item.getShopItem().isWeapon()) {
				item_weapon = item;
			} else if (item.getShopItem().isBody()) {
				item_body = item;
			} else if (item.getShopItem().isWing()) {
				item_wing = item;
			} else if (item.getShopItem().isRing(player.getSuitRing1Id())) {
				item_ring1 = item;
			} else if (item.getShopItem().isRing(player.getSuitRing2Id())) {
				item_ring2 = item;
			} else if (item.getShopItem().isNecklace()) {
				item_necklace = item;
			} else if (item.getShopItem().isFace()) {
				item_face = item;
			} else if (item.getShopItem().isHead()) {
				item_head = item;
			}
		}
		// 计算玩家力量
		force = player.getForce() + getItemProperty(10);
		// 计算玩家护甲
		armor = player.getArmor() + getItemProperty(11);
		// 计算玩家敏捷
		agility = player.getAgility() + getItemProperty(12);
		// 计算玩家暴击攻击比率
		critAttackRate = (int) ((getProficiency() >= 10000 ? 1.3f : 1.2f) * 10000);
		// 计算玩家体质
		physique = player.getPhysique() + getItemProperty(13);
		// 计算玩家爆破范围
		explodeRadius = item_weapon.getShopItem().getAttackArea();
		// 计算玩家破防率
		wreckDefense = player.getWreckDefense() + getItemProperty(7);
		// 计算玩家免暴率
		reduceCrit = player.getReduceCrit() + getItemProperty(8);
		// 计算玩家的幸运值
		luck = player.getLuck() + getItemProperty(14);
		// 计算玩家免坑率
		reduceBury = player.getReduceBury() + getItemProperty(9) + getLuck() * 10000 / 8333;
		// 当前最大体力值
		maxPF = (int) getAddition(100 + getItemProperty(4), Buff.CPOWER);
		// 计算玩家暴击率
		float sld = getProficiency();
		sld = sld / 10000;
		critRate = (int) ((agility / 2084f + 10 * Math.sqrt(sld / getItemProperty(5))) * 10000) + getItemProperty(15);
		critRate += player.getDefense() * 10;
		critRate = critRate > 9000 ? 9000 : critRate;
		// 计算玩家血量
		if (player.getZsLevel() > 0) {
			attack = (int) ((getLevel() + 108) * 0.68 - 108 * 0.68 + (getBeforeRebirthLevel(player.getZsLevel()) + 9) * 0.68 + 24 * (getBeforeRebirthLevel(player.getZsLevel()) - 50) / 50f);
			defend = (int) ((getLevel() + 109) * 0.6 - 109 * 0.6 + (getBeforeRebirthLevel(player.getZsLevel()) + 10) * 0.6 + 24 * (getBeforeRebirthLevel(player.getZsLevel()) - 50) / 50f);
			maxHP = (int) (3.8 * (1 - Math.pow(1.0035, getLevel() + 99)) / (1 - 1.0035) - 3.8 * (1 - Math.pow(1.0035, 99)) / (1 - 1.0035) + 3.8
					* (1 - Math.pow(1.0035, getBeforeRebirthLevel(player.getZsLevel()))) / (1 - 1.0035) + 150f * (getBeforeRebirthLevel(player.getZsLevel()) - 50) / 50f);
		} else {
			attack = (int) ((getLevel() + 9) * 0.68);
			defend = (int) ((getLevel() + 10) * 0.6);
			maxHP = (int) (3.8 * (1 - Math.pow(1.0035, getLevel())) / (1 - 1.0035));
		}
		// 宠物加成
		if (getPlayerPet() != null && getPlayerPet().isInUsed()) {
			attack += getPlayerPet().getAttack();
			defend += getPlayerPet().getDefend();
			maxHP += getPlayerPet().getHP();
		}
		attack += player.getAttack() + getItemProperty(1) + (getForce() * 4 / 25);
		defend += player.getDefend() + getItemProperty(3) + getArmor() * 9 / 50;
		maxHP += player.getHp() + getItemProperty(2) + getPhysique();
		// 加BUFF得出最终值
		defend = (int) getAddition(defend, Buff.CDEF);
		maxHP = (int) getAddition(maxHP, Buff.CHPCAP);
		// 计算玩家免伤率
		injuryFree = (int) ((player.getInjuryFree() + getItemProperty(6)) * 10000);
		// 计算玩家战斗力
		float c = critRate / 10000f;
		float a = getAttack();
		float h = getMaxHP();
		float w = getWreckDefense() / 10000f;
		float r = getReduceCrit() / 10000f;
		float i = getInjuryFree() / 10000f + defend / (defend + 600f);
		float e = getExplodeRadius();
		// sqrt((暴击率*(攻击力*暴击率*1.5+攻击力*(1-暴击率))+(1-暴击率)*攻击力)^1.25*生命*(1+破防率)*(1+0.75*免暴率)/(1-免伤率)
		// ^1.25) *((爆破范围-45)*0.004+1)-1000
		fighting = (int) (Math.sqrt(Math.pow((c * (a * c * 1.5 + a * (1 - c)) + (1 - c) * a), 1.25) * h * (1 + w) * (1 + 0.75 * r) / Math.pow((1 - i), 1.25)) * ((e - 45f) * 0.004 + 1f) - 1000f);

	}

	public int getId() {
		return player.getId();
	}

	/**
	 * 获取玩家的战斗力
	 * 
	 * @return
	 */
	public int getFighting() {
		return fighting;
	}

	/**
	 * 获取武器熟练度
	 * 
	 * @return
	 */
	public int getProficiency() {
		return item_weapon.getSkillful() > 10000 ? 10000 : item_weapon.getSkillful();
	}

	public int getForce() {
		return force;
	}

	public int getArmor() {
		return armor;
	}

	public int getAgility() {
		return agility;
	}

	public int getCritAttackRate() {
		return critAttackRate;
	}

	public int getPhysique() {
		return physique;
	}

	public int getExplodeRadius() {
		return explodeRadius;
	}

	public int getWreckDefense() {
		return wreckDefense;
	}

	public int getReduceCrit() {
		return reduceCrit;
	}

	public int getLuck() {
		return luck;
	}

	public int getReduceBury() {
		return reduceBury;
	}

	public int getMaxPF() {
		return maxPF;
	}

	/**
	 * 获取玩家等级
	 * 
	 * @return
	 */
	public int getLevel() {
		return this.player.getLevel();
	}

	/**
	 * 获取玩家当前的攻击力
	 * 
	 * @return
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * 获取玩家当前防御力
	 * 
	 * @return
	 */
	public int getDefend() {
		return defend;
	}

	/**
	 * 获取玩家的装备加成属性
	 * 
	 * @param type
	 *            1攻击力，2血量，3防御力，4体力值，5暴击系数，6免伤，7破防，
	 *            8免暴，9免坑，10力量，11护甲，12敏捷，13体质，14幸运,15暴击
	 * @return
	 */
	private int getItemProperty(int type) {
		int ret = 0;
		switch (type) {
		case 1:
			ret += item_head.getPAddAttack() + item_head.getShopItem().getAddAttack();
			ret += item_face.getPAddAttack() + item_face.getShopItem().getAddAttack();
			ret += item_body.getPAddAttack() + item_body.getShopItem().getAddAttack();
			ret += item_weapon.getPAddAttack() + item_weapon.getShopItem().getAddAttack();
			if (null != item_wing)
				ret += item_wing.getPAddAttack() + item_wing.getShopItem().getAddAttack();
			if (null != item_ring1)
				ret += item_ring1.getPAddAttack() + item_ring1.getShopItem().getAddAttack();
			if (null != item_ring2)
				ret += item_ring2.getPAddAttack() + item_ring2.getShopItem().getAddAttack();
			if (null != item_necklace)
				ret += item_necklace.getPAddAttack() + item_necklace.getShopItem().getAddAttack();
			break;
		case 2:
			ret += item_head.getPAddHp() + item_head.getShopItem().getAddHp();
			ret += item_face.getPAddHp() + item_face.getShopItem().getAddHp();
			ret += item_body.getPAddHp() + item_body.getShopItem().getAddHp();
			ret += item_weapon.getPAddHp() + item_weapon.getShopItem().getAddHp();
			if (null != item_wing)
				ret += item_wing.getPAddHp() + item_wing.getShopItem().getAddHp();
			if (null != item_ring1)
				ret += item_ring1.getPAddHp() + item_ring1.getShopItem().getAddHp();
			if (null != item_ring2)
				ret += item_ring2.getPAddHp() + item_ring2.getShopItem().getAddHp();
			if (null != item_necklace)
				ret += item_necklace.getPAddHp() + item_necklace.getShopItem().getAddHp();
			break;
		case 3:
			ret += item_head.getPAddDefend() + item_head.getShopItem().getAddDefend();
			ret += item_face.getPAddDefend() + item_face.getShopItem().getAddDefend();
			ret += item_body.getPAddDefend() + item_body.getShopItem().getAddDefend();
			ret += item_weapon.getPAddDefend() + item_weapon.getShopItem().getAddDefend();
			if (null != item_wing)
				ret += item_wing.getPAddDefend() + item_wing.getShopItem().getAddDefend();
			if (null != item_ring1)
				ret += item_ring1.getPAddDefend() + item_ring1.getShopItem().getAddDefend();
			if (null != item_ring2)
				ret += item_ring2.getPAddDefend() + item_ring2.getShopItem().getAddDefend();
			if (null != item_necklace)
				ret += item_necklace.getPAddDefend() + item_necklace.getShopItem().getAddDefend();
			break;
		case 4:
			ret += item_head.getShopItem().getAddPower();
			ret += item_face.getShopItem().getAddPower();
			ret += item_body.getShopItem().getAddPower();
			ret += item_weapon.getShopItem().getAddPower();
			if (null != item_wing)
				ret += item_wing.getShopItem().getAddPower();
			if (null != item_ring1)
				ret += item_ring1.getShopItem().getAddPower();
			if (null != item_ring2)
				ret += item_ring2.getShopItem().getAddPower();
			if (null != item_necklace)
				ret += item_necklace.getShopItem().getAddPower();
			break;
		case 5:
			ret += item_head.getShopItem().getCriticalCoefficient();
			ret += item_head.getShopItem().getCriticalCoefficient();
			ret += item_face.getShopItem().getCriticalCoefficient();
			ret += item_body.getShopItem().getCriticalCoefficient();
			ret += item_weapon.getShopItem().getCriticalCoefficient();
			if (null != item_wing)
				ret += item_wing.getShopItem().getCriticalCoefficient();
			if (null != item_ring1)
				ret += item_ring1.getShopItem().getCriticalCoefficient();
			if (null != item_ring2)
				ret += item_ring2.getShopItem().getCriticalCoefficient();
			if (null != item_necklace)
				ret += item_necklace.getShopItem().getCriticalCoefficient();
			break;
		case 6:
			ret += item_head.getShopItem().getAddInjuryFree();
			ret += item_face.getShopItem().getAddInjuryFree();
			ret += item_body.getShopItem().getAddInjuryFree();
			ret += item_weapon.getShopItem().getAddInjuryFree();
			if (null != item_wing)
				ret += item_wing.getShopItem().getAddInjuryFree();
			if (null != item_ring1)
				ret += item_ring1.getShopItem().getAddInjuryFree();
			if (null != item_ring2)
				ret += item_ring2.getShopItem().getAddInjuryFree();
			if (null != item_necklace)
				ret += item_necklace.getShopItem().getAddInjuryFree();
			break;
		case 7:
			ret += item_head.getpAddWreckDefense() + item_head.getShopItem().getAddWreckDefense();
			ret += item_face.getpAddWreckDefense() + item_face.getShopItem().getAddWreckDefense();
			ret += item_body.getpAddWreckDefense() + item_body.getShopItem().getAddWreckDefense();
			ret += item_weapon.getpAddWreckDefense() + item_weapon.getShopItem().getAddWreckDefense();
			if (null != item_wing)
				ret += item_wing.getpAddWreckDefense() + item_wing.getShopItem().getAddWreckDefense();
			if (null != item_ring1)
				ret += item_ring1.getpAddWreckDefense() + item_ring1.getShopItem().getAddWreckDefense();
			if (null != item_ring2)
				ret += item_ring2.getpAddWreckDefense() + item_ring2.getShopItem().getAddWreckDefense();
			if (null != item_necklace)
				ret += item_necklace.getpAddWreckDefense() + item_necklace.getShopItem().getAddWreckDefense();
			break;
		case 8:
			ret += item_head.getpAddReduceCrit() + item_head.getShopItem().getAddReduceCrit();
			ret += item_face.getpAddReduceCrit() + item_face.getShopItem().getAddReduceCrit();
			ret += item_body.getpAddReduceCrit() + item_body.getShopItem().getAddReduceCrit();
			ret += item_weapon.getpAddReduceCrit() + item_weapon.getShopItem().getAddReduceCrit();
			if (null != item_wing)
				ret += item_wing.getpAddReduceCrit() + item_wing.getShopItem().getAddReduceCrit();
			if (null != item_ring1)
				ret += item_ring1.getpAddReduceCrit() + item_ring1.getShopItem().getAddReduceCrit();
			if (null != item_ring2)
				ret += item_ring2.getpAddReduceCrit() + item_ring2.getShopItem().getAddReduceCrit();
			if (null != item_necklace)
				ret += item_necklace.getpAddReduceCrit() + item_necklace.getShopItem().getAddReduceCrit();
			break;
		case 9:
			ret += item_head.getShopItem().getAddReduceBury();
			ret += item_face.getShopItem().getAddReduceBury();
			ret += item_body.getShopItem().getAddReduceBury();
			ret += item_weapon.getShopItem().getAddReduceBury();
			if (null != item_wing)
				ret += item_wing.getShopItem().getAddReduceBury();
			if (null != item_ring1)
				ret += item_ring1.getShopItem().getAddReduceBury();
			if (null != item_ring2)
				ret += item_ring2.getShopItem().getAddReduceBury();
			if (null != item_necklace)
				ret += item_necklace.getShopItem().getAddReduceBury();
			break;
		case 10:
			ret += item_head.getpAddForce() + item_head.getShopItem().getAddForce();
			ret += item_face.getpAddForce() + item_face.getShopItem().getAddForce();
			ret += item_body.getpAddForce() + item_body.getShopItem().getAddForce();
			ret += item_weapon.getpAddForce() + item_weapon.getShopItem().getAddForce();
			if (null != item_wing)
				ret += item_wing.getpAddForce() + item_wing.getShopItem().getAddForce();
			if (null != item_ring1)
				ret += item_ring1.getpAddForce() + item_ring1.getShopItem().getAddForce();
			if (null != item_ring2)
				ret += item_ring2.getpAddForce() + item_ring2.getShopItem().getAddForce();
			if (null != item_necklace)
				ret += item_necklace.getpAddForce() + item_necklace.getShopItem().getAddForce();
			break;
		case 11:
			ret += item_head.getpAddArmor() + item_head.getShopItem().getAddArmor();
			ret += item_face.getpAddArmor() + item_face.getShopItem().getAddArmor();
			ret += item_body.getpAddArmor() + item_body.getShopItem().getAddArmor();
			ret += item_weapon.getpAddArmor() + item_weapon.getShopItem().getAddArmor();
			if (null != item_wing)
				ret += item_wing.getpAddArmor() + item_wing.getShopItem().getAddArmor();
			if (null != item_ring1)
				ret += item_ring1.getpAddArmor() + item_ring1.getShopItem().getAddArmor();
			if (null != item_ring2)
				ret += item_ring2.getpAddArmor() + item_ring2.getShopItem().getAddArmor();
			if (null != item_necklace)
				ret += item_necklace.getpAddArmor() + item_necklace.getShopItem().getAddArmor();
			break;
		case 12:
			ret += item_head.getpAddAgility() + item_head.getShopItem().getAddAgility();
			ret += item_face.getpAddAgility() + item_face.getShopItem().getAddAgility();
			ret += item_body.getpAddAgility() + item_body.getShopItem().getAddAgility();
			ret += item_weapon.getpAddAgility() + item_weapon.getShopItem().getAddAgility();
			if (null != item_wing)
				ret += item_wing.getpAddAgility() + item_wing.getShopItem().getAddAgility();
			if (null != item_ring1)
				ret += item_ring1.getpAddAgility() + item_ring1.getShopItem().getAddAgility();
			if (null != item_ring2)
				ret += item_ring2.getpAddAgility() + item_ring2.getShopItem().getAddAgility();
			if (null != item_necklace)
				ret += item_necklace.getpAddAgility() + item_necklace.getShopItem().getAddAgility();
			break;
		case 13:
			ret += item_head.getpAddPhysique() + item_head.getShopItem().getAddPhysique();
			ret += item_face.getpAddPhysique() + item_face.getShopItem().getAddPhysique();
			ret += item_body.getpAddPhysique() + item_body.getShopItem().getAddPhysique();
			ret += item_weapon.getpAddPhysique() + item_weapon.getShopItem().getAddPhysique();
			if (null != item_wing)
				ret += item_wing.getpAddPhysique() + item_wing.getShopItem().getAddPhysique();
			if (null != item_ring1)
				ret += item_ring1.getpAddPhysique() + item_ring1.getShopItem().getAddPhysique();
			if (null != item_ring2)
				ret += item_ring2.getpAddPhysique() + item_ring2.getShopItem().getAddPhysique();
			if (null != item_necklace)
				ret += item_necklace.getpAddPhysique() + item_necklace.getShopItem().getAddPhysique();
			break;
		case 14:
			ret += item_head.getpAddLuck() + item_head.getShopItem().getAddLuck();
			ret += item_face.getpAddLuck() + item_face.getShopItem().getAddLuck();
			ret += item_body.getpAddLuck() + item_body.getShopItem().getAddLuck();
			ret += item_weapon.getpAddLuck() + item_weapon.getShopItem().getAddLuck();
			if (null != item_wing)
				ret += item_wing.getpAddLuck() + item_wing.getShopItem().getAddLuck();
			if (null != item_ring1)
				ret += item_ring1.getpAddLuck() + item_ring1.getShopItem().getAddLuck();
			if (null != item_ring2)
				ret += item_ring2.getpAddLuck() + item_ring2.getShopItem().getAddLuck();
			if (null != item_necklace)
				ret += item_necklace.getpAddLuck() + item_necklace.getShopItem().getAddLuck();
			break;
		case 15:
			ret += item_head.getpAddcrit() + item_face.getpAddcrit() + item_body.getpAddcrit() + item_weapon.getpAddcrit();
			if (null != item_wing)
				ret += item_wing.getpAddcrit();
			if (null != item_ring1)
				ret += item_ring1.getpAddcrit();
			if (null != item_ring2)
				ret += item_ring2.getpAddcrit();
			if (null != item_necklace)
				ret += item_necklace.getpAddcrit();
			break;
		}
		return ret;
	}

	/**
	 * 计算玩家加成后数值
	 * 
	 * @param player
	 * @param addParam
	 * @param buffType
	 *            buff类型
	 * @return
	 */
	public float getAddition(int addParam, String buffType) {
		if (addParam < 1) {
			return addParam;
		}
		Buff buff;
		float addParamFloat = addParam;
		for (int i = buffList.size() - 1; i >= 0; i--) {
			buff = buffList.get(i);
			if (buff.getEndtime() < System.currentTimeMillis() && buff.getEndtime() != -1) {
				buffMap.remove(buff.getBuffCode());
				buffMap.remove(i);
				continue;
			}
			if (buff.getBuffCode().equals(buffType)) {
				if (0 == buff.getAddType()) {
					addParamFloat = (addParam * (100f + buff.getQuantity() / 100f)) / 100f;
				} else {
					addParamFloat += buff.getQuantity();
				}
			}
		}
		return addParamFloat;
	}

	public void initialBuff() {
		buffList.clear();
		buffMap.clear();
		PlayerItemsFromShop pifs;
		Buff buff;
		long lostTime = 0;
		long nowTime = System.currentTimeMillis();
		pifs = ServiceManager.getManager().getPlayerItemsFromShopService().getPlayerItemsFromShopByPlayerIdAndItemId(player, DOUBLEEXPID);
		if (null != pifs && pifs.getPLastTime() > 0) {
			buff = new Buff();
			lostTime = pifs.getBuyTime().getTime() + (pifs.getPLastTime() * ONEDAYLONG);
			buff.setBuffName(pifs.getShopItem().getName());
			buff.setBuffCode(Buff.EXP);
			buff.setIcon(pifs.getShopItem().getIcon());
			buff.setAddType(0);
			buff.setQuantity(200);
			buff.setEndtime(lostTime);
			buff.setSurplus((int) (lostTime - nowTime) / 1000);
			buff.setSkillId(0);
			buff.setSkillDetail("");
			addBuff(buff);
			// setHasDoubleCard(true);
		}
		pifs = ServiceManager.getManager().getPlayerItemsFromShopService().getPlayerItemsFromShopByPlayerIdAndItemId(player, WEALTHID);
		if (null != pifs && pifs.getPLastTime() > 0) {
			buff = new Buff();
			lostTime = pifs.getBuyTime().getTime() + (pifs.getPLastTime() * ONEDAYLONG);
			buff = new Buff();
			buff.setBuffName(pifs.getShopItem().getName());
			buff.setBuffCode(Buff.GOLD);
			buff.setIcon(pifs.getShopItem().getIcon());
			buff.setAddType(0);
			buff.setQuantity(200);
			buff.setEndtime(lostTime);
			buff.setSurplus((int) (lostTime - nowTime) / 1000);
			buff.setSkillId(0);
			buff.setSkillDetail("");
			addBuff(buff);
		}
		// 获得公会技能
		List<BuffRecord> skillList = ServiceManager.getManager().getConsortiaService().getBuffRecordByPlayerId(player.getId());
		for (BuffRecord br : skillList) {
			// Hibernate.initialize(br.getConsortiaSkill());
			buff = new Buff();
			buff.setBuffName(br.getBuffName());
			buff.setBuffCode(br.getBuffCode());
			buff.setIcon(br.getConsortiaSkill() != null ? br.getConsortiaSkill().getIcon() : "");
			buff.setAddType(br.getAddType());
			buff.setQuantity(br.getQuantity());
			buff.setEndtime(br.getEndtime().getTime());
			buff.setSurplus(br.getSurplus());
			buff.setSkillId(br.getConsortiaSkill() != null ? br.getConsortiaSkill().getId() : 0);
			buff.setSkillDetail(br.getConsortiaSkill() != null ? br.getConsortiaSkill().getDesc() : "");
			buff.setBufftype(br.getBuffType());
			addBuff(buff);
		}
	}

	/**
	 * 获取玩家指定转生等级转生前的等级，0表示未找到该转生等级的数据
	 * 
	 * @param rebirthLevel
	 * @return
	 */
	public int getBeforeRebirthLevel(int rebirthLevel) {
		String brl = player.getZsOldLevel();
		if (null != brl && brl.length() > 0) {
			String[] ls = brl.split(",");
			if (rebirthLevel <= ls.length && rebirthLevel > 0) {
				return Integer.parseInt(ls[rebirthLevel - 1]);
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	/**
	 * 当前血量
	 * 
	 * @return
	 */
	public int getMaxHP() {
		return maxHP;
	}

	public PlayerPet getPlayerPet() {
		return playerPet;
	}

	/**
	 * 获取免伤(10000倍)
	 * 
	 * @return
	 */
	public int getInjuryFree() {
		return injuryFree;
	}

	/**
	 * 初始化玩家宠物
	 */
	private void initPlayerPet() {
		playerPet = ServiceManager.getManager().getPlayerPetService().getInUsePet(player.getId());
	}

	/**
	 * 玩家增加buff
	 * 
	 * @param player
	 * @param buff
	 */
	public void addBuff(Buff buff) {
		getBuffList().add(buff);
		getBuffMap().put(buff.getBuffCode(), buff);
	}

	/**
	 * 获取玩家身上的BUFF
	 * 
	 * @return
	 */
	public List<Buff> getBuffList() {
		return buffList;
	}

	/**
	 * 获取玩家身上的BUFF(Map)
	 * 
	 * @return
	 */
	public Map<String, Buff> getBuffMap() {
		return buffMap;
	}

	public Player getPlayer() {
		return player;
	}

	public int getGuildId() {
		if (null != guild) {
			return guild.getId();
		} else {
			return 0;
		}
	}

	public String getGuildName() {
		if (null != guild) {
			return guild.getName();
		} else {
			return "";
		}
	}

	// 公会
	private void initCommunity() {
		guild = ServiceManager.getManager().getConsortiaService().getConsortiaByPlayerId(player.getId());
		if (getGuildId() == 0) {
			communityName = "暫未加入公會";
			communityPosition = "無";
		} else {
			// 获得公会对象
			PlayerSinConsortia playerConsortia = ServiceManager.getManager().getPlayerSinConsortiaService().findPlayerSinConsortiaByPlayerId(player.getId());
			communityName = playerConsortia.getConsortia().getName();
			switch (playerConsortia.getPosition()) {
			case 4:
				communityPosition = "普通會員";
				break;
			case 3:
				communityPosition = "精英";
				break;
			case 2:
				communityPosition = "長老";
				break;
			case 1:
				communityPosition = "副會長";
				break;
			case 0:
				communityPosition = "會長";
				break;
			}
		}
	}

	public String getCommunityName() {
		return communityName;
	}

	public String getCommunityPosition() {
		return communityPosition;
	}

	/**
	 * 获取玩家显示的称号
	 * 
	 * @return
	 */
	public String getPlayerTitle() {
		String title = "";
		PlayerDIYTitle diyTitle = ServiceManager.getManager().getTitleService().getSelDIYTitle(getId());
		if (diyTitle != null) {
			return diyTitle.getTitle();
		} else if (null != playerTitle) {
			if (1 == playerTitle.getTitleId()) {// 工会称号
				title = ServiceManager.getManager().getTitleService().guildTitle(this);
			} else if (2 == playerTitle.getTitleId()) {// 结婚
				title = ServiceManager.getManager().getTitleService().getMarryTitle(player);
			} else {
				title = ServiceManager.getManager().getTitleService().getTitleById(playerTitle.getTitleId()).getTitle();
			}
		}
		return title;
	}

	@SuppressWarnings("unchecked")
	private void initPlayerTitle() {
		byte[] titleByte=ServiceManager.getManager().getTitleService().getPlayerTaskTitle(getId()).getTitleList();
		if(titleByte==null){
			return ;
		}
		List<PlayerTitleVo> titleList = (List<PlayerTitleVo>) SerializeUtil.unserialize(titleByte);
		for (PlayerTitleVo titlevo : titleList) {
			if (titlevo.getStatus() == 3) {
				playerTitle = titlevo;
			}

		}
	}
}
