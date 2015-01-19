package com.wyd.empire.world.item;

public class RewardItemsVo {
	private int itemId;
	private String itemName;
	private String itemIcon;
	private int days;
	private int count;
	private int ownerId;
	private boolean isDiamond;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemIcon() {
		return itemIcon;
	}

	public void setItemIcon(String itemIcon) {
		this.itemIcon = itemIcon;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public boolean isDiamond() {
		return isDiamond;
	}

	public void setDiamond(boolean isDiamond) {
		this.isDiamond = isDiamond;
	}
}
