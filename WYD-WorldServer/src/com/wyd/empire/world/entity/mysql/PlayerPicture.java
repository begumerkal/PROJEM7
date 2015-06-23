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
import javax.persistence.Transient;

@Entity()
@Table(name = "tab_player_picture")
public class PlayerPicture implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private int playerId; // 玩家id
	private Integer age; // 年龄
	private int constellation; // 星座
	private String personContext; // 个性签名
	private byte[] signature; // 个性签名（byte）
	private String pictureUrlPass; // 审核通过地址
	private String pictureUrlTest; // 待审核头像地址
	private Date updateTime; // 最后更新时间
	private Date auditedTime; // 审核通过时间

	public PlayerPicture() {

	}

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic()
	@Column(name = "age")
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Basic()
	@Column(name = "constellation")
	public int getConstellation() {
		return constellation;
	}

	public void setConstellation(int constellation) {
		this.constellation = constellation;
	}

	@Basic()
	@Column(name = "person_context")
	public String getPersonContext() {
		return personContext;
	}

	public void setPersonContext(String personContext) {
		personContext = personContext == null ? "" : personContext;
		this.signature = personContext.getBytes();
	}

	@Basic()
	@Column(name = "playerid")
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	@Basic()
	@Column(name = "picture_url_pass")
	public String getPictureUrlPass() {
		return pictureUrlPass == null ? "" : pictureUrlPass;
	}

	public void setPictureUrlPass(String pictureUrlPass) {
		this.pictureUrlPass = pictureUrlPass;
	}

	@Basic()
	@Column(name = "picture_url_test")
	public String getPictureUrlTest() {
		return pictureUrlTest == null ? "" : pictureUrlTest;
	}

	public void setPictureUrlTest(String pictureUrlTest) {
		this.pictureUrlTest = pictureUrlTest;
	}

	@Basic()
	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Basic()
	@Column(name = "audited_time")
	public Date getAuditedTime() {
		return auditedTime;
	}

	public void setAuditedTime(Date auditedTime) {
		this.auditedTime = auditedTime;
	}

	@Basic()
	@Column(name = "signature")
	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	@Transient
	public String getSignatureContent() {
		personContext = personContext == null ? "" : personContext;
		return signature == null ? personContext : new String(signature);
	}

}
