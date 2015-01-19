package com.wyd.empire.world.server.handler.community;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.EnterCommunity;
import com.wyd.empire.protocol.data.community.EnterCommunityOk;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 
 * 类 <code> EnterCommunityHandler</code>Protocol.COMMUNITY
 * _EnterCommunity进入公会协议处理
 * 
 * @since JDK 1.6
 * 
 */
public class EnterCommunityHandler implements IDataHandler {
	private Logger log;

	public EnterCommunityHandler() {
		this.log = Logger.getLogger(EnterCommunityHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		EnterCommunity enterCommunity = (EnterCommunity) data;
		EnterCommunityOk enterCommunityOk = new EnterCommunityOk(data.getSessionId(), data.getSerial());
		try {
			Consortia consortia = (Consortia) ServiceManager.getManager().getConsortiaService()
					.get(Consortia.class, enterCommunity.getCommunityId());
			if (consortia == null) {
				throw new NullPointerException("公会不存在");// 如果传过来参数有问题，找不到公会，抛空指针异常
			}
			consortia.check();

			enterCommunityOk.setCommunityId(consortia.getId());
			enterCommunityOk.setCommunityName(consortia.getName());
			enterCommunityOk.setMemberCount(ServiceManager.getManager().getPlayerSinConsortiaService().getMemberNum(consortia.getId()));
			enterCommunityOk.setTotalMember(consortia.getTotalMember());
			enterCommunityOk.setLevel(consortia.getLevel());
			enterCommunityOk.setPrestige(consortia.getPrestige());
			enterCommunityOk.setMoney(consortia.getMoney());
			PlayerSinConsortia psc = ServiceManager.getManager().getPlayerSinConsortiaService().findPlayerSinConsortia(player.getId());
			if (!psc.getConsortia().equals(consortia)) {
				return;
			}
			enterCommunityOk.setPosition(psc.getPosition());
			enterCommunityOk.setNotice(consortia.getInsideNotice());
			enterCommunityOk.setDeclaration(consortia.getDeclaration());

			session.write(enterCommunityOk);

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_ENTER_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}