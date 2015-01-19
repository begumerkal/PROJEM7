/**
 * 
 */
package com.wyd.empire.world.battle;

/**
 * @author haotao
 */
public class CombatChara {
	public static final int CHARA_TYPE_PLAYER = 0;
	public static final int CHARA_TYPE_GUAI = 1;
	public static final int CHARA_TYPE_ENERMY_MELEE = -1;
	public static final int CHARA_TYPE_MINE_MELEE = -2;
	private int type; // 当前控制的角色类弄,0:player 1:guai -1:敌方近攻怪 -2:我方近攻怪
	private Combat combat = null;
	private Combat combatGuai = null;

	public CombatChara(int type, Combat combat, Combat combatGuai) {
		this.type = type;
		this.combat = combat;
		this.combatGuai = combatGuai;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Combat getCombat() {
		return combat;
	}

	public void setCombat(Combat combat) {
		this.combat = combat;
	}

	public Combat getCombatGuai() {
		return combatGuai;
	}

	public void setCombatGuai(Combat combatGuai) {
		this.combatGuai = combatGuai;
	}
}
