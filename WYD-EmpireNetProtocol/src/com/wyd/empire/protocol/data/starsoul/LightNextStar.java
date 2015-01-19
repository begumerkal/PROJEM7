package com.wyd.empire.protocol.data.starsoul;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 点亮下一个星点
 * 
 * @see AbstractData
 * @author 陈杰
 */
public class LightNextStar extends AbstractData {
	
    public LightNextStar(int sessionId, int serial) {
        super(Protocol.MAIN_STARSOUL, Protocol.STARSOUL_LightNextStar, sessionId, serial);
    }

    public LightNextStar() {
        super(Protocol.MAIN_STARSOUL, Protocol.STARSOUL_LightNextStar);
    }
}
