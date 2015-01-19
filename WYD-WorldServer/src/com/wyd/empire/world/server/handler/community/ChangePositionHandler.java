package com.wyd.empire.world.server.handler.community;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.ChangePosition;
import com.wyd.empire.protocol.data.community.ChangePositionOk;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> ChangePositionHandler</code>Protocol.COMMUNITY
 * _ChangePosition会员升降职协议处理
 * 
 * @since JDK 1.6
 */
public class ChangePositionHandler implements IDataHandler {
	private Logger log;

	public ChangePositionHandler() {
		this.log = Logger.getLogger(ExitCommunityHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		ChangePosition changePosition = (ChangePosition) data;
		ChangePositionOk changePositionOk = new ChangePositionOk(data.getSessionId(), data.getSerial());
		WorldPlayer myPlayer = session.getPlayer(data.getSessionId());
		try {
			PlayerSinConsortia mypsc = ServiceManager.getManager().getPlayerSinConsortiaService().findPlayerSinConsortia(myPlayer.getId());
			PlayerSinConsortia psc = ServiceManager.getManager().getPlayerSinConsortiaService()
					.findPlayerSinConsortia(changePosition.getPlayerId());
			int oldPosition = psc.getPosition();
			// 判断玩家是否有权限升降职
			if (!(mypsc.getPosition() < psc.getPosition() - 1) || !mypsc.getConsortia().equals(psc.getConsortia())) {
				if (changePosition.getIsUp()) {
					throw new ProtocolException(ErrorMessages.COMMUNITY_NOAUTHORITY_MESSAGE, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
			}
			// 判断升降职人数是否达到上限
			int positionNum = 0;
			if (changePosition.getIsUp()) {
				positionNum = ServiceManager.getManager().getPlayerSinConsortiaService()
						.checkPositionNum(myPlayer.getGuildId(), psc.getPosition() - 1);
			} else {
				positionNum = ServiceManager.getManager().getPlayerSinConsortiaService()
						.checkPositionNum(myPlayer.getGuildId(), psc.getPosition() + 1);
			}
			if (changePosition.getIsUp()) {
				// 升职成副会长，后台标识为1
				if (psc.getPosition() - 1 == 1 && positionNum > 0) {
					throw new Exception(Common.ERRORKEY + ErrorMessages.COMMUNITY_UP_MESSAGE);
				} else if (psc.getPosition() - 1 == 2 && !(positionNum < Common.POSITION2)) {// 升职成长老，后台标识为2
					throw new Exception(Common.ERRORKEY + ErrorMessages.COMMUNITY_UP_MESSAGE);
				} else if (psc.getPosition() - 1 == 3 && !(positionNum < Common.POSITION3 * myPlayer.getGuild().getLevel())) {// 升职成精英，后台标识为3
					throw new Exception(Common.ERRORKEY + ErrorMessages.COMMUNITY_UP_MESSAGE);
				}
			} else {
				// 降职成长老，后台标识为2
				if (psc.getPosition() + 1 == 2 && !(positionNum < Common.POSITION2)) {
					throw new Exception(Common.ERRORKEY + ErrorMessages.COMMUNITY_DOWN_MESSAGE);
				} else if (psc.getPosition() + 1 == 3 && !(positionNum < Common.POSITION3 * myPlayer.getGuild().getLevel())) {// 降职成精英，后台标识为3
					throw new Exception(Common.ERRORKEY + ErrorMessages.COMMUNITY_DOWN_MESSAGE);
				} else if (psc.getPosition() == 4) {
					throw new Exception(Common.ERRORKEY + ErrorMessages.COMMUNITY_LOWESTLEVEL_MESSAGE);
				}
			}
			String str = "";
			// 玩家升降职操作
			if (changePosition.getIsUp()) {
				str = "-升职-原职位-" + psc.getPosition();
				psc.setPosition(psc.getPosition() - 1);
			} else {
				str = "-降职-原职位-" + psc.getPosition();
				psc.setPosition(psc.getPosition() + 1);
			}
			ServiceManager.getManager().getPlayerSinConsortiaService().update(psc);
			changePositionOk.setIsUp(changePosition.getIsUp());
			session.write(changePositionOk);
			// 加入日志
			log.info("公会进出会记录：玩家Id-" + myPlayer.getId() + "-公会Id-" + myPlayer.getGuildId() + "-操作-升降职：被操作玩家-" + psc.getPlayer().getId()
					+ str);
			GameLogService.guildPostChange(psc.getPlayer().getId(), psc.getPlayer().getLevel(), myPlayer.getGuildId(), myPlayer.getGuild()
					.getLevel(), oldPosition, psc.getPosition(), myPlayer.getId(), myPlayer.getLevel(), mypsc.getPosition());
			WorldPlayer member = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(changePosition.getPlayerId());
			if (member != null) {
				String communityPosition = psc.getPositionName();
				// 更新角色信息-职位
				Map<String, String> info = new HashMap<String, String>();
				info.put("position", communityPosition);
				info.put("title", member.getPlayerTitle());
				ServiceManager.getManager().getPlayerService().sendUpdatePlayer(info, member);
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}