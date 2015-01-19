package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.MonthCard;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IMonthCardService extends UniversalManager {

	/**
	 * 获取所有月卡列表
	 * 
	 * @return
	 */
	public List<MonthCard> getAllMonthCard();

}