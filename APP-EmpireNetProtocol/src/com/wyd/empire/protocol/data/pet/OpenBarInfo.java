package com.wyd.empire.protocol.data.pet;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 请求开启宠物槽返回
 * @author zengxc
 *
 */
public class OpenBarInfo extends AbstractData {
	private int openNeedDiamond;//开槽需要的钻石
	
	public OpenBarInfo(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_OpenBarInfo, sessionId, serial);
    }
	public OpenBarInfo() {
        super(Protocol.MAIN_PET, Protocol.PET_OpenBarInfo);
    }
	public int getOpenNeedDiamond() {
		return openNeedDiamond;
	}
	public void setOpenNeedDiamond(int openNeedDiamond) {
		this.openNeedDiamond = openNeedDiamond;
	}
	
	
}
