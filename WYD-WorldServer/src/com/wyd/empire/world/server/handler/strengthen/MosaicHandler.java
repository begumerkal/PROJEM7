package com.wyd.empire.world.server.handler.strengthen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.strengthen.Mosaic;
import com.wyd.empire.protocol.data.strengthen.MosaicOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.StrongeRecord;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class MosaicHandler implements IDataHandler {
	/** 镶嵌所需所需金币基数 */
	public static final int MOSAICGOLD = 500;
	Logger log = Logger.getLogger(MosaicHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		Mosaic mosaic = (Mosaic) data;
		try {
			int itemId = mosaic.getItemId();
			int[] stone = mosaic.getChangeStone();
			PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService().uniquePlayerItem(player.getId(), itemId);
			ShopItem si = null;
			// ShopItem bSi = null;
			int needMoney = 0;
			int diamondNum = 0;
			PlayerItemsFromShop p = null;
			List<PlayerItemsFromShop> pList = new ArrayList<PlayerItemsFromShop>();
			String stoneStr = "";
			for (int stoneId : stone) {
				if (stoneId != 0) {
					si = ServiceManager.getManager().getShopItemService().getShopItemById(stoneId);
					p = ServiceManager.getManager().getPlayerItemsFromShopService().uniquePlayerItem(player.getId(), stoneId);
					int bestone = 0;
					needMoney += si.getLevel() * MOSAICGOLD;
					// 公会技能减耗
					needMoney = (int) ServiceManager.getManager().getBuffService().getAddition(player, needMoney, Buff.CGOLDLOW);
					if (player.getMoney() < needMoney) {
						throw new ProtocolException(ErrorMessages.PLAYER_INOC_MESSAGE, data.getSerial(), data.getSessionId(),
								data.getType(), data.getSubType());
					}
					if (p.getPLastNum() < 1) {
						throw new ProtocolException(TipMessages.ITEMNOTENOUGH, data.getSerial(), data.getSessionId(), data.getType(),
								data.getSubType());
					}
					p.setPLastNum(p.getPLastNum() - 1);
					pList.add(p);
					if (si.getType() == 11) {
						bestone = pifs.getAttackStone();
					} else if (si.getType() == 12) {
						bestone = pifs.getDefenseStone();
					} else if (si.getType() == 13) {
						bestone = pifs.getSpecialStone();
					}
					int useDiamond = 0, useGold = 0;
					// 扣除相应金币或钻石
					if (mosaic.getMark() == 1) {
						useDiamond = diamondNum;
						ServiceManager.getManager().getPlayerService()
								.useTicket(player, diamondNum, TradeService.ORIGIN_SHORTMAIL, new int[]{26}, null, "镶嵌购买金币");
					} else {
						useGold = needMoney;
						ServiceManager.getManager().getPlayerService().updatePlayerGold(player, -needMoney, "镶嵌", "");
					}
					// 先删除属性
					pifs = ServiceManager.getManager().getPlayerItemsFromShopService().changeParm(pifs, bestone, 0);
					// 添加属性
					pifs = ServiceManager.getManager().getPlayerItemsFromShopService().changeParm(pifs, stoneId, 1);
					ServiceManager.getManager().getPlayerItemsFromShopService().MosaicItem(player, pifs);
					stoneStr += stoneId + " ";
					GameLogService.mosaic(player.getId(), player.getLevel(), itemId, stoneId, si.getType(), useDiamond, useGold);
				}
				needMoney = 0;
			}
			for (PlayerItemsFromShop pi : pList) {
				ServiceManager.getManager().getPlayerItemsFromShopService().saveAndUpdatePifs(pi, -1, 1, 19, 1);
				ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, pi);
			}
			ServiceManager.getManager().getPlayerItemsFromShopService().update(pifs);
			// 更新玩家拥有的物品
			ServiceManager.getManager().getPlayerItemsFromShopService().MosaicItem(player, pifs);
			MosaicOk mosaicOk = new MosaicOk(data.getSessionId(), data.getSerial());
			session.write(mosaicOk);
			if (pifs.getIsInUsed()) {
				player.updateFight();
			}
			StrongeRecord strongeRecord = new StrongeRecord();
			strongeRecord.setPlayerId(player.getId());
			strongeRecord.setType(StrongeRecord.MOSAIC);
			strongeRecord.setLevel(-1);
			strongeRecord.setCreateTime(new Date());
			strongeRecord.setItemId(pifs.getShopItem().getId());
			strongeRecord.setRemark("镶嵌：" + stoneStr);
			ServiceManager.getManager().getPlayerItemsFromShopService().save(strongeRecord);
			for (int stoneId : stone) {
				ServiceManager.getManager().getTaskService().mosaic(player, itemId, stoneId);
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
