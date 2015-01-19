package com.wyd.empire.world.server.handler.chat;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.chat.GetSpeakerNum;
import com.wyd.empire.protocol.data.chat.ReceiveMessage;
import com.wyd.empire.protocol.data.chat.SendMessage;
import com.wyd.empire.world.common.util.KeywordsUtil;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.ChatService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 转发聊天信息
 * 
 * @author Administrator
 */
public class SendMessageHandler implements IDataHandler {
	Logger log = Logger.getLogger(SendMessageHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		SendMessage sendMessage = (SendMessage) data;
		try {
			long nowTime = System.currentTimeMillis();
			if ((nowTime - player.getLastSendMsgTime()) > 2000l) {
				player.setLastSendMsgCount(0);
			} else {
				player.setLastSendMsgCount(player.getLastSendMsgCount() + 1);
			}
			if (player.getLastSendMsgCount() > 3) {
				GetSpeakerNumHandler getSpeakerNumHandler = new GetSpeakerNumHandler();
				GetSpeakerNum getSpeakerNum = new GetSpeakerNum(data.getSessionId(), data.getSerial());
				getSpeakerNum.setHandlerSource(sendMessage.getHandlerSource());
				getSpeakerNum.setSource(sendMessage.getSource());
				getSpeakerNum.setPlayerId(player.getId());
				getSpeakerNumHandler.handle(getSpeakerNum);
				throw new ProtocolException(ErrorMessages.CHAT_OFTEN_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			player.setLastSendMsgTime(nowTime);
			String message = KeywordsUtil.filterKeywords(sendMessage.getMessage());
			ReceiveMessage receiveMessage = new ReceiveMessage();
			receiveMessage.setChannel(sendMessage.getChannel());
			receiveMessage.setSendName(player.getName());
			receiveMessage.setReveName(sendMessage.getPlayerName());
			receiveMessage.setMessage(message);
			receiveMessage.setChatType(0);
			receiveMessage.setChatSubType(1);
			// 发送玩家的vip等级
			if (player.isVip()) {
				receiveMessage.setVipLevel(player.getPlayer().getVipLevel());
			}
			String channel = null;
			switch (sendMessage.getChannel()) {
				case ChatService.CHANNEL_COLOR :
					channel = "世界";
					receiveMessage.setChatSubType(2);
					ServiceManager.getManager().getChatService().sendMessageToWorld(receiveMessage, player);
					break;
				case ChatService.CHANNEL_WOELD :
					channel = "世界";
					ServiceManager.getManager().getChatService().sendMessageToWorld(receiveMessage, player);
					break;
				case ChatService.CHANNEL_CURRENT :
					channel = "当前";
					ServiceManager.getManager().getChatService().sendMessageToCurrent(receiveMessage, player);
					break;
				case ChatService.CHANNEL_GUILD :
					channel = "公会";
					ServiceManager.getManager().getChatService().sendMessageToGuild(receiveMessage, player);
					break;
				case ChatService.CHANNEL_TEAM :
					channel = "队伍";
					ServiceManager.getManager().getChatService().sendMessageToTeam(receiveMessage, player);
					break;
				case ChatService.CHANNEL_WHISPER :
					channel = "私聊";
					ServiceManager.getManager().getChatService()
							.sendMessageToPlayer(receiveMessage, player, sendMessage.getPlayerName(), sendMessage.getPlayerId());
					break;
			}
			StringBuffer sbf = new StringBuffer();
			sbf.append("发送人:");
			sbf.append(player.getName());
			sbf.append("---");
			sbf.append("接收人:");
			sbf.append(sendMessage.getPlayerName());
			sbf.append("---");
			sbf.append("接收频道:");
			sbf.append(channel);
			sbf.append("---");
			sbf.append("信息内容:");
			sbf.append(sendMessage.getMessage());
			ServiceManager.getManager().getChatService().writeLog(sbf);
			ServiceManager.getManager().getChatService()
					.addChatRecord(player.getName(), sendMessage.getPlayerName(), sendMessage.getChannel(), sendMessage.getMessage());
			// 记录日志
			int receiveLevel = 0;
			if (receiveMessage.getReveId() > 0) {
				WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getLoadPlayer(receiveMessage.getReveId());
				if (null != worldPlayer) {
					receiveLevel = worldPlayer.getLevel();
				}
			}
			GameLogService.chart(receiveMessage.getSendId(), player.getLevel(), receiveMessage.getReveId(), receiveLevel, channel,
					player.getChannelId(), sendMessage.getMessage());
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
