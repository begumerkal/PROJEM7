package com.app.empire.protocol.data.admin;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 查询数据
 * 
 * @see AbstractData
 * @author mazheng
 */
public class Update extends AbstractData {
    private int        updateType; // 0更新玩家属性,1更新物品属性,2更新公告,3更新推送,5修改充值奖励,6修改服务器配置属性
    private int        id;         // 更新对象的id
    private String[]   keys;       // 更新的字段
    private String[]   values;     // 更新的值
    private int[]      types;      // 1String,2int,3byte,4date,5Boolean,6short

    public Update(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_Update, sessionId, serial);
    }

    public Update() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_Update);
    }

    public int getUpdateType() {
        return updateType;
    }

    public void setUpdateType(int updateType) {
        this.updateType = updateType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public int[] getTypes() {
        return types;
    }

    public void setTypes(int[] types) {
        this.types = types;
    }
}
