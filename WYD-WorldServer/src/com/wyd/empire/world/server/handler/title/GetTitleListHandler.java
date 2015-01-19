package com.wyd.empire.world.server.handler.title;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.title.GetTitleListOk;
import com.wyd.empire.world.bean.PlayerDIYTitle;
import com.wyd.empire.world.bean.Title;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerDIYTitleService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.empire.world.title.TitleIng;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取玩家显示的任务列表
 * 
 * @author Administrator
 * 
 */
public class GetTitleListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetTitleListHandler.class);

	// 获取玩家显示的任务列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		IPlayerDIYTitleService playerDIYTitleService = ServiceManager.getManager().getPlayerDIYTitleService();
		try {
			List<TitleIng> titleList = player.getTitleIngList();
			List<PlayerDIYTitle> diyTitles = playerDIYTitleService.getDIYTitles(player.getId());
			List<Integer> ptId = new ArrayList<Integer>();
			List<String> title = new ArrayList<String>();
			List<String> titleDesc = new ArrayList<String>();
			List<Integer> status = new ArrayList<Integer>();
			List<Integer> target = new ArrayList<Integer>();
			List<Integer> targetNum = new ArrayList<Integer>();
			List<Integer> type = new ArrayList<Integer>();
			List<Integer> daysLeft = new ArrayList<Integer>();
			boolean selDIY = false;// 是否选择了DIY
			for (PlayerDIYTitle diyTitle : diyTitles) {
				ptId.add(diyTitle.getId());
				title.add(diyTitle.getTitle());
				titleDesc.add(diyTitle.getTitleDesc());
				if (!selDIY) {
					selDIY = diyTitle.getState() == 1;
				}
				// 如果已选择则设为3(显示)
				status.add(diyTitle.getState() == 1 ? 3 : 2);
				target.add(0);
				targetNum.add(0);
				type.add(1);
				daysLeft.add(daysLeft(diyTitle.getEndDate()));
			}
			for (TitleIng titleIng : titleList) {
				Title playerTitle = ServiceManager.getManager().getTitleService().getTitleById(titleIng.getTitleId());
				if (playerTitle.getType() == 1) {
					ptId.add(titleIng.getTitleId());
					title.add(playerTitle.getTitle());
					titleDesc.add(playerTitle.getTitleDesc());
					int s = titleIng.getStatus();
					// 如果已选择了DIY则不能显示其它的
					s = (selDIY && s == 3) ? 2 : s;
					status.add(s);
					target.add(titleIng.getTargetValueList().size() > 0 ? titleIng.getTargetValueList().get(0) : 0);
					targetNum.add(titleIng.getFinishValuetList().size() > 0 ? titleIng.getFinishValuetList().get(0) : 0);
					type.add(0);
					daysLeft.add(-1);
				}
			}
			GetTitleListOk gtListOk = new GetTitleListOk(data.getSessionId(), data.getSerial());
			gtListOk.setPtId(ServiceUtils.getInts(ptId.toArray()));
			gtListOk.setTitle(title.toArray(new String[]{}));
			gtListOk.setTitleDesc(titleDesc.toArray(new String[]{}));
			gtListOk.setStatus(ServiceUtils.getInts(status.toArray()));
			gtListOk.setTarget(ServiceUtils.getInts(target.toArray()));
			gtListOk.setTargetNum(ServiceUtils.getInts(targetNum.toArray()));
			gtListOk.setTitleType(ServiceUtils.getInts(type.toArray()));
			gtListOk.setDaysLeft(ServiceUtils.getInts(daysLeft.toArray()));
			session.write(gtListOk);
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TASK_GTLF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	private int daysLeft(Date endDate) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_YEAR);
		int maxDay = cal.getMaximum(Calendar.DAY_OF_YEAR);
		cal.setTime(endDate);
		int endYear = cal.get(Calendar.YEAR);
		int endDay = cal.get(Calendar.DAY_OF_YEAR);
		if (year == endYear) {
			return endDay - day;
		} else if (endYear > year) {
			day += 1;
			return maxDay - day + endDay;
		}
		return -1;
	}
}
