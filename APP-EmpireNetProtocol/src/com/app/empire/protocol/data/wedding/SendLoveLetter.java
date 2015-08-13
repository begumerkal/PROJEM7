package com.app.empire.protocol.data.wedding;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendLoveLetter extends AbstractData {
	
	private int	coupleId;	//收件人Id
	private String	coupleName;	//收件人名称
	private int	useItemId;	//所用的道具类型id(如果是结婚：0代表奢华，1:豪华，2:浪漫，3：普通)
	private int		timeId; //婚礼开始时间（订婚：-1，结婚是相应的ID）

    public SendLoveLetter(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SendLoveLetter, sessionId, serial);
    }

    public SendLoveLetter() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SendLoveLetter);
    }

	public int getCoupleId() {
		return coupleId;
	}

	public void setCoupleId(int coupleId) {
		this.coupleId = coupleId;
	}

	public String getCoupleName() {
		return coupleName;
	}

	public void setCoupleName(String coupleName) {
		this.coupleName = coupleName;
	}

	public int getUseItemId() {
		return useItemId;
	}

	public void setUseItemId(int useItemId) {
		this.useItemId = useItemId;
	}

	public int getTimeId() {
		return timeId;
	}

	public void setTimeId(int timeId) {
		this.timeId = timeId;
	}


}
