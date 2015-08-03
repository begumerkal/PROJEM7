package com.wyd.empire.protocol.data.admin;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 查询系统信息
 * 
 * @see AbstractData
 * @author mazheng
 */
public class GetSystemInfo extends AbstractData {
    public GetSystemInfo(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GetSystemInfo, sessionId, serial);
    }

    public GetSystemInfo() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GetSystemInfo);
    }
}
