package com.wyd.empire.world.server.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.server.service.base.IOperationConfigService;

public class VersionService {
	IOperationConfigService versionService;

	public VersionService(IOperationConfigService versionService) {
		this.versionService = versionService;
	}

	public String getNotice() {
		return versionService.getConfig().getNotice();
	}

	public void setUrl(String webUrl, String areaIds) {
		OperationConfig version = versionService.getConfig();
		version.setWebUrl(webUrl);
		versionService.update(version);
		versionService.updataCfgURL(webUrl, areaIds);
	}

	/**
	 * 根据areasIds同步配置
	 * 
	 * @param config
	 *            配置类
	 * @param areasIds
	 *            分区ID
	 */
	public void updateCfgByAreasIds(OperationConfig config, String areasIds) {
		versionService.updateCfgByAreasIds(config, areasIds);
	}

	public void setNotice(String notice) {
		OperationConfig version = versionService.getConfig();
		version.setNotice(notice);
		versionService.update(version);
	}

	public String getAbout() {
		return versionService.getConfig().getAbout();
	}

	public void setAbout(String about) {
		OperationConfig version = versionService.getConfig();
		version.setAbout(about);
		versionService.update(version);
	}

	public String getHelp() {
		return versionService.getConfig().getHelp();
	}

	public void setHelp(String help) {
		OperationConfig version = versionService.getConfig();
		version.setHelp(help);
		versionService.update(version);
	}

	/**
	 * 获取正在使用的游戏版本信息
	 * 
	 * @return
	 */
	public OperationConfig getVersion() {
		return versionService.getConfig();
	}

	/**
	 * 配置表特殊标示
	 */
	public Map<String, Integer> getSpecialMark() {
		return versionService.getSpecialMark();
	}

	/**
	 * 根据key获得特殊标示
	 * 
	 * @param str
	 * @return
	 */
	public Integer getSpecialMarkByKey(String str) {
		return versionService.getSpecialMark().get(str);
	}

	/**
	 * 获取服务器操作对象
	 * 
	 * @return
	 */
	public IOperationConfigService getService() {
		return versionService;
	}

	public int getDefaultItemHeadId(int sex) {
		if (0 == sex) {
			return versionService.getConfig().getBoyHeadId();
		} else {
			return versionService.getConfig().getGirlHeadId();
		}
	}

	public int getDefaultItemFaceId(int sex) {
		if (0 == sex) {
			return versionService.getConfig().getBoyFaceId();
		} else {
			return versionService.getConfig().getGirlFaceId();
		}
	}

	public int getDefaultItemBodyId(int sex) {
		if (0 == sex) {
			return versionService.getConfig().getBoyBodyId();
		} else {
			return versionService.getConfig().getGirlBodyId();
		}
	}

	public int getDefaultItemWeaponId() {
		return versionService.getConfig().getSuitWeaponId();
	}

	/**
	 * 奖励加成
	 * 
	 * @param basicData
	 * @param type
	 *            0经验，1金币，2勋章
	 * @return
	 */
	public int getAddition(int basicData, int type) {
		int additionData = basicData;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (null != versionService.getConfig().getAddition()) {
			String[] as = versionService.getConfig().getAddition().split(",");
			String[] asTime = versionService.getConfig().getAdditionTime().split("\\|");
			switch (type) {
				case 0 :
					if (hour >= Integer.parseInt(asTime[0].split(",")[0]) && hour < Integer.parseInt(asTime[0].split(",")[1])) {
						additionData = (int) Math.ceil(additionData * Float.parseFloat(as[0].split("=")[1]));
					}
					break;
				case 1 :
					if (hour >= Integer.parseInt(asTime[1].split(",")[0]) && hour < Integer.parseInt(asTime[1].split(",")[1])) {
						additionData = (int) Math.ceil(additionData * Float.parseFloat(as[1].split("=")[1]));
					}
					break;
				case 2 :
					if (hour >= Integer.parseInt(asTime[2].split(",")[0]) && hour < Integer.parseInt(asTime[2].split(",")[1])) {
						additionData = (int) Math.ceil(additionData * Float.parseFloat(as[2].split("=")[1]));
					}
					break;
			}
		}
		return additionData;
	}

	public int getWorldChatExp() {
		if (versionService.getSpecialMark().get("worldChatExp") == null) {
			return 5;
		} else {
			return versionService.getSpecialMark().get("worldChatExp");
		}
	}

	public int getColorChatExp() {
		if (versionService.getSpecialMark().get("colorChatExp") == null) {
			return 10;
		} else {
			return versionService.getSpecialMark().get("colorChatExp");
		}
	}

	/**
	 * 获得结婚时间
	 * 
	 * @param id
	 * @return
	 */
	public String getWedTimeById(int id) {
		return versionService.getWedTimeById(id);
	}

	/**
	 * 获得结婚时间Map
	 **/
	public Map<Integer, String> getWedMap() {
		return versionService.getWedMap();
	}

	/**
	 * 获得每日首充奖励次数（根据充值额度）
	 **/
	public Map<Integer, Integer> getRechargeIntervalMap() {
		return versionService.getRechargeIntervalMap();
	}

	/**
	 * 获得结婚相关配置
	 * 
	 * @param key
	 * @return
	 */
	public Integer getWedConfigByKey(String key) {
		return versionService.getWedConfigByKey(key);
	}

	/**
	 * 根据战斗场次获得相应比例
	 * 
	 * @param battleNum
	 * @return
	 */
	public int getExpRateByBattleNum(int battleNum) {
		return versionService.getExpRateByBattleNum(battleNum);
	}

	/**
	 * 判断是否开启充值暴击
	 * 
	 * @return
	 */
	public boolean isOpenRechargeCritFlag() {
		int flag = 0;
		if (versionService.getSpecialMark().containsKey("rechargeCritFlag")) {
			flag = versionService.getSpecialMark().get("rechargeCritFlag");
		}
		return flag == 1;
	}

	/**
	 * 玩家最大可加好友数量
	 * 
	 * @return
	 */
	public int getMaxFriendCount() {
		int count = 500;
		if (versionService.getSpecialMark().containsKey("maxfriendcount")) {
			count = versionService.getSpecialMark().get("maxfriendcount");
		}
		return count;
	}

	/**
	 * 是否开启vip上线公告
	 * 
	 * @return
	 */
	public boolean isOpenWellcomVIPBulletin() {
		int flag = 0;
		if (versionService.getSpecialMark().containsKey("vipbulletin")) {
			flag = versionService.getSpecialMark().get("vipbulletin");
		}
		return flag == 1;
	}

	/**
	 * 开启武器被动技能1所需强化等级
	 * 
	 * @return 默认值6
	 */
	public int getStrongeLevel1() {
		if (versionService.getSpecialMark().get("strongeLevel1") == null) {
			return 6;
		} else {
			return versionService.getSpecialMark().get("strongeLevel1");
		}
	}

	/**
	 * 开启武器被动技能2所需强化等级
	 * 
	 * @return 默认值10
	 */
	public int getStrongeLevel2() {
		if (versionService.getSpecialMark().get("strongeLevel2") == null) {
			return 10;
		} else {
			return versionService.getSpecialMark().get("strongeLevel2");
		}
	}

	/**
	 * 最高可强化等级
	 * 
	 * @return 默认值12
	 */
	public int getStrongeTopLevel() {
		if (versionService.getSpecialMark().get("strongeTopLevel") == null) {
			return 12;
		} else {
			return versionService.getSpecialMark().get("strongeTopLevel");
		}
	}

	/**
	 * 获取强化标识
	 * 
	 * @return
	 */
	public int getStrenthFlag() {
		Integer strenthFlag = versionService.getConfig().getStrenthFlag();
		if (null == strenthFlag || 0 == strenthFlag.intValue()) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * 获取弹王挑战赛积分金币转换率
	 * 
	 * @return
	 */
	public int getInteToGold() {
		int goldPrice = 100;
		if (versionService.getSpecialMark().containsKey("inteToGold")) {
			goldPrice = versionService.getSpecialMark().get("inteToGold");
		}
		return goldPrice;
	}

	public int getConfig(String key, int defaul) {
		Integer val = getSpecialMarkByKey(key);
		if (val == null) {
			return defaul;
		}
		return val;
	}

	/**
	 * 获取补签价格列表
	 * 
	 * @return
	 */
	public List<Integer> getSupplPriceList() {
		return versionService.getSupplPriceList();
	}
}
