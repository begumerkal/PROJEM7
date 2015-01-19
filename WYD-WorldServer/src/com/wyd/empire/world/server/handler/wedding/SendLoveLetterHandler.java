package com.wyd.empire.world.server.handler.wedding;

import java.util.Date;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.wedding.SendLoveLetter;
import com.wyd.empire.protocol.data.wedding.SendLoveLetterOK;
import com.wyd.empire.protocol.data.wedding.SendLoveLetterToCouple;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.bean.MarryRecord;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.DailyActivityService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 发送求爱信
 * 
 * @author Administrator
 */
public class SendLoveLetterHandler implements IDataHandler {
	Logger log = Logger.getLogger(SendLoveLetterHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		SendLoveLetter sendLoveLetter = (SendLoveLetter) data;
		try {
			// 玩家未达到结婚等级
			if (player.getLevel() < Common.MarryMinLeveLimit) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			int coupleId = sendLoveLetter.getCoupleId();
			String coupleName = sendLoveLetter.getCoupleName();
			int useItemId = sendLoveLetter.getUseItemId();
			int timeId = sendLoveLetter.getTimeId();
			int marryMark = 0;
			int sexmark = player.getPlayer().getSex() == 0 ? 1 : 0;
			WorldPlayer couple = null;
			if (coupleId == 0 && coupleName.length() == 0) {
				throw new ProtocolException(TipMessages.MARRY_EMPTY, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			if (coupleId != 0) {
				couple = ServiceManager.getManager().getPlayerService().getWorldPlayerById(coupleId);
			} else {
				couple = ServiceManager.getManager().getPlayerService().getWorldPlayerByName(coupleName);
			}
			if (null == couple) {
				throw new ProtocolException(ErrorMessages.FRIEND_ISNOTREAL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			if (couple.getLevel() < Common.MarryMinLeveLimit) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			if (!couple.isOnline()) {
				throw new ProtocolException(TipMessages.MARRY_PLAYER_NOTONLINE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			// 被邀请的好友未达到结婚等级
			if (couple.getLevel() < Common.MarryMinLeveLimit) {
				throw new ProtocolException(ErrorMessages.LACK_OF_CLASS, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			if (couple.isInSingleMap() || couple.getRoomId() != 0 || couple.getBossmapRoomId() != 0 || couple.getBattleId() != 0
					|| couple.getBossmapBattleId() != 0) {
				throw new ProtocolException(TipMessages.MARRY_BATTLE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			if (useItemId > 4) {// 订婚
				MarryRecord mr = ServiceManager.getManager().getMarryService()
						.getSingleMarryRecordByPlayerId(player.getPlayer().getSex(), player.getId(), 0);
				if (player.getPlayer().getSex().intValue() == couple.getPlayer().getSex().intValue()) {
					throw new ProtocolException(TipMessages.MARRY_SAME_SEX, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
				if (null != mr) {
					throw new ProtocolException(TipMessages.MARRY_WAIT, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
				mr = ServiceManager.getManager().getMarryService().getSingleMarryRecordByPlayerId(sexmark, couple.getId(), 1);
				if (null != mr && mr.getStatusMode() > 0) {
					throw new ProtocolException(TipMessages.MARRY_NOTSINGLE, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
				PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService()
						.uniquePlayerItem(player.getId(), useItemId);
				if (null == pifs || pifs.getPLastNum() < 1) {
					throw new ProtocolException(TipMessages.MARRY_ITEM, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
				MarryRecord marry = new MarryRecord();
				marry.setCreateTime(new Date());
				if (player.getPlayer().getSex() == 0) {
					marry.setManId(player.getId());
					marry.setWomanId(couple.getId());
				} else {
					marry.setManId(couple.getId());
					marry.setWomanId(player.getId());
				}
				marry.setStatusMode(0);
				marry.setUseItemId(useItemId);
				marry.setDhId(player.getId());
				ServiceManager.getManager().getMarryService().save(marry);
				pifs.setPLastNum(pifs.getPLastNum() - 1);
				ServiceManager.getManager().getPlayerItemsFromShopService().update(pifs);
				// 更新玩家拥有的物品
				ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, pifs);
				ServiceManager.getManager().getPlayerItemsFromShopService()
						.saveGetItemRecord(player.getPlayer().getId(), pifs.getShopItem().getId(), -1, -1, -7, 1, null);
				DailyActivityService dailyActivityService = ServiceManager.getManager().getDailyActivityService();
				dailyActivityService.saveLogActivitiesAward(dailyActivityService.getProposeReward(), player.getId());
				GameLogService.propose(player.getId(), player.getPlayer().getSex(), player.getLevel(), couple.getId(), couple.getPlayer()
						.getSex(), couple.getLevel(), useItemId);
			} else {// 结婚
				marryMark = 1;
				MarryRecord mr = ServiceManager.getManager().getMarryService()
						.getSingleMarryRecordByPlayerId(player.getPlayer().getSex(), player.getId(), 1);
				if (mr.getJhId() != 0 && (new Date().getTime() - mr.getMarryTime().getTime() < 1 * 60 * 1000)) {
					throw new ProtocolException(TipMessages.MARRY_WAIT, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
				String[] needDiamond = ServiceManager.getManager().getVersionService().getVersion().getMarryDiamond().split(",");
				if (Integer.parseInt(needDiamond[useItemId - 1]) > player.getDiamond()) {
					throw new ProtocolException(ErrorMessages.COMMUNITY_NEEDDEMOND_MESSAGE, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
				mr.setJhId(player.getId());
				mr.setType(useItemId);
				mr.setMarryTime(new Date());
				ServiceManager.getManager().getMarryService().update(mr);
				// ServiceManager.getManager().getPlayerService().useTicket(player,
				// needDiamond, TradeService.ORIGIN_MARRY, null,
				// null, "结婚");
			}
			SendLoveLetterOK sendLoveLetterOK = new SendLoveLetterOK(data.getSessionId(), data.getSerial());
			session.write(sendLoveLetterOK);
			if (null != couple) {
				SendLoveLetterToCouple sendLoveLetterToCouple = new SendLoveLetterToCouple(data.getSessionId(), data.getSerial());
				sendLoveLetterToCouple.setSendId(player.getId());
				sendLoveLetterToCouple.setSendName(player.getName());
				sendLoveLetterToCouple.setProposeItemId(useItemId);
				sendLoveLetterToCouple.setMarryMark(marryMark);
				sendLoveLetterToCouple.setTimeId(timeId);
				couple.sendData(sendLoveLetterToCouple);
				if (useItemId > 4) {
					Mail mail = new Mail();
					mail.setReceivedId(couple.getId());
					mail.setSendId(0);
					mail.setSendName(TipMessages.SYSNAME_MESSAGE);
					mail.setSendTime(new Date());
					mail.setTheme(TipMessages.SYS_MAIL);
					switch (useItemId) {
						case Common.MARRYITEMID1 :// 梦幻求婚--钻戒
							mail.setContent(TipMessages.MARRYMAIL1.replace("##", player.getName()).replace("XX", TipMessages.DREAM));
							break;
						case Common.MARRYITEMID2 :// 浪漫求婚--易拉环
							mail.setContent(TipMessages.MARRYMAIL1.replace("##", player.getName()).replace("XX", TipMessages.ROMANTIC));
							break;
						case Common.MARRYITEMID3 :// 温馨求婚--玫瑰花束
							mail.setContent(TipMessages.MARRYMAIL1.replace("##", player.getName()).replace("XX", TipMessages.WARM));
							break;
						case Common.MARRYITEMID4 :// 朴实求婚--银行卡
							mail.setContent(TipMessages.MARRYMAIL1.replace("##", player.getName()).replace("XX", TipMessages.GUILELESS));
							break;
					}
					mail.setIsRead(false);
					mail.setType(1);
					mail.setBlackMail(false);
					mail.setIsStick(Common.IS_STICK);
					ServiceManager.getManager().getMailService().saveMail(mail, null);
				}
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
