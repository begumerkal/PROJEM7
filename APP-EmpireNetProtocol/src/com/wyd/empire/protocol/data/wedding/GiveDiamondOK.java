package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GiveDiamondOK extends AbstractData {

	private int	diamondCountGive;	//所赠送的钻石数
	private String	coupleName;	//伴侣名称
	private int	giveId;	//赠送人Id


    public GiveDiamondOK(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GiveDiamondOK, sessionId, serial);
    }

    public GiveDiamondOK() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_GiveDiamondOK);
    }

	public int getDiamondCountGive() {
		return diamondCountGive;
	}

	public void setDiamondCountGive(int diamondCountGive) {
		this.diamondCountGive = diamondCountGive;
	}

	public String getCoupleName() {
		return coupleName;
	}

	public void setCoupleName(String coupleName) {
		this.coupleName = coupleName;
	}

	public int getGiveId() {
		return giveId;
	}

	public void setGiveId(int giveId) {
		this.giveId = giveId;
	}

}
