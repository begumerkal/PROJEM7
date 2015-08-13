package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetMaritalStatusOK extends AbstractData {
	private int	marryStatus;	//婚姻状态（0：表示未婚，1：表示已订婚，2：表示已婚）
	private int	coupleId;	//伴侣的Id
	private String	coupleName;	//伴侣名称
	private int	weddingType;	//婚礼类型（0：无，1：奢华，2：豪华，3：浪漫，4：普通）
	private int[] removeDiamond;	//解除关系所需金币
	private int[] marryDiamond;	//结婚需要钻石
	private String detail;	
	private int rightToMarry; //结婚权利（玩家可以发送钻石的等级）
	private int canGiveDiamond; //可以结婚赠送的钻石数
	private int wedTime;	//距离结婚时间的秒数（只有等待结婚的人才有，订婚或已经结过婚的为-1）

    public GetMaritalStatusOK(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GetMaritalStatusOK, sessionId, serial);
    }

    public GetMaritalStatusOK() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GetMaritalStatusOK);
    }

	public int getMarryStatus() {
		return marryStatus;
	}

	public void setMarryStatus(int marryStatus) {
		this.marryStatus = marryStatus;
	}

	public int getCoupleId() {
		return coupleId;
	}

	public void setCoupleId(int coupleId) {
		this.coupleId = coupleId;
	}

	public String getCoupleName() {
		return coupleName;
	}

	public void setCoupleName(String coupleName) {
		this.coupleName = coupleName;
	}

	public int getWeddingType() {
		return weddingType;
	}

	public void setWeddingType(int weddingType) {
		this.weddingType = weddingType;
	}

	public int[] getRemoveDiamond() {
		return removeDiamond;
	}

	public void setRemoveDiamond(int[] removeDiamond) {
		this.removeDiamond = removeDiamond;
	}

	public int[] getMarryDiamond() {
		return marryDiamond;
	}

	public void setMarryDiamond(int[] marryDiamond) {
		this.marryDiamond = marryDiamond;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	

	public int getRightToMarry() {
		return rightToMarry;
	}

	public void setRightToMarry(int rightToMarry) {
		this.rightToMarry = rightToMarry;
	}

	public int getCanGiveDiamond() {
		return canGiveDiamond;
	}

	public void setCanGiveDiamond(int canGiveDiamond) {
		this.canGiveDiamond = canGiveDiamond;
	}

	public int getWedTime() {
		return wedTime;
	}

	public void setWedTime(int wedTime) {
		this.wedTime = wedTime;
	}

}
