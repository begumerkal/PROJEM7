package com.wyd.empire.protocol.data.spree;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class GetGift extends AbstractData {

    private int itemId;     // 礼包物品Id

    public GetGift(int sessionId, int serial) {
        super(Protocol.MAIN_SPREE, Protocol.SPREE_GetGift, sessionId, serial);
    }

    public GetGift() {
        super(Protocol.MAIN_SPREE, Protocol.SPREE_GetGift);
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
