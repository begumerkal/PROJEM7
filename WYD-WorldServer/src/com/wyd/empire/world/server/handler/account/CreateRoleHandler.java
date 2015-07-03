package com.wyd.empire.world.server.handler.account;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.CreateRole;
import com.wyd.empire.protocol.data.server.SetClientInfo;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.entity.mysql.Mail;
import com.wyd.empire.world.entity.mongo.Player;
import com.wyd.empire.world.exception.CreatePlayerException;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.model.Client;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.PlayerService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 创建角色协议处理
 * 
 * @author doter
 * @since JDK 1.6
 */
public class CreateRoleHandler implements IDataHandler {
	private Logger log = Logger.getLogger(CreateRoleHandler.class);

	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		Client client = session.getClient(data.getSessionId());
		CreateRole createActor = (CreateRole) data;
		String clientModel = createActor.getClientModel();
		String systemName = createActor.getSystemName();
		String systemVersion = createActor.getSystemVersion();

		if ((client == null) || (client.getStatus() != Client.STATUS.LOGIN)) {
			return null;
		}
		client.setStatus(Client.STATUS.CREATEPLAYE);
		try {
			PlayerService playerService = ServiceManager.getManager().getPlayerService();
			List<Player> list = playerService.getPlayerList(client.getAccountId());
			if (list.size() > 3) {
				return null;
			}
			Player player = playerService.createPlayer(client.getAccountId(), createActor.getNickname(), createActor.getHeroExtId(),
					client.getChannel(), clientModel, systemName, systemVersion);
			// Mail mail = new Mail();
			// mail.setBlackMail(false);
			// mail.setContent(TipMessages.WELCOME);
			// mail.setIsRead(false);
			// mail.setReceivedId(player.getId());
			// mail.setReceivedName(player.getNickname());
			// mail.setSendId(0);
			// mail.setSendName(TipMessages.SYSNAME_MESSAGE);
			// mail.setSendTime(new Date());
			// mail.setTheme(TipMessages.SYS_MAIL);
			// mail.setType(1);
			// mail.setIsStick(1);
			// ServiceManager.getManager().getMailService().saveMail(mail,
			// null);

			// 取角色列表
			GetRoleListHandler getActorListHandler = new GetRoleListHandler();
			getActorListHandler.handle(data);
			return null;
		} catch (CreatePlayerException ex) {
			ServiceUtils.log(log, -1, data.getTypeString(), "CreateActor [" + createActor.getNickname() + "] failed");
			if (!ex.getMessage().startsWith(Common.ERRORKEY)) {
				this.log.error(ex, ex);
			}
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		} finally {
			client.setStatus(Client.STATUS.LOGIN);
		}
		return null;
	}
}