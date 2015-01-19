package com.wyd.empire.nearby.handler.nearby;
import org.apache.log4j.Logger;
import com.wyd.empire.nearby.bean.PlayerInfo;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.nearby.UpdatePlayerInfo;
import com.wyd.empire.protocol.data.nearby.UpdatePlayerInfoOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
/**
 * 更新玩家信息
 * 
 * @author zguoqiu
 */
public class UpdatePlayerInfoHandler implements IDataHandler {
    private Logger log;

    public UpdatePlayerInfoHandler() {
        this.log = Logger.getLogger(UpdatePlayerInfoHandler.class);
    }

    public void handle(AbstractData data) throws Exception {
        AcceptSession session = (AcceptSession) data.getSource();
        UpdatePlayerInfo updatePlayerInfo = (UpdatePlayerInfo) data;
        try {
            int playerId = 0;
            int myInfoId = 0;
            PlayerInfo playerInfo = null;
            if (0 != updatePlayerInfo.getMyInfoId()) {
                playerInfo = (PlayerInfo) ServiceManager.getManager().getPlayerInfoManager().getBean(PlayerInfo.class, updatePlayerInfo.getMyInfoId());
                if (null != playerInfo && playerInfo.getPlayerId() == updatePlayerInfo.getPlayerId()) {
                    playerId = playerInfo.getPlayerId();
                    myInfoId = playerInfo.getId();
                    ServiceManager.getManager().getPlayerInfoManager().updatePlayerInfo(myInfoId, updatePlayerInfo.getPlayerName(), updatePlayerInfo.getAvatarURL(), updatePlayerInfo.getSuitHead(), updatePlayerInfo.getSuitFace(), updatePlayerInfo.getFighting(), updatePlayerInfo.getLongitude(), updatePlayerInfo.getLatitude());
//                    System.out.println("updatePlayerInfo:" + playerId);
                }
            }
            if (0 == myInfoId || null == playerInfo) {
                playerInfo = ServiceManager.getManager().getPlayerInfoManager().createPlayerInfo(updatePlayerInfo.getServiceId(), updatePlayerInfo.getPlayerId(), updatePlayerInfo.getPlayerName(), updatePlayerInfo.getAvatarURL(), updatePlayerInfo.getSuitHead(), updatePlayerInfo.getSuitFace(), updatePlayerInfo.getSex(), updatePlayerInfo.getFighting(), updatePlayerInfo.getLongitude(), updatePlayerInfo.getLatitude());
                playerId = playerInfo.getPlayerId();
                myInfoId = playerInfo.getId();
//                System.out.println("createPlayerInfo:" + playerId);
            }
            if(myInfoId>0){
                UpdatePlayerInfoOk updatePlayerInfoOk = new UpdatePlayerInfoOk();
                updatePlayerInfoOk.setPlayerId(playerId);
                updatePlayerInfoOk.setMyInfoId(myInfoId);
                session.send(updatePlayerInfoOk);
            }
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}