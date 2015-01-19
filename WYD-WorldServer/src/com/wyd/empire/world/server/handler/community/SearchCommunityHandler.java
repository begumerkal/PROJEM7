package com.wyd.empire.world.server.handler.community;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.GetCommunityInfoOk;
import com.wyd.empire.protocol.data.community.SearchCommunity;
import com.wyd.empire.protocol.data.community.SearchNotFound;
import com.wyd.empire.world.bean.Consortia;
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
 * 类 <code> SearchCommunityHandler</code>Protocol.COMMUNITY
 * _SearchCommunity搜索公会协议处理
 * 
 * @since JDK 1.6
 */
public class SearchCommunityHandler implements IDataHandler {
	private Logger log;

	public SearchCommunityHandler() {
		this.log = Logger.getLogger(SearchCommunityHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		SearchCommunity searchCommunity = (SearchCommunity) data;
		try {
			// 返回公会信息
			Consortia consortia = ServiceManager.getManager().getConsortiaService().getConsortiaById(searchCommunity.getCommunityId());
			if (consortia == null) {
				// 没有找到搜索的公会
				SearchNotFound searchNotFound = new SearchNotFound(data.getSessionId(), data.getSerial());
				session.write(searchNotFound);
			} else {
				GetCommunityInfoOk getCommunityInfoOk = new GetCommunityInfoOk(data.getSessionId(), data.getSerial());
				consortia.check();
				getCommunityInfoOk.setCommunityId(consortia.getId());
				getCommunityInfoOk.setCommunityName(consortia.getName());
				getCommunityInfoOk.setPresidentId(consortia.getPresident().getId());
				WorldPlayer p = ServiceManager.getManager().getPlayerService().getWorldPlayerById(consortia.getPresident().getId());
				getCommunityInfoOk.setPresidentName(p.getName());
				getCommunityInfoOk.setMemberCount(ServiceManager.getManager().getPlayerSinConsortiaService()
						.getMemberNum(consortia.getId()));
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
			}
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_SEARCH_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}