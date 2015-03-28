package com.wyd.empire.world.server.service.impl;

import com.wyd.empire.protocol.data.nearby.PlayerOffline;
import com.wyd.empire.protocol.data.nearby.PlayerOnline;
import com.wyd.empire.protocol.data.nearby.UpdatePlayerInfo;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.skeleton.NearbySkeleton;
import com.wyd.protocol.data.AbstractData;

public class NearbyService {
	private NearbySkeleton nearbySkeleton;

	public void setNearbySkeleton(NearbySkeleton nearbySkeleton) {
		this.nearbySkeleton = nearbySkeleton;
	}

	public void sendData(AbstractData data) {
		if (null != nearbySkeleton) {
			nearbySkeleton.send(data);
		}
	}

	public boolean isNearbyServiceOpen() {
		return null != nearbySkeleton;
	}

	/**
	 * 玩家上线
	 * 
	 * @param player
	 */
	public void PlayerOnline(WorldPlayer player) {
		if (player.getPlayerInfo().getNearbyId() > 0) {
			PlayerOnline playerOnline = new PlayerOnline();
			playerOnline.setMyInfoId(player.getPlayerInfo().getNearbyId());
			sendData(playerOnline);
		}
	}

	/**
	 * 玩家下线
	 * 
	 * @param player
	 */
	public void PlayerOffline(WorldPlayer player) {
		if (player.getPlayerInfo().getNearbyId() > 0) {
			PlayerOffline playerOffline = new PlayerOffline();
			playerOffline.setMyInfoId(player.getPlayerInfo().getNearbyId());
			sendData(playerOffline);
		}
	}

	public void UpdatePlayerInfo(WorldPlayer player, int longitude, int latitude) {
		if (null != nearbySkeleton) {
			int nearbyId = player.getPlayerInfo().getNearbyId();
			UpdatePlayerInfo updatePlayerInfo = new UpdatePlayerInfo();
			updatePlayerInfo.setMyInfoId(nearbyId);
			updatePlayerInfo.setLatitude(latitude);
			updatePlayerInfo.setLongitude(longitude);
			updatePlayerInfo.setServiceId(WorldServer.config.getServerId());
			updatePlayerInfo.setPlayerId(player.getId());
			String[] pUrl = player.getPlayerPicture().getPictureUrlPass().split(",");
			String avatarURL = "";
			for (int i = 0; i < pUrl.length; i++) {
				if (pUrl[i].length() > 0) {
					avatarURL = pUrl[i];
					break;
				}
			}
			updatePlayerInfo.setAvatarURL(avatarURL);
			updatePlayerInfo.setSex(player.getPlayer().getSex());
			updatePlayerInfo.setPlayerName(player.getName());
			updatePlayerInfo.setFighting(player.getFighting());
			updatePlayerInfo.setSuitHead(player.getSuit_head());
			updatePlayerInfo.setSuitFace(player.getSuit_face());
			nearbySkeleton.send(updatePlayerInfo);
		}
	}
}
