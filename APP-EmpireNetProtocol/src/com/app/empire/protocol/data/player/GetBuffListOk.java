package com.app.empire.protocol.data.player;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>GetSkillList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_PLAYER下子命令PLAYER_GetBuffList(获取Buff列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetBuffListOk extends AbstractData {
	private String[]	buffName;	//名称
	private String[]	icon;	//图标
	private int[]   countdownTime; //倒计时分钟数
	private int[]	addType;	//加成类型（0表示数值加成，1表示百分比加成）
	private int[]	param1;	//加成参数（加成类型为0时，该数值是额外加成的数值，如果加成类型为1时，该数值是加成的万分比）
	private int[]	param2;	//预留参数
	private int[]	param3;	//预留参数


    public GetBuffListOk(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetBuffList, sessionId, serial);
    }

    public GetBuffListOk() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetBuffList);
    }

	public String[] getBuffName() {
		return buffName;
	}

	public void setBuffName(String[] buffName) {
		this.buffName = buffName;
	}

	public String[] getIcon() {
		return icon;
	}

	public void setIcon(String[] icon) {
		this.icon = icon;
	}

	public int[] getAddType() {
		return addType;
	}

	public void setAddType(int[] addType) {
		this.addType = addType;
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

	public int[] getParam3() {
		return param3;
	}

	public void setParam3(int[] param3) {
		this.param3 = param3;
	}

	public int[] getCountdownTime() {
		return countdownTime;
	}

	public void setCountdownTime(int[] countdownTime) {
		this.countdownTime = countdownTime;
	}

}
