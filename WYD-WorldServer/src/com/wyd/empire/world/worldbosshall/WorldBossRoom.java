package com.wyd.empire.world.worldbosshall;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.bean.Guai;
import com.wyd.empire.world.bossmaproom.GuaiPlayer;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.player.Record;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IGuaiService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.BossBattleTeamService;

/**
 * 世界BOSS
 * 
 * @author zengxc
 */
public class WorldBossRoom {
	private final int INITSIZE = 100; // 房间默认创建位置个数
	private int id; // 房间id
	private int mapId; // 房间地图id
	private int level; // 进入房间需求等级
	private Vector<Combat> combatList; // 成员
	private boolean open; // false为关闭， true为开放
	private Combat boss; // boss
	private int playerSize; // 参与玩家个数

	public int getPlayerSize() {
		return playerSize;
	}

	public void setPlayerSize(int playerSize) {
		this.playerSize = playerSize;
	}

	private int lastNumber; // 上次参加的人数
	private WorldPlayer killer; // 杀死BOSS的玩家
	private ConcurrentHashMap<Integer, Date> playerCDTimes; // KEY:玩家ID，VAL：CDTIME
	private ConcurrentHashMap<String, Integer> playerHurtNotice; // KEY:玩家Name，VAL：百分比

	private int buffNum; // 派出buff的数量
	private int xinghun; // 派出星魂的数量

	/**
	 * 开启房间 同时创建BOSS
	 */
	public void open() {
		Combat boss = getBoss();
		if (boss == null || boss.isDead()) {
			createBoss();
		}
		open = true;
	}

	/**
	 * 关闭房间 ;记录本次开启时参与的人数
	 */
	public void close() {
		open = false;
		lastNumber = countJoinPlayer();
	}

	private void init() {
		playerSize = 0;
		playerCDTimes = new ConcurrentHashMap<Integer, Date>();
		playerHurtNotice = new ConcurrentHashMap<String, Integer>();
		combatList = new Vector<Combat>();
		killer = null;
		// 初始化成员
		for (int i = 0; i < INITSIZE; i++) {
			combatList.add(new Combat());
		}
		buffNum = 0;
		xinghun = 0;
	}

	public WorldBossRoom() {
		id = this.hashCode();
		while (id < 1000) {
			id = (int) (9999 * Math.random());
		}
		init();
	}

	public int getMapId() {
		return mapId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<Combat> getCombatList() {
		return combatList;
	}

	/**
	 * 返回 1 成功 0 战斗房间未开启 －1 冷却中
	 * 
	 * @param player
	 * @return
	 */
	public int addPlayer(WorldPlayer player) {
		long cdTime = getPlayerCDTime(player.getId());
		if (cdTime > 0) {
			return -1;
		}
		if (open) {
			Combat combat = findPlayer(player.getId());
			// 如果玩家原来就没有才创建
			if (combat == null) {
				combat = getFreeCombat();
				playerSize++;
			}
			combat.setPlayer(player);
			combat.setPlayer(player);
			combat.setCamp(0);
			combat.setRobot(false);
			player.setActionTime((new Date()).getTime());
			combat.setAiCtrlId(ServiceUtils.getRandomNum(0, 3));
			// System.out.println("玩家：" + player.getId() + "进入世界BOSS房间,");
			combat.setHp(player.getMaxHP());
			combat.setPf(player.getMaxPF(), getMapId(), 6, player);
			combat.setLost(false);
			combat.setDead(false);
			combat.setAngryValue(0);
			// System.out.println("玩家(hp:" + combat.getHp() + ",pf:" +
			// combat.getPf() + ")：" + player.getId() + " 上次的伤害输出是：" +
			// combat.getTotalHurt());
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 获取空闲成员对象，如果没有则创建一个新的
	 * 
	 * @return
	 */
	private Combat getFreeCombat() {
		for (Combat combat : combatList) {
			if (combat.getPlayer() == null) {
				combat.setTotalHurt(0);
				return combat;
			}
		}
		Combat combat = new Combat();
		combatList.add(combat);
		return combat;
	}

	/**
	 * 按伤害取前N个 max为-1是为不限制个数
	 */
	public List<Combat> hurtTop(int max) {
		if (combatList == null)
			return new ArrayList<Combat>();
		ComparatorPlayerHurt comparator = new ComparatorPlayerHurt();
		Collections.sort(combatList, comparator);
		if (max != -1) {
			return notNullList(combatList.subList(0, max));
		} else {
			return notNullList(combatList);
		}
	}

	private List<Combat> notNullList(List<Combat> combats) {
		List<Combat> results = new ArrayList<Combat>();
		for (Combat c : combats) {
			if (c.getPlayer() != null) {
				results.add(c);
			}
		}
		return results;
	}

	/**
	 * 返回参与的玩家个数
	 * 
	 * @return
	 */
	private int countJoinPlayer() {
		int i = 0;
		for (Combat combat : combatList) {
			if (combat.getPlayer() != null) {
				i++;
			}
		}
		return i;
	}

	/**
	 * 创建BOSS
	 */
	public void createBoss() {
		init();
		IGuaiService guaiService = ServiceManager.getManager().getGuaiService();
		BossBattleTeamService bossBattleTeamService = ServiceManager.getManager().getBossBattleTeamService();
		com.wyd.empire.world.bean.Map map = ServiceManager.getManager().getMapsService().getWorldBossMapById(mapId);
		boss = new Combat();
		killer = null;
		boss.setDead(false);
		// 攻击力排行
		List<Record> recordList = ServiceManager.getManager().getLogSerivce().getNowAttack();
		int attackNo1 = 1000, attackNo10 = 1000;
		if (recordList != null && recordList.size() > 0) {
			attackNo1 = recordList.get(0).getData();// 第一名攻击力
			if (recordList.size() > 9) {
				attackNo10 = recordList.get(9).getData();// 第十名攻击力
			} else {
				attackNo10 = attackNo1;// 如果玩家少于十名，则使用 第1名攻击力
			}
		}
		// 可按配置表去取参数，默认是55
		Map<String, Integer> mark = ServiceManager.getManager().getVersionService().getSpecialMark();
		Integer param = mark.get("worldBossParam1");
		param = param == null ? 40 : param.intValue();
		// lastNumber=0表示第一次开放世界BOSS(第一次计算与非第一次计算不一样)
		List<Map<String, Integer>> bossPosList = bossBattleTeamService.getplayerPos(map.getGuaiList(), false);
		Map<String, Integer> bossPos = bossPosList.get(0);
		GuaiPlayer guai = guaiService.getGuaiById(1, bossPos.get("id").intValue());
		boss.setBattleId(-21);// 一定要是负数的说:bossBattleTeam.getGuaiIdDistribution()
		boss.setGuai(guai);
		boss.setGuaiPlayer(true);
		boss.setCamp(bossPos.get("camp"));
		boss.setRobot(true);
		boss.setX(bossPos.get("posX"));
		boss.setY(bossPos.get("posY"));
		boss.setAiCtrlId(ServiceUtils.getRandomNum(0, 3));
		Guai guaiTable = boss.getGuai().getGuai();
		// 血量排行
		recordList = ServiceManager.getManager().getLogSerivce().getNowHP();
		int hpNo1 = 1000;// 默认值
		if (recordList != null && recordList.size() > 0) {
			hpNo1 = recordList.get(0).getData();// 第一名血量
		}
		// 技能1,2伤害随着第一名血量而变(注意是1L不是11)
		long skill1 = hpNo1 * 1l * guaiTable.getSkill_1() / 10000;
		long skill2 = hpNo1 * 1l * guaiTable.getSkill_2() / 10000;
		int attack = (int) (0.1111 * hpNo1);
		guaiTable.setSkill_1((int) skill1);
		guaiTable.setSkill_2((int) skill2);
		guaiTable.setAttack(attack);// 第一名的血量的0.1111
		guai.setAttack(attack);
		guai.setDefend(0);
		// 设置总血量
		guai.setMaxHP((int) (((attackNo1 + attackNo10) / 2f) * 1000 * param));
		buffNum = 300;
		xinghun = 30;

		// 玩家攻击增加3000，同时boss血量增加2倍
		guai.setMaxHP(guai.getMaxHP());
		boss.setHp(boss.getMaxHP());

	}

	/**
	 * 按玩家ID返回玩家信息
	 * 
	 * @param playerId
	 * @return
	 */
	public Combat findPlayer(int playerId) {
		if (combatList == null)
			return null;
		for (Combat combat : combatList) {
			if (combat.getPlayer() == null)
				continue;
			if (combat.getId() == playerId) {
				return combat;
			}
		}
		return null;
	}

	/**
	 * 取玩家的CDTime
	 * 
	 * @author zengxc
	 */
	public long getPlayerCDTime(int playerId) {
		Date cdTime = playerCDTimes.get(playerId);
		Date now_ = new Date();
		if (cdTime == null) {
			playerCDTimes.put(playerId, now_);
			return 0;
		}
		long t = cdTime.getTime() - now_.getTime();
		return t < 0 ? 0 : t;
	}

	public void setPlayerCDTime(int playerId) {
		setPlayerCDTime(playerId, Common.WORLDBOSS_CDTIME);
	}

	public void setPlayerCDTime(int playerId, int cdTime) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, cdTime);
		playerCDTimes.put(playerId, cal.getTime());
	}

	class ComparatorPlayerHurt implements Comparator<Combat> {
		@Override
		public int compare(Combat p1, Combat p2) {
			if (p1 == null)
				return 0;
			return p2.getTotalHurt() - p1.getTotalHurt();
		}
	}

	public static void main(String[] args) {
		// no1:14630,no10:1240
		int a = (int) (((100421 + 402) / 2f) * 3 * 55);
		System.out.println(a);
	}

	public ConcurrentHashMap<String, Integer> getPlayerHurtNotice() {
		return playerHurtNotice;
	}

	public WorldPlayer getKiller() {
		return killer;
	}

	public void setKiller(WorldPlayer killer) {
		this.killer = killer;
	}

	public int getLastNumber() {
		return lastNumber;
	}

	public Combat getBoss() {
		return boss;
	}

	public boolean isOpen() {
		return open;
	}

	public int getBuffNum() {
		return buffNum;
	}

	/**
	 * 扣除buff剩余数量
	 * 
	 * @param num
	 */
	public void useBuffNum(int num) {
		buffNum -= num;
	}

	public int getXinghun() {
		return xinghun;
	}

	/**
	 * 扣除星魂剩余数量
	 * 
	 * @param num
	 */
	public void useXinghun(int num) {
		xinghun -= num;
	}

}
