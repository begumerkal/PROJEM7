package com.wyd.service.server.factory;
import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.service.bean.PlayerBill;
/**
 * The service interface for the TabPlayeritemsfromshop entity.
 */
public interface IPlayerBillService extends UniversalManager {
	/**
     * The service Spring bean id, used in the applicationContext.xml file.
     */
	public static final String     SERVICE_BEAN_ID = "PlayerBillService";
	/**
	 * 查找充值记录
	 * startTime:yyyy-MM-dd HH:mm:ss
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<PlayerBill> findPaymentBy(String startTime,String endTime);  
	/**
     * 获得每个分区充值情况
     * @return
     */
    public void getRechargeRecordByAreaId();
}