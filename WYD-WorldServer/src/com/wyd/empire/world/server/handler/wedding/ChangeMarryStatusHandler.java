package com.wyd.empire.world.server.handler.wedding;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.wedding.ChangeMarryStatus;
import com.wyd.empire.protocol.data.wedding.ChangeMarryStatusOK;
import com.wyd.empire.protocol.data.wedding.SendMarryStatusToCouple;
import com.wyd.empire.world.bean.BuffRecord;
import com.wyd.empire.world.bean.MarryRecord;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class ChangeMarryStatusHandler implements IDataHandler {
	Logger log = Logger.getLogger(ChangeMarryStatusHandler.class);

	/**
	 * 更改婚姻状态
	 */
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		ChangeMarryStatus changeMarryStatus = (ChangeMarryStatus) data;
		try {
			// 玩家未达到结婚等级
			if (player.getLevel() < Common.MarryMinLeveLimit) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			int coupleId = changeMarryStatus.getCoupleId();
			int marryMark = changeMarryStatus.getMarryMark();
			boolean boolIsWillingPropose = changeMarryStatus.isBoolIsWillingPropose();
			MarryRecord mr = null;
			if (player.getPlayer().getSex() == 0) {
				mr = (MarryRecord) ServiceManager.getManager().getMarryService().getMarryRecordByTWOPlayerId(player.getId(), coupleId);
			} else {
				mr = (MarryRecord) ServiceManager.getManager().getMarryService().getMarryRecordByTWOPlayerId(coupleId, player.getId());
			}
			if (null == mr) {
				throw new ProtocolException(TipMessages.MARRY_NOTSINGLE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			WorldPlayer couple = ServiceManager.getManager().getPlayerService().getWorldPlayerById(coupleId);
			// 非正常操作
			if (null == couple) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			// 被邀请的好友未达到结婚等级
			if (couple.getLevel() < Common.MarryMinLeveLimit) {
				throw new ProtocolException(ErrorMessages.LACK_OF_CLASS, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			if (boolIsWillingPropose) {
				if (marryMark == 0) {
					mr.setStatusMode(1);
					mr.setEngagedTime(new Date());
					// 发放定情信物
					ServiceManager.getManager().getPlayerItemsFromShopService()
							.playerGetItem(player.getId(), 232, -1, -1, 1, 10, null, 0, 0, 0);
					ServiceManager.getManager().getPlayerItemsFromShopService().playerGetItem(coupleId, 232, -1, -1, 1, 10, null, 0, 0, 0);

				} else {
					mr.setStatusMode(2);
					String[] needDiamond = ServiceManager.getManager().getVersionService().getVersion().getMarryDiamond().split(",");
					if (Integer.parseInt(needDiamond[mr.getType() - 1]) > couple.getDiamond()) {
						throw new ProtocolException(ErrorMessages.COMMUNITY_NEEDDEMOND_MESSAGE, data.getSerial(), data.getSessionId(),
								data.getType(), data.getSubType());
					}
					int useDiamond = Integer.parseInt(needDiamond[mr.getType() - 1]);
					ServiceManager.getManager().getPlayerService()
							.useTicket(couple, useDiamond, TradeService.ORIGIN_MARRY, null, null, "结婚:" + coupleId);
					ServiceManager.getManager().getTaskService().marry(player);
					ServiceManager.getManager().getTaskService().marry(couple);
					Map<String, String> info = new HashMap<String, String>();
					info.put("mateName", couple.getName());
					ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, player);
					info.put("mateName", player.getName());
					ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, couple);
					// 创建婚礼房间
					ServiceManager.getManager().getMarryService().createWeddingHall(mr, changeMarryStatus.getTimeId(), useDiamond);
				}
				ServiceManager.getManager().getMarryService().update(mr);
				ServiceManager.getManager().getMarryService().deleteMarryRecord(player.getPlayer().getSex(), player.getId(), 0);
			} else {
				if (marryMark == 0) {
					ServiceManager.getManager().getMarryService().remove(mr);
				} else {
					mr.setJhId(0);
					mr.setMarryTime(null);
					ServiceManager.getManager().getMarryService().update(mr);
				}
			}
			ChangeMarryStatusOK changeMarryStatusOK = new ChangeMarryStatusOK(data.getSessionId(), data.getSerial());
			changeMarryStatusOK.setBoolIsWillingPropose(boolIsWillingPropose);
			changeMarryStatusOK.setCoupleName(null == couple ? "" : couple.getName());
			changeMarryStatusOK.setMarryMark(marryMark);
			session.write(changeMarryStatusOK);
			if (null != couple) {
				SendMarryStatusToCouple sendMarryStatusToCouple = new SendMarryStatusToCouple(data.getSessionId(), data.getSerial());
				sendMarryStatusToCouple.setBoolIsWillingPropose(boolIsWillingPropose);
				sendMarryStatusToCouple.setCoupleName(player.getName());
				sendMarryStatusToCouple.setMarryMark(marryMark);
				couple.sendData(sendMarryStatusToCouple);
			}
			// 送物品、加buff
			if (boolIsWillingPropose && marryMark == 1) {
				// 换结婚证
				PlayerItemsFromShop DZXW1 = ServiceManager.getManager().getPlayerItemsFromShopService()
						.uniquePlayerItem(player.getId(), 232);
				PlayerItemsFromShop DZXW2 = ServiceManager.getManager().getPlayerItemsFromShopService()
						.uniquePlayerItem(couple.getId(), 232);
				DZXW1.setPLastNum(0);
				DZXW2.setPLastNum(0);
				ServiceManager.getManager().getPlayerItemsFromShopService().update(DZXW1);
				ServiceManager.getManager().getPlayerItemsFromShopService().update(DZXW2);
				ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, DZXW1);
				WorldPlayer other = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(couple.getId());
				if (other != null) {
					ServiceManager.getManager().getPlayerItemsFromShopService().useItem(other, DZXW2);
				}
				// 发结婚证
				ServiceManager.getManager().getPlayerItemsFromShopService().playerGetItem(player.getId(), 233, 1, 10, null, 0, 0, 0);
				ServiceManager.getManager().getPlayerItemsFromShopService().playerGetItem(couple.getId(), 233, 1, 10, null, 0, 0, 0);

				int num = Math.abs(mr.getType() - 4) * 2 == 0 ? 1 : Math.abs(mr.getType() - 4) * 2;
				switch (mr.getType()) {
					case 1 :// 1是最贵的，4是最便宜的
							// 发放礼服
						ServiceManager.getManager().getPlayerItemsFromShopService()
								.playerGetItem(mr.getManId(), 787, -1, num * 15, -1, 10, null, 0, 0, 0);
						ServiceManager.getManager().getPlayerItemsFromShopService()
								.playerGetItem(mr.getManId(), 788, -1, num * 15, -1, 10, null, 0, 0, 0);
						ServiceManager.getManager().getPlayerItemsFromShopService()
								.playerGetItem(mr.getWomanId(), 785, -1, num * 15, -1, 10, null, 0, 0, 0);
						ServiceManager.getManager().getPlayerItemsFromShopService()
								.playerGetItem(mr.getWomanId(), 786, -1, num * 15, -1, 10, null, 0, 0, 0);
						ServiceManager
								.getManager()
								.getChatService()
								.sendBulletinToWorld(
										Common.MARRY_NOTICE
												+ TipMessages.MARRYNOTICE.replace("XX", couple.getName()).replace("YY", player.getName())
														.replace("ZZ", TipMessages.MERRY1), player.getName(), 1);
						break;
					case 2 :
						// 发放礼服
						ServiceManager.getManager().getPlayerItemsFromShopService()
								.playerGetItem(mr.getManId(), 787, -1, num * 15, -1, 10, null, 0, 0, 0);
						ServiceManager.getManager().getPlayerItemsFromShopService()
								.playerGetItem(mr.getManId(), 788, -1, num * 15, -1, 10, null, 0, 0, 0);
						ServiceManager.getManager().getPlayerItemsFromShopService()
								.playerGetItem(mr.getWomanId(), 785, -1, num * 15, -1, 10, null, 0, 0, 0);
						ServiceManager.getManager().getPlayerItemsFromShopService()
								.playerGetItem(mr.getWomanId(), 786, -1, num * 15, -1, 10, null, 0, 0, 0);
						ServiceManager
								.getManager()
								.getChatService()
								.sendBulletinToWorld(
										TipMessages.MARRYNOTICE.replace("XX", couple.getName()).replace("YY", player.getName())
												.replace("ZZ", TipMessages.MERRY2), player.getName(), 2);
						break;
					case 3 :
						// 发放礼服
						ServiceManager.getManager().getPlayerItemsFromShopService()
								.playerGetItem(mr.getManId(), 236, -1, num * 15, -1, 10, null, 0, 0, 0);
						ServiceManager.getManager().getPlayerItemsFromShopService()
								.playerGetItem(mr.getManId(), 237, -1, num * 15, -1, 10, null, 0, 0, 0);
						ServiceManager.getManager().getPlayerItemsFromShopService()
								.playerGetItem(mr.getWomanId(), 234, -1, num * 15, -1, 10, null, 0, 0, 0);
						ServiceManager.getManager().getPlayerItemsFromShopService()
								.playerGetItem(mr.getWomanId(), 235, -1, num * 15, -1, 10, null, 0, 0, 0);
						ServiceManager
								.getManager()
								.getChatService()
								.sendBulletinToWorld(
										TipMessages.MARRYNOTICE.replace("XX", couple.getName()).replace("YY", player.getName())
												.replace("ZZ", TipMessages.MERRY3), player.getName(), 3);
						break;
					case 4 :
						// 发放礼服
						ServiceManager.getManager().getPlayerItemsFromShopService()
								.playerGetItem(mr.getManId(), 236, -1, num * 15, -1, 10, null, 0, 0, 0);
						ServiceManager.getManager().getPlayerItemsFromShopService()
								.playerGetItem(mr.getManId(), 237, -1, num * 15, -1, 10, null, 0, 0, 0);
						ServiceManager.getManager().getPlayerItemsFromShopService()
								.playerGetItem(mr.getWomanId(), 234, -1, num * 15, -1, 10, null, 0, 0, 0);
						ServiceManager.getManager().getPlayerItemsFromShopService()
								.playerGetItem(mr.getWomanId(), 235, -1, num * 15, -1, 10, null, 0, 0, 0);
						ServiceManager
								.getManager()
								.getChatService()
								.sendBulletinToWorld(
										TipMessages.MARRYNOTICE.replace("XX", couple.getName()).replace("YY", player.getName())
												.replace("ZZ", TipMessages.MERRY4), player.getName(), 4);
						break;
				}

				long onday = 24 * 60 * 60 * 1000;
				if (mr.getType() < 4) {
					// 加buff
					Buff buff = new Buff();
					buff.setBuffName("结婚经验加成");
					buff.setBuffCode(Buff.MEXP);
					buff.setIcon("");
					buff.setAddType(1);
					buff.setQuantity(1 == mr.getType() ? 10 : 5);
					buff.setEndtime(-1);
					ServiceManager.getManager().getBuffService().addBuff(player, buff);
					ServiceManager.getManager().getBuffService().addBuff(couple, buff);
					player.updateFight();
					// 保存数据库记录
					BuffRecord br = new BuffRecord();
					br.setBuffName(buff.getBuffName());
					br.setBuffCode(buff.getBuffCode());
					br.setConsortiaSkill(null);
					br.setEndtime(new Date(System.currentTimeMillis() + 365 * 10 * onday));
					br.setPlayerId(player.getId());
					br.setQuantity(buff.getQuantity());
					br.setAddType(buff.getAddType());
					br.setSurplus(buff.getSurplus());
					br.setBuffType(buff.getBufftype());
					ServiceManager.getManager().getConsortiaService().save(br);
					br.setPlayerId(coupleId);
					ServiceManager.getManager().getConsortiaService().save(br);
				}
				if (mr.getType() < 3) {
					// 加buff
					Buff buff = new Buff();
					buff.setBuffName("结婚伤害加成");
					buff.setBuffCode(Buff.MHURT);
					buff.setIcon("");
					buff.setAddType(0);
					buff.setQuantity(1 == mr.getType() ? 500 : 200);
					buff.setEndtime(-1);
					ServiceManager.getManager().getBuffService().addBuff(player, buff);
					ServiceManager.getManager().getBuffService().addBuff(couple, buff);
					player.updateFight();
					// 保存数据库记录
					BuffRecord br = new BuffRecord();
					br.setBuffName(buff.getBuffName());
					br.setBuffCode(buff.getBuffCode());
					br.setConsortiaSkill(null);
					br.setEndtime(new Date(System.currentTimeMillis() + 365 * 10 * onday));
					br.setPlayerId(player.getId());
					br.setQuantity(buff.getQuantity());
					br.setAddType(buff.getAddType());
					br.setSurplus(buff.getSurplus());
					br.setBuffType(buff.getBufftype());
					ServiceManager.getManager().getConsortiaService().save(br);
					br.setPlayerId(coupleId);
					ServiceManager.getManager().getConsortiaService().save(br);
				}
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
