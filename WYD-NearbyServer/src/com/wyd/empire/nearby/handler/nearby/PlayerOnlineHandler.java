package com.wyd.empire.nearby.handler.nearby;
import org.apache.log4j.Logger;
import com.wyd.empire.nearby.bean.PlayerInfo;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.nearby.PlayerOnline;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 玩家上线
 * 
 * @author zguoqiu
 */
public class PlayerOnlineHandler implements IDataHandler {
    private Logger log;

    public PlayerOnlineHandler() {
        this.log = Logger.getLogger(PlayerOnlineHandler.class);
    }

    public void handle(AbstractData data) throws Exception {
        PlayerOnline playerOnline = (PlayerOnline) data;
        try {
            PlayerInfo playerInfo = (PlayerInfo) ServiceManager.getManager().getPlayerInfoManager().getBean(PlayerInfo.class, playerOnline.getMyInfoId());
            if (null != playerInfo) {
                playerInfo.setOnline(true);
            }
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}