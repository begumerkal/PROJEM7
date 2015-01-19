package com.wyd.empire.world.server.handler.friend;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.friend.SetPrivateChat;
import com.wyd.empire.protocol.data.friend.SetPrivateChatOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> SetPrivateChatHandler</code>Protocol.MAIL_SetPrivateChat设为私聊协议处理
 * 
 * @since JDK 1.6
 */
public class SetPrivateChatHandler implements IDataHandler {
	private Logger log;

	public SetPrivateChatHandler() {
		this.log = Logger.getLogger(SetPrivateChatHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		SetPrivateChat setPrivateChat = (SetPrivateChat) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			SetPrivateChatOk doOk = new SetPrivateChatOk(data.getSessionId(), data.getSerial());
			session.write(doOk);
			// 设置私聊操作
			ServiceManager.getManager().getFriendService().setPrivateChat(player.getId(), setPrivateChat.getFriendId());
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.FRIEND_PRIVATE_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}