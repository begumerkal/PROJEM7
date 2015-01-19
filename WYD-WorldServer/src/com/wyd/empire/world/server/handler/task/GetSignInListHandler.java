package com.wyd.empire.world.server.handler.task;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.task.SendSignInList;
import com.wyd.empire.world.bean.PlayerReward;
import com.wyd.empire.world.bean.Reward;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.ITaskService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获得玩家签到信息
 * 
 * @author zguoqiu
 */
public class GetSignInListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetSignInListHandler.class);

	// 获取玩家显示的任务列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (null == player)
			return;
		try {
			ITaskService taskService = ServiceManager.getManager().getTaskService().getService();
			PlayerReward signInfo = taskService.playerRewardInfo(player.getId());
			String yearMonth = DateUtil.getCurrentYearMonth();
			int maxDay = DateUtil.getCurrentMonthLastDay();
			int dayToWeek = DateUtil.getDayOfWeek(DateUtil.getCurrentMonthFirstDay());
			int toDay = DateUtil.getCurrentDay();
			List<Integer> signDays = signInfo.getSignDayList();
			int totalSign = signDays.size();
			int arraySign = signInfo.getArraySigns();
			int supplSign = signInfo.getSupplSigns();
			int supplTimes = signInfo.getSupplTimes();
			List<Integer> supplPrice = ServiceManager.getManager().getVersionService().getSupplPriceList();
			int arrayDays = 0;
			List<Integer> arrayItems = new ArrayList<Integer>();
			List<Integer> arrayCount = new ArrayList<Integer>();
			Reward signReward = taskService.getRewardByParam(arraySign + 1, 1);
			if (null == signReward) {
				List<Reward> rewardList = taskService.getRewardList(1);
				if (rewardList.size() > 0) {
					signReward = rewardList.get(rewardList.size() - 1);
				}
			}
			if (null != signReward) {
				arrayDays = signReward.getParam();
				List<RewardInfo> rewardList = ServiceUtils.getRewardInfo(signReward.getReward(), player.getPlayer().getSex().intValue());
				for (RewardInfo ri : rewardList) {
					arrayItems.add(ri.getItemId());
					arrayCount.add(ri.getCount());
				}
			}
			List<Integer> totalDays = new ArrayList<Integer>();
			List<Boolean> totalRewards = new ArrayList<Boolean>();
			List<Integer> rewardCount = new ArrayList<Integer>();
			List<Integer> totalItems = new ArrayList<Integer>();
			List<Integer> totalCount = new ArrayList<Integer>();
			List<Reward> srList = taskService.getRewardList(0);
			for (Reward sr : srList) {
				totalDays.add(sr.getParam());
				totalRewards.add(signInfo.isSignReward(sr.getParam()));
				List<RewardInfo> rewardList = ServiceUtils.getRewardInfo(sr.getReward(), player.getPlayer().getSex().intValue());
				rewardCount.add(rewardList.size());
				for (RewardInfo ri : rewardList) {
					totalItems.add(ri.getItemId());
					totalCount.add(ri.getCount());
				}
			}
			SendSignInList ssil = new SendSignInList(data.getSessionId(), data.getSerial());
			ssil.setYearMonth(yearMonth);
			ssil.setMaxDay(maxDay);
			ssil.setDayToWeek(dayToWeek);
			ssil.setToDay(toDay);
			ssil.setSignDays(ServiceUtils.getInts(signDays.toArray()));
			ssil.setTotalSign(totalSign);
			ssil.setArraySign(arraySign);
			ssil.setSupplSign(supplSign);
			ssil.setSupplTimes(supplTimes);
			ssil.setSupplPrice(ServiceUtils.getInts(supplPrice.toArray()));
			ssil.setArrayDays(arrayDays);
			ssil.setArrayItems(ServiceUtils.getInts(arrayItems.toArray()));
			ssil.setArrayCount(ServiceUtils.getInts(arrayCount.toArray()));
			ssil.setTotalDays(ServiceUtils.getInts(totalDays.toArray()));
			ssil.setTotalRewards(ServiceUtils.getBooleans(totalRewards.toArray()));
			ssil.setRewardCount(ServiceUtils.getInts(rewardCount.toArray()));
			ssil.setTotalItems(ServiceUtils.getInts(totalItems.toArray()));
			ssil.setTotalCount(ServiceUtils.getInts(totalCount.toArray()));
			session.write(ssil);
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TASK_GETEVERY_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
