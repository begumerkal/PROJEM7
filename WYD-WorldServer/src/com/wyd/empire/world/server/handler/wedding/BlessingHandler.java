package com.wyd.empire.world.server.handler.wedding;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.chat.ReceiveMessage;
import com.wyd.empire.protocol.data.wedding.Blessing;
import com.wyd.empire.protocol.data.wedding.BlessingOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WeddingRoom;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.impl.MarryService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.ChatService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 礼炮祝福
 * 
 * @author Administrator
 */
public class BlessingHandler implements IDataHandler {
	Logger log = Logger.getLogger(BlessingHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		Blessing blessing = (Blessing) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			String wedNum = blessing.getWedNum();
			WeddingRoom weddingRoom = MarryService.weddingMap.get(wedNum);
			if (null == weddingRoom) {
				return;
			}
			PlayerItemsFromShop blessingItem = ServiceManager.getManager().getPlayerItemsFromShopService()
					.uniquePlayerItem(player.getId(), Common.BLESS);
			if (null == blessingItem || blessingItem.getPLastNum() < 1) {
				throw new ProtocolException(ErrorMessages.WEDDING_BLESS_ENOUGH, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			blessingItem.setPLastNum(blessingItem.getPLastNum() - 1);
			ServiceManager.getManager().getPlayerItemsFromShopService().update(blessingItem);
			ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, blessingItem);

			Integer contribute = ServiceManager.getManager().getVersionService().getWedConfigByKey("contributemax");
			if (null == contribute) {
				contribute = 250;
			}
			if (player.getWedGetContribute() < contribute && player.getGuildId() != 0) {
				Integer peoplecontribute = ServiceManager.getManager().getVersionService().getWedConfigByKey("peoplecontribute");
				if (null == peoplecontribute) {
					peoplecontribute = 1;
				}
				ServiceManager.getManager().getPlayerSinConsortiaService().updatePlayerContribute(player, peoplecontribute);
				player.setWedGetContribute(player.getWedGetContribute() + peoplecontribute);
			}

			BlessingOk blessingOk = new BlessingOk(data.getSessionId(), data.getSerial());
			for (WorldPlayer worldPlayer : weddingRoom.getPlayerList()) {
				worldPlayer.sendData(blessingOk);
			}

			// 给新郎新娘发
			WorldPlayer couple = ServiceManager.getManager().getPlayerService().getWorldPlayerById(weddingRoom.getWedHall().getManId());
			couple.sendData(blessingOk);

			couple = ServiceManager.getManager().getPlayerService().getWorldPlayerById(weddingRoom.getWedHall().getWomanId());
			couple.sendData(blessingOk);

			ReceiveMessage receiveMessage = new ReceiveMessage();
			receiveMessage.setChannel(ChatService.CHANNEL_CURRENT);
			receiveMessage.setSendName(player.getName());
			receiveMessage.setReveName("");
			receiveMessage.setMessage(TipMessages.WEDDING_BLESS.replace("{0}", player.getName()));
			receiveMessage.setChatType(0);
			receiveMessage.setChatSubType(1);
			ServiceManager.getManager().getChatService().sendMessageToCurrent(receiveMessage, player);

		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
