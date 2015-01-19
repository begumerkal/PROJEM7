package com.wyd.empire.world.server.handler.account;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.CreateRoleActor;
import com.wyd.empire.protocol.data.server.SetClientInfo;
import com.wyd.empire.world.Client;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.CreatePlayerException;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> CreateActorHandler</code>Protocol.ACCOUNT_CreateActor 创建角色协议处理
 * 
 * @since JDK 1.6
 */
public class CreateRoleActorHandler implements IDataHandler {
	private Logger log = Logger.getLogger(CreateRoleActorHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		Client client = session.getClient(data.getSessionId());
		CreateRoleActor createActor = (CreateRoleActor) data;
		if ((client == null) || (client.getStatus() != Client.STATUS.LOGIN)) {
			return;
		} else {
			client.setStatus(Client.STATUS.CREATEPLAYE);
		}
		try {
			List<Player> list = ServiceManager.getManager().getPlayerService().getPlayerList(client.getGameAccountId());
			if (null == list || list.isEmpty()) {
				Player player = ServiceManager
						.getManager()
						.getPlayerService()
						.createPlayer(client.getGameAccountId(), createActor.getPlayerName(), createActor.getPlayerSex(),
								client.getChannel(), client.getName());
				Mail mail = new Mail();
				mail.setBlackMail(false);
				mail.setContent(TipMessages.WELCOME);
				mail.setIsRead(false);
				mail.setReceivedId(player.getId());
				mail.setReceivedName(player.getName());
				mail.setSendId(0);
				mail.setSendName(TipMessages.SYSNAME_MESSAGE);
				mail.setSendTime(new Date());
				mail.setTheme(TipMessages.SYS_MAIL);
				mail.setType(1);
				mail.setIsStick(Common.IS_STICK);
				ServiceManager.getManager().getMailService().saveMail(mail, null);
				if (null != createActor.getArea() && createActor.getArea().length() > 0) {
					String area = createActor.getArea().substring(1);
					area = URLDecoder.decode(area, "UTF-8");
					String[] infos = area.split("&");
					if (infos.length == 7) {
						String clientModel = infos[1];
						String systemName = infos[3];
						String systemVersion = infos[4];
						int index = clientModel.indexOf("=");
						if (index > -1) {
							clientModel = clientModel.substring(index + 1);
						}
						index = systemName.indexOf("=");
						if (index > -1) {
							systemName = systemName.substring(index + 1);
						}
						index = systemVersion.indexOf("=");
						if (index > -1) {
							systemVersion = systemVersion.substring(index + 1);
						}
						SetClientInfo setClientInfo = new SetClientInfo();
						setClientInfo.setAccountId(client.getGameAccountId());
						setClientInfo.setClientModel(clientModel);
						setClientInfo.setSystemName(systemName);
						setClientInfo.setSystemVersion(systemVersion);
						ServiceManager.getManager().getAccountSkeleton().send(setClientInfo);
					}
				}
			}
			// 取角色列表
			GetRoleActorListHandler getActorListHandler = new GetRoleActorListHandler();
			getActorListHandler.handle(data);
		} catch (CreatePlayerException ex) {
			ServiceUtils.log(log, -1, data.getTypeString(), "CreateActor [" + createActor.getPlayerName() + "] failed");
			if (!ex.getMessage().startsWith(Common.ERRORKEY)) {
				this.log.error(ex, ex);
			}
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		} finally {
			client.setStatus(Client.STATUS.LOGIN);
		}
	}
}