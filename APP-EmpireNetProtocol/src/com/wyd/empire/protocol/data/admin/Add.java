package com.wyd.empire.protocol.data.admin;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 查询数据
 * 
 * @see AbstractData
 * @author mazheng
 */
public class Add extends AbstractData {
    private int      addType; // 0新增物品, 1增加公告, 2发送即时公告, 3增加推送
    private String[] keys;    // 更新的字段
    private String[] values;  // 更新的值
    private int[]    types;   // 1String,2int,3byte,4date,5Boolean,6short

    public Add(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_Add, sessionId, serial);
    }

    public Add() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_Add);
    }

    public int getAddType() {
        return addType;
    }

    public void setAddType(int addType) {
        this.addType = addType;
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
