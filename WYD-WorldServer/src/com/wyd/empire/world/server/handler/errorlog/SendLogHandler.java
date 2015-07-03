package com.wyd.empire.world.server.handler.errorlog;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.errorlog.SendLog;
import com.wyd.empire.world.common.util.CryptionUtil;
import com.wyd.empire.world.model.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取错误列表
 * 
 * @author Administrator
 * 
 */
public class SendLogHandler implements IDataHandler {
	Logger log = Logger.getLogger(SendLogHandler.class);

	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer worldPlayer = session.getPlayer(data.getSessionId());
		SendLog sendLog = (SendLog) data;
		try {
			log.info("id:" + worldPlayer.getPlayer().getId() + "-----player:" + worldPlayer.getPlayer().getNickname() + "-----文件名称："
					+ sendLog.getLogfilename() + "---------------------------------");
			for (String count : sendLog.getLogs()) {
				count = CryptionUtil.Decrypt(CryptionUtil.getByteFromHexString(count), ServiceManager.getManager().getConfiguration()
						.getString("deckey"));
				log.info(count);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
