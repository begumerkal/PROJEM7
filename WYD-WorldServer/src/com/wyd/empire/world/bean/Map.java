package com.wyd.empire.world.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the tab_map database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_map")
public class Map implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String animationIndexCode;
	private short channel;
	private String color;
	private String dese;
	private String icon;
	private String name;
	private short star;
	private String mapIcon;
	private String nameShort;
	private int number;
	private int level;
	private int times;
	private int mapType;
	private int mapSubtypeid;
	private int bossmapSerial;
	private String guaiList;
	private int seqmap;
	private String runTime1;// 难度一回合数限制
	private String runTime2;// 难度二回合数限制
	private String runTime3;// 难度三回合数限制
	private String buffList;// BUFF坐标
	private String npcNumber;// 怪物出生数量
	private int passTimes;// 通关次数
	private int vitalityExpend;// 活力消耗
	private String passCoor;// 关卡图示坐标点
	private int dropId1;// 普通掉落ID
	private String dropId2;// 精英掉落ID
	private int eventId; // 事件ID
	private int eventRate; // 发生概率
	private String reward;// 固定得到的奖励（如经验，金币）

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
	@Column(name = "animationIndexCode", nullable = false, length = 100)
	public String getAnimationIndexCode() {
		return this.animationIndexCode;
	}

	public void setAnimationIndexCode(String animationIndexCode) {
		this.animationIndexCode = animationIndexCode;
	}

	@Basic()
	@Column(name = "channel", nullable = false, precision = 5)
	public short getChannel() {
		return this.channel;
	}

	public void setChannel(short channel) {
		this.channel = channel;
	}

	@Basic()
	@Column(name = "color", nullable = false, length = 30)
	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Basic()
	@Column(name = "dese", nullable = false, length = 100)
	public String getDese() {
		return this.dese;
	}

	public void setDese(String dese) {
		this.dese = dese;
	}

	@Basic()
	@Column(name = "icon", nullable = false, length = 100)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Basic()
	@Column(name = "name", nullable = false, length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic()
	@Column(name = "star", nullable = false, precision = 5)
	public short getStar() {
		return this.star;
	}

	public void setStar(short star) {
		this.star = star;
	}

	@Basic()
	@Column(name = "map_icon", length = 100)
	public String getMapIcon() {
		return mapIcon;
	}

	public void setMapIcon(String mapIcon) {
		this.mapIcon = mapIcon;
	}

	@Basic()
	@Column(name = "bossmap_serial", precision = 10)
	public int getBossmapSerial() {
		return bossmapSerial;
	}

	public void setBossmapSerial(int bossmapSerial) {
		this.bossmapSerial = bossmapSerial;
	}

	@Basic()
	@Column(name = "reward", length = 100)
	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	@Basic()
	@Column(name = "guai_list", precision = 500)
	public String getGuaiList() {
		return guaiList;
	}

	public void setGuaiList(String guaiList) {
		this.guaiList = guaiList;
	}

	@Basic()
	@Column(name = "level", precision = 10)
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Basic()
	@Column(name = "map_subtypeid", precision = 10)
	public int getMapSubtypeid() {
		return mapSubtypeid;
	}

	public void setMapSubtypeid(int mapSubtypeid) {
		this.mapSubtypeid = mapSubtypeid;
	}

	@Basic()
	@Column(name = "map_type", precision = 4)
	public int getMapType() {
		return mapType;
	}

	public void setMapType(int mapType) {
		this.mapType = mapType;
	}

	@Basic()
	@Column(name = "name_short", length = 16)
	public String getNameShort() {
		return nameShort;
	}

	public void setNameShort(String nameShort) {
		this.nameShort = nameShort;
	}

	@Basic()
	@Column(name = "number", precision = 4)
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Basic()
	@Column(name = "times", precision = 10)
	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	@Basic()
	@Column(name = "seqmap", precision = 10)
	public int getSeqmap() {
		return seqmap;
	}

	public void setSeqmap(int seqmap) {
		this.seqmap = seqmap;
	}

	@Basic()
	@Column(name = "run_time1", length = 50)
	public String getRunTime1() {
		return runTime1;
	}

	public void setRunTime1(String runTime1) {
		this.runTime1 = runTime1;
	}

	@Basic()
	@Column(name = "run_time2", length = 50)
	public String getRunTime2() {
		return runTime2;
	}

	public void setRunTime2(String runTime2) {
		this.runTime2 = runTime2;
	}

	@Basic()
	@Column(name = "run_time3", length = 50)
	public String getRunTime3() {
		return runTime3;
	}

	public void setRunTime3(String runTime3) {
		this.runTime3 = runTime3;
	}

	@Basic()
	@Column(name = "buff_list", length = 255)
	public String getBuffList() {
		return buffList;
	}

	public void setBuffList(String buffList) {
		this.buffList = buffList;
	}

	@Basic()
	@Column(name = "npc_number", length = 50)
	public String getNpcNumber() {
		return npcNumber;
	}

	public void setNpcNumber(String npcNumber) {
		this.npcNumber = npcNumber;
	}

	@Basic()
	@Column(name = "pass_times", length = 2)
	public int getPassTimes() {
		return passTimes;
	}

	public void setPassTimes(int passTimes) {
		this.passTimes = passTimes;
	}

	@Basic()
	@Column(name = "vitality_expend", length = 2)
	public int getVitalityExpend() {
		return vitalityExpend;
	}

	public void setVitalityExpend(int vitalityExpend) {
		this.vitalityExpend = vitalityExpend;
	}

	@Basic()
	@Column(name = "pass_coor", length = 2)
	public String getPassCoor() {
		return passCoor;
	}

	public void setPassCoor(String passCoor) {
		this.passCoor = passCoor;
	}

	@Basic()
	@Column(name = "drop_id1", length = 2)
	public int getDropId1() {
		return dropId1;
	}

	public void setDropId1(int dropId1) {
		this.dropId1 = dropId1;
	}

	@Basic()
	@Column(name = "drop_id2", length = 15)
	public String getDropId2() {
		return dropId2;
	}

	public void setDropId2(String dropId2) {
		this.dropId2 = dropId2;
	}

	@Basic()
	@Column(name = "event_id", length = 2)
	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	@Basic()
	@Column(name = "event_rate", length = 4)
	public int getEventRate() {
		return eventRate;
	}

	public void setEventRate(int eventRate) {
		this.eventRate = eventRate;
	}

	/**
	 * 把BuffList字符串格式为List<Map<String,String>> Map的KEY：id,posX,posY
	 * 
	 * @return
	 */
	public List<java.util.Map<String, Integer>> BuffListPos() {
		if (buffList == null || "-1".equals(buffList) || "".equals(buffList))
			return null;

		List<java.util.Map<String, Integer>> list = new ArrayList<java.util.Map<String, Integer>>();
		String[] posOut = buffList.split("\\),\\(");
		String[] posIn;
		String posInStr = "";
		for (int i = 0; i < posOut.length; i++) {
			java.util.Map<String, Integer> map = new HashMap<String, Integer>();
			posInStr = posOut[i];
			if (i == 0) {
				posInStr = posInStr.substring(1);
			} else if (i == posOut.length - 1) {
				posInStr = posInStr.substring(0, posOut[i].length() - 1);
			}
			posIn = posInStr.split(",");
			map.put("id", Integer.parseInt(posIn[0]));
			map.put("posX", Integer.parseInt(posIn[1]));
			map.put("posY", Integer.parseInt(posIn[2]));
			list.add(map);

		}
		return list;
	}

	/**
	 * passCoor 转成 int[] 0:x 1:y
	 */
	public int[] passCoor() {
		int[] coor = new int[2];
		if (passCoor == null || passCoor.length() < 3) {
			return coor;
		}
		String[] coor_s = passCoor.split(",");
		coor[0] = Integer.parseInt(coor_s[0]);
		coor[1] = Integer.parseInt(coor_s[1]);
		return coor;
	}
}