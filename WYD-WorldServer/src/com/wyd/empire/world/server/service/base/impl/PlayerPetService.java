package com.wyd.empire.world.server.service.base.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.protocol.data.cache.UpdatePet;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.bean.PetCulture;
import com.wyd.empire.world.bean.PetItem;
import com.wyd.empire.world.bean.PetRecord;
import com.wyd.empire.world.bean.PetTrain;
import com.wyd.empire.world.bean.PlayerPet;
import com.wyd.empire.world.bean.PlayerPetBar;
import com.wyd.empire.world.dao.IPlayerPetDao;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerPetService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * The service class for the TabConsortiaright entity.
 */
public class PlayerPetService extends UniversalManagerImpl implements IPlayerPetService {
	Logger log = Logger.getLogger(PlayerPetService.class);
	private List<PetTrain> trainList = null;
	/**
	 * The dao instance injected by Spring.
	 */
	private IPlayerPetDao dao;

	public PlayerPetService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IPlayerPetService getInstance(ApplicationContext context) {
		return (IPlayerPetService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IPlayerPetDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IPlayerPetDao getDao() {
		return this.dao;
	}

	@Override
	public List<PlayerPet> getPetListByPlayer(Integer playerId) {
		return dao.getPetListByPlayer(playerId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public PetTrain getTrainByLevel(int level) {
		if (trainList == null) {
			trainList = dao.getAll(PetTrain.class);
		}
		for (PetTrain train : trainList) {
			if (train.getPetLevel().intValue() == level) {
				return train;
			}
		}
		return null;
	}

	/**
	 * 获得玩家正在使用的宠物
	 * 
	 * @param playerId
	 * @return
	 */
	public PlayerPet getInUsePet(int playerId) {
		return dao.getInUsePet(playerId);
	}

	@Override
	public boolean isInTrain(int playerId, int petId) {
		return dao.isInTrain(playerId, petId);
	}

	@Override
	public PlayerPet getByPlayerAndPet(Integer playerId, Integer petId) {
		return dao.getByPlayerAndPet(playerId, petId);
	}

	@Override
	public PetCulture getCultureByLevel(Integer level) {
		return dao.getCultureByLevel(level);
	}

	@Override
	public PlayerPet getInTrainPet(int playerId) {
		return dao.getInTrainPet(playerId);
	}

	/**
	 * 更新宠物经验 宠物级别不能大于玩家的级别
	 * 
	 * @param playerPet
	 * @param exp
	 * @return 升级次数
	 */
	@Override
	public int updateExp(WorldPlayer player, PlayerPet playerPet, int exp) {
		if (exp < 0)
			return 0; // 宠物只会增加经验不扣经验
		int addLevel = 0;
		int playerLevel = player.getLevel();
		if (playerPet == null || playerPet.getId() == null)
			return addLevel;
		if (playerPet.getLevel() >= Server.config.getMaxLevel(0))
			return 0;
		int quality = playerPet.getPet().getQuality();
		PetTrain train = getTrainByLevel(playerPet.getLevel());
		int needExp = train.getUpLevelExp(quality);
		playerPet = (PlayerPet) get(PlayerPet.class, playerPet.getId());
		exp = playerPet.getPetExp() + exp;
		Map<String, String> info = new HashMap<String, String>();
		// 循环升级，直至当前经验不足够再升级
		while (exp >= needExp) {
			// 宠物级别不能大于玩家级别
			if (playerPet.getLevel() >= playerLevel) {
				break;
			} else {
				addLevel++;
				// 升级
				upLevel(playerPet, info);
				exp -= needExp;
				train = getTrainByLevel(playerPet.getLevel());
				needExp = train.getUpLevelExp(quality);
			}
		}
		playerPet.setPetExp(exp);
		dao.update(playerPet);
		ServiceManager.getManager().getTitleService().upPetLevel(player, playerPet.getLevel());
		train = getTrainByLevel(playerPet.getLevel());
		int trainNeedTime = train.getTrainTime();
		int trainAddExp = train.getTrainExp();
		int trainPrice = train.getTrainPrice();
		int trainAddLevel = expToLevel(playerPet, trainAddExp, player.getLevel());
		info.put("trainAddLevel", trainAddLevel + "");
		info.put("trainNeedTime", trainNeedTime + "");
		info.put("trainAddExp", trainAddExp + "");
		info.put("trainPrice", trainPrice + "");
		if (addLevel > 0) {
			// 更新战力
			player.setPlayerPet(playerPet);
			info.put("startExp", "0");
			info.put("needExp", train.getUpLevelExp(playerPet.getPet().getQuality()) + "");
			info.put("level", playerPet.getLevel() + "");
			info.put("hasHp", playerPet.getHP() + "");
			info.put("hasAttack", playerPet.getAttack() + "");
			info.put("hasDefend", playerPet.getDefend() + "");
			PetCulture petCulture = getCultureByLevel(playerPet.getLevel());
			int hpmax = petCulture.getHpMax(playerPet.getPet().getQuality());
			int atkmax = petCulture.getAtkMax(playerPet.getPet().getQuality());
			int defmax = petCulture.getAtkMax(playerPet.getPet().getQuality());
			info.put("culMaxHp", hpmax + "");
			info.put("culMaxAttack", atkmax + "");
			info.put("culMaxDefend", defmax + "");
		}
		info.put("currExp", playerPet.getPetExp() + "");
		sendUpdatePet(player, playerPet.getPet().getId(), info);
		return addLevel;
	}

	/**
	 * 升级增加属性
	 * 
	 * @param playerPet
	 */
	private void upLevel(PlayerPet playerPet, Map<String, String> info) {

		PetItem pet = playerPet.getPet();
		int addAttack = pet.getAttackGrowth();
		int addHp = pet.getHpGrowth();
		int addDefend = pet.getDefendGrowth();

		int attack = playerPet.getPetAttack();
		int hp = playerPet.getPetHP();
		int defend = playerPet.getPetDefend();
		int level = playerPet.getLevel();

		playerPet.setPetAttack(attack + addAttack);
		playerPet.setPetHP(hp + addHp);
		playerPet.setPetDefend(defend + addDefend);
		playerPet.setLevel(level + 1);
		// 进化
		evolution(playerPet, info);
		// 记录日志
		PetRecord logBean = new PetRecord();
		logBean.setCreateTime(new Date());
		logBean.setLevel(playerPet.getLevel());
		logBean.setPetId(pet.getId());
		logBean.setPlayerId(playerPet.getPlayerId());
		logBean.setRemark("宠物由" + level + "升级到" + playerPet.getLevel());
		logBean.setType(PetRecord.UPLEVEL);
		save(logBean);
	}

	/**
	 * 进化 进化后，技能改变
	 * 
	 * @param playerPet
	 */
	private void evolution(PlayerPet playerPet, Map<String, String> info) {
		PetItem pet = playerPet.getPet();
		int evoLevel = pet.getEvoLevel();
		int petLevel = playerPet.getLevel();
		if (petLevel >= evoLevel) {
			playerPet.setSkill(pet.getEvoSkill());
		} else {
			playerPet.setSkill(pet.getSkill());
		}
		int skillId = playerPet.getSkill().getId();
		String skillIcon = playerPet.getSkill().getIcon();
		String skillDesc = playerPet.getSkill().getDesc();
		String skillName = playerPet.getSkill().getName();
		info.put("skillId", skillId + "");
		info.put("skillIcon", skillIcon);
		info.put("skillDesc", skillDesc);
		info.put("skillName", skillName);
		String icon = playerPet.getPetIcon();
		String picture = playerPet.getPetPicture();
		info.put("icon", icon);
		info.put("picture", picture);
	}

	public int expToLevel(PlayerPet playerPet, int addExp, int playerLevel) {
		int addLevel = 0;
		int exp = playerPet.getPetExp() + addExp;
		int quality = playerPet.getPet().getQuality();
		int petLevel = playerPet.getLevel();
		PetTrain train = getTrainByLevel(petLevel);
		int needExp = train.getUpLevelExp(quality);
		// 循环升级，直至当前经验不足够再升级
		while (exp >= needExp) {
			// 宠物级别不能大于玩家级别
			if (playerPet.getLevel() >= playerLevel) {
				break;
			} else {
				addLevel++;
				exp -= needExp;
				petLevel++;
				train = getTrainByLevel(petLevel);
				needExp = train.getUpLevelExp(quality);
			}
		}
		return addLevel;
	}

	public int openBarNum(int playerId) {
		PlayerPetBar playerPetBar = dao.getPlayerPetBar(playerId);
		// 如果空则设置默认值
		if (playerPetBar == null) {
			playerPetBar = new PlayerPetBar();
			playerPetBar.setPlayerId(playerId);
			playerPetBar.setNum(5);
			dao.save(playerPetBar);
		}
		return playerPetBar.getNum();
	}

	public PlayerPet playerGetPet(int playerId, int petId, boolean isUse) {
		PetItem pet = (PetItem) get(PetItem.class, petId);
		if (pet == null || pet.getId() == null)
			return null;
		PlayerPet playerPet = new PlayerPet();
		playerPet.setCreateTime(new Date());
		playerPet.setLevel(1);
		playerPet.setPet(pet);
		playerPet.setPetAttack(pet.getInitAttack());
		playerPet.setPetDefend(pet.getInitDefend());
		playerPet.setPetExp(0);
		playerPet.setPetHP(pet.getInitHp());
		playerPet.setPlayerId(playerId);
		playerPet.setTrainEndTime(null);
		playerPet.setCulAttack(0);
		playerPet.setCulDefend(0);
		playerPet.setCulHP(0);
		// 设置默认技能
		playerPet.setSkill(playerPet.getPet().getSkill());
		playerPet.setInUsed(isUse);
		// 记录日志
		PetRecord logBean = new PetRecord();
		logBean.setCreateTime(new Date());
		logBean.setLevel(playerPet.getLevel());
		logBean.setPetId(playerPet.getPet().getId());
		logBean.setPlayerId(playerPet.getPlayerId());
		logBean.setRemark("获得宠物：" + playerPet.getPet().getId());
		logBean.setType(PetRecord.GETPET);
		save(logBean);
		playerPet = (PlayerPet) save(playerPet);
		WorldPlayer player = ServiceManager.getManager().getPlayerService().getLoadPlayer(playerId);
		sendAddPet(player, playerPet);
		return playerPet;
	}

	@Override
	public void sendUpdatePet(WorldPlayer player, int petId, Map<String, String> info) {
		// 缓存没推送完不能推送更新协议
		if (player == null || !player.isCacheOk())
			return;
		int size = info.size();
		String[] key = new String[size];
		String[] value = new String[size];
		int i = 0;
		Set<String> keyset = info.keySet();
		for (String k : keyset) {
			key[i] = k;
			value[i] = info.get(k);
			i++;
		}
		UpdatePet updatePet = new UpdatePet();
		updatePet.setId(petId);
		updatePet.setKey(key);
		updatePet.setValue(value);
		player.sendData(updatePet);
	}

	public int getOpenBarDiamond(int num) {
		return dao.getPetBar(num).getDiamond();
	}

	@Override
	public PlayerPetBar getPlayerPetBar(int playerId) {
		return dao.getPlayerPetBar(playerId);
	}

	@Override
	public PlayerPet playerGetPet(int playerId, int petId) {
		return playerGetPet(playerId, petId, true);
	}

	@Override
	public void sendPlayerPet(WorldPlayer player) {
		if (player == null || !player.isOnline())
			return;

		int playerId = player.getId();
		List<PlayerPet> playerPets = getPetListByPlayer(playerId);
		// 检查宠物等级和经验。
		checkPetExpAndLevel(playerPets);
		int count = playerPets.size();
		if (count < 1)
			return;
		int[] petIds = new int[count];
		String[] names = new String[count];
		String[] icons = new String[count];
		String[] pictures = new String[count];
		String[] skillIcons = new String[count];
		int[] levels = new int[count];
		int[] qualitys = new int[count];
		int[] hps = new int[count];
		int[] attacks = new int[count];
		int[] defends = new int[count];
		int[] hasHps = new int[count];
		int[] hasAttacks = new int[count];
		int[] hasDefends = new int[count];
		int[] startExps = new int[count];
		int[] currExps = new int[count];
		int[] needExps = new int[count];
		int[] inTrains = new int[count];
		int[] trainNeedTimes = new int[count];
		int[] trainAddExps = new int[count];
		int[] trainPrices = new int[count];
		int[] cultureGold = new int[count];
		int[] cultureDiamond = new int[count];
		int[] culHps = new int[count];
		int[] culAttacks = new int[count];
		int[] culDefends = new int[count];
		int[] culMaxHps = new int[count];
		int[] culMaxAttacks = new int[count];
		int[] culMaxDefends = new int[count];
		int[] isPlayeds = new int[count];
		int[] trainAddLevels = new int[count];
		int[] skillId = new int[count];
		String[] skillDescs = new String[count];
		String[] skillNames = new String[count];

		for (int i = 0; i < playerPets.size(); i++) {
			PlayerPet playerPet = playerPets.get(i);
			int petLevel = playerPet.getLevel();
			PetCulture petCulture = getCultureByLevel(playerPet.getLevel());
			PetItem pet = playerPet.getPet();
			PetTrain train = getTrainByLevel(petLevel);
			petIds[i] = pet.getId();
			names[i] = playerPet.getPetName();
			icons[i] = playerPet.getPetIcon();
			pictures[i] = playerPet.getPetPicture();
			qualitys[i] = pet.getQuality();
			// 高级宠物和普通宠物上限不一样
			int hpmax = petCulture.getHpMax(playerPet.getPet().getQuality());
			int atkmax = petCulture.getAtkMax(playerPet.getPet().getQuality());
			int defmax = petCulture.getAtkMax(playerPet.getPet().getQuality());
			// 生命上限=初始生命+生命成长点*当前级数+最大培养点*培养系数(petLevel-1原因是宠物2级时候才开始加成长)
			int growthLevel = petLevel - 1;
			hps[i] = (int) (pet.getInitHp() + pet.getHpGrowth() * growthLevel + petCulture.getHpCoef() * hpmax);
			attacks[i] = (int) (pet.getInitAttack() + pet.getAttackGrowth() * growthLevel + petCulture.getAttackCoef() * atkmax);
			defends[i] = (int) (pet.getInitDefend() + pet.getDefendGrowth() * growthLevel + petCulture.getDefendCoef() * defmax);

			levels[i] = petLevel;
			hasHps[i] = playerPet.getHP();
			hasAttacks[i] = playerPet.getAttack();
			hasDefends[i] = playerPet.getDefend();

			skillId[i] = playerPet.getSkill().getId();
			skillIcons[i] = playerPet.getSkill().getIcon();
			skillDescs[i] = playerPet.getSkill().getDesc();
			skillNames[i] = playerPet.getSkill().getName();

			startExps[i] = 0;
			currExps[i] = playerPet.getPetExp();
			needExps[i] = train.getUpLevelExp(pet.getQuality());
			int maxLevel = Server.config.getMaxLevel(0);
			// 达到最大级时候，经验不能超
			if (maxLevel == petLevel) {
				if (currExps[i] > needExps[i]) {
					currExps[i] = needExps[i];
				}
			}
			inTrains[i] = isInTrain(playerId, petIds[i]) ? 1 : 0;
			trainNeedTimes[i] = train.getTrainTime();
			trainAddExps[i] = train.getTrainExp();
			trainPrices[i] = train.getTrainPrice();
			PetCulture culture = getCultureByLevel(petLevel);
			cultureGold[i] = culture.getCultureGold();
			cultureDiamond[i] = culture.getCultureDiamond();

			culHps[i] = playerPet.getCulHPNum();
			culAttacks[i] = playerPet.getCulAttackNum();
			culDefends[i] = playerPet.getCulDefendNum();

			culMaxHps[i] = hpmax;
			culMaxAttacks[i] = atkmax;
			culMaxDefends[i] = defmax;
			isPlayeds[i] = playerPet.isInUsed() ? 1 : 0;

			// ===计算练后能增加的级别===
			int trainAddLevel = expToLevel(playerPet, trainAddExps[i], player.getLevel());
			trainAddLevels[i] = trainAddLevel < 0 ? 0 : trainAddLevel;
			// ================

		}
		com.wyd.empire.protocol.data.cache.PlayerPet playerPet = new com.wyd.empire.protocol.data.cache.PlayerPet();
		playerPet.setPetId(petIds);
		playerPet.setName(names);
		playerPet.setAttack(attacks);
		playerPet.setCurrExp(currExps);
		playerPet.setDefend(defends);
		playerPet.setHasAttack(hasAttacks);
		playerPet.setHasDefend(hasDefends);
		playerPet.setHasHp(hasHps);
		playerPet.setHp(hps);
		playerPet.setIcon(icons);
		playerPet.setInTrain(inTrains);
		playerPet.setLevel(levels);
		playerPet.setNeedExp(needExps);
		playerPet.setPicture(pictures);
		playerPet.setQuality(qualitys);
		playerPet.setTrainAddExp(trainAddExps);
		playerPet.setTrainPrice(trainPrices);
		playerPet.setSkillIcon(skillIcons);
		playerPet.setTrainNeedTime(trainNeedTimes);
		playerPet.setStartExp(startExps);
		playerPet.setCultureGold(cultureGold);
		playerPet.setCultureDiamond(cultureDiamond);
		playerPet.setCulHp(culHps);
		playerPet.setCulAttack(culAttacks);
		playerPet.setCulDefend(culDefends);
		playerPet.setCulMaxHp(culMaxHps);
		playerPet.setCulMaxAttack(culMaxAttacks);
		playerPet.setCulMaxDefend(culMaxDefends);
		playerPet.setIsPlayed(isPlayeds);
		playerPet.setSkillDesc(skillDescs);
		playerPet.setSkillName(skillNames);
		playerPet.setTrainAddLevel(trainAddLevels);
		playerPet.setSkillId(skillId);
		player.sendData(playerPet);
	}

	public void sendAddPet(WorldPlayer player, PlayerPet playerPet) {
		if (player == null || !player.isOnline() || !player.isCacheOk())
			return;
		int playerId = player.getId();
		int count = 1;
		int[] petIds = new int[count];
		String[] names = new String[count];
		String[] icons = new String[count];
		String[] pictures = new String[count];
		String[] skillIcons = new String[count];
		int[] levels = new int[count];
		int[] qualitys = new int[count];
		int[] hps = new int[count];
		int[] attacks = new int[count];
		int[] defends = new int[count];
		int[] hasHps = new int[count];
		int[] hasAttacks = new int[count];
		int[] hasDefends = new int[count];
		int[] startExps = new int[count];
		int[] currExps = new int[count];
		int[] needExps = new int[count];
		int[] inTrains = new int[count];
		int[] trainNeedTimes = new int[count];
		int[] trainAddExps = new int[count];
		int[] trainPrices = new int[count];
		int[] cultureGold = new int[count];
		int[] cultureDiamond = new int[count];
		int[] culHps = new int[count];
		int[] culAttacks = new int[count];
		int[] culDefends = new int[count];
		int[] culMaxHps = new int[count];
		int[] culMaxAttacks = new int[count];
		int[] culMaxDefends = new int[count];
		int[] isPlayeds = new int[count];
		int[] trainAddLevels = new int[count];
		int[] skillId = new int[count];
		String[] skillDescs = new String[count];
		String[] skillNames = new String[count];

		int i = 0;
		int petLevel = playerPet.getLevel();
		PetCulture petCulture = getCultureByLevel(playerPet.getLevel());
		PetItem pet = playerPet.getPet();
		PetTrain train = getTrainByLevel(petLevel);
		petIds[i] = pet.getId();
		names[i] = playerPet.getPetName();
		icons[i] = playerPet.getPetIcon();
		pictures[i] = playerPet.getPetPicture();
		qualitys[i] = pet.getQuality();
		// 高级宠物和普通宠物上限不一样
		int hpmax = petCulture.getHpMax(playerPet.getPet().getQuality());
		int atkmax = petCulture.getAtkMax(playerPet.getPet().getQuality());
		int defmax = petCulture.getAtkMax(playerPet.getPet().getQuality());
		// 生命上限=初始生命+生命成长点*当前级数+最大培养点*培养系数(petLevel-1原因是宠物2级时候才开始加成长)
		int growthLevel = petLevel - 1;
		hps[i] = (int) (pet.getInitHp() + pet.getHpGrowth() * growthLevel + petCulture.getHpCoef() * hpmax);
		attacks[i] = (int) (pet.getInitAttack() + pet.getAttackGrowth() * growthLevel + petCulture.getAttackCoef() * atkmax);
		defends[i] = (int) (pet.getInitDefend() + pet.getDefendGrowth() * growthLevel + petCulture.getDefendCoef() * defmax);

		levels[i] = petLevel;
		hasHps[i] = playerPet.getHP();
		hasAttacks[i] = playerPet.getAttack();
		hasDefends[i] = playerPet.getDefend();

		skillId[i] = playerPet.getSkill().getId();
		skillIcons[i] = playerPet.getSkill().getIcon();
		skillDescs[i] = playerPet.getSkill().getDesc();
		skillNames[i] = playerPet.getSkill().getName();

		startExps[i] = 0;
		currExps[i] = playerPet.getPetExp();
		needExps[i] = train.getUpLevelExp(pet.getQuality());
		int maxLevel = Server.config.getMaxLevel(0);
		// 达到最大级时候，经验不能超
		if (maxLevel == petLevel) {
			if (currExps[i] > needExps[i]) {
				currExps[i] = needExps[i];
			}
		}
		inTrains[i] = isInTrain(playerId, petIds[i]) ? 1 : 0;
		trainNeedTimes[i] = train.getTrainTime();
		trainAddExps[i] = train.getTrainExp();
		trainPrices[i] = train.getTrainPrice();
		PetCulture culture = getCultureByLevel(petLevel);

		cultureGold[i] = culture.getCultureGold();
		cultureDiamond[i] = culture.getCultureDiamond();

		culHps[i] = playerPet.getCulHPNum();
		culAttacks[i] = playerPet.getCulAttackNum();
		culDefends[i] = playerPet.getCulDefendNum();

		culMaxHps[i] = hpmax;
		culMaxAttacks[i] = atkmax;
		culMaxDefends[i] = defmax;
		isPlayeds[i] = playerPet.isInUsed() ? 1 : 0;

		// ===计算练后能增加的级别===
		int trainAddLevel = expToLevel(playerPet, trainAddExps[i], player.getLevel());
		trainAddLevels[i] = trainAddLevel < 0 ? 0 : trainAddLevel;
		// ================

		com.wyd.empire.protocol.data.cache.AddPet addPet = new com.wyd.empire.protocol.data.cache.AddPet();
		addPet.setPetId(petIds);
		addPet.setName(names);
		addPet.setAttack(attacks);
		addPet.setCurrExp(currExps);
		addPet.setDefend(defends);
		addPet.setHasAttack(hasAttacks);
		addPet.setHasDefend(hasDefends);
		addPet.setHasHp(hasHps);
		addPet.setHp(hps);
		addPet.setIcon(icons);
		addPet.setInTrain(inTrains);
		addPet.setLevel(levels);
		addPet.setNeedExp(needExps);
		addPet.setPicture(pictures);
		addPet.setQuality(qualitys);
		addPet.setTrainAddExp(trainAddExps);
		addPet.setTrainPrice(trainPrices);
		addPet.setSkillIcon(skillIcons);
		addPet.setTrainNeedTime(trainNeedTimes);
		addPet.setStartExp(startExps);
		addPet.setCultureGold(cultureGold);
		addPet.setCultureDiamond(cultureDiamond);
		addPet.setCulHp(culHps);
		addPet.setCulAttack(culAttacks);
		addPet.setCulDefend(culDefends);
		addPet.setCulMaxHp(culMaxHps);
		addPet.setCulMaxAttack(culMaxAttacks);
		addPet.setCulMaxDefend(culMaxDefends);
		addPet.setIsPlayed(isPlayeds);
		addPet.setSkillDesc(skillDescs);
		addPet.setSkillName(skillNames);
		addPet.setTrainAddLevel(trainAddLevels);
		addPet.setSkillId(skillId);
		player.sendData(addPet);
	}

	/**
	 * 检查宠物等级和经验。以等级为主如果经验大于当前级别则设置为当前级别的经验
	 * 
	 * @param playerId
	 */
	private void checkPetExpAndLevel(List<PlayerPet> playerPets) {
		for (PlayerPet playerPet : playerPets) {
			int level = playerPet.getLevel();
			int exp = playerPet.getPetExp();
			PetTrain train = getTrainByLevel(level);
			int maxExp = train.getUpLevelExp(playerPet.getPet().getQuality());
			if (exp > maxExp) {
				playerPet.setPetExp(maxExp);
				update(playerPet);
			}
		}
	}
}