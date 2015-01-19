package com.wyd.empire.world.server.handler.strengthen;

import java.util.Date;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.strengthen.UnLockSkill;
import com.wyd.empire.protocol.data.strengthen.UnLockSkillOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.StrongeRecord;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerItemsFromShopService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 解除技能锁 只针对旧锁***
 * 
 * @author zengxc 2013-10-12
 */
public class UnLockSkillHandler implements IDataHandler {
	Logger log = Logger.getLogger(UnLockSkillHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		UnLockSkill unLockSkill = (UnLockSkill) data;
		UnLockSkillOk unLockSkillOk = new UnLockSkillOk(data.getSessionId(), data.getSerial());
		int weaponId = unLockSkill.getWeaponId();
		try {
			IPlayerItemsFromShopService playerItemsFromShopService = ServiceManager.getManager().getPlayerItemsFromShopService();
			PlayerItemsFromShop weapon = playerItemsFromShopService.uniquePlayerItem(player.getId(), weaponId);
			int skillLockId = weapon.getSkillLock();
			// 设置锁定技能为0，即不锁定任何技能
			weapon.setSkillLock(0);
			playerItemsFromShopService.update(weapon);
			StrongeRecord strongeRecord = new StrongeRecord();
			strongeRecord.setPlayerId(player.getId());
			strongeRecord.setType(StrongeRecord.UNLOCK_SKILL);
			strongeRecord.setLevel(1);
			strongeRecord.setCreateTime(new Date());
			strongeRecord.setItemId(weapon.getShopItem().getId());
			strongeRecord.setRemark("技能锁拆卸：" + skillLockId);
			playerItemsFromShopService.save(strongeRecord);
			session.write(unLockSkillOk);
		} catch (Exception ex) {
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
