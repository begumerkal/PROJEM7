package com.app.empire.protocol.data.mail;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>GetInboxMail</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_MAIL下子命令MAIL_GetInboxMail(获得收件箱列表协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetInboxMailNew extends AbstractData {
	private int 		pageNumber = 1; //要获得的页数
	

    public GetInboxMailNew(int sessionId, int serial) {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_GetInboxMailNew, sessionId, serial);
    }

    public GetInboxMailNew() {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_GetInboxMailNew);
    }

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
    
}
