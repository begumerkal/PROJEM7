package com.wyd.empire.world.server.handler.wedding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.wedding.WeddingOver;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.bean.MarryRecord;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.WeddingHall;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WeddingRoom;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.impl.MarryService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class RemoveEngagementHandler implements IDataHandler {
	Logger log = Logger.getLogger(RemoveEngagementHandler.class);

	/**
	 * 解除关系（解除订婚/离婚）
	 */
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		// RemoveEngagement removeEngagement = (RemoveEngagement)data;
		boolean mark = false;
		try {
			MarryRecord mr = ServiceManager.getManager().getMarryService()
					.getSingleMarryRecordByPlayerId(player.getPlayer().getSex(), player.getId(), 1);
			int marryMark = mr.getStatusMode();
			int needDiamond = 0;
			int coupleId = player.getPlayer().getSex() == 0 ? mr.getWomanId() : mr.getManId();
			WorldPlayer couple = ServiceManager.getManager().getPlayerService().getWorldPlayerById(coupleId);
			if (marryMark == 1) {
				needDiamond = ServiceManager.getManager().getVersionService().getVersion().getDhPrice();
			} else if (marryMark == 2) {
				needDiamond = ServiceManager.getManager().getVersionService().getVersion().getJhPrice();
			}
			if (needDiamond > player.getDiamond()) {
				mark = true;
				throw new Exception(Common.ERRORKEY + ErrorMessages.COMMUNITY_NEEDDEMOND_MESSAGE);
			}
			if (marryMark == 1) {
				ServiceManager.getManager().getPlayerService()
						.useTicket(player, needDiamond, TradeService.ORIGIN_DH, null, null, "解除订婚:" + coupleId);
				ServiceManager.getManager().getMarryService().deleteBuffRecord(player.getId());
				ServiceManager.getManager().getMarryService().deleteBuffRecord(coupleId);
				PlayerItemsFromShop DQXW1 = ServiceManager.getManager().getPlayerItemsFromShopService()
						.uniquePlayerItem(player.getId(), 232);
				PlayerItemsFromShop DQXW2 = ServiceManager.getManager().getPlayerItemsFromShopService()
						.uniquePlayerItem(couple.getId(), 232);
				DQXW1.setPLastNum(0);
				DQXW2.setPLastNum(0);
				ServiceManager.getManager().getPlayerItemsFromShopService().update(DQXW1);
				ServiceManager.getManager().getPlayerItemsFromShopService().update(DQXW2);
				ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, DQXW1);
				WorldPlayer other = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(couple.getId());
				if (other != null) {
					ServiceManager.getManager().getPlayerItemsFromShopService().useItem(other, DQXW2);
				}
				Mail mail = new Mail();
				mail.setReceivedId(coupleId);
				mail.setSendId(0);
				mail.setSendName(TipMessages.SYSNAME_MESSAGE);
				mail.setSendTime(new Date());
				mail.setTheme(TipMessages.SYS_MAIL);
				mail.setContent(TipMessages.MARRYMAIL2.replace("XX", player.getName()));
				mail.setIsRead(false);
				mail.setType(1);
				mail.setBlackMail(false);
				mail.setIsStick(Common.IS_STICK);
				ServiceManager.getManager().getMailService().saveMail(mail, null);
				int mId, mLevel, wId, wLevel;
				if (0 == player.getPlayer().getSex().intValue()) {
					mId = player.getId();
					mLevel = player.getLevel();
					wId = couple.getId();
					wLevel = couple.getLevel();
				} else {
					wId = player.getId();
					wLevel = player.getLevel();
					mId = couple.getId();
					mLevel = couple.getLevel();
				}
				GameLogService.divorce(1, mId, mLevel, wId, wLevel, player.getId(), needDiamond);
			} else {
				ServiceManager.getManager().getPlayerService()
						.useTicket(player, needDiamond, TradeService.ORIGIN_JH, null, null, "离婚:" + coupleId);
				List<Buff> buffList = player.getBuffList();
				List<Buff> tmpLiat = new ArrayList<Buff>();
				Map<String, Buff> buffMap = player.getBuffMap();
				for (Buff b : buffList) {
					if (b.getBuffCode().equals("mexp")) {
						tmpLiat.add(b);
						buffMap.remove("mexp");
					} else if (b.getBuffCode().equals("mhurt")) {
						tmpLiat.add(b);
						buffMap.remove("mhurt");
					}
				}
				buffList.removeAll(tmpLiat);
				ServiceManager.getManager().getMarryService().deleteBuffRecord(player.getId());
				ServiceManager.getManager().getMarryService().deleteBuffRecord(coupleId);
				PlayerItemsFromShop JHZ1 = ServiceManager.getManager().getPlayerItemsFromShopService()
						.uniquePlayerItem(player.getId(), 233);
				PlayerItemsFromShop JHZ2 = ServiceManager.getManager().getPlayerItemsFromShopService()
						.uniquePlayerItem(couple.getId(), 233);
				JHZ1.setPLastNum(0);
				JHZ2.setPLastNum(0);
				ServiceManager.getManager().getPlayerItemsFromShopService().update(JHZ1);
				ServiceManager.getManager().getPlayerItemsFromShopService().update(JHZ2);
				ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, JHZ1);
				WorldPlayer other = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(couple.getId());
				if (other != null) {
					ServiceManager.getManager().getPlayerItemsFromShopService().useItem(other, JHZ2);
				}
				WeddingHall wh = ServiceManager.getManager().getMarryService()
						.getWeddingHallByPlayerId(player.getPlayer().getSex(), player.getId());
				if (null != wh) {
					WeddingRoom wr = MarryService.weddingMap.get(wh.getManId() + "" + wh.getWomanId());
					WeddingOver weddingOver = new WeddingOver();
					// 给宾客发送
					for (WorldPlayer wp : wr.getPlayerList()) {
						wp.sendData(weddingOver);
					}
					// 给新郎发送
					WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(wh.getManId());
					worldPlayer.sendData(weddingOver);
					// 给新娘发送
					worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(wh.getWomanId());
					worldPlayer.sendData(weddingOver);
					// 删除结婚的礼堂
					MarryService.weddingMap.remove(wh.getManId() + "" + wh.getWomanId());
					MarryService.weddingList.remove(wr);
				}
				ServiceManager.getManager().getMarryService().deleteWeddingHall(mr);
				Mail mail = new Mail();
				mail.setReceivedId(coupleId);
				mail.setSendId(0);
				mail.setSendName(TipMessages.SYSNAME_MESSAGE);
				mail.setSendTime(new Date());
				mail.setTheme(TipMessages.SYS_MAIL);
				mail.setContent(TipMessages.MARRYMAIL3.replace("XX", player.getName()));
				mail.setIsRead(false);
				mail.setType(1);
				mail.setBlackMail(false);
				ServiceManager.getManager().getMailService().saveMail(mail, null);
				int mId, mLevel, wId, wLevel;
				if (0 == player.getPlayer().getSex().intValue()) {
					mId = player.getId();
					mLevel = player.getLevel();
					wId = couple.getId();
					wLevel = couple.getLevel();
				} else {
					wId = player.getId();
					wLevel = player.getLevel();
					mId = couple.getId();
					mLevel = couple.getLevel();
				}
				GameLogService.divorce(2, mId, mLevel, wId, wLevel, player.getId(), needDiamond);
			}
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (mark) {
				if (null != ex.getMessage())
					throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
			} else {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
		}
	}
}
