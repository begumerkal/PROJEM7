package com.wyd.empire.world.server.handler.wedding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.wedding.JoinWedding;
import com.wyd.empire.protocol.data.wedding.JoinWeddingOk;
import com.wyd.empire.protocol.data.wedding.RefreshWedding;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WeddingRoom;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.impl.MarryService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 参加婚礼
 * 
 * @author Administrator
 */
public class JoinWeddingHandler implements IDataHandler {
	Logger log = Logger.getLogger(JoinWeddingHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		JoinWedding joinWedding = (JoinWedding) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			String wedNum = joinWedding.getWedNum();
			WeddingRoom weddingRoom = MarryService.weddingMap.get(wedNum);
			if (null == weddingRoom) {
				return;
			}
			// 新娘新郎不需要密码进入
			if (player.getId() != weddingRoom.getWedHall().getManId() && player.getId() != weddingRoom.getWedHall().getWomanId()) {
				// 密码不正确
				if (!weddingRoom.getPassword().equals(joinWedding.getPassword())) {
					throw new ProtocolException(ErrorMessages.ROOM_PWERROR_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
			}

			if (weddingRoom.getWedHall().getStartTime().after(new Date()) || weddingRoom.getWedHall().getEndTime().before(new Date())) {
				throw new ProtocolException(ErrorMessages.WEDDING_NOT_START, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			Integer hallNumMax = ServiceManager.getManager().getVersionService().getWedConfigByKey("hallNumMax");
			if (null == hallNumMax) {
				hallNumMax = 30;
			}
			if (weddingRoom.getPlayerList().size() >= hallNumMax && weddingRoom.getWedHall().getManId() != player.getId()
					&& weddingRoom.getWedHall().getWomanId() != player.getId()) {
				throw new ProtocolException(ErrorMessages.WEDDING_FULL, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			// 将玩家添加到列表中
			if (null != player && weddingRoom.getWedHall().getManId() != player.getId()
					&& weddingRoom.getWedHall().getWomanId() != player.getId()) {
				weddingRoom.getPlayerList().remove(player);
				weddingRoom.getPlayerList().add(player);
			}
			int[] playerId = new int[weddingRoom.getPlayerList().size()];
			String[] playerName = new String[weddingRoom.getPlayerList().size()];
			boolean[] sex = new boolean[weddingRoom.getPlayerList().size()];
			JoinWeddingOk joinWeddingOk = new JoinWeddingOk(data.getSessionId(), data.getSerial());
			// 先获得男方
			WorldPlayer wp = ServiceManager.getManager().getPlayerService().getWorldPlayerById(weddingRoom.getWedHall().getManId());
			if (wp.getRoomId() != 0 || wp.getBossmapRoomId() != 0) {// 玩家战斗中就不显示在婚礼现场
				String[] manIcon = new String[0];
				joinWeddingOk.setManName(wp.getName());
				joinWeddingOk.setManIcon(manIcon);
			} else {
				String[] manIcon = new String[2];
				joinWeddingOk.setManName(wp.getName());
				manIcon[0] = wp.getSuit_head();
				manIcon[1] = wp.getSuit_face();
				joinWeddingOk.setManIcon(manIcon);
			}
			// 获得女方
			wp = ServiceManager.getManager().getPlayerService().getWorldPlayerById(weddingRoom.getWedHall().getWomanId());
			if (wp.getRoomId() != 0 || wp.getBossmapRoomId() != 0) {// 玩家战斗中就不显示在婚礼现场
				String[] womanIcon = new String[0];
				joinWeddingOk.setWomanName(wp.getName());
				joinWeddingOk.setWomanIcon(womanIcon);
			} else {
				String[] womanIcon = new String[2];
				joinWeddingOk.setWomanName(wp.getName());
				womanIcon[0] = wp.getSuit_head();
				womanIcon[1] = wp.getSuit_face();
				joinWeddingOk.setWomanIcon(womanIcon);
			}
			// 遍历来宾数据
			List<String> playerEquipment = new ArrayList<String>();
			int index = 0;
			for (WorldPlayer worldplayer : weddingRoom.getPlayerList()) {
				playerId[index] = worldplayer.getId();
				playerName[index] = worldplayer.getName();
				playerEquipment.add(worldplayer.getSuit_head());
				playerEquipment.add(worldplayer.getSuit_face());
				// playerEquipment.add(worldplayer.getSuit_body());
				// playerEquipment.add(worldplayer.getSuit_weapon());
				// playerEquipment.add(worldplayer.getSuit_wing());
				sex[index] = worldplayer.getPlayer().getSex().intValue() == 0 ? true : false;
				index++;
			}
			joinWeddingOk.setPlayerId(playerId);
			joinWeddingOk.setPlayerName(playerName);
			joinWeddingOk.setPlayerEquipment(playerEquipment.toArray(new String[0]));
			joinWeddingOk.setRewardNum(weddingRoom.getWedHall().getRewardNum());
			PlayerItemsFromShop blessing = ServiceManager.getManager().getPlayerItemsFromShopService()
					.uniquePlayerItem(player.getId(), Common.BLESS);
			if (null == blessing) {
				joinWeddingOk.setBlessingNum(0);
			} else {
				joinWeddingOk.setBlessingNum(blessing.getPLastNum());
			}
			Integer price = ServiceManager.getManager().getVersionService().getWedConfigByKey("rewardprice");
			if (null == price || price < 1) {// 校验红包价格
				price = 1;
			}
			joinWeddingOk.setRewardPrice(price);
			Integer rewardhigh = ServiceManager.getManager().getVersionService().getWedConfigByKey("rewardhigh");
			Integer rewardlow = ServiceManager.getManager().getVersionService().getWedConfigByKey("rewardlow");
			if (null == rewardhigh)
				rewardhigh = 9999;
			if (null == rewardlow)
				rewardlow = 10;
			joinWeddingOk.setRewardLowNum(rewardlow);
			joinWeddingOk.setRewardHighNum(rewardhigh);
			joinWeddingOk.setWedType(weddingRoom.getWedHall().getWedtype());
			joinWeddingOk.setWedNum(weddingRoom.getWedHall().getManId() + "" + weddingRoom.getWedHall().getWomanId());
			joinWeddingOk.setPriestSay(TipMessages.WEDDING_PRIEST_SAY.split("\\|"));
			joinWeddingOk.setSex(sex);
			joinWeddingOk.setFristEnter(player.isFristEnter());
			joinWeddingOk.setRandomClothes(weddingRoom.getRandomClothes());
			session.write(joinWeddingOk);
			player.setFristEnter(false);
			player.setWedNum(wedNum);
			// 宾客进入，刷新婚礼现场
			RefreshWedding refreshWedding = new RefreshWedding();
			refreshWedding.setPlayerId(player.getId());
			refreshWedding.setPlayerName(player.getName());
			refreshWedding.setPlayerEquipment(new String[]{player.getSuit_head(), player.getSuit_face()});
			refreshWedding.setSex(player.getPlayer().getSex().intValue() == 0 ? true : false);
			for (WorldPlayer worldplayer : weddingRoom.getPlayerList()) {
				if (worldplayer.isOnline() && worldplayer.getId() != player.getId()) {
					worldplayer.sendData(refreshWedding);
				}
			}
			// 给新郎新娘发
			WorldPlayer couple;
			if (player.getId() != weddingRoom.getWedHall().getManId()) {
				couple = ServiceManager.getManager().getPlayerService().getWorldPlayerById(weddingRoom.getWedHall().getManId());
				couple.sendData(refreshWedding);
			}
			// 给新郎新娘发
			if (player.getId() != weddingRoom.getWedHall().getWomanId()) {
				couple = ServiceManager.getManager().getPlayerService().getWorldPlayerById(weddingRoom.getWedHall().getWomanId());
				couple.sendData(refreshWedding);
			}
			if (player.getId() == weddingRoom.getWedHall().getManId()) {
				// 记录新郎进入时间
				weddingRoom.setBgInTime(System.currentTimeMillis());
			} else if (player.getId() == weddingRoom.getWedHall().getWomanId()) {
				// 记录新娘进入时间
				weddingRoom.setbInTime(System.currentTimeMillis());
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
