package com.wyd.empire.protocol.data.system;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 获取付费包奖励列表
 * @author zengxc
 *
 */
public class GetPayAppRewardList extends AbstractData {
    private String code;

    public GetPayAppRewardList(int sessionId, int serial) {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_GetPayAppRewardList, sessionId, serial);
    }

    public GetPayAppRewardList() {
        super(Protocol.MAIN_SYSTEM, Protocol.SYSTEM_GetPayAppRewardList);
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
