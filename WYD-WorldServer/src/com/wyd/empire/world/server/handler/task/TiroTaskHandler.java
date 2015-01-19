package com.wyd.empire.world.server.handler.task;

import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.task.TiroTaskOk;
import com.wyd.empire.world.bean.PlayerGuide;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取新手指引步骤
 * 
 * @author Administrator
 */
public class TiroTaskHandler implements IDataHandler {
	Logger log = Logger.getLogger(TiroTaskHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (null == player)
			return;
		try {
			List<PlayerGuide> pgList = ServiceManager.getManager().getTaskService().getService().getPlayerGuideList(player.getId());
			int[] ids = new int[pgList.size()];
			int[] step = new int[pgList.size()];
			PlayerGuide pg;
			for (int i = 0; i < pgList.size(); i++) {
				pg = pgList.get(i);
				ids[i] = pg.getGuide();
				step[i] = pg.getStep();
			}
			TiroTaskOk tiroTaskOk = new TiroTaskOk(data.getSessionId(), data.getSerial());
			tiroTaskOk.setIds(ids);
			tiroTaskOk.setStep(step);
			session.write(tiroTaskOk);
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
