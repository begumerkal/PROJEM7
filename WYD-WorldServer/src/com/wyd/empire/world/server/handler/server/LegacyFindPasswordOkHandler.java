package com.wyd.empire.world.server.handler.server;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.server.LegacyFindPasswordOk;
import com.wyd.empire.world.model.player.AcountMailVo;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 玩家修改密码
 * 
 * @since JDK 1.6
 */
public class LegacyFindPasswordOkHandler implements IDataHandler {
	private Logger log = Logger.getLogger(LegacyFindPasswordOkHandler.class);

	public AbstractData handle(AbstractData data) {
		LegacyFindPasswordOk legacyFindPasswordOk = (LegacyFindPasswordOk) data;
		try {
			String email = legacyFindPasswordOk.getEmail();
			String[] account = legacyFindPasswordOk.getAccount();
			if (account.length > 0) {
				String[] password = legacyFindPasswordOk.getPassword();
				AcountMailVo acountMailVo = new AcountMailVo();
				acountMailVo.setEmail(email);
				acountMailVo.setUsername(account);
				acountMailVo.setPassword(password);
				ServiceManager.getManager().getEMailService().addMailVo(acountMailVo);
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
}
