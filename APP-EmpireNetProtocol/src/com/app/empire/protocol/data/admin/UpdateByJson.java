package com.app.empire.protocol.data.admin;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 查询数据
 * 
 * @see AbstractData
 * @author mazheng
 */
public class UpdateByJson extends AbstractData {
    private int    updateType; // 0更新商品价格 1更换房间座位号
    private String content;   // json格式数据

    public UpdateByJson(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_UpdateByJson, sessionId, serial);
    }

    public UpdateByJson() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_UpdateByJson);
    }

    public int getUpdateType() {
        return updateType;
    }

    public void setUpdateType(int updateType) {
        this.updateType = updateType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
