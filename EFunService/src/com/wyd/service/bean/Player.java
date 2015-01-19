package com.wyd.service.bean;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * The persistent class for the tab_player database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_player")
public class Player implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;
    private Integer           id;
    private Integer           accountId;
    private Integer           areaId;
    private Integer           attack;
    private Integer           bossmapProgress;
    private Integer           chatStatus;
    private Integer           channel;
    private Integer           defend;
    private Integer           defense;
    private Integer           exp;
    private Integer           gp;
    private Integer           honor;
    private Integer           hp;
    private Integer           itemDj1;
    private Integer           itemDj2;
    private Integer           itemDj3;
    private Integer           itemDj4;
    private Integer           itemJn1;
    private Integer           itemJn2;
    private Integer           itemJn3;
    private Integer           itemJn4;
    private Integer           level;
    private Integer           moneyGold;
    private String            name;
    private Integer           onLineTime;
    private Integer           playTimes1v1Athletics;
    private Integer           playTimes1v1Champion;
    private Integer           playTimes1v1Relive;
    private Integer           playTimes2v2Athletics;
    private Integer           playTimes2v2Champion;
    private Integer           playTimes2v2Relive;
    private Integer           playTimes3v3Athletics;
    private Integer           playTimes3v3Champion;
    private Integer           playTimes3v3Relive;
    private Byte              sex;
    private Byte              status;
    private Integer           suitBodyId;
    private Integer           suitFaceId;
    private Integer           suitHeadId;
    private Integer           suitWeaponId;
    private String            suitWearCode;
    private Integer           suitWingId;
    private Integer           suitRing1Id;
    private Integer           suitRing2Id;
    private Integer           suitNecklaceId;
    private Integer           vipExp;
    private Integer           vipLevel;
    private Date              vipTime;
    private Integer           winTimes1v1Athletics;
    private Integer           winTimes1v1Champion;
    private Integer           winTimes1v1Relive;
    private Integer           winTimes2v2Athletics;
    private Integer           winTimes2v2Champion;
    private Integer           winTimes2v2Relive;
    private Integer           winTimes3v3Athletics;
    private Integer           winTimes3v3Champion;
    private Integer           winTimes3v3Relive;
    private Date              prohibitTime;
    private Date              updateTime;
    private Date              taskUpdateTime;
    private Date              bsTime;               // 封禁开始时间
    private Date              beTime;               // 封禁结束时间
    private String            udid;
    private String            token;
    private Integer           amount;
    private String            mac;
    private Date              createTime;
    private Integer           accumulateDiamond;
    private int               honorLevel;
    private Integer           lotteryCount;
    private String            wbUserId;             // 微博UID
    private String            inviteCode;           // 玩家的邀请码
    private String            bindInviteCode;       // 玩家绑定的邀请码
    private int               inviteGrade;          // 玩家的邀请奖励级别
    private String            channelInfo;          // 用户渠道绑定相关的信息，91的用户用于记录用户基金信息
    private String            wbUserIcon;           // 微博头像url
    private int               zsLevel;              // 玩家的转生等级
    private String            zsOldLevel;           // 玩家转生时的等级
    private int               injuryFree;           // 免伤
    private int               wreckDefense;         // 破防
    private int               reduceCrit;           // 免暴
    private int               reduceBury;           // 免坑
    private int               force;                // 力量
    private int               armor;                // 护甲
    private int               agility;              // 敏捷
    private int               physique;             // 体质
    private int               luck;                 // 幸运
    private int               steps;                // 新手教程步骤
    private int				  refreshDay;	 //抽奖刷新的天数 	

    public Player() {
    }

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false, precision = 10)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic()
    @Column(name = "accountId", precision = 10)
    public Integer getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Basic()
    @Column(name = "areaId", precision = 10)
    public Integer getAreaId() {
        return this.areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    @Basic()
    @Column(name = "attack", precision = 10)
    public Integer getAttack() {
        return this.attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    @Basic()
    @Column(name = "bossmap_progress", precision = 10)
    public Integer getBossmapProgress() {
        if (bossmapProgress == null) {
            bossmapProgress = 1;
        }
        return bossmapProgress;
    }

    @Basic()
    @Column(name = "chat_status", precision = 1)
    public Integer getChatStatus() {
        if (chatStatus == null) {
            chatStatus = 1;
        }
        return chatStatus;
    }

    @Basic()
    @Column(name = "channel", precision = 10)
    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public void setChatStatus(Integer chatStatus) {
        this.chatStatus = chatStatus;
    }

    public void setBossmapProgress(Integer bossmapProgress) {
        this.bossmapProgress = bossmapProgress;
    }

    @Basic()
    @Column(name = "defend", precision = 10)
    public Integer getDefend() {
        return this.defend;
    }

    public void setDefend(Integer defend) {
        this.defend = defend;
    }

    @Basic()
    @Column(name = "defense", precision = 10)
    public Integer getDefense() {
        return this.defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    @Basic()
    @Column(name = "exp", precision = 10)
    public Integer getExp() {
        return this.exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    @Basic()
    @Column(name = "gp", precision = 10)
    public Integer getGp() {
        return this.gp;
    }

    public void setGp(Integer gp) {
        this.gp = gp;
    }

    @Basic()
    @Column(name = "honor", precision = 10)
    public Integer getHonor() {
        return this.honor;
    }

    public void setHonor(Integer honor) {
        this.honor = honor;
    }

    @Basic()
    @Column(name = "hp", precision = 10)
    public Integer getHp() {
        if (hp == null || hp < 1000) {
            hp = 1000;
        }
        return this.hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    @Basic()
    @Column(name = "item_dj_1", precision = 10)
    public Integer getItemDj1() {
        return this.itemDj1;
    }

    public void setItemDj1(Integer itemDj1) {
        this.itemDj1 = itemDj1;
    }

    @Basic()
    @Column(name = "item_dj_2", precision = 10)
    public Integer getItemDj2() {
        return this.itemDj2;
    }

    public void setItemDj2(Integer itemDj2) {
        this.itemDj2 = itemDj2;
    }

    @Basic()
    @Column(name = "item_dj_3", precision = 10)
    public Integer getItemDj3() {
        return this.itemDj3;
    }

    public void setItemDj3(Integer itemDj3) {
        this.itemDj3 = itemDj3;
    }

    @Basic()
    @Column(name = "item_dj_4", precision = 10)
    public Integer getItemDj4() {
        return this.itemDj4;
    }

    public void setItemDj4(Integer itemDj4) {
        this.itemDj4 = itemDj4;
    }

    @Basic()
    @Column(name = "item_jn_1", precision = 10)
    public Integer getItemJn1() {
        return this.itemJn1;
    }

    public void setItemJn1(Integer itemJn1) {
        this.itemJn1 = itemJn1;
    }

    @Basic()
    @Column(name = "item_jn_2", precision = 10)
    public Integer getItemJn2() {
        return this.itemJn2;
    }

    public void setItemJn2(Integer itemJn2) {
        this.itemJn2 = itemJn2;
    }

    @Basic()
    @Column(name = "item_jn_3", precision = 10)
    public Integer getItemJn3() {
        return this.itemJn3;
    }

    public void setItemJn3(Integer itemJn3) {
        this.itemJn3 = itemJn3;
    }

    @Basic()
    @Column(name = "item_jn_4", precision = 10)
    public Integer getItemJn4() {
        return this.itemJn4;
    }

    public void setItemJn4(Integer itemJn4) {
        this.itemJn4 = itemJn4;
    }

    @Basic()
    @Column(name = "level", precision = 10)
    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Basic()
    @Column(name = "money_gold", precision = 10)
    public Integer getMoneyGold() {
        return this.moneyGold;
    }

    public void setMoneyGold(Integer moneyGold) {
        this.moneyGold = moneyGold;
    }

    @Basic()
    @Column(name = "name", length = 255)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic()
    @Column(name = "onLineTime", precision = 10)
    public Integer getOnLineTime() {
        return this.onLineTime;
    }

    public void setOnLineTime(Integer onLineTime) {
        this.onLineTime = onLineTime;
    }

    @Basic()
    @Column(name = "playTimes1v1_athletics", precision = 10)
    public Integer getPlayTimes1v1Athletics() {
        return this.playTimes1v1Athletics;
    }

    public void setPlayTimes1v1Athletics(Integer playTimes1v1Athletics) {
        this.playTimes1v1Athletics = playTimes1v1Athletics;
    }

    @Basic()
    @Column(name = "playTimes1v1_champion", precision = 10)
    public Integer getPlayTimes1v1Champion() {
        return this.playTimes1v1Champion;
    }

    public void setPlayTimes1v1Champion(Integer playTimes1v1Champion) {
        this.playTimes1v1Champion = playTimes1v1Champion;
    }

    @Basic()
    @Column(name = "playTimes1v1_relive", precision = 10)
    public Integer getPlayTimes1v1Relive() {
        return this.playTimes1v1Relive;
    }

    public void setPlayTimes1v1Relive(Integer playTimes1v1Relive) {
        this.playTimes1v1Relive = playTimes1v1Relive;
    }

    @Basic()
    @Column(name = "playTimes2v2_athletics", precision = 10)
    public Integer getPlayTimes2v2Athletics() {
        return this.playTimes2v2Athletics;
    }

    public void setPlayTimes2v2Athletics(Integer playTimes2v2Athletics) {
        this.playTimes2v2Athletics = playTimes2v2Athletics;
    }

    @Basic()
    @Column(name = "playTimes2v2_champion", precision = 10)
    public Integer getPlayTimes2v2Champion() {
        return this.playTimes2v2Champion;
    }

    public void setPlayTimes2v2Champion(Integer playTimes2v2Champion) {
        this.playTimes2v2Champion = playTimes2v2Champion;
    }

    @Basic()
    @Column(name = "playTimes2v2_relive", precision = 10)
    public Integer getPlayTimes2v2Relive() {
        return this.playTimes2v2Relive;
    }

    public void setPlayTimes2v2Relive(Integer playTimes2v2Relive) {
        this.playTimes2v2Relive = playTimes2v2Relive;
    }

    @Basic()
    @Column(name = "playTimes3v3_athletics", precision = 10)
    public Integer getPlayTimes3v3Athletics() {
        return this.playTimes3v3Athletics;
    }

    public void setPlayTimes3v3Athletics(Integer playTimes3v3Athletics) {
        this.playTimes3v3Athletics = playTimes3v3Athletics;
    }

    @Basic()
    @Column(name = "playTimes3v3_champion", precision = 10)
    public Integer getPlayTimes3v3Champion() {
        return this.playTimes3v3Champion;
    }

    public void setPlayTimes3v3Champion(Integer playTimes3v3Champion) {
        this.playTimes3v3Champion = playTimes3v3Champion;
    }

    @Basic()
    @Column(name = "playTimes3v3_relive", precision = 10)
    public Integer getPlayTimes3v3Relive() {
        return this.playTimes3v3Relive;
    }

    public void setPlayTimes3v3Relive(Integer playTimes3v3Relive) {
        this.playTimes3v3Relive = playTimes3v3Relive;
    }

    @Basic()
    @Column(name = "sex", precision = 3)
    public Byte getSex() {
        return this.sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    @Basic()
    @Column(name = "status", precision = 3)
    public Byte getStatus() {
        return this.status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic()
    @Column(name = "suit_body_id", precision = 10)
    public Integer getSuitBodyId() {
        return this.suitBodyId;
    }

    public void setSuitBodyId(Integer suitBodyId) {
        this.suitBodyId = suitBodyId;
    }

    @Basic()
    @Column(name = "suit_face_id", precision = 10)
    public Integer getSuitFaceId() {
        return this.suitFaceId;
    }

    public void setSuitFaceId(Integer suitFaceId) {
        this.suitFaceId = suitFaceId;
    }

    @Basic()
    @Column(name = "suit_head_id", precision = 10)
    public Integer getSuitHeadId() {
        return this.suitHeadId;
    }

    public void setSuitHeadId(Integer suitHeadId) {
        this.suitHeadId = suitHeadId;
    }

    @Basic()
    @Column(name = "suit_weapon_id", precision = 10)
    public Integer getSuitWeaponId() {
        return this.suitWeaponId;
    }

    public void setSuitWeaponId(Integer suitWeaponId) {
        this.suitWeaponId = suitWeaponId;
    }

    @Basic()
    @Column(name = "suit_wear_code", length = 255)
    public String getSuitWearCode() {
        return this.suitWearCode;
    }

    public void setSuitWearCode(String suitWearCode) {
        this.suitWearCode = suitWearCode;
    }

    @Basic()
    @Column(name = "suit_wing_id", precision = 10)
    public Integer getSuitWingId() {
    	if(null==suitWingId){
    		return 0;
    	}
        return suitWingId;
    }

    public void setSuitWingId(Integer suitWingId) {
        this.suitWingId = suitWingId;
    }

    @Basic()
    @Column(name = "suit_ring1_id", precision = 10)
    public Integer getSuitRing1Id() {
    	if(null==suitRing1Id){
    		return 0;
    	}
		return suitRing1Id;
	}

	public void setSuitRing1Id(Integer suitRing1Id) {
		this.suitRing1Id = suitRing1Id;
	}

    @Basic()
    @Column(name = "suit_ring2_id", precision = 10)
	public Integer getSuitRing2Id() {
    	if(null==suitRing2Id){
    		return 0;
    	}
		return suitRing2Id;
	}

	public void setSuitRing2Id(Integer suitRing2Id) {
		this.suitRing2Id = suitRing2Id;
	}

    @Basic()
    @Column(name = "suit_necklace_id", precision = 10)
	public Integer getSuitNecklaceId() {
    	if(null==suitNecklaceId){
    		return 0;
    	}
		return suitNecklaceId;
	}

	public void setSuitNecklaceId(Integer suitNecklaceId) {
		this.suitNecklaceId = suitNecklaceId;
	}

	@Basic()
    @Column(name = "vipExp", precision = 10)
    public Integer getVipExp() {
        return this.vipExp;
    }

    public void setVipExp(Integer vipExp) {
        this.vipExp = vipExp;
    }

    @Basic()
    @Column(name = "vipLevel", precision = 10)
    public Integer getVipLevel() {
        return this.vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }

    @Basic()
    @Column(name = "vipTime")
    public Date getVipTime() {
        return this.vipTime;
    }

    public void setVipTime(Date vipTime) {
        this.vipTime = vipTime;
    }

    @Basic()
    @Column(name = "winTimes1v1_athletics", precision = 10)
    public Integer getWinTimes1v1Athletics() {
        return this.winTimes1v1Athletics;
    }

    public void setWinTimes1v1Athletics(Integer winTimes1v1Athletics) {
        this.winTimes1v1Athletics = winTimes1v1Athletics;
    }

    @Basic()
    @Column(name = "winTimes1v1_champion", precision = 10)
    public Integer getWinTimes1v1Champion() {
        return this.winTimes1v1Champion;
    }

    public void setWinTimes1v1Champion(Integer winTimes1v1Champion) {
        this.winTimes1v1Champion = winTimes1v1Champion;
    }

    @Basic()
    @Column(name = "winTimes1v1_relive", precision = 10)
    public Integer getWinTimes1v1Relive() {
        return this.winTimes1v1Relive;
    }

    public void setWinTimes1v1Relive(Integer winTimes1v1Relive) {
        this.winTimes1v1Relive = winTimes1v1Relive;
    }

    @Basic()
    @Column(name = "winTimes2v2_athletics", precision = 10)
    public Integer getWinTimes2v2Athletics() {
        return this.winTimes2v2Athletics;
    }

    public void setWinTimes2v2Athletics(Integer winTimes2v2Athletics) {
        this.winTimes2v2Athletics = winTimes2v2Athletics;
    }

    @Basic()
    @Column(name = "winTimes2v2_champion", precision = 10)
    public Integer getWinTimes2v2Champion() {
        return this.winTimes2v2Champion;
    }

    public void setWinTimes2v2Champion(Integer winTimes2v2Champion) {
        this.winTimes2v2Champion = winTimes2v2Champion;
    }

    @Basic()
    @Column(name = "winTimes2v2_relive", precision = 10)
    public Integer getWinTimes2v2Relive() {
        return this.winTimes2v2Relive;
    }

    public void setWinTimes2v2Relive(Integer winTimes2v2Relive) {
        this.winTimes2v2Relive = winTimes2v2Relive;
    }

    @Basic()
    @Column(name = "winTimes3v3_athletics", precision = 10)
    public Integer getWinTimes3v3Athletics() {
        return this.winTimes3v3Athletics;
    }

    public void setWinTimes3v3Athletics(Integer winTimes3v3Athletics) {
        this.winTimes3v3Athletics = winTimes3v3Athletics;
    }

    @Basic()
    @Column(name = "winTimes3v3_champion", precision = 10)
    public Integer getWinTimes3v3Champion() {
        return this.winTimes3v3Champion;
    }

    public void setWinTimes3v3Champion(Integer winTimes3v3Champion) {
        this.winTimes3v3Champion = winTimes3v3Champion;
    }

    @Basic()
    @Column(name = "winTimes3v3_relive", precision = 10)
    public Integer getWinTimes3v3Relive() {
        return this.winTimes3v3Relive;
    }

    public void setWinTimes3v3Relive(Integer winTimes3v3Relive) {
        this.winTimes3v3Relive = winTimes3v3Relive;
    }

    @Basic()
    @Column(name = "prohibit_time")
    public Date getProhibitTime() {
        return prohibitTime;
    }

    public void setProhibitTime(Date prohibitTime) {
        this.prohibitTime = prohibitTime;
    }

    @Basic()
    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Basic()
    @Column(name = "task_update_time")
    public Date getTaskUpdateTime() {
        return taskUpdateTime;
    }

    public void setTaskUpdateTime(Date taskUpdateTime) {
        this.taskUpdateTime = taskUpdateTime;
    }

    @Basic()
    @Column(name = "bsTime")
    public Date getBsTime() {
        return bsTime;
    }

    public void setBsTime(Date bsTime) {
        this.bsTime = bsTime;
    }

    @Basic()
    @Column(name = "beTime")
    public Date getBeTime() {
        return beTime;
    }

    public void setBeTime(Date beTime) {
        this.beTime = beTime;
    }

    @Basic()
    @Column(name = "udid", length = 100)
    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    @Basic()
    @Column(name = "token", length = 100)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic()
    @Column(name = "amount", precision = 10)
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Basic()
    @Column(name = "mac", length = 100)
    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Basic()
    @Column(name = "createTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic()
    @Column(name = "accumulateDiamond", precision = 10)
    public Integer getAccumulateDiamond() {
        if (null == accumulateDiamond) return 0;
        return accumulateDiamond;
    }

    public void setAccumulateDiamond(Integer accumulateDiamond) {
        this.accumulateDiamond = accumulateDiamond;
    }

    @Basic()
    @Column(name = "honorLevel", precision = 10)
    public int getHonorLevel() {
        return honorLevel;
    }

    public void setHonorLevel(int honorLevel) {
        this.honorLevel = honorLevel;
    }

    @Basic()
    @Column(name = "lottery_count", precision = 10)
    public Integer getLotteryCount() {
        return lotteryCount;
    }

    public void setLotteryCount(Integer lotteryCount) {
        this.lotteryCount = lotteryCount;
    }

    @Basic()
    @Column(name = "wb_user_id", length = 255)
    public String getWbUserId() {
        return wbUserId;
    }

    public void setWbUserId(String wbUserId) {
        this.wbUserId = wbUserId;
    }

    @Basic()
    @Column(name = "invite_code", length = 50)
    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    @Basic()
    @Column(name = "bind_invite_code", length = 50)
    public String getBindInviteCode() {
        if (null == bindInviteCode) {
            return "";
        } else {
            return bindInviteCode;
        }
    }

    public void setBindInviteCode(String bindInviteCode) {
        this.bindInviteCode = bindInviteCode;
    }

    @Basic()
    @Column(name = "invite_grade", precision = 3)
    public int getInviteGrade() {
        return inviteGrade;
    }

    public void setInviteGrade(int inviteGrade) {
        this.inviteGrade = inviteGrade;
    }

    @Basic()
    @Column(name = "channel_info", length = 255)
    public String getChannelInfo() {
        if (null == channelInfo) {
            return "";
        }
        return channelInfo;
    }

    public void setChannelInfo(String channelInfo) {
        this.channelInfo = channelInfo;
    }

    @Basic()
    @Column(name = "wb_user_icon", length = 1000)
    public String getWbUserIcon() {
        return wbUserIcon;
    }

    public void setWbUserIcon(String wbUserIcon) {
        this.wbUserIcon = wbUserIcon;
    }

    @Basic()
    @Column(name = "zs_level", precision = 3)
    public int getZsLevel() {
        return zsLevel;
    }

    public void setZsLevel(int zsLevel) {
        this.zsLevel = zsLevel;
    }

    @Basic()
    @Column(name = "zs_old_level", length = 100)
    public String getZsOldLevel() {
        if (null == zsOldLevel) {
            return "";
        } else {
            return zsOldLevel;
        }
    }

    public void setZsOldLevel(String zsOldLevel) {
        this.zsOldLevel = zsOldLevel;
    }

    @Basic()
    @Column(name = "injury_free", precision = 10)
    public int getInjuryFree() {
        return injuryFree;
    }

    public void setInjuryFree(int injuryFree) {
        this.injuryFree = injuryFree;
    }

    @Basic()
    @Column(name = "wreck_defense", precision = 10)
    public int getWreckDefense() {
        return wreckDefense;
    }

    public void setWreckDefense(int wreckDefense) {
        this.wreckDefense = wreckDefense;
    }

    @Basic()
    @Column(name = "reduce_crit", precision = 10)
    public int getReduceCrit() {
        return reduceCrit;
    }

    public void setReduceCrit(int reduceCrit) {
        this.reduceCrit = reduceCrit;
    }

    @Basic()
    @Column(name = "reduce_bury", precision = 10)
    public int getReduceBury() {
        return reduceBury;
    }

    public void setReduceBury(int reduceBury) {
        this.reduceBury = reduceBury;
    }

    @Basic()
    @Column(name = "p_force", precision = 10)
    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    @Basic()
    @Column(name = "armor", precision = 10)
    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    @Basic()
    @Column(name = "agility", precision = 10)
    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    @Basic()
    @Column(name = "physique", precision = 10)
    public int getPhysique() {
        return physique;
    }

    public void setPhysique(int physique) {
        this.physique = physique;
    }

    @Basic()
    @Column(name = "luck", precision = 10)
    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    @Basic()
    @Column(name = "steps", precision = 2)
    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
    
    @Basic()
    @Column(name = "refresh_day", precision = 3)
	public int getRefreshDay() {
		return refreshDay;
	}

	public void setRefreshDay(int refreshDay) {
		this.refreshDay = refreshDay;
	}
	/**
     * 总胜利数
     * @return
     */
    @Transient
    public int getWinNum(){
    	return getWinTimes1v1Athletics() + getWinTimes1v1Champion() + getWinTimes1v1Relive() + getWinTimes2v2Athletics()
		+ getWinTimes2v2Champion() + getWinTimes2v2Relive() + getWinTimes3v3Athletics() + getWinTimes3v3Champion()
		+ getWinTimes3v3Relive();
    }
    /**
     * 总游戏次数
     * @return
     */
    @Transient
    public int getPlayNum(){
    	return getPlayTimes1v1Athletics() + getPlayTimes1v1Champion() + getPlayTimes1v1Relive() + getPlayTimes2v2Athletics()
		+ getPlayTimes2v2Champion() + getPlayTimes2v2Relive() + getPlayTimes3v3Athletics() + getPlayTimes3v3Champion()
		+ getPlayTimes3v3Relive();
    }
}