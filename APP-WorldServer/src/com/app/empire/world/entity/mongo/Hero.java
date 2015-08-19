package com.app.empire.world.entity.mongo;

import java.util.ArrayList;
import org.springframework.data.mongodb.core.mapping.Document;
import com.app.db.mongo.entity.IEntity;

/**
 * 英雄表
 * 
 * @author doter
 */

@Document(collection = "hero")
public class Hero extends IEntity {
	private int user_id;// 用户id
	private int hero_base_id;// 基表英雄id
	private int hero_ext_id;// 英雄扩展id
	private int experience;// 经验
	private int lv;// 等级
	private ArrayList<skill> skill;// 技能
	private int fighting;// 战斗力
	private int quality;// 品质

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getHero_base_id() {
		return hero_base_id;
	}
	public void setHero_base_id(int hero_base_id) {
		this.hero_base_id = hero_base_id;
	}
	public int getHero_ext_id() {
		return hero_ext_id;
	}
	public void setHero_ext_id(int hero_ext_id) {
		this.hero_ext_id = hero_ext_id;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public ArrayList<?> getSkill() {
		return skill;
	}
	public void setSkill(ArrayList<skill> skill) {
		this.skill = skill;
	}
	public int getFighting() {
		return fighting;
	}
	public void setFighting(int fighting) {
		this.fighting = fighting;
	}
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}

}
