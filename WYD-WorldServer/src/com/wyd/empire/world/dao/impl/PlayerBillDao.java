package com.wyd.empire.world.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import org.springframework.util.StringUtils;
import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.PlayerBill;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.dao.IPlayerBillDao;
import com.wyd.empire.world.server.service.impl.TradeService;

/**
 * 类 <code>FeeDaoImpl</code>执行与Fee表相关数据库操作
 * 
 * @see com.wyd.accountserver.account.dao.impl.UniversalDaoHibernate
 * @author sunzx
 */
public class PlayerBillDao extends UniversalDaoHibernate implements IPlayerBillDao {
	/**
	 * 验证订单是否已存在
	 * 
	 * @param oNum
	 * @return
	 */
	public boolean checkOrder(String oNum) {
		@SuppressWarnings("rawtypes")
		List plsit = getList("from " + PlayerBill.class.getSimpleName() + " where orderNum=?", new Object[]{oNum});
		if (null != plsit && plsit.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 查询用户是否首充
	 * 
	 * @param player
	 * @return
	 */
	public boolean playerIsFirstCharge(Player player) {
		@SuppressWarnings("rawtypes")
		List list = getList("from " + PlayerBill.class.getSimpleName() + " where origin=1 and playerId=?", new Object[]{player.getId()});
		if (null != list && list.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 查询用户是否当日首充了
	 * 
	 * @param player
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean playerIsEveryDayFirstCharge(Player player) {
		List<PlayerBill> list = getList(" FROM " + PlayerBill.class.getSimpleName()
				+ " where origin=1 and playerId=? and to_days(createTime) = to_days(now())", new Object[]{player.getId()}, 1);
		if (list == null || list.size() < 1) {
			return false;
		}
		return true;
	}

	/**
	 * GM工具--查询玩家消费情况
	 * 
	 * @param key
	 *            查询条件
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少条
	 * @return
	 */
	public PageList getBillPageList(String key, int pageIndex, int pageSize) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + PlayerBill.class.getSimpleName() + " WHERE 1 = 1 ");
		String[] params = key.split("\\|");
		for (int i = 0; i < params.length; i++) {
			if (StringUtils.hasText(params[i])) {
				switch (i) {
					case 0 :
						hsql.append(" AND playerId = ? ");
						values.add(Integer.parseInt(params[0]));
						break;
					case 1 :
						try {
							hsql.append(" AND createTime BETWEEN ? and ? ");
							values.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params[1]));
							values.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params[2]));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						break;
					case 3 :
						hsql.append(" AND origin = ? ");
						values.add(Integer.parseInt(params[3]));
						break;
					default :
						break;
				}
			}
		}
		hsql.append(" ORDER BY createTime DESC ");
		String hqlc = "SELECT COUNT(*) " + hsql.toString();
		return getPageList(hsql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	/**
	 * 根据玩家ID查询出玩家充值记录数--首冲使用
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public long getBillCount(int playerId) {
		return count(
				"SELECT COUNT(*) FROM " + PlayerBill.class.getSimpleName() + " WHERE origin = ? AND playerId=? AND isFirstRecharge=? ",
				new Object[]{Common.STATUS_SHOW, playerId, Common.PLAYER_BILL_STATUS_N});
	}

	/**
	 * 获取玩家充值钻石数量
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public long getPlayerBillCount(int playerId) {
		List<PlayerBill> list = getList("SELECT SUM(amount) FROM " + PlayerBill.class.getSimpleName() + " WHERE origin = ? AND playerId=?",
				new Object[]{TradeService.ORIGIN_RECH, playerId});
		if (list == null || list.size() == 0 || null == list.get(0)) {
			return 0;
		} else {
			return Long.valueOf(list.get(0) + "");
		}
	}

	/**
	 * 获取玩家第一条充值记录
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PlayerBill getFirstBillByPlayer(int playerId) {
		List<PlayerBill> list = this.getList("FROM " + PlayerBill.class.getSimpleName()
				+ " WHERE origin = ? AND playerId=? AND isFirstRecharge=? ORDER BY createTime asc ", new Object[]{Common.STATUS_SHOW,
				playerId, Common.PLAYER_BILL_STATUS_N});
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获取玩家每日首充记录
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param id
	 *            ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PlayerBill getEveryDayFirstBillByPlayer(int playerId, int id) {
		List<PlayerBill> list = this.getList("FROM " + PlayerBill.class.getSimpleName()
				+ " WHERE  origin = ? AND playerId=?  ORDER BY createTime asc ", new Object[]{Common.STATUS_SHOW, playerId});
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获得每个分区充值情况
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getRechargeRecordByAreaId() {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append("SELECT pb.amount,pb.channel_id,pb.chargeprice,pb.createTime,pb.orderNum,pb.playerId,pb.remark,tp.areaId ");
		hsql.append(" FROM log_playerbill pb,tab_player tp where tp.id = pb.playerId ");
		hsql.append(" AND origin = 1 ");
		hsql.append(" AND TO_DAYS(now()) - TO_DAYS(pb.createTime) = 1 ");
		// hsql.append("AND tp.areaId = ? " );
		// values.add(WorldServer.config.getMachineCode());
		List<Object> list = getListBySql(hsql.toString(), values.toArray());
		return list;
	}

	/**
	 * 获取玩家某项钻石数
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public long getPlayerBillCountByOrigin(int origin, int playerId) {
		List<PlayerBill> list = getList("SELECT SUM(amount) FROM " + PlayerBill.class.getSimpleName() + " WHERE origin = ? AND playerId=?",
				new Object[]{origin, playerId});
		if (list == null || list.size() == 0 || null == list.get(0)) {
			return 0;
		} else {
			return Long.valueOf(list.get(0) + "");
		}
	}
}
