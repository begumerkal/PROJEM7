package com.wyd.empire.world.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_user", catalog = "game3")
public class BaseUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private String userName;
	private String userPwd;
	private Integer channelId;
	private Integer createtime;

	// Constructors

	/** default constructor */
	public BaseUser() {
	}

	/** full constructor */
	public BaseUser(Integer id, String userName, String userPwd,
			Integer channelId, Integer createtime) {
		this.id = id;
		this.userName = userName;
		this.userPwd = userPwd;
		this.channelId = channelId;
		this.createtime = createtime;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "user_name", nullable = false, length = 60)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_pwd", nullable = false, length = 32)
	public String getUserPwd() {
		return this.userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	@Column(name = "channel_id", nullable = false)
	public Integer getChannelId() {
		return this.channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	@Column(name = "createtime", nullable = false)
	public Integer getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Integer createtime) {
		this.createtime = createtime;
	}

}