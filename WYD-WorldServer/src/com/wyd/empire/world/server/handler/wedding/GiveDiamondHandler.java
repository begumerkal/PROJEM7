package com.wyd.empire.world.server.handler.wedding;

import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.wedding.GiveDiamond;
import com.wyd.empire.protocol.data.wedding.GiveDiamondOK;
import com.wyd.empire.world.bean.MarryRecord;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class GiveDiamondHandler implements IDataHandler {
	Logger log = Logger.getLogger(GiveDiamondHandler.class);

	/**
	 * 赠送钻石
	 */
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		GiveDiamond giveDiamond = (GiveDiamond) data;
		try {
			int coupleId = giveDiamond.getCoupleId();
			int diamondCountGive = giveDiamond.getDiamondCountGive();
			MarryRecord mr = null;
			if (player.getPlayer().getSex() == 0) {
				mr = (MarryRecord) ServiceManager.getManager().getMarryService().getMarryRecordByTWOPlayerId(player.getId(), coupleId);
			} else {
				mr = (MarryRecord) ServiceManager.getManager().getMarryService().getMarryRecordByTWOPlayerId(coupleId, player.getId());
			}
			if (null == mr || mr.getStatusMode() < 2) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOAUTHORITY_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			// 结婚转钻开关
			Map<String, Integer> map = ServiceManager.getManager().getVersionService().getSpecialMark();
			if (map.get("openWedGive") == null || map.get("openWedGive") == 0) {
				if (diamondCountGive > player.getDiamond()) {
					throw new ProtocolException(ErrorMessages.COMMUNITY_NEEDDEMOND_MESSAGE, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
			} else {
				if ((map.get("openWedGive") == 1 && diamondCountGive > player.getCanGiveDiamondMode1())
						|| (map.get("openWedGive") == 2 && diamondCountGive > player.getCanGiveDiamondMode2())
						|| diamondCountGive > player.getDiamond()) {
					throw new ProtocolException(ErrorMessages.COMMUNITY_NEEDDEMOND_MESSAGE, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
			}
			player.setCanGiveDiamond(-diamondCountGive);
			WorldPlayer couple = ServiceManager.getManager().getPlayerService().getWorldPlayerById(coupleId);
			if (null != couple) {
				ServiceManager
						.getManager()
						.getPlayerService()
						.useTicket(player, diamondCountGive, TradeService.ORIGIN_MARRYGIVE, null, null,
								"结婚钻石赠送：" + player.getId() + " to " + couple.getId());
				ServiceManager
						.getManager()
						.getPlayerService()
						.addTicket(couple, diamondCountGive, 0, TradeService.ORIGIN_MARRYGET, 0, "",
								"结婚钻石获得：" + couple.getId() + " from " + player.getId(), "", "");
				GiveDiamondOK giveDiamondOK1 = new GiveDiamondOK(data.getSessionId(), data.getSerial());
				giveDiamondOK1.setCoupleName(couple.getName());
				giveDiamondOK1.setDiamondCountGive(diamondCountGive);
				giveDiamondOK1.setGiveId(player.getId());
				session.write(giveDiamondOK1);
				GiveDiamondOK giveDiamondOK2 = new GiveDiamondOK();
				giveDiamondOK2.setCoupleName(player.getName());
				giveDiamondOK2.setDiamondCountGive(diamondCountGive);
				giveDiamondOK2.setGiveId(player.getId());
				couple.sendData(giveDiamondOK2);
				GameLogService.shiftMoney(player.getId(), player.getPlayer().getSex(), player.getLevel(), couple.getId(), couple
						.getPlayer().getSex(), couple.getLevel(), diamondCountGive);
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
