package com.wyd.empire.world.server.handler.wedding;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.wedding.GetLoveLetterInfo;
import com.wyd.empire.protocol.data.wedding.SendLoveLetterToCouple;
import com.wyd.empire.world.bean.MarryRecord;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class GetLoveLetterInfoHandler implements IDataHandler {

	Logger log = Logger.getLogger(GetLoveLetterInfoHandler.class);

	/**
	 * 获取求婚信内容
	 */
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		GetLoveLetterInfo getLoveLetterInfo = (GetLoveLetterInfo) data;
		try {
			int marryMailId = getLoveLetterInfo.getMarryMailId();
			int coupleId = 0;
			MarryRecord mr = (MarryRecord) ServiceManager.getManager().getMarryService().get(MarryRecord.class, marryMailId);

			SendLoveLetterToCouple sendLoveLetterToCouple = new SendLoveLetterToCouple(data.getSessionId(), data.getSerial());
			if (player.getId() == mr.getManId()) {
				coupleId = mr.getWomanId();
			} else {
				coupleId = mr.getManId();
			}

			Player couple = ServiceManager.getManager().getPlayerService().getPlayerById(coupleId);

			sendLoveLetterToCouple.setMarryMark(0);
			sendLoveLetterToCouple.setSendId(coupleId);
			sendLoveLetterToCouple.setSendName(couple.getName());
			sendLoveLetterToCouple.setProposeItemId(mr.getUseItemId());
			sendLoveLetterToCouple.setTimeId(-1);
			session.write(sendLoveLetterToCouple);

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
