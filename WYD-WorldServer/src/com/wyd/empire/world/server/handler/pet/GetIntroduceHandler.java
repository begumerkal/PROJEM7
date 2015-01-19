package com.wyd.empire.world.server.handler.pet;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.pet.GetIntroduceOk;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.server.service.base.IPlayerPetService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 宠物训练与培养说明
 * 
 * @author zengxc
 *
 */

public class GetIntroduceHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetIntroduceHandler.class);
	IPlayerPetService playerPetService = null;
	ServiceManager manager = ServiceManager.getManager();

	@Override
	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			GetIntroduceOk ok = new GetIntroduceOk(data.getSessionId(), data.getSerial());
			ok.setTrainDesc(TipMessages.PET_TRAIN_DESC);
			ok.setCultureDesc(TipMessages.PET_CULTURE_DESC);
			ok.setInheritance(TipMessages.PET_INHERITANCE_DESC);
			return ok;
			// session.write(ok);
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.PET_FAIL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

}
