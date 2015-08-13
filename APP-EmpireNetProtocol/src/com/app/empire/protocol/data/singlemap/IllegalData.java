package com.app.empire.protocol.data.singlemap;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 战斗数据异常
 * @author zengxc
 *
 */
public class IllegalData extends AbstractData {
	private String message;//
	public IllegalData(int sessionId, int serial) {
        super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_IllegalData, sessionId, serial);
    }
	public IllegalData(){
		 super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_IllegalData);
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
