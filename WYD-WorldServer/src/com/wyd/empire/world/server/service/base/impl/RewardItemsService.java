package com.wyd.empire.world.server.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.FullServiceReward;
import com.wyd.empire.world.bean.RewardItems;
import com.wyd.empire.world.bean.Tips;
import com.wyd.empire.world.dao.IRewardItemsDao;
import com.wyd.empire.world.item.RewardItemsVo;
import com.wyd.empire.world.server.service.base.IRewardItemsService;
import com.wyd.empire.world.server.service.base.IToolsService;

/**
 * The service class for the TabTool entity.
 */
public class RewardItemsService extends UniversalManagerImpl implements IRewardItemsService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IRewardItemsDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "RewardItemsService";

	public RewardItemsService() {
		super();
	}

	/**
	 * Returns the singleton <code>IToolService</code> instance.
	 */
	public static IToolsService getInstance(ApplicationContext context) {
		return (IToolsService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IRewardItemsDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IRewardItemsDao getDao() {
		return this.dao;
	}

	/**
	 * 随机获取8件奖励物品
	 * 
	 * @return
	 */
	public List<RewardItemsVo> getRewardItems() {
		List<RewardItemsVo> rivList = new ArrayList<RewardItemsVo>();
		RewardItemsVo riv;
		for (int i = 0; i < 8; i++) {
			int random = (int) (Math.random() * 10000);
			List<RewardItems> rewardItemsList = this.dao.getRewardItemByRandom(random);
			for (RewardItems rewardItems : rewardItemsList) {
				riv = new RewardItemsVo();
				rivList.add(riv);
				riv.setItemId(rewardItems.getShopItem().getId());
				riv.setItemName(rewardItems.getShopItem().getName());
				riv.setItemIcon(rewardItems.getShopItem().getIcon());
				riv.setDays(rewardItems.getDays());
				riv.setCount(rewardItems.getCount());
			}
		}
		return rivList;
	}

	/**
	 * 获得所有提示
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Tips> getTips() {
		return dao.getAll(Tips.class);
	}

	/**
	 * 获取提示分页列表
	 * 
	 * @param pageNum
	 * @param mark
	 * @return
	 */
	public PageList getTipsList(int pageNum, int pageSize) {
		return dao.getTipsList(pageNum, pageSize);
	}

	/**
	 * 获取通用物品奖励列表
	 * 
	 * @return
	 */
	public List<RewardItems> getRewardItemsList() {
		List<RewardItems> list = dao.getRewardItemsList();
		if (list != null && list.size() > 0) {
			for (RewardItems rewardItems : list) {
				Hibernate.initialize(rewardItems.getShopItem());
			}
		}
		return list;
	}

	/**
	 * 根据id查询通用物品奖励
	 * 
	 * @param id
	 * @return
	 */
	public RewardItems getRewardItemsById(int id) {
		RewardItems itmes = dao.getRewardItemsById(id);
		if (itmes != null) {
			Hibernate.initialize(itmes.getShopItem());
		}
		return itmes;
	}

	/**
	 * 查询出所有全服物品奖励
	 * 
	 * @return
	 */
	public List<FullServiceReward> findAllFullServiceReward() {
		return dao.findAllFullServiceReward();
	}

	/**
	 * 获取未发送的全服物品奖励
	 * 
	 * @return
	 */
	public List<FullServiceReward> findFullServiceRewardByStatus() {
		return dao.findFullServiceRewardByStatus();
	}

	/**
	 * 根据多个ID值删除全服物品奖励
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByIds(String ids) {
		dao.deleteByIds(ids);
	}
}