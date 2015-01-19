package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.bean.MarryRecord;
import com.wyd.empire.world.bean.WeddingHall;
import com.wyd.empire.world.dao.IMarryDao;

public class MarryDao extends UniversalDaoHibernate implements IMarryDao {
	public MarryDao() {
		super();
	}

	/**
	 * 根据playerId获得结婚记录
	 * 
	 * @param playerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MarryRecord> getMarryRecordByPlayerId(int sexmark, int playerId, int status) {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hsql = new StringBuffer();
		hsql.append("from MarryRecord where statusMode = ? ");
		if (sexmark == 0) {
			hsql.append(" and manId = ?");
		} else {
			hsql.append(" and womanId = ?");
		}
		hsql.append(" and dhId != ?");
		values.add(status);
		values.add(playerId);
		values.add(playerId);
		return this.getList(hsql.toString(), values.toArray());
	}

	/**
	 * 根据playerId获得单个结婚记录
	 * 
	 * @param playerId
	 * @param sexmark
	 *            性别标记
	 * @param status
	 *            状态
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public MarryRecord getSingleMarryRecordByPlayerId(int sexmark, int playerId, int status) {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hsql = new StringBuffer();
		hsql.append("from MarryRecord where 1=1 ");
		if (sexmark == 0) {
			hsql.append(" and manId = ?");
		} else {
			hsql.append(" and womanId = ?");
		}
		if (status == 1) {
			hsql.append(" and statusMode > 0");
		}
		values.add(playerId);
		// return (MarryRecord) this.getUniqueResult(hsql.toString(),
		// values.toArray());
		// getUniqueResult 转 getList
		List<MarryRecord> list = this.getList(hsql.toString(), values.toArray());
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据婚姻的两个人获得对象
	 * 
	 * @param manId
	 * @param womanId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public MarryRecord getMarryRecordByTWOPlayerId(int manId, int womanId) {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hsql = new StringBuffer();
		hsql.append("from MarryRecord where  manId = ? and womanId=? ");
		values.add(manId);
		values.add(womanId);
		// return (MarryRecord) this.getUniqueResult(hsql.toString(),
		// values.toArray());
		// getUniqueResult 转 getList
		List<MarryRecord> list = this.getList(hsql.toString(), values.toArray());
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 删除多余的记录
	 * 
	 * @param playerId
	 * @return
	 */
	public void deleteMarryRecord(int sexmark, int playerId, int status) {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hsql = new StringBuffer();
		hsql.append("delete from MarryRecord where 1 = 1 ");
		if (sexmark == 0) {
			hsql.append(" and manId = ?");
		} else {
			hsql.append(" and womanId = ?");
		}
		hsql.append(" and statusMode = ?");
		values.add(playerId);
		values.add(status);
		execute(hsql.toString(), values.toArray());
	}

	/**
	 * 删除过期的求婚记录
	 * 
	 * @return
	 */
	public void deleteMarryRecordByCreateTime() {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hsql = new StringBuffer();
		hsql.append("delete from MarryRecord where 1 = 1 ");
		hsql.append(" and statusMode = 0");
		hsql.append(" and date_add(createTime, 1, HOUR) < now()");
		execute(hsql.toString(), values.toArray());
	}

	/**
	 * 离婚删除buff
	 * 
	 * @return
	 */
	public void deleteBuffRecord(int playerId) {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hsql = new StringBuffer();
		hsql.append("delete from BuffRecord where 1 = 1 ");
		hsql.append(" and playerId = ?");
		hsql.append(" and buffCode in ('mexp','mhurt')");
		values.add(playerId);
		execute(hsql.toString(), values.toArray());
	}

	public boolean checkPlayerMarriage(int playerId) {
		Object count = getObjectBySql("select count(*) from tab_marryrecord where (manId=? or womanId=?) and statusMode=2", new Object[]{
				playerId, playerId});
		int c = Integer.parseInt(count.toString());
		if (c > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 批量获取结婚记录
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MarryRecord> getMarryRecordByPlayerIds(List<Integer> ids) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" from MarryRecord where 1 = 1 ");
		for (Integer i : ids) {
			hql.append(" or (manId = ? or womanId = ?) ");
			values.add(i);
			values.add(i);
		}
		return this.getList(hql.toString(), values.toArray());
	}

	/**
	 * 获得全区的婚礼
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WeddingHall> getWeddingHallByAreaId() {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hsql = new StringBuffer();
		hsql.append("from WeddingHall where areaId = ? ");
		values.add(Server.config.getMachineCode());
		return this.getList(hsql.toString(), values.toArray());
	}

	/**
	 * 根据playerId获得结婚礼堂
	 * 
	 * @param playerId
	 * @param sexmark
	 *            性别标记
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public WeddingHall getWeddingHallByPlayerId(int sexmark, int playerId) {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hsql = new StringBuffer();
		hsql.append("from WeddingHall where 1=1 ");
		if (sexmark == 0) {
			hsql.append(" and manId = ?");
		} else {
			hsql.append(" and womanId = ?");
		}
		values.add(playerId);
		List<WeddingHall> list = this.getList(hsql.toString(), values.toArray());
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 删除婚礼
	 * 
	 * @param mr
	 */
	public void deleteWeddingHall(MarryRecord mr) {
		List<Object> values = new ArrayList<Object>();
		StringBuffer hsql = new StringBuffer();
		hsql.append("Delete from WeddingHall where manId = ? and womanId = ?");
		values.add(mr.getManId());
		values.add(mr.getWomanId());
		execute(hsql.toString(), values.toArray());
	}
}