package com.wyd.empire.world.server.handler.task;

import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.task.GetStrongerListOk;
import com.wyd.empire.world.bean.DoEveryDay;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取我要变强列表
 * 
 * @author Administrator
 */
public class GetStrongerListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetStrongerListHandler.class);

	// 获取玩家显示的任务列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			List<DoEveryDay> dedList = ServiceManager.getManager().getDoEveryDayService().getDoEveryDayListByType((byte) 2);
			int dedSize = dedList.size();
			String[] taskName = new String[dedSize];
			String[] taskTime = new String[dedSize];
			String[] taskRemark = new String[dedSize];
			String[] buttomText = new String[dedSize];
			String[] taskIcon = new String[dedSize];
			int[] jumpLevel = new int[dedSize];
			String[] jumpId = new String[dedSize];
			DoEveryDay ded;
			for (int i = 0; i < dedSize; i++) {
				ded = dedList.get(i);
				taskName[i] = ded.getTaskName();
				taskTime[i] = ded.getShowTime();
				taskRemark[i] = ded.getRemark();
				buttomText[i] = ded.getButtomText();
				taskIcon[i] = ded.getTaskIcon();
				jumpLevel[i] = ded.getLevel();
				jumpId[i] = ded.getInterfaceId();
			}
			GetStrongerListOk gsl = new GetStrongerListOk(data.getSessionId(), data.getSerial());
			gsl.setTaskName(taskName);
			gsl.setTaskTime(taskTime);
			gsl.setTaskRemark(taskRemark);
			gsl.setButtomText(buttomText);
			gsl.setTaskIcon(taskIcon);
			gsl.setJumpLevel(jumpLevel);
			gsl.setJumpId(jumpId);
			session.write(gsl);
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TASK_GTLF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
