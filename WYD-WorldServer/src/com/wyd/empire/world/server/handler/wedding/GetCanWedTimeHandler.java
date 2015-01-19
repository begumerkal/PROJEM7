package com.wyd.empire.world.server.handler.wedding;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.wedding.SendCanWedTime;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获得结婚可结婚时间
 * 
 * @author Administrator
 */
public class GetCanWedTimeHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetCanWedTimeHandler.class);

	@SuppressWarnings({"rawtypes"})
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			Map<Integer, String> map = ServiceManager.getManager().getVersionService().getWedMap();

			Set<Integer> key = map.keySet();
			int[] timeId = new int[key.size()];
			String[] startTime = new String[key.size()];
			String[] endTime = new String[key.size()];

			// 遍历map，解析结婚时间
			int index = 0;
			for (Iterator it = key.iterator(); it.hasNext();) {
				int keyvalue = (Integer) it.next();
				timeId[index] = keyvalue;
				String[] str = map.get(keyvalue).split("-");
				startTime[index] = str[0];
				endTime[index] = str[1];
				index++;
			}

			SendCanWedTime sendCanWedTime = new SendCanWedTime(data.getSessionId(), data.getSerial());
			sendCanWedTime.setEndTime(endTime);
			sendCanWedTime.setStartTime(startTime);
			sendCanWedTime.setTimeId(timeId);
			session.write(sendCanWedTime);

		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
