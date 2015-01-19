package com.wyd.service.server.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.service.bean.PlayerBill;
import com.wyd.service.dao.IPlayerBillDao;
import com.wyd.service.server.factory.IPlayerBillService;
import com.wyd.service.server.factory.ServiceManager;

public class PlayerBillService  extends UniversalManagerImpl implements IPlayerBillService {
    Logger log= Logger.getLogger(PlayerBillService.class);
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * The dao instance injected by Spring.
     */
    private IPlayerBillDao dao;
    

    public PlayerBillService() {
        super();
    }

    /**
     * Returns the singleton <code>IPlayeritemsfromshopService</code> instance.
     */
    public static IPlayerBillService getInstance(ApplicationContext context) {
        return (IPlayerBillService) context.getBean(SERVICE_BEAN_ID);
    }

    /**
     * Called by Spring using the injection rules specified in the Spring beans file "applicationContext.xml".
     */
    public void setDao(IPlayerBillDao dao) {
        super.setDao(dao);
        this.dao = dao;
    }

    public IPlayerBillDao getDao() {
        return this.dao;
    }

	@Override
	public List<PlayerBill> findPaymentBy(String startTime, String endTime) {
		try {
			List<PlayerBill> playerBills = dao.findPaymentBy(sf.parse(startTime),sf.parse( endTime));
			for(PlayerBill playerBill:playerBills){
				Hibernate.initialize(playerBill.getPlayer());
			}
			return playerBills;
		} catch (ParseException e) {
			log.error(e,e);
			return null;
		}
		
	}
   
	/**
     * 获得每个分区充值情况
     * @return
     */
    public void getRechargeRecordByAreaId(){
    	try {
			ServiceManager.getManager().getCheckRechargeService().addIntegralInfo(dao.getRechargeRecordByAreaId());
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.error(e, e);
		}
    }
    
    
}
