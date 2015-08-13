package com.app.empire.protocol.data.singlemap;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetBigPointListOk extends AbstractData {
	private int[] id;//大关卡ID
	private String[] name;//关卡名称
	private int[] status;//状态0未开启1已开启2已通关
	private int[] passPoint;//通关个数
	private int[] totalPoint;//总关卡数

	public GetBigPointListOk(int sessionId, int serial) {
        super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_GetBigPointListOk, sessionId, serial);
    }
	public GetBigPointListOk(){
		 super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_GetBigPointListOk);
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
	public int[] getStatus() {
		return status;
	}
	public void setStatus(int[] status) {
		this.status = status;
	}
	public int[] getPassPoint() {
		return passPoint;
	}
	public void setPassPoint(int[] passPoint) {
		this.passPoint = passPoint;
	}
	public int[] getTotalPoint() {
		return totalPoint;
	}
	public void setTotalPoint(int[] totalPoint) {
		this.totalPoint = totalPoint;
	}
	
}
