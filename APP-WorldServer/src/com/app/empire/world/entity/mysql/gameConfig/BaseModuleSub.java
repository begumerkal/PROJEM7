package com.app.empire.world.entity.mysql.gameConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BaseModuleSub entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "base_module_sub", catalog = "game3")
public class BaseModuleSub implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer moduleId;
	private String subModuleName;
	private Integer isMain;
	private Integer location;
	private Integer openType;
	private String openModule;
	private Integer noviceId;
	private String useNumber;
	private String buyNumber;
	private String initdata;
	private String className;
	private String testModlue;
	private Integer locationId;
	private String info;
	private Integer openOrder;
	private String openInfo;

	// Constructors

	/** default constructor */
	public BaseModuleSub() {
	}

	/** full constructor */
	public BaseModuleSub(Integer id, Integer moduleId, String subModuleName,
			Integer isMain, Integer location, Integer openType,
			String openModule, Integer noviceId, String useNumber,
			String buyNumber, String initdata, String className,
			String testModlue, Integer locationId, String info,
			Integer openOrder, String openInfo) {
		this.id = id;
		this.moduleId = moduleId;
		this.subModuleName = subModuleName;
		this.isMain = isMain;
		this.location = location;
		this.openType = openType;
		this.openModule = openModule;
		this.noviceId = noviceId;
		this.useNumber = useNumber;
		this.buyNumber = buyNumber;
		this.initdata = initdata;
		this.className = className;
		this.testModlue = testModlue;
		this.locationId = locationId;
		this.info = info;
		this.openOrder = openOrder;
		this.openInfo = openInfo;
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

	@Column(name = "module_id", nullable = false)
	public Integer getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	@Column(name = "sub_module_name", nullable = false, length = 10)
	public String getSubModuleName() {
		return this.subModuleName;
	}

	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
	}

	@Column(name = "isMain", nullable = false)
	public Integer getIsMain() {
		return this.isMain;
	}

	public void setIsMain(Integer isMain) {
		this.isMain = isMain;
	}

	@Column(name = "location", nullable = false)
	public Integer getLocation() {
		return this.location;
	}

	public void setLocation(Integer location) {
		this.location = location;
	}

	@Column(name = "open_type", nullable = false)
	public Integer getOpenType() {
		return this.openType;
	}

	public void setOpenType(Integer openType) {
		this.openType = openType;
	}

	@Column(name = "open_module", nullable = false, length = 256)
	public String getOpenModule() {
		return this.openModule;
	}

	public void setOpenModule(String openModule) {
		this.openModule = openModule;
	}

	@Column(name = "novice_id", nullable = false)
	public Integer getNoviceId() {
		return this.noviceId;
	}

	public void setNoviceId(Integer noviceId) {
		this.noviceId = noviceId;
	}

	@Column(name = "use_number", nullable = false, length = 1024)
	public String getUseNumber() {
		return this.useNumber;
	}

	public void setUseNumber(String useNumber) {
		this.useNumber = useNumber;
	}

	@Column(name = "buy_number", nullable = false, length = 256)
	public String getBuyNumber() {
		return this.buyNumber;
	}

	public void setBuyNumber(String buyNumber) {
		this.buyNumber = buyNumber;
	}

	@Column(name = "initdata", nullable = false, length = 300)
	public String getInitdata() {
		return this.initdata;
	}

	public void setInitdata(String initdata) {
		this.initdata = initdata;
	}

	@Column(name = "class_name", nullable = false, length = 30)
	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name = "test_modlue", nullable = false, length = 32)
	public String getTestModlue() {
		return this.testModlue;
	}

	public void setTestModlue(String testModlue) {
		this.testModlue = testModlue;
	}

	@Column(name = "location_id", nullable = false)
	public Integer getLocationId() {
		return this.locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	@Column(name = "info", nullable = false, length = 1000)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Column(name = "open_order", nullable = false)
	public Integer getOpenOrder() {
		return this.openOrder;
	}

	public void setOpenOrder(Integer openOrder) {
		this.openOrder = openOrder;
	}

	@Column(name = "open_info", nullable = false, length = 128)
	public String getOpenInfo() {
		return this.openInfo;
	}

	public void setOpenInfo(String openInfo) {
		this.openInfo = openInfo;
	}

}