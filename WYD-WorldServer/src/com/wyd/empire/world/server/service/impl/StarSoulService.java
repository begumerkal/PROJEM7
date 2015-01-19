package com.wyd.empire.world.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wyd.empire.world.battle.StarSoulVo;
import com.wyd.empire.world.bean.StarSoul;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.server.service.base.IStarSoulService;

public class StarSoulService {
	/** 数据库星图列表 */
	private static List<StarSoul> starSoulList = new ArrayList<StarSoul>();
	private IStarSoulService starSoulService;
	/** 解析后星图数据 */
	private static Map<Integer, StarSoulVo> starSoulMap = new HashMap<Integer, StarSoulVo>();
	/** 星图属性索引 */
	private static Map<String, Integer> starSoulIndexMap = new HashMap<String, Integer>();

	// public IStarSoulService getStarSoulService() {
	// return starSoulService;
	// }

	public StarSoulService(IStarSoulService starSoulService) {
		this.starSoulService = starSoulService;
		initMapIndex();
	}

	/**
	 * 初始化星魂数据
	 */
	public void initStarSoulData() {
		starSoulList = starSoulService.getAllStarSoul();
		if (starSoulList.size() > 0) {
			for (StarSoul starSoul : starSoulList) {
				StarSoulVo vo = new StarSoulVo();
				vo.setMapLeve(starSoul.getId()); // 星图等级
				vo.setPlayerLeve(starSoul.getPlayerLeve()); // 限制玩家等级
				String[] values = starSoul.getPeculiarityValue().split("\\|"); // 多个星点
				String[] bonusAttribute = new String[values.length]; // 星点显示的加成属性（服务端传key，客户端显示value）
				int[] bonusValue = new int[values.length]; // 属性加成值
				String[] bonusValueStr = new String[values.length]; // 属性加成值字符串
				String[] consumptionGolds = new String[values.length]; // 消耗金币数
				String[] consumptionDebris = new String[values.length]; // 消耗星魂碎片数
				int[] coordinateX = new int[values.length]; // X坐标
				int[] coordinateY = new int[values.length]; // Y坐标
				int[] bonusIndex = new int[values.length]; // 属性索引
				int index = 0;
				for (String value : values) { // 根据数据动态加载星点属性
					String[] parm = value.split(":");
					bonusAttribute[index] = parm[0]; // 星点显示的加成属性（服务端传key，客户端显示value）
					bonusValueStr[index] = parm[1]; // 属性加成值
					bonusValue[index] = Integer.parseInt(parm[1]); // 属性加成值
					consumptionDebris[index] = parm[2]; // 消耗星魂碎片数
					consumptionGolds[index] = (parm.length > 4 ? parm[3] : "0");// 消耗金币数（防止策划配置数据出错）
					coordinateX[index] = parm.length > 5 ? Integer.parseInt(parm[4]) : 0; // X坐标
					coordinateY[index] = parm.length == 6 ? Integer.parseInt(parm[5]) : 0; // Y坐标
					bonusIndex[index] = starSoulIndexMap.get(parm[0]);// 属性索引
					index++;
				}
				vo.setBonusAttribute(bonusAttribute);
				vo.setBonusValue(bonusValue);
				vo.setConsumptionDebris(consumptionDebris);
				vo.setConsumptionGolds(consumptionGolds);
				vo.setCoordinateX(coordinateX);
				vo.setCoordinateY(coordinateY);
				vo.setBonusIndex(bonusIndex);
				vo.setBonusValueStr(bonusValueStr);
				starSoulMap.put(starSoul.getId(), vo);
			}
		}
	}

	/**
	 * 初始化属性索引值
	 */
	private void initMapIndex() {
		starSoulIndexMap.put(Common.DEFENSE, 1);
		starSoulIndexMap.put(Common.ATTACK, 2);
		starSoulIndexMap.put(Common.DEFEND, 3);
		starSoulIndexMap.put(Common.HP, 4);
		starSoulIndexMap.put(Common.INJURYFREE, 5);
		starSoulIndexMap.put(Common.WRECK_DEFENSE, 6);
		starSoulIndexMap.put(Common.REDUCE_CRIT, 7);
		starSoulIndexMap.put(Common.REDUCE_BURY, 8);
		starSoulIndexMap.put(Common.FORCE, 9);
		starSoulIndexMap.put(Common.ARMOR, 10);
		starSoulIndexMap.put(Common.AGILITY, 11);
		starSoulIndexMap.put(Common.PHYSIQUE, 12);
		starSoulIndexMap.put(Common.LUCK, 13);
	}

	/**
	 * 获取星图map
	 * 
	 * @return
	 */
	public Map<Integer, StarSoulVo> getStarSoulMap() {
		return starSoulMap;
	}

	/**
	 * 获取星点显示的加成属性key
	 * 
	 * @param mapLeve
	 *            星图等级
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String[] getBonusAttributeByMapLeve(int mapLeve) {
		StarSoulVo vo = this.starSoulMap.get(mapLeve);
		if (vo != null) {
			return vo.getBonusAttribute();
		}
		return new String[]{"hp", "attack", "defend", "force", "armor", "agility", "physique"};
	}

	/**
	 * 获取星图属性索引
	 * 
	 * @param mapLeve
	 *            星图等级
	 * @return
	 */
	@SuppressWarnings("static-access")
	public int[] getMapIndexByMapLeve(int mapLeve) {
		StarSoulVo vo = this.starSoulMap.get(mapLeve);
		if (vo != null) {
			return vo.getBonusIndex();
		}
		return new int[]{1, 2, 3, 4, 5, 6, 7};
	}

	/**
	 * 获取星点显示的X坐标
	 * 
	 * @param mapLeve
	 *            星图等级
	 * @return
	 */
	@SuppressWarnings("static-access")
	public int[] getCoordinateXByMapLeve(int mapLeve) {
		StarSoulVo vo = this.starSoulMap.get(mapLeve);
		if (vo != null) {
			return vo.getCoordinateX();
		}
		return new int[]{0, 0, 0, 0, 0, 0, 0};
	}

	/**
	 * 获取星点显示的Y坐标
	 * 
	 * @param mapLeve
	 *            星图等级
	 * @return
	 */
	@SuppressWarnings("static-access")
	public int[] getCoordinateYByMapLeve(int mapLeve) {
		StarSoulVo vo = this.starSoulMap.get(mapLeve);
		if (vo != null) {
			return vo.getCoordinateY();
		}
		return new int[]{0, 0, 0, 0, 0, 0, 0};
	}

	/**
	 * 获取星点显示的属性加成值
	 * 
	 * @param mapLeve
	 *            星图等级
	 * @return
	 */
	@SuppressWarnings("static-access")
	public int[] getBonusValueByMapLeve(int mapLeve) {
		StarSoulVo vo = this.starSoulMap.get(mapLeve);
		if (vo != null) {
			return vo.getBonusValue();
		}
		return new int[]{0, 0, 0, 0, 0, 0, 0};
	}

	/**
	 * 获取星点点亮消耗所需的代币数组
	 * 
	 * @param mapLeve
	 *            星图等级
	 * @param soulDot
	 *            当前星点
	 * @return int[]{消耗星魂碎片数,消耗金币数}
	 */
	@SuppressWarnings("static-access")
	public int[] getConsumptionValueByMapLeveAndSoulDot(int mapLeve, int soulDot) {
		StarSoulVo vo = this.starSoulMap.get(mapLeve);
		if (vo != null) {
			return new int[]{Integer.parseInt(vo.getConsumptionDebris()[soulDot]), Integer.parseInt(vo.getConsumptionGolds()[soulDot])};
		}
		return new int[]{0, 0};
	}

	/**
	 * 获取星点显示的属性加成key和value
	 * 
	 * @param mapLeve
	 *            星图等级
	 * @param soulDot
	 *            当前星点
	 * @return String[]{加成属性key,加成属性value}
	 */
	@SuppressWarnings("static-access")
	public String[] getBonusValueByMapLeveAndSoulDot(int mapLeve, int soulDot) {
		StarSoulVo vo = this.starSoulMap.get(mapLeve);
		if (vo != null) {
			return new String[]{vo.getBonusAttribute()[soulDot], vo.getBonusValueStr()[soulDot]};
		}
		return new String[]{"attack", "0"};
	}

	/**
	 * 获取星图星点最高等级
	 * 
	 * @param mapLeve
	 *            星图等级
	 * @return
	 */
	@SuppressWarnings("static-access")
	public int getSoulDotMaxLeveByMapLeve(int mapLeve) {
		StarSoulVo vo = this.starSoulMap.get(mapLeve);
		if (vo != null) {
			return vo.getBonusValue().length;
		}
		return 0;
	}

	/**
	 * 获取星图限制等级
	 * 
	 * @param mapLeve
	 *            星图等级
	 * @return
	 */
	@SuppressWarnings("static-access")
	public int getPlayerLeveByMapLeve(int mapLeve) {
		StarSoulVo vo = this.starSoulMap.get(mapLeve);
		if (vo != null) {
			return vo.getPlayerLeve();
		}
		return 0;
	}

	/**
	 * 获取星图最高等级
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public int getMapMaxLeve() {
		return this.starSoulMap.size();
	}

}
