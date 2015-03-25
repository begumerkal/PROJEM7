package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 更新服务器版本信息
 * @see AbstractData
 * @author doter
 */
public class UpdateServerInfo extends AbstractData {
    private String area;
    private String machine;
    private int line;
	private String version;
	private String updateurl;
	private String remark;
	private String appraisal;
    private String group;
    private int serverId;
    public UpdateServerInfo(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_UpdateServerInfo, sessionId, serial);
    }

    public UpdateServerInfo() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_UpdateServerInfo);
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdateurl() {
        return updateurl;
    }

    public void setUpdateurl(String updateurl) {
        this.updateurl = updateurl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(String appraisal) {
        this.appraisal = appraisal;
    }

    public String getGroup() {
        if (null == group) {
            return "";
        } else {
            return group;
        }
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

}
