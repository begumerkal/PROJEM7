package com.wyd.empire.world.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.wyd.empire.world.battle.PracticeVo;
import com.wyd.empire.world.bean.Practice;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.server.service.base.IPracticeService;

/**
 * 修炼服务
 * 
 * @author 陈杰
 *
 */
public class PracticeService {
	/** 数据库修炼列表 */
	private static List<Practice> practiceList = new ArrayList<Practice>();
	private IPracticeService practiceService;
	/** 解析后修炼图数据 */
	private static Map<Integer, PracticeVo> practiceMap = new HashMap<Integer, PracticeVo>();
	/** 修炼属性索引 */
	private static Map<String, Integer> practiceIndexMap = new HashMap<String, Integer>();

	// public IStarSoulService getStarSoulService() {
	// return starSoulService;
	// }

	public PracticeService(IPracticeService practiceService) {
		this.practiceService = practiceService;
		initMapIndex();
	}

	/**
	 * 初始化修炼数据
	 */
	public void initPracticeData() {
		practiceList = practiceService.getAllPractice();
		if (practiceList.size() > 0) {
			for (Practice practice : practiceList) {
				PracticeVo vo = new PracticeVo();
				vo.setMapLeve(practice.getId()); // 星图等级
				vo.setPlayerLeve(practice.getPlayerLeve()); // 限制玩家等级
				vo.setExp(practice.getExp());// 所需经验
				vo.setLowMedalExchangeExp(practice.getLowMedalExchangeExp());// 普通勋章兑换经验值
				vo.setSeniorMedalExchangeExp(practice.getSeniorMedalExchangeExp());// 高级勋章兑换经验值
				vo.setDayConsumptionNumber(practice.getDayConsumptionNumber());// 日消耗数
				String[] values = practice.getPeculiarityValue().split("\\|"); // 多个属性
				String[] bonusAttribute = new String[values.length]; // 修炼点显示的加成属性（服务端传key，客户端显示value）
				int[] bonusValue = new int[values.length]; // 属性加成值
				String[] bonusValueStr = new String[values.length]; // 属性加成值字符串
				int[] bonusIndex = new int[values.length]; // 属性索引
				int index = 0;
				for (String value : values) { // 根据数据动态加载星点属性
					String[] parm = value.split(":");
					bonusAttribute[index] = parm[0]; // 星点显示的加成属性（服务端传key，客户端显示value）
					bonusValueStr[index] = parm[1]; // 属性加成值
					bonusValue[index] = Integer.parseInt(parm[1]); // 属性加成值
					bonusIndex[index] = practiceIndexMap.get(parm[0]);// 属性索引
					index++;
				}
				vo.setBonusAttribute(bonusAttribute);
				vo.setBonusValue(bonusValue);
				vo.setBonusIndex(bonusIndex);
				vo.setBonusValueStr(bonusValueStr);
				practiceMap.put(practice.getId(), vo);
			}
		}
	}

	/**
	 * 初始化属性索引值
	 */
	private void initMapIndex() {
		practiceIndexMap.put(Common.DEFENSE, 1);
		practiceIndexMap.put(Common.ATTACK, 2);
		practiceIndexMap.put(Common.DEFEND, 3);
		practiceIndexMap.put(Common.HP, 4);
		practiceIndexMap.put(Common.INJURYFREE, 5);
		practiceIndexMap.put(Common.WRECK_DEFENSE, 6);
		practiceIndexMap.put(Common.REDUCE_CRIT, 7);
		practiceIndexMap.put(Common.REDUCE_BURY, 8);
		practiceIndexMap.put(Common.FORCE, 9);
		practiceIndexMap.put(Common.ARMOR, 10);
		practiceIndexMap.put(Common.AGILITY, 11);
		practiceIndexMap.put(Common.PHYSIQUE, 12);
		practiceIndexMap.put(Common.LUCK, 13);
	}

	/**
	 * 获取修炼图map
	 * 
	 * @return
	 */
	public static Map<Integer, PracticeVo> getPracticeMap() {
		return practiceMap;
	}

	/**
	 * 获取星点显示的加成属性key
	 * 
	 * @param mapLeve
	 *            修炼图等级
	 * @return
	 */
	public static String[] getBonusAttributeByMapLeve(int mapLeve) {
		PracticeVo vo = practiceMap.get(mapLeve);
		if (vo != null) {
			return vo.getBonusAttribute();
		}
		return new String[]{"force", "armor", "luck", "agility", "physique"};
	}

	/**
	 * 获取修炼属性索引
	 * 
	 * @param mapLeve
	 *            修炼图等级
	 * @return
	 */
	public static int[] getMapIndexByMapLeve(int mapLeve) {
		PracticeVo vo = practiceMap.get(mapLeve);
		if (vo != null) {
			return vo.getBonusIndex();
		}
		return new int[]{1, 2, 3, 4, 5};
	}

	/**
	 * 获取修炼属性个数
	 * 
	 * @param mapLeve
	 *            修炼图等级
	 * @return
	 */
	public static int getAttributeNuberByMapLeve(int mapLeve) {
		PracticeVo vo = practiceMap.get(mapLeve);
		if (vo != null) {
			return vo.getBonusAttribute().length;
		}
		return 5;
	}

	/**
	 * 获取修炼显示的属性加成值
	 * 
	 * @param mapLeve
	 *            修炼图等级
	 * @return
	 */

	public static int[] getBonusValueByMapLeve(int mapLeve) {
		PracticeVo vo = practiceMap.get(mapLeve);
		if (vo != null) {
			return vo.getBonusValue();
		}
		return new int[]{0, 0, 0, 0, 0};
	}

	/**
	 * 获取修炼显示的属性加成值
	 * 
	 * @param mapLeve
	 *            修炼图等级
	 * @param index
	 *            属性点下标
	 * @return
	 */

	public static int getBonusValueByMapLeve(int mapLeve, int index) {
		PracticeVo vo = practiceMap.get(mapLeve);
		if (vo != null) {
			return vo.getBonusValue()[index];
		}
		return 0;
	}

	/**
	 * 获取当前等级勋章兑换经验数
	 * 
	 * @param mapLeve
	 *            修炼图等级
	 * @return int[]{普通勋章兑换经验值,高级勋章兑换经验值}
	 */
	public static int[] getConsumptionValueByMapLeveAndSoulDot(int mapLeve) {
		PracticeVo vo = practiceMap.get(mapLeve);
		if (vo != null) {
			return new int[]{vo.getLowMedalExchangeExp(), vo.getSeniorMedalExchangeExp()};
		}
		return new int[]{0, 0};
	}

	/**
	 * 获取修炼显示的属性加成key和value
	 * 
	 * @param mapLeve
	 *            修炼图等级
	 * @param soulDot
	 *            当前修炼点
	 * @return String[]{加成属性key,加成属性value}
	 */
	public static String[] getBonusValueByMapLeveAndSoulDot(int mapLeve, int soulDot) {
		PracticeVo vo = practiceMap.get(mapLeve);
		if (vo != null) {
			return new String[]{vo.getBonusAttribute()[soulDot], vo.getBonusValueStr()[soulDot]};
		}
		return new String[]{"attack", "0"};
	}

	// /**
	// * 获取星图星点最高等级
	// * @param mapLeve 星图等级
	// * @return
	// */
	// @SuppressWarnings("static-access")
	// public int getSoulDotMaxLeveByMapLeve(int mapLeve){
	// PracticeVo vo = this.practiceMap.get(mapLeve);
	// if (vo != null) {
	// return vo.getBonusValue().length;
	// }
	// return 0;
	// }

	/**
	 * 获取修炼限制等级
	 * 
	 * @param mapLeve
	 *            修炼图等级
	 * @return
	 */
	public static int getPlayerLeveByMapLeve(int mapLeve) {
		PracticeVo vo = practiceMap.get(mapLeve);
		if (vo != null) {
			return vo.getPlayerLeve();
		}
		return 0;
	}

	/**
	 * 获取修炼所需经验
	 * 
	 * @param mapLeve
	 *            修炼图等级
	 * @return
	 */
	public static int getExpByMapLeve(int mapLeve) {
		PracticeVo vo = practiceMap.get(mapLeve);
		if (vo != null) {
			return vo.getExp();
		}
		return 0;
	}

	/**
	 * 根据等级获取激活每日扣除勋章数
	 * 
	 * @param mapLeve
	 *            修炼图等级
	 * @return
	 */
	public static int getDayConsumptionNumberByMapLeve(int mapLeve) {
		PracticeVo vo = practiceMap.get(mapLeve);
		if (vo != null) {
			return vo.getDayConsumptionNumber();
		}
		return 0;
	}

	/**
	 * 根据等级获取普通勋章兑换经验数
	 * 
	 * @param mapLeve
	 *            修炼图等级
	 * @return
	 */
	public static int getLowMedalExchangeExpByMapLeve(int mapLeve) {
		PracticeVo vo = practiceMap.get(mapLeve);
		if (vo != null) {
			return vo.getLowMedalExchangeExp();
		}
		return 0;
	}

	/**
	 * 根据等级获取高级勋章兑换经验数
	 * 
	 * @param mapLeve
	 *            修炼图等级
	 * @return
	 */
	public static int getSeniorMedalExchangeExpByMapLeve(int mapLeve) {
		PracticeVo vo = practiceMap.get(mapLeve);
		if (vo != null) {
			return vo.getSeniorMedalExchangeExp();
		}
		return 0;
	}

	/**
	 * 获取修炼最高等级
	 * 
	 * @return
	 */
	public static int getMapMaxLeve() {
		return practiceMap.size();
	}

	/**
	 * 每日首次登录是重置修炼系统激活状态
	 * 
	 * @param player
	 */
	// public void initPracticeStatus(WorldPlayer player) {
	// // 修炼系统激活状态下每天扣取相应的勋章数
	// if (player.getPlayerInfo().getPracticeStatus()) {
	// // 玩家修炼属性对应的等级
	// int mapLeve = player.getPlayerInfo().getPracticeLeve() == 0 ? 1 :
	// player.getPlayerInfo().getPracticeLeve();
	// // 当前等级激活每日扣除勋章数
	// int consumeMedalNumber = getDayConsumptionNumberByMapLeve(mapLeve);
	// if (player.getMedalNum() < consumeMedalNumber) {
	// // 更新状态
	// player.getPlayerInfo().setPracticeStatus(false);
	// } else {
	// // 消耗勋章一系列操作
	// PlayerItemsFromShop playerItem =
	// ServiceManager.getManager().getPlayerItemsFromShopService().getPlayerItemsFromShopByPlayerIdAndItemId(player.getPlayer(),
	// Common.BADGEID);
	// if (null == playerItem || consumeMedalNumber > playerItem.getPLastNum())
	// {
	// // 更新状态
	// player.getPlayerInfo().setPracticeStatus(false);
	// }
	// playerItem.setPLastNum(playerItem.getPLastNum() - consumeMedalNumber);
	// ServiceManager.getManager().getPlayerItemsFromShopService().update(playerItem);
	// player.setMedalNum(player.getMedalNum() - consumeMedalNumber);
	// // TODO 更新玩家拥有的物品 数据暂未下发,待缓存优化完相关模块人员加上去
	// ServiceManager.getManager().getPlayerItemsFromShopService().saveGetItemRecord(player.getPlayer().getId(),
	// Common.BADGEID, -1, consumeMedalNumber, 34, 1, null);
	// // 更新状态
	// player.getPlayerInfo().setPracticeStatus(true);
	// }
	// player.updatePlayerInfo();
	// player.updateItem();
	// }
	// }
}
