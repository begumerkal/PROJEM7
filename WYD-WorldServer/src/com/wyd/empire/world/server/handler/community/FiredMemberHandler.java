package com.wyd.empire.world.server.handler.community;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.FiredMember;
import com.wyd.empire.protocol.data.community.FiredMemberOk;
import com.wyd.empire.protocol.data.community.GetCommunityMemberList;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> FiredMemberHandler</code>Protocol.COMMUNITY _FiredMember开除会员协议处理
 * 
 * @since JDK 1.6
 */
public class FiredMemberHandler implements IDataHandler {
	private Logger log;

	public FiredMemberHandler() {
		this.log = Logger.getLogger(ExitCommunityHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		FiredMember firedMember = (FiredMember) data;
		FiredMemberOk firedMemberOk = new FiredMemberOk(data.getSessionId(), data.getSerial());
		WorldPlayer myPlayer = session.getPlayer(data.getSessionId());
		try {
			PlayerSinConsortia mypsc = ServiceManager.getManager().getPlayerSinConsortiaService().findPlayerSinConsortia(myPlayer.getId());
			PlayerSinConsortia psc = ServiceManager.getManager().getPlayerSinConsortiaService()
					.findPlayerSinConsortia(firedMember.getPlayerId());
			// 长老以上才可以开除
			if (!(mypsc.getPosition() < psc.getPosition()) || !mypsc.getConsortia().equals(psc.getConsortia()) || mypsc.getPosition() > 2) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOAUTHORITY_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			ServiceManager.getManager().getConsortiaService().exitCommunity(psc.getPlayer().getId(), myPlayer);
			// 返回开除成功协议
			session.write(firedMemberOk);
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getLoadPlayer(psc.getPlayer().getId());
			if (player != null) {
				player.setGuild(null);
			}
			GetCommunityMemberList communityMemberList = new GetCommunityMemberList(data.getSessionId(), data.getSerial());
			communityMemberList.setHandlerSource(firedMember.getHandlerSource());
			communityMemberList.setSource(firedMember.getSource());
			communityMemberList.setCommunityId(mypsc.getConsortia().getId());
			communityMemberList.setPageNumber(firedMember.getPageNumber());
			GetCommunityMemberListHandler communityMemberListHandler = new GetCommunityMemberListHandler();
			communityMemberListHandler.handle(communityMemberList);
			// 加入日志
			log.info("公会进出会记录：玩家Id-" + player.getId() + "-公会Id-" + mypsc.getConsortia().getId() + "-操作-开除会员-" + mypsc.getPlayer().getId()
					+ "开除" + psc.getPlayer().getId());
			WorldPlayer member = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(firedMember.getPlayerId());
			if (member != null) {
				// 更新角色信息-职位
				Map<String, String> info = new HashMap<String, String>();
				info.put("position", TipMessages.NOTJOIN);
				ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, player);
			}
		} catch (ProtocolException ex) {
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		} catch (Exception ex) {
			log.error(ex, ex);
			ex.printStackTrace();
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}