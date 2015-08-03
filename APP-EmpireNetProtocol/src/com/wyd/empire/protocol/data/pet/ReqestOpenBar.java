package com.wyd.empire.protocol.data.pet;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 请求开启宠物槽
 * @author zengxc
 *
 */
public class ReqestOpenBar extends AbstractData {
	public ReqestOpenBar(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_RequestOpenBar, sessionId, serial);
    }
	public ReqestOpenBar() {
        super(Protocol.MAIN_PET, Protocol.PET_RequestOpenBar);
    }
	
	
}
