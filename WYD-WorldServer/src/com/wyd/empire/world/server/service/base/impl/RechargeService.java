package com.wyd.empire.world.server.service.base.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.Channel;
import com.wyd.empire.world.bean.Recharge;
import com.wyd.empire.world.bean.RechargeCrit;
import com.wyd.empire.world.dao.IRechargeDao;
import com.wyd.empire.world.server.service.base.IRechargeService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class RechargeService extends UniversalManagerImpl implements IRechargeService {
	private static Map<Integer, RechargeCritCount> rccMap = null;
	/**
	 * The dao instance injected by Spring.
	 */
	private IRechargeDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "RechargeService";

	public RechargeService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IRechargeService getInstance(ApplicationContext context) {
		return (IRechargeService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IRechargeDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IRechargeDao getDao() {
		return this.dao;
	}

	/**
	 * 定时刷新充值暴击累积人数
	 */
	@SuppressWarnings("unchecked")
	public void initData() {
		Map<Integer, RechargeCritCount> rccMap = new HashMap<Integer, RechargeService.RechargeCritCount>();
		List<RechargeCrit> rcList = this.dao.getAll(RechargeCrit.class);
		for (RechargeCrit rc : rcList) {
			RechargeCritCount rcc = new RechargeCritCount();
			rcc.setEveryDayCount2(rc.getNum1());
			rcc.setEveryDayCount5(rc.getNum2());
			rcc.setEveryDayCount10(rc.getNum3());
			rccMap.put(rc.getId(), rcc);
		}
		RechargeService.rccMap = rccMap;
	}

	public RechargeCritCount getRechargeCritCountById(int rechargeCritId) {
		return RechargeService.rccMap.get(rechargeCritId);
	}

	/**
	 * 根据商品id返回 对应的商品
	 * 
	 * @param cid
	 * @return
	 */
	public Recharge getRechargeByCID(String cid) {
		return this.dao.getRechargeByCID(cid);
	}

	/**
	 * 验证订单是否已存在
	 * 
	 * @param oNum
	 * @return
	 */
	public boolean checkOrder(String oNum) {
		return ServiceManager.getManager().getPlayerBillService().checkOrder(oNum);
	}

	/**
	 * 根据钻石数查询出充值对象
	 * 
	 * @param amount
	 *            钻石数
	 * @return
	 */
	public Recharge getRechargeByAmount(int amount) {
		return dao.getRechargeByAmount(amount);
	}

	/**
	 * 根据渠道号获取商品列表
	 * 
	 * @param channelId
	 * @return
	 */
	public List<Recharge> getRechargeListByChannel(int channelId) {
		return dao.getRechargeListByChannel(channelId);
	}

	/**
	 * 根据渠道号获取订单验证id
	 * 
	 * @param channelId
	 * @return
	 */
	public String getDidByChannel(int channelId) {
		return dao.getDidByChannel(channelId);
	}

	/**
	 * 根据产品ID查询出商品价格对象
	 * 
	 * @param productId
	 *            产品ID
	 * @param channelId
	 *            渠道ID
	 * @return
	 */
	public Recharge findByProductId(String productId, int channelId) {
		return dao.findByProductId(productId, channelId);
	}

	/**
	 * 根据砖石数量查找充值暴击对象
	 */
	public RechargeCrit rechargeCritByAmount(int amount) {
		return dao.findByAmount(amount);
	}

	/**
	 * 获取充值暴击概率分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList getRechargeCritList(int pageNum, int pageSize) {
		return dao.getRechargeCritList(pageNum, pageSize);
	}

	public class RechargeCritCount {
		int everyDayCount2;
		int everyDayCount5;
		int everyDayCount10;

		public int getEveryDayCount2() {
			return everyDayCount2;
		}

		public void setEveryDayCount2(int everyDayCount2) {
			this.everyDayCount2 = everyDayCount2;
		}

		public int getEveryDayCount5() {
			return everyDayCount5;
		}

		public void setEveryDayCount5(int everyDayCount5) {
			this.everyDayCount5 = everyDayCount5;
		}

		public int getEveryDayCount10() {
			return everyDayCount10;
		}

		public void setEveryDayCount10(int everyDayCount10) {
			this.everyDayCount10 = everyDayCount10;
		}

		public void useEveryDayCount2() {
			everyDayCount2--;
		}

		public void useEveryDayCount5() {
			everyDayCount5--;
		}

		public void useEveryDayCount10() {
			everyDayCount10--;
		}
	}

	/**
	 * 根据id查询Channel信息
	 * 
	 * @param channelId
	 * @return
	 */
	public Channel getChannelById(int channelId) {
		return dao.getChannelById(channelId);
	}
}