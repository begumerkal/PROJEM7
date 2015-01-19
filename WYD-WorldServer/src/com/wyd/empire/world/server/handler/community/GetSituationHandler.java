package com.wyd.empire.world.server.handler.community;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.GetSituation;
import com.wyd.empire.protocol.data.community.SendSituation;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 
 * 类 <code> GetCommunityInfoHandler</code>Protocol.COMMUNITY
 * _GetCommunityInfoHandler获得工会信息协议处理
 * 
 * @since JDK 1.6
 * 
 */
public class GetSituationHandler implements IDataHandler {
	private Logger log;

	public GetSituationHandler() {
		this.log = Logger.getLogger(GetSituationHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GetSituation getSituation = (GetSituation) data;
		try {
			SendSituation sendSituation = new SendSituation(data.getSessionId(), data.getSerial());

			Consortia consortia = (Consortia) ServiceManager.getManager().getConsortiaService()
					.get(Consortia.class, getSituation.getCommunityId());
			if (consortia == null) {
				throw new NullPointerException();// 如果传过来参数有问题，找不到公会，抛空指针异常
			}
			consortia.check();

			// int todayWin =
			// ServiceManager.getManager().getConsortiaService().getTodayConsortiaBattle(1);
			// int loseWin =
			// ServiceManager.getManager().getConsortiaService().getTodayConsortiaBattle(0);

			String totalSituation = "";
			if (consortia.getTotalNum() != 0) {
				totalSituation = TipMessages.COMMUNITY_HOS_MESSAGE.replace("ZZ", consortia.getWinNum().toString())
						.replace("MM", (consortia.getTotalNum() - consortia.getWinNum()) + "")
						.replace("NN", (consortia.getWinNum() * 100 / consortia.getTotalNum()) + "");
			} else {
				totalSituation = TipMessages.COMMUNITY_HOS_MESSAGE.replace("ZZ", consortia.getWinNum().toString())
						.replace("MM", (consortia.getTotalNum() - consortia.getWinNum()) + "").replace("NN", "0");
			}

			sendSituation.setTotalSituation(totalSituation);

			String daySituation = "";

			if (consortia.getTodayNum() != 0) {
				daySituation = TipMessages.COMMUNITY_HOS_MESSAGE.replace("ZZ", consortia.getTodayWin() + "")
						.replace("MM", (consortia.getTodayNum() - consortia.getTodayWin()) + "")
						.replace("NN", (consortia.getTodayWin() * 100 / consortia.getTodayNum()) + "");
			} else {
				daySituation = TipMessages.COMMUNITY_HOS_MESSAGE.replace("ZZ", "0").replace("MM", "0").replace("NN", "0");
			}

			sendSituation.setDaySituation(daySituation);

			List<Consortia> list = ServiceManager.getManager().getConsortiaService()
					.getHosConsortia(consortia.getHosId(), consortia.getId());
			String[] enemyCommunityList = new String[list.size()];
			String[] enemyNameList = new String[list.size()];
			int index = 0;
			for (Consortia c : list) {
				enemyNameList[index] = c.getId() + ":" + c.getName();

				if (c.getTotalNum() != 0) {
					enemyCommunityList[index] = TipMessages.COMMUNITY_HOS_MESSAGE.replace("ZZ", c.getWinNum().toString())
							.replace("MM", (c.getTotalNum() - c.getWinNum()) + "")
							.replace("NN", (c.getWinNum() * 100 / c.getTotalNum()) + "");
				} else {
					enemyCommunityList[index] = TipMessages.COMMUNITY_HOS_MESSAGE.replace("ZZ", "0").replace("MM", "0").replace("NN", "0");
				}

				index++;
			}
			sendSituation.setEnemyNameList(enemyNameList);
			sendSituation.setEnemySituationList(enemyCommunityList);
			if (list.size() == 0) {
				sendSituation.setEnemyCommunityId(-1);
			} else {
				sendSituation.setEnemyCommunityId(consortia.getHosId());
			}

			session.write(sendSituation);

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_COMMUNITYINFO_MESSAGE, data.getSerial(), data.getSessionId(),
					data.getType(), data.getSubType());
		}
	}
}