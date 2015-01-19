package com.wyd.empire.world.server.handler.community;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.community.ChangePresident;
import com.wyd.empire.protocol.data.community.ChangePresidentOk;
import com.wyd.empire.protocol.data.mail.HasNewMail;
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
 * 类 <code> ChangePresidentHandler</code>Protocol.COMMUNITY
 * _ChangePresident会长让位协议处理
 * 
 * @since JDK 1.6
 * 
 */
public class ChangePresidentHandler implements IDataHandler {
	private Logger log;

	public ChangePresidentHandler() {
		this.log = Logger.getLogger(ExitCommunityHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		ChangePresident changePresident = (ChangePresident) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			ChangePresidentOk changePresidentOk = new ChangePresidentOk(data.getSessionId(), data.getSerial());
			PlayerSinConsortia oldpsc = ServiceManager.getManager().getPlayerSinConsortiaService()
					.findPlayerSinConsortia(changePresident.getOldPresidentId());
			PlayerSinConsortia newpsc = ServiceManager.getManager().getPlayerSinConsortiaService()
					.findPlayerSinConsortia(changePresident.getNewPresidentId());

			if (null == player || newpsc == null || oldpsc.getConsortia().getId().intValue() != newpsc.getConsortia().getId().intValue()) {
				throw new Exception();
			}

			// 操作人不是老会长
			if (player.getId() != oldpsc.getPlayer().getId().intValue()) {
				throw new ProtocolException(data, ErrorMessages.COMMUNITY_NOAUTHORITY_MESSAGE);
			}

			// 更新新会长
			ServiceManager.getManager().getConsortiaService()
					.changePresident(changePresident.getNewPresidentId(), changePresident.getOldPresidentId());

			session.write(changePresidentOk);

			Mail mail = new Mail();

			mail.setContent(TipMessages.COMMUNITY_APPOINTEDPART1_MESSAGE + player.getPlayer().getName()
					+ TipMessages.COMMUNITY_APPOINTEDPART2_MESSAGE);
			mail.setIsRead(false);
			mail.setReceivedId(changePresident.getNewPresidentId());
			mail.setSendId(0);
			mail.setSendName(TipMessages.SYSNAME_MESSAGE);
			mail.setSendTime(new Date());
			mail.setTheme(TipMessages.COMMUNITY_APPOINTED_MESSAGE);
			mail.setType(1);
			mail.setBlackMail(false);
			mail.setIsStick(Common.IS_STICK);
			ServiceManager.getManager().getMailService().saveMail(mail, null);

			HasNewMail remindSendMailOk = new HasNewMail(data.getSessionId(), data.getSerial());
			// 提醒收件玩家
			WorldPlayer newPlayer = ServiceManager.getManager().getPlayerService()
					.getOnlineWorldPlayer(changePresident.getNewPresidentId());
			if (newPlayer != null && newPlayer.isOnline()) {
				newPlayer.sendData(remindSendMailOk);
			}
			// 加入日志
			log.info("公会进出会记录：玩家Id-" + player.getId() + "-公会Id-" + oldpsc.getConsortia().getId() + "-操作-让位：老会长-"
					+ oldpsc.getPlayer().getId() + "-新会长-" + newpsc.getPlayer().getId());
			// 更新角色信息-职位
			Map<String, String> info = new HashMap<String, String>();
			info.put("position", TipMessages.POSITION4);
			info.put("title", player.getPlayerTitle());
			ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, player);
			WorldPlayer newPresident = ServiceManager.getManager().getPlayerService()
					.getOnlineWorldPlayer(changePresident.getNewPresidentId());
			if (newPresident != null) {
				// 更新角色信息-职位
				info = new HashMap<String, String>();
				info.put("position", TipMessages.POSITION0);
				info.put("title", newPresident.getPlayerTitle());
				ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, newPresident);
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_CHANGEPRESIDENT_MESSAGE, data.getSerial(), data.getSessionId(),
					data.getType(), data.getSubType());
		}
	}
}