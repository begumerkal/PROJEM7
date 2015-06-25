package com.wyd.empire.world.dao.mysql.impl;

import java.util.ArrayList;
import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.dao.mysql.IRankRecordDao;
import com.wyd.empire.world.entity.mysql.ChallengeRecord;
import com.wyd.empire.world.entity.mysql.IntegralArea;
import com.wyd.empire.world.entity.mysql.RankRecord;
import com.wyd.empire.world.entity.mysql.ShopItem;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class RankRecordDao extends UniversalDaoHibernate implements IRankRecordDao {
	public RankRecordDao() {
		super();
	}

	/**
	 * 获得排位赛排名
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RankRecord> getRankRecordList(int limitNum) {
		int areaId = ServiceManager.getManager().getConfiguration().getInt("machinecode");
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM RankRecord rr where rr.player.areaId = ? order by integral desc");
		values.add(areaId);
		if (limitNum != 0) {
			return getList(hql.toString(), values.toArray(), limitNum);
		}
		return getList(hql.toString(), values.toArray());
	}

	/**
	 * 获得玩家排名
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Integer getPlayerRankNum(int playerId) {
		int areaId = ServiceManager.getManager().getConfiguration().getInt("machinecode");
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("select count(id)+1 from RankRecord rr where rr.player.areaId = ? and  integral > (select integral from RankRecord where playerId = ?)");
		values.add(areaId);
		values.add(playerId);
		List<Object> list = this.getList(hql.toString(), values.toArray());
		if (null != list && list.size() != 0) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 获得玩家排名赛对象
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public RankRecord getPlayerRankByPlayerId(int playerId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("From RankRecord rr where playerId = ? ");
		values.add(playerId);
		List<RankRecord> list = this.getList(hql.toString(), values.toArray());
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 清除排位赛记录
	 */
	public void deleteRankRecordForStart() {
		int areaId = ServiceManager.getManager().getConfiguration().getInt("machinecode");
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("delete rr.* from tab_rankrecord rr,tab_player tp where rr.playerid = tp.id and tp.areaId = ?");
		values.add(areaId);
		executeSql(hql.toString(), values.toArray());
	}

	/**
	 * 获得符合等级的排位赛
	 * 
	 * @param sex
	 * @param honorLevel
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ShopItem> getHonorReward(int sex, int honorLevel) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("From ShopItem si where si.sex = ? and si.saledNum = ?");
		values.add(sex);
		values.add(honorLevel);
		return getList(hql.toString(), values.toArray());
	}

	// 挑战赛相关方法------------------------------------------------------------------------------
	/**
	 * 获取本服玩家的挑战赛积分
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ChallengeRecord> getAllIntegral() {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("From ChallengeRecord where serviceId = ? ");
		values.add(WorldServer.config.getMachineCode());
		return getList(hql.toString(), values.toArray());
	}

	/**
	 * 获得玩家挑战赛应得奖励
	 * 
	 * @param rankNum
	 * @return
	 */
	public IntegralArea getIntegralAreaByRank(int rankNum) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("From IntegralArea ir where rank_low<= ? and ?<=rank_high ");
		values.add(rankNum);
		values.add(rankNum);
		return (IntegralArea) getUniqueResult(hql.toString(), values.toArray());
	}

	/**
	 * 清除挑战赛记录
	 */
	public void deleteChallengeRecord() {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("delete from ChallengeRecord where serviceId = ?");
		values.add(WorldServer.config.getMachineCode());
		execute(hql.toString(), values.toArray());
	}

	/**
	 * 更新积分
	 */
	public void updateIntegralByArea() {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("update ChallengeRecord set lastIntegral = integral,winNum=0 where serviceId = ?");
		values.add(WorldServer.config.getMachineCode());
		execute(hql.toString(), values.toArray());
	}
}