package com.app.empire.protocol.data.admin;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 删除数据
 * 
 * @see AbstractData
 * @author mazheng
 */
public class Delete extends AbstractData {
    private int    deleteType; // 0删除公告,1删除推送
    private int    id;

    public Delete(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_Delete, sessionId, serial);
    }

    public Delete() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_Delete);
    }

    public int getDeleteType() {
        return deleteType;
    }

    public void setDeleteType(int deleteType) {
        this.deleteType = deleteType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
