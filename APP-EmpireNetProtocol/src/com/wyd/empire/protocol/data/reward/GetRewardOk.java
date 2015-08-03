package com.wyd.empire.protocol.data.reward;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class GetRewardOk extends AbstractData {
    private int itemId;
    private String msg;
    private int status;

    public GetRewardOk(int sessionId, int serial) {
        super(Protocol.MAIN_REWARD, Protocol.REWARD_GetRewardOk, sessionId, serial);
    }

    public GetRewardOk() {
        super(Protocol.MAIN_REWARD, Protocol.REWARD_GetRewardOk);
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
    
	
}
