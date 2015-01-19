package com.wyd.empire.nearby.handler.nearby;
import org.apache.log4j.Logger;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.nearby.AddNearbyFriend;
import com.wyd.empire.protocol.data.nearby.AddNearbyFriendOk;
import com.wyd.empire.protocol.data.nearby.GetNearbyFriendList;
import com.wyd.empire.protocol.data.nearby.GetNearbyPlayerList;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
/**
 * 添加附近好友
 * @author zguoqiu
 */
public class AddNearbyFriendHandler implements IDataHandler {
    private Logger log;

    public AddNearbyFriendHandler() {
        this.log = Logger.getLogger(AddNearbyFriendHandler.class);
    }

    public void handle(AbstractData data) throws Exception {
        AcceptSession session = (AcceptSession) data.getSource();
        AddNearbyFriend addNearbyFriend = (AddNearbyFriend) data;
        try {
            int playerId = ServiceManager.getManager().getPlayerInfoManager().addFriend(addNearbyFriend.getMyInfoId(), addNearbyFriend.getFriendInfoId());
            if (playerId > 0) {
                AddNearbyFriendOk addNearbyFriendOk = new AddNearbyFriendOk();
                addNearbyFriendOk.setPlayerId(playerId);
                session.send(addNearbyFriendOk);
                if (addNearbyFriend.getRefreshMark() == 0) {
                    GetNearbyFriendList getNearbyFriendList = new GetNearbyFriendList();
                    getNearbyFriendList.setSource(session);
                    getNearbyFriendList.setPlayerInfoId(addNearbyFriend.getMyInfoId());
                    GetNearbyFriendListHandler handler = new GetNearbyFriendListHandler();
                    handler.handle(getNearbyFriendList);
                } else {
                    GetNearbyPlayerList getNearbyPlayerList = new GetNearbyPlayerList();
                    getNearbyPlayerList.setSource(session);
                    getNearbyPlayerList.setPlayerInfoId(addNearbyFriend.getMyInfoId());
                    GetNearbyPlayerListHandler handler = new GetNearbyPlayerListHandler();
                    handler.handle(getNearbyPlayerList);
                }
            }
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}