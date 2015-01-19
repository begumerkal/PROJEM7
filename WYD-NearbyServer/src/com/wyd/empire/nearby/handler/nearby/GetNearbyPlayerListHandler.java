package com.wyd.empire.nearby.handler.nearby;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.nearby.vo.PlayerVo;
import com.wyd.empire.nearby.vo.PlayerVo.PlayerInfoVo;
import com.wyd.empire.protocol.data.nearby.GetNearbyPlayerList;
import com.wyd.empire.protocol.data.nearby.GetNearbyPlayerListOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
/**
 * 获取附近玩家列表
 * @author zguoqiu
 */
public class GetNearbyPlayerListHandler implements IDataHandler {
    private Logger log;

    public GetNearbyPlayerListHandler() {
        this.log = Logger.getLogger(GetNearbyPlayerListHandler.class);
    }

    public void handle(AbstractData data) throws Exception {
        AcceptSession session = (AcceptSession) data.getSource();
        GetNearbyPlayerList getNearbyPlayerList = (GetNearbyPlayerList) data;
        try {
            PlayerVo playerVo = ServiceManager.getManager().getPlayerInfoManager().getPlayerVo(getNearbyPlayerList.getPlayerInfoId());
            if (null != playerVo) {
                GetNearbyPlayerListOk getNearbyPlayerListOk = new GetNearbyPlayerListOk();
                getNearbyPlayerListOk.setPlayerId(playerVo.getMyInfo().getPlayerId());
                List<PlayerInfoVo> nearbyList = playerVo.getNearbyList();
                int friendSize = nearbyList.size();
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
                boolean[] isFriend = new boolean[friendSize];
                PlayerInfoVo piv;
                for (int i = 0; i < friendSize; i++) {
                    piv = nearbyList.get(i);
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
                    isFriend[i] = piv.isFriend();
                }
                getNearbyPlayerListOk.setPlayerInfoId(playerInfoId);
                getNearbyPlayerListOk.setAvatarURL(avatarURL);
                getNearbyPlayerListOk.setSuitHead(suitHead);
                getNearbyPlayerListOk.setSuitFace(suitFace);
                getNearbyPlayerListOk.setSex(sex);
                getNearbyPlayerListOk.setPlayerName(playerName);
                getNearbyPlayerListOk.setFighting(fighting);
                getNearbyPlayerListOk.setDistance(distance);
                getNearbyPlayerListOk.setMailCount(mailCount);
                getNearbyPlayerListOk.setIsOnline(isOnline);
                getNearbyPlayerListOk.setIsFriend(isFriend);
                session.send(getNearbyPlayerListOk);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
		    log.error(ex, ex);
		}
    }
}