package com.wyd.empire.world.entity.mysql;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the tab_active_reward database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_button_info")
public class ButtonInfo implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;//
	private int buttonId;// 弹弹岛相关按钮控制信息ID
	private String buttonIcon;// 按钮对应的图标
	private byte buttonType;// 按钮类型
							// \r\n0主界面建筑按钮，\r\n1主界面左侧按钮，\r\n2主界面中部按钮，\r\n3主界面右侧按钮。\r\n4活动类型按钮。
	private int buttonStatus1Level;// 按钮状态 按钮显示不可用需求等级
	private int buttonStatus2Level;// 按钮状态 按钮显示可用返回提示需求等级
	private int buttonStatus3Level;// 按钮状态 按钮显示可用功能开放需求等级
	private int buttonSort;// 按钮的默认排序值
	private String buttonRemark;// 按钮相关的提示信息
	private String areaId;// 所属分区
	private String buttonName;// 按钮对应的名称（GM工具做展示使用，客户端此字段无效）

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
	@Column(name = "button_id", precision = 10)
	public int getButtonId() {
		return buttonId;
	}

	public void setButtonId(int buttonId) {
		this.buttonId = buttonId;
	}

	@Basic()
	@Column(name = "button_icon", length = 100)
	public String getButtonIcon() {
		return buttonIcon;
	}

	public void setButtonIcon(String buttonIcon) {
		this.buttonIcon = buttonIcon;
	}

	@Basic()
	@Column(name = "button_type", precision = 3)
	public byte getButtonType() {
		return buttonType;
	}

	public void setButtonType(byte buttonType) {
		this.buttonType = buttonType;
	}

	@Basic()
	@Column(name = "button_status1_level", precision = 6)
	public int getButtonStatus1Level() {
		return buttonStatus1Level;
	}

	public void setButtonStatus1Level(int buttonStatus1Level) {
		this.buttonStatus1Level = buttonStatus1Level;
	}

	@Basic()
	@Column(name = "button_status2_level", precision = 6)
	public int getButtonStatus2Level() {
		return buttonStatus2Level;
	}

	public void setButtonStatus2Level(int buttonStatus2Level) {
		this.buttonStatus2Level = buttonStatus2Level;
	}

	@Basic()
	@Column(name = "button_status3_level", precision = 6)
	public int getButtonStatus3Level() {
		return buttonStatus3Level;
	}

	public void setButtonStatus3Level(int buttonStatus3Level) {
		this.buttonStatus3Level = buttonStatus3Level;
	}

	@Basic()
	@Column(name = "button_sort", precision = 10)
	public int getButtonSort() {
		return buttonSort;
	}

	public void setButtonSort(int buttonSort) {
		this.buttonSort = buttonSort;
	}

	@Basic()
	@Column(name = "button_remark", length = 255)
	public String getButtonRemark() {
		return buttonRemark;
	}

	public void setButtonRemark(String buttonRemark) {
		this.buttonRemark = buttonRemark;
	}

	@Basic()
	@Column(name = "area_id", length = 10)
	public String getAreaId() {
		return this.areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Basic()
	@Column(name = "button_name", length = 50)
	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

}