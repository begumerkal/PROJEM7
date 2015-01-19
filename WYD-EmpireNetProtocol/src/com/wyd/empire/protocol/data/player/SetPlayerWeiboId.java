package com.wyd.empire.protocol.data.player;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class SetPlayerWeiboId extends AbstractData {
    private int    weiboType;
    private String weiboID;
    private String weiboIcon;

    public SetPlayerWeiboId(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_SetPlayerWeiboId, sessionId, serial);
    }

    public SetPlayerWeiboId() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_SetPlayerWeiboId);
    }

    public int getWeiboType() {
        return weiboType;
    }

    public void setWeiboType(int weiboType) {
        this.weiboType = weiboType;
    }

    public String getWeiboID() {
        return weiboID;
    }

    public void setWeiboID(String weiboID) {
        this.weiboID = weiboID;
    }

    public String getWeiboIcon() {
        return weiboIcon;
    }

    public void setWeiboIcon(String weiboIcon) {
        this.weiboIcon = weiboIcon;
    }
}
