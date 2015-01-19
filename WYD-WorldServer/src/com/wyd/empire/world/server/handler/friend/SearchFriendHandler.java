package com.wyd.empire.world.server.handler.friend;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.friend.SearchFriend;
import com.wyd.empire.protocol.data.friend.SearchFriendOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> SearchFriendHandler</code>Protocol.MAIL_SearchFriend搜索好友协议处理
 * 
 * @since JDK 1.6
 */
public class SearchFriendHandler implements IDataHandler {
	private Logger log;

	public SearchFriendHandler() {
		this.log = Logger.getLogger(SearchFriendHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		SearchFriend searchFriend = (SearchFriend) data;
		SearchFriendOk searchFriendOk = new SearchFriendOk(data.getSessionId(), data.getSerial());
		boolean mark = false;
		try {
			int playerId;
			if (!searchFriend.getPlayerName().trim().isEmpty()) {
				WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerByName(searchFriend.getPlayerName());
				if (worldPlayer == null) {
					mark = true;
					throw new Exception(Common.ERRORKEY + ErrorMessages.FRIEND_ISNOTREAL_MESSAGE);
				}
				playerId = worldPlayer.getId();
			} else {
				WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(searchFriend.getPlayerId());
				if (worldPlayer == null) {
					mark = true;
					throw new Exception(Common.ERRORKEY + ErrorMessages.FRIEND_ISNOTREAL_MESSAGE);
				}
				playerId = searchFriend.getPlayerId();
			}
			searchFriendOk.setPlayerId(playerId);
			session.write(searchFriendOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (mark) {
				if (null != ex.getMessage())
					throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
			} else {
				throw new ProtocolException(ErrorMessages.FRIEND_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
		}
	}
}