package com.wyd.empire.world.server.handler.strengthen;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.strengthen.SendSkillList;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.WeapSkill;
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
public class GetSkillListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetSkillListHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			PlayerItemsFromShop pifs = player.getWeapon();
			WeapSkill ws;
			int[] skillId = new int[2];
			String[] skillName = new String[2];
			boolean[] lockOr = new boolean[2];
			if (pifs.getWeapSkill1() != 0) {
				ws = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill1());
				skillId[0] = ws.getId();
				skillName[0] = TipMessages.WEAPSKILLINFO + "1：" + ws.getSkillName();
				if (ws.getId() == pifs.getSkillLock()) {
					lockOr[0] = true;
				}
			} else {
				skillId[0] = 0;
				skillName[0] = "";
				lockOr[0] = false;
			}

			if (pifs.getWeapSkill2() != 0) {
				ws = ServiceManager.getManager().getStrengthenService().getWeapSkillById(pifs.getWeapSkill2());
				skillId[1] = ws.getId();
				skillName[1] = TipMessages.WEAPSKILLINFO + "2：" + ws.getSkillName();
				if (ws.getId() == pifs.getSkillLock()) {
					lockOr[1] = true;
				}
			} else {
				skillId[1] = 0;
				skillName[1] = "";
				lockOr[1] = false;
			}

			SendSkillList sendSkillList = new SendSkillList(data.getSessionId(), data.getSerial());
			sendSkillList.setIcon(pifs.getShopItem().getIcon());
			sendSkillList.setItemId(pifs.getShopItem().getId());
			sendSkillList.setLockOr(lockOr);
			sendSkillList.setSkillId(skillId);
			sendSkillList.setSkillName(skillName);
			sendSkillList.setStrengthoneLevel(pifs.getStrongLevel());

			session.write(sendSkillList);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.STRENGTHEN_FAIL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
