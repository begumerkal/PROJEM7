package com.wyd.empire.protocol.data.spree;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetGiftOk extends AbstractData {

    private String[] itemName;          // 开启的物品名称
    private String[] itemIcon;          // 开启的物品图标
    private int[] itemNum;              // 开启的物品数

    public GetGiftOk(int sessionId, int serial) {
        super(Protocol.MAIN_SPREE, Protocol.SPREE_GetGiftOk, sessionId, serial);
    }

    public GetGiftOk() {
        super(Protocol.MAIN_SPREE, Protocol.SPREE_GetGiftOk);
    }

    public String[] getItemName() {
        return itemName;
    }

    public void setItemName(String[] itemName) {
        this.itemName = itemName;
    }

    public String[] getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(String[] itemIcon) {
        this.itemIcon = itemIcon;
    }

    public int[] getItemNum() {
        return itemNum;
    }

    public void setItemNum(int[] itemNum) {
        this.itemNum = itemNum;
    }
}
