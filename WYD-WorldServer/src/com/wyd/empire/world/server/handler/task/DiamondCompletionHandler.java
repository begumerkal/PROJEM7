package com.wyd.empire.world.server.handler.task;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.task.DiamondCompletion;
import com.wyd.empire.world.bean.DayTask;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerTaskTitleService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.empire.world.task.PlayerTask;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 使用钻石完成每日任务
 * 
 * @author zengxc
 */
public class DiamondCompletionHandler implements IDataHandler {
	private Logger log = Logger.getLogger(DiamondCompletionHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		DiamondCompletion diamondCompletion = (DiamondCompletion) data;
		try {
			int taskId = diamondCompletion.getTaskId();
			PlayerTask taskIng = player.getTaskIngByTaskId(taskId, Common.TASK_TYPE_DAY);
			DayTask task = ServiceManager.getManager().getTaskService().getService().getDayTaskById(taskIng.getTaskId());
			// 支付
			pay(player, task.getQuickCost(), data);
			// 设置完成值
			IPlayerTaskTitleService playerTaskTitleService = ServiceManager.getManager().getPlayerTaskTitleService();
			playerTaskTitleService.updatePlayerTaskTarget(player, taskIng, -1, 0, false);
			playerTaskTitleService.updatePlayerTaskStatus(player, taskIng, Common.TASK_STATUS_SUBMIT, true);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.ILLEGAL_OPERATION_ERROR, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	/**
	 * 直接完成任务支付钻石
	 * 
	 * @param player
	 * @param diamondNum
	 * @throws Exception
	 */
	private void pay(WorldPlayer player, int diamondNum, AbstractData data) throws ProtocolException {
		if (player.getDiamond() < diamondNum) {
			throw new ProtocolException(ErrorMessages.TRATE_LOWTICKETNUM_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
		ServiceManager.getManager().getPlayerService().useTicket(player, diamondNum, TradeService.ORIGIN_PAYFORTASK, null, null, "直接完成任务");
	}

}
