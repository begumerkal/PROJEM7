package com.wyd.empire.protocol.data.strengthen;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class GetItemListOk extends AbstractData {
    private int       vipLevel;           // Vip等级
    private int       vipAddition;        // Vip等级加成
    private int       guildLevel;         // 公会等级
    private int       guildAddition;      // 公会等级加成（强化）
    private int[]     itemId;             // 物品序号
    private String[]  name;               // 物品名字
    private String[]  icon;               // 图标名称
    private int[]     itemMainType;       // 物品类型
    private int[]     itemSubType;        // 物品子类型
    private int[]     itemNum;            // 物品数量
    private boolean[] isExpired;          // 物品是否过期
    private int[]     strengthoneLevel;   // 物品（武器）强化等级
    private int[]     attack;             // 攻击力
    private int[]     attackArea;         // 攻击范围
    private int[]     addHP;              // 生命
    private int[]     power;              // 体力
    private int[]     defend;             // 防御
    private int[]     luckRate;           // 幸运石加成
    private String[]  weaponSkillDetail1; // 武器技能1描述
    private String[]  weaponSkillDetail2; // 武器技能2描述
    private int[]     weaponSkillLevel1;  // 武器技能1等级
    private int[]     weaponSkillLevel2;  // 武器技能2等级
    private int[]     attackOpen;         // 攻击宝石的开启
    private String[]  attackIcon;         // 攻击宝石的ICON
    private int[]     defendOpen;         // 防御宝石的开启
    private String[]  defendIcon;         // 防御宝石ICON
    private int[]     specialOpen;        // 特殊宝石的开启
    private String[]  specialIcon;        // 特殊宝石的ICON
    private int[]     attackStoneLevel;
    private int[]     defendStoneLevel;
    private int[]     specailStoneLevel;
    private int[]     parms;              // 镶嵌的参数
    private int[]     attackStoneType;    // 已镶嵌攻击宝石类型（未镶嵌和不可镶嵌物品为0）
    private int[]     defendStoneType;    // 已镶嵌防御宝石类型（未镶嵌和不可镶嵌物品为0）
    private int[]     specailStoneType;   // 已镶嵌特殊宝石类型（未镶嵌和不可镶嵌物品为0）
    private int[]     attackStoneSubType; // 已镶嵌攻击宝石子类型（未镶嵌和不可镶嵌物品为0）
    private int[]     defendStoneSubType; // 已镶嵌防御宝石子类型（未镶嵌和不可镶嵌物品为0）
    private int[]     specailStoneSubType;// 已镶嵌特殊宝石子类型（未镶嵌和不可镶嵌物品为0）
    private int[]     attackStoneParm;    // 已镶嵌攻击宝石参数（未镶嵌和不可镶嵌物品为0）
    private int[]     defendStoneParm;    // 已镶嵌防御宝石参数（未镶嵌和不可镶嵌物品为0）
    private int[]     specailStoneParm;   // 已镶嵌特殊宝石参数（未镶嵌和不可镶嵌物品为0）
    private int[]     starlevel;          // 装备星级
    private int[]     pAttack;            // 装备总攻击力
    private int[]     pAddHP;             // 装备总生命
    private int[]     pDefend;            // 装备总防御
    private int[]	  getInRate;		  // 合成概率
    private int[]	  costAmount;         // 拆卸每等级所需钻石数
    private int[]	  hcdj;		          // 合成目标道具ID
    private int[]     weaponSkillLock;    // 武器锁定技能ID
    private int[]	  weaponSkillId1;	  // 武器技能1Id
    private int[]	  weaponSkillId2;	  // 武器技能2Id
    private int		  guildGetInAddition;      // 公会等级加成（合成用）

    public int[] getWeaponSkillId1() {
		return weaponSkillId1;
	}

	public void setWeaponSkillId1(int[] weaponSkillId1) {
		this.weaponSkillId1 = weaponSkillId1;
	}

	public int[] getWeaponSkillId2() {
		return weaponSkillId2;
	}

	public void setWeaponSkillId2(int[] weaponSkillId2) {
		this.weaponSkillId2 = weaponSkillId2;
	}

	public int[] getWeaponSkillLock() {
		return weaponSkillLock;
	}

	public void setWeaponSkillLock(int[] weaponSkillLock) {
		this.weaponSkillLock = weaponSkillLock;
	}

	public GetItemListOk(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_GetItemListOk, sessionId, serial);
    }

    public GetItemListOk() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_GetItemListOk);
    }

    public int[] getAddHP() {
        return addHP;
    }

    public void setAddHP(int[] addHP) {
        this.addHP = addHP;
    }

    public int[] getAttack() {
        return attack;
    }

    public void setAttack(int[] attack) {
        this.attack = attack;
    }

    public int[] getAttackArea() {
        return attackArea;
    }

    public void setAttackArea(int[] attackArea) {
        this.attackArea = attackArea;
    }

    public int[] getDefend() {
        return defend;
    }

    public void setDefend(int[] defend) {
        this.defend = defend;
    }

    public String[] getIcon() {
        return icon;
    }

    public void setIcon(String[] icon) {
        this.icon = icon;
    }

    public int[] getItemId() {
        return itemId;
    }

    public void setItemId(int[] itemId) {
        this.itemId = itemId;
    }

    public int[] getItemMainType() {
        return itemMainType;
    }

    public void setItemMainType(int[] itemMainType) {
        this.itemMainType = itemMainType;
    }

    public int[] getItemNum() {
        return itemNum;
    }

    public void setItemNum(int[] itemNum) {
        this.itemNum = itemNum;
    }

    public int[] getItemSubType() {
        return itemSubType;
    }

    public void setItemSubType(int[] itemSubType) {
        this.itemSubType = itemSubType;
    }

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public int[] getPower() {
        return power;
    }

    public void setPower(int[] power) {
        this.power = power;
    }

    public int[] getStrengthoneLevel() {
        return strengthoneLevel;
    }

    public void setStrengthoneLevel(int[] strengthoneLevel) {
        this.strengthoneLevel = strengthoneLevel;
    }

    public boolean[] getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(boolean[] isExpired) {
        this.isExpired = isExpired;
    }

    public int getGuildAddition() {
        return guildAddition;
    }

    public void setGuildAddition(int guildAddition) {
        this.guildAddition = guildAddition;
    }

    public int getGuildLevel() {
        return guildLevel;
    }

    public void setGuildLevel(int guildLevel) {
        this.guildLevel = guildLevel;
    }

    public int getVipAddition() {
        return vipAddition;
    }

    public void setVipAddition(int vipAddition) {
        this.vipAddition = vipAddition;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public int[] getLuckRate() {
        return luckRate;
    }

    public void setLuckRate(int[] luckRate) {
        this.luckRate = luckRate;
    }

    public String[] getWeaponSkillDetail1() {
        return weaponSkillDetail1;
    }

    public void setWeaponSkillDetail1(String[] weaponSkillDetail1) {
        this.weaponSkillDetail1 = weaponSkillDetail1;
    }

    public String[] getWeaponSkillDetail2() {
        return weaponSkillDetail2;
    }

    public void setWeaponSkillDetail2(String[] weaponSkillDetail2) {
        this.weaponSkillDetail2 = weaponSkillDetail2;
    }

    public int[] getWeaponSkillLevel1() {
        return weaponSkillLevel1;
    }

    public void setWeaponSkillLevel1(int[] weaponSkillLevel1) {
        this.weaponSkillLevel1 = weaponSkillLevel1;
    }

    public int[] getWeaponSkillLevel2() {
        return weaponSkillLevel2;
    }

    public void setWeaponSkillLevel2(int[] weaponSkillLevel2) {
        this.weaponSkillLevel2 = weaponSkillLevel2;
    }

    public int[] getAttackOpen() {
        return attackOpen;
    }

    public void setAttackOpen(int[] attackOpen) {
        this.attackOpen = attackOpen;
    }

    public String[] getAttackIcon() {
        return attackIcon;
    }

    public void setAttackIcon(String[] attackIcon) {
        this.attackIcon = attackIcon;
    }

    public int[] getDefendOpen() {
        return defendOpen;
    }

    public void setDefendOpen(int[] defendOpen) {
        this.defendOpen = defendOpen;
    }

    public String[] getDefendIcon() {
        return defendIcon;
    }

    public void setDefendIcon(String[] defendIcon) {
        this.defendIcon = defendIcon;
    }

    public int[] getSpecialOpen() {
        return specialOpen;
    }

    public void setSpecialOpen(int[] specialOpen) {
        this.specialOpen = specialOpen;
    }

    public String[] getSpecialIcon() {
        return specialIcon;
    }

    public void setSpecialIcon(String[] specialIcon) {
        this.specialIcon = specialIcon;
    }

    public int[] getAttackStoneLevel() {
        return attackStoneLevel;
    }

    public void setAttackStoneLevel(int[] attackStoneLevel) {
        this.attackStoneLevel = attackStoneLevel;
    }

    public int[] getDefendStoneLevel() {
        return defendStoneLevel;
    }

    public void setDefendStoneLevel(int[] defendStoneLevel) {
        this.defendStoneLevel = defendStoneLevel;
    }

    public int[] getSpecailStoneLevel() {
        return specailStoneLevel;
    }

    public void setSpecailStoneLevel(int[] specailStoneLevel) {
        this.specailStoneLevel = specailStoneLevel;
    }

    public int[] getParms() {
        return parms;
    }

    public void setParms(int[] parms) {
        this.parms = parms;
    }

    public int[] getAttackStoneType() {
        return attackStoneType;
    }

    public void setAttackStoneType(int[] attackStoneType) {
        this.attackStoneType = attackStoneType;
    }

    public int[] getDefendStoneType() {
        return defendStoneType;
    }

    public void setDefendStoneType(int[] defendStoneType) {
        this.defendStoneType = defendStoneType;
    }

    public int[] getSpecailStoneType() {
        return specailStoneType;
    }

    public void setSpecailStoneType(int[] specailStoneType) {
        this.specailStoneType = specailStoneType;
    }

    public int[] getAttackStoneSubType() {
        return attackStoneSubType;
    }

    public void setAttackStoneSubType(int[] attackStoneSubType) {
        this.attackStoneSubType = attackStoneSubType;
    }

    public int[] getDefendStoneSubType() {
        return defendStoneSubType;
    }

    public void setDefendStoneSubType(int[] defendStoneSubType) {
        this.defendStoneSubType = defendStoneSubType;
    }

    public int[] getSpecailStoneSubType() {
        return specailStoneSubType;
    }

    public void setSpecailStoneSubType(int[] specailStoneSubType) {
        this.specailStoneSubType = specailStoneSubType;
    }

    public int[] getAttackStoneParm() {
        return attackStoneParm;
    }

    public void setAttackStoneParm(int[] attackStoneParm) {
        this.attackStoneParm = attackStoneParm;
    }

    public int[] getDefendStoneParm() {
        return defendStoneParm;
    }

    public void setDefendStoneParm(int[] defendStoneParm) {
        this.defendStoneParm = defendStoneParm;
    }

    public int[] getSpecailStoneParm() {
        return specailStoneParm;
    }

    public void setSpecailStoneParm(int[] specailStoneParm) {
        this.specailStoneParm = specailStoneParm;
    }

    public int[] getStarlevel() {
        return starlevel;
    }

    public void setStarlevel(int[] starlevel) {
        this.starlevel = starlevel;
    }

    public int[] getpAttack() {
        return pAttack;
    }

    public void setpAttack(int[] pAttack) {
        this.pAttack = pAttack;
    }

    public int[] getpAddHP() {
        return pAddHP;
    }

    public void setpAddHP(int[] pAddHP) {
        this.pAddHP = pAddHP;
    }

    public int[] getpDefend() {
        return pDefend;
    }

    public void setpDefend(int[] pDefend) {
        this.pDefend = pDefend;
    }

	public int[] getGetInRate() {
		return getInRate;
	}

	public void setGetInRate(int[] getInRate) {
		this.getInRate = getInRate;
	}

	public int[] getHcdj() {
		return hcdj;
	}

	public void setHcdj(int[] hcdj) {
		this.hcdj = hcdj;
	}

	public int[] getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(int[] costAmount) {
		this.costAmount = costAmount;
	}

	public int getGuildGetInAddition() {
		return guildGetInAddition;
	}

	public void setGuildGetInAddition(int guildGetInAddition) {
		this.guildGetInAddition = guildGetInAddition;
	}
}
