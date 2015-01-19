package com.wyd.empire.world.server.handler.player;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.player.GetPlayerSkillOk;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取玩家已装备的技能
 * 
 * @author Administrator
 *
 */
public class GetPlayerSkillHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetPlayerSkillHandler.class);

	// 获取道具列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			List<Tools> toolsList = player.getPlayerJNs();
			int count = toolsList.size();
			int[] id = new int[count];
			String[] name = new String[count];
			String[] icon = new String[count];
			int[] priceCostGold = new int[count];
			String[] desc = new String[count];
			byte[] mainType = new byte[count];
			byte[] isSubType = new byte[count];
			int[] param1 = new int[count];
			int[] param2 = new int[count];
			int[] tireValue = new int[count];
			int[] consumePower = new int[count];
			int[] specialAttackType = new int[count];
			int[] specialAttackParam = new int[count];
			int i = 0;
			for (Tools tool : toolsList) {
				if (null != tool) {
					id[i] = (null == tool.getId() || (!player.isVip() && i == 3)) ? -1 : tool.getId();
					name[i] = null == tool.getName() ? "" : tool.getName();
					icon[i] = null == tool.getIcon() ? "" : tool.getIcon();
					priceCostGold[i] = null == tool.getPriceCostGold() ? -1 : tool.getPriceCostGold();
					desc[i] = null == tool.getDesc() ? "" : tool.getDesc();
					mainType[i] = null == tool.getType() ? -1 : Byte.parseByte(tool.getType().toString());
					isSubType[i] = null == tool.getSubtype() ? -1 : Byte.parseByte(tool.getSubtype().toString());
					param1[i] = null == tool.getParam1() ? -1 : tool.getParam1();
					param2[i] = null == tool.getParam2() ? -1 : tool.getParam2();
					tireValue[i] = null == tool.getTireValue() ? -1 : tool.getTireValue();
					consumePower[i] = null == tool.getConsumePower() ? -1 : tool.getConsumePower();
					specialAttackType[i] = null == tool.getSpecialAttackType() ? -1 : tool.getSpecialAttackType();
					specialAttackParam[i] = null == tool.getSpecialAttackType() ? -1 : tool.getSpecialAttackType();
				} else {
					if (!player.isVip() && i == 3) {
						id[i] = -1;
					} else {
						id[i] = 0;
					}
					name[i] = "-1";
					icon[i] = "-1";
					priceCostGold[i] = -1;
					desc[i] = "-1";
					mainType[i] = -1;
					isSubType[i] = -1;
					param1[i] = -1;
					param2[i] = -1;
					tireValue[i] = -1;
					consumePower[i] = -1;
					specialAttackType[i] = -1;
					specialAttackParam[i] = -1;
				}
				i++;
			}

			GetPlayerSkillOk getPlayerSkillOk = new GetPlayerSkillOk(data.getSessionId(), data.getSerial());
			getPlayerSkillOk.setCount(count);
			getPlayerSkillOk.setId(id);
			getPlayerSkillOk.setName(name);
			getPlayerSkillOk.setIcon(icon);
			getPlayerSkillOk.setPriceCostGold(priceCostGold);
			getPlayerSkillOk.setDesc(desc);
			getPlayerSkillOk.setMainType(mainType);
			getPlayerSkillOk.setIsSubType(isSubType);
			getPlayerSkillOk.setParam1(param1);
			getPlayerSkillOk.setParam2(param2);
			getPlayerSkillOk.setTireValue(tireValue);
			getPlayerSkillOk.setConsumePower(consumePower);
			getPlayerSkillOk.setSpecialAttackType(specialAttackType);
			getPlayerSkillOk.setSpecialAttackParam(specialAttackParam);

			session.write(getPlayerSkillOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.PLAYER_GUKF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
