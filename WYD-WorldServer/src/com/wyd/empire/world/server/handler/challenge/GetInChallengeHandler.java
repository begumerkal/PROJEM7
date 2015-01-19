package com.wyd.empire.world.server.handler.challenge;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.challenge.GetInChallengeOk;
import com.wyd.empire.world.bean.ChallengeRecord;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.IntegralService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 进入挑战赛界面
 * 
 * @author Administrator
 */
public class GetInChallengeHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetInChallengeHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			if (null == player)
				return;
			String challengeReward = ServiceManager.getManager().getVersionService().getVersion().getChallengeReward();
			ChallengeRecord cr = ServiceManager.getManager().getChallengeSerService().getChallengeRecord(player.getId());
			GetInChallengeOk getInChallengeOk = new GetInChallengeOk(data.getSessionId(), data.getSerial());
			getInChallengeOk.setPlayerId(player.getId());
			if (null != cr) {
				getInChallengeOk.setIntegral(cr.getIntegral());
				getInChallengeOk.setLastIntegral(cr.getIntegral() - cr.getLastIntegral());
			} else {
				getInChallengeOk.setIntegral(0);
				getInChallengeOk.setLastIntegral(0);
			}
			// 奖励图标
			String[] challengeRewardStr = challengeReward.split(",");
			int[] reward = new int[4];
			int index = 0;
			ShopItem si;
			for (String str : challengeRewardStr) {
				si = ServiceManager.getManager().getShopItemService().getShopItemById(Integer.parseInt(str));
				if (si.getSex().intValue() == player.getPlayer().getSex().intValue() || si.getSex().intValue() == 2) {
					reward[index] = si.getId();
					index++;
				}
				if (index == 4) {
					break;
				}
			}
			if (null == Common.CHALLENGE_RANK || Common.CHALLENGE_RANK.length() == 0) {
				ServiceManager.getManager().getThreadIntegralService().runOrder(IntegralService.INTEGRAL_TOP_THREE);
			}
			String challengeRank = Common.CHALLENGE_RANK;
			// 排位前三的玩家
			String[] name = {"", "", ""};
			String[] teamName = {"", "", ""};
			String[] challengeRankStr = challengeRank.split("#");
			index = 0;
			if (challengeRank.length() != 0) {
				for (String str : challengeRankStr) {
					String[] tmp = str.split(",");
					name[index] = tmp[0];
					teamName[index] = tmp[1];
					index++;
				}
			}
			getInChallengeOk.setReward(reward);
			getInChallengeOk.setName(name);
			getInChallengeOk.setTeamName(teamName);
			getInChallengeOk.setDetail(TipMessages.CHALLENGE_DETAIL);
			getInChallengeOk.setStartOr(ServiceManager.getManager().getChallengeSerService().isInTime());
			session.write(getInChallengeOk);
			player.setPlayerInChallenge(true);
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
