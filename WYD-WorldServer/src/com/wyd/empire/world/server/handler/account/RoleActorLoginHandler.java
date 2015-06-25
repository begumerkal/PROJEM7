package com.wyd.empire.world.server.handler.account;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.RoleActorLogin;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.PlayerDataException;
import com.wyd.empire.world.model.Client;
import com.wyd.empire.world.model.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 游戏角色登陆协议处理
 * 
 * @since JDK 1.7
 */
public class RoleActorLoginHandler implements IDataHandler {
	private Logger log;
	private Logger logedLog = Logger.getLogger("logedLog");

	public RoleActorLoginHandler() {
		this.log = Logger.getLogger(RoleActorLoginHandler.class);
	}

	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		RoleActorLogin login = (RoleActorLogin) data;
		Client client = session.getClient(data.getSessionId());
		if ((client != null) && (client.isLogin())) {
			if (session.isFull()) {
				session.removeClient(client);
				throw new ProtocolException(ErrorMessages.LOGIN_FULL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			WorldPlayer player = null;
			try {
				player = ServiceManager.getManager().getPlayerService().loadWorldPlayer(login.getPlayerName(), client);
			} catch (PlayerDataException ex) {
				session.removeClient(client);
				this.log.error(ex, ex);
				throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
			}
			if (player != null) {
				try {
					// 设置玩家首次登录的mac地址
					if (null == player.getPlayer().getMac() || player.getPlayer().getMac().length() < 1) {
						// 推广用户激活
						player.getPlayer().setMac(login.getMacCode());
					}
					// 返回loginPlayerOk
					long nTime = System.currentTimeMillis();
					if (null != player.getPlayer().getBsTime() && null != player.getPlayer().getBeTime()
							&& 0 == player.getPlayer().getStatus() && player.getPlayer().getBsTime().getTime() <= nTime
							&& nTime <= player.getPlayer().getBeTime().getTime()) {
						throw new ProtocolException(ErrorMessages.LOGIN_FREEZE_MESSAGE, data.getSerial(), data.getSessionId(),
								data.getType(), data.getSubType());
					} else {
						session.write(session.loginPlayer(player, data, client));
						logedLog.info("udid:" + client.getName());
					}
				} catch (ProtocolException ex) {
					ServiceManager.getManager().getPlayerService().release(player);
					throw ex;
				} catch (Exception ex) {
					this.log.error(ex, ex);
					ServiceManager.getManager().getPlayerService().release(player);
					throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
				}
			} else {
				this.log.info("AccountId[" + client.getAccountId() + "]login.getPlayerName()[" + login.getPlayerName() + "]LOGIN ERROR");
			}
		}
		return null;
	}
}