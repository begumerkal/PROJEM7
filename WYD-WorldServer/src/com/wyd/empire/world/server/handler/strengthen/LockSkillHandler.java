package com.wyd.empire.world.server.handler.strengthen;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.strengthen.LockSkill;
import com.wyd.empire.protocol.data.strengthen.LockSkillOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 
 * @author Administrator
 *
 */
public class LockSkillHandler implements IDataHandler {
	Logger log = Logger.getLogger(LockSkillHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		LockSkill lockSkill = (LockSkill) data;
		try {
			int itemId = lockSkill.getItemId();
			int skillId = lockSkill.getSkilLId();
			PlayerItemsFromShop weap = ServiceManager.getManager().getPlayerItemsFromShopService().uniquePlayerItem(player.getId(), itemId);;
			if (weap == null || skillId != weap.getWeapSkill1() && skillId != weap.getWeapSkill2()) {
				throw new ProtocolException(ErrorMessages.CANNOT_FINDSKILL, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService()
					.uniquePlayerItem(player.getId(), Common.LOCKID);
			if (null == pifs || pifs.getPLastNum() < 1) {
				throw new ProtocolException(TipMessages.ITEMNOTENOUGH, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			pifs.setPLastNum(pifs.getPLastNum() - 1);
			weap.setSkillLock(skillId);
			ServiceManager.getManager().getPlayerItemsFromShopService().update(pifs);
			ServiceManager.getManager().getPlayerItemsFromShopService().update(weap);
			player.updateFight();
			LockSkillOk lockSkillOk = new LockSkillOk(data.getSessionId(), data.getSerial());
			// 更新玩家拥有的物品
			ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, pifs);
			session.write(lockSkillOk);
			ServiceManager.getManager().getPlayerItemsFromShopService()
					.saveGetItemRecord(player.getPlayer().getId(), Common.LOCKID, -1, -1, 16, 1, null);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());

		}
	}
}
