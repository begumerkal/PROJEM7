package com.wyd.empire.protocol.data.admin;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 查询用户--查询单条数据结果
 * 
 * @see AbstractData
 * @author mazheng
 */
public class GetDataByIdResult extends AbstractData {
    private String content; // 数据内容（json格式）

    public GetDataByIdResult(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GetDataByIdResult, sessionId, serial);
    }

    public GetDataByIdResult() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_GetDataByIdResult);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
