package com.wyd.empire.world.server.handler.admin;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.admin.UpdateAccountResult;
import com.wyd.empire.protocol.data.server.UpdateAccountInfo;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.request.UpdateAccountRequest;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.AdminSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> UpdateAccountInfoHandler</code>更新玩家资料
 * 
 * @since JDK 1.6
 */
public class UpdateAccountResultHandler implements IDataHandler {
	private Logger log = Logger.getLogger(UpdateAccountResultHandler.class);

	public void handle(AbstractData data) throws Exception {
		AdminSession session = (AdminSession) data.getHandlerSource();
		try {
			UpdateAccountResult updateAccountInfo = (UpdateAccountResult) data;
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(updateAccountInfo.getPlayerId());
			UpdateAccountInfo updateAccount = new UpdateAccountInfo(data.getSessionId(), data.getSerial());
			updateAccount.setPlayerId(updateAccountInfo.getPlayerId());
			updateAccount.setAccountId(player.getPlayer().getAccountId());
			updateAccount.setUpdateType(updateAccountInfo.getUpdateType());
			updateAccount.setValues(updateAccountInfo.getValues());
			UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest(data.getSerial(), data.getSessionId(), session,
					updateAccount.getUpdateType(), updateAccount.getValues(), updateAccount.getAccountId(), updateAccount.getPlayerId());
			ServiceManager.getManager().getRequestService().add(updateAccount.getSerial(), updateAccountRequest);
			ServiceManager.getManager().getAccountSkeleton().send(updateAccount);
		} catch (Exception ex) {
			if (!ex.getMessage().startsWith(Common.ERRORKEY)) {
				this.log.error(ex, ex);
			}
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}
}