package com.wyd.empire.world.server.handler.player;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.player.GetBuffList;
import com.wyd.empire.protocol.data.player.GetBuffListOk;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取技能列表
 * 
 * @author Administrator
 *
 */
public class GetBuffListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetBuffListHandler.class);

	// 获取技能列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GetBuffList getBuffList = (GetBuffList) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(getBuffList.getPlayerId());
			if (null == player) {
				throw new NullPointerException();
			}

			List<Buff> list = player.getBuffList();
			String[] buffName = new String[list.size()];
			String[] icon = new String[list.size()];
			int[] countdownTime = new int[list.size()];
			int[] addType = new int[list.size()];
			int[] param1 = new int[list.size()];
			int[] param2 = new int[list.size()];
			int[] param3 = new int[list.size()];
			int i = 0;
			for (Buff buff : list) {
				buffName[i] = buff.getBuffName();
				icon[i] = buff.getIcon();
				countdownTime[i] = (int) (buff.getEndtime() - new Date().getTime()) / 1000 / 60;
				addType[i] = buff.getAddType();
				param1[i] = buff.getQuantity();
				param2[i] = 0;
				param3[i] = 0;
				i++;
			}

			GetBuffListOk getBuffListOk = new GetBuffListOk(data.getSessionId(), data.getSerial());
			getBuffListOk.setBuffName(buffName);
			getBuffListOk.setIcon(icon);
			getBuffListOk.setAddType(addType);
			getBuffListOk.setCountdownTime(countdownTime);
			getBuffListOk.setParam1(param1);
			getBuffListOk.setParam2(param2);
			getBuffListOk.setParam3(param3);

			session.write(getBuffListOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.PLAYER_GKF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
