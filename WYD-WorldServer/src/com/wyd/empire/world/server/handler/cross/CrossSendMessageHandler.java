package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.chat.ReceiveMessage;
import com.wyd.empire.protocol.data.cross.CrossSendMessage;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 跨服对战聊天
 * 
 * @author zguoqiu
 */
public class CrossSendMessageHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossSendMessageHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossSendMessage crossSendMessage = (CrossSendMessage) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(crossSendMessage.getRoomId());
			if (null != room) {
				ReceiveMessage receiveMessage = new ReceiveMessage();
				receiveMessage.setChannel(crossSendMessage.getChannel());
				receiveMessage.setSendId(-crossSendMessage.getSendId());// 发负数标识为跨服聊天
				receiveMessage.setSendName(crossSendMessage.getSendName());
				receiveMessage.setReveId(crossSendMessage.getReceiveId());
				receiveMessage.setReveName(crossSendMessage.getReceiveName());
				receiveMessage.setMessage(crossSendMessage.getMessage());
				receiveMessage.setTime(ServiceManager.getManager().getChatService().getTime());
				receiveMessage.setChatType(5);
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer() && !seat.isRobot()) {
						seat.getPlayer().sendData(receiveMessage);
					}
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}