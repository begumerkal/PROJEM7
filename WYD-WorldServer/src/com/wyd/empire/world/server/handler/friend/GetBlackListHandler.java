package com.wyd.empire.world.server.handler.friend;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.friend.GetBlackList;
import com.wyd.empire.protocol.data.friend.SendBlackList;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
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
public class GetBlackListHandler implements IDataHandler {
	private Logger log;

	public GetBlackListHandler() {
		this.log = Logger.getLogger(GetBlackListHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer myplayer = session.getPlayer(data.getSessionId());
		GetBlackList getBlackList = (GetBlackList) data;
		try {
			int pageNum = getBlackList.getPageNumber();
			int pageSize = Common.PAGESIZE;// 页面单页条数
			SendBlackList sendBlackList = new SendBlackList(data.getSessionId(), data.getSerial());
			result(myplayer.getId(), pageNum, pageSize, sendBlackList);
			session.write(sendBlackList);
			// 返回好友列表
			new GetFriendListHandler().sendFriendList(myplayer);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.FRIEND_GETBLACKLIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	private void result(int playerId, int pageNum, int pageSize, SendBlackList sendBlackList) {
		int pageCount = Common.PAGESIZE;// 返回给客户端的条数
		int endPagecount = 0;
		int stratPagecount = 0;
		List<Player> list = ServiceManager.getManager().getFriendService().getBlackList(playerId);
		int totalSize = (int) Math.ceil(list.size() / (double) pageSize);
		pageNum = pageNum > totalSize && list.size() != 0 ? totalSize : pageNum;
		stratPagecount = (pageNum - 1) * pageSize;
		endPagecount = pageNum * pageSize;
		endPagecount = endPagecount > list.size() ? list.size() : endPagecount;
		pageCount = endPagecount - stratPagecount;
		pageCount = pageCount > 0 ? pageCount : 0;
		int[] friendId = new int[pageCount];
		int[] zsleve = new int[pageCount];
		String[] fighting = new String[pageCount];
		String[] community = new String[pageCount];
		String[] friendName = new String[pageCount];
		int[] level = new int[pageCount];
		boolean[] sex = new boolean[pageCount];
		boolean[] online = new boolean[pageCount];
		List<Integer> friendIdList = new ArrayList<Integer>();
		List<Integer> zsleveList = new ArrayList<Integer>();
		List<String> friendNameList = new ArrayList<String>();
		List<Integer> levelList = new ArrayList<Integer>();
		List<Boolean> sexList = new ArrayList<Boolean>();
		List<Boolean> onlineList = new ArrayList<Boolean>();
		for (Player player : list) {
			if (ServiceManager.getManager().getPlayerService().playerIsOnline(player.getId().intValue())) {
				friendIdList.add(0, player.getId());
				zsleveList.add(0, player.getZsLevel());
				friendNameList.add(0, player.getName());
				levelList.add(0, player.getLevel());
				sexList.add(0, player.getSex().intValue() == 0 ? false : true);
				onlineList.add(0, true);
			} else {
				friendIdList.add(player.getId());
				zsleveList.add(player.getZsLevel());
				friendNameList.add(player.getName());
				levelList.add(player.getLevel());
				sexList.add(player.getSex().intValue() == 0 ? false : true);
				onlineList.add(false);
			}
		}
		int index = 0;
		for (int i = stratPagecount; i < endPagecount; i++) {
			friendId[index] = friendIdList.get(i);
			zsleve[index] = zsleveList.get(i);
			friendName[index] = friendNameList.get(i);
			level[index] = levelList.get(i);
			sex[index] = sexList.get(i);
			online[index] = onlineList.get(i);
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getLoadPlayer(friendIdList.get(i));
			if (null != player) {
				fighting[index] = player.getFighting() + "";
				community[index] = player.getGuildName().length() > 0 ? player.getGuildName() : TipMessages.NULL_STRING;
			} else {
				fighting[index] = "---";
				community[index] = "------";
			}
			index++;
		}

		sendBlackList.setPlayerId(friendId);
		sendBlackList.setPlayerName(friendName);
		sendBlackList.setLevel(level);
		sendBlackList.setSex(sex);
		sendBlackList.setOnline(online);
		sendBlackList.setPageNumber(pageNum);
		sendBlackList.setPageCount(totalSize);
		sendBlackList.setZsleve(zsleve);
		sendBlackList.setFighting(fighting);
		sendBlackList.setCommunity(community);
	}

	public void sendBlackList(WorldPlayer player) {
		SendBlackList sendBlackList = new SendBlackList();
		result(player.getId(), 1, 1000, sendBlackList);
		player.sendData(sendBlackList);
	}
}