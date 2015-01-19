package com.wyd.empire.world.server.handler.community;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.db.page.PageList;
import com.wyd.empire.protocol.data.community.GetSituationList;
import com.wyd.empire.protocol.data.community.SendSituationList;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> GetCommunityListHandler</code>Protocol.COMMUNITY
 * _GetCommunityList获得工会列表协议处理
 * 
 * @since JDK 1.6
 */
public class GetSituationListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetSituationListHandler.class);

	public GetSituationListHandler() {
		this.log = Logger.getLogger(GetSituationListHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		GetSituationList getSituationList = (GetSituationList) data;
		SendSituationList sendSituationList = new SendSituationList(data.getSessionId(), data.getSerial());
		try {
			int pageNum = getSituationList.getPageNumber();
			int consortiaCount = 0;
			if (player.getGuildId() > 0 && pageNum == 1) {
				// 获得当前玩家所在公会
				Consortia con = ServiceManager.getManager().getConsortiaService().getConsortiaByPlayerId(player.getId());
				// 获得公会列表
				PageList pl = ServiceManager.getManager().getConsortiaService().getConsortiaList(pageNum, 1);
				List<Object> list = pl.getList();
				if (list != null) {
					consortiaCount = list.size();
				}
				int[] communityId = new int[consortiaCount + 1];
				String[] communityName = new String[consortiaCount + 1];
				int[] winOdds = new int[consortiaCount + 1];
				int[] winNumber = new int[consortiaCount + 1];
				int[] rank = new int[consortiaCount + 1];

				communityId[0] = con.getId();
				communityName[0] = con.getName();
				if (con.getTotalNum() != 0) {
					winOdds[0] = con.getWinNum() * 10000 / con.getTotalNum();
				} else {
					winOdds[0] = 0;
				}

				winNumber[0] = con.getWinNum();
				Consortia consortia;
				for (int i = 0; i < consortiaCount; i++) {
					consortia = (Consortia) list.get(i);
					if (con.getId().intValue() == consortia.getId().intValue()) {
						rank[0] = i + 1;
					}
					communityId[i + 1] = consortia.getId();
					communityName[i + 1] = consortia.getName();
					winOdds[i + 1] = 0;
					if (consortia.getTotalNum() != 0) {
						winOdds[i + 1] = consortia.getWinNum() * 10000 / consortia.getTotalNum();
					}
					winNumber[i + 1] = consortia.getWinNum();
					rank[i + 1] = ((pageNum - 1) * Common.PAGESIZE) + (i + 1);
				}
				if (rank[0] == 0) {
					rank[0] = ServiceManager.getManager().getConsortiaService().getRankNum(player.getGuildId(), 1);
				}
				sendSituationList.setCommunityId(communityId);
				sendSituationList.setCommunityName(communityName);
				sendSituationList.setWinOdds(winOdds);
				sendSituationList.setWinNumber(winNumber);
				sendSituationList.setRank(rank);
				sendSituationList.setPageNumber(pl.getPageIndex() + 1);
				sendSituationList.setTotalNumber(pl.getTotalSize());
				session.write(sendSituationList);
			} else {
				PageList pl = ServiceManager.getManager().getConsortiaService().getConsortiaList(pageNum, 1);
				List<Object> list = pl.getList();
				consortiaCount = 0;
				if (list != null) {
					consortiaCount = list.size();
				}
				int[] communityId = new int[consortiaCount];
				String[] communityName = new String[consortiaCount];
				int[] winOdds = new int[consortiaCount];
				int[] winNumber = new int[consortiaCount];
				int[] rank = new int[consortiaCount];

				Consortia consortia;
				for (int i = 0; i < consortiaCount; i++) {
					consortia = (Consortia) list.get(i);
					communityId[i] = consortia.getId();
					communityName[i] = consortia.getName();
					winOdds[i] = 0;
					if (consortia.getTotalNum() != 0) {
						winOdds[i] = consortia.getWinNum() * 10000 / consortia.getTotalNum();
					}
					winNumber[i] = consortia.getWinNum();
					rank[i] = ((pageNum - 1) * Common.PAGESIZE) + (i + 1);
				}
				sendSituationList.setCommunityId(communityId);
				sendSituationList.setCommunityName(communityName);
				sendSituationList.setWinOdds(winOdds);
				sendSituationList.setWinNumber(winNumber);
				sendSituationList.setRank(rank);
				sendSituationList.setPageNumber(pl.getPageIndex() + 1);
				sendSituationList.setTotalNumber(pl.getTotalSize());
				session.write(sendSituationList);
			}
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_COMMUNITYLIST_MESSAGE, data.getSerial(), data.getSessionId(),
					data.getType(), data.getSubType());
		}
	}
}