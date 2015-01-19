package com.wyd.empire.world.server.handler.community;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.GetApprovingMemberList;
import com.wyd.empire.protocol.data.community.SendApprovingMemberList;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> GetApprovingMemberListHandler</code>Protocol.COMMUNITY
 * _GetApprovingMemberListHandler获得工会待审核列表协议处理
 * 
 * @since JDK 1.6
 */
public class GetApprovingMemberListHandler implements IDataHandler {
	private Logger log;

	public GetApprovingMemberListHandler() {
		this.log = Logger.getLogger(GetApprovingMemberListHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GetApprovingMemberList getApprovingMemberList = (GetApprovingMemberList) data;
		SendApprovingMemberList sendApprovingMemberList = new SendApprovingMemberList(data.getSessionId(), data.getSerial());
		try {
			List<PlayerSinConsortia> list = ServiceManager.getManager().getPlayerSinConsortiaService()
					.getCommunityMembers(getApprovingMemberList.getCommunityId(), 0);
			int[] playerId = new int[list.size()];
			String[] playerName = new String[list.size()];
			int[] playerLevel = new int[list.size()];
			int[] sex = new int[list.size()];
			int[] zsleve = new int[list.size()];
			boolean isAcceptMember = true;
			Consortia con = (Consortia) ServiceManager.getManager().getConsortiaService()
					.get(Consortia.class, getApprovingMemberList.getCommunityId());
			if (con == null) {
				throw new NullPointerException();// 如果传过来参数有问题，找不到公会，抛空指针异常
			}
			isAcceptMember = con.getIsAcceptMember();
			int i = 0;
			for (PlayerSinConsortia psc : list) {
				playerId[i] = psc.getPlayer().getId();
				playerName[i] = psc.getPlayer().getName();
				playerLevel[i] = psc.getPlayer().getLevel();
				sex[i] = psc.getPlayer().getSex();
				zsleve[i] = psc.getPlayer().getZsLevel();
				i++;
			}
			sendApprovingMemberList.setPlayerId(playerId);
			sendApprovingMemberList.setPlayerName(playerName);
			sendApprovingMemberList.setPlayerLevel(playerLevel);
			sendApprovingMemberList.setAcceptMemberOr(isAcceptMember);
			sendApprovingMemberList.setZsleve(zsleve);
			sendApprovingMemberList.setSex(sex);
			session.write(sendApprovingMemberList);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_APPROVINGMEMBER_MESSAGE, data.getSerial(), data.getSessionId(),
					data.getType(), data.getSubType());
		}
	}
}