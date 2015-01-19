package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GiveDiamond extends AbstractData {
	private int	coupleId;   //伴侣Id
	private int	diamondCountGive;   //所需要的钻石数


    public GiveDiamond(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GiveDiamond, sessionId, serial);
    }

    public GiveDiamond() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GiveDiamond);
    }

	public int getCoupleId() {
		return coupleId;
	}

	public void setCoupleId(int coupleId) {
		this.coupleId = coupleId;
	}

	public int getDiamondCountGive() {
		return diamondCountGive;
	}

	public void setDiamondCountGive(int diamondCountGive) {
		this.diamondCountGive = diamondCountGive;
	}

}
