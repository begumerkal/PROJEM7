package com.wyd.empire.world.server.handler.player;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.player.GetPropListOk;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取道具列表
 * 
 * @author Administrator
 */
public class GetPropListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetPropListHandler.class);

	// 获取道具列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			List<Tools> toolsList = ServiceManager.getManager().getToolsService().getToolsListByType(1);
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
				id[i] = null == tool.getId() ? -1 : tool.getId();
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
				i++;
			}
			GetPropListOk getPropListOk = new GetPropListOk(data.getSessionId(), data.getSerial());
			getPropListOk.setCount(count);
			getPropListOk.setId(id);
			getPropListOk.setName(name);
			getPropListOk.setIcon(icon);
			getPropListOk.setPriceCostGold(priceCostGold);
			getPropListOk.setDesc(desc);
			getPropListOk.setMainType(mainType);
			getPropListOk.setIsSubType(isSubType);
			getPropListOk.setParam1(param1);
			getPropListOk.setParam2(param2);
			getPropListOk.setTireValue(tireValue);
			getPropListOk.setConsumePower(consumePower);
			getPropListOk.setSpecialAttackType(specialAttackType);
			getPropListOk.setSpecialAttackParam(specialAttackParam);
			session.write(getPropListOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.PLAYER_GPF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
