package com.wyd.empire.world.server.handler.friend;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.friend.DeleteFriend;
import com.wyd.empire.protocol.data.friend.DeleteFriendOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 
 * 类 <code> DeleteFriendHandler</code>Protocol.MAIL_DeleteFriend删除好友协议处理
 * 
 * @since JDK 1.6
 * 
 */
public class DeleteFriendHandler implements IDataHandler {
	private Logger log;

	public DeleteFriendHandler() {
		this.log = Logger.getLogger(DeleteFriendHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		DeleteFriend deleteFriend = (DeleteFriend) data;
		WorldPlayer myplayer = session.getPlayer(data.getSessionId());
		DeleteFriendOk deleteFriendOk = new DeleteFriendOk(data.getSessionId(), data.getSerial());
		try {

			// 删除好友操作
			ServiceManager.getManager().getFriendService().deleteFriend(myplayer.getId(), deleteFriend.getFriendId());

			session.write(deleteFriendOk);
			GameLogService.friend(myplayer.getId(), myplayer.getLevel(), 2, 1, deleteFriend.getFriendId());
			// 返回好友列表
			new GetFriendListHandler().sendFriendList(myplayer);
			// 返回黑名单
			new GetBlackListHandler().sendBlackList(myplayer);

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.FRIEND_DELETE_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}