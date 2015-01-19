package com.wyd.empire.protocol.data.account;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class ChannelLogin extends AbstractData {
    private int      channel;
    private String[] parameter;

    public ChannelLogin(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_ChannelLogin, sessionId, serial);
    }

    public ChannelLogin() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_ChannelLogin);
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String[] getParameter() {
        return parameter;
    }

    public void setParameter(String[] parameter) {
        this.parameter = parameter;
    }
}
