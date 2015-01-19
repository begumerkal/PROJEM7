package com.wyd.empire.world.bean;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * The persistent class for the tab_title database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_doeveryday")
public class DoEveryDay extends AbstractBean implements Serializable {
	// default serial version id, required for serializable classes.
	private static final AtomicInteger maxid = new AtomicInteger();

	private static final int newId() {
		if (maxid.get() < 1) {
			initDbMaxId();
		}
		maxid.getAndIncrement();
		return maxid.get();
	}

	public static final void initDbMaxId() {
		Object obj = ServiceManager.getManager().getDoEveryDayService().getDao().getMaxId();
		if (obj == null) {
			maxid.set(0);
		} else {
			maxid.set(Integer.parseInt(String.valueOf(obj)));
		}
	}

	private static final long serialVersionUID = 1L;
	private Integer id;
	private byte type; // 1每日必做，2我要变强
	private byte subType;
	private String taskName;
	private String showTime;
	private String doTime;
	private String doDay;
	private String remark;
	private String buttomText;
	private int level;
	private String interfaceId;
	private String taskIcon;

	public DoEveryDay(boolean isGetid) {
		if (isGetid) {
			id = newId();
			this.cuBeanState = AbstractBean.BeanState.insert;
		} else {
			id = 0;
		}
	}

	public DoEveryDay() {
	}

	@Id()
	@GenericGenerator(name = "id", strategy = "assigned")
	@Column(name = "id", unique = true, nullable = false, precision = 10)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
		this.setModifyState();
	}

	@Basic()
	@Column(name = "type", precision = 3)
	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
		this.setModifyState();
	}

	@Basic()
	@Column(name = "sub_type", precision = 3)
	public byte getSubType() {
		return subType;
	}

	public void setSubType(byte subType) {
		this.subType = subType;
		this.setModifyState();
	}

	@Basic()
	@Column(name = "task_name", length = 50)
	public String getTaskName() {
		return taskName == null ? "" : taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
		this.setModifyState();
	}

	@Basic()
	@Column(name = "show_time", length = 50)
	public String getShowTime() {
		return showTime == null ? "" : showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
		this.setModifyState();
	}

	@Basic()
	@Column(name = "do_time", length = 50)
	public String getDoTime() {
		return doTime == null ? "" : doTime;
	}

	public void setDoTime(String doTime) {
		this.doTime = doTime;
		this.setModifyState();
	}

	@Basic()
	@Column(name = "do_day", length = 50)
	public String getDoDay() {
		return doDay == null ? "" : doDay;
	}

	public void setDoDay(String doDay) {
		this.doDay = doDay;
		this.setModifyState();
	}

	@Basic()
	@Column(name = "remark", length = 100)
	public String getRemark() {
		return remark == null ? "" : remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
		this.setModifyState();
	}

	@Basic()
	@Column(name = "buttom_text", length = 50)
	public String getButtomText() {
		return buttomText == null ? "" : buttomText;
	}

	public void setButtomText(String buttomText) {
		this.buttomText = buttomText;
		this.setModifyState();
	}

	@Basic()
	@Column(name = "level", precision = 10)
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
		this.setModifyState();
	}

	@Basic()
	@Column(name = "interface_id", length = 50)
	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
		this.setModifyState();
	}

	@Basic()
	@Column(name = "task_icon", length = 50)
	public String getTaskIcon() {
		return taskIcon == null ? "" : taskIcon;
	}

	public void setTaskIcon(String taskIcon) {
		this.taskIcon = taskIcon;
		this.setModifyState();
	}
}