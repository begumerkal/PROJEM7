package com.app.empire.world.server.handler.account;

import java.util.Date;

import org.apache.log4j.Logger;

import com.app.empire.protocol.data.account.RoleLogin;
import com.app.empire.protocol.data.account.RoleLoginOk;
import com.app.empire.world.entity.mongo.Player;
import com.app.empire.world.exception.ErrorMessages;
import com.app.empire.world.exception.PlayerDataException;
import com.app.empire.world.model.Client;
import com.app.empire.world.model.player.WorldPlayer;
import com.app.empire.world.service.factory.ServiceManager;
import com.app.empire.world.session.ConnectSession;
import com.app.protocol.data.AbstractData;
import com.app.protocol.exception.ProtocolException;
import com.app.protocol.handler.IDataHandler;

/**
 * 游戏角色登陆协议处理
 * 
 * @since JDK 1.7
 */
public class RoleLoginHandler implements IDataHandler {
	private Logger log;
	private Logger logedLog = Logger.getLogger("logedLog");

	public RoleLoginHandler() {
		this.log = Logger.getLogger(RoleLoginHandler.class);
	}

	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		RoleLogin login = (RoleLogin) data;
		String nickname = login.getNickname();

		Client client = session.getClient(data.getSessionId());
		if ((client != null) && (client.isLogin())) {
			if (session.isFull()) {
				session.removeClient(client);
				throw new ProtocolException(ErrorMessages.LOGIN_FULL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			WorldPlayer worldPlayer = null;
			try {
				worldPlayer = ServiceManager.getManager().getPlayerService().loadWorldPlayer(client, login);
			} catch (Exception ex) {
				session.removeClient(client);
				this.log.error(ex, ex);
				throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
			}
			if (worldPlayer != null) {
				try {
					// 返回loginPlayerOk
					long nTime = System.currentTimeMillis();
					Player player = worldPlayer.getPlayer();
					Date bsTime = player.getBsTime();
					Date beTime = player.getBeTime();
					byte status = player.getStatus();
					if (status == 0) {
						throw new ProtocolException(ErrorMessages.LOGIN_FREEZE_MESSAGE, data.getSerial(), data.getSessionId(),
								data.getType(), data.getSubType());
					}
					if (bsTime != null && beTime != null && nTime >= bsTime.getTime() && nTime <= beTime.getTime()) {
						throw new ProtocolException(ErrorMessages.LOGIN_FREEZE_MESSAGE, data.getSerial(), data.getSessionId(),
								data.getType(), data.getSubType());
					}
					// 角色登录成功
					session.loginPlayer(worldPlayer, data, client);
					player.setLoginTime(new Date());//设置登录时间
					RoleLoginOk playerLoginOk = new RoleLoginOk(data.getSessionId(), data.getSerial());
					playerLoginOk.setId(player.getId());
					playerLoginOk.setNickname(player.getNickname());
					playerLoginOk.setLv(player.getLv());
					playerLoginOk.setLvExp(player.getLvExp());
					playerLoginOk.setVipLv(player.getVipLv());
					playerLoginOk.setVipExp(player.getVipExp());
					playerLoginOk.setMoney(player.getMoney());
					playerLoginOk.setProperty(player.getProperty());
					playerLoginOk.setFight(player.getFight());
					logedLog.info("udid:" + client.getName());

					return playerLoginOk;
				} catch (ProtocolException ex) {
					ServiceManager.getManager().getPlayerService().release(worldPlayer);
					throw ex;
				} catch (Exception ex) {
					this.log.error(ex, ex);
					ServiceManager.getManager().getPlayerService().release(worldPlayer);
					throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
				}
			} else {
				this.log.info("AccountId[" + client.getAccountId() + "]login.getPlayerName()[" + login.getNickname() + "]LOGIN ERROR");
			}
		}
		return null;
	}
}