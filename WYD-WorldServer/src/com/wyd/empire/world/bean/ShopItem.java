package com.wyd.empire.world.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.wyd.empire.world.common.util.StrValueUtils;

/**
 * The persistent class for the tab_shopitems database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_shopitems")
public class ShopItem implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private int id;
	private Integer addAttack;
	private Integer addCritical;
	private Integer addDefend;
	private Integer addHp;
	private Integer addPower; // 体力
	private String animationIndexCode;
	private Integer attackArea;
	private int autoUse;
	private String desc;
	private Boolean dispearAtOverTime;
	private Integer expExtraRate;
	private Integer hollForStoneId;
	private String icon;
	private Boolean isOnSale;
	private int level;
	private int maxHollNum;
	private String name;
	private Date offSaleTime;
	private Date onSaleTime;
	private int powerskill;
	private Integer saledNum;
	private int sex;
	private int strongByStoneId;
	private byte subtype; // type=6,subytype=7 属于碎片
	private byte type;
	private int useLastTime;
	private String weaponblastinganimation;
	private String weaponfollow;
	private String strengthen;
	private int move;
	private int useType; // 0使用数量，1使用天数
	private int addInjuryFree; // 免伤
	private int addWreckDefense; // 破防
	private int addReduceCrit; // 免暴
	private int addReduceBury; // 免坑
	private int addForce; // 力量
	private int addArmor; // 护甲
	private int addAgility; // 敏捷
	private int addPhysique; // 体质
	private int addLuck; // 幸运
	private int saleAgain;
	private int canRecycle; // 装备是否可回收

	public ShopItem() {
	}

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic()
	@Column(name = "addAttack", nullable = false, precision = 10)
	public Integer getAddAttack() {
		return this.addAttack;
	}

	public void setAddAttack(Integer addAttack) {
		this.addAttack = addAttack;
	}

	@Basic()
	@Column(name = "addCritical", nullable = false, precision = 10)
	public Integer getAddCritical() {
		return this.addCritical;
	}

	public void setAddCritical(Integer addCritical) {
		this.addCritical = addCritical;
	}

	@Basic()
	@Column(name = "addDefend", nullable = false, precision = 10)
	public Integer getAddDefend() {
		return this.addDefend;
	}

	public void setAddDefend(Integer addDefend) {
		this.addDefend = addDefend;
	}

	@Basic()
	@Column(name = "addHP", nullable = false, precision = 10)
	public Integer getAddHp() {
		return this.addHp;
	}

	public void setAddHp(Integer addHp) {
		this.addHp = addHp;
	}

	@Basic()
	@Column(name = "addPower", nullable = false, precision = 10)
	public Integer getAddPower() {
		return this.addPower;
	}

	public void setAddPower(Integer addPower) {
		this.addPower = addPower;
	}

	@Basic()
	@Column(name = "animationIndexCode", nullable = false, length = 256)
	public String getAnimationIndexCode() {
		return this.animationIndexCode;
	}

	public void setAnimationIndexCode(String animationIndexCode) {
		this.animationIndexCode = animationIndexCode;
	}

	@Basic()
	@Column(name = "attackArea", nullable = false, precision = 10)
	public Integer getAttackArea() {
		return this.attackArea;
	}

	public void setAttackArea(Integer attackArea) {
		this.attackArea = attackArea;
	}

	@Basic()
	@Column(name = "autoUse", nullable = false, precision = 10)
	public Integer getAutoUse() {
		return this.autoUse;
	}

	public void setAutoUse(Integer autoUse) {
		this.autoUse = autoUse;
	}

	// @Basic()
	// @Column(name = "criticalCoefficient", nullable = false, precision = 10)
	// public Integer getCriticalCoefficient() {
	// return this.criticalCoefficient;
	// }
	//
	// public void setCriticalCoefficient(Integer criticalCoefficient) {
	// this.criticalCoefficient = criticalCoefficient;
	// }

	@Basic()
	@Column(name = "item_desc", nullable = false, length = 128)
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Basic()
	@Column(name = "dispearAtOverTime", nullable = false)
	public Boolean getDispearAtOverTime() {
		return this.dispearAtOverTime;
	}

	public void setDispearAtOverTime(Boolean dispearAtOverTime) {
		this.dispearAtOverTime = dispearAtOverTime;
	}

	@Basic()
	@Column(name = "expExtraRate", nullable = false, precision = 10)
	public Integer getExpExtraRate() {
		return this.expExtraRate;
	}

	public void setExpExtraRate(Integer expExtraRate) {
		this.expExtraRate = expExtraRate;
	}

	@Basic()
	@Column(name = "hollForStoneId", nullable = false, precision = 10)
	public Integer getHollForStoneId() {
		return this.hollForStoneId;
	}

	public void setHollForStoneId(Integer hollForStoneId) {
		this.hollForStoneId = hollForStoneId;
	}

	@Basic()
	@Column(name = "icon", nullable = false, length = 256)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Basic()
	@Column(name = "isOnSale", nullable = false)
	public Boolean getIsOnSale() {
		return this.isOnSale;
	}

	public void setIsOnSale(Boolean isOnSale) {
		this.isOnSale = isOnSale;
	}

	@Basic()
	@Column(name = "level", nullable = false, precision = 10)
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Basic()
	@Column(name = "maxHollNum", nullable = false, precision = 10)
	public Integer getMaxHollNum() {
		return this.maxHollNum;
	}

	public void setMaxHollNum(Integer maxHollNum) {
		this.maxHollNum = maxHollNum;
	}

	@Basic()
	@Column(name = "name", nullable = false, length = 16)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic()
	@Column(name = "offSaleTime", nullable = false)
	public Date getOffSaleTime() {
		return this.offSaleTime;
	}

	public void setOffSaleTime(Date offSaleTime) {
		this.offSaleTime = offSaleTime;
	}

	@Basic()
	@Column(name = "onSaleTime", nullable = false)
	public Date getOnSaleTime() {
		return this.onSaleTime;
	}

	public void setOnSaleTime(Date onSaleTime) {
		this.onSaleTime = onSaleTime;
	}

	@Basic()
	@Column(name = "powerskill", nullable = false, precision = 10)
	public Integer getPowerskill() {
		return this.powerskill;
	}

	public void setPowerskill(Integer powerskill) {
		this.powerskill = powerskill;
	}

	@Basic()
	@Column(name = "saledNum", nullable = false, precision = 10)
	public Integer getSaledNum() {
		return this.saledNum;
	}

	public void setSaledNum(Integer saledNum) {
		this.saledNum = saledNum;
	}

	@Basic()
	@Column(name = "sex", nullable = false, precision = 10)
	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Basic()
	@Column(name = "strongByStoneId", nullable = false, precision = 10)
	public Integer getStrongByStoneId() {
		return this.strongByStoneId;
	}

	public void setStrongByStoneId(Integer strongByStoneId) {
		this.strongByStoneId = strongByStoneId;
	}

	@Basic()
	@Column(name = "subtype", nullable = false, precision = 3)
	public Byte getSubtype() {
		return this.subtype;
	}

	public void setSubtype(Byte subtype) {
		this.subtype = subtype;
	}

	@Basic()
	@Column(name = "type", nullable = false, precision = 3)
	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	@Basic()
	@Column(name = "useLastTime", nullable = false, precision = 10)
	public Integer getUseLastTime() {
		return this.useLastTime;
	}

	public void setUseLastTime(Integer useLastTime) {
		this.useLastTime = useLastTime;
	}

	@Basic()
	@Column(name = "weaponblastinganimation", nullable = false, length = 256)
	public String getWeaponblastinganimation() {
		return this.weaponblastinganimation;
	}

	public void setWeaponblastinganimation(String weaponblastinganimation) {
		this.weaponblastinganimation = weaponblastinganimation;
	}

	@Basic()
	@Column(name = "weaponfollow", nullable = false, length = 256)
	public String getWeaponfollow() {
		return this.weaponfollow;
	}

	public void setWeaponfollow(String weaponfollow) {
		this.weaponfollow = weaponfollow;
	}

	@Basic()
	@Column(name = "strengthen", nullable = false, length = 100)
	public String getStrengthen() {
		return strengthen;
	}

	public void setStrengthen(String strengthen) {
		this.strengthen = strengthen;
	}

	@Basic()
	@Column(name = "move", nullable = false, length = 100)
	public int getMove() {
		return move;
	}

	public void setMove(int move) {
		this.move = move;
	}

	@Basic()
	@Column(name = "use_type", nullable = false, length = 100)
	public int getUseType() {
		return useType;
	}

	public void setUseType(int useType) {
		this.useType = useType;
	}

	@Basic()
	@Column(name = "add_injury_free", precision = 10)
	public int getAddInjuryFree() {
		return addInjuryFree;
	}

	public void setAddInjuryFree(int addInjuryFree) {
		this.addInjuryFree = addInjuryFree;
	}

	@Basic()
	@Column(name = "add_wreck_defense", precision = 10)
	public int getAddWreckDefense() {
		return addWreckDefense;
	}

	public void setAddWreckDefense(int addWreckDefense) {
		this.addWreckDefense = addWreckDefense;
	}

	@Basic()
	@Column(name = "add_reduce_crit", precision = 10)
	public int getAddReduceCrit() {
		return addReduceCrit;
	}

	public void setAddReduceCrit(int addReduceCrit) {
		this.addReduceCrit = addReduceCrit;
	}

	@Basic()
	@Column(name = "add_reduce_bury", precision = 10)
	public int getAddReduceBury() {
		return addReduceBury;
	}

	public void setAddReduceBury(int addReduceBury) {
		this.addReduceBury = addReduceBury;
	}

	@Basic()
	@Column(name = "add_force", precision = 10)
	public int getAddForce() {
		return addForce;
	}

	public void setAddForce(int addForce) {
		this.addForce = addForce;
	}

	@Basic()
	@Column(name = "add_armor", precision = 10)
	public int getAddArmor() {
		return addArmor;
	}

	public void setAddArmor(int addArmor) {
		this.addArmor = addArmor;
	}

	@Basic()
	@Column(name = "add_agility", precision = 10)
	public int getAddAgility() {
		return addAgility;
	}

	public void setAddAgility(int addAgility) {
		this.addAgility = addAgility;
	}

	@Basic()
	@Column(name = "add_physique", precision = 10)
	public int getAddPhysique() {
		return addPhysique;
	}

	public void setAddPhysique(int addPhysique) {
		this.addPhysique = addPhysique;
	}

	@Basic()
	@Column(name = "add_luck", precision = 10)
	public int getAddLuck() {
		return addLuck;
	}

	public void setAddLuck(int addLuck) {
		this.addLuck = addLuck;
	}

	@Basic()
	@Column(name = "sale_again", precision = 10)
	public int getSaleAgain() {
		return saleAgain;
	}

	public void setSaleAgain(int saleAgain) {
		this.saleAgain = saleAgain;
	}

	@Basic()
	@Column(name = "can_recycle", precision = 10)
	public int getCanRecycle() {
		return canRecycle;
	}

	public void setCanRecycle(int canRecycle) {
		this.canRecycle = canRecycle;
	}

	public void check() {
		addAttack = null == addAttack ? 0 : addAttack;
		addCritical = null == addCritical ? 0 : addCritical;
		addDefend = null == addDefend ? 0 : addDefend;
		addHp = null == addHp ? 0 : addHp;
		addPower = null == addPower ? 0 : addPower;
		animationIndexCode = null == animationIndexCode ? "" : animationIndexCode;
		animationIndexCode = ("-1").equals(animationIndexCode) ? "" : animationIndexCode;
		attackArea = null == attackArea ? 0 : attackArea;
		desc = null == desc ? "" : desc;
		desc = ("-1").equals(desc) ? "" : desc;
		dispearAtOverTime = null == dispearAtOverTime ? false : dispearAtOverTime;
		expExtraRate = null == expExtraRate ? 0 : expExtraRate;
		hollForStoneId = null == hollForStoneId ? 0 : hollForStoneId;
		icon = null == icon ? "" : icon;
		isOnSale = null == isOnSale ? false : isOnSale;
		name = null == name ? "" : name;
		offSaleTime = null == offSaleTime ? new Date(System.currentTimeMillis()) : offSaleTime;
		onSaleTime = null == onSaleTime ? new Date(System.currentTimeMillis()) : onSaleTime;
		saledNum = null == saledNum ? 0 : saledNum;

	}

	public Strengthen StrengthenToObj() {
		return new Strengthen(getStrengthen());
	}

	public String powerskillName() {
		StrValueUtils str2Val = StrValueUtils.getInstance();
		switch (this.powerskill) {
			case 0 :
				return str2Val.getValueByKey("POWERSKILL0");
			case 1 :
				return str2Val.getValueByKey("POWERSKILL1");
			case 2 :
				return str2Val.getValueByKey("POWERSKILL2");
			default :
				return "";
		}
	}

	/**
	 * 物品是否是装备（武器 头 脸 身 项链 戒指 翅膀）
	 * 
	 * @param item
	 * @return
	 */
	@Transient
	public boolean isEquipment() {
		return isWeapon() || isBody() || isFace() || isHead() || isNecklace() || isRing() || isWing();
	}

	/**
	 * 物品是否是头部
	 * 
	 * @param item
	 * @return
	 */
	@Transient
	public boolean isHead() {
		return getType() == 4;
	}

	/**
	 * 物品是否是脸谱
	 * 
	 * @param item
	 * @return
	 */
	@Transient
	public boolean isFace() {
		return getType() == 3;
	}

	/**
	 * 物品是否是衣服
	 * 
	 * @return
	 */
	@Transient
	public boolean isBody() {
		return getType() == 2 && getSubtype() == 1;
	}

	/**
	 * 物品是否是武器
	 * 
	 * @return
	 */
	@Transient
	public boolean isWeapon() {
		return getType() < 2;
	}

	/**
	 * 物品是否是翅膀装备
	 * 
	 * @return
	 */
	@Transient
	public boolean isWing() {
		return getType() == 16 && getSubtype() == 1;
	}

	/**
	 * 物品是否是戒指装备
	 */
	@Transient
	public boolean isRing() {
		return getType() == 17;
	}

	/**
	 * 物品是否是戒指装备(左)
	 */
	@Transient
	public boolean isRing_L() {
		return getType() == 17 && getSubtype() == 1;
	}

	/**
	 * 物品是否是戒指装备(右)
	 */
	@Transient
	public boolean isRing_R() {
		return getType() == 17 && getSubtype() == 2;
	}

	/**
	 * 物品是否是项链装备
	 * 
	 * @return
	 */
	@Transient
	public boolean isNecklace() {
		return getType() == 18;
	}

	/**
	 * 从strengthen里得到tk的值
	 * 
	 * @param strengthen
	 * @return
	 */
	@Transient
	public int getTkId() {
		if (strengthen == null)
			return 0;
		try {
			if (strengthen.length() > 3 && strengthen.indexOf("tk") != -1 && strengthen.indexOf("=") != -1) {
				if (strengthen.indexOf(",") == -1) {
					return Integer.parseInt(strengthen.split("=")[1]);
				} else {
					String[] keySet = strengthen.split(",");
					String tkVal = keySet[0].split("=")[2];
					return Integer.parseInt(tkVal);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("解析套卡字段出错");
		}
		return -1;
	}

	/**
	 * 升星经验 从strengthen里得到exp的值 exp=200
	 * 
	 * @param strengthen
	 * @return
	 */
	@Transient
	public int getUpStarExp() {
		if (strengthen == null)
			return 0;
		try {
			if (strengthen.length() > 3 && strengthen.indexOf("exp") != -1 && strengthen.indexOf("=") != -1) {
				if (strengthen.indexOf(",") == -1) {
					return Integer.parseInt(strengthen.split("=")[1]);
				} else {
					String[] keySet = strengthen.split(",");
					String tkVal = keySet[0].split("=")[2];
					return Integer.parseInt(tkVal);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("解析升星经验段出错");
		}
		return -1;
	}

}