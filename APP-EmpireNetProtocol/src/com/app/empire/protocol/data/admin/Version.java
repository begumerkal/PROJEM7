package com.app.empire.protocol.data.admin;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 版本管理
 * 
 * @see AbstractData
 * @author mazheng
 */
public class Version extends AbstractData {
    private String num;
    private String updateurl;
    private String remark;
    private String appraisal;
    private String mark;     // 服务器描述
    private String sort;     // 服务器先后排序
    private String maxPlayer; // 服务器人数点显示

    public Version(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_Version, sessionId, serial);
    }

    public Version() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_Version);
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(String maxPlayer) {
        this.maxPlayer = maxPlayer;
    }
}
