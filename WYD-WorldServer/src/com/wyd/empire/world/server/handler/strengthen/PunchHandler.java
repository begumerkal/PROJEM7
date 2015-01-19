package com.wyd.empire.world.server.handler.strengthen;

import java.util.Date;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.strengthen.Punch;
import com.wyd.empire.protocol.data.strengthen.PunchOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.StrongeRecord;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 拆卸
 * 
 * @author Administrator
 */
public class PunchHandler implements IDataHandler {

	Logger log = Logger.getLogger(PunchHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		Punch punch = (Punch) data;
		int markIndex = 0;
		try {
			int domark = punch.getMark();
			int stoneType = punch.getStoneType();
			int itemId = 0;
			PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService()
					.uniquePlayerItem(player.getId(), punch.getItemId());

			if (pifs == null || pifs.getShopItem().getMove() == Common.CHANGE_ATTRIBUTE_N) {
				throw new ProtocolException(ErrorMessages.CANNOT_PUNCH, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}

			int stoneId = 0;
			StrongeRecord strongeRecord = new StrongeRecord();

			switch (stoneType) {
				case 1 :
					stoneId = pifs.getAttackStone();
					pifs.updateAttackStone(0);
					break;
				case 2 :
					stoneId = pifs.getDefenseStone();
					pifs.updateDefenseStone(0);
					break;
				case 3 :
					stoneId = pifs.getSpecialStone();
					pifs.updateSpecialStone(0);
					break;
				default :
					break;
			}

			ServiceManager.getManager().getPlayerItemsFromShopService().playerGetItem(player.getId(), stoneId, -1, -1, 1, 18, "", 0, 0, 0);
			markIndex = 0;
			ServiceManager.getManager().getPlayerItemsFromShopService().update(pifs);
			// 更新玩家拥有的物品
			ServiceManager.getManager().getPlayerItemsFromShopService().PunchItem(player, pifs);
			itemId = stoneId;

			strongeRecord.setPlayerId(player.getId());
			strongeRecord.setType(StrongeRecord.TAKEOFF);
			strongeRecord.setLevel(-1);
			strongeRecord.setCreateTime(new Date());
			strongeRecord.setItemId(pifs.getShopItem().getId());
			strongeRecord.setRemark("拆卸：" + itemId);
			ServiceManager.getManager().getPlayerItemsFromShopService().save(strongeRecord);
			PunchOk punchOk = new PunchOk(data.getSessionId(), data.getSerial());
			punchOk.setMark(domark);
			punchOk.setStoneType(stoneType);
			punchOk.setItemId(itemId);
			punchOk.setResult(markIndex);
			session.write(punchOk);
			if (pifs.getIsInUsed()) {
				player.updateFight();
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
