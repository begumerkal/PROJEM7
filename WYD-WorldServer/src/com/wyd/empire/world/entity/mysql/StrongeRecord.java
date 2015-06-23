package com.wyd.empire.world.entity.mysql;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author BEA Workshop
 */
@Entity()
@Table(name = "log_strongerecord")
public class StrongeRecord implements Serializable {
	/** 强化成功 **/
	public static final int STRONGE_SUCCESS = 0;
	/** 合成石头成功 **/
	public static final int MERGE_SUCCESS = 1;
	/** 洗练 **/
	public static final int PURIFY = 2;
	/** 装备属性转化 **/
	public static final int CHANGE_ATTRIBUTE = 3;
	/** 装备拆卸 **/
	public static final int TAKEOFF = 4;
	/** 装备打孔 **/
	public static final int OPEN = 5;
	/** 装备镶嵌 **/
	public static final int MOSAIC = 6;
	/** 升星成功 **/
	public static final int Upgrade_success = 7;
	/** 碎片合成成功 **/
	public static final int MergeScrap_success = 8;
	/** 技能锁拆卸 **/
	public static final int UNLOCK_SKILL = 9;
	/** 强化失败 **/
	public static final int STRONGE_FAIL = -1;
	/** 合成石头失败 **/
	public static final int MERGE_FAIL = -2;
	/** 升星失败 **/
	public static final int Upgrade_fail = -3;
	/** 碎片合成失败 **/
	public static final int MergeScrap_fail = -8;
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private int playerId;
	private int type; // -2合成失败,-1强化失败,0是强化成功,1是合成,2洗练,7升星,+8碎片合成成功,-8碎片合成失败

	private int level;
	private int itemId;
	private Date createTime;
	private String remark;

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
	@Column(name = "type", precision = 10)
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Basic()
	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Basic()
	@Column(name = "playerId", precision = 10)
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
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
	@Column(name = "remark", length = 20)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Basic()
	@Column(name = "itemId", precision = 10)
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof StrongeRecord)) {
			return false;
		}
		StrongeRecord castOther = (StrongeRecord) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
}