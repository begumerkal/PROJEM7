package com.wyd.service.server.impl;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.service.bean.BuffRecord;
import com.wyd.service.bean.Consortia;
import com.wyd.service.dao.IConsortiaDao;
import com.wyd.service.server.factory.IConsortiaService;

/**
 * The service class for the TabConsortia entity.
 */
public class ConsortiaService extends UniversalManagerImpl implements IConsortiaService {
    
    
    /**
     * The dao instance injected by Spring.
     */
    private IConsortiaDao       dao;
    /**
     * The service Spring bean id, used in the applicationContext.xml file.
     */
    private static final String SERVICE_BEAN_ID = "ConsortiaService";

    public ConsortiaService() {
        super();
    }

    /**
     * Returns the singleton <code>IConsortiaService</code> instance.
     */
    public static IConsortiaService getInstance(ApplicationContext context) {
        return (IConsortiaService) context.getBean(SERVICE_BEAN_ID);
    }

    /**
     * Called by Spring using the injection rules specified in the Spring beans file "applicationContext.xml".
     */
    public void setDao(IConsortiaDao dao) {
        super.setDao(dao);
        this.dao = dao;
    }

    public IConsortiaDao getDao() {
        return this.dao;
    }

    /**
     * 获得玩家的buff记录
     * 
     * @param playerId
     * @return
     */
    public List<BuffRecord> getBuffRecordByPlayerId(int playerId) {
        List<BuffRecord> brList = dao.getBuffRecordByPlayerId(playerId);
        for (BuffRecord br : brList) {
            if (null == br.getConsortiaSkill() || 0 == br.getConsortiaSkill().getId()) {
                br.setConsortiaSkill(null);
                continue;
            }
            Hibernate.initialize(br.getConsortiaSkill());
        }
        return brList;
    }

	@Override
	public Consortia getConsortiaByPlayerId(int playerId) {
		return dao.getConsortiaByPlayerId(playerId);		
	}
}