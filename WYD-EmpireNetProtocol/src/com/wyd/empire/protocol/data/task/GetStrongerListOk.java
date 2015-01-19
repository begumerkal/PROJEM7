package com.wyd.empire.protocol.data.task;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class GetStrongerListOk extends AbstractData {
    private String[] taskName;
    private String[] taskTime;
    private String[] taskRemark;
    private String[] buttomText;
    private int[]    jumpLevel;
    private String[]    jumpId;
    private String[] taskIcon;

    public GetStrongerListOk(int sessionId, int serial) {
        super(Protocol.MAIN_TASK, Protocol.TASK_GetStrongerListOk, sessionId, serial);
    }

    public GetStrongerListOk() {
        super(Protocol.MAIN_TASK, Protocol.TASK_GetStrongerListOk);
    }

    public String[] getTaskName() {
        return taskName;
    }

    public void setTaskName(String[] taskName) {
        this.taskName = taskName;
    }

    public String[] getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String[] taskTime) {
        this.taskTime = taskTime;
    }

    public String[] getTaskRemark() {
        return taskRemark;
    }

    public void setTaskRemark(String[] taskRemark) {
        this.taskRemark = taskRemark;
    }

    public String[] getButtomText() {
        return buttomText;
    }

    public void setButtomText(String[] buttomText) {
        this.buttomText = buttomText;
    }

    public int[] getJumpLevel() {
        return jumpLevel;
    }

    public void setJumpLevel(int[] jumpLevel) {
        this.jumpLevel = jumpLevel;
    }

    public String[] getJumpId() {
        return jumpId;
    }

    public void setJumpId(String[] jumpId) {
        this.jumpId = jumpId;
    }

    public String[] getTaskIcon() {
        return taskIcon;
    }

    public void setTaskIcon(String[] taskIcon) {
        this.taskIcon = taskIcon;
    }
}
