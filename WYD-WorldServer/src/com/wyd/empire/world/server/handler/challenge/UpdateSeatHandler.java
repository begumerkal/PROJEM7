package com.wyd.empire.world.server.handler.challenge;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.challenge.UpdateSeat;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 更新座位
 * 
 * @author Administrator
 *
 */
public class UpdateSeatHandler implements IDataHandler {
	Logger log = Logger.getLogger(UpdateSeatHandler.class);

	public void handle(AbstractData data) throws Exception {
		UpdateSeat updateSeat = (UpdateSeat) data;
		try {
			ServiceManager.getManager().getChallengeService()
					.updateSeat(updateSeat.getBattleTeamId(), updateSeat.getOldSeat(), updateSeat.getNewSeat());
			ServiceManager.getManager().getChallengeService().SynRoomInfo(updateSeat.getBattleTeamId());
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}
}
