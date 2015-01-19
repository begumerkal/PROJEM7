package com.wyd.empire.world.bean;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 微博分享表
 * 
 * @author zengxc
 */
@Entity()
@Table(name = "tab_share")
public class Share implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id; //
	private int shareType;
	private int triggerType;
	private String target;
	private String content;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic()
	@Column(name = "share_type", nullable = false)
	public int getShareType() {
		return shareType;
	}

	public void setShareType(int shareType) {
		this.shareType = shareType;
	}

	@Basic()
	@Column(name = "trigger_type", nullable = false)
	public int getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(int triggerType) {
		this.triggerType = triggerType;
	}

	@Basic()
	@Column(name = "target", nullable = false, length = 50)
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Basic()
	@Column(name = "share_desc", nullable = false, length = 150)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}