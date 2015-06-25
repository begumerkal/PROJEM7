package com.wyd.empire.world.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.wyd.empire.protocol.data.chat.ReceiveMessage;
import com.wyd.empire.protocol.data.chat.SyncChannels;
import com.wyd.empire.protocol.data.server.BroadCast;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.entity.mysql.ChatRecord;
import com.wyd.empire.world.entity.mysql.Friend;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.model.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.ProtocolManager;
import com.wyd.session.Session;

/**
 * 聊天服务
 * 
 * @author zgq
 */
@Service
public class ChatService {
	Logger log = Logger.getLogger(ChatService.class);
	/** 世界频道 */
	public static final int CHANNEL_WOELD = 0; // 世界
	/** 当前频道 */
	public static final int CHANNEL_CURRENT = 1; // 当前
	/** 公会频道 */
	public static final int CHANNEL_GUILD = 2; // 公会
	/** 队伍频道 */
	public static final int CHANNEL_TEAM = 3; // 队伍
	/** 私聊频道 */
	public static final int CHANNEL_WHISPER = 4; // 私聊
	/** 系统频道 */
	public static final int CHANNEL_SYSTEM = 5; // 系统
	/** 彩聊频道 */
	public static final int CHANNEL_COLOR = 6; // 彩聊
	/** 允许所有频道聊天 */
	public static final int CHAT_STATUS1 = 1; // 允许所有频道聊天
	/** 禁止公共频道聊天 */
	public static final int CHAT_STATUS2 = 2; // 禁止公共频道聊天
	/** 禁止所有频道聊天 */
	public static final int CHAT_STATUS3 = 3; // 禁止所有频道聊天
	SimpleDateFormat timeSF = new SimpleDateFormat("MM-dd HH:mm");
	Vector<ChatRecord> chatRecordList = new Vector<ChatRecord>();
	/** 世界聊天频道 */
	public static final String CHAT_WORLD_CHANNEL = "WORLD";
	/** 公会聊天频道 */
	public static final String CHAT_GUILD_CHANNEL = "GUILD";
	/** 当前聊天频道 */
	public static final String CHAT_CURRENT_CHANNEL = "CURRENT";

	/**
	 * 初始化玩家聊天频道
	 * 
	 * @param player
	 *            玩家信息
	 */
	public void initPlayerChannels(WorldPlayer player) {
		player.setChannelId(0);
		if ((player.getGuildId() > 0)) {
			Vector<String> channels = new Vector<String>();
			channels.add(CHAT_GUILD_CHANNEL + "_" + player.getGuildId());
			String[] ret = new String[channels.size()];
			channels.toArray(ret);
			this.addToChannels(player.getId(), ret);
		}
	}

	/**
	 * 同步界面中的频道选项
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param oldMapId
	 *            旧界面ID
	 * @param newMapId
	 *            新界面ID
	 */
	public void changeMapChannel(WorldPlayer player, int oldMapId, int newMapId) {
		ConnectSession session = getPlayerSession(player.getId());
		if (session == null) {
			return;
		}
		SyncChannels sync = new SyncChannels();
		sync.setToSession(session.getPlayerSessionId(player.getId()));
		if (newMapId != 0 && newMapId != Common.GAME_INTERFACE_ROOM && newMapId != Common.GAME_INTERFACE_BATTLE) {
			if (newMapId != Common.GAME_INTERFACE_HALL) {
				sync.setAdd(new String[]{CHAT_CURRENT_CHANNEL + "_" + newMapId});
			} else {
				sync.setAdd(new String[]{CHAT_CURRENT_CHANNEL + "_" + newMapId + "" + 1});
			}
		} else {
			sync.setAdd(new String[0]);
		}
		if (oldMapId != 0) {
			if (oldMapId != Common.GAME_INTERFACE_HALL) {
				sync.setRemove(new String[]{CHAT_CURRENT_CHANNEL + "_" + oldMapId});
			} else {
				sync.setRemove(new String[]{CHAT_CURRENT_CHANNEL + "_" + oldMapId + "" + 1});
			}
		} else {
			sync.setRemove(new String[0]);
		}
		session.write(sync);
	}

	/**
	 * 将玩家加入到相关聊天频道中
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param channels
	 *            频道信息
	 */
	public void addToChannels(int playerId, String[] channels) {
		ConnectSession session = getPlayerSession(playerId);
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
	public void syncChannels(int playerId, String[] aChannels, String[] rChannels) {
		ConnectSession session = getPlayerSession(playerId);
		if (session == null) {
			return;
		}
		SyncChannels sync = new SyncChannels();
		sync.setToSession(session.getPlayerSessionId(playerId));
		sync.setAdd(aChannels);
		sync.setRemove(rChannels);
		session.write(sync);
	}

	/**
	 * 根据玩家ID获取玩家对应会话信息
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 会放信息
	 */
	private ConnectSession getPlayerSession(int playerId) {
		ConnectService connectService = ServiceManager.getManager().getConnectService();
		ConcurrentHashMap<Integer, Session> connectionSessions = connectService.getRegistry().getSessionID2Session();
		for (Session session : connectionSessions.values()) {
			ConnectSession connectSession = (ConnectSession) session;
			if (connectSession.getPlayerSessionId(playerId) != -1) {
				return connectSession;
			}
		}
		return null;
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
		receiveMessage.setTime(getTime());
		WorldPlayer receivePlayer;
		if (playerId > 0) {
			playerName = playerId + "";
			receivePlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
		} else {
			receivePlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerByName(playerName);
		}
		if (null == receivePlayer) {
			receiveMessage.setChannel(CHANNEL_WHISPER);
			receiveMessage.setSendName(TipMessages.SYSNAME_MESSAGE);
			receiveMessage.setReveId(player.getId());
			receiveMessage.setReveName(player.getName());
			receiveMessage.setMessage(TipMessages.PLAYER + playerName + ErrorMessages.CHAT_NOTONLINE);
			player.sendData(receiveMessage);
			return;
		}
		if (ChatService.CHAT_STATUS3 == player.getPlayer().getChatStatus()
				&& player.getPlayer().getProhibitTime().getTime() > System.currentTimeMillis()) {
			player.sendData(receiveMessage);
			return;
		}
		receiveMessage.setSendId(player.getId());
		receiveMessage.setReveId(receivePlayer.getId());
		receiveMessage.setReveName(receivePlayer.getName());
		player.sendData(receiveMessage);

		receivePlayer.sendData(receiveMessage);
	}

	/**
	 * 发送信息给世界频道内的玩家
	 * 
	 * @param receiveMessage
	 * @param player
	 */
	public void sendMessageToWorld(ReceiveMessage receiveMessage, WorldPlayer player) {
		receiveMessage.setTime(getTime());

		receiveMessage.setChannel(CHANNEL_SYSTEM);
		receiveMessage.setSendName(TipMessages.SYSNAME_MESSAGE);
		receiveMessage.setReveId(player.getId());
		receiveMessage.setReveName(player.getName());
		receiveMessage.setMessage(ErrorMessages.CHAT_LABA);
		player.sendData(receiveMessage);

		// ServiceManager.getManager().getTaskService().useSomething(player,
		// hornId, 1);
		// ServiceManager.getManager().getTitleService().useSomething(player,
		// hornId);
		// ServiceManager.getManager().getTaskService().chat(player, 1);
	}

	/**
	 * 发送信息给所有在线玩家
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
		receiveMessage.setTime(getTime());
		receiveMessage.setChatType(1);
		receiveMessage.setChatSubType(1);
		if (isColor) {
			receiveMessage.setChannel(ChatService.CHANNEL_COLOR);
			receiveMessage.setChatSubType(2);
		} else {
			receiveMessage.setChannel(ChatService.CHANNEL_SYSTEM);
		}
		receiveMessage.setSendName(TipMessages.SYSNAME_MESSAGE);
		if (null != receiveName && receiveName.length() > 0) {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getWorldPlayerByName(receiveName);
			if (null != player) {
				receiveMessage.setReveId(player.getId());
			}
		}
		receiveMessage.setMessage(content.replace("\r", "").replace("\n", ""));
		receiveMessage.setReveName(receiveName == null ? "" : receiveName);
		BroadCast broadCast = new BroadCast();
		broadCast.setChannel(CHAT_WORLD_CHANNEL);
		broadCast.setData(ProtocolManager.makeSegment(receiveMessage).getPacketByteArray());
		ServiceManager.getManager().getConnectService().broadcast(broadCast);
	}

	/**
	 * 发送结婚公告
	 * 
	 * @param content
	 *            公告内容
	 * @param receiveName
	 *            公告发送人名称
	 * @param isColor
	 *            是否彩色公告
	 */
	public void sendBulletinToWorld(String content, String receiveName, int marryType) {
		ReceiveMessage receiveMessage = new ReceiveMessage();
		receiveMessage.setTime(getTime());
		receiveMessage.setChannel(ChatService.CHANNEL_SYSTEM);
		receiveMessage.setSendName(TipMessages.SYSNAME_MESSAGE);
		if (null != receiveName && receiveName.length() > 0) {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getWorldPlayerByName(receiveName);
			if (null != player) {
				receiveMessage.setReveId(player.getId());
			}
		}
		content = content.replace(Common.MARRY_NOTICE, "");
		receiveMessage.setChatType(2);
		receiveMessage.setChatSubType(marryType);
		receiveMessage.setMessage(content.replace("\r", "").replace("\n", ""));
		receiveMessage.setReveName(receiveName == null ? "" : receiveName);
		BroadCast broadCast = new BroadCast();
		broadCast.setChannel(CHAT_WORLD_CHANNEL);
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
		receiveMessage.setTime(getTime());
		receiveMessage.setChannel(ChatService.CHANNEL_SYSTEM);
		receiveMessage.setSendName(TipMessages.SYSNAME_MESSAGE);
		receiveMessage.setReveName(worldPlayer.getName());
		receiveMessage.setMessage(content);
		worldPlayer.sendData(receiveMessage);
	}

	/**
	 * 发送信息给玩家当前所在界面的玩家
	 * 
	 * @param receiveMessage
	 * @param player
	 */
	public void sendMessageToCurrent(ReceiveMessage receiveMessage, WorldPlayer player) {
		// receiveMessage.setTime(getTime());
		// receiveMessage.setSendId(player.getId());
		// if (ChatService.CHAT_STATUS1 != player.getPlayer().getChatStatus()
		// && player.getPlayer().getProhibitTime().getTime() >
		// System.currentTimeMillis()) {
		// player.sendData(receiveMessage);
		// return;
		// }
		// if (player.getBattleId() != 0) {
		// if (player.getBattleId() > 0) {
		// BattleTeam battleTeam =
		// ServiceManager.getManager().getBattleTeamService().getBattleTeam(player.getBattleId());
		// if (null != battleTeam) {
		// for (Combat combat : battleTeam.getCombatList()) {
		// if (!combat.isRobot() && !combat.isLost() && null !=
		// combat.getPlayer()) {
		// combat.getPlayer().sendData(receiveMessage);
		// }
		// }
		// }
		// } else {
		// ServiceManager.getManager().getCrossService().sendMessage(player.getBattleId(),
		// receiveMessage);
		// }
		// } else if (player.getBossmapBattleId() > 0) {
		// BossBattleTeam battleTeam =
		// ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(player.getBossmapBattleId());
		// if (null != battleTeam) {
		// Vector<Combat> combatList = battleTeam.getCombatList();
		// for (Combat combat : combatList) {
		// if (!combat.isRobot() && !combat.isLost() && null !=
		// combat.getPlayer()) {
		// combat.getPlayer().sendData(receiveMessage);
		// }
		// }
		// }
		// } else if (player.getRoomId() > 0) {
		// Room room =
		// ServiceManager.getManager().getRoomService().getRoom(player.getRoomId());
		// if (null != room) {
		// Vector<Seat> seatList = room.getPlayerList();
		// for (Seat seat : seatList) {
		// if (null != seat.getPlayer() && !seat.isRobot()) {
		// seat.getPlayer().sendData(receiveMessage);
		// }
		// }
		// }
		// } else if (player.getBossmapRoomId() > 0) {
		// BossRoom bossmapRoom =
		// ServiceManager.getManager().getBossRoomService().getRoom(player.getBossmapRoomId());
		// if (null != bossmapRoom) {
		// List<BossSeat> seatList = bossmapRoom.getPlayerList();
		// for (BossSeat seat : seatList) {
		// if (null != seat.getPlayer()) {
		// seat.getPlayer().sendData(receiveMessage);
		// }
		// }
		// }
		// } else {
		// StringBuffer channelContent = new StringBuffer(CHAT_CURRENT_CHANNEL);
		// channelContent.append("_");
		// channelContent.append(player.getChannelId());
		// if (Common.GAME_INTERFACE_HALL == player.getChannelId()) {//
		// 玩家在游戏大厅做特殊处理
		// channelContent.append(player.getBattleChannel());
		// }
		// BroadCast broadCast = new BroadCast();
		// broadCast.setChannel(channelContent.toString());
		// broadCast.setData(ProtocolManager.makeSegment(receiveMessage).getPacketByteArray());
		// ServiceManager.getManager().getConnectService().broadcast(broadCast);
		// }
		// ServiceManager.getManager().getTaskService().chat(player, 2);
	}

	/**
	 * 发送信息给公会成员
	 * 
	 * @param receiveMessage
	 * @param player
	 */
	public void sendMessageToGuild(ReceiveMessage receiveMessage, WorldPlayer player) {
		receiveMessage.setTime(getTime());
		if (0 == player.getGuildId()) {
			receiveMessage.setChannel(CHANNEL_GUILD);
			receiveMessage.setSendName(TipMessages.SYSNAME_MESSAGE);
			receiveMessage.setReveId(player.getId());
			receiveMessage.setReveName(player.getName());
			receiveMessage.setMessage(ErrorMessages.CHAT_COMMUNITY);
			player.sendData(receiveMessage);
		} else {
			receiveMessage.setSendId(player.getId());
			if (ChatService.CHAT_STATUS1 != player.getPlayer().getChatStatus()
					&& player.getPlayer().getProhibitTime().getTime() > System.currentTimeMillis()) {
				player.sendData(receiveMessage);
				return;
			}
			BroadCast broadCast = new BroadCast();
			broadCast.setChannel(CHAT_GUILD_CHANNEL + "_" + player.getGuildId());
			broadCast.setData(ProtocolManager.makeSegment(receiveMessage).getPacketByteArray());
			ServiceManager.getManager().getConnectService().broadcast(broadCast);
		}
	}

	/**
	 * 发送信息给队友
	 * 
	 * @param receiveMessage
	 * @param player
	 */
	public void sendMessageToTeam(ReceiveMessage receiveMessage, WorldPlayer player) {
		if (player.getRoomId() > 0) {
			receiveMessage.setTime(getTime());
			receiveMessage.setSendId(player.getId());
			if (ChatService.CHAT_STATUS1 != player.getPlayer().getChatStatus()
					&& player.getPlayer().getProhibitTime().getTime() > System.currentTimeMillis()) {
				player.sendData(receiveMessage);
				return;
			}

		}
	}

	public String getTime() {
		return timeSF.format(new Date());
	}

	public void writeLog(Object message) {
		log.info(message);
	}

	public List<ChatRecord> getChatRecord(String key) {
		List<ChatRecord> newList = new ArrayList<ChatRecord>(chatRecordList);
		if (null != key && key.length() > 0) {
			ChatRecord chatRecord;
			for (int i = newList.size() - 1; i >= 0; i--) {
				chatRecord = newList.get(i);
				if (chatRecord.getSendName().indexOf(key) < 0 && chatRecord.getReveName().indexOf(key) < 0
						&& chatRecord.getMessage().indexOf(key) < 0) {
					newList.remove(i);
				}
			}
		}
		return newList;
	}

	public void addChatRecord(String sendName, String reveName, int channel, String message) {
		ChatRecord chatRecord = new ChatRecord();
		chatRecord.setSendName(sendName);
		chatRecord.setReveName(reveName);
		chatRecord.setChannel(channel);
		chatRecord.setMessage(message);
		chatRecord.setCreateTime(new Date());
		chatRecordList.add(chatRecord);
		while (chatRecordList.size() > 1000) {
			chatRecordList.remove(0);
		}
	}

	/**
	 * vip上线公告
	 * 
	 * @param player
	 */
	public void wellcomVIP(WorldPlayer player) {
		if (player.isVip() && player.getPlayer().getVipLevel() > 8) {
			String[] contents = TipMessages.VIPONLINEBULLETIN.split("&");
			String content = contents[ServiceUtils.getRandomNum(0, contents.length)];
			content = content.replace("***", player.getPlayer().getVipLevel().toString());
			content = content.replace("###", player.getName());
			sendBulletinToWorld(content, null, true);
		}
	}

	/**
	 * 世界BOSS开启公告 0提前五分钟 1准点
	 */
	public void openWorldBoss(int type) {
		ReceiveMessage receiveMessage = new ReceiveMessage();
		receiveMessage.setTime(getTime());
		receiveMessage.setChannel(ChatService.CHANNEL_SYSTEM);
		receiveMessage.setSendName(TipMessages.SYSNAME_MESSAGE);
		String content = TipMessages.WORLDBOSS_OPEN_NOTICE;
		if (type == 1) {
			content = TipMessages.WORLDBOSS_OPEN_NOTICE1;
		}
		// receiveMessage.setSendId(-3);//客户端识别为-3时开启世界BOSS入口
		receiveMessage.setChatType(3);
		receiveMessage.setChatSubType(1);
		receiveMessage.setMessage(content);
		receiveMessage.setReveName("");
		BroadCast broadCast = new BroadCast();
		broadCast.setChannel(CHAT_WORLD_CHANNEL);
		broadCast.setData(ProtocolManager.makeSegment(receiveMessage).getPacketByteArray());
		ServiceManager.getManager().getConnectService().broadcast(broadCast);
	}

	/**
	 * 世界BOSS关闭公告 内容：在大家的共同努力下世界boss被成功击杀，***成为了手刃boss的英雄!
	 */
	public void closeWorldBoss(String playerName) {
		ReceiveMessage receiveMessage = new ReceiveMessage();
		receiveMessage.setTime(getTime());
		receiveMessage.setChannel(ChatService.CHANNEL_SYSTEM);
		receiveMessage.setSendName(TipMessages.SYSNAME_MESSAGE);
		String content = TipMessages.WORLDBOSS_TIMEOUT_NOTICE;
		// 如果名字为空则使用另外一种公告
		if (playerName != null && !"".equals(playerName)) {
			content = TipMessages.WORLDBOSS_KILL_NOTICE;
			content = content.replace("{0}", playerName);
		}
		// receiveMessage.setSendId(-4);//客户端识别为-4时关闭世界BOSS
		receiveMessage.setChatType(3);
		receiveMessage.setChatSubType(2);
		receiveMessage.setMessage(content);
		receiveMessage.setReveName("");
		BroadCast broadCast = new BroadCast();
		broadCast.setChannel(CHAT_WORLD_CHANNEL);
		broadCast.setData(ProtocolManager.makeSegment(receiveMessage).getPacketByteArray());
		ServiceManager.getManager().getConnectService().broadcast(broadCast);
	}

	/**
	 * 世界BOSS关闭公告 内容：世界boss活动结束，经过顽强的抗争，BOSS留下一堆奖励，成功逃脱了围剿！
	 */
	public void closeWorldBoss() {
		closeWorldBoss(null);
	}

	/**
	 * 击杀世界BOSS伤害出输占比广播 内容：在击杀世界boss的活动中，***单人伤害达到了5%（10%、15%、20%），真是人中豪杰。
	 */
	public void hurtWorldBoss(String playerName, int proportion) {
		ReceiveMessage receiveMessage = new ReceiveMessage();
		receiveMessage.setTime(getTime());
		receiveMessage.setChannel(ChatService.CHANNEL_SYSTEM);
		receiveMessage.setSendName(TipMessages.SYSNAME_MESSAGE);
		String content = TipMessages.WORLDBOSS_HURT_NOTICE;
		content = content.replace("{0}", playerName);// 人名
		content = content.replace("{1}", proportion + "");// 占比
		// receiveMessage.setSendId(-2);//系统
		receiveMessage.setChatType(1);
		receiveMessage.setChatSubType(2);
		receiveMessage.setMessage(content);
		receiveMessage.setReveName("");
		BroadCast broadCast = new BroadCast();
		broadCast.setChannel(CHAT_WORLD_CHANNEL);
		broadCast.setData(ProtocolManager.makeSegment(receiveMessage).getPacketByteArray());
		ServiceManager.getManager().getConnectService().broadcast(broadCast);
	}

	/**
	 * 挑战赛开启公告
	 */
	public void openChallenge() {
		ReceiveMessage receiveMessage = new ReceiveMessage();
		receiveMessage.setTime(getTime());
		receiveMessage.setChannel(ChatService.CHANNEL_SYSTEM);
		receiveMessage.setSendName(TipMessages.SYSNAME_MESSAGE);
		String content = TipMessages.CHALLENGE_OPEN;
		// receiveMessage.setSendId(-5);//客户端识别为-5时开启挑战赛入口
		receiveMessage.setChatType(4);
		receiveMessage.setChatSubType(1);
		receiveMessage.setMessage(content);
		receiveMessage.setReveName("");
		BroadCast broadCast = new BroadCast();
		broadCast.setChannel(CHAT_WORLD_CHANNEL);
		broadCast.setData(ProtocolManager.makeSegment(receiveMessage).getPacketByteArray());
		ServiceManager.getManager().getConnectService().broadcast(broadCast);
	}

	/**
	 * 挑战赛关闭公告
	 */
	public void closeChallenge() {
		ReceiveMessage receiveMessage = new ReceiveMessage();
		receiveMessage.setTime(getTime());
		receiveMessage.setChannel(ChatService.CHANNEL_SYSTEM);
		receiveMessage.setSendName(TipMessages.SYSNAME_MESSAGE);
		String content = TipMessages.CHALLENGE_CLOSE;
		// receiveMessage.setSendId(-6);//客户端识别为-4时关闭挑战赛
		receiveMessage.setChatType(4);
		receiveMessage.setChatSubType(2);
		receiveMessage.setMessage(content);
		receiveMessage.setReveName("");
		BroadCast broadCast = new BroadCast();
		broadCast.setChannel(CHAT_WORLD_CHANNEL);
		broadCast.setData(ProtocolManager.makeSegment(receiveMessage).getPacketByteArray());
		ServiceManager.getManager().getConnectService().broadcast(broadCast);
	}

	/**
	 * 单人副本得到钻石 内容：恭喜%s在神秘之域中获取%d钻石！
	 */
	public void singleMap_GetDiamond(String playerName, int count) {
		ReceiveMessage receiveMessage = new ReceiveMessage();
		receiveMessage.setTime(getTime());
		receiveMessage.setChannel(ChatService.CHANNEL_SYSTEM);
		receiveMessage.setSendName(TipMessages.SYSNAME_MESSAGE);
		String content = TipMessages.SINGLEMAP_GETDIAM;
		content = content.replace("{0}", playerName);// 人名
		content = content.replace("{1}", count + "");// 个数
		// receiveMessage.setSendId(-2);//系统
		receiveMessage.setChatType(1);
		receiveMessage.setChatSubType(1);
		receiveMessage.setMessage(content);
		receiveMessage.setReveName("");
		BroadCast broadCast = new BroadCast();
		broadCast.setChannel(CHAT_WORLD_CHANNEL);
		broadCast.setData(ProtocolManager.makeSegment(receiveMessage).getPacketByteArray());
		ServiceManager.getManager().getConnectService().broadcast(broadCast);
	}

}
