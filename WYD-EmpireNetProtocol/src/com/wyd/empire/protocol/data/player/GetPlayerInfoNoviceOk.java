package com.wyd.empire.protocol.data.player;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class GetPlayerInfoNoviceOk extends AbstractData {
    private int      playerId;
    private String   playerName;
    private int      tickets;
    private int      maxLevel;
    private int      playerHp;
    private int      playerDefend;
    private int      playerPhysical;
    private int      playerDefense;
    private int      playerGold;
    private int      playerHonor;
    private int      playerSex;
    private int      level;
    private int      attack;
    private int      exp;
    private String   guildName;
    private int      medalNum;
    private int      critRate;
    private int      explodeRadius;
    private int      proficiency;
    private String   suit_head;
    private String   suit_face;
    private String   suit_body;
    private String   suit_weapon;
    private int      weapon_type;
    private int      upgradeexp;
    private int      vipLevel;
    private String   suit_wing;
    private String   player_title;
    private int      weaponLevel;
    private String[] wbUserId;
    private int      zsleve;        // 转生等级
    private int      injuryFree;    // 免伤
    private int      wreckDefense;  // 破防
    private int      reduceCrit;    // 免暴
    private int      reduceBury;    // 免坑
    private int      force;         // 力量
    private int      armor;         // 护甲
    private int      agility;       // 敏捷
    private int      physique;      // 体质
    private int      luck;          // 幸运
    private int      fighting;      // 战斗力
    private int      vipMark;       // vip状态（0，不是Vip，1是临时vip，2是正式vip）
    private int      vipLastDay;    // vip剩余天数（用于临时vip提示）
    private int      heart;         // 爱心数

    public GetPlayerInfoNoviceOk(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetPlayerInfoNoviceOk, sessionId, serial);
    }

    public GetPlayerInfoNoviceOk() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetPlayerInfoNoviceOk);
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getPlayerHp() {
        return playerHp;
    }

    public void setPlayerHp(int playerHp) {
        this.playerHp = playerHp;
    }

    public int getPlayerDefend() {
        return playerDefend;
    }

    public void setPlayerDefend(int playerDefend) {
        this.playerDefend = playerDefend;
    }

    public int getPlayerPhysical() {
        return playerPhysical;
    }

    public void setPlayerPhysical(int playerPhysical) {
        this.playerPhysical = playerPhysical;
    }

    public int getPlayerDefense() {
        return playerDefense;
    }

    public void setPlayerDefense(int playerDefense) {
        this.playerDefense = playerDefense;
    }

    public int getPlayerGold() {
        return playerGold;
    }

    public void setPlayerGold(int playerGold) {
        this.playerGold = playerGold;
    }

    public int getPlayerHonor() {
        return playerHonor;
    }

    public void setPlayerHonor(int playerHonor) {
        this.playerHonor = playerHonor;
    }

    public int getPlayerSex() {
        return playerSex;
    }

    public void setPlayerSex(int playerSex) {
        this.playerSex = playerSex;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public int getMedalNum() {
        return medalNum;
    }

    public void setMedalNum(int medalNum) {
        this.medalNum = medalNum;
    }

    public int getCritRate() {
        return critRate;
    }

    public void setCritRate(int critRate) {
        this.critRate = critRate;
    }

    public int getExplodeRadius() {
        return explodeRadius;
    }

    public void setExplodeRadius(int explodeRadius) {
        this.explodeRadius = explodeRadius;
    }

    public int getProficiency() {
        return proficiency;
    }

    public void setProficiency(int proficiency) {
        this.proficiency = proficiency;
    }

    public String getSuit_head() {
        return suit_head;
    }

    public void setSuit_head(String suit_head) {
        this.suit_head = suit_head;
    }

    public String getSuit_face() {
        return suit_face;
    }

    public void setSuit_face(String suit_face) {
        this.suit_face = suit_face;
    }

    public String getSuit_body() {
        return suit_body;
    }

    public void setSuit_body(String suit_body) {
        this.suit_body = suit_body;
    }

    public String getSuit_weapon() {
        return suit_weapon;
    }

    public void setSuit_weapon(String suit_weapon) {
        this.suit_weapon = suit_weapon;
    }

    public int getWeapon_type() {
        return weapon_type;
    }

    public void setWeapon_type(int weapon_type) {
        this.weapon_type = weapon_type;
    }

    public int getUpgradeexp() {
        return upgradeexp;
    }

    public void setUpgradeexp(int upgradeexp) {
        this.upgradeexp = upgradeexp;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getSuit_wing() {
        return suit_wing;
    }

    public void setSuit_wing(String suit_wing) {
        this.suit_wing = suit_wing;
    }

    public String getPlayer_title() {
        return player_title;
    }

    public void setPlayer_title(String player_title) {
        this.player_title = player_title;
    }

    public int getWeaponLevel() {
        return weaponLevel;
    }

    public void setWeaponLevel(int weaponLevel) {
        this.weaponLevel = weaponLevel;
    }

    public String[] getWbUserId() {
        return wbUserId;
    }

    public void setWbUserId(String[] wbUserId) {
        this.wbUserId = wbUserId;
    }

    public int getZsleve() {
        return zsleve;
    }

    public void setZsleve(int zsleve) {
        this.zsleve = zsleve;
    }

    public int getInjuryFree() {
        return injuryFree;
    }

    public void setInjuryFree(int injuryFree) {
        this.injuryFree = injuryFree;
    }

    public int getWreckDefense() {
        return wreckDefense;
    }

    public void setWreckDefense(int wreckDefense) {
        this.wreckDefense = wreckDefense;
    }

    public int getReduceCrit() {
        return reduceCrit;
    }

    public void setReduceCrit(int reduceCrit) {
        this.reduceCrit = reduceCrit;
    }

    public int getReduceBury() {
        return reduceBury;
    }

    public void setReduceBury(int reduceBury) {
        this.reduceBury = reduceBury;
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getPhysique() {
        return physique;
    }

    public void setPhysique(int physique) {
        this.physique = physique;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public int getFighting() {
        return fighting;
    }

    public void setFighting(int fighting) {
        this.fighting = fighting;
    }

    public int getVipMark() {
        return vipMark;
    }

    public void setVipMark(int vipMark) {
        this.vipMark = vipMark;
    }

    public int getVipLastDay() {
        return vipLastDay;
    }

    public void setVipLastDay(int vipLastDay) {
        this.vipLastDay = vipLastDay;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

}
