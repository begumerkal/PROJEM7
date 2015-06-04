package com.wyd.empire.world.server.handler.system;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.system.GetPayAppRewardList;
import com.wyd.empire.protocol.data.system.GetPayAppRewardListOk;
import com.wyd.empire.world.Client;
import com.wyd.empire.world.bean.PayAppReward;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPayAppRewardService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取奖励列表 code 机器识别码 account 帐号 playerId 角色ID
 * 
 * @author zengxc
 * 
 */
public class GetPayAppRewardListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetPayAppRewardHandler.class);

	public AbstractData handle(AbstractData data) throws Exception {
		GetPayAppRewardList getPayAppRewardList = (GetPayAppRewardList) data;
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		String code = getPayAppRewardList.getCode();
		String account = getAccountId(player.getClient());
		int playerId = player.getId();
		int size = 0;
		// 获得特殊标示
		Map<String, Integer> map = ServiceManager.getManager().getVersionService().getSpecialMark();
		boolean state = map.get("bmAppReward") == null || map.get("bmAppReward") < 1;
		List<PayAppReward> rewards = null;
		if (!state) {
			IPayAppRewardService server = ServiceManager.getManager().getPayAppRewardService();
			state = server.isExistCode(code, account, playerId);
			if (!state) {
				int sex = player.getPlayer().getSex();
				rewards = server.findAllReward(sex);
				size = rewards.size();
			}
		}
		String[] name = new String[size];
		String[] icon = new String[size];
		int[] count = new int[size];
		int[] days = new int[size];
		int[] strongLevel = new int[size];
		if (!state && size > 0) {
			for (int i = 0; i < size; i++) {
				PayAppReward reward = rewards.get(i);
				name[i] = reward.getShopItem().getName();
				icon[i] = reward.getShopItem().getIcon();
				count[i] = reward.getCount();
				days[i] = reward.getDays();
				strongLevel[i] = reward.getStrongLevel();
			}
		}
		GetPayAppRewardListOk ok = new GetPayAppRewardListOk(data.getSessionId(), data.getSerial());
		ok.setState(state ? 1 : 0);
		ok.setName(name);
		ok.setIcon(icon);
		ok.setCount(count);
		ok.setDays(days);
		ok.setStrongLevel(strongLevel);
		session.write(ok);
		return null;
	}

	private String getAccountId(Client client) {
		if (client == null)
			return "";
		String name = client.getName();
		return name.substring(name.indexOf("_") + 1);
	}
}
