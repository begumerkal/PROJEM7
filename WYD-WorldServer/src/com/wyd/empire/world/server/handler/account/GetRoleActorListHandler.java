package com.wyd.empire.world.server.handler.account;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.SendRoleActorList;
import com.wyd.empire.world.Client;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.common.util.Common;
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
			List<Player> list = ServiceManager.getManager().getPlayerService().getPlayerList(client.getGameAccountId());
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
			PlayerItemsFromShop item_head = null; // 头部装备
			PlayerItemsFromShop item_face = null; // 脸部装备
			PlayerItemsFromShop item_body = null; // 身体装备
			PlayerItemsFromShop item_weapon = null; // 武器装备
			PlayerItemsFromShop item_wing = null; // 翅膀装备
			Player player;
			for (int i = 0; i < playerCount; i++) {
				player = list.get(i);
				playerName[i] = player.getName();
				playerLevel[i] = player.getLevel();
				playerSex[i] = player.getSex().intValue();
				playerDiamond[i] = player.getAmount();
				playerGold[i] = player.getMoneyGold();
				zsLevel[i] = player.getZsLevel();
				List<PlayerItemsFromShop> playerItems = ServiceManager.getManager().getPlayerItemsFromShopService()
						.getPlayerItemsFromShopByPlayerId(player.getId());
				for (PlayerItemsFromShop item : playerItems) {
					if (!item.getIsInUsed())
						continue;
					if (item.getShopItem().isWeapon()) {
						item_weapon = item;
					} else if (item.getShopItem().isBody()) {
						item_body = item;
					} else if (item.getShopItem().isWing()) {
						item_wing = item;
					} else if (item.getShopItem().isFace()) {
						item_face = item;
					} else if (item.getShopItem().isHead()) {
						item_head = item;
					}
				}
				PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService()
						.uniquePlayerItem(player.getId(), Common.DOUBLEEXPID);
				if (null != pifs && pifs.getPLastTime() > 0) {
					doubleCard[i] = true;
				} else {
					doubleCard[i] = false;
				}
				if (null == player.getVipTime() || System.currentTimeMillis() > player.getVipTime().getTime()) {
					vipLevel[i] = 0;
				} else {
					vipLevel[i] = player.getVipLevel();
				}
				playerRank[i] = player.getHonorLevel();

				if (null != item_head) {
					headMessage[i] = item_head.getShopItem().getAnimationIndexCode();
				} else {
					headMessage[i] = "";
				}
				if (null != item_face) {
					faceMessage[i] = item_face.getShopItem().getAnimationIndexCode();
				} else {
					faceMessage[i] = "";
				}
				if (null != item_body) {
					bodyMessage[i] = item_body.getShopItem().getAnimationIndexCode();
				} else {
					bodyMessage[i] = "";
				}
				if (null != item_weapon) {
					weapMessage[i] = item_weapon.getShopItem().getAnimationIndexCode();
					weapSkillType[i] = item_weapon.getShopItem().getType();
					if (null != item_weapon) {
						weapProf[i] = item_weapon.getSkillful();
						weapLevel[i] = (byte) item_weapon.getStrongLevel();
					}
				} else {
					weapMessage[i] = "";
				}
				if (null != item_wing) {
					wingMessage[i] = item_wing.getShopItem().getAnimationIndexCode();
				} else {
					wingMessage[i] = "";
				}
				PlayerPet playerPet = ServiceManager.getManager().getPlayerPetService().getInUsePet(player.getId());
				if (null != playerPet && null != playerPet.getSkill()) {
					petMessage[i] = playerPet.getSkill().getId().toString();
				} else {
					petMessage[i] = "";
				}
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