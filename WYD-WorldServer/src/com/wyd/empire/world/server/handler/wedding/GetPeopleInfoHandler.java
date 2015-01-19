package com.wyd.empire.world.server.handler.wedding;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.wedding.GetPeopleInfo;
import com.wyd.empire.protocol.data.wedding.SendPeopleInfo;
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
 * 获得结婚双方玩家信息
 * 
 * @author Administrator
 */
public class GetPeopleInfoHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetPeopleInfoHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GetPeopleInfo getPeopleInfo = (GetPeopleInfo) data;
		try {
			WeddingRoom wedRoom = MarryService.weddingMap.get(getPeopleInfo.getWedNum());
			WorldPlayer man = ServiceManager.getManager().getPlayerService().getWorldPlayerById(wedRoom.getWedHall().getManId());
			WorldPlayer woman = ServiceManager.getManager().getPlayerService().getWorldPlayerById(wedRoom.getWedHall().getWomanId());
			SendPeopleInfo sendPeopleInfo = new SendPeopleInfo(data.getSessionId(), data.getSerial());
			sendPeopleInfo.setManName(man.getName());
			sendPeopleInfo.setWomanName(woman.getName());
			sendPeopleInfo.setWedTime(wedRoom.getWedHall().getStartTime() + "-" + wedRoom.getWedHall().getEndTime());
			String[] manIcon = new String[2];
			String[] womanIcon = new String[2];
			manIcon[0] = man.getSuit_head();
			manIcon[1] = man.getSuit_face();
			womanIcon[0] = woman.getSuit_head();
			womanIcon[1] = woman.getSuit_face();
			sendPeopleInfo.setManIcon(manIcon);
			sendPeopleInfo.setWomanIcon(womanIcon);

			session.write(sendPeopleInfo);
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
