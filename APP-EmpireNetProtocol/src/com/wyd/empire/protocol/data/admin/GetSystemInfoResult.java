package com.wyd.empire.protocol.data.admin;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 查询系统信息
 * 
 * @see AbstractData
 * @author mazheng
 */
public class GetSystemInfoResult extends AbstractData {
    private String content;
    
    public GetSystemInfoResult(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GetSystemInfoResult, sessionId, serial);
    }

    public GetSystemInfoResult() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GetSystemInfoResult);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
