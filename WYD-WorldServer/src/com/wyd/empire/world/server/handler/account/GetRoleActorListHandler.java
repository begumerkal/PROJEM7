package com.wyd.empire.world.server.handler.account;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.SendRoleActorList;
import com.wyd.empire.world.Client;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.entity.mysql.Player;
import com.wyd.empire.world.entity.mysql.PlayerPet;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取角色列表
 * 
 * @since JDK 1.6
 */
public class GetRoleActorListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetRoleActorListHandler.class);

	// 读取角色列表，但是现在只处理了新加角色
	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		Client client = session.getClient(data.getSessionId());
		if ((client == null) || (!(client.isLogin())))
			return null;
		try {
			// long time = System.currentTimeMillis();
			List<Player> list = ServiceManager.getManager().getPlayerService().getPlayerList(client.getAccountId());
			// log.info("GetRoleActorList AccountId:"+client.getAccountId()+"-----------------time:"+(System.currentTimeMillis()-time));
			SendRoleActorList sendActorList = new SendRoleActorList(data.getSessionId(), data.getSerial());
			int playerCount = 0;
			if (list != null) {
				playerCount = list.size();
			}
			String[] playerName = new String[playerCount];
			int[] playerLevel = new int[playerCount];
			int[] playerSex = new int[playerCount];
			int[] playerDiamond = new int[playerCount];
			int[] playerGold = new int[playerCount];
			int[] zsLevel = new int[playerCount];
			boolean[] doubleCard = new boolean[playerCount];
			int[] vipLevel = new int[playerCount];
			int[] playerRank = new int[playerCount];
			String[] headMessage = new String[playerCount];
			String[] faceMessage = new String[playerCount];
			String[] bodyMessage = new String[playerCount];
			String[] weapMessage = new String[playerCount];
			String[] wingMessage = new String[playerCount];
			String[] petMessage = new String[playerCount];
			int[] weapProf = new int[playerCount];
			byte[] weapLevel = new byte[playerCount];
			byte[] weapSkillType = new byte[playerCount];
 
			Player player;
			for (int i = 0; i < playerCount; i++) {
				player = list.get(i);
				playerName[i] = player.getName();
				playerLevel[i] = player.getLevel();
				playerSex[i] = player.getSex().intValue();
				playerDiamond[i] = player.getAmount();
				playerGold[i] = player.getMoneyGold();
				zsLevel[i] = player.getZsLevel();
 
	 
					doubleCard[i] = false;
 
				if (null == player.getVipTime() || System.currentTimeMillis() > player.getVipTime().getTime()) {
					vipLevel[i] = 0;
				} else {
					vipLevel[i] = player.getVipLevel();
				}
				playerRank[i] = player.getHonorLevel();

				headMessage[i] = "";
				faceMessage[i] = "";
 
					bodyMessage[i] = "";
 
					weapMessage[i] = "";
 
					wingMessage[i] = "";
 
					petMessage[i] = "";
			}
			
			
			sendActorList.setPlayerCount(playerCount);
			sendActorList.setPlayerName(playerName);
			sendActorList.setPlayerLevel(playerLevel);
			sendActorList.setPlayerSex(playerSex);
			sendActorList.setPlayerDiamond(playerDiamond);
			sendActorList.setPlayerGold(playerGold);
			sendActorList.setZsLevel(zsLevel);
			sendActorList.setDoubleCard(doubleCard);
			sendActorList.setVipLevel(vipLevel);
			sendActorList.setPlayerRank(playerRank);
			sendActorList.setHeadMessage(headMessage);
			sendActorList.setFaceMessage(faceMessage);
			sendActorList.setBodyMessage(bodyMessage);
			sendActorList.setWeapMessage(weapMessage);
			sendActorList.setWingMessage(wingMessage);
			sendActorList.setPetMessage(petMessage);
			sendActorList.setWeapProf(weapProf);
			sendActorList.setWeapLevel(weapLevel);
			sendActorList.setWeapSkillType(weapSkillType);
			return sendActorList;
//			session.write(sendActorList);
//			list = null;
		} catch (Exception e) {
			if (!e.getMessage().startsWith(Common.ERRORKEY)) {
				this.log.error(e, e);
			}
			throw new ProtocolException(ErrorMessages.LOGIN_GRLFAIL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}