package com.wyd.empire.world.server.handler.wedding;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.wedding.PeopleGetReward;
import com.wyd.empire.protocol.data.wedding.PeopleGetRewardOk;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WeddingRoom;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.impl.MarryService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 领取红包
 * 
 * @author Administrator
 */
public class PeopleGetRewardHandler implements IDataHandler {
	Logger log = Logger.getLogger(PeopleGetRewardHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		PeopleGetReward peopleGetReward = (PeopleGetReward) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			String wedNum = peopleGetReward.getWedNum();
			WeddingRoom weddingRoom = MarryService.weddingMap.get(wedNum);
			if (null == weddingRoom) {
				return;
			}

			// 校验该玩家是否领取过红包
			if (null != weddingRoom.getMap().get(player.getId())) {
				throw new ProtocolException(ErrorMessages.WEDDING_GOT_REWARD, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			// 校验红包是否领取完
			if (weddingRoom.getWedHall().getRewardNum() < 1) {
				throw new ProtocolException(ErrorMessages.WEDDING_NO_HAVE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}

			// 计算玩家获得金币数
			int avggold = weddingRoom.getWedHall().getAvgGoldNum();
			int othergold = ServiceUtils.getRandomNum(0, weddingRoom.getWedHall().getOtherGoldNum());
			Integer singlereward = ServiceManager.getManager().getVersionService().getWedConfigByKey("singlereward");
			if (null == singlereward) {
				singlereward = 15;
			}
			int goldHigh = weddingRoom.getWedHall().getRewardGoldNum() * singlereward / 100;
			int peopleGetGold = 0;
			// 玩家获得的金币上限是红包总金额的15%（可配的）
			if (avggold + othergold > goldHigh) {
				peopleGetGold = goldHigh;
				othergold = avggold + othergold - goldHigh;
			} else {
				peopleGetGold = avggold + othergold;
			}
			weddingRoom.getWedHall().setOtherGoldNum(weddingRoom.getWedHall().getOtherGoldNum() - othergold);
			weddingRoom.getWedHall().setRewardNum(weddingRoom.getWedHall().getRewardNum() - 1);

			ServiceManager.getManager().getMarryService().update(weddingRoom.getWedHall());
			// 保存已领取玩家MAP
			weddingRoom.getMap().put(player.getId(), player);

			// 发金币给玩家
			ServiceManager
					.getManager()
					.getPlayerService()
					.updatePlayerGold(player, peopleGetGold, "领取结婚红包",
							"男ID:" + weddingRoom.getWedHall().getManId() + "--女ID:" + weddingRoom.getWedHall().getWomanId());

			PeopleGetRewardOk peopleGetRewardOk = new PeopleGetRewardOk(data.getSessionId(), data.getSerial());
			peopleGetRewardOk.setGoldNum(peopleGetGold);
			peopleGetRewardOk.setRewardNum(weddingRoom.getWedHall().getRewardNum());
			session.write(peopleGetRewardOk);

			peopleGetRewardOk.setGoldNum(-1);

			// 给宾客发送
			for (WorldPlayer wp : weddingRoom.getPlayerList()) {
				if (wp.getId() != player.getId()) {
					wp.sendData(peopleGetRewardOk);
				}
			}
			// 给新郎发送
			WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService()
					.getWorldPlayerById(weddingRoom.getWedHall().getManId());
			worldPlayer.sendData(peopleGetRewardOk);
			// 给新娘发送
			worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(weddingRoom.getWedHall().getWomanId());
			worldPlayer.sendData(peopleGetRewardOk);

		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
