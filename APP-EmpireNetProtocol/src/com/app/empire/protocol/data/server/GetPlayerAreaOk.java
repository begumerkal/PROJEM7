package com.app.empire.protocol.data.server;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class GetPlayerAreaOk extends AbstractData {
    private String udid;
    private boolean isNew;
    private String areaId;
    private String machinecode;
    
    public GetPlayerAreaOk(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_GetPlayerAreaOk, sessionId, serial);
    }

    public GetPlayerAreaOk() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_GetPlayerAreaOk);
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getMachinecode() {
        return machinecode;
    }

    public void setMachinecode(String machinecode) {
        this.machinecode = machinecode;
    }
}
