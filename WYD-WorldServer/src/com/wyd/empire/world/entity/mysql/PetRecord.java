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
 * 宠物记录
 * 
 * @author zengxc
 */
//
@Entity()
@Table(name = "log_petrecord")
public class PetRecord implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	public static final int GETPET = 1;// 获取宠物
	public static final int UPLEVEL = 2;// 升级
	public static final int TRAIN = 3;// 训练
	public static final int DIAMONDTRAIN = 4;// 钻石跳过CDTIME
	public static final int DIAMONCUL = 5;// 钻石培养
	public static final int GOLDCUL = 6;// 金币培养
	public static final int INHERITANCE = 7;// 传承
	private Integer id;
	private int playerId;
	private int type;
	private int level;
	private int petId;
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PetRecord)) {
			return false;
		}
		PetRecord castOther = (PetRecord) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	@Basic()
	@Column(name = "petId", length = 4)
	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}
}