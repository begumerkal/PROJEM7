package com.wyd.empire.world.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class FuncUtil {

	/**
	 * 自定义字符串格式化
	 * 
	 * @param str
	 *            bd:1,be:0:15,aa:1:4:4,ab:1:4:4:5,ac:1:4:4:1:4:4:5
	 */
	public Map<String, ArrayList<String>> strToMap(String str) {
		String[] arrStr = str.split(",");
		Map<String, ArrayList<String>> dataMap = new HashMap<String, ArrayList<String>>();
		for (String str1 : arrStr) {
			String[] arrStr2 = str1.split(":");

			ArrayList<String> arr = new ArrayList<String>();
			for (int i = 0; i < arrStr2.length; i++) {
				if (i == 0 && !dataMap.containsKey(arrStr2[i])) {
					dataMap.put(arrStr2[i], arr);
				} else {
					arr.add(arrStr2[i]);
				}
			}
		}
		return dataMap;
	}

	/**
	 * map 转自定义字符串
	 * 
	 * @param map
	 *            {"ab":[1,2,3,4,5,6,7,8],"ac":[1,2,3,4,5,6,7,8]}
	 */
	public String mapToStr(Map<String, ArrayList<Object>> map) {
		StringBuffer strBuff = new StringBuffer("");
		boolean isRun = false;
		for (String str : map.keySet()) {
			strBuff.append(str);
			strBuff.append(":");
			ArrayList<Object> arr = map.get(str);
			for (int i = 0; i < arr.size(); i++) {
				strBuff.append(arr.get(i));
				if (i == arr.size() - 1) {
					strBuff.append(",");
				} else {
					strBuff.append(":");
				}
				isRun = true;
			}
		}
		if (isRun) {
			return strBuff.substring(0, strBuff.length() - 1);
		}
		return strBuff.toString();
	}

	/**
	 * 字典模式随机最大只支持3位小数,否则报错
	 * 
	 * @param List
	 *            <HashMap<String, Double>> dic
	 *            概率[{"a":0.005},{"b":0.658}]长度不定，根据需要
	 */
	public String rand_dict1(HashMap<String, Object> map) {
		double total = 0;
		for (Entry<String, Object> item : map.entrySet()) {
			total += Double.valueOf(item.getValue().toString());
		}
		double r = Math.random() * total;
		double start = 0;
		double end = 0;
		for (Entry<String, Object> item : map.entrySet()) {
			double v = Double.valueOf(item.getValue().toString());
			end += v;
			if (r >= start && r <= end) {
				return item.getKey();
			}
			start += v;
		}
		return null;
	}
}
