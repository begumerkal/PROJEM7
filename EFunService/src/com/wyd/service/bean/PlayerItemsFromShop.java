package com.wyd.service.bean;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * The persistent class for the tab_playeritemsfromshop database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_playeritemsfromshop")
public class PlayerItemsFromShop implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;
    private Integer           id;
    private Date              buyTime;
    private Boolean           dispearAtOverTime;
    private int               hollNum;
    private int               hollUsedNum;
    private boolean           isInUsed;
    private int               PAddAttack;
    private int               PAddDefend;
    private int               PAddHp;
    // private int PAddPower;
    // private int PAttackArea;
    // private int PCriticalCoefficient;
    private int               PExpExtraRate;
    private int               PLastNum;
    private int               PLastTime;
    private int               PUseLastTime;
    private int               skillful;
    private int               strongLevel;
    private Player            player;
    private ShopItem          shopItem;
    private int               weapSkill1;
    private int               weapSkill2;
    // private int pAddInjuryFree; // 免伤
    private int               pAddWreckDefense;     // 破防
    private int               pAddReduceCrit;       // 免暴
    // private int pAddReduceBury; // 免坑
    private int               pAddForce;            // 力量
    private int               pAddArmor;            // 护甲
    private int               pAddAgility;          // 敏捷
    private int               pAddPhysique;         // 体质
    private int               pAddLuck;             // 幸运
    private int               missTimes;            // 强化失败次数
    private int               skillLock;            // 被锁定的技能
    private int               attackStone;          // 攻击石（镶嵌）
    private int               defenseStone;         // 防御石（镶嵌）
    private int               specialStone;         // 特殊石（镶嵌）
    private int               pAddcrit;             // 暴击
    private int               stars;                // 装备星级
    private Date              updateTime;           // 更新时间

    public PlayerItemsFromShop() {
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
    @Column(name = "buyTime")
    public Date getBuyTime() {
        buyTime = null == buyTime ? new Date(System.currentTimeMillis()) : buyTime;
        return this.buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    @Basic()
    @Column(name = "dispearAtOverTime", precision = 3)
    public Boolean getDispearAtOverTime() {
        return this.dispearAtOverTime;
    }

    public void setDispearAtOverTime(Boolean dispearAtOverTime) {
        this.dispearAtOverTime = dispearAtOverTime;
    }

    @Basic()
    @Column(name = "hollNum", precision = 10)
    public int getHollNum() {
        return this.hollNum;
    }

    public void setHollNum(int hollNum) {
        this.hollNum = hollNum;
    }

    @Basic()
    @Column(name = "hollUsedNum", precision = 10)
    public int getHollUsedNum() {
        return this.hollUsedNum;
    }

    public void setHollUsedNum(int hollUsedNum) {
        this.hollUsedNum = hollUsedNum;
    }

    @Basic()
    @Column(name = "isInUsed")
    public Boolean getIsInUsed() {
        return this.isInUsed;
    }

    public void setIsInUsed(Boolean isInUsed) {
        this.isInUsed = isInUsed;
    }

    @Basic()
    @Column(name = "p_addAttack", precision = 10)
    public int getPAddAttack() {
        return PAddAttack;
    }

    public void setPAddAttack(int pAddAttack) {
        PAddAttack = pAddAttack;
    }

    @Basic()
    @Column(name = "p_addDefend", precision = 10)
    public int getPAddDefend() {
        return PAddDefend;
    }

    public void setPAddDefend(int pAddDefend) {
        PAddDefend = pAddDefend;
    }

    @Basic()
    @Column(name = "p_addHP", precision = 10)
    public int getPAddHp() {
        return PAddHp;
    }

    public void setPAddHp(int pAddHp) {
        PAddHp = pAddHp;
    }

    @Basic()
    @Column(name = "p_expExtraRate", precision = 10)
    public int getPExpExtraRate() {
        return this.PExpExtraRate;
    }

    public void setPExpExtraRate(int PExpExtraRate) {
        this.PExpExtraRate = PExpExtraRate;
    }

    @Basic()
    @Column(name = "p_lastNum", precision = 10)
    public int getPLastNum() {
        return this.PLastNum;
    }

    public void setPLastNum(int PLastNum) {
        this.PLastNum = PLastNum;
    }

    @Basic()
    @Column(name = "p_lastTime", precision = 10)
    public int getPLastTime() {
        return this.PLastTime;
    }

    public void setPLastTime(int PLastTime) {
        this.PLastTime = PLastTime;
    }

    @Basic()
    @Column(name = "p_useLastTime", precision = 10)
    public int getPUseLastTime() {
        return this.PUseLastTime;
    }

    public void setPUseLastTime(int PUseLastTime) {
        this.PUseLastTime = PUseLastTime;
    }

    @Basic()
    @Column(name = "skillful", precision = 10)
    public int getSkillful() {
        return this.skillful;
    }

    public void setSkillful(int skillful) {
        this.skillful = skillful;
    }

    @Basic()
    @Column(name = "strongLevel", precision = 10)
    public int getStrongLevel() {
        return this.strongLevel;
    }

    public void setStrongLevel(int strongLevel) {
        this.strongLevel = strongLevel;
    }

    // bi-directional many-to-one association to Player
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playerId", referencedColumnName = "id")
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    // bi-directional many-to-one association to ShopItem
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shopItemId", referencedColumnName = "id")
    public ShopItem getShopItem() {
        return this.shopItem;
    }

    public void setShopItem(ShopItem shopItem) {
        this.shopItem = shopItem;
    }

    @Basic()
    @Column(name = "weapSkill1", precision = 10)
    public int getWeapSkill1() {
        return weapSkill1;
    }

    public void setWeapSkill1(int weapSkill1) {
        this.weapSkill1 = weapSkill1;
    }

    @Basic()
    @Column(name = "weapSkill2", precision = 10)
    public int getWeapSkill2() {
        return weapSkill2;
    }

    public void setWeapSkill2(int weapSkill2) {
        this.weapSkill2 = weapSkill2;
    }

    @Basic()
    @Column(name = "p_add_wreck_defense", precision = 10)
    public int getpAddWreckDefense() {
        return pAddWreckDefense;
    }

    public void setpAddWreckDefense(int pAddWreckDefense) {
        this.pAddWreckDefense = pAddWreckDefense;
    }

    @Basic()
    @Column(name = "p_add_reduce_crit", precision = 10)
    public int getpAddReduceCrit() {
        return pAddReduceCrit;
    }

    public void setpAddReduceCrit(int pAddReduceCrit) {
        this.pAddReduceCrit = pAddReduceCrit;
    }

    @Basic()
    @Column(name = "p_add_force", precision = 10)
    public int getpAddForce() {
        return pAddForce;
    }

    public void setpAddForce(int pAddForce) {
        this.pAddForce = pAddForce;
    }

    @Basic()
    @Column(name = "p_add_armor", precision = 10)
    public int getpAddArmor() {
        return pAddArmor;
    }

    public void setpAddArmor(int pAddArmor) {
        this.pAddArmor = pAddArmor;
    }

    @Basic()
    @Column(name = "p_add_agility", precision = 10)
    public int getpAddAgility() {
        return pAddAgility;
    }

    public void setpAddAgility(int pAddAgility) {
        this.pAddAgility = pAddAgility;
    }

    @Basic()
    @Column(name = "p_add_physique", precision = 10)
    public int getpAddPhysique() {
        return pAddPhysique;
    }

    public void setpAddPhysique(int pAddPhysique) {
        this.pAddPhysique = pAddPhysique;
    }

    @Basic()
    @Column(name = "p_add_luck", precision = 10)
    public int getpAddLuck() {
        return pAddLuck;
    }

    public void setpAddLuck(int pAddLuck) {
        this.pAddLuck = pAddLuck;
    }

    @Basic()
    @Column(name = "miss_times", precision = 10)
    public int getMissTimes() {
        return missTimes;
    }

    public void setMissTimes(int missTimes) {
        this.missTimes = missTimes;
    }

    @Basic()
    @Column(name = "skillLock", precision = 10)
    public int getSkillLock() {
        return skillLock;
    }

    public void setSkillLock(int skillLock) {
        this.skillLock = skillLock;
    }

    @Basic()
    @Column(name = "attackStone", precision = 10)
    public int getAttackStone() {
        return attackStone;
    }

    public void setAttackStone(int attackStone) {
        this.attackStone = attackStone;
    }

    @Basic()
    @Column(name = "defenseStone", precision = 10)
    public int getDefenseStone() {
        return defenseStone;
    }

    public void setDefenseStone(int defenseStone) {
        this.defenseStone = defenseStone;
    }

    @Basic()
    @Column(name = "specialStone", precision = 10)
    public int getSpecialStone() {
        return specialStone;
    }

    public void setSpecialStone(int specialStone) {
        this.specialStone = specialStone;
    }

    @Basic()
    @Column(name = "p_add_crit", precision = 10)
    public int getpAddcrit() {
        return pAddcrit;
    }

    public void setpAddcrit(int pAddcrit) {
        this.pAddcrit = pAddcrit;
    }

    @Basic()
    @Column(name = "stars", precision = 2)
    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    @Basic()
    @Column(name = "update_time")
    public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	

   

   

    
}