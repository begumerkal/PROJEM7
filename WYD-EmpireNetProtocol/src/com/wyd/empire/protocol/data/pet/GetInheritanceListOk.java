package com.wyd.empire.protocol.data.pet;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetInheritanceListOk extends AbstractData {
    private int[]    petId;         // 宠物ID
    private String[] name;          // 名称
    private String[] icon;          // 宠物图标
    private int[]    petLevel;      // 级别
    private int[]    initHp;        // 初始生命
    private int[]    initAttack;    // 初始攻击
    private int[]    initDefend;    // 初始防御
    private int[]    hasHp;         // 已有的生命
    private int[]    hasAttack;     // 已有的攻击
    private int[]    hasDefend;     // 已有的防御
    private int[]    isInheritance; // 是否传承过（0没有，1传承过）
    private int[]    isBeInherited; // 是否被传承（0否，1是）
    private int      needDiamond;   // 传承需要钻石
    private int[]    isPlayed;      // 是否已出战
    private int[]    hpGrowth;      // 生命成长系数
    private int[]    attackGrowth;  // 攻击成长系数
    private int[]    defendGrowth;  // 防御成长系数
    private int[]    culHp;         // 培养的生命
    private int[]    culAttack;     // 培养的初始攻击
    private int[]    culDefend;     // 培养的初始防御

    // 生命=初始生命+生命成长系数*(level-1)+ 培养的生命
	public GetInheritanceListOk(int sessionId, int serial) {
		super(Protocol.MAIN_PET, Protocol.PET_GetInheritanceListOk, sessionId,
				serial);
	}

	public GetInheritanceListOk() {
		super(Protocol.MAIN_PET, Protocol.PET_GetInheritanceListOk);
	}

    public int[] getPetId() {
        return petId;
    }

    public void setPetId(int[] petId) {
        this.petId = petId;
    }

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public String[] getIcon() {
        return icon;
    }

    public void setIcon(String[] icon) {
        this.icon = icon;
    }

    public int[] getPetLevel() {
        return petLevel;
    }

    public void setPetLevel(int[] petLevel) {
        this.petLevel = petLevel;
    }

    public int[] getInitHp() {
        return initHp;
    }

    public void setInitHp(int[] initHp) {
        this.initHp = initHp;
    }

    public int[] getInitAttack() {
        return initAttack;
    }

    public void setInitAttack(int[] initAttack) {
        this.initAttack = initAttack;
    }

    public int[] getInitDefend() {
        return initDefend;
    }

    public void setInitDefend(int[] initDefend) {
        this.initDefend = initDefend;
    }

    public int[] getHasHp() {
        return hasHp;
    }

    public void setHasHp(int[] hasHp) {
        this.hasHp = hasHp;
    }

    public int[] getHasAttack() {
        return hasAttack;
    }

    public void setHasAttack(int[] hasAttack) {
        this.hasAttack = hasAttack;
    }

    public int[] getHasDefend() {
        return hasDefend;
    }

    public void setHasDefend(int[] hasDefend) {
        this.hasDefend = hasDefend;
    }

    public int[] getIsInheritance() {
        return isInheritance;
    }

    public void setIsInheritance(int[] isInheritance) {
        this.isInheritance = isInheritance;
    }

    public int[] getIsBeInherited() {
        return isBeInherited;
    }

    public void setIsBeInherited(int[] isBeInherited) {
        this.isBeInherited = isBeInherited;
    }

    public int getNeedDiamond() {
        return needDiamond;
    }

    public void setNeedDiamond(int needDiamond) {
        this.needDiamond = needDiamond;
    }

    public int[] getIsPlayed() {
        return isPlayed;
    }

    public void setIsPlayed(int[] isPlayed) {
        this.isPlayed = isPlayed;
    }

    public int[] getHpGrowth() {
        return hpGrowth;
    }

    public void setHpGrowth(int[] hpGrowth) {
        this.hpGrowth = hpGrowth;
    }

    public int[] getAttackGrowth() {
        return attackGrowth;
    }

    public void setAttackGrowth(int[] attackGrowth) {
        this.attackGrowth = attackGrowth;
    }

    public int[] getDefendGrowth() {
        return defendGrowth;
    }

    public void setDefendGrowth(int[] defendGrowth) {
        this.defendGrowth = defendGrowth;
    }

    public int[] getCulHp() {
        return culHp;
    }

    public void setCulHp(int[] culHp) {
        this.culHp = culHp;
    }

    public int[] getCulAttack() {
        return culAttack;
    }

    public void setCulAttack(int[] culAttack) {
        this.culAttack = culAttack;
    }

    public int[] getCulDefend() {
        return culDefend;
    }

    public void setCulDefend(int[] culDefend) {
        this.culDefend = culDefend;
    }
}
