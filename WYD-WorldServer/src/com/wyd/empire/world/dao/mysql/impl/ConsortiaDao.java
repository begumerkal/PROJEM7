package com.wyd.empire.world.dao.mysql.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.wyd.db.mysql.dao.impl.UniversalDaoHibernate;
import com.wyd.db.mysql.page.PageList;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.consortia.MaxPrestigeVo;
import com.wyd.empire.world.dao.mysql.IConsortiaDao;
import com.wyd.empire.world.entity.mysql.BuffRecord;
import com.wyd.empire.world.entity.mysql.Consortia;
import com.wyd.empire.world.entity.mysql.ConsortiaBattle;
import com.wyd.empire.world.entity.mysql.ConsortiaSkill;
import com.wyd.empire.world.entity.mysql.PlayerSinConsortia;
import com.wyd.empire.world.service.factory.ServiceManager;

/**
 * The DAO class for the TabConsortia entity.
 */
public class ConsortiaDao extends UniversalDaoHibernate implements IConsortiaDao {
	public ConsortiaDao() {
		super();
	}

	/**
	 * 根据公会名称获得公会对象
	 * 
	 * @param name
	 *            公会名称
	 * @return 公会对象
	 */
	@SuppressWarnings("unchecked")
	public Consortia getConsortiaByName(String name) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM  " + Consortia.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND name = ? ");
		values.add(name.trim());
		// return (Consortia)this.getUniqueResult(hql.toString(),
		// values.toArray());
		// getUniqueResult 转 getList
		List<Consortia> list = this.getList(hql.toString(), values.toArray());
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 更改新的会长的职位（会长让位）
	 * 
	 * @param consortiaId
	 *            公会ID号
	 * @param newPId
	 *            新会长玩家ID号
	 */
	public void changePresident(int newPId, int oldPId) {
		StringBuffer hql = new StringBuffer();
		hql.append("call changePresident(?,?)");
		executeSql(hql.toString(), new Object[]{oldPId, newPId});
	}

	/**
	 * 根据玩家Id获取公会对象
	 * 
	 * @param playerId
	 *            玩家Id
	 * @return 公会对象
	 */
	@SuppressWarnings("unchecked")
	public Consortia getConsortiaByPlayerId(int playerId) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" SELECT pc.consortia FROM " + PlayerSinConsortia.class.getSimpleName() + " AS pc WHERE 1 = 1 ");
		hql.append(" AND pc.player.id = ? ");
		values.add(playerId);
		hql.append(" AND pc.identity = ? ");
		values.add(1);
		List<Consortia> list = getList(hql.toString(), values.toArray());
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据公会ID查询对应公会排名
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @return 对应公会排名
	 */
	public Integer getRankNum(int consortiaId, int mark) {
		int num = 0;
		Consortia consortia = (Consortia) this.getHibernateTemplate().get(Consortia.class, consortiaId);
		if (consortia != null) {
			StringBuffer hql = new StringBuffer();
			List<Object> values = new ArrayList<Object>();
			hql.append("SELECT COUNT(c) FROM " + Consortia.class.getSimpleName() + " AS c WHERE 1 = 1 ");
			if (mark == 0) {
				hql.append(" AND prestige > ? or (prestige = ? and  id < ?) ");
				values.add(consortia.getPrestige());
				values.add(consortia.getPrestige());
				values.add(consortiaId);
			} else {
				hql.append(" AND winNum > ? or (winNum = ? and  id < ?) ");
				values.add(consortia.getWinNum());
				values.add(consortia.getWinNum());
				values.add(consortiaId);
			}

			Long count = this.count(hql.toString(), values.toArray());
			if (count != null && count > 0) {
				num = (int) (count + 1);
			}
		}
		return num;
	}

	/**
	 * 更新公会排名
	 */
	public void updateCommunityRank() {
		StringBuilder hql = new StringBuilder();
		hql.append("call updateCommunityRank(?)");
		executeSql(hql.toString(), new Object[]{WorldServer.config.getMachineCode()});
	}

	/**
	 * 根据公会ID获得此ID以外的所有公会列表
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @return 所有公会列表
	 */
	@SuppressWarnings("unchecked")
	public List<Consortia> getConsortiaOthers(int consortiaId) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + Consortia.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND id != ? ");
		values.add(consortiaId);
		hql.append(" AND president.areaId = ? ");
		values.add(WorldServer.config.getMachineCode());
		hql.append(" ORDER BY prestige DESC ");
		return getList(hql.toString(), values.toArray());
	}

	/**
	 * 根据威信大小进行排序，获得公会列表
	 * 
	 * @return 公会列表
	 */
	public PageList getConsortiaList(int pageNum, int mark) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + Consortia.class.getSimpleName() + "  WHERE 1 = 1");
		hql.append(" AND president.areaId = ? ");
		values.add(WorldServer.config.getMachineCode());
		if (mark == 0) {
			hql.append(" ORDER BY prestige DESC ,id ASC");
		} else {
			hql.append(" ORDER BY winNum DESC ,id ASC");
		}

		String countHql = "SELECT COUNT(id) " + hql.toString();
		return getPageList(hql.toString(), countHql, values.toArray(), pageNum - 1, 1);
	}

	/**
	 * 根据威信大小进行排序，获得公会列表
	 * 
	 * @return 公会列表
	 */
	@SuppressWarnings("unchecked")
	public List<Consortia> getConsortiaList() {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + Consortia.class.getSimpleName() + "  WHERE 1 = 1");
		hql.append(" AND president.areaId = ? ");
		values.add(WorldServer.config.getMachineCode());
		hql.append(" ORDER BY prestige DESC ");
		return getList(hql.toString(), values.toArray());
	}

	/**
	 * 根据公会ID，搜索公会信息
	 * 
	 * @param consortiaId
	 *            公司ID
	 * @return 对应公会信息
	 */
	@SuppressWarnings("unchecked")
	public Consortia getConsortiaById(int consortiaId) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM " + Consortia.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND president.areaId = ? ");
		values.add(WorldServer.config.getMachineCode());
		hql.append(" AND id = ? ");
		values.add(consortiaId);
		// return (Consortia)getUniqueResult(hql.toString(), values.toArray());
		// getUniqueResult 转 getList
		List<Consortia> list = this.getList(hql.toString(), values.toArray());
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获得玩家的buff记录
	 * 
	 * @param playerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BuffRecord> getBuffRecordByPlayerId(int playerId) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM " + BuffRecord.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND playerId = ? ");
		values.add(playerId);
		hql.append(" AND endtime > now() ");
		List<BuffRecord> list = getList(hql.toString(), values.toArray());
		return list;
	}

	/**
	 * 删除过期的玩家的buff记录
	 * 
	 * @param playerId
	 * @return
	 */
	public void deleteBuffRecordOverTime(int playerId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("DELETE FROM " + BuffRecord.class.getSimpleName() + " AS ex WHERE 1 = 1 ");
		hql.append(" AND ex.endtime < now() AND playerId = ?");
		values.add(playerId);
		this.execute(hql.toString(), values.toArray());
	}

	/**
	 * 将每日贡献度清零
	 */
	public void updateEverydayAddToZero() {
		int areaId = ServiceManager.getManager().getConfiguration().getInt("machinecode");
		StringBuilder hql = new StringBuilder();
		List<Object> values = new Vector<Object>();
		hql.append("update tab_consortiacontribute tc,tab_player tp set everydayAdd = 0 where tc.playerId = tp.id and tp.areaId = ?");
		values.add(areaId);
		executeSql(hql.toString(), values.toArray());

		hql.delete(0, hql.length());
		values.clear();
		hql.append("update tab_playersinconsortia tc,tab_player tp set everydayAdd = 0 where tc.playerId = tp.id and tp.areaId = ?");
		values.add(areaId);
		executeSql(hql.toString(), values.toArray());
	}

	/**
	 * 获取敌对工会列表
	 */
	@SuppressWarnings("unchecked")
	public List<Consortia> getHosConsortia(int hosId, int conId) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + Consortia.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND id = ? ");
		values.add(hosId);
		hql.append(" OR hosId = ? ");
		values.add(conId);
		return getList(hql.toString(), values.toArray());
	}

	/**
	 * 获得当日工会战参数
	 * 
	 * @param mark
	 * @return
	 */
	public int getTodayConsortiaBattle(int mark) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" SELECT count(id) FROM " + ConsortiaBattle.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND status = ? ");
		values.add(mark);
		hql.append(" AND DATE_FORMAT(time,'%Y-%m-%d') = ? ");
		values.add(DateUtil.getCurrentDate());
		return (int) this.count(hql.toString(), values.toArray());
	}

	/**
	 * 判断两个公会是否是敌对工会
	 * 
	 * @param consortiaId1
	 * @param consortiaId2
	 * @return
	 */
	public boolean checkEnemyConsortia(int consortiaId1, int consortiaId2) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" SELECT count(id) FROM " + Consortia.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND id in (?,?) ");
		values.add(consortiaId1);
		values.add(consortiaId2);
		hql.append(" AND hosId in (?,?) ");
		values.add(consortiaId1);
		values.add(consortiaId2);
		if ((int) this.count(hql.toString(), values.toArray()) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 记录公会战斗情况
	 * 
	 * @param consortiaId
	 * @param num
	 */
	public void updateConsortiaBattleNum(int consortiaId, int num) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append("update " + Consortia.class.getSimpleName()
				+ " set totalNum=totalNum+1,winNum=winNum+?,todayNum=todayNum+1,todayWin=todayWin+?");
		hql.append(" where id = ? ");
		values.add(num);
		values.add(num);
		values.add(consortiaId);
		this.execute(hql.toString(), values.toArray());
	}

	/**
	 * 获取公会技能
	 */
	@SuppressWarnings("unchecked")
	public List<ConsortiaSkill> getConsortiaSkill() {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + ConsortiaSkill.class.getSimpleName() + " ORDER BY comLv asc ");
		return getList(hql.toString(), values.toArray());
	}

	/**
	 * 获取公会技能分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList getConsortiaListSkill(int pageNum, int pageSize) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + ConsortiaSkill.class.getSimpleName() + " ORDER BY comLv asc ");
		String countHql = "SELECT COUNT(id) " + hql.toString();
		return getPageList(hql.toString(), countHql, values.toArray(), pageNum, pageSize);
	}

	/**
	 * 根据公会技能id获得公会技能对象
	 * 
	 * @param id
	 *            公会技能id
	 * @return 公会技能对象
	 */
	@SuppressWarnings("unchecked")
	public ConsortiaSkill getConsortiaSkillById(Integer id) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM  " + ConsortiaSkill.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND id = ? ");
		values.add(id);
		// return (ConsortiaSkill)this.getUniqueResult(hql.toString(),
		// values.toArray());
		// getUniqueResult 转 getList
		List<ConsortiaSkill> list = this.getList(hql.toString(), values.toArray());
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据玩家Id获取公会对象
	 * 
	 * @param playerId
	 *            玩家Id
	 * @return 公会对象
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object[] getConsortiaNameAndIdByPlayerId(int playerId) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" SELECT pc.consortia.name, pc.consortia.id FROM " + PlayerSinConsortia.class.getSimpleName() + " AS pc WHERE 1 = 1 ");
		hql.append(" AND pc.player.id = ? ");
		values.add(playerId);
		hql.append(" AND pc.identity = ? ");
		values.add(1);
		// return (Object[])getUniqueResult(hql.toString(), values.toArray());
		// getUniqueResult 转 getList
		List<Object[]> list = this.getList(hql.toString(), values.toArray());
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获取公会列表
	 * 
	 * @return 公会列表
	 */
	@Override
	public PageList getConsortiaListByPage(int pageNum, int pageSize) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + Consortia.class.getSimpleName() + "  WHERE 1 = 1");
		hql.append(" AND president.areaId = ? ");
		values.add(WorldServer.config.getMachineCode());
		String countHql = "SELECT COUNT(id) " + hql.toString();
		return getPageList(hql.toString(), countHql, values.toArray(), pageNum - 1, pageSize);
	}

	/**
	 * 根据公会id或名称查询公会
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Consortia> getConsortiaByKey(String key) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new Vector<Object>();
		hql.append(" FROM " + Consortia.class.getSimpleName() + "  WHERE 1 = 1");
		hql.append(" AND president.areaId = ? ");
		values.add(WorldServer.config.getMachineCode());
		if (ServiceUtils.isNumeric(key)) {
			hql.append(" AND (id = ? OR name like '%" + key + "%') ");
			values.add(Integer.parseInt(key));
		} else {
			hql.append(" AND name like '%" + key + "%' ");
		}
		return (List<Consortia>) getList(hql.toString(), values.toArray());
	}

	/**
	 * 获取当前服威望最高的公会及会长
	 */
	@SuppressWarnings("unchecked")
	@Override
	public MaxPrestigeVo getMaxPrestige() {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" SELECT president.id, president.name,id, name FROM " + Consortia.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND prestige = (");
		hql.append(" SELECT MAX(prestige) FROM " + Consortia.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND president.areaId = ?) ");
		values.add(WorldServer.config.getMachineCode());
		List<Object[]> obj = (List<Object[]>) this.getList(hql.toString(), values.toArray(), 1);
		MaxPrestigeVo maxPrestigeVo = new MaxPrestigeVo();
		if (obj != null && !obj.isEmpty()) {
			Object[] o = obj.get(0);
			maxPrestigeVo.setPresidentId(((Number) o[0]).intValue());
			maxPrestigeVo.setPresidentName((String) o[1]);
			maxPrestigeVo.setConsortiaId(((Number) o[2]).intValue());
			maxPrestigeVo.setConsortiaName((String) o[3]);
		}
		return maxPrestigeVo;
	}

}