package com.wyd.empire.world.server.handler.community;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.GetCommunityInfo;
import com.wyd.empire.protocol.data.community.GetCommunityInfoOk;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> GetCommunityInfoHandler</code>Protocol.COMMUNITY
 * _GetCommunityInfoHandler获得工会信息协议处理
 * 
 * @since JDK 1.6
 */
public class GetCommunityInfoHandler implements IDataHandler {
	private Logger log;

	public GetCommunityInfoHandler() {
		this.log = Logger.getLogger(GetCommunityInfoHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GetCommunityInfo getCommunityInfo = (GetCommunityInfo) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			GetCommunityInfoOk getCommunityInfoOk = new GetCommunityInfoOk(data.getSessionId(), data.getSerial());
			Consortia consortia = (Consortia) ServiceManager.getManager().getConsortiaService()
					.get(Consortia.class, getCommunityInfo.getCommunityId());
			if (consortia == null) {
				throw new NullPointerException();// 如果传过来参数有问题，找不到公会，抛空指针异常
			}
			consortia.check();
			getCommunityInfoOk.setCommunityId(consortia.getId());
			getCommunityInfoOk.setCommunityName(consortia.getName());
			getCommunityInfoOk.setPresidentId(consortia.getPresident().getId());
			WorldPlayer p = ServiceManager.getManager().getPlayerService().getWorldPlayerById(consortia.getPresident().getId());
			getCommunityInfoOk.setPresidentName(p.getName());
			getCommunityInfoOk.setMemberCount(ServiceManager.getManager().getPlayerSinConsortiaService().getMemberNum(consortia.getId()));
			getCommunityInfoOk.setTotalMember(consortia.getTotalMember());
			getCommunityInfoOk.setLevel(consortia.getLevel());
			getCommunityInfoOk.setPrestige(consortia.getPrestige());
			getCommunityInfoOk.setMoney(consortia.getMoney());
			getCommunityInfoOk.setDeclaration(consortia.getDeclaration());
			if (!consortia.getIsAcceptMember()) {
				getCommunityInfoOk.setIsCanApply(false);
			} else {
				getCommunityInfoOk.setIsCanApply(player.getGuildId() == 0);
			}
			List<Consortia> list = ServiceManager.getManager().getConsortiaService()
					.getHosConsortia(consortia.getHosId(), consortia.getId());
			String[] enemyNameList = new String[list.size()];
			String[] enemyCommunityList = new String[list.size()];
			int index = 0;
			for (Consortia c : list) {
				enemyNameList[index] = c.getId() + ":" + c.getName();
				if (c.getTotalNum() != 0) {
					enemyCommunityList[index] = TipMessages.COMMUNITY_HOS_MESSAGE.replace("ZZ", c.getWinNum().toString())
							.replace("MM", (c.getTotalNum() - c.getWinNum()) + "")
							.replace("NN", (c.getWinNum() * 100 / c.getTotalNum()) + "");
				} else {
					enemyCommunityList[index] = TipMessages.COMMUNITY_HOS_MESSAGE.replace("ZZ", c.getWinNum().toString())
							.replace("MM", (c.getTotalNum() - c.getWinNum()) + "").replace("NN", "0");
				}
				index++;
			}
			getCommunityInfoOk.setEnemyNameList(enemyNameList);
			getCommunityInfoOk.setEnemyCommunityList(enemyCommunityList);
			session.write(getCommunityInfoOk);
			GameLogService.checkGuild(player.getId(), player.getLevel(), consortia.getId(), consortia.getLevel(), consortia.getPrestige(),
					consortia.getWinNum());
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_COMMUNITYINFO_MESSAGE, data.getSerial(), data.getSessionId(),
					data.getType(), data.getSubType());
		}
	}
}