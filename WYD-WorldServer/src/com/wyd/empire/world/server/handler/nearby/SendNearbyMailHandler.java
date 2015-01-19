package com.wyd.empire.world.server.handler.nearby;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.nearby.SendNearbyMail;
import com.wyd.empire.world.common.util.KeywordsUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 发送附近好友邮件
 * 
 * @author zguoqiu
 */
public class SendNearbyMailHandler implements IDataHandler {
	private Logger log;

	public SendNearbyMailHandler() {
		this.log = Logger.getLogger(SendNearbyMailHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		SendNearbyMail sendNearbyMail = (SendNearbyMail) data;
		try {
			int nearbyId = player.getPlayerInfo().getNearbyId();
			if (nearbyId > 0) {
				if (sendNearbyMail.getTheme().getBytes("gbk").length > 30) {// 主题15个汉字
					throw new ProtocolException(ErrorMessages.MAIL_THEMELONG_MESSAGE, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
				if (sendNearbyMail.getContent().getBytes("gbk").length > 300) {// 内容150个汉字
					throw new ProtocolException(ErrorMessages.MAIL_CONTENTLONG_MESSAGE, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
				if (sendNearbyMail.getContent().trim().length() == 0 || sendNearbyMail.getTheme().trim().length() == 0) {// 邮件主题、内容不能为空
					throw new ProtocolException(ErrorMessages.MAIL_NOTNULL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
				String themeStr = sendNearbyMail.getTheme().replaceAll("\\pZ", "");
				String content = KeywordsUtil.filterKeywords(sendNearbyMail.getContent());
				sendNearbyMail.setContent(content);
				if (!(ServiceUtils.checkString(themeStr, true))) {
					throw new ProtocolException(ErrorMessages.MAIL_NOTRIGHT_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
				sendNearbyMail.setSenderId(nearbyId);
				sendNearbyMail.setTheme(KeywordsUtil.filterKeywords(themeStr));
				ServiceManager.getManager().getNearbyService().sendData(sendNearbyMail);
			} else {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}