package com.wyd.empire.world.server.handler.task;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.task.QuickUpExp;
import com.wyd.empire.protocol.data.task.QuickUpExpOk;
import com.wyd.empire.world.bean.DayTask;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.impl.PlayerTaskTitleService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.empire.world.task.PlayerTask;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 经验快速提升
 * 
 * @author Administrator
 */
public class QuickUpExpHandler implements IDataHandler {
	Logger log = Logger.getLogger(QuickUpExpHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		QuickUpExp quickUpExp = (QuickUpExp) data;
		try {
			int taskId = quickUpExp.getTaskId();
			PlayerTask taskIng = player.getTaskIngByTaskId(taskId, Common.TASK_TYPE_DAY);
			if (null == taskIng || taskIng.getUpLevel() >= Common.TASK_TOP_LEVEL) {
				throw new ProtocolException(ErrorMessages.TASK_GTLF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			int upLevel = taskIng.getUpLevel();
			DayTask task = ServiceManager.getManager().getTaskService().getService().getDayTaskById(taskId);
			int upCost = PlayerTaskTitleService.getUpLevelCost(task.getUpLevelCost(), taskIng.getUpLevel() + 1);
			if (upCost > player.getDiamond()) {
				throw new ProtocolException(ErrorMessages.TRATE_LOWTICKETNUM_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			ServiceManager.getManager().getPlayerService().useTicket(player, upCost, TradeService.ORIGIN_QUICKUPTASK, null, null, "提升任务奖励");
			taskIng.setUpLevel(upLevel + 1);
			QuickUpExpOk quickUpExpOk = new QuickUpExpOk(data.getSessionId(), data.getSerial());
			quickUpExpOk.setTaskId(taskId);
			session.write(quickUpExpOk);
			GameLogService.taskRewardChange(player.getId(), player.getLevel(), taskId, upCost);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TASK_GTLF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
