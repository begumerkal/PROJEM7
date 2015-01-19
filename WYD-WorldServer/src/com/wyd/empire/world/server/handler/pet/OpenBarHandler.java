package com.wyd.empire.world.server.handler.pet;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.pet.OpenBarOk;
import com.wyd.empire.world.bean.PlayerPetBar;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerPetService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 确认开启宠物槽
 * 
 * @author zengxc
 *
 */
public class OpenBarHandler implements IDataHandler {
	Logger log = Logger.getLogger(OpenBarHandler.class);
	IPlayerPetService playerPetService = null;
	ServiceManager manager = ServiceManager.getManager();

	@Override
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		playerPetService = manager.getPlayerPetService();
		try {
			int openNum = playerPetService.openBarNum(player.getId());
			openNum += 1;
			int diamond = playerPetService.getOpenBarDiamond(openNum);
			pay(player, diamond);
			PlayerPetBar pb = playerPetService.getPlayerPetBar(player.getId());
			pb.setNum(openNum);
			playerPetService.update(pb);
			Map<String, String> info = new HashMap<String, String>();
			info.put("petBarNum", pb.getNum() + "");
			manager.getPlayerService().sendUpdatePlayer(info, player);
			OpenBarOk ok = new OpenBarOk(data.getSessionId(), data.getSerial());
			session.write(ok);
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}

	/**
	 * 宠物开槽支付钻石
	 * 
	 * @param player
	 * @param diamondNum
	 * @throws Exception
	 */
	private void pay(WorldPlayer player, int diamondNum) throws Exception {
		if (player.getDiamond() < diamondNum) {
			throw new Exception(ErrorMessages.TRATE_LOWTICKETNUM_MESSAGE);
		}
		manager.getPlayerService().useTicket(player, diamondNum, TradeService.ORIGIN_PETOPENFIELDS, null, null, "宠物开槽");
	}
}
