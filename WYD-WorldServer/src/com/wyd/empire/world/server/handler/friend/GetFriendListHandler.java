package com.wyd.empire.world.server.handler.friend;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.friend.GetFriendList;
import com.wyd.empire.protocol.data.friend.SendFriendList;
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
public class GetFriendListHandler implements IDataHandler {
	private Logger log;
	/** 记录Handler处理时间日志 */
	private static Logger timeLog = Logger.getLogger("handlerTimeLog");

	public GetFriendListHandler() {
		this.log = Logger.getLogger(GetFriendListHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer myplayer = session.getPlayer(data.getSessionId());
		GetFriendList getFriendList = (GetFriendList) data;
		try {
			int pageNum = getFriendList.getPageNumber();
			int needsex = getFriendList.getSex();
			int pageSize = Common.PAGESIZE;// 页面单页条数
			SendFriendList sendFriendList = new SendFriendList(data.getSessionId(), data.getSerial());
			result(myplayer.getId(), needsex, pageNum, pageSize, sendFriendList);
			session.write(sendFriendList);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.FRIEND_FRIENDLIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	private void result(int playerId, int sex, int pageNum, int pageSize, SendFriendList sendFriendList) {
		int endPagecount = 0;
		int stratPagecount = 0;
		int pageCount = Common.PAGESIZE;// 返回给客户端的条数
		List<Player> list = ServiceManager.getManager().getFriendService().getFriendList(playerId, sex);
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
		boolean[] sexs = new boolean[pageCount];
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
			sexs[index] = sexList.get(i);
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

		sendFriendList.setPlayerId(friendId);
		sendFriendList.setPlayerName(friendName);
		sendFriendList.setLevel(level);
		sendFriendList.setSex(sexs);
		sendFriendList.setOnline(online);
		sendFriendList.setPageNumber(pageNum);
		sendFriendList.setPageCount(totalSize);
		sendFriendList.setZsleve(zsleve);
		sendFriendList.setFighting(fighting);
		sendFriendList.setCommunity(community);
		sendFriendList.setFriendCount(ServiceManager.getManager().getFriendService().getFriendNum(playerId));
		sendFriendList.setMaxCount(ServiceManager.getManager().getVersionService().getMaxFriendCount());
	}

	public void sendFriendList(WorldPlayer player) {
		SendFriendList sendFriendList = new SendFriendList();
		result(player.getId(), -1, 1, 1000, sendFriendList);
		player.sendData(sendFriendList);
	}

	/**
	 * 通知好友我上线/离线
	 * 
	 * @param fansList
	 */
	public void notifyFans(WorldPlayer player) {
		try {
			int onlineFans = 0;
			long sTime = System.currentTimeMillis();
			List<Player> fansList = ServiceManager.getManager().getFriendService().getFansList(player.getId());
			for (Player fans : fansList) {
				WorldPlayer worldFans = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(fans.getId());
				if (worldFans != null) {
					sendFriendList(worldFans);
					onlineFans++;
				}
			}
			long time = System.currentTimeMillis() - sTime;
			if (time > 100) {
				timeLog.info("[上下线通知] id:" + player.getId() + " totalFans:" + fansList.size() + " onlineFans:" + onlineFans + " time:"
						+ time);
				System.out.println("[上下线通知] id:" + player.getId() + " totalFans:" + fansList.size() + " onlineFans:" + onlineFans
						+ " time:" + time);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}