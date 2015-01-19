package com.wyd.empire.nearby.cache.service;

import java.util.HashMap;
import java.util.Map;

import com.wyd.empire.nearby.bean.PlayerInfo;
import com.wyd.empire.nearby.nenum.BeanStatus;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.nearby.vo.PlayerVo;

public class PlayerInfoManager extends CacheService{
	private int maxId;
	private Map<Integer, PlayerVo> playerVoMap = new HashMap<Integer, PlayerVo>();
	
	public void init(){
		super.start(10000);
		maxId = ServiceManager.getManager().getNearbyService().getMaxId(PlayerInfo.class);
	}
	
	private synchronized int createId(){
		return maxId++;
	}
	
	/**
	 * 创建玩家附加信息
	 * @param serviceId
	 * @param playerId
	 * @param playerName
	 * @param avatarURL
	 * @param sex
	 * @param fighting
	 * @param longitude
	 * @param latitude
	 * @return 玩家附加信息id
	 */
	public PlayerInfo createPlayerInfo(int serviceId, int playerId, String playerName, String avatarURL, String suitHead, String suitFace, byte sex, int fighting, int longitude, int latitude){
		PlayerInfo playerInfo = new PlayerInfo();
		playerInfo.setId(createId());
		playerInfo.setServiceId(serviceId);
		playerInfo.setPlayerId(playerId);
		playerInfo.setAvatarURL(avatarURL);
		playerInfo.setSex(sex);
		playerInfo.setPlayerName(playerName);
		playerInfo.setFighting(fighting);
		playerInfo.setLongitude(longitude);
		playerInfo.setLatitude(latitude);
		playerInfo.setSuitHead(suitHead);
		playerInfo.setSuitFace(suitFace);
		playerInfo.setOnline(true);
		setBeanStatus(playerInfo, BeanStatus.Create);
		return playerInfo;
	}
	
	/**
	 * 更新玩家附加信息
	 * @param myInfoId
	 * @param playerName
	 * @param avatarURL
	 * @param downloadURL
	 * @param suitHead
	 * @param suitFace
	 * @param fighting
	 * @param longitude
	 * @param latitude
	 */
    public void updatePlayerInfo(int myInfoId, String playerName, String avatarURL, String suitHead, String suitFace, int fighting, int longitude, int latitude) {
		PlayerInfo playerInfo = (PlayerInfo) getBean(PlayerInfo.class, myInfoId);
		if (null != playerInfo) {
		    playerInfo.setOnline(true);
			boolean update = false;
			if(!playerInfo.getPlayerName().equals(playerName)){
				playerInfo.setPlayerName(playerName);
				update = true;
			}
			if(!playerInfo.getAvatarURL().equals(avatarURL)){
				playerInfo.setAvatarURL(avatarURL);
				update = true;
			}
			if(!playerInfo.getSuitHead().equals(suitHead)){
                playerInfo.setSuitHead(suitHead);
                update = true;
            }
			if(!playerInfo.getSuitFace().equals(suitFace)){
                playerInfo.setSuitFace(suitFace);
                update = true;
            }
			if(playerInfo.getFighting()!=fighting){
				playerInfo.setFighting(fighting);
				update = true;
			}
			if(playerInfo.getLongitude()!=longitude){
				playerInfo.setLongitude(longitude);
				update = true;
			}
			if(playerInfo.getLatitude()!=latitude){
				playerInfo.setLatitude(latitude);
				update = true;
			}
			if(update){
				setBeanStatus(playerInfo, BeanStatus.Update);
			}
		}
	}
	
	/**
	 * 玩家增加附近好友
	 * @param myInfoId 
	 * @param friendId
	 */
    public int addFriend(int myInfoId, int friendId) {
        PlayerVo playerVo = getPlayerVo(myInfoId);
        if (null != playerVo) {
            PlayerInfo playerInfo = playerVo.getMyInfo();
            synchronized (playerInfo) {
                if (playerVo.getFriendList().size() < Integer.parseInt(ServiceManager.getManager().getConfiguration().getValue("maxfriendcount"))) {
                    String fridedIdString = "," + playerInfo.getFriendId() + ",";
                    if (fridedIdString.indexOf("," + friendId + ",") < 0) {
                        fridedIdString = playerInfo.getFriendId() + "," + friendId;
                        if (fridedIdString.startsWith(",")) fridedIdString = fridedIdString.substring(1);
                        playerInfo.setFriendId(fridedIdString);
                        setBeanStatus(playerInfo, BeanStatus.Update);
                        updateFriendList(myInfoId);
                    }
                }
            }
            return playerInfo.getPlayerId();
        }
        return 0;
    }
	
	/**
	 * 移除附近好友
	 * @param myInfoId
	 * @param friendId
	 */
    public int removeFriend(int myInfoId, int friendId) {
        PlayerInfo playerInfo = (PlayerInfo) getBean(PlayerInfo.class, myInfoId);
        if (null != playerInfo) {
            synchronized (playerInfo) {
                String fridedIdString = "," + playerInfo.getFriendId() + ",";
                fridedIdString = fridedIdString.replace("," + friendId + ",", ",");
                fridedIdString = fridedIdString.length() > 1 ? fridedIdString.substring(1, fridedIdString.length() - 1) : "";
                playerInfo.setFriendId(fridedIdString);
                setBeanStatus(playerInfo, BeanStatus.Update);
                updateFriendList(myInfoId);
            }
            return playerInfo.getPlayerId();
        }
        return 0;
    }
	
	/**
	 * 获取玩家的相关信息
	 * @param playerId
	 * @return
	 */
	public PlayerVo getPlayerVo(int playerId){
		PlayerVo playerVo = playerVoMap.get(playerId);
		if(null==playerVo){
			PlayerInfo playerInfo = (PlayerInfo) getBean(PlayerInfo.class, playerId);
			if (null != playerInfo) {
				playerVo = new PlayerVo(playerInfo);
				playerVoMap.put(playerId, playerVo);
			}
		}
		return playerVo;
	}
	
	public void updateFriendList(int playerId){
		PlayerVo playerVo = playerVoMap.get(playerId);
		if(null!=playerVo){
			playerVo.initfriendList();
		}
	}
}
