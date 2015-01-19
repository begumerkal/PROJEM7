package com.wyd.empire.battle.bean;

import com.wyd.empire.protocol.data.cross.CrossKillLine;

/**
 * 战斗成员
 * @author Administrator
 */
public class Combat {
    /** 角色对象 */
    private WorldPlayer         player;
    /** 用户所属阵营 */
    private int                 camp;
    /** 是否机器人，默认为否 */
    private boolean             robot         = false;
    /** 当前状态： 0表示等待加载，1表示正在加载，2表示加载完成，3表示正在战斗 */
    private int                 state         = 0;
    /** 玩家当前所在X坐标 */
    private float               x             = 0f;
    /** 玩家当前所在Y坐标 */
    private float               y             = 0f;
    /** 玩家当血量 */
    private int                 hp;
    /** 玩家体力值 */
    private float               pf;
    /** 玩家疲劳值，此值决定行动的次序 */
    private int                 tiredValue;
    /** 玩家怒气值 */
    private int                 angryValue;
    /** 本轮操作是否完成，默认为否 */
    private boolean             curRound      = false;
    /** 行动次数，默认为0 */
    private int                 actionTimes   = 0;
    /** 攻击次数，默认为0 */
    private int                 shootTimes    = 0;
    /** 命中次数，默认为0 */
    private int                 huntTimes     = 0;
    /** dps（伤害输出），默认为0 */
    private int                 totalHurt     = 0;
    /** 击退的玩家次数，默认为0 */
    private int                 killCount     = 0;
    /** 被击退的次数，默认为0 */
    private int                 beKilledCount = 0;
    /** 被杀回合数，默认为1 */
    private int                 beKillRound   = 1;
    /** 玩家复活次数，默认为0 */
    private int                 fhTimes       = 0;
    /** 玩家是否掉线，默认为否 */
    private boolean             lost          = false;
    /** 玩家获得的经验 */
    private int                 exp;
    /** 玩家是否掉胜利，默认为否 */
    private boolean             win           = false;
    /** 是否已经计算命中次数，默认为否 */
    private boolean             hit           = false;
    /** 玩家是否死亡，默认为否 */
    private boolean             dead          = false;
    /** 玩家是否自杀 */
    private boolean             suicide       = false;
    /** ai技能方案 */
    private int                 aiCtrlId;
    /** 玩家是否强退，默认为否 */
    private boolean             enforceQuit   = false;
    /** 变机器人后实际获得的经验 */
    private int                 cexp;
    /** 冰冻剩余回合数 -1表示等待解除冰冻*/
    private int                 frozenTimes   = 0;
    //防外挂验证---------------------------------------------------------------------
    private int                 playerAttackTimes             = 1;               // 玩家本回合可攻击次数
    private int                 skillAttackTimes              = 6;               // 玩家技能本回合可攻击次数
    private int                 continued                     = 1;               // 连射数
    private int                 scatter                       = 1;               // 散射数
    private int                 petAttackTimes                = 1;               // 宠物本回合可攻击次数
    private float               hurtRate                      = 1f;              // 本回合伤害倍率
    private boolean             hide                          = false;           // 是否隐身
    private int                 hideRound                     = 0;               // 隐身 剩余回合数
    private WeapSkill           CABuff                        = null;            // 持续扣血 buff
    private int                 CABPlayer                     = 0;               // 持续扣血 buff 触发玩家ID
    private int                 CABRound                      = 0;               // 持续扣血 buff 剩余回合数
    private int                 addAttackValue                = 0;               // 本回合攻击附加伤害
    private int                 critRate                      = 0;               // 本回合附加暴击率
    private int                 hurtToBloodRate               = 0;               // 本回合吸血比率
    private WeapSkill           weapSkill                     = null;            // 本回合触发的持续伤害BUFF
    private int                 willFrozen                    = 0;               // 本回合是否使用冰冻弹0表示不会其，他表示冰冻的回合数
    private boolean             isNuke                        = false;           // 玩家本回合是否触发核弹

    public WorldPlayer getPlayer() {
        return player;
    }

    public void setPlayer(WorldPlayer player) {
        this.player = player;
    }

    public int getCamp() {
        return camp;
    }

    public void setCamp(int camp) {
        this.camp = camp;
    }

    public boolean isRobot() {
        return robot;
    }

    public void setRobot(boolean robot) {
        this.robot = robot;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public float getPf() {
        return pf;
    }

    public void setPf(float pf) {
        this.pf = pf;
        if (this.pf < 0 && !this.isRobot() && !this.isLost() && null != this.getPlayer()) {
            killLine();
        }
    }

    public int getTiredValue() {
        return tiredValue;
    }

    public void setTiredValue(int tiredValue) {
        this.tiredValue = tiredValue;
    }

    public int getAngryValue() {
        return angryValue;
    }

    public void setAngryValue(int angryValue) {
        this.angryValue = angryValue;
    }

    public boolean isCurRound() {
        return curRound;
    }

    public void setCurRound(boolean curRound) {
        this.curRound = curRound;
    }

    public int getActionTimes() {
        return actionTimes;
    }

    public void setActionTimes(int actionTimes) {
        this.actionTimes = actionTimes;
    }

    public int getShootTimes() {
        return shootTimes;
    }

    public void setShootTimes(int shootTimes) {
        this.shootTimes = shootTimes;
    }

    public int getHuntTimes() {
        return huntTimes;
    }

    public void setHuntTimes(int huntTimes) {
        this.huntTimes = huntTimes;
    }

    public int getTotalHurt() {
        return totalHurt;
    }

    public void setTotalHurt(int totalHurt) {
        this.totalHurt = totalHurt;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    public int getBeKilledCount() {
        return beKilledCount;
    }

    public void setBeKilledCount(int beKilledCount) {
        this.beKilledCount = beKilledCount;
    }

    public int getBeKillRound() {
        return beKillRound;
    }

    public void setBeKillRound(int beKillRound) {
        this.beKillRound = beKillRound;
    }

    public int getFhTimes() {
        return fhTimes;
    }

    public void setFhTimes(int fhTimes) {
        this.fhTimes = fhTimes;
    }

    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isSuicide() {
        return suicide;
    }

    public void setSuicide(boolean suicide) {
        this.suicide = suicide;
    }

    public int getAiCtrlId() {
        return aiCtrlId;
    }

    public void setAiCtrlId(int aiCtrlId) {
        this.aiCtrlId = aiCtrlId;
    }

    public boolean isEnforceQuit() {
        return enforceQuit;
    }

    public void setEnforceQuit(boolean enforceQuit) {
        this.enforceQuit = enforceQuit;
    }
    
    public int getCexp() {
        return cexp;
    }

    public void setCexp(int cexp) {
        this.cexp = cexp;
    }

    public int getId() {
        return player.getId();
    }

    public int getLevel() {
        return player.getLevel();
    }

    public int getSex() {
        return player.getSex();
    }

    public String getName() {
        return player.getName();
    }

    public String getSuit_head() {
        return player.getSuit_head();
    }

    public String getSuit_face() {
        return player.getSuit_face();
    }

    public String getSuit_body() {
        return player.getSuit_body();
    }

    public String getSuit_weapon() {
        return player.getSuit_weapon();
    }

    public int getWeapon_type() {
        return player.getWeapon_type();
    }

    public String getSuit_wing() {
        return player.getSuit_wing();
    }

    public String getTitle() {
        return player.getTitle();
    }

    public String getGuildName() {
        return player.getGuildName();
    }

    public int getMaxHP() {
        return player.getMaxHP();
    }

    public int getMaxPF() {
        return player.getMaxPF();
    }

    public int getMaxSP() {
        return player.getMaxSP();
    }

    public int getAttack() {
        return player.getAttack();
    }

    public int getBigSkillAttack() {
        return player.getBigSkillAttack();
    }
    
    /**
     * 获取玩家暴击比率
     * @return
     */
    public int getCritAttackRate() {
        return player.getCritAttackRate();
    }
    
    /**
     * 获取玩家暴击概率
     * @return
     */
    public int getCritRate() {
        return player.getCritRate();
    }

    public int getDefend() {
        return player.getDefend();
    }

    public int getBigSkillType() {
        return player.getBigSkillType();
    }

    public int getExplodeRadius() {
        return player.getExplodeRadius();
    }

    public int IsCriticalHit() {
        return player.IsCriticalHit();
    }
    
    public int getInjuryFree() {
        return player.getInjuryFree();
    }

    public int getWreckDefense() {
        return player.getWreckDefense();
    }

    public int getReduceCrit() {
        return player.getReduceCrit();
    }

    public int getReduceBury() {
        return player.getReduceBury();
    }

    public int getFrozenTimes() {
        return frozenTimes;
    }

    public void setFrozenTimes(int frozenTimes) {
        this.frozenTimes = frozenTimes;
    }
    
  //防外挂验证---------------------------------------------------------------------
    public void setPlayerAttackTimes(int playerAttackTimes) {
        this.playerAttackTimes = playerAttackTimes;
//        System.out.println("玩家本回合可以攻击的次数:" + playerAttackTimes);
    }

    public void setSkillAttackTimes(int skillAttackTimes) {
        this.skillAttackTimes = skillAttackTimes;
    }

    public int getContinued() {
        return continued;
    }

    public void setContinued(int continued) {
        this.continued = continued;
    }

    public int getScatter() {
        return scatter;
    }

    public void setScatter(int scatter) {
        this.scatter = scatter;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide, int hideRound) {
        this.hide = hide;
        this.hideRound = hideRound;
//        System.out.println("SetHide Hide:"+hide+"---HideRound:"+hideRound);
    }

    public void setWillFrozen(int willFrozen) {
        this.willFrozen = willFrozen;
    }

    public int getWillFrozen() {
        return willFrozen;
    }

    public void initPlayerBattleInfo() {
        this.tiredValue = 0;// 初始化疲劳值
        this.hit = false;
        this.pf = this.player.getMaxPF();// 初始化体力值
        // 防外挂验证---------------------------------------------------------------------------------------
        this.playerAttackTimes = 1; // 玩家本回合可攻击次数
        this.skillAttackTimes = 6; // 玩家技能本回合可攻击次数
        this.continued = 1; // 连射数
        this.scatter = 1; // 散射数
        this.petAttackTimes = 1; // 宠物本回合可攻击次数
        this.hurtRate = 1f; // 本回合伤害倍率
        this.addAttackValue = 0; // 本回合攻击附加伤害
        this.critRate = 0; // 本回合附加暴击率
        this.hurtToBloodRate = 0; // 本回合吸血比率
        this.weapSkill = null;//本回合触发的持续伤害BUFF
        this.willFrozen = 0; // 本回合是否使用冰冻弹
        this.isNuke = false; // 本回合是否使触发核弹
        if (null != this.CABuff && frozenTimes == 0) {
            this.CABRound--;
            if (this.CABRound < 0) {
                resetCAB();
            }
        }
        if (this.hide && frozenTimes == 0) {
            this.hideRound--;
            if (this.hideRound <= 0) {// 隐身结束
                setHide(false, 0);
            }
        }
    }

    /**
     * 重置玩家持续扣血 buff信息
     */
    public void resetCAB() {
        this.CABuff = null; // 持续扣血 buff
        this.CABPlayer = 0; // 持续扣血 buff 触发玩家ID
        this.CABRound = 0; // 持续扣血 buff 剩余回合数
    }
    
    /**
     * 初始化玩家持续扣血 buff信息
     * @param buffId
     * @param playerId
     */
    public void initCAB(WeapSkill weapSkill, int playerId) {
        if (null != weapSkill) {
            this.CABuff = weapSkill;
            this.CABPlayer = playerId;
            this.CABRound = weapSkill.getParam2();
            // System.out.println("触发 CABRound:" + CABRound + "--------CABId:" + CABId + "------hurtplayer:" + getId() + "------shootplayer:" + playerId);
        }
    }
    
    /**
     * 获取玩家持续伤害值
     * @return
     */
    public int getCABHurt(int playerId) {
        int maxHurt = 0;
        // System.out.println("验证 CABRound:" + CABRound + "--------CABId:" + CABId + "------hurtplayer:" + getId() + "------shootplayer:" + playerId);
        if (null != CABuff && CABRound >= 0 && CABPlayer == playerId) {
            maxHurt = CABuff.getParam1();
        }
        return maxHurt;
    }

    /**
     * 检查玩家的射击次数
     * @param attackType
     * @return true 则踢下线
     */
    public boolean checkShoot(int attackType) {
        boolean kill = true;
        if (0 == attackType) {
            playerAttackTimes--;
        } else if (-2 == attackType) {
            petAttackTimes--;
        } else {
            skillAttackTimes--;
        }
        if (!this.isRobot() && !this.isLost() && null != this.player) {
            if (playerAttackTimes >= 0 && petAttackTimes >= 0 && skillAttackTimes >= 0) {
                kill = false;
            }
        } else {
            kill = false;
        }
        // 当玩家或玩家宠物攻击次数超出范围则踢下线
        if (kill) {
            killLine();
        }
        return kill;
    }
    
    public float getHurtRate() {
        return hurtRate;
    }

    public void setHurtRate(float hurtRate) {
        this.hurtRate = hurtRate;
    }

    /**
     * 验证玩家伤害
     * @param hurt 客户端计算的伤害值
     * @param attackType 攻击类型（-2:宠物攻击，-1:加血，0:普通伤害，其他:技能伤害的Id,GuaiPlayer中该参数为使用技能下标+1）
     * @param targetHero 被攻击者
     * @param distance 爆炸距离(isPVP为false时该值为BOSS技能伤害数值下标)
     * @param hurtFloat 伤害浮动空间 0~8（百分比）
     * @param critValue 暴击类型
     * @param hurtToBloodRate 受伤多少伤害转换为血量的比率(放大1万倍)
     * @param attackerRandom 攻击者武器技能触发使用随机数下标(isPVP为false时该值为BOSS伤害数组)
     * @param targetRandom 被攻击目标武器技能触发使用随机数下标(isPVP为false时该值为BOSS技能伤害数组)
     * @param battleRand 游戏随机数 
     * @return
     */
    public boolean verifyHurt(int hurt, int attackType, Combat targetHero, int distance, int hurtFloat, int critValue, int hurtToBloodRate, int[] attackerRandom, int[] targetRandom, int[] battleRand) {
        boolean kill = true;
        int maxHurt = 0;
            if (0 == attackType) {// 普通攻击
                maxHurt = (int) calculateHurt(targetHero, distance, hurtFloat, critValue, hurtToBloodRate, attackerRandom, targetRandom, battleRand, hurt);
//                System.out.println("attackType:" + attackType + "---maxHurt:" + maxHurt + "---hurt:" + hurt + "---hurtplayer:" + targetHero.getId() + "---shootplayer:" + getId());
                if (hurt == maxHurt) {
                    kill = false;
                }
            } else if (-2 == attackType) {// 宠物攻击
                maxHurt = (int) (getAttack() * (player.getPetParam1() * 0.0001));
//                System.out.println("attackType:" + attackType + "---maxHurt:" + maxHurt + "---hurt:" + hurt + "---hurtplayer:" + targetHero.getId() + "---shootplayer:" + getId());
                if (hurt == maxHurt) {
                    kill = false;
                }
            } else if (0 < attackType) {// 技能攻击
                maxHurt = targetHero.getCABHurt(getId());
//                System.out.println("attackType:" + attackType + "---maxHurt:" + maxHurt + "---hurt:" + hurt + "---hurtplayer:" + targetHero.getId() + "---shootplayer:" + getId());
                if (hurt == maxHurt) {
                    kill = false;
                }
            }
        if (kill) {
            killLine();
        }
        return kill;
    }
    
    /**
     * 踢玩家下线
     */
    public void killLine() {
        System.out.println("作弊踢下线:" + getId());
        CrossKillLine ckl = new CrossKillLine();
        ckl.setPlayerId(this.getId());
        this.getPlayer().sendData(ckl);
    }

    /**
     * 计算玩家攻击伤害
     * @param targetHero 被伤害玩家
     * @param distance 爆炸距离
     * @param hurtFloat 伤害浮动空间 0~8（百分比）
     * @param critValue 本回合是否暴击
     * @param hurtToBloodRate 受伤多少伤害转换为血量的比率(放大1万倍)
     * @param attackerRandom 攻击者武器技能触发使用随机数下标
     * @param targetRandom 被攻击目标武器技能触发使用随机数下标
     * @param battleRand 游戏随机数
     * @return
     */
    private double calculateHurt(Combat targetHero, int distance, int hurtFloat, int critValue, int hurtToBloodRate, int[] attackerRandom, int[] targetRandom, int[] battleRand, int realHurt) {
        targetHero.initCAB(this.weapSkill, getId());
        if(willFrozen>0){
            targetHero.setFrozenTimes(willFrozen);
            return 0;
        }
        if (targetHero.checkTargetSkill(targetRandom, battleRand)) {
            return 0;
        }
        if (hurtToBloodRate != this.hurtToBloodRate) {
            // 触发吸血数据不正确则返回伤害为0，使之踢下线
            return 0;
        }
        double hurt = 0;
        int attack = this.getAttack();
        // 破防
        double wreckDefense = this.getWreckDefense() * 0.0001;
        // 暴击倍率
        int critRate = this.getCritAttackRate();
        // 对方防御
        int defend = targetHero.getDefend();//
        // 对方免伤
        int injuryFree = targetHero.getInjuryFree();//
        // 减伤
        double reduceAcctak = (defend * (1 - wreckDefense)) / ((defend * (1 - wreckDefense)) + 600) + injuryFree * 0.0001;
        boolean iscrit = isCrit(targetHero, critValue, this.critRate, battleRand);
        // 暴击
        if (iscrit) {
            hurt = (attack * critRate * 0.0001 * 1.5 + attack * (1 - critRate * 0.0001)) * (1 - reduceAcctak);
        } else {
            // 非暴击
            hurt = attack * (1 - reduceAcctak);
        }
        // 伤害值在96%~104%之间浮动
        hurt = hurt * (hurtFloat + 96) * 0.01;
        if (hurt < attack * 0.1) {
            hurt = attack * 0.1;
        }
        // 技能损耗或加成
        hurt = hurt * this.hurtRate;
        int explodeRadius = isNuke ? 100 : this.getExplodeRadius();
        if (distance >= (int) (explodeRadius * 0.6)) {
            hurt *= 0.3;
        } else if (distance >= (int) (explodeRadius * 0.3)) {
            hurt *= 0.7;
        }
        if (targetHero.isHide()) {
            targetHero.setHide(false, 0);
            hurt *= 0.5;
        }
        hurt = hurt + this.addAttackValue;
        if (realHurt != (int) hurt) {
            System.out.println("伤害验证不一致 realHurt:" + realHurt + "   maxhurt:" + (int)hurt + "    attack:" + attack + "   wreckDefense:" + wreckDefense + "   critRate:" + critRate + "   targetDefend:" + defend + " injuryFree:" + injuryFree + "   reduceAcctak:" + reduceAcctak + "   iscrit:" + iscrit + "   hurtFloat:" + hurtFloat + " hurtRate:" + hurtRate + "   distance:" + distance + "   ExplodeRadius:" + this.getExplodeRadius() + "   targetHide:" + targetHero.isHide() + "  addAttackValue:" + addAttackValue);
        }
        return hurt;
    }
    
    /**
     * 检查攻击者单个炸弹触发的武器技能
     * @param attackerRandom 攻击者武器技能触发使用随机数下标
     * @param battleRand 游戏随机数
     * @return 
     */
    public void checkAttackerSkill(int[] attackerRandom, int[] battleRand) {
        boolean trigger = false;
        if (null != player.getWeapSkill1()) {
            WeapSkill weapSkill = player.getWeapSkill1();
            if (null != weapSkill && attackerRandom[0] > -1 && attackerRandom[0] < battleRand.length && battleRand[attackerRandom[0]] < weapSkill.getChance()) {
                trigger = true;
                switch (weapSkill.getType()) {
                case 1:// 攻击
                    setHurtRate(getHurtRate() * (1f + weapSkill.getParam1() / 10000f));
                    break;
                case 2:// 伤害
                    this.addAttackValue = weapSkill.getParam1();
                    break;
                case 3:// 暴击
                    this.critRate = weapSkill.getParam1();
                    break;
                case 7:// 吸血
                    this.hurtToBloodRate = weapSkill.getParam1();
                    break;
                case 9:// 连击
                    setContinued(getContinued() + weapSkill.getParam2());
                    setPlayerAttackTimes(getContinued() * getScatter());
                    setHurtRate(getHurtRate() * (weapSkill.getParam1() / 10000f));
                    break;
                case 11:// 核弹
                    isNuke = true;
                case 10:// 引导
                    setHurtRate(getHurtRate() * (weapSkill.getParam1() / 10000f));
                    break;
                case 4:// 灼伤
                case 12:// 毒素
                case 13:// 寒冰
                    this.weapSkill = weapSkill;
                    break;
                }
            }
        }
        if (!trigger && null != player.getWeapSkill2()) {
            WeapSkill weapSkill = player.getWeapSkill2();
            if (null != weapSkill && attackerRandom[1] > -1 && attackerRandom[1] < battleRand.length && battleRand[attackerRandom[1]] < weapSkill.getChance()) {
                switch (weapSkill.getType()) {
                case 1:// 攻击
                    setHurtRate(getHurtRate() * (1f + weapSkill.getParam1() / 10000f));
                    break;
                case 2:// 伤害
                    this.addAttackValue = weapSkill.getParam1();
                    break;
                case 3:// 暴击
                    this.critRate = weapSkill.getParam1();
                    break;
                case 7:// 吸血
                    this.hurtToBloodRate = weapSkill.getParam1();
                    break;
                case 9:// 连击
                    setContinued(getContinued() + weapSkill.getParam2());
                    setPlayerAttackTimes(getContinued() * getScatter());
                    setHurtRate(getHurtRate() * (weapSkill.getParam1() / 10000f));
                    break;
                case 11:// 核弹
                    isNuke = true;
                case 10:// 引导
                    setHurtRate(getHurtRate() * (weapSkill.getParam1() / 10000f));
                    break;
                case 4:// 灼伤
                case 12:// 毒素
                case 13:// 寒冰
                    this.weapSkill = weapSkill;
                    break;
                }
            }
        }
    }
    
    /**
     * 检查被攻击者触发的武器技能
     * @param targetRandom 被攻击目标武器技能触发使用随机数下标
     * @param battleRand 游戏随机数
     * @return 返回被攻击者是否免伤
     */
    private boolean checkTargetSkill(int[] targetRandom, int[] battleRand) {
        boolean notHurt = false;
        boolean trigger = false;
        if (null != player.getWeapSkill1()) {
            WeapSkill weapSkill = player.getWeapSkill1();
            if (null != weapSkill && targetRandom[0] > -1 && targetRandom[0] < battleRand.length && battleRand[targetRandom[0]] < weapSkill.getChance()) {
                trigger = true;
                switch (weapSkill.getType()) {
                case 19:// 免疫
                    resetCAB();
                case 18:// 吸收
                    notHurt = true;
                    break;
                }
            }
        }
        if (!trigger && null != player.getWeapSkill2()) {
            WeapSkill weapSkill = player.getWeapSkill2();
            if (null != weapSkill && targetRandom[1] > -1 && targetRandom[1] < battleRand.length && battleRand[targetRandom[1]] < weapSkill.getChance()) {
                switch (weapSkill.getType()) {
                case 19:// 免疫
                    resetCAB();
                case 18:// 吸收
                    notHurt = true;
                    break;
                }
            }
        }
        return notHurt;
    }
    
    /**
     * 计算玩家是否暴击
     * @param targetHero 被攻击者
     * @param critValue 暴击概率
     * @param critRate 被动技能暴击概率
     * @return
     */
    private boolean isCrit(Combat targetHero, int critValue, int critRate, int[] battleRand) {
        boolean crit = false;
        if (critValue == 1) {
            int randNumber = battleRand[0] + 1;
            if (randNumber > targetHero.getReduceCrit()) {
                crit = true;
            }
        } else {
            int randNumber = battleRand[1] + 1;
            if (randNumber <= critRate) {
                crit = true;
            }
        }
        return crit;
    }
}
