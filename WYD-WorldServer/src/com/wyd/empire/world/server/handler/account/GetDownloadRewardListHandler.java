package com.wyd.empire.world.server.handler.account;

import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.account.GetDownloadRewardList;
import com.wyd.empire.protocol.data.account.GetDownloadRewardListOK;
import com.wyd.empire.world.bean.DownloadReward;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.common.util.ServiceUtils.RewardInfo;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取分包下载奖励
 * 
 * @author Administrator
 */
public class GetDownloadRewardListHandler implements IDataHandler {
	private Logger log;

	public GetDownloadRewardListHandler() {
		this.log = Logger.getLogger(GetDownloadRewardListHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GetDownloadRewardList getDownloadRewardList = (GetDownloadRewardList) data;
		int playerLevel = getDownloadRewardList.getPlayerLevel();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			DownloadReward downloadRewardList = ServiceManager.getManager().getDownloadRewardService().findDownloadReward(playerLevel);
			if (downloadRewardList == null)
				return;
			List<RewardInfo> rewardList = ServiceUtils
					.getRewardInfo(downloadRewardList.getReward(), player.getPlayer().getSex().intValue());
			int[] itemsId = new int[rewardList.size()];
			int[] itemsNum = new int[rewardList.size()];
			for (int i = 0; i < rewardList.size(); i++) {
				itemsId[i] = rewardList.get(i).getItemId();
				itemsNum[i] = rewardList.get(i).getCount();
			}
			GetDownloadRewardListOK ok = new GetDownloadRewardListOK(data.getSessionId(), data.getSerial());
			ok.setItemsId(itemsId);
			ok.setItemsNum(itemsNum);
			session.write(ok);

		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY)) {
				this.log.error(ex, ex);
			}
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}
}