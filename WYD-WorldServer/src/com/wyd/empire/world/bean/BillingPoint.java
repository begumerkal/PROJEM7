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
 * The persistent class for the tab_playersinconsortia database table.
 * 
 * @author BEA Workshop
 */
@Entity()
@Table(name = "tab_billing_point")
public class BillingPoint implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	private Integer id;
	private float price; // 价格
	private int itemId; // 物品id
	private int shopType;
	private int type; // 数量类型0:天数 1:个数,2:月卡
	private int count; // 数量
	private String smsCode; // 短代号
	private String remark1; // 短代说明支付
	private String remark2; // 非短代支付说明
	private int channelId; // 渠道id：-1为通用否则为某渠道特有
	private String extensionInfo; // 扩展参数【默认空字符串】格式如：CPID:741511,CPServiceID:651110072600

	public BillingPoint() {
	}

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
	@Column(name = "price", precision = 10)
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Basic()
	@Column(name = "item_id", precision = 10)
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	@Basic()
	@Column(name = "shop_type", precision = 10)
	public int getShopType() {
		return shopType;
	}

	public void setShopType(int shopType) {
		this.shopType = shopType;
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
	@Column(name = "count", precision = 10)
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Column(name = "sms_code", length = 100)
	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	@Column(name = "remark1", length = 255)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	@Column(name = "remark2", length = 255)
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	@Column(name = "channel_id")
	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	@Column(name = "extension_info", length = 255)
	public String getExtensionInfo() {
		return extensionInfo;
	}

	public void setExtensionInfo(String extensionInfo) {
		this.extensionInfo = extensionInfo;
	}

}