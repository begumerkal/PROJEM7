package com.wyd.empire.world.server.handler.mail;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.mail.DeleteMail;
import com.wyd.empire.protocol.data.mail.DeleteMailOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> DeleteMailHandler</code>Protocol.MAIL_DeleteMail删除邮件协议处理
 * 
 * @since JDK 1.6
 */
public class DeleteMailHandler implements IDataHandler {
	private Logger log;

	public DeleteMailHandler() {
		this.log = Logger.getLogger(DeleteMailHandler.class);
	}

	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		DeleteMail deleteMail = (DeleteMail) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			DeleteMailOk deleteMailOk = new DeleteMailOk(data.getSessionId(), data.getSerial());
			if (deleteMail.getMailId().length == 0) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.MAIL_DETELE_MESSAGE);
			}
			session.write(deleteMailOk);
//			ServiceManager.getManager().getEMailService().deleteMail(player.getId(), deleteMail.getMailId(), player.getLevel());
//			ServiceManager.getManager().getEMailService().sendMailStatus(player);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.MAIL_DETELE_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
		return null;
	}
}