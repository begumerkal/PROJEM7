package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class LegacyLogin extends AbstractData {
    private String udid;
    private String name;
    private String password;
    private int    channel;        // 渠道ID
    private int    isChannelLogon; // 第三方用户体系(0:自己的用户体系,1:第三方用户体系)
    private String oldUdid;        // 旧规则udid

    public LegacyLogin(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyLogin, sessionId, serial);
    }

    public LegacyLogin() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyLogin);
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getIsChannelLogon() {
        return isChannelLogon;
    }

    public void setIsChannelLogon(int isChannelLogon) {
        this.isChannelLogon = isChannelLogon;
    }

    public String getOldUdid() {
        return oldUdid;
    }

    public void setOldUdid(String oldUdid) {
        this.oldUdid = oldUdid;
    }
}
