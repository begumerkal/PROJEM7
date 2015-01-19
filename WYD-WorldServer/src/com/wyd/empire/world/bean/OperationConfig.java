package com.wyd.empire.world.bean;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * The persistent class for the tab_operationconfig database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_operationconfig")
public class OperationConfig implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String about; // 游戏“关于”内容
	private String areaId; // 配置所属分区
	private Integer boyBodyId; // 男新角色默认衣服
	private Integer boyFaceId; // 男新角色默认脸型
	private Integer boyHeadId; // 男新角色默认头型
								// private Integer curUsed; // 是否启用(已废弃)
	private Integer girlBodyId; // 女新角色默认衣服
	private Integer girlFaceId; // 女新角色默认脸型
	private Integer girlHeadId; // 女新角色默认头型
	private String help; // 游戏帮助信息
	private String notice; // 游戏公告
							// private Integer pushEndTime; // 推送关闭时间（已废弃）
							// private Integer pushStartTime; // 推送开始时间（已废弃）
	private Integer suitWeaponId; // 新角色默认武器id
									// private String version; // 配置版本号（已废弃）
	private Integer checkVersion; // 错误提示语版本号（用于客户端检查是否需要更新错误提示语）
	private String prices; // 副本砸蛋价格
							// private Integer firstChargeReward; //
							// 首冲奖励（0不奖励，1奖励）(已废弃)
							// private Integer moreChargeReward; //
							// 充值奖励（0不奖励，1奖励）(已废弃)
							// private Integer discount; // 促销折扣（10000倍）(已废弃)
	private Integer strengthenLevel; // 强化发送喇叭的等级
										// private String marryDetail; // 结婚说明
										// (已废弃)
	private Integer dhPrice; // 解除订婚要的钻石
	private Integer jhPrice; // 离婚要的钻石
	private String marryDiamond; // 婚礼模式
									// private Integer newYearMark; // 特殊状态开关
									// (已废弃)
	private Integer reDramond; // 兑换刷新所需钻石数
	private Integer reBadge; // 兑换刷新所需徽章数
	private String specialMark; // 特殊标示
	private String addition; // 经验、金币、勋章加成
								// private Integer firstChargeRewardType;//
								// 首冲奖励（0非统一奖励，1统一奖励）(已废弃)
								// private String chargeReward; // 首冲统一奖励 (已废弃)
								// private String chargeRewardRemark; //
								// 首冲统一奖励描述 (已废弃)
	private String rankreward; // 排位赛奖励
	private String webUrl; // 活动广场url
	private String wbPicUrl; // 微博图片链接
	private String wbAppKey; // 微博绑定应用id
	private String webAppSecret; // 微博密钥
	private String webAppRedirectUri; // 微博主页地址
	private String wbUid; // 微博绑定帐号id
	private Integer recallDay; // 玩家召回天数
								// private String etlType; // 服务器区类型 (已废弃)
								// private int dayTaskNum; // 日常任务激活数量(已废弃)
	private String rebirthRemark; // 转生说明
	private int roomRefreshTime; // 房间状态刷新时间>=10秒
	private int rerp; // 机器人进入房间的概率（0到100）
	private int becomeRobot; // 玩家掉线后是否会变成机器人（0否，其他是）
	private int rebirthDiamond; // 完美转生消耗钻石基数
	private int bulletinStarsLevel; // 全服公告所需星级
	private String fundAllocation; // 基金配置字符串格式为：10=50,20=100,30=150|10=50,20=100,30=150|10=50,20=100,30=150
	private int shopDiscount; // 商城折扣(放大100倍)
	private String shopNoDiscountId; // 商城不进行全场打折的商品ID
	private String additionTime; // 加成时间：格式为：0,0|0,0|0,0
	private int noviceType; // 新手教程类型 0和怪打，1和玩家打 ,2跟怪和玩家打
							// private String noviceRemark; // 新手说明
	private String unitPrice; // 外放包底价
	private String firstRankReward; // 排位赛第一名奖励
	private int guideLevel; // 攻击自动制导最高等级
	private int blastLevel; // 爆破地图最低等级
	private int rightToMarryLevel; // 结婚送钻石最低等级
	private String areaName; // 分区名
	private String punchAmount; // 拆卸的钻石数
	private String punchRate; // 拆卸的成功概率
	private int newPlayerLevel; // 新手对战配对策略等级
	private String punchDisappear; // 拆卸的石头消失的概率
									// private int guideLevel2; //
									// 战斗技能引导最高等级(已废弃)
	private String worldBoss; // 世界BOSS相关配置(JSON)
	private String challengeReward; // 弹王挑战赛的奖励
	private String drawDetail; // 抽奖说明
	private String challengeJson; // 挑战赛开始结束时间配置
	private int crossLevel; // 默认跨服对战等级，0表示关闭跨服对战
	private String moreGame; // 交叉推荐开关-1为不显示，显示时字段值作为URL
								// private String bubbleTips; // 小金人冒泡提示多个|分割
								// (已废弃)
	private String showTipsTimePeriod; // 小金人冒泡提示时间段11:27:41-11:27:55为-1时客户端不做展示
	private int giveDiamondRatio = 1; // 夫妻转钻的倍率
	private String levelParabolaRange; // 2.1 新加GM工具控制等级抛物线范围
	private Integer strenthFlag; // 强化标识
	private String wedTime; // 可以举办婚礼的时间格式
							// 1#11:11:11-22:22:22|2#11:11:11-22:22:22
	private String wedconfig; // 结婚礼堂相关配置
	private String expRate; // 战斗场次的经验比例
	// 2.2新增
	private String robotSkill; // 机器人技能使用方案
	private String supplPrice; // 签到补签价格
	private String rechargeInterval; // 根据充值金额间隔配置每日首充奖励每次 格式：1#100|2#200|3#300

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
	@Column(name = "about", length = 500)
	public String getAbout() {
		return this.about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	@Basic()
	@Column(name = "areaId", length = 10)
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Basic()
	@Column(name = "boy_body_id", precision = 10)
	public Integer getBoyBodyId() {
		return this.boyBodyId;
	}

	public void setBoyBodyId(Integer boyBodyId) {
		this.boyBodyId = boyBodyId;
	}

	@Basic()
	@Column(name = "boy_face_id", precision = 10)
	public Integer getBoyFaceId() {
		return this.boyFaceId;
	}

	public void setBoyFaceId(Integer boyFaceId) {
		this.boyFaceId = boyFaceId;
	}

	@Basic()
	@Column(name = "boy_head_id", precision = 10)
	public Integer getBoyHeadId() {
		return this.boyHeadId;
	}

	public void setBoyHeadId(Integer boyHeadId) {
		this.boyHeadId = boyHeadId;
	}

	// @Basic()
	// @Column(name = "curUsed", precision = 10)
	// public Integer getCurUsed() {
	// return this.curUsed;
	// }
	//
	// public void setCurUsed(Integer curUsed) {
	// this.curUsed = curUsed;
	// }
	@Basic()
	@Column(name = "fundInfo", length = 1000)
	public String getFundAllocation() {
		return this.fundAllocation;
	}

	public void setFundAllocation(String fundAllocation) {
		this.fundAllocation = fundAllocation;
	}

	@Basic()
	@Column(name = "girl_body_id", precision = 10)
	public Integer getGirlBodyId() {
		return this.girlBodyId;
	}

	public void setGirlBodyId(Integer girlBodyId) {
		this.girlBodyId = girlBodyId;
	}

	@Basic()
	@Column(name = "girl_face_id", precision = 10)
	public Integer getGirlFaceId() {
		return this.girlFaceId;
	}

	public void setGirlFaceId(Integer girlFaceId) {
		this.girlFaceId = girlFaceId;
	}

	@Basic()
	@Column(name = "girl_head_id", precision = 10)
	public Integer getGirlHeadId() {
		return this.girlHeadId;
	}

	public void setGirlHeadId(Integer girlHeadId) {
		this.girlHeadId = girlHeadId;
	}

	@Basic()
	@Column(name = "help", length = 1000)
	public String getHelp() {
		return this.help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	@Basic()
	@Column(name = "notice", length = 1000)
	public String getNotice() {
		return this.notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	// @Basic()
	// @Column(name = "push_end_time", precision = 10)
	// public Integer getPushEndTime() {
	// return this.pushEndTime;
	// }
	//
	// public void setPushEndTime(Integer pushEndTime) {
	// this.pushEndTime = pushEndTime;
	// }
	//
	// @Basic()
	// @Column(name = "push_start_time", precision = 10)
	// public Integer getPushStartTime() {
	// return this.pushStartTime;
	// }
	//
	// public void setPushStartTime(Integer pushStartTime) {
	// this.pushStartTime = pushStartTime;
	// }
	@Basic()
	@Column(name = "suit_weapon_id", precision = 10)
	public Integer getSuitWeaponId() {
		return this.suitWeaponId;
	}

	public void setSuitWeaponId(Integer suitWeaponId) {
		this.suitWeaponId = suitWeaponId;
	}

	// @Basic()
	// @Column(name = "version", length = 80)
	// public String getVersion() {
	// return this.version;
	// }
	//
	// public void setVersion(String version) {
	// this.version = version;
	// }
	@Basic()
	@Column(name = "check_version", precision = 10)
	public Integer getCheckVersion() {
		return checkVersion;
	}

	public void setCheckVersion(Integer checkVersion) {
		this.checkVersion = checkVersion;
	}

	@Basic()
	@Column(name = "prices")
	public String getPrices() {
		return prices;
	}

	public void setPrices(String prices) {
		this.prices = prices;
	}

	// @Basic()
	// @Column(name = "first_charge_reward", precision = 2)
	// public Integer getFirstChargeReward() {
	// return firstChargeReward;
	// }
	//
	// public void setFirstChargeReward(Integer firstChargeReward) {
	// this.firstChargeReward = firstChargeReward;
	// }
	//
	// @Basic()
	// @Column(name = "more_charge_eward", precision = 2)
	// public Integer getMoreChargeReward() {
	// return moreChargeReward;
	// }
	//
	// public void setMoreChargeReward(Integer moreChargeReward) {
	// this.moreChargeReward = moreChargeReward;
	// }
	//
	// @Basic()
	// @Column(name = "discount", precision = 10)
	// public Integer getDiscount() {
	// return discount;
	// }
	//
	// public void setDiscount(Integer discount) {
	// this.discount = discount;
	// }
	@Basic()
	@Column(name = "strengthenLevel", precision = 10)
	public Integer getStrengthenLevel() {
		return strengthenLevel;
	}

	public void setStrengthenLevel(Integer strengthenLevel) {
		this.strengthenLevel = strengthenLevel;
	}

	// @Basic()
	// @Column(name = "marryDetail", length = 1000)
	// public String getMarryDetail() {
	// return marryDetail;
	// }
	//
	// public void setMarryDetail(String marryDetail) {
	// this.marryDetail = marryDetail;
	// }
	@Basic()
	@Column(name = "dhPrice", precision = 10)
	public Integer getDhPrice() {
		return dhPrice;
	}

	public void setDhPrice(Integer dhPrice) {
		this.dhPrice = dhPrice;
	}

	@Basic()
	@Column(name = "jhPrice", precision = 10)
	public Integer getJhPrice() {
		return jhPrice;
	}

	public void setJhPrice(Integer jhPrice) {
		this.jhPrice = jhPrice;
	}

	@Basic()
	@Column(name = "marryDiamond", length = 20)
	public String getMarryDiamond() {
		return marryDiamond;
	}

	public void setMarryDiamond(String marryDiamond) {
		this.marryDiamond = marryDiamond;
	}

	// @Basic()
	// @Column(name = "newYearMark", precision = 10)
	// public Integer getNewYearMark() {
	// return newYearMark;
	// }
	//
	// public void setNewYearMark(Integer newYearMark) {
	// this.newYearMark = newYearMark;
	// }
	@Basic()
	@Column(name = "reDramond", precision = 10)
	public Integer getReDramond() {
		return reDramond;
	}

	public void setReDramond(Integer reDramond) {
		this.reDramond = reDramond;
	}

	@Basic()
	@Column(name = "reBadge", precision = 10)
	public Integer getReBadge() {
		return reBadge;
	}

	public void setReBadge(Integer reBadge) {
		this.reBadge = reBadge;
	}

	@Basic()
	@Column(name = "specialMark", length = 255)
	public String getSpecialMark() {
		return specialMark;
	}

	public void setSpecialMark(String specialMark) {
		this.specialMark = specialMark;
	}

	@Basic()
	@Column(name = "addition", length = 1000)
	public String getAddition() {
		return addition;
	}

	public void setAddition(String addition) {
		this.addition = addition;
	}

	// @Basic()
	// @Column(name = "first_chargereward_type", precision = 10)
	// public Integer getFirstChargeRewardType() {
	// return firstChargeRewardType;
	// }
	//
	// public void setFirstChargeRewardType(Integer firstChargeRewardType) {
	// this.firstChargeRewardType = firstChargeRewardType;
	// }
	//
	// @Basic()
	// @Column(name = "chargereward", length = 1000)
	// public String getChargeReward() {
	// return chargeReward;
	// }
	//
	// public void setChargeReward(String chargeReward) {
	// this.chargeReward = chargeReward;
	// }
	//
	// @Basic()
	// @Column(name = "chargereward_remark", length = 1000)
	// public String getChargeRewardRemark() {
	// return chargeRewardRemark;
	// }
	//
	// public void setChargeRewardRemark(String chargeRewardRemark) {
	// this.chargeRewardRemark = chargeRewardRemark;
	// }
	@Basic()
	@Column(name = "rankreward", length = 255)
	public String getRankreward() {
		return rankreward;
	}

	public void setRankreward(String rankreward) {
		this.rankreward = rankreward;
	}

	@Basic()
	@Column(name = "webUrl", length = 255)
	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	@Basic()
	@Column(name = "wb_picurl", length = 255)
	public String getWbPicUrl() {
		return wbPicUrl;
	}

	public void setWbPicUrl(String wbPicUrl) {
		this.wbPicUrl = wbPicUrl;
	}

	@Basic()
	@Column(name = "wb_app_key", length = 30)
	public String getWbAppKey() {
		return wbAppKey;
	}

	public void setWbAppKey(String wbAppKey) {
		this.wbAppKey = wbAppKey;
	}

	@Basic()
	@Column(name = "web_app_secret", length = 100)
	public String getWebAppSecret() {
		return webAppSecret;
	}

	public void setWebAppSecret(String webAppSecret) {
		this.webAppSecret = webAppSecret;
	}

	@Basic()
	@Column(name = "web_app_redirect_uri", length = 100)
	public String getWebAppRedirectUri() {
		return webAppRedirectUri;
	}

	public void setWebAppRedirectUri(String webAppRedirectUri) {
		this.webAppRedirectUri = webAppRedirectUri;
	}

	@Basic()
	@Column(name = "wb_uid", length = 30)
	public String getWbUid() {
		return wbUid;
	}

	public void setWbUid(String wbUid) {
		this.wbUid = wbUid;
	}

	@Basic()
	@Column(name = "recall_day")
	public Integer getRecallDay() {
		return recallDay;
	}

	public void setRecallDay(Integer recallDay) {
		this.recallDay = recallDay;
	}

	// @Basic()
	// @Column(name = "etl_type")
	// public String getEtlType() {
	// return etlType;
	// }
	//
	// public void setEtlType(String etlType) {
	// this.etlType = etlType;
	// }
	//
	// @Basic()
	// @Column(name = "day_task_num", precision = 10)
	// public int getDayTaskNum() {
	// return dayTaskNum;
	// }
	//
	// public void setDayTaskNum(int dayTaskNum) {
	// this.dayTaskNum = dayTaskNum;
	// }
	@Basic()
	@Column(name = "rebirth_remark", length = 1000)
	public String getRebirthRemark() {
		return rebirthRemark;
	}

	public void setRebirthRemark(String rebirthRemark) {
		this.rebirthRemark = rebirthRemark;
	}

	@Basic()
	@Column(name = "room_refresh_time", precision = 10)
	public int getRoomRefreshTime() {
		return roomRefreshTime;
	}

	public void setRoomRefreshTime(int roomRefreshTime) {
		this.roomRefreshTime = roomRefreshTime;
	}

	@Basic()
	@Column(name = "rerp", precision = 3)
	public int getRerp() {
		return rerp;
	}

	public void setRerp(int rerp) {
		this.rerp = rerp;
	}

	@Basic()
	@Column(name = "become_robot", precision = 1)
	public int getBecomeRobot() {
		return becomeRobot;
	}

	public void setBecomeRobot(int becomeRobot) {
		this.becomeRobot = becomeRobot;
	}

	@Basic()
	@Column(name = "rebirth_diamond", precision = 10)
	public int getRebirthDiamond() {
		return rebirthDiamond;
	}

	public void setRebirthDiamond(int rebirthDiamond) {
		this.rebirthDiamond = rebirthDiamond;
	}

	@Basic()
	@Column(name = "bulletin_stars_level", precision = 3)
	public int getBulletinStarsLevel() {
		return bulletinStarsLevel;
	}

	public void setBulletinStarsLevel(int bulletinStarsLevel) {
		this.bulletinStarsLevel = bulletinStarsLevel;
	}

	@Basic()
	@Column(name = "shop_discount")
	public int getShopDiscount() {
		return shopDiscount;
	}

	public void setShopDiscount(int shopDiscount) {
		this.shopDiscount = shopDiscount;
	}

	@Basic()
	@Column(name = "shop_no_discount_id")
	public String getShopNoDiscountId() {
		return shopNoDiscountId;
	}

	public void setShopNoDiscountId(String shopNoDiscountId) {
		this.shopNoDiscountId = shopNoDiscountId;
	}

	@Basic()
	@Column(name = "addition_time")
	public String getAdditionTime() {
		return additionTime;
	}

	public void setAdditionTime(String additionTime) {
		this.additionTime = additionTime;
	}

	@Basic()
	@Column(name = "novice_type", precision = 2)
	public int getNoviceType() {
		return noviceType;
	}

	public void setNoviceType(int noviceType) {
		this.noviceType = noviceType;
	}

	@Basic()
	@Column(name = "unitPrice")
	public String getUnitPrice() {
		if (null != unitPrice) {
			return unitPrice;
		} else {
			return "";
		}
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	// @Basic()
	// @Column(name = "novice_remark", length = 1000)
	// public String getNoviceRemark() {
	// if (null != noviceRemark) {
	// return noviceRemark;
	// } else {
	// return "";
	// }
	// }
	//
	// public void setNoviceRemark(String noviceRemark) {
	// this.noviceRemark = noviceRemark;
	// }
	@Basic()
	@Column(name = "first_rank_reward")
	public String getFirstRankReward() {
		return firstRankReward;
	}

	public void setFirstRankReward(String firstRankReward) {
		this.firstRankReward = firstRankReward;
	}

	@Basic()
	@Column(name = "areaName")
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Basic()
	@Column(name = "guide_level", precision = 2)
	public int getGuideLevel() {
		return guideLevel;
	}

	public void setGuideLevel(int guideLevel) {
		this.guideLevel = guideLevel;
	}

	// @Basic()
	// @Column(name = "guide_level2", precision = 2)
	// public int getGuideLevel2() {
	// return guideLevel2;
	// }
	//
	// public void setGuideLevel2(int guideLevel2) {
	// this.guideLevel2 = guideLevel2;
	// }
	@Basic()
	@Column(name = "blast_level", precision = 2)
	public int getBlastLevel() {
		return blastLevel;
	}

	public void setBlastLevel(int blastLevel) {
		this.blastLevel = blastLevel;
	}

	@Basic()
	@Column(name = "right_to_marry_level", precision = 2)
	public int getRightToMarryLevel() {
		return rightToMarryLevel;
	}

	public void setRightToMarryLevel(int rightToMarryLevel) {
		this.rightToMarryLevel = rightToMarryLevel;
	}

	@Basic()
	@Column(name = "punch_amount")
	public String getPunchAmount() {
		return punchAmount;
	}

	public void setPunchAmount(String punchAmount) {
		this.punchAmount = punchAmount;
	}

	@Basic()
	@Column(name = "punch_rate")
	public String getPunchRate() {
		return punchRate;
	}

	public void setPunchRate(String punchRate) {
		this.punchRate = punchRate;
	}

	@Basic()
	@Column(name = "new_player_level", precision = 2)
	public int getNewPlayerLevel() {
		return newPlayerLevel;
	}

	public void setNewPlayerLevel(int newPlayerLevel) {
		this.newPlayerLevel = newPlayerLevel;
	}

	@Basic()
	@Column(name = "punch_disappear")
	public String getPunchDisappear() {
		return punchDisappear;
	}

	public void setPunchDisappear(String punchDisappear) {
		this.punchDisappear = punchDisappear;
	}

	@Basic()
	@Column(name = "world_boss", precision = 10)
	public String getWorldBoss() {
		return worldBoss;
	}

	public void setWorldBoss(String worldBoss) {
		this.worldBoss = worldBoss;
	}

	@Basic()
	@Column(name = "challenge_reward")
	public String getChallengeReward() {
		return challengeReward;
	}

	public void setChallengeReward(String challengeReward) {
		this.challengeReward = challengeReward;
	}

	@Basic()
	@Column(name = "draw_detail")
	public String getDrawDetail() {
		return drawDetail;
	}

	public void setDrawDetail(String drawDetail) {
		this.drawDetail = drawDetail;
	}

	@Basic()
	@Column(name = "challenge_json")
	public String getChallengeJson() {
		return challengeJson;
	}

	public void setChallengeJson(String challengeJson) {
		this.challengeJson = challengeJson;
	}

	@Basic()
	@Column(name = "cross_level", precision = 3)
	public int getCrossLevel() {
		return crossLevel;
	}

	public void setCrossLevel(int crossLevel) {
		this.crossLevel = crossLevel;
	}

	@Basic()
	@Column(name = "moreGame", length = 100)
	public String getMoreGame() {
		return moreGame;
	}

	public void setMoreGame(String moreGame) {
		this.moreGame = moreGame;
	}

	// @Basic()
	// @Column(name = "bubble_tips")
	// public String getBubbleTips() {
	// return bubbleTips;
	// }
	//
	// public void setBubbleTips(String bubbleTips) {
	// this.bubbleTips = bubbleTips;
	// }
	@Basic()
	@Column(name = "show_tips_time", length = 200)
	public String getShowTipsTimePeriod() {
		return showTipsTimePeriod;
	}

	public void setShowTipsTimePeriod(String showTipsTimePeriod) {
		this.showTipsTimePeriod = showTipsTimePeriod;
	}

	@Basic()
	@Column(name = "give_diamond_ratio", length = 200)
	public int getGiveDiamondRatio() {
		return giveDiamondRatio;
	}

	public void setGiveDiamondRatio(int giveDiamondRatio) {
		this.giveDiamondRatio = giveDiamondRatio;
	}

	@Basic()
	@Column(name = "level_parabola_range", length = 100)
	public String getLevelParabolaRange() {
		return levelParabolaRange;
	}

	public void setLevelParabolaRange(String levelParabolaRange) {
		this.levelParabolaRange = levelParabolaRange;
	}

	@Basic()
	@Column(name = "strenth_flag")
	public Integer getStrenthFlag() {
		return strenthFlag;
	}

	public void setStrenthFlag(Integer strenthFlag) {
		this.strenthFlag = strenthFlag;
	}

	@Basic()
	@Column(name = "wedTime")
	public String getWedTime() {
		return wedTime;
	}

	public void setWedTime(String wedTime) {
		this.wedTime = wedTime;
	}

	@Basic()
	@Column(name = "rechargeInterval")
	public String getRechargeInterval() {
		return rechargeInterval;
	}

	public void setRechargeInterval(String rechargeInterval) {
		this.rechargeInterval = rechargeInterval;
	}

	@Basic()
	@Column(name = "wedconfig")
	public String getWedconfig() {
		return wedconfig;
	}

	public void setWedconfig(String wedconfig) {
		this.wedconfig = wedconfig;
	}

	@Basic()
	@Column(name = "expRate")
	public String getExpRate() {
		return expRate;
	}

	public void setExpRate(String expRate) {
		this.expRate = expRate;
	}

	@Basic()
	@Column(name = "robot_skill", length = 100)
	public String getRobotSkill() {
		return robotSkill == null ? "" : robotSkill;
	}

	public void setRobotSkill(String robotSkill) {
		this.robotSkill = robotSkill;
	}

	@Basic()
	@Column(name = "suppl_price", length = 255)
	public String getSupplPrice() {
		return supplPrice;
	}

	public void setSupplPrice(String supplPrice) {
		this.supplPrice = supplPrice;
	}

	/**
	 * 副本砸蛋价格数组
	 * 
	 * @return
	 */
	@Transient
	public int[] priceArray() {
		String[] pricestr = prices.split(",");
		int[] priceArray = new int[pricestr.length];
		for (int i = 0; i < pricestr.length; i++) {
			priceArray[i] = Integer.parseInt(pricestr[i]);
		}
		return priceArray;
	}
}