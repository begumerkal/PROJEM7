package com.wyd.empire.world.server.handler.community;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.GetCommunityMemberList;
import com.wyd.empire.protocol.data.community.SendCommunityMemberList;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> GetCommunityMemberListHandler</code>Protocol.COMMUNITY
 * _GetCommunityMemberList获得公会成员协议处理
 * 
 * @since JDK 1.6
 */
public class GetCommunityMemberListHandler implements IDataHandler {
	private Logger log;

	public GetCommunityMemberListHandler() {
		this.log = Logger.getLogger(GetCommunityMemberListHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GetCommunityMemberList getCommunityMemberList = (GetCommunityMemberList) data;
		try {
			int pageNum = getCommunityMemberList.getPageNumber();
			int pageSize = Common.PAGESIZE;// 页面单页条数
			int pageCount = Common.PAGESIZE;// 返回给客户端的条数
			int endPagecount = 0;
			int stratPagecount = 0;
			List<PlayerSinConsortia> list = ServiceManager.getManager().getPlayerSinConsortiaService()
					.getCommunityMembers(getCommunityMemberList.getCommunityId(), 1);

			int totalSize = (int) Math.ceil(list.size() / (double) pageSize);
			pageNum = pageNum > totalSize && list.size() != 0 ? totalSize : pageNum;
			stratPagecount = (pageNum - 1) * pageSize;
			endPagecount = pageNum * pageSize;
			endPagecount = endPagecount > list.size() ? list.size() : endPagecount;
			pageCount = endPagecount - stratPagecount;
			pageCount = pageCount > 0 ? pageCount : 0;

			SendCommunityMemberList sendCommunityMemberList = new SendCommunityMemberList(data.getSessionId(), data.getSerial());
			String[] playerName = new String[pageCount];
			int[] position = new int[pageCount];
			int[] playerContribution = new int[pageCount];
			boolean[] online = new boolean[pageCount];
			String[] onLineState = new String[pageCount];
			int[] rank = new int[pageCount];
			int[] playerId = new int[pageCount];
			int[] level = new int[pageCount];
			int[] todayContribution = new int[pageCount];
			int[] zsleve = new int[pageCount];
			// 分组时使用的临时参数
			List<String> onPlayerNameTemp = new ArrayList<String>();
			List<Integer> onPositionTemp = new ArrayList<Integer>();
			List<Integer> onConTemp = new ArrayList<Integer>();
			List<String> onTemp = new ArrayList<String>();
			List<Integer> onRankTemp = new ArrayList<Integer>();
			List<Integer> onplayerId = new ArrayList<Integer>();
			List<Integer> onlevel = new ArrayList<Integer>();
			List<Boolean> onlineTemp = new ArrayList<Boolean>();
			List<Integer> onTodayContribution = new ArrayList<Integer>();
			List<Integer> zslevelist = new ArrayList<Integer>();
			int index = 0;
			int idx = 0;
			for (PlayerSinConsortia psc : list) {
				if (ServiceManager.getManager().getPlayerService().playerIsOnline(psc.getPlayer().getId().intValue())) {
					onlineTemp.add(idx, true);
					onTemp.add(idx, TipMessages.ONLINE);
					onPlayerNameTemp.add(idx, psc.getPlayer().getName());
					onPositionTemp.add(idx, psc.getPosition());
					onConTemp.add(idx, psc.getContribute());
					onRankTemp.add(idx, index + 1);
					onplayerId.add(idx, psc.getPlayer().getId());
					onlevel.add(idx, psc.getPlayer().getLevel());
					onTodayContribution.add(idx, psc.getEverydayAdd());
					zslevelist.add(idx, psc.getPlayer().getZsLevel());
					idx++;
				} else {
					onlineTemp.add(false);
					long offTimeLong = System.currentTimeMillis() - psc.getPlayer().getUpdateTime().getTime();
					long day = 0;
					long hour = 0;
					String offTimeString = "";
					if (offTimeLong < 0) {
						day = 0;
						hour = 0;
					} else {
						day = offTimeLong / (Common.ONEDAYLONG);
						hour = (offTimeLong - day * Common.ONEDAYLONG) / (Common.ONEHOURLONG);
					}
					if (day != 0) {
						if (hour > 0) {
							day = day + 1;
						}
						offTimeString = TipMessages.OFFLINE + day + TipMessages.DAY;
					} else {
						offTimeString = TipMessages.OFFLINE + (hour + 1) + TipMessages.HOUR;
					}
					onTemp.add(offTimeString);
					onPlayerNameTemp.add(psc.getPlayer().getName());
					onPositionTemp.add(psc.getPosition());
					onConTemp.add(psc.getContribute());
					onRankTemp.add(index + 1);
					onplayerId.add(psc.getPlayer().getId());
					onlevel.add(psc.getPlayer().getLevel());
					onTodayContribution.add(psc.getEverydayAdd());
					zslevelist.add(psc.getPlayer().getZsLevel());
				}
				index++;
			}
			index = 0;
			for (int i = stratPagecount; i < endPagecount; i++) {
				online[index] = onlineTemp.get(i);
				playerName[index] = onPlayerNameTemp.get(i);
				position[index] = onPositionTemp.get(i);
				playerContribution[index] = onConTemp.get(i);
				onLineState[index] = onTemp.get(i);
				rank[index] = onRankTemp.get(i);
				playerId[index] = onplayerId.get(i);
				level[index] = onlevel.get(i);
				todayContribution[index] = onTodayContribution.get(i);
				zsleve[index] = zslevelist.get(i);
				index++;
			}
			sendCommunityMemberList.setPlayerName(playerName);
			sendCommunityMemberList.setPosition(position);
			sendCommunityMemberList.setPlayerContribution(playerContribution);
			sendCommunityMemberList.setOnLineState(onLineState);
			sendCommunityMemberList.setRank(rank);
			sendCommunityMemberList.setPlayerId(playerId);
			sendCommunityMemberList.setLevel(level);
			sendCommunityMemberList.setOnLine(online);
			sendCommunityMemberList.setPageNumber(pageNum);
			sendCommunityMemberList.setTotalNumber(totalSize);
			sendCommunityMemberList.setTodayContribution(todayContribution);
			sendCommunityMemberList.setZsleve(zsleve);
			session.write(sendCommunityMemberList);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_COMMUNITYMEMBERLIST_MESSAGE, data.getSerial(), data.getSessionId(),
					data.getType(), data.getSubType());
		}
	}
}