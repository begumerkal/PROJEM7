package com.wyd.empire.world.server.handler.community;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.UpgradeOk;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> SearchCommunityHandler</code>Protocol.COMMUNITY
 * _SearchCommunity公会升级协议处理
 * 
 * @since JDK 1.6
 */
public class UpgradeHandler implements IDataHandler {
	private Logger log;

	public UpgradeHandler() {
		this.log = Logger.getLogger(UpgradeHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			Consortia cos = (Consortia) ServiceManager.getManager().getConsortiaService().get(Consortia.class, player.getGuildId());
			int cLevel = cos.getLevel();
			if (cos.getPrestige() < (cLevel + 1) * 5000) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NEEDPRESTIGE_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			if (player.getMoney() < (cLevel + 1) * 50000) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NEEDGOLD_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			if (player.getDiamond() < (cLevel + 1) * 200) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NEEDDEMOND_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			// 扣金币
			ServiceManager.getManager().getPlayerService().updatePlayerGold(player, -(cLevel + 1) * 50000, "公会升级", "");
			// 扣钻石
			ServiceManager.getManager().getPlayerService()
					.useTicket(player, (cLevel + 1) * 200, TradeService.ORIGIN_COMMUNITYUP, null, null, "公会升级");
			UpgradeOk upgradeOk = new UpgradeOk(data.getSessionId(), data.getSerial());
			cos.setLevel(cos.getLevel() + 1);
			cos.setTotalMember(cos.getLevel() * 50);
			ServiceManager.getManager().getConsortiaService().update(cos);
			// 修改worldplayer
			List<PlayerSinConsortia> list = ServiceManager.getManager().getPlayerSinConsortiaService()
					.getCommunityMemberList(player.getGuildId(), 1);
			WorldPlayer worldPlayer;
			for (PlayerSinConsortia psc : list) {
				worldPlayer = ServiceManager.getManager().getPlayerService().getLoadPlayer(psc.getPlayer().getId().intValue());
				if (null != worldPlayer)
					worldPlayer.initialGuild();
			}
			session.write(upgradeOk);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}
}