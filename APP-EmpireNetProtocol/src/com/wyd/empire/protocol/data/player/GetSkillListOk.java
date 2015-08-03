package com.wyd.empire.protocol.data.player;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>GetSkillListOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_PLAYER下子命令PLAYER_GetSkillListOk(获取技能成功列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetSkillListOk extends AbstractData {
	
	private int	count;		//数量
	private int[]	id;	//道具序号
	private String[]	name;		//道具名字
	private String[]	icon;		//relativePath/图标名称.png(资源会放到同一个目录下)
	private int[]	priceCostGold;		//价格消耗多少金币
	private String[]	desc;		//物品描述
	private byte[]	mainType;		//0：技能 1：道具
	private byte[]	isSubType;		
	private int[]	param1;		//参数1    不同类型的道具参数的成值，意义都不同
	private int[]	param2;		//参数2   不同类型的道具参数的成值，意义都不同
	private int[]	tireValue;		//使用这个道使资增加的疲劳值
	private int[]	consumePower;		//体力消耗
	private int[]	specialAttackType;		//附加的特殊攻击类型
	private int[]	specialAttackParam;		//附加的特殊攻击数值参数


    public GetSkillListOk(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetSkillListOk, sessionId, serial);
    }

    public GetSkillListOk() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetSkillListOk);
    }

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int[] getId() {
		return id;
	}

	public void setId(int[] id) {
		this.id = id;
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

	public int[] getPriceCostGold() {
		return priceCostGold;
	}

	public void setPriceCostGold(int[] priceCostGold) {
		this.priceCostGold = priceCostGold;
	}

	public String[] getDesc() {
		return desc;
	}

	public void setDesc(String[] desc) {
		this.desc = desc;
	}

	public byte[] getMainType() {
		return mainType;
	}

	public void setMainType(byte[] mainType) {
		this.mainType = mainType;
	}

	public byte[] getIsSubType() {
		return isSubType;
	}

	public void setIsSubType(byte[] isSubType) {
		this.isSubType = isSubType;
	}

	public int[] getParam1() {
		return param1;
	}
	
	public void setParam1(int[] param1) {
		this.param1 = param1;
	}

	public int[] getParam2() {
		return param2;
	}

	public void setParam2(int[] param2) {
		this.param2 = param2;
	}

	public int[] getTireValue() {
		return tireValue;
	}

	public void setTireValue(int[] tireValue) {
		this.tireValue = tireValue;
	}

	public int[] getConsumePower() {
		return consumePower;
	}

	public void setConsumePower(int[] consumePower) {
		this.consumePower = consumePower;
	}

	public int[] getSpecialAttackType() {
		return specialAttackType;
	}

	public void setSpecialAttackType(int[] specialAttackType) {
		this.specialAttackType = specialAttackType;
	}

	public int[] getSpecialAttackParam() {
		return specialAttackParam;
	}

	public void setSpecialAttackParam(int[] specialAttackParam) {
		this.specialAttackParam = specialAttackParam;
	}



    

}
