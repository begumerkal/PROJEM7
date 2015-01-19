package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.SpreeGift;

public interface ISpreeGiftService extends UniversalManager {

	/**
	 * 返回开启礼包结果
	 * 
	 * @param shopItemId
	 * @param type
	 * @return
	 */
	public List<SpreeGift> getSpreeGiftResult(int shopItemId, int type);

	/**
	 * 判断是否已经有此礼包的信息
	 * 
	 * @param itmeId
	 * @return
	 */
	public boolean isExistShopItmeId(int itmeId);
}