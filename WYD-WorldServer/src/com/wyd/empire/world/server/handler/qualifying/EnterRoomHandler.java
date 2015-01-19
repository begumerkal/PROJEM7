package com.wyd.empire.world.server.handler.qualifying;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.qualifying.EnterRoomOk;
import com.wyd.empire.world.bean.RankRecord;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.handler.task.GetEverydayRewardListHandler;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 进入排位赛界面
 * 
 * @author Administrator
 */
public class EnterRoomHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetEverydayRewardListHandler.class);

	// 获取玩家显示的任务列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		boolean mark = false;
		try {
			List<RankRecord> rrList = ServiceManager.getManager().getRankRecordService().getRankRecordList(100);
			int myRanking = ServiceManager.getManager().getRankRecordService().getPlayerRankNum(player.getId());
			RankRecord myRR = ServiceManager.getManager().getRankRecordService().getPlayerRankByPlayerId(player.getId());
			String rankReward = ServiceManager.getManager().getVersionService().getVersion().getRankreward();
			// 获得奖励列表
			List<RewardInfo> rewardList = ServiceUtils.getRewardInfo(rankReward, player.getPlayer().getSex().intValue());
			int[] playerId = new int[rrList.size()];
			int[] rankings = new int[rrList.size()];
			String[] names = new String[rrList.size()];
			int[] scores = new int[rrList.size()];
			int[] ranks = new int[rrList.size()];
			int[] successRates = new int[rrList.size()];
			String[] rewardIcons = new String[rewardList.size()];
			String[] rewardName = new String[rewardList.size()];
			int[] rewardValidTime = new int[rewardList.size()];
			int index = 0;
			for (RankRecord rr : rrList) {
				playerId[index] = rr.getPlayer().getId();
				rankings[index] = index + 1;
				names[index] = rr.getPlayer().getName();
				scores[index] = rr.getIntegral();
				ranks[index] = rr.getPlayer().getHonorLevel();
				successRates[index] = rr.getTotalNum() == 0 ? 0 : rr.getWinNum() * 10000 / rr.getTotalNum();
				if (index != 0 && rr.getIntegral() == scores[index - 1]) {
					rankings[index] = rankings[index - 1];
				}
				index++;
			}
			ShopItem si;
			index = 0;
			for (RewardInfo reward : rewardList) {
				si = ServiceManager.getManager().getShopItemService().getShopItemById(reward.getItemId());
				rewardIcons[index] = si.getIcon();
				rewardName[index] = si.getName();
				rewardValidTime[index] = reward.getCount();
				index++;
			}
			// 获得军衔名称
			String myRankName = "---";
			if (player.getPlayer().getHonorLevel() != 0) {
				myRankName = "(" + TipMessages.honorMap.get("HONORLEVEL" + player.getPlayer().getHonorLevel()) + ")";
			}
			// 计算下一级经验
			int myNextExp = 120 * (int) Math.pow(player.getPlayer().getHonorLevel(), 3);
			EnterRoomOk enterRoomOk = new EnterRoomOk(data.getSessionId(), data.getSerial());
			enterRoomOk.setPlayerId(playerId);
			enterRoomOk.setRankings(rankings);
			enterRoomOk.setNames(names);
			enterRoomOk.setScores(scores);
			enterRoomOk.setRanks(ranks);
			enterRoomOk.setSuccessRates(successRates);
			// 获得当前的号数
			Calendar cal = Calendar.getInstance();
			if (cal.get(Calendar.DAY_OF_WEEK) == 2) {// 周一休赛
				enterRoomOk.setRemainingTime(0);
			} else if (cal.get(Calendar.DAY_OF_WEEK) == 1) {// 周日
				enterRoomOk.setRemainingTime(1);
			} else {// 其他天数
				enterRoomOk.setRemainingTime(9 - cal.get(Calendar.DAY_OF_WEEK));
			}
			// enterRoomOk.setRemainingTime(26-DateUtil.getCurrentDay()<0?0:26-DateUtil.getCurrentDay());
			// enterRoomOk.setRemainingTime(30-DateUtil.getCurrentDay()<0?0:30-DateUtil.getCurrentDay());
			enterRoomOk.setMyRanking(myRanking);
			enterRoomOk.setMyScore(null == myRR ? 0 : myRR.getIntegral());
			enterRoomOk.setMyRank(player.getPlayer().getHonorLevel());
			enterRoomOk.setMyRankName(myRankName);
			enterRoomOk.setMyExp(player.getPlayer().getHonor());
			enterRoomOk.setMyNextExp(myNextExp);
			enterRoomOk.setRewardIcons(rewardIcons);
			enterRoomOk.setRewardName(rewardName);
			// enterRoomOk.setHasOpened(Common.rankStart);
			enterRoomOk.setRewardValidTime(rewardValidTime);
			Map<String, Integer> map = ServiceManager.getManager().getVersionService().getSpecialMark();
			int rankOpen = map.get("rankOpen");
			int rankClose = map.get("rankClose");
			if (enterRoomOk.getRemainingTime() == 0 || DateUtil.getCurrentHour() < rankOpen || DateUtil.getCurrentHour() > rankClose) {
				enterRoomOk.setHasOpened(false);
			} else {
				enterRoomOk.setHasOpened(true);
			}
			session.write(enterRoomOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (mark) {
				if (null != ex.getMessage())
					throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
			} else {
				throw new ProtocolException(ErrorMessages.ROOM_GRLF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
		}
	}
}
