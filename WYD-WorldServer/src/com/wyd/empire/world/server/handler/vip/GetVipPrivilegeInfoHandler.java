package com.wyd.empire.world.server.handler.vip;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.vip.GetVipPrivilegeInfoOk;
import com.wyd.empire.world.bean.VipRate;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * vip特权
 * 
 * @author Administrator
 * 
 */
public class GetVipPrivilegeInfoHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetVipPrivilegeInfoHandler.class);

	@SuppressWarnings("unchecked")
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			List<VipRate> list = ServiceManager.getManager().getPlayerItemsFromShopService().getAll(VipRate.class);
			int listCount = list.size();
			int[] vipLevel = new int[listCount];
			int[] experienceAddPer = new int[listCount];
			int[] shopDiscoun = new int[listCount];
			int[] strengthenAddPer = new int[listCount];

			int i = 0;
			for (VipRate er : list) {
				vipLevel[i] = er.getId();
				experienceAddPer[i] = er.getExpRate();
				shopDiscoun[i] = er.getSaleRate();
				strengthenAddPer[i] = er.getStrongRate();
				i++;
			}

			GetVipPrivilegeInfoOk getVipPrivilegeInfoOk = new GetVipPrivilegeInfoOk(data.getSessionId(), data.getSerial());
			getVipPrivilegeInfoOk.setExperienceAddPer(experienceAddPer);
			getVipPrivilegeInfoOk.setShopDiscoun(shopDiscoun);
			getVipPrivilegeInfoOk.setStrengthenAddPer(strengthenAddPer);
			getVipPrivilegeInfoOk.setVipLevel(vipLevel);
			session.write(getVipPrivilegeInfoOk);

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TASK_GETEVERY_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
