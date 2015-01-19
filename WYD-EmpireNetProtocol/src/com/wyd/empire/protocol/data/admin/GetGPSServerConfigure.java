package com.wyd.empire.protocol.data.admin;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 获取GPS服务配置
 * 
 * @see AbstractData
 * @author chenjie
 */
public class GetGPSServerConfigure extends AbstractData {

    public GetGPSServerConfigure(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GetGPSServerConfigure, sessionId, serial);
    }

    public GetGPSServerConfigure() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GetGPSServerConfigure);
    }

}
