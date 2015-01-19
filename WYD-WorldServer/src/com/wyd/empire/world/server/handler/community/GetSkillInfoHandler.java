package com.wyd.empire.world.server.handler.community;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.GetSkillInfo;
import com.wyd.empire.protocol.data.community.GetSkillInfoOk;
import com.wyd.empire.world.bean.ConsortiaSkill;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.item.DailyRewardVo;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.DailyActivityService;
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
public class GetSkillInfoHandler implements IDataHandler {
	private Logger log;

	public GetSkillInfoHandler() {
		this.log = Logger.getLogger(GetSkillInfoHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GetSkillInfo getSkillInfo = (GetSkillInfo) data;
		try {
			GetSkillInfoOk getSkillInfoOk = new GetSkillInfoOk(data.getSessionId(), data.getSerial());
			ConsortiaSkill cs = (ConsortiaSkill) ServiceManager.getManager().getConsortiaService()
					.get(ConsortiaSkill.class, getSkillInfo.getSkillId());
			getSkillInfoOk.setSkillDetail(TipMessages.DETAILNAME + cs.getComSkillName() + "\n" + TipMessages.DETAILINFO + cs.getDesc()
					+ "\n" + TipMessages.DETAILLASTTIME);
			getSkillInfoOk.setSkillBuyByContribution(cs.getCostUseContribution().toString());
			DailyActivityService dailyActivityService = ServiceManager.getManager().getDailyActivityService();
			DailyRewardVo dailyRewardVo = dailyActivityService.getBuyConsortiaSkillReward();
			int ticket = dailyActivityService.getRewardedVal(cs.getCostUseTickets(), dailyRewardVo.getSubTicket());
			getSkillInfoOk.setSkillBuyByDiamond(String.valueOf(ticket));
			int gold = dailyActivityService.getRewardedVal(cs.getCostUseGold(), dailyRewardVo.getSubGold());
			getSkillInfoOk.setSkillBuyByGold(String.valueOf(gold));
			getSkillInfoOk.setSkillIcon(cs.getIcon());
			session.write(getSkillInfoOk);

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_GETLIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}