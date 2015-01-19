package com.wyd.empire.world.title;
import java.util.Date;
/**
 * 玩家与称号中间表信息
 * @author sunzx
 *
 */
public class PlayerTitleVo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private int               titleId;
    private Date              endTime;
    private byte              status;
    private String            targetStatus;
    private boolean           isNew;

    public PlayerTitleVo(){
        
    }
    
    public PlayerTitleVo(int titleId) {
        this.titleId = titleId;
        this.status = 1;
        this.isNew = true;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(String targetStatus) {
        this.targetStatus = targetStatus;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }
}
