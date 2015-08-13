package com.app.empire.protocol.data.practice;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 属性修炼消耗勋章添加对应属性的经验值
 * 
 * @see AbstractData
 * @author 陈杰
 */
public class LightNextPractice extends AbstractData {
	
	private String   bonusAttribute;		//修炼的属性key
	private int		 itemId;				//消耗勋章id
	private int		 useNumber;				//使用个数

	
    public LightNextPractice(int sessionId, int serial) {
        super(Protocol.MAIN_PRACTICE, Protocol.PRACTICE_LightNextPractice, sessionId, serial);
    }

    public LightNextPractice() {
        super(Protocol.MAIN_PRACTICE, Protocol.PRACTICE_LightNextPractice);
    }

	public String getBonusAttribute() {
		return bonusAttribute;
	}

	public void setBonusAttribute(String bonusAttribute) {
		this.bonusAttribute = bonusAttribute;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getUseNumber() {
		return useNumber;
	}

	public void setUseNumber(int useNumber) {
		this.useNumber = useNumber;
	}
    
    
}
