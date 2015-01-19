package com.wyd.empire.nearby.handler.nearby;
import org.apache.log4j.Logger;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.nearby.GetNearbyFriendList;
import com.wyd.empire.protocol.data.nearby.GetNearbyPlayerList;
import com.wyd.empire.protocol.data.nearby.RemoveNearbyFriend;
import com.wyd.empire.protocol.data.nearby.RemoveNearbyFriendOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
/**
 * 添加附近好友
 * 
 * @author zguoqiu
 */
public class RemoveNearbyFriendHandler implements IDataHandler {
    private Logger log;

    public RemoveNearbyFriendHandler() {
        this.log = Logger.getLogger(RemoveNearbyFriendHandler.class);
    }

    public void handle(AbstractData data) throws Exception {
        AcceptSession session = (AcceptSession) data.getSource();
        RemoveNearbyFriend removeNearbyFriend = (RemoveNearbyFriend) data;
        try {
            int playerId = ServiceManager.getManager().getPlayerInfoManager().removeFriend(removeNearbyFriend.getMyInfoId(), removeNearbyFriend.getFriendInfoId());
            if (playerId > 0) {
                RemoveNearbyFriendOk removeNearbyFriendOk = new RemoveNearbyFriendOk();
                removeNearbyFriendOk.setPlayerId(playerId);
                session.send(removeNearbyFriendOk);
                if (removeNearbyFriend.getRefreshMark() == 0) {
                    GetNearbyFriendList getNearbyFriendList = new GetNearbyFriendList();
                    getNearbyFriendList.setSource(session);
                    getNearbyFriendList.setPlayerInfoId(removeNearbyFriend.getMyInfoId());
                    GetNearbyFriendListHandler handler = new GetNearbyFriendListHandler();
                    handler.handle(getNearbyFriendList);
                } else {
                    GetNearbyPlayerList getNearbyPlayerList = new GetNearbyPlayerList();
                    getNearbyPlayerList.setSource(session);
                    getNearbyPlayerList.setPlayerInfoId(removeNearbyFriend.getMyInfoId());
                    GetNearbyPlayerListHandler handler = new GetNearbyPlayerListHandler();
                    handler.handle(getNearbyPlayerList);
                }
            }
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}