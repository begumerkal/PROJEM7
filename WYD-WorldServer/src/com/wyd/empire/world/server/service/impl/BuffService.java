package com.wyd.empire.world.server.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.wyd.empire.world.bean.VipRate;
import com.wyd.empire.world.buff.Buff;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * 玩家增益状态管理对象
 * 
 * @author Administrator
 */
public class BuffService implements Runnable {
	public void start() {
		Thread t = new Thread(this);
		t.setName("BuffService-Thread");
		t.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				// System.out.println((new
				// Date()).toString()+"---BuffService---sleep");
				Thread.sleep(60000);
				// System.out.println((new
				// Date()).toString()+"---BuffService---start");
				checkUserBuff();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 检查所有玩家的buff
	 */
	public void checkUserBuff() {
		Collection<WorldPlayer> playerList = ServiceManager.getManager().getPlayerService().getOnlinePlayer();
		long nowTime = System.currentTimeMillis();
		List<Buff> buffList;
		Map<String, Buff> buffMap;
		for (WorldPlayer player : playerList) {
			buffList = player.getBuffList();
			buffMap = player.getBuffMap();
			Buff buff;
			for (int i = buffList.size() - 1; i >= 0; i--) {
				buff = buffList.get(i);
				if (buff.getEndtime() != -1 && buff.getEndtime() < nowTime) {
					buffMap.remove(buff.getBuffCode());
					buffList.remove(i);
				}
			}
		}
		playerList = null;
	}

	/**
	 * 玩家增加buff
	 * 
	 * @param player
	 * @param buff
	 */
	public void addBuff(WorldPlayer player, Buff buff) {
		player.getBuffList().add(buff);
		player.getBuffMap().put(buff.getBuffCode(), buff);
	}

	/**
	 * 计算玩家加成后的经验
	 * 
	 * @param player
	 * @param oExp
	 * @return
	 */
	public int getExp(WorldPlayer player, int exp) {
		if (exp < 1) {
			return exp;
		}
		Buff buff;
		for (int i = player.getBuffList().size() - 1; i >= 0; i--) {
			buff = player.getBuffList().get(i);
			if (buff.getEndtime() < System.currentTimeMillis() && buff.getEndtime() != -1) {
				player.getBuffMap().remove(buff.getBuffCode());
				player.getBuffList().remove(i);
				continue;
			}
			if (buff.getBuffCode().equals(Buff.EXP)) {
				if (0 == buff.getAddType()) {
					exp = (int) (Math.ceil((exp * buff.getQuantity()) / 100.0));
				} else {
					exp += buff.getQuantity();
				}
			}
		}
		if (player.isVip()) {
			VipRate vr = ServiceManager.getManager().getPlayerItemsFromShopService().getVipRateById(player.getPlayer().getVipLevel());
			exp = (int) Math.ceil(exp * (100.0 + vr.getExpRate()) / 100);
		}
		return exp;
	}

	/**
	 * 计算玩家加成后数值
	 * 
	 * @param player
	 * @param addParam
	 * @param buffType
	 *            buff类型
	 * @return
	 */
	public float getAddition(WorldPlayer player, int addParam, String buffType) {
		if (addParam < 1) {
			return addParam;
		}
		Buff buff;
		float addParamFloat = addParam;
		for (int i = player.getBuffList().size() - 1; i >= 0; i--) {
			buff = player.getBuffList().get(i);
			if (buff.getEndtime() < System.currentTimeMillis() && buff.getEndtime() != -1) {
				player.getBuffMap().remove(buff.getBuffCode());
				player.getBuffList().remove(i);
				continue;
			}
			if (buff.getBuffCode().equals(buffType)) {
				if (0 == buff.getAddType()) {
					addParamFloat = (addParam * (100f + buff.getQuantity() / 100f)) / 100f;
				} else {
					addParamFloat += buff.getQuantity();
				}
			}
		}
		return addParamFloat;
	}
}
