package com.app.empire.protocol.data.monthcard;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 获取月卡列表
 * 
 * @see AbstractData
 * @author 陈杰
 */
public class GetMonthCardList extends AbstractData {
	
    public GetMonthCardList(int sessionId, int serial) {
        super(Protocol.MAIN_MONTHCARD, Protocol.MONTHCARD_GetMonthCardList, sessionId, serial);
    }

    public GetMonthCardList() {
        super(Protocol.MAIN_MONTHCARD, Protocol.MONTHCARD_GetMonthCardList);
    }

    
}
