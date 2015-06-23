package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.dao.IRechargeDao;
import com.wyd.empire.world.entity.mysql.Channel;
import com.wyd.empire.world.entity.mysql.Recharge;
import com.wyd.empire.world.entity.mysql.RechargeCrit;

/**
 * The DAO class for the TabConsortiaright entity.
 */
public class RechargeDao extends UniversalDaoHibernate implements IRechargeDao {
	public RechargeDao() {
		super();
	}

	public Recharge getRechargeByCID(String cid) {
		return (Recharge) getClassObj("from Recharge where commodityId=?", new Object[]{cid});
	}

	/**
	 * 根据钻石数查询出充值对象
	 * 
	 * @param amount
	 *            钻石数
	 * @return
	 */
	public Recharge getRechargeByAmount(int amount) {
		String hql = " FROM " + Recharge.class.getSimpleName() + " WHERE number = ? ";
		return (Recharge) this.getList(hql, new Object[]{amount}).get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Recharge> getRechargeListByChannel(int channelId) {
		String hql = " FROM " + Recharge.class.getSimpleName() + " WHERE channelId = ? ";
		return this.getList(hql, new Object[]{channelId});
	}

	public String getDidByChannel(int channelId) {
		String hql = "SELECT key FROM " + Channel.class.getSimpleName() + " WHERE code = ? ";
		return this.getClassObj(hql, new Object[]{channelId}).toString();
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
		return (Recharge) this.getUniqueResult(" FROM Recharge WHERE commodityId = ? AND channelId = ? ",
				new Object[]{productId, channelId});
	}

	/**
	 * 根据充值钻石数获得充值暴击率
	 * 
	 * @param amount
	 *            充值钻石数
	 * @return
	 */
	public RechargeCrit findByAmount(int amount) {
		String hql = " FROM " + RechargeCrit.class.getSimpleName() + " WHERE ? BETWEEN lowNum and highNum ";
		return (RechargeCrit) this.getUniqueResult(hql, new Object[]{amount});
	}

	/**
	 * 获取充值暴击概率分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList getRechargeCritList(int pageNum, int pageSize) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + RechargeCrit.class.getSimpleName() + " ORDER BY id asc ");
		String countHql = "SELECT COUNT(id) " + hql.toString();
		return getPageList(hql.toString(), countHql, values.toArray(), pageNum, pageSize);
	}

	/**
	 * 根据id查询Channel信息
	 * 
	 * @param channelId
	 * @return
	 */
	public Channel getChannelById(int channelId) {
		String hql = "FROM " + Channel.class.getSimpleName() + " WHERE code = ? ";
		return (Channel) this.getUniqueResult(hql, new Object[]{channelId});
	}
}