package com.wyd.empire.protocol.data.title;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class GetTitleListOk extends AbstractData {
    private int[]    ptId;     // 玩家任务id
    private String[] title;
    private String[] titleDesc;
    private int[]    status;
    private int[]    target;
    private int[]    targetNum;
    private int[]    titleType;     // 称号类型0系统1自定义
    private int[]    daysLeft;      // 剩余天数

    

	public int[] getTitleType() {
		return titleType;
	}

	public void setTitleType(int[] titleType) {
		this.titleType = titleType;
	}

	public int[] getDaysLeft() {
		return daysLeft;
	}

	public void setDaysLeft(int[] daysLeft) {
		this.daysLeft = daysLeft;
	}

	public GetTitleListOk(int sessionId, int serial) {
        super(Protocol.MAIN_TITLE, Protocol.TITLE_GetTitleListOk, sessionId, serial);
    }

    public GetTitleListOk() {
        super(Protocol.MAIN_TITLE, Protocol.TITLE_GetTitleListOk);
    }

    public int[] getPtId() {
        return ptId;
    }

    public void setPtId(int[] ptId) {
        this.ptId = ptId;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String[] getTitleDesc() {
        return titleDesc;
    }

    public void setTitleDesc(String[] titleDesc) {
        this.titleDesc = titleDesc;
    }

    public int[] getStatus() {
        return status;
    }

    public void setStatus(int[] status) {
        this.status = status;
    }

    public int[] getTarget() {
        return target;
    }

    public void setTarget(int[] target) {
        this.target = target;
    }

    public int[] getTargetNum() {
        return targetNum;
    }

    public void setTargetNum(int[] targetNum) {
        this.targetNum = targetNum;
    }
}
