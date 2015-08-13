package com.app.empire.protocol.data.admin;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 新增
 * @author qiang
 *
 */
public class AddByJson extends AbstractData {
    private int    addType; // 0新增物品价格
    private String content; // json数据

    public AddByJson(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_AddByJson, sessionId, serial);
    }

    public AddByJson() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_AddByJson);
    }

    public int getAddType() {
        return addType;
    }

    public void setAddType(int addType) {
        this.addType = addType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
