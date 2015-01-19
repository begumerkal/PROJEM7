package com.wyd.empire.protocol.data.singlemap;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 获取大关卡列表
 * @author zengxc
 *
 */
public class GetBigPointList extends AbstractData {
	public GetBigPointList(int sessionId, int serial) {
        super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_GetBigPointList, sessionId, serial);
    }
	public GetBigPointList(){
		 super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_GetBigPointList);
	}
	
}
