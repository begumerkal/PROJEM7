package com.wyd.empire.world.server.handler.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.player.GetPlayerInfo;
import com.wyd.empire.protocol.data.player.GetPlayerInfoOk;
import com.wyd.empire.world.bean.MarryRecord;
import com.wyd.empire.world.bean.PetItem;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.bean.PlayerPicture;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 查看玩家信息
 * 
 * @author zengxc
 */
public class GetPlayerInfoHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetPlayerInfoHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer myplayer = session.getPlayer(data.getSessionId());
		GetPlayerInfo getPlayerInfo = (GetPlayerInfo) data;
		int friendId = getPlayerInfo.getPlayerId();
		try {
			if (friendId < 0) {
				friendId = ServiceManager.getManager().getCrossService().getPlayerId(-friendId);
			}
			if (friendId > 0) {
				WorldPlayer player = ServiceManager.getManager().getPlayerService().getWorldPlayerById(friendId);
				GetPlayerInfoOk playerInfo = new GetPlayerInfoOk(data.getSessionId(), data.getSerial());
				Player playerBean = player.getPlayer();
				playerInfo.setId(player.getId());
				playerInfo.setName(player.getName());
				playerInfo.setSex(playerBean.getSex());
				playerInfo.setTitle(player.getPlayerTitle());
				// 公会
				setGuild(player, playerInfo);
				playerInfo.setLevel(player.getLevel());
				playerInfo.setRank(playerBean.getHonorLevel());
				if (player.isVip()) {
					playerInfo.setVipLevel(playerBean.getVipLevel());
				} else {
					playerInfo.setVipLevel(0);
				}
				playerInfo.setWinNum(playerBean.getWinNum());
				playerInfo.setPlayNum(playerBean.getPlayNum());
				playerInfo.setZsLevel(playerBean.getZsLevel());
				playerInfo.setFighting(player.getFighting());
				playerInfo.setForce(player.getForce());
				playerInfo.setHp(player.getMaxHP());
				playerInfo.setArmor(player.getArmor());
				playerInfo.setAttack(player.getAttack());
				playerInfo.setAgility(player.getAgility());
				playerInfo.setDefend(player.getDefend());
				playerInfo.setPhysique(player.getPhysique());
				playerInfo.setCritRate(player.getCrit());
				playerInfo.setInjuryFree(player.getInjuryFree());
				playerInfo.setReduceCrit(player.getReduceCrit());
				playerInfo.setPhysical(player.getMaxPF());
				playerInfo.setWreckDefense(player.getWreckDefense());
				playerInfo.setLuck(player.getLuck());
				PlayerPicture privateInfo = player.getPlayerPicture();
				playerInfo.setAge(privateInfo.getAge());
				playerInfo.setSignature(privateInfo.getSignatureContent());
				playerInfo.setConstellation(privateInfo.getConstellation());
				// 玩家头像
				setPictureUrl(privateInfo, playerInfo);
				playerInfo.setMateName(getMateName(player.getPlayer()));
				boolean isFriend = ServiceManager.getManager().getFriendService().checkPlayerIsFriend(myplayer.getId(), friendId) != null;
				playerInfo.setFriend(isFriend);
				List<PlayerItemsFromShop> playerItemList = getPlayerItem(friendId);
				int size = playerItemList.size(), i = 0;
				int[] itemId = new int[size]; // 玩家身上装备（包括双倍经验卡）
				int[] maintype = new int[size]; // 装备主类型
				int[] subtype = new int[size]; // 装备子类型
				String[] extranInfo = new String[size];
				for (PlayerItemsFromShop playerItem : playerItemList) {
					ShopItem item = playerItem.getShopItem();
					itemId[i] = item.getId();
					maintype[i] = item.getType();
					subtype[i] = item.getSubtype();
					extranInfo[i] = getExtranInfo(playerItem);
					i++;
				}
				playerInfo.setExtranInfo(extranInfo);
				playerInfo.setItemId(itemId);
				playerInfo.setMaintype(maintype);
				playerInfo.setSubtype(subtype);
				playerInfo.setPetInfo(getPetInfo(player.getPlayerPet()));
				// 设置微博信息
				setWeibo(player, playerInfo);
				session.write(playerInfo);
			} else if (myplayer.getBattleId() < 0) {
				ServiceManager
						.getManager()
						.getCrossService()
						.getPlayerInfo(myplayer.getBattleId(),
								ServiceManager.getManager().getCrossService().getCrossPlayerId(myplayer.getId()),
								-getPlayerInfo.getPlayerId());
			} else {
				throw new ProtocolException(ErrorMessages.FRIEND_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.FRIEND_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	private List<PlayerItemsFromShop> getPlayerItem(int playerId) {
		List<PlayerItemsFromShop> playerItems = ServiceManager.getManager().getPlayerItemsFromShopService()
				.getPlayerItemsFromShopByPlayerId(playerId);
		List<PlayerItemsFromShop> playerItemList = new ArrayList<PlayerItemsFromShop>();
		for (PlayerItemsFromShop playerItem : playerItems) {
			if (playerItem.getIsInUsed() || playerItem.getShopItem().getId() == Common.DOUBLEEXPID) {
				playerItemList.add(playerItem);
			}
		}
		return playerItemList;
	}

	private void setPictureUrl(PlayerPicture privateInfo, com.wyd.empire.protocol.data.player.GetPlayerInfoOk playerInfo) {
		String[] testArry = privateInfo.getPictureUrlTest().equals("") ? new String[0] : privateInfo.getPictureUrlTest().split(",");
		// 格式调整非空状态下前后加上","
		String passUrl = "," + privateInfo.getPictureUrlPass() + ",";
		// 处理对应的待审核图片替换已经更改的图片显示
		for (String urls : testArry) {
			// 格式： 待审核替换地址 # 待被替换图片地址 192.168.1.2#192.168.1.8
			String[] url = urls.split("#");
			if (url.length != 2) { // 容错处理
				passUrl = passUrl.replaceAll(url[1] + ",", "");
			}
		}
		// 最终格式处理去除多余的","
		if (!passUrl.equals("") && !passUrl.equals(",")) {
			passUrl = passUrl.substring(1, passUrl.length() - 1);
		} else if (passUrl.equals(",")) {
			passUrl = "";
		}
		playerInfo.setPictureUrl(passUrl);
	}

	// 公会
	private void setGuild(WorldPlayer player, GetPlayerInfoOk playerInfo) {
		String guildName = TipMessages.NULL_STRING, position = TipMessages.NOTJOIN;
		if (player.getGuildId() > 0) {
			// 获得公会对象
			PlayerSinConsortia playerConsortia = ServiceManager.getManager().getPlayerSinConsortiaService()
					.findPlayerSinConsortia(player.getId());
			guildName = playerConsortia.getConsortia().getName();
			position = playerConsortia.getPositionName();
		}
		playerInfo.setGuildName(guildName);
		playerInfo.setPosition(position);
	}

	private String getMateName(Player player) {
		MarryRecord marryRecord = ServiceManager.getManager().getMarryService()
				.getSingleMarryRecordByPlayerId(player.getSex(), player.getId(), 1);
		if (null == marryRecord) {
			return "";
		}
		int mId = 0;
		if (0 == player.getSex()) {
			mId = marryRecord.getWomanId();
		} else {
			mId = marryRecord.getManId();
		}
		return ServiceManager.getManager().getPlayerService().getPlayerById(mId).getName();
	}

	private String getExtranInfo(PlayerItemsFromShop playerItem) {
		Map<String, Object> data = new HashMap<String, Object>();
		ShopItem item = playerItem.getShopItem();
		// 武器装扮
		if (item.isEquipment()) {
			data.put("strongLevel", playerItem.getStrongLevel());
			data.put("starLevel", playerItem.getStars());
			if (item.isWeapon()) {
				data.put("proficiency", playerItem.getSkillful());
				data.put("skillId1", playerItem.getWeapSkill1());
				data.put("skillId2", playerItem.getWeapSkill2());
				data.put("skillLock", playerItem.getSkillLock());
			}
			data.put("attackStoneId", playerItem.getAttackStone());
			data.put("defendStoneId", playerItem.getDefenseStone());
			data.put("specailStoneId", playerItem.getSpecialStone());
			data.put("wreckDefense", playerItem.getpAddWreckDefense());
			data.put("reduceCrit", playerItem.getpAddReduceCrit());
			data.put("force", playerItem.getpAddForce());
			data.put("armor", playerItem.getpAddArmor());
			data.put("agility", playerItem.getpAddAgility());
			data.put("physique", playerItem.getpAddPhysique());
			data.put("luck", playerItem.getpAddLuck());
			data.put("crit", playerItem.getpAddcrit());
			data.put("attack", playerItem.getPAddAttack());
			data.put("defend", playerItem.getPAddDefend());
			data.put("hp", playerItem.getPAddHp());
		}
		return JSONObject.fromObject(data).toString();
	}

	private String getPetInfo(PlayerPet playerPet) {
		Map<String, Object> petInfo = new HashMap<String, Object>();
		if (playerPet != null) {
			PetItem pet = playerPet.getPet();
			petInfo.put("id", pet.getId());
			petInfo.put("name", pet.getName());
			petInfo.put("level", playerPet.getLevel());
			petInfo.put("icon", playerPet.getPetPicture());
			petInfo.put("hp", playerPet.getHP());
			petInfo.put("attack", playerPet.getAttack());
			petInfo.put("defend", playerPet.getDefend());
			petInfo.put("skillName", playerPet.getSkill().getName());
			petInfo.put("skillId", playerPet.getSkill().getId());
		}
		return JSONObject.fromObject(petInfo).toString();
	}

	// 微博信息
	private void setWeibo(WorldPlayer player, com.wyd.empire.protocol.data.player.GetPlayerInfoOk playerInfo) {
		String[] wbIdSplits = player.getPlayer().getWbUserId().split(",");
		String[] wbIconsSplit = player.getPlayer().getWbUserIcon().split(",");
		String wbId = "", wbIcon = "";
		for (String wbIdStr : wbIdSplits) {
			String[] wbIdSplit = wbIdStr.split("=");
			wbId = wbIdSplit[1];
			if (wbId.length() > 3 && !"null".equalsIgnoreCase(wbId)) {
				break;
			}
		}
		for (String wbIconStr : wbIconsSplit) {
			String[] wbIconSplit = wbIconStr.split("=");
			wbIcon = wbIconSplit[1];
			if (wbIcon.length() > 10) {
				break;
			}
		}
		playerInfo.setWeibo(wbId + "," + wbIcon);
	}
}
