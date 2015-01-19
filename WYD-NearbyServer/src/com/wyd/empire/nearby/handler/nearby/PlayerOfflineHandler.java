package com.wyd.empire.nearby.handler.nearby;
import org.apache.log4j.Logger;
import com.wyd.empire.nearby.bean.PlayerInfo;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.nearby.PlayerOffline;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 玩家下线
 * 
 * @author zguoqiu
 */
public class PlayerOfflineHandler implements IDataHandler {
    private Logger log;

    public PlayerOfflineHandler() {
        this.log = Logger.getLogger(PlayerOfflineHandler.class);
    }

    public void handle(AbstractData data) throws Exception {
        PlayerOffline playerOffline = (PlayerOffline) data;
        try {
            PlayerInfo playerInfo = (PlayerInfo) ServiceManager.getManager().getPlayerInfoManager().getBean(PlayerInfo.class, playerOffline.getMyInfoId());
            if (null != playerInfo) {
                playerInfo.setOnline(false);
            }
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}