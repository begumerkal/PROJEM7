package com.app.empire.protocol.data.exchange;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ResponseExchange extends AbstractData {
	
	private	int	code; //0表示成功，1表示失败
	private	String	message; //提示语


    public ResponseExchange(int sessionId, int serial) {
        super(Protocol.MAIN_EXCHANGE, Protocol.EXCHANGE_ResponseExchange, sessionId, serial);
    }

    public ResponseExchange() {
        super(Protocol.MAIN_EXCHANGE, Protocol.EXCHANGE_ResponseExchange);
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
