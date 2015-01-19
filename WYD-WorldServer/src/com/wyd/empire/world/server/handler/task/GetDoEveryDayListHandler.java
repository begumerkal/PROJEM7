package com.wyd.empire.world.server.handler.task;

import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.task.GetDoEveryDayListOk;
import com.wyd.empire.world.bean.DoEveryDay;
import com.wyd.empire.world.dao.impl.DoEveryDayDao.DoEveryDayVo;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取每日必做列表
 * 
 * @author Administrator
 */
public class GetDoEveryDayListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetDoEveryDayListHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			List<DoEveryDayVo> dedList = ServiceManager.getManager().getDoEveryDayService().getDoEveryDayList();
			int dedSize = dedList.size();
			String[] taskName = new String[dedSize];
			String[] taskTime = new String[dedSize];
			String[] taskRemark = new String[dedSize];
			String[] buttomText = new String[dedSize];
			String[] taskIcon = new String[dedSize];
			int[] jumpLevel = new int[dedSize];
			String[] jumpId = new String[dedSize];
			boolean[] activate = new boolean[dedSize];
			DoEveryDay ded;
			DoEveryDayVo dedv;
			for (int i = 0; i < dedSize; i++) {
				dedv = dedList.get(i);
				ded = dedv.getDed();
				taskName[i] = ded.getTaskName();
				taskTime[i] = ded.getShowTime();
				taskRemark[i] = ded.getRemark();
				buttomText[i] = ded.getButtomText();
				taskIcon[i] = ded.getTaskIcon();
				jumpLevel[i] = ded.getLevel();
				jumpId[i] = ded.getInterfaceId();
				activate[i] = dedv.isActivate();
			}
			GetDoEveryDayListOk gdedl = new GetDoEveryDayListOk(data.getSessionId(), data.getSerial());
			gdedl.setTaskName(taskName);
			gdedl.setTaskTime(taskTime);
			gdedl.setTaskRemark(taskRemark);
			gdedl.setButtomText(buttomText);
			gdedl.setTaskIcon(taskIcon);
			gdedl.setJumpLevel(jumpLevel);
			gdedl.setJumpId(jumpId);
			gdedl.setActivate(activate);
			session.write(gdedl);
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TASK_GTLF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
