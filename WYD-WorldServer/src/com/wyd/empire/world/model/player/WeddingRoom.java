package com.wyd.empire.world.model.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.entity.mysql.WeddingHall;

public class WeddingRoom {
	private WeddingHall wedHall;
	private List<WorldPlayer> playerList = new ArrayList<WorldPlayer>();// 来宾列表
	private Map<Integer, WorldPlayer> map = new HashMap<Integer, WorldPlayer>();
	private int randomClothes = ServiceUtils.getRandomNum(1, 3);
	private String password = "";

	private int useDiamond;// 消耗钻石
	private int bgGifts;// 新郎发放红包数（钻石）
	private int bGifts;// 新娘发放红包数（钻石）
	private long bgInTime;// 新郎进入时间
	private long bInTime;// 新娘进入时间
	private long bgTime;// 新郎累计时间
	private long bTime;// 新娘累计时间

	public WeddingHall getWedHall() {
		return wedHall;
	}

	public void setWedHall(WeddingHall wedHall) {
		this.wedHall = wedHall;
	}

	public List<WorldPlayer> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<WorldPlayer> playerList) {
		this.playerList = playerList;
	}

	public Map<Integer, WorldPlayer> getMap() {
		return map;
	}

	public void setMap(Map<Integer, WorldPlayer> map) {
		this.map = map;
	}

	public int getRandomClothes() {
		return randomClothes;
	}

	public void setRandomClothes(int randomClothes) {
		this.randomClothes = randomClothes;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUseDiamond() {
		return useDiamond;
	}

	public void setUseDiamond(int useDiamond) {
		this.useDiamond = useDiamond;
	}

	public int getBgGifts() {
		return bgGifts;
	}

	public void setBgGifts(int bgGifts) {
		this.bgGifts = bgGifts;
	}

	public int getbGifts() {
		return bGifts;
	}

	public void setbGifts(int bGifts) {
		this.bGifts = bGifts;
	}

	public long getBgInTime() {
		return bgInTime;
	}

	public void setBgInTime(long bgInTime) {
		this.bgInTime = bgInTime;
	}

	public long getbInTime() {
		return bInTime;
	}

	public void setbInTime(long bInTime) {
		this.bInTime = bInTime;
	}

	public long getBgTime() {
		return bgTime;
	}

	public void setBgTime(long bgTime) {
		this.bgTime += bgTime;
	}

	public long getbTime() {
		return bTime;
	}

	public void setbTime(long bTime) {
		this.bTime += bTime;
	}
}