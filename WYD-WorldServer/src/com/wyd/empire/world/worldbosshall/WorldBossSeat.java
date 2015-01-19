package com.wyd.empire.world.worldbosshall;

import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.player.WorldPlayer;

public class WorldBossSeat {
	private WorldPlayer player; // 玩家
	private int hurt; // 伤害输出
	private Combat combat; // 战斗对象

	public Combat getCombat() {
		return combat;
	}

	public void setCombat(Combat combat) {
		this.combat = combat;
	}

	public void init() {
		hurt = 0;
	}

	public int getHurt() {
		return hurt;
	}

	public void setHurt(int hurt) {
		this.hurt = hurt;
	}

	public WorldPlayer getPlayer() {
		return player;
	}

	public void setPlayer(WorldPlayer player) {
		this.player = player;
	}

}
