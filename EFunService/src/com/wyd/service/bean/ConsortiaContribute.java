package com.wyd.service.bean;
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The persistent class for the tab_playersinconsortia database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name="tab_consortiacontribute")
public class ConsortiaContribute  implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer contribute;
	private Integer discontribute;
	private Player player;
	private Consortia consortia;
	private Integer everydayAdd;

    public ConsortiaContribute() {
        player = new Player();
        consortia = new Consortia();
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
	@Column(name="contribute", precision=10)
	public Integer getContribute() {
		return this.contribute;
	}
	public void setContribute(Integer contribute) {
		this.contribute = contribute;
	}

	@Basic()
	@Column(name="discontribute", precision=10)
	public Integer getDiscontribute() {
		return this.discontribute;
	}
	public void setDiscontribute(Integer discontribute) {
		this.discontribute = discontribute;
	}

	//bi-directional many-to-one association to Player
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="playerId", referencedColumnName="id")
	public Player getPlayer() {
		return this.player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

	//bi-directional many-to-one association to Consortia
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="consortiaId", referencedColumnName="id")
	public Consortia getConsortia() {
		return this.consortia;
	}
	public void setConsortia(Consortia consortia) {
		this.consortia = consortia;
	}
	
	@Basic()
	@Column(name="everydayAdd", precision=10)
	public Integer getEverydayAdd() {
		return everydayAdd;
	}

	public void setEverydayAdd(Integer everydayAdd) {
		this.everydayAdd = everydayAdd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ConsortiaContribute)) {
			return false;
		}
		ConsortiaContribute castOther = (ConsortiaContribute)other;
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