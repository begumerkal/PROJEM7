package com.wyd.empire.world.server.handler.worldbosshall;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.worldbosshall.Accelerate;
import com.wyd.empire.protocol.data.worldbosshall.GetRoomState;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IWorldBossService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 消除CDTime
 * 
 * @author zengxc
 * 
 */
public class AccelerateHandler implements IDataHandler {
	Logger log = Logger.getLogger(AccelerateHandler.class);
	ServiceManager manager = ServiceManager.getManager();
	IWorldBossService worldBossService = null;

	@Override
	public void handle(AbstractData data) throws Exception {
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			WorldPlayer player = session.getPlayer(data.getSessionId());
			Accelerate accelerate = (Accelerate) data;
			accelerate.setMapId(accelerate.getMapId() < 1 ? Common.WORLDBOSS_DEFAULT_MAP : accelerate.getMapId());
			int mapId = accelerate.getMapId();

			int playerId = player.getId();
			worldBossService = manager.getWorldBossService();
			// 支付
			pay(player, Common.WORLDBOSS_CLEAR_CDTIME, data);
			// 消除CDTime
			worldBossService.clearCDTime(playerId, mapId);
			GameLogService.speedUpWordBoss(playerId, player.getId(), Common.WORLDBOSS_CLEAR_CDTIME);
			GetRoomState getRoomState = new GetRoomState(data.getSessionId(), data.getSerial());
			getRoomState.setHandlerSource(data.getHandlerSource());
			getRoomState.setMapId(accelerate.getMapId());
			new GetRoomStateHandler().handle(getRoomState);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.WORLDBOSS_FAIL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	/**
	 * 地图冻结加速支付钻石
	 * 
	 * @param player
	 * @param diamondNum
	 * @throws Exception
	 */
	private void pay(WorldPlayer player, int diamondNum, AbstractData data) throws Exception {
		if (player.getDiamond() < diamondNum) {
			throw new ProtocolException(ErrorMessages.TRATE_LOWTICKETNUM_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
		manager.getPlayerService().useTicket(player, diamondNum, TradeService.ORIGIN_WOELDBOSSACCELERATE, null, null, "世界BOSS清除CDTime");
	}
}
