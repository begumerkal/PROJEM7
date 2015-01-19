package com.wyd.empire.world.server.handler.task;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.task.CommitTask;
import com.wyd.empire.protocol.data.task.CommitTaskOk;
import com.wyd.empire.protocol.data.task.GetTaskList;
import com.wyd.empire.world.bean.Task;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerTaskTitleService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.empire.world.task.PlayerTask;
import com.wyd.net.ProtocolFactory;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 提交完成任务
 * 
 * @author Administrator
 */
public class CommitTaskHandler implements IDataHandler {
	Logger log = Logger.getLogger(CommitTaskHandler.class);

	// 提交完成任务
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer worldPlayer = session.getPlayer(data.getSessionId());
		CommitTask commitTask = (CommitTask) data;
		try {
			IPlayerTaskTitleService baseTaskService = ServiceManager.getManager().getPlayerTaskTitleService();
			PlayerTask taskIng = worldPlayer.getTaskIngByTaskId(commitTask.getTaskId(), commitTask.getTaskType());
			if (null == taskIng || Common.TASK_STATUS_SUBMIT != taskIng.getStatus()) {
				throw new ProtocolException(ErrorMessages.TASK_TNF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			baseTaskService.updateTaskFinishStatus(taskIng, worldPlayer);
			if (taskIng.getTaskType() != Common.TASK_TYPE_DAY) {
				Task task = ServiceManager.getManager().getTaskService().getService().getTaskById(taskIng.getTaskId());
				if (ServiceManager.getManager().getTaskService().getService().isParentTask(task.getId())) {
					ServiceManager.getManager().getTaskService().getService().checkTask(worldPlayer);
				}
			} else {
				ServiceManager.getManager().getTaskService().doDayTask(worldPlayer);
			}
			// 更新任务列表
			GetTaskList gtl = new GetTaskList();
			gtl.setHandlerSource(session);
			gtl.setSessionId(data.getSessionId());
			ProtocolFactory.getDataHandler(GetTaskList.class).handle(gtl);
			// 通知奖励领取成功
			CommitTaskOk cto = new CommitTaskOk(data.getSessionId(), data.getSerial());
			cto.setTaskId(commitTask.getTaskId());
			cto.setTaskType(commitTask.getTaskType());
			session.write(cto);
			ServiceManager.getManager().getTitleService().completeTask(worldPlayer);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TASK_CTFAIL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
