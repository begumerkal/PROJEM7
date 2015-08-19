package com.app.empire.world.entity.mongo;

/**
 * 技能实体
 * 
 * @author doter
 */

public class skill {
	private int skill_ext_id;// 技能扩展id
	private int lv;// 等级
	private String property;// 属性

	public int getSkill_ext_id() {
		return skill_ext_id;
	}
	public void setSkill_ext_id(int skill_ext_id) {
		this.skill_ext_id = skill_ext_id;
	}
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}

}
