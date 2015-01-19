package com.wyd.empire.world.server.handler.community;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.InviteMember;
import com.wyd.empire.protocol.data.community.InviteMemberOk;
import com.wyd.empire.world.bean.ConsortiaContribute;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 
 * 类 <code> InviteMemberHandler</code>Protocol.COMMUNITY _InviteMember邀请入会协议处理
 * 
 * @since JDK 1.6
 * 
 */
public class InviteMemberHandler implements IDataHandler {
	private Logger log;

	public InviteMemberHandler() {
		this.log = Logger.getLogger(InviteMemberHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		InviteMember inviteMember = (InviteMember) data;
		try {
			InviteMemberOk inviteMemberOk = new InviteMemberOk(data.getSessionId(), data.getSerial());

			// 保存公会贡献度记录
			ConsortiaContribute consortiaContribute = new ConsortiaContribute();
			consortiaContribute.getPlayer().setId(inviteMember.getPlayerId());
			consortiaContribute.getConsortia().setId(inviteMember.getCommunityId());
			consortiaContribute.setContribute(0);
			consortiaContribute.setDiscontribute(0);
			ServiceManager.getManager().getPlayerSinConsortiaService().save(consortiaContribute);

			// 保存玩家邀请对象
			PlayerSinConsortia pcs = new PlayerSinConsortia();

			pcs.getPlayer().setId(inviteMember.getPlayerId());
			pcs.getConsortia().setId(inviteMember.getCommunityId());
			pcs.setIdentity(0);
			pcs.setPosition(4);
			pcs.setContribute(0);
			pcs.setDiscontribute(0);
			pcs.setConsortiaContribute(consortiaContribute);

			ServiceManager.getManager().getPlayerSinConsortiaService().save(pcs);

			session.write(inviteMemberOk);

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_INVITE_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}