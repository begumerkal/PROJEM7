package com.wyd.empire.world.server.handler.account;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.GetRoleListOK;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.entity.mongo.Player;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.model.Client;
import com.wyd.empire.world.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取角色列表
 * 
 * @since JDK 1.6
 */
public class GetRoleListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetRoleListHandler.class);

	// 读取角色列表，但是现在只处理了新加角色
	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		Client client = session.getClient(data.getSessionId());
		if ((client == null) || (!(client.isLogin())))
			return null;
		try {
			// long time = System.currentTimeMillis();
			List<Player> list = ServiceManager.getManager().getPlayerService().getPlayerList(client.getAccountId());
			// log.info("GetRoleList AccountId:"+client.getAccountId()+"-----------------time:"+(System.currentTimeMillis()-time));
			int playerCount = 0;
			if (list != null) {
				playerCount = list.size();
			}
			String[] nickName = new String[playerCount]; // 角色名称
			int[] money = new int[playerCount]; // 钱
			int[] lv = new int[playerCount]; // 玩家等级
			int[] lvExp = new int[playerCount]; // 经验
			int[] vipExp = new int[playerCount]; // vip经验
			int[] vipLv = new int[playerCount]; // vip等级
			String[] property = new String[playerCount]; // 属性
			int[] fight = new int[playerCount]; // 战斗力

			for (int i = 0; i < playerCount; i++) {
				Player p = list.get(i);
				nickName[i] = p.getNickname();
				money[i] = p.getMoney();
				lv[i] = p.getLv();
				lvExp[i] = p.getLvExp();
				vipLv[i] = p.getVipLv();
				vipExp[i] = p.getVipExp();
				property[i] = p.getProperty();
				fight[i] = p.getFight();
			}

			GetRoleListOK sendActorList = new GetRoleListOK(data.getSessionId(), data.getSerial());
			sendActorList.setPlayerCount(playerCount);
			sendActorList.setNickName(nickName);
			sendActorList.setMoney(money);
			sendActorList.setLv(lv);
			sendActorList.setLvExp(lvExp);
			sendActorList.setVipLv(vipLv);
			sendActorList.setVipExp(vipExp);
			sendActorList.setProperty(property);
			sendActorList.setFight(fight);

			return sendActorList;
			// session.write(sendActorList);
		} catch (Exception e) {
			if (!e.getMessage().startsWith(Common.ERRORKEY)) {
				this.log.error(e, e);
			}
			throw new ProtocolException(ErrorMessages.LOGIN_GRLFAIL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}