package com.wyd.empire.protocol.data.pet;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 确定开槽
 * @author zengxc
 *
 */
public class OpenBar extends AbstractData {
	
	public OpenBar(int sessionId, int serial) {
        super(Protocol.MAIN_PET, Protocol.PET_OpenBar, sessionId, serial);
    }
	public OpenBar() {
        super(Protocol.MAIN_PET, Protocol.PET_OpenBar);
    }
	
}
