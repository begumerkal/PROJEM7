package com.wyd.empire.world.server.handler.draw;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.draw.SendDrawTypeList;
import com.wyd.empire.world.bean.DrawType;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * @author Administrator
 */
public class GetDrawTypeListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetDrawTypeListHandler.class);

	/**
	 * 获得抽奖类型
	 */
	@SuppressWarnings("unchecked")
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			List<DrawType> dtList = ServiceManager.getManager().getDrawService().getAll(DrawType.class);
			int[] itemId = new int[dtList.size()];
			String[] icon = new String[dtList.size()];
			String[] name = new String[dtList.size()];
			String[] miniIcon = new String[dtList.size()];

			int index = 0;
			for (DrawType dt : dtList) {
				itemId[index] = dt.getItemId();
				icon[index] = dt.getBigIcon();
				name[index] = dt.getName();

				miniIcon[index] = dt.getMiniIcon();
				index++;
			}

			OperationConfig oc = ServiceManager.getManager().getVersionService().getVersion();

			SendDrawTypeList sendDrawTypeList = new SendDrawTypeList(data.getSessionId(), data.getSerial());
			sendDrawTypeList.setIcon(icon);
			sendDrawTypeList.setId(itemId);
			sendDrawTypeList.setMiniIcon(miniIcon);
			sendDrawTypeList.setName(name);
			sendDrawTypeList.setDetail(oc.getDrawDetail());
			session.write(sendDrawTypeList);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_GETLIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
