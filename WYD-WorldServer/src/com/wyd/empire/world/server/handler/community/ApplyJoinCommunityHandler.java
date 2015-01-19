package com.wyd.empire.world.server.handler.community;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.ApplyJoinCommunity;
import com.wyd.empire.protocol.data.community.ApplyJoinCommunityOk;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> ApplyJoinCommunityHandler</code>Protocol.COMMUNITY
 * _ApplyJoinCommunity申请入会协议协议处理
 * 
 * @since JDK 1.6
 */
public class ApplyJoinCommunityHandler implements IDataHandler {
	private Logger log;

	public ApplyJoinCommunityHandler() {
		this.log = Logger.getLogger(ApplyJoinCommunityHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		ApplyJoinCommunity applyJoinCommunity = (ApplyJoinCommunity) data;
		try {
			ApplyJoinCommunityOk applyJoinCommunityOk = new ApplyJoinCommunityOk(data.getSessionId(), data.getSerial());
			Consortia consortia = (Consortia) ServiceManager.getManager().getConsortiaService()
					.get(Consortia.class, applyJoinCommunity.getCommunityId());
			if (!(ServiceManager.getManager().getPlayerSinConsortiaService().checkCommunityNum(applyJoinCommunity.getCommunityId()) < consortia
					.getTotalMember())) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_TOOMANY2_LONG, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			if (player.getGuildId() == 0) {
				// 判断该玩家是否申请过该工会
				if (!ServiceManager.getManager().getPlayerSinConsortiaService()
						.checkPlayerCanApply(player.getId(), applyJoinCommunity.getCommunityId())) {
					// 添加玩家申请记录
					ServiceManager.getManager().getConsortiaService()
							.applyJoinCommunity(applyJoinCommunity.getCommunityId(), player.getId());
				}
			} else {
				throw new ProtocolException(ErrorMessages.COMMUNITY_ISMEMBER_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			session.write(applyJoinCommunityOk);
			GameLogService.applyGuild(player.getId(), player.getLevel(), consortia.getId(), consortia.getLevel(), consortia.getPrestige(),
					consortia.getWinNum());
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_APPLYJOIN_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}