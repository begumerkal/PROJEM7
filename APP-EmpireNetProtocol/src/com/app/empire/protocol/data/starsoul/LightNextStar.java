package com.app.empire.protocol.data.starsoul;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
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
