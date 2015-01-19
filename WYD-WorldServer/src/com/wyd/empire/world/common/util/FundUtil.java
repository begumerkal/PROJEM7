package com.wyd.empire.world.common.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * 基金相关配置信息
 * 
 * @author sunzx
 */
public class FundUtil {
	/** 初级基金类型 */
	public static final int FUND_TYPE_LOW = 0;
	/** 中级基金类型 */
	public static final int FUND_TYPE_MIDDLE = 1;
	/** 高级基金类型 */
	public static final int FUND_TYPE_HIGH = 2;
	/** 初级基金金额 */
	public static final int FUND_TYPE_LOW_AMOUNT = 100;
	/** 中级基金金额 */
	public static final int FUND_TYPE_MIDDLE_AMOUNT = 500;
	/** 高级基金金额 */
	public static final int FUND_TYPE_HIGH_AMOUNT = 1000;

	/** 初级基金金额信息 */
	public static String FUND_TYPE_LOW_AMOUNT_INFO = "";
	/** 中级基金金额 信息 */
	public static String FUND_TYPE_MIDDLE_AMOUNT_INFO = "";
	/** 高级基金金额 信息 */
	public static String FUND_TYPE_HIGH_AMOUNT_INFO = "";

	private static Map<Integer, Map<Integer, Integer>> fundMap = null;

	// static{
	// initMap();
	// }
	/**
	 * 根据数据库中的基金配置动态初始化相应map、
	 */
	public static void initMap() {
		String fund = ServiceManager.getManager().getVersionService().getVersion().getFundAllocation();
		if (fund == null || fund.equals(null) || fund.equals("")) {
			System.out.println("基金参数为空！");
			return;
		}
		String[] fundStr = fund.split("\\|");
		fundMap = new HashMap<Integer, Map<Integer, Integer>>();
		if (fundStr.length >= 1) {
			Map<Integer, Integer> levelLowMap = new LinkedHashMap<Integer, Integer>();
			String[] str = fundStr[0].split(",");
			FUND_TYPE_LOW_AMOUNT_INFO = fundStr[0];
			for (String parameter : str) {
				levelLowMap.put(Integer.parseInt(parameter.split("=")[0]), Integer.parseInt(parameter.split("=")[1]));
			}
			fundMap.put(FUND_TYPE_LOW, levelLowMap);
		}
		if (fundStr.length >= 2) {
			Map<Integer, Integer> levelMiddleMap = new LinkedHashMap<Integer, Integer>();
			String[] str = fundStr[1].split(",");
			FUND_TYPE_MIDDLE_AMOUNT_INFO = fundStr[1];
			for (String parameter : str) {
				levelMiddleMap.put(Integer.parseInt(parameter.split("=")[0]), Integer.parseInt(parameter.split("=")[1]));
			}
			fundMap.put(FUND_TYPE_MIDDLE, levelMiddleMap);
		}
		if (fundStr.length >= 3) {
			Map<Integer, Integer> levelHighMap = new LinkedHashMap<Integer, Integer>();
			String[] str = fundStr[2].split(",");
			FUND_TYPE_HIGH_AMOUNT_INFO = fundStr[2];
			for (String parameter : str) {
				levelHighMap.put(Integer.parseInt(parameter.split("=")[0]), Integer.parseInt(parameter.split("=")[1]));
			}
			fundMap.put(FUND_TYPE_HIGH, levelHighMap);
		}

	}

	/**
	 * 获取基金类型MAP
	 * 
	 * @return 基金类型MAP
	 */
	public static Map<Integer, Integer> getFundLevelMap(int type) {
		return fundMap.get(type);
	}
}
