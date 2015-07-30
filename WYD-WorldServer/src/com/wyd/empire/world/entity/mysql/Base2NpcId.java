package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Base2NpcId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class Base2NpcId implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String nickname;
	private String showId;
	private Integer incity;
	private Integer sex;
	private Integer lv;
	private Integer color;
	private String dialogue;
	private Integer type;
	private Integer action;
	private Integer invisibleFall;
	private String rota;
	private String pos;
	private Integer jumpSceneId;
	private String iconName;
	private Float iconOffset;

	// Constructors

	/** default constructor */
	public Base2NpcId() {
	}

	/** full constructor */
	public Base2NpcId(Integer id, String name, String nickname, String showId,
			Integer incity, Integer sex, Integer lv, Integer color,
			String dialogue, Integer type, Integer action,
			Integer invisibleFall, String rota, String pos,
			Integer jumpSceneId, String iconName, Float iconOffset) {
		this.id = id;
		this.name = name;
		this.nickname = nickname;
		this.showId = showId;
		this.incity = incity;
		this.sex = sex;
		this.lv = lv;
		this.color = color;
		this.dialogue = dialogue;
		this.type = type;
		this.action = action;
		this.invisibleFall = invisibleFall;
		this.rota = rota;
		this.pos = pos;
		this.jumpSceneId = jumpSceneId;
		this.iconName = iconName;
		this.iconOffset = iconOffset;
	}

	// Property accessors

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 12)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "nickname", nullable = false, length = 25)
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "show_id", nullable = false, length = 4)
	public String getShowId() {
		return this.showId;
	}

	public void setShowId(String showId) {
		this.showId = showId;
	}

	@Column(name = "incity", nullable = false)
	public Integer getIncity() {
		return this.incity;
	}

	public void setIncity(Integer incity) {
		this.incity = incity;
	}

	@Column(name = "sex", nullable = false)
	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Column(name = "lv", nullable = false)
	public Integer getLv() {
		return this.lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
	}

	@Column(name = "color", nullable = false)
	public Integer getColor() {
		return this.color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	@Column(name = "dialogue", nullable = false, length = 125)
	public String getDialogue() {
		return this.dialogue;
	}

	public void setDialogue(String dialogue) {
		this.dialogue = dialogue;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "action", nullable = false)
	public Integer getAction() {
		return this.action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	@Column(name = "invisible_fall", nullable = false)
	public Integer getInvisibleFall() {
		return this.invisibleFall;
	}

	public void setInvisibleFall(Integer invisibleFall) {
		this.invisibleFall = invisibleFall;
	}

	@Column(name = "rota", nullable = false, length = 64)
	public String getRota() {
		return this.rota;
	}

	public void setRota(String rota) {
		this.rota = rota;
	}

	@Column(name = "pos", nullable = false, length = 64)
	public String getPos() {
		return this.pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	@Column(name = "jump_scene_id", nullable = false)
	public Integer getJumpSceneId() {
		return this.jumpSceneId;
	}

	public void setJumpSceneId(Integer jumpSceneId) {
		this.jumpSceneId = jumpSceneId;
	}

	@Column(name = "icon_name", nullable = false, length = 32)
	public String getIconName() {
		return this.iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	@Column(name = "icon_offset", nullable = false, precision = 12, scale = 0)
	public Float getIconOffset() {
		return this.iconOffset;
	}

	public void setIconOffset(Float iconOffset) {
		this.iconOffset = iconOffset;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Base2NpcId))
			return false;
		Base2NpcId castOther = (Base2NpcId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getName() == castOther.getName()) || (this.getName() != null
						&& castOther.getName() != null && this.getName()
						.equals(castOther.getName())))
				&& ((this.getNickname() == castOther.getNickname()) || (this
						.getNickname() != null
						&& castOther.getNickname() != null && this
						.getNickname().equals(castOther.getNickname())))
				&& ((this.getShowId() == castOther.getShowId()) || (this
						.getShowId() != null && castOther.getShowId() != null && this
						.getShowId().equals(castOther.getShowId())))
				&& ((this.getIncity() == castOther.getIncity()) || (this
						.getIncity() != null && castOther.getIncity() != null && this
						.getIncity().equals(castOther.getIncity())))
				&& ((this.getSex() == castOther.getSex()) || (this.getSex() != null
						&& castOther.getSex() != null && this.getSex().equals(
						castOther.getSex())))
				&& ((this.getLv() == castOther.getLv()) || (this.getLv() != null
						&& castOther.getLv() != null && this.getLv().equals(
						castOther.getLv())))
				&& ((this.getColor() == castOther.getColor()) || (this
						.getColor() != null && castOther.getColor() != null && this
						.getColor().equals(castOther.getColor())))
				&& ((this.getDialogue() == castOther.getDialogue()) || (this
						.getDialogue() != null
						&& castOther.getDialogue() != null && this
						.getDialogue().equals(castOther.getDialogue())))
				&& ((this.getType() == castOther.getType()) || (this.getType() != null
						&& castOther.getType() != null && this.getType()
						.equals(castOther.getType())))
				&& ((this.getAction() == castOther.getAction()) || (this
						.getAction() != null && castOther.getAction() != null && this
						.getAction().equals(castOther.getAction())))
				&& ((this.getInvisibleFall() == castOther.getInvisibleFall()) || (this
						.getInvisibleFall() != null
						&& castOther.getInvisibleFall() != null && this
						.getInvisibleFall()
						.equals(castOther.getInvisibleFall())))
				&& ((this.getRota() == castOther.getRota()) || (this.getRota() != null
						&& castOther.getRota() != null && this.getRota()
						.equals(castOther.getRota())))
				&& ((this.getPos() == castOther.getPos()) || (this.getPos() != null
						&& castOther.getPos() != null && this.getPos().equals(
						castOther.getPos())))
				&& ((this.getJumpSceneId() == castOther.getJumpSceneId()) || (this
						.getJumpSceneId() != null
						&& castOther.getJumpSceneId() != null && this
						.getJumpSceneId().equals(castOther.getJumpSceneId())))
				&& ((this.getIconName() == castOther.getIconName()) || (this
						.getIconName() != null
						&& castOther.getIconName() != null && this
						.getIconName().equals(castOther.getIconName())))
				&& ((this.getIconOffset() == castOther.getIconOffset()) || (this
						.getIconOffset() != null
						&& castOther.getIconOffset() != null && this
						.getIconOffset().equals(castOther.getIconOffset())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getName() == null ? 0 : this.getName().hashCode());
		result = 37 * result
				+ (getNickname() == null ? 0 : this.getNickname().hashCode());
		result = 37 * result
				+ (getShowId() == null ? 0 : this.getShowId().hashCode());
		result = 37 * result
				+ (getIncity() == null ? 0 : this.getIncity().hashCode());
		result = 37 * result
				+ (getSex() == null ? 0 : this.getSex().hashCode());
		result = 37 * result + (getLv() == null ? 0 : this.getLv().hashCode());
		result = 37 * result
				+ (getColor() == null ? 0 : this.getColor().hashCode());
		result = 37 * result
				+ (getDialogue() == null ? 0 : this.getDialogue().hashCode());
		result = 37 * result
				+ (getType() == null ? 0 : this.getType().hashCode());
		result = 37 * result
				+ (getAction() == null ? 0 : this.getAction().hashCode());
		result = 37
				* result
				+ (getInvisibleFall() == null ? 0 : this.getInvisibleFall()
						.hashCode());
		result = 37 * result
				+ (getRota() == null ? 0 : this.getRota().hashCode());
		result = 37 * result
				+ (getPos() == null ? 0 : this.getPos().hashCode());
		result = 37
				* result
				+ (getJumpSceneId() == null ? 0 : this.getJumpSceneId()
						.hashCode());
		result = 37 * result
				+ (getIconName() == null ? 0 : this.getIconName().hashCode());
		result = 37
				* result
				+ (getIconOffset() == null ? 0 : this.getIconOffset()
						.hashCode());
		return result;
	}

}