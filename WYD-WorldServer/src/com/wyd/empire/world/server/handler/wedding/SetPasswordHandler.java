package com.wyd.empire.world.server.handler.wedding;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.wedding.SetPassword;
import com.wyd.empire.protocol.data.wedding.SetPasswordOk;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WeddingRoom;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.impl.MarryService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 设置密码
 * 
 * @author Administrator
 */
public class SetPasswordHandler implements IDataHandler {
	Logger log = Logger.getLogger(SetPasswordHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		SetPassword setPassword = (SetPassword) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			boolean usePassword = setPassword.getUsePassword();
			String password = setPassword.getPassword();
			String wedNum = setPassword.getWedNum();

			WeddingRoom weddingRoom = MarryService.weddingMap.get(wedNum);
			if (null == weddingRoom) {
				return;
			}

			// 只有新郎新娘才能操作
			if (player.getId() == weddingRoom.getWedHall().getManId() || player.getId() == weddingRoom.getWedHall().getWomanId()) {
				if (usePassword) {// 设置密码
					weddingRoom.setPassword(password);
				} else {// 取消密码
					weddingRoom.setPassword("");
				}
				SetPasswordOk setPasswordOk = new SetPasswordOk(data.getSessionId(), data.getSerial());
				setPasswordOk.setPassword(weddingRoom.getPassword());

				WorldPlayer wp = ServiceManager.getManager().getPlayerService().getWorldPlayerById(weddingRoom.getWedHall().getManId());
				wp.sendData(setPasswordOk);
				wp = ServiceManager.getManager().getPlayerService().getWorldPlayerById(weddingRoom.getWedHall().getWomanId());
				wp.sendData(setPasswordOk);
			}

		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
