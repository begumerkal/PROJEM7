package com.wyd.empire.battle.service.impl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.bean.CombatComparator;
import com.wyd.empire.battle.bean.WeapSkill;
import com.wyd.empire.battle.bean.WorldPlayer;
import com.wyd.empire.battle.handler.cross.CrossEndCurRoundHandler;
import com.wyd.empire.battle.handler.cross.CrossFinishLoadingHandler;
import com.wyd.empire.battle.handler.cross.CrossPassHandler;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.battle.util.ServiceUtils;
import com.wyd.empire.protocol.data.cross.CrossAIControlCommon;
import com.wyd.empire.protocol.data.cross.CrossChangeAngryValue;
import com.wyd.empire.protocol.data.cross.CrossEndCurRound;
import com.wyd.empire.protocol.data.cross.CrossFinishLoading;
import com.wyd.empire.protocol.data.cross.CrossFrozenOver;
import com.wyd.empire.protocol.data.cross.CrossGameOver;
import com.wyd.empire.protocol.data.cross.CrossPass;
import com.wyd.empire.protocol.data.cross.CrossPlayerLose;
import com.wyd.empire.protocol.data.cross.CrossSynPlayerInfo;
/**
 * 类<code>BattleTeamService</code> 战斗组管理服务
 * 
 * @author Administrator
 */
public class BattleTeamService implements Runnable {
    private Logger                   log        = Logger.getLogger(BattleTeamService.class);
    /** 自动断线时间 */
    public static final int          OUT_TIME   = 60000;
    /** 休眠时间 */
    private static final int         SLEEP_TIME = 10000;
    /** 战斗组ID号与战斗级对象对应哈希表 */
    private Map<Integer, BattleTeam> battleTeamMap;
    private List<BattleTeam>         battleTeamList;

    /**
     * 无参构造函数，启动服务
     */
    public BattleTeamService() {
        battleTeamMap = new ConcurrentHashMap<Integer, BattleTeam>();
        battleTeamList = new Vector<BattleTeam>();
    }

    /**
     * 启动服务
     */
    public void start() {
        Thread t = new Thread(this);
        t.setName("BattleTeamService-Thread");
        t.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(SLEEP_TIME);
                long nowTime = System.currentTimeMillis();
                BattleTeam battleTeam;
                for (int i = battleTeamList.size() - 1; i >= 0; i--) {
                    battleTeam = battleTeamList.get(i);
                    ServiceManager.getManager().getSimpleThreadPool().execute(createTask(nowTime, battleTeam));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Runnable createTask(long nowTime, BattleTeam battleTeam) {
        return new BattleThread(nowTime, battleTeam);
    }
    public class BattleThread implements Runnable {
        private long       nowTime;
        private BattleTeam battleTeam;

        public BattleThread(long nowTime, BattleTeam battleTeam) {
            this.nowTime = nowTime;
            this.battleTeam = battleTeam;
        }

        @Override
        public void run() {
            try {
                for (Combat combat : battleTeam.getCombatList()) {
                    WorldPlayer player = combat.getPlayer();
                    if (null != player && !combat.isRobot() && !combat.isLost()) {
                        if ((nowTime - player.getActionTime()) > OUT_TIME) {
                            playerLose(battleTeam.getBattleId(), player.getId());
                            CrossPlayerLose playerLose = new CrossPlayerLose();
                            playerLose.setBattleId(battleTeam.getBattleId());
                            playerLose.setCurrentPlayerId(player.getId());
                            player.sendData(playerLose);
                            StringBuffer buf = new StringBuffer();
                            buf.append("玩家超过");
                            buf.append(OUT_TIME);
                            buf.append("秒没有响应强制退出对战。");
                            buf.append("---战斗组:");
                            buf.append(battleTeam.getBattleId());
                            log.info(buf);
                        }
                    }
                }
                if (null != battleTeam) {
                    gameOver(battleTeam, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建战斗组
     * 
     * @param battleMode
     * @param playerNum
     * @return 返回战斗组的id
     */
    public int createBattleTeam(int battleMode, int playerNum) {
        BattleTeam battleTeam = new BattleTeam();
        battleTeam.setBattleMode(battleMode);
        battleTeam.setPlayerNumMode(playerNum);
        // System.out.println("CreateBattleTeam------------" + battleTeam.getBattleId());
        battleTeam.setStartTime(System.currentTimeMillis());
        battleTeamMap.put(battleTeam.getBattleId(), battleTeam);
        battleTeamList.add(battleTeam);
        return battleTeam.getBattleId();
    }

    /**
     * 加入战斗组
     * 
     * @param battleId
     * @param player
     * @param camp
     *            玩家阵营
     * @param isRobot
     *            是否机器人
     */
    public void enBattleTeam(int battleId, WorldPlayer player, int camp, boolean isRobot) {
        BattleTeam battleTeam = battleTeamMap.get(battleId);
        Combat combat = new Combat();
        combat.setPlayer(player);
        combat.setCamp(camp);
        combat.setRobot(isRobot);
        combat.setHp(player.getMaxHP());
        combat.setPf(player.getMaxPF());
        combat.setAiCtrlId(ServiceUtils.getRandomNum(0, 3));
        battleTeam.enter(combat);
    }

    /**
     * 通知战斗组成员开始战斗
     * 
     * @param battleId
     * @param battleMap
     *            地图字串
     */
    public void startBattle(int battleId, String battleMap) {
        BattleTeam battleTeam = battleTeamMap.get(battleId);
        if (null != battleTeam) {
            battleTeam.setTogetherType(1);
            int playerCount = battleTeam.getCombatList().size();
            int[] playerId = new int[playerCount];
            int[] roomId = new int[playerCount];
            String[] playerName = new String[playerCount];
            int[] playerLevel = new int[playerCount];
            int[] boyOrGirl = new int[playerCount];
            String[] suit_head = new String[playerCount];
            String[] suit_face = new String[playerCount];
            String[] suit_body = new String[playerCount];
            String[] suit_weapon = new String[playerCount];
            int[] weapon_type = new int[playerCount];
            int[] camp = new int[playerCount];
            int[] maxHP = new int[playerCount];
            int[] maxPF = new int[playerCount];
            int[] maxSP = new int[playerCount];
            int[] attack = new int[playerCount];
            int[] bigSkillAttack = new int[playerCount];
            int[] critRate = new int[playerCount];
            int[] defence = new int[playerCount];
            int[] bigSkillType = new int[playerCount];
            int[] explodeRadius = new int[playerCount];
            int[] injuryFree = new int[playerCount];
            int[] wreckDefense = new int[playerCount];
            int[] reduceCrit = new int[playerCount];
            int[] reduceBury = new int[playerCount];
            int[] item_id = new int[playerCount * 8];
            int[] item_used = new int[playerCount * 8];
            String[] item_img = new String[playerCount * 8];
            String[] item_name = new String[playerCount * 8];
            String[] item_desc = new String[playerCount * 8];
            int[] item_type = new int[playerCount * 8];
            int[] item_subType = new int[playerCount * 8];
            int[] item_param1 = new int[playerCount * 8];
            int[] item_param2 = new int[playerCount * 8];
            int[] item_ConsumePower = new int[playerCount * 8];
            int[] specialAttackType = new int[playerCount * 8];
            int[] specialAttackParam = new int[playerCount * 8];
            String[] suit_wing = new String[playerCount];
            String[] player_title = new String[playerCount];
            String[] player_community = new String[playerCount];
            int[] zsleve = new int[playerCount];
            int[] skillful = new int[playerCount];
            int[] petId = new int[playerCount];
            String[] petIcon = new String[playerCount];
            int[] petType = new int[playerCount];
            int[] petSkillId = new int[playerCount];
            int[] petProbability = new int[playerCount];
            int[] petParam1 = new int[playerCount];
            int[] petParam2 = new int[playerCount];
            String[] petEffect = new String[playerCount];
            int[] petVersion = new int[playerCount];
            int[] force = new int[playerCount];
            int[] armor = new int[playerCount];
            int[] fighting = new int[playerCount];
            int[] winRate = new int[playerCount];
            int[] constitution = new int[playerCount];
            int[] agility = new int[playerCount];
            int[] lucky = new int[playerCount];
            int[] sourcePlayerId = new int[playerCount];
            String[] serverId = new String[playerCount];
            String[] serverName = new String[playerCount];
            int[] playerBuffCount = new int[playerCount];
            List<Integer> buffType = new ArrayList<Integer>();
            List<Integer> buffParam1 = new ArrayList<Integer>();
            List<Integer> buffParam2 = new ArrayList<Integer>();
            List<Integer> buffParam3 = new ArrayList<Integer>();
            List<Integer> weaponSkillPlayerId = new ArrayList<Integer>();
            List<String> weaponSkillName = new ArrayList<String>();
            List<Integer> weaponSkillType = new ArrayList<Integer>();
            List<Integer> weaponSkillChance = new ArrayList<Integer>();
            List<Integer> weaponSkillParam1 = new ArrayList<Integer>();
            List<Integer> weaponSkillParam2 = new ArrayList<Integer>();
            Combat combat;
            int z = 0;
            for (int i = 0; i < battleTeam.getCombatList().size(); i++) {
                combat = battleTeam.getCombatList().get(i);
                combat.setTiredValue(combat.getPlayer().getLevel());
                writeLog("battleId:" + battleId + "---player:" + combat.getPlayer().getId() + "---Camp:" + combat.getCamp());
                petId[i] = combat.getPlayer().getPetId();
                petIcon[i] = combat.getPlayer().getPetIcon();
                petType[i] = combat.getPlayer().getPetType();
                petSkillId[i] = combat.getPlayer().getPetSkillId();
                petProbability[i] = combat.getPlayer().getPetProbability();
                petParam1[i] = combat.getPlayer().getPetParam1();
                petParam2[i] = combat.getPlayer().getPetParam2();
                petEffect[i] = combat.getPlayer().getPetEffect();
                petVersion[i] = combat.getPlayer().getPetVersion();
                playerId[i] = combat.getPlayer().getId();
                sourcePlayerId[i] = combat.getPlayer().getPlayerId();
                serverId[i] = combat.getPlayer().getServerId();
                serverName[i] = combat.getPlayer().getServerName();
                zsleve[i] = combat.getPlayer().getZsleve();
                skillful[i] = combat.getPlayer().getSkillful();
                roomId[i] = combat.getPlayer().getRoomId();
                playerName[i] = combat.getPlayer().getName();
                playerLevel[i] = combat.getPlayer().getLevel();
                boyOrGirl[i] = combat.getPlayer().getSex();
                suit_head[i] = combat.getPlayer().getSuit_head();
                suit_face[i] = combat.getPlayer().getSuit_face();
                suit_body[i] = combat.getPlayer().getSuit_body();
                suit_weapon[i] = combat.getPlayer().getSuit_weapon();
                weapon_type[i] = combat.getPlayer().getWeapon_type();
                suit_wing[i] = combat.getPlayer().getSuit_wing();
                player_title[i] = combat.getPlayer().getTitle();
                player_community[i] = combat.getPlayer().getGuildName();
                camp[i] = combat.getCamp();
                maxHP[i] = combat.getPlayer().getMaxHP();
                maxPF[i] = combat.getPlayer().getMaxPF();
                maxSP[i] = combat.getPlayer().getMaxSP();
                attack[i] = combat.getPlayer().getAttack();
                injuryFree[i] = combat.getPlayer().getInjuryFree();
                wreckDefense[i] = combat.getPlayer().getWreckDefense();
                reduceCrit[i] = combat.getPlayer().getReduceCrit();
                reduceBury[i] = combat.getPlayer().getReduceBury();
                bigSkillAttack[i] = combat.getPlayer().getBigSkillAttack();
                critRate[i] = combat.getPlayer().getCritAttackRate();
                defence[i] = combat.getPlayer().getDefend();
                bigSkillType[i] = combat.getPlayer().getBigSkillType();
                explodeRadius[i] = combat.getPlayer().getExplodeRadius();
                force[i] = combat.getPlayer().getForce();
                armor[i] = combat.getPlayer().getArmor();
                fighting[i] = combat.getPlayer().getFighting();
                winRate[i] = combat.getPlayer().getWinRate();
                constitution[i] = combat.getPlayer().getConstitution();
                agility[i] = combat.getPlayer().getAgility();
                lucky[i] = combat.getPlayer().getLucky();
                for (int y = 0; y < 8; y++) {
                    item_used[z] = combat.getPlayer().getItem_used()[y];
                    item_id[z] = combat.getPlayer().getItem_id()[y];
                    item_img[z] = combat.getPlayer().getItem_img()[y];
                    item_name[z] = combat.getPlayer().getItem_name()[y];
                    item_desc[z] = combat.getPlayer().getItem_desc()[y];
                    item_type[z] = combat.getPlayer().getItem_type()[y];
                    item_subType[z] = combat.getPlayer().getItem_subType()[y];
                    item_param1[z] = combat.getPlayer().getItem_param1()[y];
                    item_param2[z] = combat.getPlayer().getItem_param2()[y];
                    item_ConsumePower[z] = combat.getPlayer().getItem_ConsumePower()[y];
                    specialAttackType[z] = combat.getPlayer().getSpecialAttackType()[y];
                    specialAttackParam[z] = combat.getPlayer().getSpecialAttackParam()[y];
                    z++;
                }
                if (!combat.isLost() && !combat.isRobot()) {
                    playerBuffCount[i] = combat.getPlayer().getBuffType().size();
                    buffType.addAll(combat.getPlayer().getBuffType());
                    buffParam1.addAll(combat.getPlayer().getBuffParam1());
                    buffParam2.addAll(combat.getPlayer().getBuffParam2());
                    buffParam3.addAll(combat.getPlayer().getBuffParam3());
                    WeapSkill weapSkill = combat.getPlayer().getWeapSkill1();
                    if (null != weapSkill) {
                        weaponSkillPlayerId.add(combat.getPlayer().getId());
                        weaponSkillName.add(weapSkill.getName());
                        weaponSkillType.add(weapSkill.getType());
                        weaponSkillChance.add(weapSkill.getChance());
                        weaponSkillParam1.add(weapSkill.getParam1());
                        weaponSkillParam2.add(weapSkill.getParam2());
                    }
                    weapSkill = combat.getPlayer().getWeapSkill2();
                    if (null != weapSkill) {
                        weaponSkillPlayerId.add(combat.getPlayer().getId());
                        weaponSkillName.add(weapSkill.getName());
                        weaponSkillType.add(weapSkill.getType());
                        weaponSkillChance.add(weapSkill.getChance());
                        weaponSkillParam1.add(weapSkill.getParam1());
                        weaponSkillParam2.add(weapSkill.getParam2());
                    }
                }
            }
            CrossSynPlayerInfo synPlayerInfo = new CrossSynPlayerInfo();
            synPlayerInfo.setBattleId(battleId);
            synPlayerInfo.setBattleMode(battleTeam.getBattleMode());
            synPlayerInfo.setBattleMap(battleMap);
            synPlayerInfo.setPlayerId(playerId);
            synPlayerInfo.setRoomIds(roomId);
            synPlayerInfo.setPlayerName(playerName);
            synPlayerInfo.setPlayerLevel(playerLevel);
            synPlayerInfo.setBoyOrGirl(boyOrGirl);
            synPlayerInfo.setSuit_head(suit_head);
            synPlayerInfo.setSuit_face(suit_face);
            synPlayerInfo.setSuit_body(suit_body);
            synPlayerInfo.setSuit_weapon(suit_weapon);
            synPlayerInfo.setWeapon_type(weapon_type);
            synPlayerInfo.setCamp(camp);
            synPlayerInfo.setMaxHP(maxHP);
            synPlayerInfo.setMaxPF(maxPF);
            synPlayerInfo.setMaxSP(maxSP);
            synPlayerInfo.setAttack(attack);
            synPlayerInfo.setBigSkillAttack(bigSkillAttack);
            synPlayerInfo.setCritRate(critRate);
            synPlayerInfo.setDefence(defence);
            synPlayerInfo.setBigSkillType(bigSkillType);
            synPlayerInfo.setExplodeRadius(explodeRadius);
            synPlayerInfo.setItem_id(item_id);
            synPlayerInfo.setItem_used(item_used);
            synPlayerInfo.setItem_img(item_img);
            synPlayerInfo.setItem_name(item_name);
            synPlayerInfo.setItem_desc(item_desc);
            synPlayerInfo.setItem_type(item_type);
            synPlayerInfo.setItem_subType(item_subType);
            synPlayerInfo.setItem_param1(item_param1);
            synPlayerInfo.setItem_param2(item_param2);
            synPlayerInfo.setItem_ConsumePower(item_ConsumePower);
            synPlayerInfo.setSpecialAttackType(specialAttackType);
            synPlayerInfo.setSpecialAttackParam(specialAttackParam);
            synPlayerInfo.setPlayerBuffCount(playerBuffCount);
            synPlayerInfo.setBuffType(ServiceUtils.getInts(buffType.toArray()));
            synPlayerInfo.setBuffParam1(ServiceUtils.getInts(buffParam1.toArray()));
            synPlayerInfo.setBuffParam2(ServiceUtils.getInts(buffParam2.toArray()));
            synPlayerInfo.setBuffParam3(ServiceUtils.getInts(buffParam3.toArray()));
            synPlayerInfo.setSuit_wing(suit_wing);
            synPlayerInfo.setPlayer_title(player_title);
            synPlayerInfo.setPlayer_community(player_community);
            synPlayerInfo.setWeaponSkillPlayerId(ServiceUtils.getInts(weaponSkillPlayerId.toArray()));
            synPlayerInfo.setWeaponSkillName(weaponSkillName.toArray(new String[] {}));
            synPlayerInfo.setWeaponSkillType(ServiceUtils.getInts(weaponSkillType.toArray()));
            synPlayerInfo.setWeaponSkillChance(ServiceUtils.getInts(weaponSkillChance.toArray()));
            synPlayerInfo.setWeaponSkillParam1(ServiceUtils.getInts(weaponSkillParam1.toArray()));
            synPlayerInfo.setWeaponSkillParam2(ServiceUtils.getInts(weaponSkillParam2.toArray()));
            synPlayerInfo.setInjuryFree(injuryFree);
            synPlayerInfo.setWreckDefense(wreckDefense);
            synPlayerInfo.setReduceCrit(reduceCrit);
            synPlayerInfo.setReduceBury(reduceBury);
            synPlayerInfo.setZsleve(zsleve);
            synPlayerInfo.setSkillful(skillful);
            synPlayerInfo.setPetId(petId);
            synPlayerInfo.setPetIcon(petIcon);
            synPlayerInfo.setPetType(petType);
            synPlayerInfo.setPetSkillId(petSkillId);
            synPlayerInfo.setPetProbability(petProbability);
            synPlayerInfo.setPetParam1(petParam1);
            synPlayerInfo.setPetParam2(petParam2);
            synPlayerInfo.setPetEffect(petEffect);
            synPlayerInfo.setSourcePlayerId(sourcePlayerId);
            synPlayerInfo.setServerId(serverId);
            synPlayerInfo.setServerName(serverName);
            synPlayerInfo.setPetVersion(petVersion);
            synPlayerInfo.setForce(force);
            synPlayerInfo.setArmor(armor);
            synPlayerInfo.setFighting(fighting);
            synPlayerInfo.setWinRate(winRate);
            synPlayerInfo.setConstitution(constitution);
            synPlayerInfo.setAgility(agility);
            synPlayerInfo.setLucky(lucky);
            sort(battleTeam);
            battleTeam.sendData(synPlayerInfo);
        }
    }

    /**
     * 获取战斗组
     * 
     * @param battleId
     * @return
     */
    public BattleTeam getBattleTeam(int battleId) {
        return battleTeamMap.get(battleId);
    }

    /**
     * 删除战斗组
     * 
     * @param battleId
     */
    public void deleteBattleTeam(BattleTeam battleTeam) {
        // System.out.println("DeleteBattleTeam------------" + battleTeam.getBattleId());
        battleTeamMap.remove(battleTeam.getBattleId());
        battleTeamList.remove(battleTeam);
    }

    /**
     * 加载完成准备进入战斗
     * 
     * @param battleId
     * @param playerId
     */
    public void ready(int battleId, int playerId) {
        BattleTeam battleTeam = battleTeamMap.get(battleId);
        if (null != battleTeam) battleTeam.ready(playerId);
    }

    /**
     * 是否所有玩家加载完成
     * 
     * @param battleId
     * @return
     */
    public boolean isReady(int battleId) {
        BattleTeam battleTeam = battleTeamMap.get(battleId);
        Vector<Combat> combatList = battleTeam.getCombatList();
        for (Combat combat : combatList) {
            if (!combat.isRobot() && !combat.isLost()) {
                if (2 != combat.getState()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getWind() {
        int num = ServiceUtils.getRandomNum(1, 101) - ServiceUtils.getRandomNum(1, 101);
        if (num == 0) {
            return 0;
        }
        int fh = Math.abs(num) / num;
        if (num < 13) {
            return 0;
        } else if (num < 38) {
            return 1 * fh;
        } else if (num < 60) {
            return 2 * fh;
        } else if (num < 79) {
            return 3 * fh;
        } else if (num < 90) {
            return 4 * fh;
        } else if (num < 97) {
            return 5 * fh;
        } else {
            return 6 * fh;
        }
    }

    /**
     * 回合结束发送新一轮的行动次序
     * 
     * @param battleId
     * @param playerId
     */
    public void sendSort(BattleTeam battleTeam, int playerId) {
        if (null == battleTeam) {
            return;
        }
        battleTeam.setNewRun(false);
        Vector<Combat> combatList = battleTeam.getCombatList();
        Combat combat = null;
        boolean chack = false;
        List<Combat> fList = new ArrayList<Combat>();
        for (int i = combatList.size() - 1; i > -1; i--) {
            combat = combatList.get(i);
            if (!combat.isLost() && !combat.isDead() && combat.getFrozenTimes() < 1) {
                break;
            } else if (combat.getPlayer().getId() == playerId || -1 == playerId) {
                chack = true;
            }
            fList.add(combat);
            combat = null;
        }
        if (null == combat) {
            try {
                if (gameOver(battleTeam, 0)) return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (chack || combat.getId() == playerId) {
            for (Combat c : fList) {
                frozenMinus(c);
            }
            battleTeam.setNewRun(true);
            battleTeam.setActionIndex(0);
            battleTeam.setWind(getWind());
            battleTeam.setRound(battleTeam.getRound() + 1);
            battleTeam.setPlayerIds(null);
            frozenOver(battleTeam);
            sort(battleTeam);
        }
    }

    /**
     * 返回角色攻击顺序
     * 
     * @param battleId
     * @return
     */
    public int[] sort(BattleTeam battleTeam) {
        if (null != battleTeam.getPlayerIds()) {
            return ArrayUtils.toPrimitive(battleTeam.getPlayerIds().toArray(new Integer[0]));
        }
        Vector<Combat> combatList = battleTeam.getCombatList();
        Vector<Integer> palyerIdList = new Vector<Integer>();
        battleTeam.setPlayerIds(palyerIdList);
        int size = combatList.size();
        int[] playerIds = new int[size];
        Comparator<Combat> ascComparator = new CombatComparator();
        Collections.sort(combatList, ascComparator);
        Combat combat;
        for (int i = 0; i < size; i++) {
            combat = combatList.get(i);
            combat.initPlayerBattleInfo();
            playerIds[i] = combat.getPlayer().getId();
            palyerIdList.add(playerIds[i]);
        }
        return playerIds;
    }

    /**
     * 减少冰冻回合
     * 
     * @param combat
     */
    public void frozenMinus(Combat combat) {
        if (combat.getFrozenTimes() > 0) {
            combat.setFrozenTimes(combat.getFrozenTimes() - 1);
            if (combat.getFrozenTimes() == 0) {
                // 等待新回合解除冰冻
                combat.setFrozenTimes(-1);
            }
        }
    }

    /**
     * 解除玩家冰冻状态
     * 
     * @param battleTeam
     */
    public void frozenOver(BattleTeam battleTeam) {
        List<Integer> frozeIdList = new ArrayList<Integer>();
        for (Combat combat : battleTeam.getCombatList()) {
            if (combat.getFrozenTimes() == -1) {
                combat.setFrozenTimes(0);
                frozeIdList.add(combat.getId());
            }
        }
        if (frozeIdList.size() > 0) {
            CrossFrozenOver frozenOver = new CrossFrozenOver();
            frozenOver.setPlayerIds(ServiceUtils.getInts(frozeIdList.toArray()));
            battleTeam.sendData(frozenOver);
        }
    }

    /**
     * 获得当前行动玩家
     * 
     * @param battleId
     * @return
     */
    public WorldPlayer getActionPlayer(int battleId) {
        BattleTeam battleTeam = battleTeamMap.get(battleId);
        Combat actionCombat = battleTeam.getActionPlayer();
        int i = 0;
        while (actionCombat.isLost() || actionCombat.isDead() || actionCombat.getFrozenTimes() > 0) {
            frozenMinus(actionCombat);
            actionCombat = battleTeam.getActionPlayer();
            i++;
            if (i >= battleTeam.getCombatNum()) {
                break;
            }
        }
        if (null != actionCombat) {
            return actionCombat.getPlayer();
        } else {
            return null;
        }
    }

    /**
     * 同步怒气值
     * 
     * @param battleId
     * @param playerId
     * @param value
     */
    public void updateAngryValue(int battleId, int playerId, int value, boolean useBigKill) {
        BattleTeam battleTeam = battleTeamMap.get(battleId);
        Combat combat = battleTeam.getCombatMap().get(playerId);
        if (useBigKill) {
            combat.setAngryValue(0);
        } else {
            int angryValue = combat.getAngryValue() + value;
            angryValue = angryValue > 100 ? 100 : angryValue;
            combat.setAngryValue(angryValue);
        }
        // System.out.println("playerId:"+playerId+"-----AngryValue:"+combat.getAngryValue());
        CrossChangeAngryValue changeAngryValue = new CrossChangeAngryValue();
        changeAngryValue.setBattleId(battleId);
        changeAngryValue.setPlayerId(playerId);
        changeAngryValue.setAngryValue(combat.getAngryValue());
        battleTeam.sendData(changeAngryValue);
    }

    /**
     * 判断游戏是否结束
     * 
     * @param battleId
     *            对战组id
     * @param operatCamp
     *            触发检测游戏结束的玩家阵营
     * @throws Exception
     */
    public boolean gameOver(BattleTeam battleTeam, int operatCamp) throws Exception {
        if (null == battleTeam) {
            return true;
        }
        Vector<Combat> combatList = battleTeam.getCombatList();
        boolean isOver = false;
        int winCamp = 0;
        if (battleTeam.getBattleMode() == 1 || battleTeam.getBattleMode() == 5 || battleTeam.getBattleMode() == 6) {
            boolean allDead0 = true;
            boolean allDead1 = true;
            for (Combat combat : combatList) {
                if (0 == combat.getCamp() && !combat.isDead()) {
                    allDead0 = false;
                }
                if (1 == combat.getCamp() && !combat.isDead()) {
                    allDead1 = false;
                }
            }
            if (allDead0 || allDead1) {
                isOver = true;
                if (allDead0 && allDead1) {
                    winCamp = operatCamp;
                } else if (allDead0) {
                    winCamp = 1;
                }
            }
        }
        if (battleTeam.getBattleMode() == 2) {
            if (battleTeam.isCampAllLost(0)) {
                isOver = true;
                winCamp = 1;
            }
            if (battleTeam.isCampAllLost(1)) {
                isOver = true;
                winCamp = 0;
            }
            if (battleTeam.getCamp0BeKillCount() >= 3 || battleTeam.getCamp1BeKillCount() >= 3 || (1 == battleTeam.getPlayerNumMode() && (battleTeam.getCamp0BeKillCount() >= 2 || battleTeam.getCamp1BeKillCount() >= 2))) {
                isOver = true;
                if (battleTeam.getCamp0BeKillCount() == battleTeam.getCamp1BeKillCount()) {
                    winCamp = operatCamp;
                } else if (battleTeam.getCamp0BeKillCount() > battleTeam.getCamp1BeKillCount()) {
                    winCamp = 1;
                }
            }
        }
        if (battleTeam.getBattleMode() == 3 || battleTeam.getBattleMode() == 4) {
            int aliveNum = 0;
            for (Combat combat : combatList) {
                if (!combat.isDead()) {
                    winCamp = combat.getCamp();
                    aliveNum++;
                }
            }
            if (0 == aliveNum) {
                isOver = true;
                winCamp = operatCamp;
            } else if (1 == aliveNum) {
                isOver = true;
            }
        }
        if (isOver && !battleTeam.isOver()) {
            try {
                int times0 = 0;
                int times1 = 1;
                int pLevel0 = 0;
                int pLevel1 = 0;
                int pcount = battleTeam.getCombatList().size();
                int[] playerIds = new int[pcount];
                int[] shootRate = new int[pcount];
                int[] totalHurt = new int[pcount];
                int[] killCount = new int[pcount];
                int[] beKilledCount = new int[pcount];
                int[] huntTimes = new int[pcount];
                int[] hps = new int[pcount];
                int[] pLevel = new int[pcount];
                int[] actionTimes = new int[pcount];
                int[] beKillRound = new int[pcount];
                boolean[] isEnforceQuit = new boolean[pcount];
                boolean[] isLost = new boolean[pcount];
                boolean[] isSuicide = new boolean[pcount];
                boolean[] isWin = new boolean[pcount];
                Combat combat;
                for (int i = 0; i < pcount; i++) {
                    combat = battleTeam.getCombatList().get(i);
                    if (0 == combat.getCamp()) {
                        times0 += combat.getShootTimes();
                        pLevel0 += combat.getPlayer().getLevel();
                    } else {
                        times1 += combat.getShootTimes();
                        pLevel1 += combat.getPlayer().getLevel();
                    }
                    playerIds[i] = combat.getPlayer().getId();
                    shootRate[i] = (int) ((combat.getHuntTimes() / (float) combat.getShootTimes()) * 100);
                    totalHurt[i] = combat.getTotalHurt();
                    killCount[i] = combat.getKillCount();
                    beKilledCount[i] = combat.getBeKilledCount();
                    huntTimes[i] = combat.getHuntTimes();
                    hps[i] = combat.getHp();
                    actionTimes[i] = combat.getActionTimes();
                    beKillRound[i] = combat.getBeKillRound();
                    isEnforceQuit[i] = combat.isEnforceQuit();
                    isLost[i] = combat.isLost();
                    isSuicide[i] = combat.isSuicide();
                    isWin[i] = winCamp == combat.getCamp() ? true : false;
                }
                int pc = (int) Math.ceil((pcount / 2d));
                if (pc > 0) {
                    pLevel0 = pLevel0 / pc;
                    pLevel1 = pLevel1 / pc;
                }
                for (int i = 0; i < pcount; i++) {
                    combat = battleTeam.getCombatList().get(i);
                    pLevel[i] = combat.getCamp() == 0 ? pLevel0 : pLevel1;
                }
                battleTeam.setOver(true);
                CrossGameOver gameOver = new CrossGameOver();
                gameOver.setBattleId(battleTeam.getBattleId());
                gameOver.setFirstHurtPlayerId(battleTeam.getFirstHurtPlayerId());
                gameOver.setWinCamp(winCamp);
                gameOver.setTimes0(times0);
                gameOver.setTimes1(times1);
                gameOver.setRound(battleTeam.getRound());
                gameOver.setPlayerCount(pcount);
                gameOver.setPlayerNumMode(battleTeam.getPlayerNumMode());
                gameOver.setPlayerIds(playerIds);
                gameOver.setShootRate(shootRate);
                gameOver.setTotalHurt(totalHurt);
                gameOver.setKillCount(killCount);
                gameOver.setBeKilledCount(beKilledCount);
                gameOver.setHuntTimes(huntTimes);
                gameOver.setHp(hps);
                gameOver.setpLevel(pLevel);
                gameOver.setActionTimes(actionTimes);
                gameOver.setBeKillRound(beKillRound);
                gameOver.setIsEnforceQuit(isEnforceQuit);
                gameOver.setIsLost(isLost);
                gameOver.setIsSuicide(isSuicide);
                gameOver.setIsWin(isWin);
                battleTeam.sendData(gameOver);
            } catch (Exception e) {
                e.printStackTrace();
            }
            exitBattle(battleTeam);
        }
        return isOver;
    }

    /**
     * 战斗退出
     * 
     * @param battleId
     * @throws Exception
     */
    public void exitBattle(BattleTeam battleTeam) {
        try {
            deleteBattleTeam(battleTeam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 玩家掉线
     * 
     * @param battleId
     * @param playerId
     * @param isDropped
     *            是否掉线
     * @throws Exception
     */
    public void playerLose(int battleId, int playerId) throws Exception {
        writeLog("battleId:" + battleId + "---playerLose:" + playerId);
        BattleTeam battleTeam = battleTeamMap.get(battleId);
        if (null == battleTeam) {
            return;
        }
        Combat combat = battleTeam.getCombatMap().get(playerId);
        if (null != combat.getPlayer()) {
            combat.getPlayer().setBattleId(0);
        }
        combat.setHp(0);
        combat.setLost(true);
        combat.setDead(true);
        if (playerId == battleTeam.getAicounter()) {
            battleTeam.setAicounter(0);
            sendAIControlCommon(battleId);
        }
        int camp = 0;
        CrossPlayerLose playerLose = new CrossPlayerLose();
        playerLose.setBattleId(battleId);
        playerLose.setCurrentPlayerId(playerId);
        battleTeam.sendData(playerLose);
        for (Combat cb : battleTeam.getCombatList()) {
            if (!cb.isLost() && !cb.isRobot() && null != cb.getPlayer()) {
                camp = cb.getCamp();
                break;
            }
        }
        if (0 != battleTeam.getStat()) {
            if (!gameOver(battleTeam, camp)) {
                int actionIndex = battleTeam.getActionIndex() - 1;
                actionIndex = actionIndex < 0 ? battleTeam.getCombatList().size() - 1 : actionIndex;
                if (playerId == battleTeam.getCombatList().get(actionIndex).getPlayer().getId()) {
                    CrossPass pass = new CrossPass();
                    pass.setBattleId(battleId);
                    pass.setPlayerId(playerId);
                    CrossPassHandler passHandler = new CrossPassHandler();
                    passHandler.handle(pass);
                }
                CrossEndCurRound endCurRound = new CrossEndCurRound();
                endCurRound.setBattleId(battleId);
                endCurRound.setPlayerId(playerId);
                endCurRound.setCurrentPlayerId(playerId);
                CrossEndCurRoundHandler endCurRoundHandler = new CrossEndCurRoundHandler();
                endCurRoundHandler.handle(endCurRound);
            }
        } else {
            if (battleTeam.isCampAllLost(0) && battleTeam.isCampAllLost(1)) {
                gameOver(battleTeam, camp);
            } else {
                CrossFinishLoading finishLoading = new CrossFinishLoading();
                finishLoading.setBattleId(battleId);
                finishLoading.setPlayerId(playerId);
                CrossFinishLoadingHandler finishLoadingHandler = new CrossFinishLoadingHandler();
                finishLoadingHandler.handle(finishLoading);
            }
        }
    }

    /**
     * 发送ai玩家信息
     * 
     * @param battleId
     * @param playerLose
     * @throws Exception
     */
    public void sendAIControlCommon(int battleId) throws Exception {
        BattleTeam battleTeam = battleTeamMap.get(battleId);
        Combat combat = null;
        if (0 != battleTeam.getAicounter()) {
            combat = battleTeam.getCombatMap().get(battleTeam.getAicounter());
        }
        List<Integer> playerId = new ArrayList<Integer>();
        List<Integer> aiCtrlId = new ArrayList<Integer>();
        for (Combat cb : battleTeam.getCombatList()) {
            if (null != cb.getPlayer()) {
                if (cb.isRobot()) {
                    playerId.add(cb.getPlayer().getId());
                    aiCtrlId.add(cb.getAiCtrlId());
                } else if (null == combat && !cb.isLost()) {
                    combat = cb;
                }
            }
        }
        if (playerId.size() > 0) {
            if (null != combat) {
                CrossAIControlCommon aIControlCommon = new CrossAIControlCommon();
                aIControlCommon.setPlayerId(combat.getPlayer().getId());
                aIControlCommon.setBattleId(battleTeam.getBattleId());
                aIControlCommon.setIdcount(playerId.size());
                aIControlCommon.setPlayerIds(ArrayUtils.toPrimitive(playerId.toArray(new Integer[0])));
                aIControlCommon.setAiCtrlId(ArrayUtils.toPrimitive(aiCtrlId.toArray(new Integer[0])));
                battleTeam.setAicounter(combat.getPlayer().getId());
                combat.getPlayer().sendData(aIControlCommon);
                writeLog("BattleTeam:" + battleId + "----AiCounter:" + battleTeam.getAicounter());
            } else {
                for (Combat cb : battleTeam.getCombatList()) {
                    if (cb.isRobot()) {
                        cb.setHp(0);
                        cb.setLost(true);
                        cb.setDead(true);
                    }
                }
            }
        }
    }

    /**
     * 角色已完成当前动作，可以开始新的动作
     * 
     * @param battleId
     * @param playerId
     */
    public void playerRun(int battleId, int playerId) {
        BattleTeam battleTeam = battleTeamMap.get(battleId);
        Combat cb = battleTeam.getCombatMap().get(playerId);
        cb.setCurRound(true);
        boolean c = true;
        for (Combat combat : battleTeam.getCombatList()) {
            if (!combat.isLost() && !combat.isRobot() && !combat.isCurRound()) {
                // System.out.println(combat.getId()+"----------playerRun false:"+combat.isCurRound());
                c = false;
                break;
            }
        }
        // System.out.println(playerId+"----------playerRun:" + c);
        battleTeam.setCurRound(c);
    }

    public void writeLog(Object message) {
        log.info(message);
    }
}