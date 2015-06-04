package com.wyd.empire.world.server.handler.system;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.system.GetPayAppReward;
import com.wyd.empire.protocol.data.system.GetPayAppRewardOk;
import com.wyd.empire.world.Client;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.bean.PayAppReward;
import com.wyd.empire.world.bean.PlayerPayAppReward;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPayAppRewardService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取奖励
 * 
 * @author zengxc
 * 
 */
public class GetPayAppRewardHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetPayAppRewardHandler.class);

	public AbstractData handle(AbstractData data) throws Exception {
		GetPayAppReward getPayAppReward = (GetPayAppReward) data;
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		synchronized (player) {
			String code = getPayAppReward.getCode();
			String account = getAccountId(player.getClient());
			int playerId = player.getId();
			int sex = player.getPlayer().getSex();
			String itemIds = "";
			// 获得特殊标示
			Map<String, Integer> map = ServiceManager.getManager().getVersionService().getSpecialMark();
			boolean state = map.get("bmAppReward") == null || map.get("bmAppReward") < 1;
			List<PayAppReward> rewards = null;
			if (!state) {
				IPayAppRewardService server = ServiceManager.getManager().getPayAppRewardService();
				state = server.isExistCode(code, account, playerId);
				if (!state) {
					String title = "", content = "";
					rewards = server.findAllReward(sex);
					for (PayAppReward reward : rewards) {
						int itemId = reward.getShopItem().getId();
						server.givenItems(player, reward.getCount(), reward.getDays(), itemId, reward.getStrongLevel());
						itemIds += itemId + ",";
						title = reward.getMailTitle();
						content = reward.getMailContent();
					}
					PlayerPayAppReward log = new PlayerPayAppReward();
					log.setPlayerId(playerId);
					log.setAccount(account);
					log.setCode(code);
					log.setItemIds(itemIds);
					server.save(log);
					sendMail(player.getId(), title, content);
				}
			}
			GetPayAppRewardOk ok = new GetPayAppRewardOk();
			session.write(ok);
			return null;
		}
	}

	private String getAccountId(Client client) {
		if (client == null)
			return "";
		String name = client.getName();
		return name.substring(name.indexOf("_") + 1);
	}

	private void sendMail(int playerId, String title, String content) {
		Mail mail = new Mail();
		mail.setContent(content);
		mail.setIsRead(false);
		mail.setReceivedId(playerId);
		mail.setSendId(0);
		mail.setSendName(TipMessages.SYSNAME_MESSAGE);
		mail.setSendTime(new Date());
		mail.setTheme(title);
		mail.setType(1);
		mail.setBlackMail(false);
		mail.setIsStick(Common.IS_STICK);
		ServiceManager.getManager().getMailService().saveMail(mail, null);
	}
}
