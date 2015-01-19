package com.wyd.empire.world.server.handler.friend;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.friend.SetBlackList;
import com.wyd.empire.protocol.data.friend.SetBlackListOk;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> SetBlackListHandler</code>Protocol.MAIL_SetBlackList设为黑名单协议处理
 * 
 * @since JDK 1.6
 */
public class SetBlackListHandler implements IDataHandler {
	private Logger log;

	public SetBlackListHandler() {
		this.log = Logger.getLogger(SetBlackListHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		SetBlackList setBlackList = (SetBlackList) data;
		WorldPlayer myplayer = session.getPlayer(data.getSessionId());
		SetBlackListOk setBlackListOk = new SetBlackListOk(data.getSessionId(), data.getSerial());
		try {
			// 黑名单设置操作
			ServiceManager.getManager().getFriendService().setBlackList(myplayer.getId(), setBlackList.getFriendId());
			session.write(setBlackListOk);
			GameLogService.friend(myplayer.getId(), myplayer.getLevel(), 1, 2, setBlackList.getFriendId());
			// 返回好友列表
			new GetFriendListHandler().sendFriendList(myplayer);
			// 返回黑名单列表
			new GetBlackListHandler().sendBlackList(myplayer);
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.FRIEND_SETBLACKLIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}