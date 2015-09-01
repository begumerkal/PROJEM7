package com.app.empire.world.service.base.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.app.empire.protocol.data.chat.ReceiveMessage;
import com.app.empire.protocol.data.chat.SyncChannels;
import com.app.empire.protocol.data.server.BroadCast;
import com.app.empire.world.common.util.Common;
import com.app.empire.world.common.util.DateUtil;
import com.app.empire.world.exception.ErrorMessages;
import com.app.empire.world.exception.TipMessages;
import com.app.empire.world.model.player.WorldPlayer;
import com.app.empire.world.service.factory.ServiceManager;
import com.app.empire.world.service.impl.ConnectService;
import com.app.empire.world.session.ConnectSession;
import com.app.protocol.ProtocolManager;
import com.app.session.Session;

/**
 * 聊天服务
 * 
 * @author doter
 */
@Service
public class ChatService {
	Logger log = Logger.getLogger(ChatService.class);

	/**
	 * 初始化玩家聊天频道(公会，deng
	 * 
	 * @param player
	 *            玩家信息
	 */
	public void initPlayerChannels(ConnectSession session, WorldPlayer player) {
		int guildId = player.getPlayer().getGuildId();
		if ((guildId > 0)) {
			Vector<String> channels = new Vector<String>();
			channels.add(Common.CHAT_GUILD_CHANNEL + "_" + guildId);
			String[] ret = new String[channels.size()];
			channels.toArray(ret);
			this.addToChannels(session, player.getPlayer().getId(), ret);
		}
	}

	/**
	 * 将玩家加入到相关聊天频道中
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param channels
	 *            频道信息
	 */
	public void addToChannels(ConnectSession session, int playerId, String[] channels) {
		if (session == null)
			return;
		SyncChannels sync = new SyncChannels();
		sync.setToSession(session.getPlayerSessionId(playerId));
		sync.setAdd(channels);
		sync.setRemove(new String[0]);
		session.write(sync);
	}

	/**
	 * 同步玩家频道设置
	 * 
	 * @param playerId
	 *            玩家信息
	 * @param aChannels
	 * @param rChannels
	 */
	public void syncChannels(ConnectSession session, int playerId, String[] addChannels, String[] removeChannels) {
		if (session == null)
			return;
		SyncChannels sync = new SyncChannels();
		sync.setToSession(session.getPlayerSessionId(playerId));
		sync.setAdd(addChannels);
		sync.setRemove(removeChannels);
		session.write(sync);
	}

	/**
	 * 发送信息给指定玩家
	 *
	 * @param receiveMessage
	 * @param player
	 *            信息发送玩家
	 * @param playerName
	 *            信息目标玩家名称
	 */
	public void sendMessageToPlayer(ReceiveMessage receiveMessage, WorldPlayer player, String playerName, int playerId) {
		receiveMessage.setTime(DateUtil.format(new Date()));
		WorldPlayer receivePlayer = ServiceManager.getManager().getPlayerService().getPlayer(playerId);
		if (receivePlayer == null) {
			receiveMessage.setChannel(Common.CHAT_WHISPER);
			receiveMessage.setSendName(TipMessages.SYSNAME_MESSAGE);
			receiveMessage.setReveId(player.getPlayer().getId());
			receiveMessage.setReveName(player.getName());
			receiveMessage.setMessage(TipMessages.PLAYER + playerName + ErrorMessages.CHAT_NOTONLINE);
			player.sendData(receiveMessage);
			return;
		}

		Date gagEndTime = player.getPlayer().getGagEndTime();
		if (gagEndTime != null && gagEndTime.getTime() > System.currentTimeMillis()) {
			// player.sendData(receiveMessage);
			return;
		}
		receiveMessage.setSendId(player.getPlayer().getId());
		receiveMessage.setReveId(receivePlayer.getPlayer().getId());
		receiveMessage.setReveName(receivePlayer.getPlayer().getNickname());
		// player.sendData(receiveMessage);
		receivePlayer.sendData(receiveMessage);
	}
	/**
	 * 发送信息给世界频道内的所有玩家
	 *
	 * @param receiveMessage
	 * @param player
	 */
	public void sendMessageToWorld(ReceiveMessage receiveMessage, WorldPlayer player) {
		receiveMessage.setTime(DateUtil.format(new Date()));
		receiveMessage.setChannel(Common.CHAT_WORLD);
		receiveMessage.setSendName(TipMessages.SYSNAME_MESSAGE);
		receiveMessage.setReveId(player.getPlayer().getId());
		receiveMessage.setReveName(player.getName());
		receiveMessage.setMessage(ErrorMessages.CHAT_LABA);
		player.sendData(receiveMessage);

		Date gagEndTime = player.getPlayer().getGagEndTime();
		if (gagEndTime != null && gagEndTime.getTime() > System.currentTimeMillis()) {
			// receiveMessage.setChannel(Common.CHAT_SYSTEM);
			// receiveMessage.setSendName(TipMessages.SYSNAME_MESSAGE);
			// receiveMessage.setReveId(player.getPlayer().getId());
			// receiveMessage.setReveName(player.getName());
			// receiveMessage.setMessage(ErrorMessages.CHAT_LABA);
			// player.sendData(receiveMessage);
			return;
		} else {
			receiveMessage.setSendId(player.getPlayer().getId());
			BroadCast broadCast = new BroadCast();
			broadCast.setChannel(Common.CHAT_WORLD_CHANNEL);
			broadCast.setData(ProtocolManager.makeSegment(receiveMessage).getPacketByteArray());
			ServiceManager.getManager().getConnectService().broadcast(broadCast);
		}
	}

	/**
	 * 发送公告信息给所有在线玩家
	 *
	 * @param content
	 *            公告内容
	 * @param receiveName
	 *            公告发送人名称
	 * @param isColor
	 *            是否彩色公告
	 */
	public void sendBulletinToWorld(String content, String receiveName, boolean isColor) {
		ReceiveMessage receiveMessage = new ReceiveMessage();
		receiveMessage.setTime(DateUtil.format(new Date()));
		receiveMessage.setChatType(1);
		receiveMessage.setChatSubType(1);
		receiveMessage.setSendName(TipMessages.SYSNAME_MESSAGE);
		receiveMessage.setReveId(0);
		receiveMessage.setMessage(content.replace("\r", "").replace("\n", ""));
		receiveMessage.setReveName(receiveName == null ? "" : receiveName);
		BroadCast broadCast = new BroadCast();
		broadCast.setChannel(Common.CHAT_WORLD_CHANNEL);
		broadCast.setData(ProtocolManager.makeSegment(receiveMessage).getPacketByteArray());
		ServiceManager.getManager().getConnectService().broadcast(broadCast);
	}

	/**
	 * 发送系统信息给指定玩家
	 *
	 * @param worldPlayer
	 * @param content
	 */
	public void sendSystemMessage(WorldPlayer worldPlayer, String content) {
		ReceiveMessage receiveMessage = new ReceiveMessage();
		receiveMessage.setChatType(0);
		receiveMessage.setChatSubType(1);
		receiveMessage.setTime(DateUtil.format(new Date()));
		receiveMessage.setSendName(TipMessages.SYSNAME_MESSAGE);
		receiveMessage.setReveName(worldPlayer.getName());
		receiveMessage.setMessage(content);
		worldPlayer.sendData(receiveMessage);
	}
	//
	// /**
	// * 发送信息给公会成员
	// *
	// * @param receiveMessage
	// * @param player
	// */
	// public void sendMessageToGuild(ReceiveMessage receiveMessage, WorldPlayer
	// player) {
	// receiveMessage.setTime(getTime());
	// if (0 == player.getGuildId()) {
	// receiveMessage.setChannel(CHANNEL_GUILD);
	// receiveMessage.setSendName(TipMessages.SYSNAME_MESSAGE);
	// receiveMessage.setReveId(player.getId());
	// receiveMessage.setReveName(player.getName());
	// receiveMessage.setMessage(ErrorMessages.CHAT_COMMUNITY);
	// player.sendData(receiveMessage);
	// } else {
	// receiveMessage.setSendId(player.getId());
	// if (ChatService.CHAT_STATUS1 != player.getPlayer().getChatStatus()
	// && player.getPlayer().getProhibitTime().getTime() >
	// System.currentTimeMillis()) {
	// player.sendData(receiveMessage);
	// return;
	// }
	// BroadCast broadCast = new BroadCast();
	// broadCast.setChannel(CHAT_GUILD_CHANNEL + "_" + player.getGuildId());
	// broadCast.setData(ProtocolManager.makeSegment(receiveMessage).getPacketByteArray());
	// ServiceManager.getManager().getConnectService().broadcast(broadCast);
	// }
	// }
	//
	// /**
	// * 发送信息给队友
	// *
	// * @param receiveMessage
	// * @param player
	// */
	// public void sendMessageToTeam(ReceiveMessage receiveMessage, WorldPlayer
	// player) {
	// if (player.getRoomId() > 0) {
	// receiveMessage.setTime(getTime());
	// receiveMessage.setSendId(player.getId());
	// if (ChatService.CHAT_STATUS1 != player.getPlayer().getChatStatus()
	// && player.getPlayer().getProhibitTime().getTime() >
	// System.currentTimeMillis()) {
	// player.sendData(receiveMessage);
	// return;
	// }
	//
	// }
	// }
	//
	// public String getTime() {
	// return timeSF.format(new Date());
	// }
	//
	// public void writeLog(Object message) {
	// log.info(message);
	// }
	//
	//
	//
	// /**
	// * vip上线公告
	// *
	// * @param player
	// */
	// public void wellcomVIP(WorldPlayer player) {
	// if (player.isVip() && player.getPlayer().getVipLevel() > 8) {
	// String[] contents = TipMessages.VIPONLINEBULLETIN.split("&");
	// String content = contents[ServiceUtils.getRandomNum(0, contents.length)];
	// content = content.replace("***",
	// player.getPlayer().getVipLevel().toString());
	// content = content.replace("###", player.getName());
	// sendBulletinToWorld(content, null, true);
	// }
	// }
	//

}
