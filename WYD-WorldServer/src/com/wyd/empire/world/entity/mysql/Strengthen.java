package com.wyd.empire.world.entity.mysql;

public class Strengthen {
	String val = "";

	public Strengthen(String stregthen) {
		val = stregthen;
	}

	/**
	 * 合成下一等级石头的id（目标道具）
	 * 
	 * @return
	 */
	public int hcdj() {
		if (val == null)
			return 0;
		String[] split = val.split(",");
		if (split.length > 2) {
			String[] keyVal = split[2].split("=");
			if (keyVal.length < 2)
				return 0;
			return Integer.parseInt(keyVal[1]);
		}
		return 0;
	}

	/**
	 * 合成成功概率
	 * 
	 * @return
	 */
	public int cggl() {
		if (val == null)
			return 0;
		String[] split = val.split(",");
		if (split.length > 0) {
			String[] keyVal = split[0].split("=");
			if (keyVal.length < 2)
				return 0;
			return Integer.parseInt(keyVal[1]);
		}
		return 0;
	}

	/**
	 * 合成下一等级真强化石的id
	 * 
	 * @return
	 */
	public int zqdj() {
		if (val == null)
			return 0;
		String[] split = val.split(",");
		if (split.length > 3) {
			String[] keyVal = split[3].split("=");
			if (keyVal.length < 2)
				return 0;
			return Integer.parseInt(keyVal[1]);
		}
		return 0;
	}

	/**
	 * 合成道具使用天数 默认为1天
	 * 
	 * @return
	 */
	public int hcts() {
		if (val == null)
			return 1;
		String[] split = val.split(",");
		if (split.length > 3) {
			String[] keyVal = split[3].split("=");
			if (keyVal.length < 2)
				return 0;
			return Integer.parseInt(keyVal[1]);
		}
		return 1;
	}
}
