package com.wyd.empire.world.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家卡阵.
 * 
 * @author zengxc
 */

public class PlayerCards {
	private PlayerItemsFromShop jk; // 金卡
	private PlayerItemsFromShop mk; // 木卡
	private PlayerItemsFromShop sk; // 水卡
	private PlayerItemsFromShop hk; // 火卡
	private PlayerItemsFromShop tk; // 土卡

	public PlayerItemsFromShop getJk() {
		return jk;
	}

	public void setJk(PlayerItemsFromShop jk) {
		this.jk = jk;
	}

	public PlayerItemsFromShop getMk() {
		return mk;
	}

	public void setMk(PlayerItemsFromShop mk) {
		this.mk = mk;
	}

	public PlayerItemsFromShop getSk() {
		return sk;
	}

	public void setSk(PlayerItemsFromShop sk) {
		this.sk = sk;
	}

	public PlayerItemsFromShop getHk() {
		return hk;
	}

	public void setHk(PlayerItemsFromShop hk) {
		this.hk = hk;
	}

	public PlayerItemsFromShop getTk() {
		return tk;
	}

	public void setTk(PlayerItemsFromShop tk) {
		this.tk = tk;
	}

	/**
	 * 获取shopItemId
	 * 
	 * @return
	 */

	public int getCardJId() {
		return jk == null ? 0 : jk.getShopItem().getId();
	}

	public int getCardMId() {
		return mk == null ? 0 : mk.getShopItem().getId();
	}

	public int getCardSId() {
		return sk == null ? 0 : sk.getShopItem().getId();
	}

	public int getCardHId() {
		return hk == null ? 0 : hk.getShopItem().getId();
	}

	public int getCardTId() {
		return tk == null ? 0 : tk.getShopItem().getId();
	}

	/**
	 * 获取金卡所属套卡ID
	 * 
	 * @return
	 */

	public int getGroupJ() {
		return jk == null ? 0 : jk.getShopItem().getTkId();
	}

	/**
	 * 获取木卡所属套卡ID
	 * 
	 * @return
	 */

	public int getGroupM() {
		return mk == null ? 0 : mk.getShopItem().getTkId();
	}

	/**
	 * 获取水卡所属套卡ID
	 * 
	 * @return
	 */

	public int getGroupS() {
		return sk == null ? 0 : sk.getShopItem().getTkId();
	}

	/**
	 * 获取金火所属套卡ID
	 * 
	 * @return
	 */

	public int getGroupH() {
		return hk == null ? 0 : hk.getShopItem().getTkId();
	}

	/**
	 * 获取土卡所属套卡ID
	 * 
	 * @return
	 */

	public int getGroupT() {
		return tk == null ? 0 : tk.getShopItem().getTkId();
	}

	/**
	 * 把卡阵上的卡放到集合里
	 * 
	 * @return
	 */
	public List<PlayerItemsFromShop> toList() {
		List<PlayerItemsFromShop> cardList = new ArrayList<PlayerItemsFromShop>();
		cardList.add(getJk());
		cardList.add(getMk());
		cardList.add(getSk());
		cardList.add(getHk());
		cardList.add(getTk());
		return cardList;
	}

}
