package com.wyd.empire.world.dao;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.world.bean.SpreeGift;

public interface ISpreeGiftDao extends UniversalDao {

	/**
	 * 返回许愿结果
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