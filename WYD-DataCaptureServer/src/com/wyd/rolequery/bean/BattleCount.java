package com.wyd.rolequery.bean;
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
 * The persistent class for the log_battlecount database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name="log_battlecount")
public class BattleCount  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer averageTime;
	private Integer battleMode;
	private Integer battleTimes;
	private Integer fightWithAi;
	private Date inday;
	private Integer playerNumMode;
	private Integer togetherType;

    public BattleCount() {
    }

	@Id()
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", unique=true, nullable=false, precision=10)
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Basic()
	@Column(name="averageTime", precision=10)
	public Integer getAverageTime() {
		return this.averageTime;
	}
	public void setAverageTime(Integer averageTime) {
		this.averageTime = averageTime;
	}

	@Basic()
	@Column(name="battleMode", precision=10)
	public Integer getBattleMode() {
		return this.battleMode;
	}
	public void setBattleMode(Integer battleMode) {
		this.battleMode = battleMode;
	}

	@Basic()
	@Column(name="battleTimes", precision=10)
	public Integer getBattleTimes() {
		return this.battleTimes;
	}
	public void setBattleTimes(Integer battleTimes) {
		this.battleTimes = battleTimes;
	}

	@Basic()
	@Column(name="fightWithAI", precision=10)
	public Integer getFightWithAi() {
		return this.fightWithAi;
	}
	public void setFightWithAi(Integer fightWithAi) {
		this.fightWithAi = fightWithAi;
	}

	@Basic()
	@Column(name="inday")
	public Date getInday() {
		return this.inday;
	}
	public void setInday(Date inday) {
		this.inday = inday;
	}

	@Basic()
	@Column(name="playerNumMode", precision=10)
	public Integer getPlayerNumMode() {
		return this.playerNumMode;
	}
	public void setPlayerNumMode(Integer playerNumMode) {
		this.playerNumMode = playerNumMode;
	}

	@Basic()
	@Column(name="togetherType", precision=10)
	public Integer getTogetherType() {
		return this.togetherType;
	}
	public void setTogetherType(Integer togetherType) {
		this.togetherType = togetherType;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BattleCount)) {
			return false;
		}
		BattleCount castOther = (BattleCount)other;
		return new EqualsBuilder()
			.append(this.getId(), castOther.getId())
			.isEquals();
    }
    
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
    }   

	public String toString() {
		return new ToStringBuilder(this)
			.append("id", getId())
			.toString();
	}
}