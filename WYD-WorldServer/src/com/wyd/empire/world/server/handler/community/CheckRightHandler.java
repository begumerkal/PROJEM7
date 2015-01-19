package com.wyd.empire.world.server.handler.community;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.CheckRightFailed;
import com.wyd.empire.protocol.data.community.CheckRightOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> CheckRightHandler</code>Protocol.COMMUNITY _CheckRight退出公会协议处理
 * 
 * @since JDK 1.6
 */
public class CheckRightHandler implements IDataHandler {
	private Logger log;

	public CheckRightHandler() {
		this.log = Logger.getLogger(CheckRightHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		boolean mark = false;
		try {
			if (!mark) {// 之前都没有返回过协议
				// 校验玩家公会构造券
				PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService()
						.uniquePlayerItem(player.getId(), Common.COMMUNITYBUILDID);
				if (pifs == null || !(pifs.getPLastNum() > 0)) {
					mark = true;
					CheckRightFailed checkRightFailed = new CheckRightFailed(data.getSessionId(), data.getSerial());
					checkRightFailed.setErrorMsg(ErrorMessages.COMMUNITY_CREATETICKET_MESSAGE);
					session.write(checkRightFailed);
				}
			}
			// 等级和构造券都没有问题，返回成功协议
			if (!mark) {
				CheckRightOk checkRightOk = new CheckRightOk(data.getSessionId(), data.getSerial());
				session.write(checkRightOk);
			}
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}