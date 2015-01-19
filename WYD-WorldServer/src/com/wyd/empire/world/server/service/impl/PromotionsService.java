package com.wyd.empire.world.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.world.bean.Promotions;
import com.wyd.empire.world.bean.ShopItemsPrice;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.server.service.base.IPromotionsService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class PromotionsService implements Runnable {
	private Logger errorLog = Logger.getLogger(PromotionsService.class);
	IPromotionsService service;
	List<PromotionVo> promotionsBoyList;
	List<PromotionVo> promotionsGirList;
	long refreshTime = 0;

	public PromotionsService(IPromotionsService service) {
		this.service = service;
	}

	public IPromotionsService getIPromotionsService() {
		return service;
	}

	public void start() {
		Thread t = new Thread(this);
		t.setName("PromotionsService-Thread");
		t.start();
	}

	@Override
	public void run() {
		try {
			initPromotionsList();
			waitTo();
			while (true) {
				try {
					initPromotionsList();
				} catch (Exception e) {
					e.printStackTrace();
					errorLog.error(e, e);
				}
				refreshTime += DateUtil.DAY_MSELS;
				Thread.sleep(DateUtil.DAY_MSELS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorLog.error(e, e);
		}
	}

	private void waitTo() throws InterruptedException {
		Calendar now = Calendar.getInstance();
		Calendar yesterday = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
		yesterday.add(Calendar.DAY_OF_MONTH, 1);
		refreshTime = yesterday.getTimeInMillis();
		long wait = refreshTime - System.currentTimeMillis();
		if (wait > 0) {
			Thread.sleep(wait);
		}
	}

	/**
	 * 初始化促销列表
	 */
	public void initPromotionsList() {
		List<Promotions> pBoyl = service.getPromotionsList(Common.PLAYER_SEX_M);
		List<Promotions> pGirl = service.getPromotionsList(Common.PLAYER_SEX_W);
		List<PromotionVo> pBoyvl = new ArrayList<PromotionVo>();
		List<PromotionVo> pGirvl = new ArrayList<PromotionVo>();
		PromotionVo promotionVo;
		for (Promotions promotions : pBoyl) {
			promotionVo = new PromotionVo();
			List<ShopItemsPrice> sipList = ServiceManager.getManager().getiShopItemsPriceService()
					.getItemPrice(promotions.getShopItem().getId());
			if (promotions.getGold() != -1) {
				ShopItemsPrice shopItemsPrice = new ShopItemsPrice();
				shopItemsPrice.setId(-1);
				shopItemsPrice.setCostType((byte) 1);
				shopItemsPrice.setCostUseGold(promotions.getGold());
				shopItemsPrice.setCostUseTickets(0);
				shopItemsPrice.setCount(promotions.getCount());
				shopItemsPrice.setDays(promotions.getDays());
				shopItemsPrice.setShopItem(promotions.getShopItem());
				sipList.clear();
				sipList.add(shopItemsPrice);
			}
			if (null == sipList || sipList.size() < 1)
				continue;
			pBoyvl.add(promotionVo);
			promotionVo.setPromotions(promotions);
			promotionVo.setDiscount(promotions.getDiscount());
			promotionVo.setSipList(sipList);
			List<int[]> idList = new ArrayList<int[]>();
			promotionVo.setPrices(idList);
			for (ShopItemsPrice sip : sipList) {
				if (sip.getCostType() > 1)
					continue;
				int[] data = new int[3];
				idList.add(data);
				data[0] = sip.getCostType();
				data[1] = (int) (sip.getCostUseTickets() * (promotions.getDiscount() / 10000f));
				data[2] = (int) (sip.getCostUseGold() * (promotions.getDiscount() / 10000f));
			}
			if (promotions.getPersonal() != null && promotions.getPersonal().intValue() == 1) {
				promotionVo.setPersonal(true);
			} else {
				promotionVo.setPersonal(false);
			}
			promotionVo.setMaxCount(promotions.getQuantity());
		}
		promotionsBoyList = pBoyvl;
		for (Promotions promotions : pGirl) {
			promotionVo = new PromotionVo();
			List<ShopItemsPrice> sipList = ServiceManager.getManager().getiShopItemsPriceService()
					.getItemPrice(promotions.getShopItem().getId());
			if (promotions.getGold() != -1) {
				ShopItemsPrice shopItemsPrice = new ShopItemsPrice();
				shopItemsPrice.setId(-1);
				shopItemsPrice.setCostType((byte) 1);
				shopItemsPrice.setCostUseGold(promotions.getGold());
				shopItemsPrice.setCostUseTickets(0);
				shopItemsPrice.setCount(promotions.getCount());
				shopItemsPrice.setDays(promotions.getDays());
				shopItemsPrice.setShopItem(promotions.getShopItem());
				sipList.clear();
				sipList.add(shopItemsPrice);
			}
			if (null == sipList || sipList.size() < 1)
				continue;
			pGirvl.add(promotionVo);
			promotionVo.setPromotions(promotions);
			promotionVo.setDiscount(promotions.getDiscount());
			promotionVo.setSipList(sipList);
			List<int[]> idList = new ArrayList<int[]>();
			promotionVo.setPrices(idList);
			for (ShopItemsPrice sip : sipList) {
				if (sip.getCostType() > 1)
					continue;
				int[] data = new int[3];
				idList.add(data);
				data[0] = sip.getCostType();
				data[1] = (int) (sip.getCostUseTickets() * (promotions.getDiscount() / 10000f));
				data[2] = (int) (sip.getCostUseGold() * (promotions.getDiscount() / 10000f));
			}
			if (promotions.getPersonal() != null && promotions.getPersonal().intValue() == 1) {
				promotionVo.setPersonal(true);
			} else {
				promotionVo.setPersonal(false);
			}
			promotionVo.setMaxCount(promotions.getQuantity());
		}
		promotionsGirList = pGirvl;
	}

	public List<PromotionVo> getPromotionsList(int sex) {
		if (Common.PLAYER_SEX_M == sex) {
			return promotionsBoyList;
		} else {
			return promotionsGirList;
		}
	}

	public PromotionVo getPromotionVo(int itemId, int sex) {
		List<PromotionVo> promotionsList;
		if (0 == sex) {
			promotionsList = promotionsBoyList;
		} else {
			promotionsList = promotionsGirList;
		}
		List<PromotionVo> pvl = new ArrayList<PromotionVo>(promotionsList);
		for (PromotionVo pv : pvl) {
			if (pv.getPromotions().getShopItem().getId().intValue() == itemId) {
				return pv;
			}
		}
		return null;
	}

	public ShopItemsPrice getPrice(PromotionVo promotionVo, int priceId) {
		for (ShopItemsPrice price : promotionVo.getSipList()) {
			if (priceId == price.getId().intValue()) {
				return price;
			}
		}
		return null;
	}

	public class PromotionVo {
		private Promotions promotions;
		private int discount; // 折扣
		private List<int[]> prices;
		private List<ShopItemsPrice> sipList;
		private boolean isPersonal;
		private int maxCount;

		public Promotions getPromotions() {
			return promotions;
		}

		public void setPromotions(Promotions promotions) {
			this.promotions = promotions;
		}

		public int getDiscount() {
			return discount;
		}

		public void setDiscount(int discount) {
			this.discount = discount;
		}

		public List<int[]> getPrices() {
			return prices;
		}

		public void setPrices(List<int[]> prices) {
			this.prices = prices;
		}

		public List<ShopItemsPrice> getSipList() {
			return sipList;
		}

		public void setSipList(List<ShopItemsPrice> sipList) {
			this.sipList = sipList;
		}

		public boolean isPersonal() {
			return isPersonal;
		}

		public void setPersonal(boolean isPersonal) {
			this.isPersonal = isPersonal;
		}

		public int getMaxCount() {
			return maxCount;
		}

		public void setMaxCount(int maxCount) {
			this.maxCount = maxCount;
		}
	}

	public int getRemaTime() {
		long time = refreshTime - System.currentTimeMillis();
		return (int) (time / 1000);
	}
}
