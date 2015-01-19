package com.wyd.empire.world.server.handler.friend;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.friend.AddFriend;
import com.wyd.empire.protocol.data.friend.AddFriendOk;
import com.wyd.empire.world.bean.Friend;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> AddFriendHandler</code>Protocol.MAIL_AddFriend添加好友协议处理
 * 
 * @since JDK 1.6
 * 
 */
public class AddFriendHandler implements IDataHandler {
	private Logger log;

	public AddFriendHandler() {
		this.log = Logger.getLogger(AddFriendHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer myplayer = session.getPlayer(data.getSessionId());
		AddFriend addFriend = (AddFriend) data;
		AddFriendOk addFriendOk = new AddFriendOk(data.getSessionId(), data.getSerial());
		try {
			int playerId = addFriend.getFriendId();
			if (playerId < 0) {
				playerId = ServiceManager.getManager().getCrossService().getPlayerId(-playerId);
			}
			if (myplayer.getId() == playerId) {
				throw new ProtocolException(ErrorMessages.FRIEND_ME_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			WorldPlayer friendPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
			if (null == friendPlayer) {
				throw new ProtocolException(ErrorMessages.FRIEND_ISNOTREAL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			int friendCount = ServiceManager.getManager().getFriendService().getFriendNum(myplayer.getId());
			if (friendCount >= ServiceManager.getManager().getVersionService().getMaxFriendCount()) {
				throw new ProtocolException(ErrorMessages.FRIEND_TOOMANY, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			Friend friend = ServiceManager.getManager().getFriendService().checkPlayerIsFriend(myplayer.getId(), playerId);
			if (friend != null) {
				if (friend.getBlackList()) {
					ServiceManager.getManager().getFriendService().updateBlackList(myplayer.getId(), playerId);
					GameLogService.friend(myplayer.getId(), myplayer.getLevel(), 2, 2, playerId);
				} else {
					throw new ProtocolException(ErrorMessages.FRIEND_ISFRIEND_MESSAGE, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
			} else {
				// 添加好友操作
				ServiceManager.getManager().getFriendService().addFriend(myplayer.getId(), playerId);
				ServiceManager.getManager().getTaskService().addFriend(myplayer);
				ServiceManager.getManager().getTitleService().addFriend(myplayer);
			}
			session.write(addFriendOk);
			GameLogService.friend(myplayer.getId(), myplayer.getLevel(), 1, 1, playerId);
			// 返回好友列表
			new GetFriendListHandler().sendFriendList(myplayer);
			// 返回黑名单列表
			new GetBlackListHandler().sendBlackList(myplayer);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.FRIEND_ADD_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}