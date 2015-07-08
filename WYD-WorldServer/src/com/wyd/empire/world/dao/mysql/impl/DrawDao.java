package com.wyd.empire.world.dao.mysql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wyd.db.mysql.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.mysql.IDrawDao;
import com.wyd.empire.world.entity.mysql.DrawItem;
import com.wyd.empire.world.entity.mysql.DrawRate;
import com.wyd.empire.world.entity.mysql.DrawReward;
import com.wyd.empire.world.entity.mysql.DrawType;
import com.wyd.empire.world.entity.mysql.PlayerDraw;

/**
 * The DAO class for the TabConsortiaright entity.
 */
public class DrawDao extends UniversalDaoHibernate implements IDrawDao {
	private Map<Integer, DrawRate> drawMap = null;

	/**
	 * 初始化数据
	 */
	@SuppressWarnings("unchecked")
	public void initData() {
		if (drawMap == null) {
			drawMap = new HashMap<Integer, DrawRate>();
		} else {
			drawMap.clear();
		}
		List<DrawRate> drawRateList = getList("FROM DrawRate ", new Object[]{});
		for (DrawRate item : drawRateList) {
			drawMap.put(item.getId(), item);
		}
	}

	public DrawDao() {
		super();
	}

	/**
	 * 获得玩家的奖励物品
	 * 
	 * @param playerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DrawItem> getRewardByPlayerId(int playerId, int type) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + DrawItem.class.getSimpleName() + " as di WHERE 1 = 1 ");
		hql.append(" AND di.playerId = ? ");
		values.add(playerId);
		hql.append(" AND di.type = ? ");
		values.add(type);
		hql.append(" ORDER by di.drawReward.rewardType asc");
		List<DrawItem> list = getList(hql.toString(), values.toArray());
		if (list.size() == 0) {
			list = refreshDrawItem(playerId, type, 0);
		}
		return getList(hql.toString(), values.toArray());
	}

	/**
	 * 根据typeId获得抽奖类型
	 * 
	 * @param typeId
	 * @return
	 */
	public DrawType getDrawItemByItemId(int typeId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + DrawType.class.getSimpleName() + " as dt WHERE 1 = 1 ");
		hql.append(" AND dt.itemId = ? ");
		values.add(typeId);
		return (DrawType) getUniqueResult(hql.toString(), values.toArray());
	}

	/**
	 * 刷新抽奖列表
	 * 
	 * @param player
	 */
	@SuppressWarnings("unchecked")
	public List<DrawItem> refreshDrawItem(int playerId, int typeId, int refreshMark) {
		deleteDrawItem(playerId, typeId, refreshMark);
		StringBuilder hql = new StringBuilder();
		List<DrawItem> list = new ArrayList<DrawItem>();
		// 添加新的记录
		int randomNum = ServiceUtils.getRandomNum(0, 10000);
		hql.append(" FROM " + DrawReward.class.getSimpleName() + " as dr WHERE 1 = 1 ");
		hql.append(" AND  startRate <=? and ?<endRate");
		hql.append(" AND  drawType = ?");
		hql.append(" ORDER by dr.rewardType asc");
		List<DrawReward> drList = getList(hql.toString(), new Object[]{randomNum, randomNum, typeId});
		for (DrawReward dr : drList) {
			DrawItem di = new DrawItem();
			di.setPlayerId(playerId);
			di.setDrawReward(dr);
			di.setType(typeId);
			this.save(di);
			list.add(di);
		}

		return list;
	}

	/**
	 * 删除抽奖物品
	 * 
	 * @param playerId
	 * @param typeId
	 * @param refreshMark
	 */
	public void deleteDrawItem(int playerId, int typeId, int refreshMark) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("DELETE FROM DrawItem AS ex WHERE 1 = 1 ");
		hql.append(" AND ex.playerId = ?");
		values.add(playerId);
		if (refreshMark != 1) {
			hql.append(" AND ex.type = ?");
			values.add(typeId);
		}
		this.execute(hql.toString(), values.toArray());
	}

	/**
	 * 获得玩家奖励物品
	 * 
	 * @param playerId
	 * @param starNum
	 * @param typeId
	 * @return
	 */
	public DrawItem getDrawRewardByPlayerIdAndStarNum(int playerId, int starNum, int typeId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + DrawItem.class.getSimpleName() + " as di WHERE 1 = 1 ");
		hql.append(" AND di.drawReward.rewardType = ? ");
		values.add(starNum);
		hql.append(" AND di.playerId = ? ");
		values.add(playerId);
		hql.append(" AND di.type = ? ");
		values.add(typeId);
		return (DrawItem) getUniqueResult(hql.toString(), values.toArray());
	}

	/**
	 * 更新已抽中的物品
	 * 
	 * @param playerId
	 * @param typeId
	 * @param starNum
	 */
	public void updateDrawItem(int playerId, int typeId, int starNum, DrawItem di) {
		StringBuilder hql = new StringBuilder();
		int randomNum = ServiceUtils.getRandomNum(0, 10000);
		hql.append(" FROM " + DrawReward.class.getSimpleName() + " as dr WHERE 1 = 1 ");
		hql.append(" AND  startRate <=? and ?<endRate");
		hql.append(" AND  rewardType = ?");
		hql.append(" AND  drawType = ?");
		DrawReward dr = (DrawReward) getUniqueResult(hql.toString(), new Object[]{randomNum, randomNum, starNum, typeId});
		hql.setLength(0);

		// 更新物品
		di.setDrawReward(dr);

		this.update(di);
	}

	public DrawRate getDrawRateById(int id) {
		return drawMap.get(id);
	}

	/**
	 * 获得玩家抽奖的记录
	 * 
	 * @param playerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PlayerDraw getPlayerDrawByPlayerId(int playerId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + PlayerDraw.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND playerId = ? ");
		values.add(playerId);
		// return (PlayerDraw) getUniqueResult(hql.toString(),
		// values.toArray());
		// getUniqueResult 转 getList
		List<PlayerDraw> list = this.getList(hql.toString(), values.toArray());
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

}