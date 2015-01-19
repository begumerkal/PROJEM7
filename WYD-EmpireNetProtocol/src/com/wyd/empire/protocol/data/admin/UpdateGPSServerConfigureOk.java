package com.wyd.empire.protocol.data.admin;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 更新GPS服务结果
 * 
 * @see AbstractData
 * @author chenjie
 */
public class UpdateGPSServerConfigureOk extends AbstractData {
	/** 执行结果 */
	private boolean result;

    public UpdateGPSServerConfigureOk(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_UpdateGPSServerConfigureOk, sessionId, serial);
    }

    public UpdateGPSServerConfigureOk() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_UpdateGPSServerConfigureOk);
    }

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

   
}
