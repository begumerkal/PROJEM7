package com.wyd.empire.world.server.handler.chat;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.chat.GetSpeakerNum;
import com.wyd.empire.protocol.data.chat.GetSpeakerNumOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取玩家喇叭数
 * 
 * @author Administrator
 */
public class GetSpeakerNumHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetSpeakerNumHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		GetSpeakerNum getSpeakerNum = (GetSpeakerNum) data;
		if (null == player) {
			return;
		}
		int speakNum = 0;
		int colorSpeakNum = 0;
		try {
			List<Integer> itemIds = new ArrayList<Integer>();
			itemIds.add(Common.HORNID);
			itemIds.add(Common.COLOURHORNID);

			List<PlayerItemsFromShop> list = ServiceManager.getManager().getPlayerItemsFromShopService()
					.getPlayerItem(getSpeakerNum.getPlayerId(), itemIds);
			for (PlayerItemsFromShop p : list) {
				if (p.getShopItem().getId() == Common.HORNID) {
					speakNum = p.getPLastNum();
				} else if (p.getShopItem().getId() == Common.COLOURHORNID) {
					colorSpeakNum = p.getPLastNum();
				}
			}

			GetSpeakerNumOk getSpeakerNumOk = new GetSpeakerNumOk(data.getSessionId(), data.getSerial());
			getSpeakerNumOk.setSpeakNum(speakNum);
			getSpeakerNumOk.setColorSpeakNum(colorSpeakNum);
			session.write(getSpeakerNumOk);

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.CHAT_CHANNEL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
