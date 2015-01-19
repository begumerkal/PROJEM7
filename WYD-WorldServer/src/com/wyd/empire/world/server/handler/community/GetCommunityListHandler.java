package com.wyd.empire.world.server.handler.community;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.db.page.PageList;
import com.wyd.empire.protocol.data.community.GetCommunityList;
import com.wyd.empire.protocol.data.community.SendCommunityList;
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
public class GetCommunityListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetCommunityListHandler.class);

	public GetCommunityListHandler() {
		this.log = Logger.getLogger(GetCommunityListHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		GetCommunityList getCommunityList = (GetCommunityList) data;
		SendCommunityList sendCommunityList = new SendCommunityList(data.getSessionId(), data.getSerial());
		try {
			int pageNum = getCommunityList.getPageNumber();
			int consortiaCount = 0;
			if (null != player && player.getGuildId() > 0 && pageNum == 1) {
				// 获得当前玩家所在公会
				Consortia con = ServiceManager.getManager().getConsortiaService().getConsortiaByPlayerId(player.getId());
				// 获得公会列表
				PageList pl = ServiceManager.getManager().getConsortiaService().getConsortiaList(pageNum, 0);
				List<Object> list = pl.getList();
				if (list != null) {
					consortiaCount = list.size();
				}
				int[] communityId = new int[consortiaCount + 1];
				String[] communityName = new String[consortiaCount + 1];
				int[] level = new int[consortiaCount + 1];
				int[] prestige = new int[consortiaCount + 1];
				int[] rank = new int[consortiaCount + 1];

				communityId[0] = con.getId();
				communityName[0] = con.getName();
				level[0] = con.getLevel();
				prestige[0] = con.getPrestige();
				Consortia consortia;
				for (int i = 0; i < consortiaCount; i++) {
					consortia = (Consortia) list.get(i);
					if (con.getId().intValue() == consortia.getId().intValue()) {
						rank[0] = i + 1;
					}
					communityId[i + 1] = consortia.getId();
					communityName[i + 1] = consortia.getName();
					level[i + 1] = consortia.getLevel();
					prestige[i + 1] = consortia.getPrestige();
					rank[i + 1] = ((pageNum - 1) * Common.PAGESIZE) + (i + 1);
				}
				if (rank[0] == 0) {
					rank[0] = ServiceManager.getManager().getConsortiaService().getRankNum(player.getGuildId(), 0);
				}
				sendCommunityList.setCommunityId(communityId);
				sendCommunityList.setCommunityName(communityName);
				sendCommunityList.setLevel(level);
				sendCommunityList.setPrestige(prestige);
				sendCommunityList.setRank(rank);
				sendCommunityList.setIsCommunityMember(true);
				sendCommunityList.setPageNumber(pl.getPageIndex() + 1);
				sendCommunityList.setTotalNumber(pl.getTotalSize());
				session.write(sendCommunityList);
			} else {
				PageList pl = ServiceManager.getManager().getConsortiaService().getConsortiaList(pageNum, 0);
				List<Object> list = pl.getList();
				consortiaCount = 0;
				if (list != null) {
					consortiaCount = list.size();
				}
				int[] communityId = new int[consortiaCount];
				String[] communityName = new String[consortiaCount];
				int[] level = new int[consortiaCount];
				int[] prestige = new int[consortiaCount];
				int[] rank = new int[consortiaCount];

				Consortia consortia;
				for (int i = 0; i < consortiaCount; i++) {
					consortia = (Consortia) list.get(i);
					communityId[i] = consortia.getId();
					communityName[i] = consortia.getName();
					level[i] = consortia.getLevel();
					prestige[i] = consortia.getPrestige();
					rank[i] = ((pageNum - 1) * Common.PAGESIZE) + (i + 1);
				}
				sendCommunityList.setCommunityId(communityId);
				sendCommunityList.setCommunityName(communityName);
				sendCommunityList.setLevel(level);
				sendCommunityList.setPrestige(prestige);
				sendCommunityList.setRank(rank);
				if (null != player && player.getGuildId() > 0) {
					sendCommunityList.setIsCommunityMember(true);
				} else {
					sendCommunityList.setIsCommunityMember(false);
				}
				sendCommunityList.setPageNumber(pl.getPageIndex() + 1);
				sendCommunityList.setTotalNumber(pl.getTotalSize());
				session.write(sendCommunityList);
			}
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_COMMUNITYLIST_MESSAGE, data.getSerial(), data.getSessionId(),
					data.getType(), data.getSubType());
		}
	}
}