package com.wyd.empire.world.bossmaproom;

import com.wyd.empire.world.bean.Guai;

/**
 * @author zguoqiu
 * @version 创建时间：2013-10-9 上午10:18:04 类说明
 */
public class GuaiPlayer {
	private Guai guai;
	private int attack;
	private int defend;
	private int crit;
	private int critAttackRate;
	private int maxHP;
	private int maxPF;
	private int explodeRadius;
	private int injuryFree;
	private int wreckDefense;
	private int reduceCrit;
	private int reduceBury;
	private int force;
	private int armor;
	private int agility;
	private int physique;
	private int luck;
	private int fighting;
	private int proficiency;// 武器熟练度

	public GuaiPlayer(Guai guai) {
		this.guai = guai;
		attack = (int) ((guai.getLevel() + 9) * 0.68) + guai.getAttack() + (guai.getForce() * 4 / 25);
		defend = (int) ((guai.getLevel() + 10) * 0.6) + guai.getDefend() + guai.getArmor() * 9 / 50;
		crit = 500;
		critAttackRate = 13000;
		maxHP = (int) (3.8 * (1 - Math.pow(1.0035, guai.getLevel())) / (1 - 1.0035)) + guai.getHp() + guai.getPhysique();
		explodeRadius = guai.getAttackArea();
		injuryFree = (int) (guai.getInjuryFree());
		wreckDefense = guai.getWreckDefense();
		reduceCrit = guai.getReduceCrit();
		reduceBury = guai.getReduceBury() + guai.getLuck() * 10000 / 8333;
		force = guai.getForce();
		armor = guai.getArmor();
		agility = guai.getAgility();
		physique = guai.getPhysique();
		luck = guai.getLuck();
		float c = getCrit();
		float a = getAttack();
		float h = getMaxHP();
		float w = getWreckDefense();
		float r = getReduceCrit();
		float i = getInjuryFree() + defend / (defend + 600f);
		float e = getExplodeRadius();
		// sqrt((暴击率*(攻击力*暴击率*1.5+攻击力*(1-暴击率))+(1-暴击率)*攻击力)^1.25*生命*(1+破防率)*(1+0.75*免暴率)/(1-免伤率)
		// ^1.25) *((爆破范围-45)*0.004+1)-1000
		fighting = (int) (Math.sqrt(Math.pow((c * (a * c * 1.5 + a * (1 - c)) + (1 - c) * a), 1.25) * h * (1 + w) * (1 + 0.75 * r)
				/ Math.pow((1 - i), 1.25))
				* ((e - 45f) * 0.004 + 1f) - 1000f);
		maxPF = 100;
		proficiency = 10000;
	}

	public int getProficiency() {
		return proficiency;
	}

	public void setProficiency(int proficiency) {
		this.proficiency = proficiency;
	}

	public Guai getGuai() {
		return guai;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public void setDefend(int defend) {
		this.defend = defend;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public void setMaxPF(int maxPF) {
		this.maxPF = maxPF;
	}

	public int getDefend() {
		return defend;
	}

	public int getCrit() {
		return crit;
	}

	public int getCritAttackRate() {
		return critAttackRate;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public int getMaxPF() {
		return maxPF;
	}

	public int getExplodeRadius() {
		return explodeRadius;
	}

	public int getInjuryFree() {
		return injuryFree;
	}

	public int getWreckDefense() {
		return wreckDefense;
	}

	public int getReduceCrit() {
		return reduceCrit;
	}

	public int getReduceBury() {
		return reduceBury;
	}

	public int getForce() {
		return force;
	}

	public int getArmor() {
		return armor;
	}

	public int getAgility() {
		return agility;
	}

	public int getPhysique() {
		return physique;
	}

	public int getLuck() {
		return luck;
	}

	public int getFighting() {
		return fighting;
	}

	/**
	 * 当前最大怒气值
	 * 
	 * @return
	 */
	public int getMaxSP() {
		return 100;
	}

}
