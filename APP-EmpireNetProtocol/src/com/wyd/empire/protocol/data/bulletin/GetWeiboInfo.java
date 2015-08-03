package com.wyd.empire.protocol.data.bulletin;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 获取微博文字和图片链接
 * 
 * @see AbstractData
 * @author mazheng
 */
public class GetWeiboInfo extends AbstractData {
    public GetWeiboInfo(int sessionId, int serial) {
        super(Protocol.MAIN_BULLETIN, Protocol.BULLETIN_GetWeiboInfo, sessionId, serial);
    }

    public GetWeiboInfo() {
        super(Protocol.MAIN_BULLETIN, Protocol.BULLETIN_GetWeiboInfo);
    }
}
