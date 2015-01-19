package com.wyd.empire.world.server.handler.community;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.SendGroupMail;
import com.wyd.empire.protocol.data.community.SendGroupMailOk;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.bean.PlayerSinConsortia;
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
 * 类 <code> SendGroupMail</code>Protocol.COMMUNITY _SendGroupMail公会群发邮件协议处理
 * 
 * @since JDK 1.6
 * 
 */
public class SendGroupMailHandler implements IDataHandler {
	private Logger log;

	public SendGroupMailHandler() {
		this.log = Logger.getLogger(SendGroupMailHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		SendGroupMail sendGroupMail = (SendGroupMail) data;
		SendGroupMailOk sendGroupMailOk = new SendGroupMailOk(data.getSessionId(), data.getSerial());
		WorldPlayer player = session.getPlayer(data.getSessionId());
		boolean mark = false;

		try {

			if (sendGroupMail.getMailContent().getBytes("gbk").length > 300) {// 内容150个汉字
				mark = true;// utf8汉字长度不规范，转成gbk格式判断
				throw new Exception(Common.ERRORKEY + ErrorMessages.MAIL_THEMELONG_MESSAGE);
			}

			// 获得公会成员列表
			List<PlayerSinConsortia> list = ServiceManager.getManager().getPlayerSinConsortiaService()
					.getCommunityMemberList(player.getGuildId(), 1);

			for (PlayerSinConsortia psc : list) {
				if (psc.getPlayer().getId().intValue() != player.getId()) {
					// 发送邮件
					Mail mail = new Mail();

					mail.setContent(sendGroupMail.getMailContent());
					mail.setIsRead(false);
					mail.setReceivedId(psc.getPlayer().getId().intValue());
					mail.setSendId(player.getId());
					mail.setSendName(player.getName());
					mail.setSendTime(new Date());
					mail.setTheme(TipMessages.COMMUNITY_COMMUNITYMAIL_MESSAGE);
					mail.setType(2);
					mail.setBlackMail(false);
					mail.setIsStick(Common.IS_STICK);
					ServiceManager.getManager().getMailService().saveMail(mail, null);

					// HasNewMail hasNewMail = new
					// HasNewMail(data.getSessionId(),data.getSerial());
					// 提醒收件玩家
					// WorldPlayer toPlayer =
					// ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(psc.getPlayer().getId().intValue());
					// if(toPlayer!=null&&toPlayer.isOnline()){
					// toPlayer.sendData(hasNewMail);
					// }
				}
			}

			session.write(sendGroupMailOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (mark) {
				if (null != ex.getMessage())
					throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
			} else {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
		}
	}
}