package com.wyd.empire.world.server.handler.friend;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.friend.GetCommunityFriendList;
import com.wyd.empire.protocol.data.friend.SendCommunityFriendList;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> GetFriendListHandler</code>Protocol.MAIL_GetFriendList获得好友列表协议处理
 * 
 * @since JDK 1.6
 */
public class GetCommunityFriendListHandler implements IDataHandler {
	private Logger log;

	public GetCommunityFriendListHandler() {
		this.log = Logger.getLogger(GetCommunityFriendListHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer myplayer = session.getPlayer(data.getSessionId());
		SendCommunityFriendList sendCommunityFriendList = new SendCommunityFriendList(data.getSessionId(), data.getSerial());
		GetCommunityFriendList getCommunityFriendList = (GetCommunityFriendList) data;
		try {
			int pageNum = getCommunityFriendList.getPageNumber();
			int pageSize = Common.PAGESIZE;// 页面单页条数
			int pageCount = Common.PAGESIZE;// 返回给客户端的条数
			int endPagecount = 0;
			int stratPagecount = 0;
			List<PlayerSinConsortia> list = ServiceManager.getManager().getPlayerSinConsortiaService()
					.getCommunityMembers(myplayer.getGuildId(), 1);
			if (list.size() == 0) {
				sendCommunityFriendList.setPlayerId(new int[0]);
				sendCommunityFriendList.setPlayerName(new String[0]);
				sendCommunityFriendList.setLevel(new int[0]);
				sendCommunityFriendList.setSex(new boolean[0]);
				sendCommunityFriendList.setOnline(new boolean[0]);
				sendCommunityFriendList.setPageNumber(0);
				sendCommunityFriendList.setPageCount(0);
				sendCommunityFriendList.setZsleve(new int[0]);
				session.write(sendCommunityFriendList);
			} else {
				// 分组时使用的临时参数
				List<Integer> friendIdList = new ArrayList<Integer>();
				List<Integer> zsleveList = new ArrayList<Integer>();
				List<String> friendNameList = new ArrayList<String>();
				List<Integer> levelList = new ArrayList<Integer>();
				List<Boolean> sexList = new ArrayList<Boolean>();
				List<Boolean> onlineList = new ArrayList<Boolean>();
				for (PlayerSinConsortia psc : list) {
					if (myplayer.getId() != psc.getPlayer().getId().intValue()) {
						if (ServiceManager.getManager().getPlayerService().playerIsOnline(psc.getPlayer().getId().intValue())) {
							friendIdList.add(0, psc.getPlayer().getId());
							zsleveList.add(0, psc.getPlayer().getZsLevel());
							friendNameList.add(0, psc.getPlayer().getName());
							levelList.add(0, psc.getPlayer().getLevel());
							sexList.add(0, psc.getPlayer().getSex() == 0 ? false : true);
							onlineList.add(0, true);
						} else {
							friendIdList.add(psc.getPlayer().getId());
							zsleveList.add(psc.getPlayer().getZsLevel());
							friendNameList.add(psc.getPlayer().getName());
							levelList.add(psc.getPlayer().getLevel());
							sexList.add(psc.getPlayer().getSex() == 0 ? false : true);
							onlineList.add(false);
						}
					}
				}

				int totalSize = (int) Math.ceil(onlineList.size() / (double) pageSize);
				stratPagecount = (pageNum - 1) * pageSize;
				endPagecount = pageNum * pageSize;
				endPagecount = endPagecount > onlineList.size() ? onlineList.size() : endPagecount;
				pageCount = endPagecount - stratPagecount;
				pageCount = pageCount > 0 ? pageCount : 0;

				int[] friendId = new int[pageCount];
				String[] friendName = new String[pageCount];
				int[] zsleve = new int[pageCount];
				int[] level = new int[pageCount];
				boolean[] sex = new boolean[pageCount];
				boolean[] online = new boolean[pageCount];
				int index = 0;
				for (int i = stratPagecount; i < endPagecount; i++) {
					online[index] = onlineList.get(i);
					friendId[index] = friendIdList.get(i);
					zsleve[index] = zsleveList.get(i);
					friendName[index] = friendNameList.get(i);
					level[index] = levelList.get(i);
					sex[index] = sexList.get(i);
					index++;
				}
				sendCommunityFriendList.setPlayerId(friendId);
				sendCommunityFriendList.setPlayerName(friendName);
				sendCommunityFriendList.setLevel(level);
				sendCommunityFriendList.setSex(sex);
				sendCommunityFriendList.setOnline(online);
				sendCommunityFriendList.setPageNumber(pageNum);
				sendCommunityFriendList.setPageCount(totalSize);
				sendCommunityFriendList.setZsleve(zsleve);
				session.write(sendCommunityFriendList);
			}
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.FRIEND_COMMUNITY_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}