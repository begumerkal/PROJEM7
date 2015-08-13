package com.wyd.service.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.app.db.dao.impl.UniversalDaoHibernate;
import com.wyd.service.bean.PlayerBill;
import com.wyd.service.dao.IPlayerBillDao;

/**
 * The DAO class for the TabPlayeritemsfromshop entity.
 */
public class PlayerBillDao extends UniversalDaoHibernate implements IPlayerBillDao {

	public PlayerBillDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlayerBill> findPaymentBy(Date startTime, Date endTime) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM ").append(PlayerBill.class.getSimpleName()).append(" WHERE  origin=1");
		hql.append(" AND createTime BETWEEN ? AND ?");
		values.add(startTime);
		values.add(endTime);
		return getList(hql.toString(), values.toArray());
	}
	/** 获得每个分区充值情况
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Object> getRechargeRecordByAreaId(){
    	StringBuffer hsql = new StringBuffer();
        List<Object> values = new Vector<Object>();
        hsql.append("SELECT pb.amount,pb.chargeprice,pb.createTime,pb.orderNum,pb.playerId,pb.remark,pb.channel_id FROM log_playerbill pb,tab_player tp where tp.id = pb.playerId ");
        hsql.append("AND origin = 1 " );
        hsql.append("AND TO_DAYS(now()) - TO_DAYS(pb.createTime) = 1 ");
        List<Object> list = getListBySql(hsql.toString(), values.toArray());
        return list;
    }

}