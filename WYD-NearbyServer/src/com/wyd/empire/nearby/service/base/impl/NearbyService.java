package com.wyd.empire.nearby.service.base.impl;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.nearby.bean.Mail;
import com.wyd.empire.nearby.bean.PlayerInfo;
import com.wyd.empire.nearby.dao.INearbyDao;
import com.wyd.empire.nearby.service.base.INearbyService;
/**
 * The service class for the TabConsortia entity.
 */
public class NearbyService extends UniversalManagerImpl implements INearbyService {
    /**
     * The dao instance injected by Spring.
     */
    private INearbyDao            dao;
    /**
     * The service Spring bean id, used in the applicationContext.xml file.
     */
    private static final String SERVICE_BEAN_ID = "NearbyService";

    public NearbyService() {
        super();
    }

    /**
     * Returns the singleton <code>IConsortiaService</code> instance.
     */
    public static INearbyService getInstance(ApplicationContext context) {
        return (INearbyService) context.getBean(SERVICE_BEAN_ID);
    }

    /**
     * Called by Spring using the injection rules specified in the Spring beans file "applicationContext.xml".
     */
    public void setDao(INearbyDao dao) {
        super.setDao(dao);
        this.dao = dao;
    }

    public INearbyDao getDao() {
        return this.dao;
    }

	@Override
	public int getMaxId(Class<?> clazz) {
		return this.dao.getMaxId(clazz);
	}

	@Override
	public List<PlayerInfo> getNearbyPlayer(int lon, int lat, int threshold, int maxResults) {
		return this.dao.getNearbyPlayer(lon, lat, threshold, maxResults);
	}

	@Override
	public void saveOrUpdate(Object entity) {
		this.dao.saveOrUpdate(entity);
	}

    @Override
    public List<Mail> getSendMailList(int sendId) {
        return this.dao.getSendMailList(sendId);
    }

    @Override
    public List<Mail> getReceivedMailList(int receivedId) {
        return this.dao.getReceivedMailList(receivedId);
    }
}