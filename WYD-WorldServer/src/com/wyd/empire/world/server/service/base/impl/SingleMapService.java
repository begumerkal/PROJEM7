package com.wyd.empire.world.server.service.base.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.bean.PlayerSingleMap;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.SingleMapDrop;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.bossmaproom.GuaiPlayer;
import com.wyd.empire.world.common.util.CryptionUtil;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.ISingleMapDao;
import com.wyd.empire.world.item.RewardItemsVo;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.ISingleMapService;
import com.wyd.empire.world.server.service.base.IToolsService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * 
 * The service class for the TabConsortiaright entity.
 */
public class SingleMapService extends UniversalManagerImpl implements ISingleMapService {
	Logger log = Logger.getLogger(SingleMapService.class);
	/**
	 * The dao instance injected by Spring.
	 */
	private ISingleMapDao dao;

	public SingleMapService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static ISingleMapService getInstance(ApplicationContext context) {
		return (ISingleMapService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(ISingleMapDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	/**
	 * 取单人副本可能掉落的物品种类 金币，钻石，徽章
	 */
	@Override
	public List<Integer> getReward(int dropId) {
		List<ShopItem> rewards = dao.getShowItems(dropId);
		List<Integer> ids = new ArrayList<Integer>();
		for (ShopItem item : rewards) {
			ids.add(item.getId());
		}
		return ids;
	}

	@Override
	public PlayerSingleMap getPlayerSingleMap(int playerId, int mapId) {
		return dao.getPlayerSingleMap(playerId, mapId);
	}

	public List<PlayerSingleMap> getPlayerSingleMap(int playerId) {
		return dao.getPlayerSingleMap(playerId);
	}

	@Override
	public PlayerSingleMap savePassTimes(int playerId, int mapId) {
		PlayerSingleMap playerMap = getPlayerSingleMap(playerId, mapId);
		if (playerMap == null) {
			playerMap = new PlayerSingleMap();
			playerMap.setCreateTime(new Date());
			playerMap.setUpdateTime(playerMap.getCreateTime());
			playerMap.setResetTime(playerMap.getCreateTime());
			playerMap.setMapId(mapId);
			playerMap.setPassTimes(1);
			playerMap.setTotalPassTimes(1);
			playerMap.setPlayerId(playerId);
			dao.save(playerMap);
		} else {
			int passTimes = playerMap.getPassTimes();
			passTimes = passTimes > 0 ? passTimes : 0;
			int totalpassTimes = playerMap.getTotalPassTimes();
			passTimes++;
			totalpassTimes++;
			playerMap.setPassTimes(passTimes);
			playerMap.setTotalPassTimes(totalpassTimes);
			playerMap.setUpdateTime(new Date());
			dao.update(playerMap);
		}
		return playerMap;

	}

	private int getAllElitePassTimes(int playerId) {
		List<PlayerSingleMap> playerMapList = dao.findPlayerMapBy(playerId);
		int count = 0;
		for (PlayerSingleMap playerMap : playerMapList) {
			Map map = dao.getMapById(playerMap.getMapId());
			if (map == null)
				continue;
			if (map.getVitalityExpend() == 12) {// 活力消耗12为精英副本
				count += playerMap.getTotalPassTimes();
			}
		}
		return count;
	}

	private int getAllPassTimes(int playerId) {
		List<PlayerSingleMap> playerMapList = dao.findPlayerMapBy(playerId);
		int count = 0;
		for (PlayerSingleMap playerMap : playerMapList) {
			count += playerMap.getTotalPassTimes();
		}
		return count;
	}

	/**
	 * 获取掉落ID dropId2格式:(150,2) 150 表示需要达到的次数，2表示掉落ID
	 * 
	 * @param playerId
	 * @param map
	 * @return
	 */
	@Override
	public int getDropId(int playerId, Map map) {
		String eliteDrop = map.getDropId2();// 精英掉落
		if (!"-1".equals(eliteDrop)) {
			eliteDrop = eliteDrop.replace("(", "").replace(")", "");
			String[] drop_s = eliteDrop.split(",");
			int times = Integer.parseInt(drop_s[0]);
			int id = Integer.parseInt(drop_s[1]);
			if (isEliteDrop(playerId, times)) {
				System.out.println("精英掉落：" + id);
				return id;
			}
		}
		return map.getDropId1();
	}

	private boolean isEliteDrop(int playerId, int times) {
		int passTimes = getAllElitePassTimes(playerId);
		return passTimes % times == 0;
	}

	@Override
	public RewardItemsVo getOneReward(int playerId, int sex, Map map) {
		int dropId = getDropId(playerId, map);
		List<SingleMapDrop> allDropList = getDropList(dropId);
		List<SingleMapDrop> dropList = new ArrayList<SingleMapDrop>();
		// 先按取一个掉落给玩家
		lottery(dropList, allDropList, getAllPassTimes(playerId));
		return createRewardVO(dropList.get(0), sex);
	}

	@Override
	public List<RewardItemsVo> getRewardList(int playerId, int sex, Map map) {
		List<RewardItemsVo> rewardList = new ArrayList<RewardItemsVo>();
		int dropId = getDropId(playerId, map);
		List<SingleMapDrop> allDropList = getDropList(dropId);
		List<SingleMapDrop> dropList = new ArrayList<SingleMapDrop>();
		// 先按取一个掉落给玩家
		lottery(dropList, allDropList, getAllPassTimes(playerId));
		// 再随机取出五个展示用（不按概率）
		for (int i = 0; i < 7; i++) {
			int y = 0;
			while (y < 100) {// 取到重复的要重来
				int random = ServiceUtils.getRandomNum(0, allDropList.size());
				SingleMapDrop singleMapDrop = allDropList.get(random);
				if (!dropList.contains(singleMapDrop)) {
					dropList.add(singleMapDrop);
					break;
				}
				y++;
			}
		}
		// drop转成vo
		for (int i = 0; i < dropList.size(); i++) {
			SingleMapDrop drop = dropList.get(i);
			RewardItemsVo vo = createRewardVO(drop, sex);
			if (i == 0) {// 第一个是给玩家的
				vo.setOwnerId(1);
			}
			rewardList.add(vo);
		}
		// 把第一个物品随机放到数组里
		rewardList = randomSite(rewardList);
		return rewardList;
	}

	/**
	 * 按万分比概率抽取一个物品给玩家
	 * 
	 * @param dropList
	 * @param allDropList
	 * @param totalPassTimes
	 */
	private void lottery(List<SingleMapDrop> dropList, List<SingleMapDrop> allDropList, int totalPassTimes) {
		SingleMapDrop palyerDrop = null;
		int random = ServiceUtils.getRandomNum(0, 10000);
		for (SingleMapDrop drop : allDropList) {
			if (random >= drop.getStartChance() && random < drop.getEndChance()) {
				palyerDrop = drop;
				break;
			}
		}
		dropList.add(palyerDrop);
	}

	private List<RewardItemsVo> randomSite(List<RewardItemsVo> rewardList) {
		List<RewardItemsVo> newVos = new ArrayList<RewardItemsVo>();
		int newSite = ServiceUtils.getRandomNum(0, rewardList.size());
		RewardItemsVo reward = rewardList.get(0);
		RewardItemsVo temp = rewardList.get(newSite);
		RewardItemsVo[] vos = new RewardItemsVo[rewardList.size()];
		vos[0] = temp;
		for (int i = 1; i < rewardList.size(); i++) {
			vos[i] = rewardList.get(i);
			if (i == newSite) {
				vos[i] = reward;
			}
		}
		for (int i = 0; i < rewardList.size(); i++) {
			newVos.add(vos[i]);
		}
		return newVos;
	}

	private RewardItemsVo createRewardVO(SingleMapDrop drop, int sex) {
		RewardItemsVo vo = new RewardItemsVo();
		ShopItem item = null;
		// 0男1女
		if (sex == 0) {
			item = drop.getShopItem1();
		} else {
			item = drop.getShopItem2();
		}
		vo.setItemIcon(item.getIcon());
		vo.setItemName(item.getName());
		vo.setItemId(item.getId());
		vo.setCount(drop.getNum());
		return vo;
	}

	@Override
	public List<SingleMapDrop> getDropList(int dropId) {
		return dao.getDropList(dropId);
	}

	@Override
	public int getCurrBigPointId(int playerId) {
		Map map = dao.getMaxPassMap(playerId);
		if (map == null) {
			return 1;
		} else {
			int pageSize = 5;
			int fullListSize = map.getBossmapSerial();
			int totalSize = fullListSize / pageSize;
			return fullListSize % pageSize > 0 ? totalSize + 1 : totalSize;
		}
	}

	@Override
	public void sysResetTimes() {
		Collection<WorldPlayer> playerList = ServiceManager.getManager().getPlayerService().getOnlinePlayer();
		for (WorldPlayer player : playerList) {
			resetPassTimes(player.getId(), false);
			ServiceManager.getManager().getPlayerBossmapService().resetPassTimes(player.getId(), false);
		}
	}

	@Override
	public void resetPassTimes(int playerId, boolean validate) {
		List<PlayerSingleMap> playerMapList = dao.findPlayerMapBy(playerId);
		Calendar cal = Calendar.getInstance();
		for (PlayerSingleMap playerMap : playerMapList) {
			if (playerMap.getPassTimes() > 0) {
				// 在需要验证时，跳过当天重置过的记录
				if (validate && DateUtil.isSameDate(playerMap.getResetTime(), cal.getTime())) {
					continue;
				}
				playerMap.setPassTimes(0);
				playerMap.setResetTime(cal.getTime());
				dao.update(playerMap);
			}
		}
	}

	/**
	 * 战斗过程检验－防外挂
	 * 
	 * @param data
	 * @return 失败信息，success开头表示验证成功
	 */
	@Override
	public String battleVerify(WorldPlayer player, List<GuaiPlayer> guaiPlayers, byte[] vdata) {
		String request = "success,";
		String logMsg = "";
		String data = "";
		int maxPF = player.getMaxPF();
		try {
			// 解密
			data = CryptionUtil.Decrypt(vdata, ServiceManager.getManager().getConfiguration().getString("deckey"));
			logMsg = data + "\n";
			if (!StringUtils.hasText(data)) {
				return "验证信息为空";
			}
			JSONArray array = JSONArray.fromObject(data);
			IToolsService toolsService = ServiceManager.getManager().getToolsService();
			int[] guaiMaxHps = new int[guaiPlayers.size()];
			boolean[] guaiIsDeads = new boolean[guaiPlayers.size()];
			for (int i = 0; i < guaiPlayers.size(); i++) {
				guaiMaxHps[i] = guaiPlayers.get(i).getMaxHP();
				guaiIsDeads[i] = false;
			}
			if (array == null || array.size() < 1) {
				return "转换JSON失败";
			}
			for (int i = 0; i < array.size(); i++) {
				logMsg += "=========================第" + (i + 1) + "回合=============================\n";
				int skillPF = 0;
				JSONObject json = array.getJSONObject(i);
				JSONArray skills = (JSONArray) json.get("skillProp");// 玩家使用的技能道具的Id数组
				boolean isPlayerBigSkill = json.getBoolean("isPlayerBigSkill");// 玩家大招
				// 计算技能消耗体力是否正常
				for (int y = 0; y < skills.size(); y++) {
					int skillId = skills.getInt(y);
					Tools skill = toolsService.getToolById(skillId);
					skillPF += skill.getConsumePower();
					request += skillId + ",";
				}
				if (skills.size() > 0) {
					request = request.substring(0, request.length() - 1);
				}
				logMsg += "技能消耗了" + skillPF + "/" + maxPF + "体力\n";
				if (skillPF > maxPF) {
					logMsg += "技能消耗体力超过最大体力值\n";
					log.info(logMsg);
					return "技能消耗体力超过最大体力值";
				}
				double attPercent = 1;// 技能损耗或加成百分比
				// 计算炮弹数量是否正常
				int maxbulletCount = 1;
				for (int y = 0; y < skills.size(); y++) {
					int skillId = skills.getInt(y);
					if (skillId == 2) {
						maxbulletCount += 1;
					} else if (skillId == 3) {
						maxbulletCount += 2;
					}
				}
				for (int y = 0; y < skills.size(); y++) {
					int skillId = skills.getInt(y);
					if (skillId == 4) {
						maxbulletCount *= 3;
					} else if (skillId == 18) {
						maxbulletCount *= 2;
					}
				}
				// 大招
				if (isPlayerBigSkill) {
					if (player.getBigSkillType() == 1) {
						maxbulletCount = 1;
					} else {
						maxbulletCount = 7;
					}
				}
				int bulletCount = json.getInt("bulletCount");// 玩家发射的子弹数量
				logMsg += "炮弹数量" + bulletCount + "/" + maxbulletCount + "\n";
				if (bulletCount > maxbulletCount) {
					logMsg += "炮弹数量大于最大炮弹数量\n";
					log.info(logMsg);
					return "炮弹数量大于最大炮弹数量";
				}
				// 验证最大伤害
				int guaiHurtWithPlayer = 0, guaiHurtWithPet = 0, maxHurtWithPlayer = 0, maxHurtWithPet = 0;
				boolean isCritical = json.getInt("isCritical") == 1;// 是否暴击,
																	// 0不是, 1是
				int bulletHitCount = json.getInt("bulletHitCount");// 玩家的发炮打中了几炮
				for (int y = 0; y < skills.size(); y++) {
					int skillId = skills.getInt(y);
					Tools skill = toolsService.getToolById(skillId);
					if (skillId == 2 || skillId == 3 || skillId == 4 || skillId == 18) {
						attPercent *= skill.getParam2() / 1000d;
					} else if (skillId == 5 || skillId == 6 || skillId == 7 || skillId == 8 || skillId == 9) {
						attPercent *= (1 + skill.getParam1() / 1000d);
					}
				}
				JSONArray addHurts = (JSONArray) json.get("guaiSkillHurt");// 怪这回合受到的技能伤害
				JSONArray guaiHurtWithPets = (JSONArray) json.get("guaiHurtWithPet");// 宠物对怪做成的伤害
				JSONArray guaiHurtWithPlayers = (JSONArray) json.get("guaiHurtWithPlayer");// 玩家对怪做成的伤害
				JSONArray guaiHurtWithGuais = (JSONArray) json.get("guaiHurtWithGuai");// 怪对怪做成的伤害
				JSONArray guaiHps = (JSONArray) json.get("guaiHp");// 怪物剩下的血量
				JSONArray guaiIsHoles = (JSONArray) json.get("guaiIsHole");// 是否被坑杀
				// 大招
				if (isPlayerBigSkill) {
					if (player.getBigSkillType() == 1) {
						attPercent = 2.5;
					} else if (player.getBigSkillType() == 0) {
						attPercent = 0.3;
					} else if (player.getBigSkillType() == 2) {
						attPercent = 0.4;
					}
				}
				for (int y = 0; y < guaiHurtWithPlayers.size(); y++) {
					guaiHurtWithPlayer = guaiHurtWithPlayers.getInt(y);
					maxHurtWithPlayer = (int) (calculateHurt(guaiPlayers.get(y), isCritical, addHurts.getInt(y), attPercent, player) * bulletHitCount);
					logMsg += "玩家对怪物伤害" + guaiHurtWithPlayer + "/" + maxHurtWithPlayer + "\n";
					// System.out.println("guaiHurtWithPlayer:"+guaiHurtWithPlayer+" maxHurtWithPlayer:"+maxHurtWithPlayer);
					if (guaiHurtWithPlayer > maxHurtWithPlayer) {
						logMsg += "玩家对怪伤害超过玩家最大伤害\n";
						log.info(logMsg);
						return "玩家对怪伤害超过玩家最大伤害";
					}
					guaiMaxHps[y] -= guaiHurtWithPlayer;
				}
				if (guaiHurtWithPets != null) {
					for (int y = 0; y < guaiHurtWithPets.size(); y++) {
						guaiHurtWithPet = guaiHurtWithPets.getInt(y);
						if (guaiHurtWithPet < 1)
							continue;
						PlayerPet playerPet = player.getPlayerPet();
						maxHurtWithPet = (int) (player.getAttack() * playerPet.getSkill().getParam1() * 0.0001);
						logMsg += "宠物对怪物伤害" + guaiHurtWithPet + "/" + maxHurtWithPet + "\n";
						if (guaiHurtWithPet > maxHurtWithPet) {
							logMsg += "宠物对怪伤害超过宠物最大伤害\n";
							log.info(logMsg);
							return "宠物对怪伤害超过宠物最大伤害";
						}
						guaiMaxHps[y] -= guaiHurtWithPet;
					}
				}

				for (int y = 0; y < guaiHurtWithGuais.size(); y++) {
					guaiMaxHps[y] -= guaiHurtWithGuais.getInt(y);
					logMsg += "怪物对怪物造成伤害：" + guaiHurtWithGuais.getInt(y) + "\n";
				}
				if (addHurts != null) {
					for (int y = 0; y < guaiHurtWithGuais.size(); y++) {
						logMsg += "附加伤害：" + addHurts.getInt(y) + "\n";
						guaiMaxHps[y] -= addHurts.getInt(y);
					}
				}
				// 检验怪物血量
				for (int y = 0; y < guaiPlayers.size(); y++) {
					guaiIsDeads[y] = guaiIsDeads[y] ? true : guaiMaxHps[y] < 1;
					if (guaiIsHoles != null && guaiIsHoles.getBoolean(y)) {
						guaiIsDeads[y] = true;
					}
					if (!guaiIsDeads[y]) {
						logMsg += "怪剩下的血量：" + guaiMaxHps[y] + "/" + guaiHps.getInt(y) + "\n";
						if (guaiMaxHps[y] > guaiHps.getInt(y)) {
							logMsg += "怪物扣除血量异常\n";
							log.info(logMsg);
							return "怪物扣除血量异常";
						}
					}
				}

			}
			for (boolean isdead : guaiIsDeads) {
				if (!isdead) {
					logMsg += "怪物还没有死完！\n";
					log.info(logMsg);
					return "怪物还没有死完";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "未知异常";
		}
		return request;
	}

	private double calculateHurt(GuaiPlayer guaiPlayer, boolean isCritical, int addAttackValue, double attPercent, WorldPlayer player) {
		//
		double hurt = 0;
		// 攻击方攻击
		double attack0 = player.getAttack() * 1.0;
		// 攻击方力量
		double force0 = player.getForce() * 1.0;
		// 攻击方体质
		double physique0 = player.getPhysique() * 1.0;
		// 攻击方体敏捷
		double agility0 = player.getAgility() * 1.0;
		// 攻击方等级
		int level0 = player.getLevel();
		// 攻击方破防
		double wreckDefense0 = player.getWreckDefense() * 0.0001;
		// 被攻击方护甲
		double armor1 = guaiPlayer.getArmor() * 1.0;
		// 被攻击方体质
		double physique1 = guaiPlayer.getPhysique() * 1.0;
		// 被攻击方幸运
		double luck1 = guaiPlayer.getLuck() * 1.0;
		// 被攻击方防御
		double defend1 = guaiPlayer.getDefend() * 1.0;
		// 被攻击方等级
		double level1 = guaiPlayer.getGuai().getLevel() * 1.0;
		// 被攻击方免伤
		double injuryFree1 = guaiPlayer.getInjuryFree() * 0.0001;

		// 弹弹岛新伤害公式=攻击方攻击*（1.0+1.0*（攻击方力量+500）/(攻击方力量+被攻击方护甲+1000））*（0.5+1.0*（攻击方体质+500）/(攻击方体质+被攻击方体质+1000））*（0.75+
		// 0.5*（攻击方体敏捷+500）/(攻击方敏捷+被攻击方幸运+1000））*（1-（被攻击方防御*（1-攻击方破防/(攻击方破防+500)）/（2*被攻击方防御*（1-攻击方破防/(攻击方破防+500)）+600））*（1+（攻击方等级-被攻击方等级）/100）*(1-0.3*被攻击方免伤/(被攻击方免伤+1000））*(96%~104%)*（爆炸范围百分比伤害））
		hurt = attack0 * (1.0 + 1.0 * (force0 + 500) / (force0 + armor1 + 1000))
				* (0.5 + 1.0 * (physique0 + 500) / (physique0 + physique1 + 1000));
		double hurt1 = (0.75 + 0.5 * (agility0 + 500) / (agility0 + luck1 + 1000));
		double hurt2 = (1 - (defend1 * (1 - wreckDefense0 / (wreckDefense0 + 500)) / (2 * defend1
				* (1 - wreckDefense0 / (wreckDefense0 + 500)) + 600))
				* (1 + (level0 - level1) / 100) * (1 - 0.3 * injuryFree1 / (injuryFree1 + 1000)));
		hurt = hurt * hurt1 * hurt2;
		// 伤害值增104的浮动上限和最大武器技能105
		hurt = hurt * 1.04 * 1.05;
		if (hurt < attack0 * 0.1) {
			hurt = attack0 * 0.1;
		}

		// 技能损耗或加成
		hurt = hurt * attPercent;

		// 增加伤害被动(最大增加10点)
		if (addAttackValue < 11) {
			hurt = hurt + addAttackValue;//
		}
		return hurt;
	}

	@Override
	public Map getMaxPassMap(int playerId) {
		return dao.getMaxPassMap(playerId);
	}

}