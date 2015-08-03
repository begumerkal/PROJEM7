package com.wyd.empire.protocol.data.strengthen;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 武器碎片合成
 */
public class MergeScrapOK extends AbstractData {
    
    /** 合成结果*/
    private boolean result;
    
    /** 描述信息*/
    private String content;
    
    private int	itemId;//物品序号
    private String	name;//物品名字
    private String	icon;//relativePath/图标名称.png(资源会放到同一个目录下)
    private int	itemMainType;//物品类型
    private int	itemSubType;//物品子类型
    private int	itemNum;//物品数量
    private boolean	expired;//是否过期(true:过期。False：未过期)
    private int	strengthoneLevel;//物品（武器）强化等级
    private int	attack;//攻击力
    private int	attackArea;//攻击范围
    private int	addHP;//生命
    private int	power;//体力
    private int	defend;//防御

    
    public MergeScrapOK(int sessionId, int serial) {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_MergeScrapOK, sessionId, serial);
    }

    public MergeScrapOK() {
        super(Protocol.MAIN_STRENGTHEN, Protocol.STRENGTHEN_MergeScrapOK);
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }


	public boolean getExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public int getStrengthoneLevel() {
		return strengthoneLevel;
	}

	public void setStrengthoneLevel(int strengthoneLevel) {
		this.strengthoneLevel = strengthoneLevel;
	}


	public int getAttackArea() {
		return attackArea;
	}

	public void setAttackArea(int attackArea) {
		this.attackArea = attackArea;
	}

	public int getAddHP() {
		return addHP;
	}

	public void setAddHP(int addHP) {
		this.addHP = addHP;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getItemMainType() {
		return itemMainType;
	}

	public void setItemMainType(int itemMainType) {
		this.itemMainType = itemMainType;
	}

	public int getItemSubType() {
		return itemSubType;
	}

	public void setItemSubType(int itemSubType) {
		this.itemSubType = itemSubType;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getDefend() {
		return defend;
	}

	public void setDefend(int defend) {
		this.defend = defend;
	}

    
    
}
