package com.app.empire.protocol.data.worldbosshall;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 获取开启状态
 * @author zengxc
 *
 */
public class GetOpenState extends AbstractData {
	public GetOpenState(int sessionId, int serial) {
        super(Protocol.MAIN_WORLDBOSSHALL, Protocol.WORLDBOSSHALL_GetOpenState, sessionId, serial);
    }
	public GetOpenState(){
		super(Protocol.MAIN_WORLDBOSSHALL, Protocol.WORLDBOSSHALL_GetOpenState);
	}

}
