package com.wyd.service.dao;

import java.util.Date;
import java.util.List;

import com.app.db.dao.UniversalDao;
import com.wyd.service.bean.PlayerBill;

public interface IPlayerBillDao extends UniversalDao {
	public List<PlayerBill> findPaymentBy(Date startTime,Date endTime);
	 /**
     * 获得每个分区充值情况
     * @return
     */
    public List<Object> getRechargeRecordByAreaId();
}
