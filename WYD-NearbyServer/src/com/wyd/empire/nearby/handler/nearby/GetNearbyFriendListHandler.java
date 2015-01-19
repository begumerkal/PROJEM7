package com.wyd.empire.nearby.handler.nearby;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.nearby.vo.PlayerVo;
import com.wyd.empire.nearby.vo.PlayerVo.PlayerInfoVo;
import com.wyd.empire.protocol.data.nearby.GetNearbyFriendList;
import com.wyd.empire.protocol.data.nearby.GetNearbyFriendListOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
/**
 * 获取附近好友列表
 * @author zguoqiu
 */
public class GetNearbyFriendListHandler implements IDataHandler {
    private Logger log;

    public GetNearbyFriendListHandler() {
        this.log = Logger.getLogger(GetNearbyFriendListHandler.class);
    }

    public void handle(AbstractData data) throws Exception {
        AcceptSession session = (AcceptSession) data.getSource();
        GetNearbyFriendList getNearbyFriendList = (GetNearbyFriendList) data;
        try {
            PlayerVo playerVo = ServiceManager.getManager().getPlayerInfoManager().getPlayerVo(getNearbyFriendList.getPlayerInfoId());
            if (null != playerVo) {
                GetNearbyFriendListOk getNearbyFriendListOk = new GetNearbyFriendListOk();
                getNearbyFriendListOk.setPlayerId(playerVo.getMyInfo().getPlayerId());
                List<PlayerInfoVo> friendList = playerVo.getFriendList();
                int friendSize = friendList.size();
                int[] playerInfoId = new int[friendSize];
                String[] avatarURL = new String[friendSize];
                String[] suitHead = new String[friendSize];
                String[] suitFace = new String[friendSize];
                byte[] sex = new byte[friendSize];
                String[] playerName = new String[friendSize];
                int[] fighting = new int[friendSize];
                int[] distance = new int[friendSize];
                int[] mailCount = new int[friendSize];
                boolean[] isOnline = new boolean[friendSize];
                PlayerInfoVo piv;
                for (int i = 0; i < friendSize; i++) {
                    piv = friendList.get(i);
                    playerInfoId[i] = piv.getPlayerInfo().getId();
                    avatarURL[i] = piv.getPlayerInfo().getAvatarURL();
                    suitHead[i] = piv.getPlayerInfo().getSuitHead();
                    suitFace[i] = piv.getPlayerInfo().getSuitFace();
                    sex[i] = piv.getPlayerInfo().getSex();
                    playerName[i] = piv.getPlayerInfo().getPlayerName();
                    fighting[i] = piv.getPlayerInfo().getFighting();
                    distance[i] = piv.getDistance();
                    mailCount[i] = piv.getMailCount();
                    isOnline[i] = piv.getPlayerInfo().isOnline();
                }
                getNearbyFriendListOk.setPlayerInfoId(playerInfoId);
                getNearbyFriendListOk.setAvatarURL(avatarURL);
                getNearbyFriendListOk.setSuitHead(suitHead);
                getNearbyFriendListOk.setSuitFace(suitFace);
                getNearbyFriendListOk.setSex(sex);
                getNearbyFriendListOk.setPlayerName(playerName);
                getNearbyFriendListOk.setFighting(fighting);
                getNearbyFriendListOk.setDistance(distance);
                getNearbyFriendListOk.setMailCount(mailCount);
                getNearbyFriendListOk.setIsOnline(isOnline);
                getNearbyFriendListOk.setFriendCount(friendSize);
                getNearbyFriendListOk.setMaxCount(Integer.parseInt(ServiceManager.getManager().getConfiguration().getValue("maxfriendcount")));
                session.send(getNearbyFriendListOk);
            }
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}