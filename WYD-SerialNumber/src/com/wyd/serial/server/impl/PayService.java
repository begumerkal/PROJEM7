package com.wyd.serial.server.impl;
import org.springframework.context.ApplicationContext;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.serial.bean.App;
import com.wyd.serial.dao.IPayDao;
import com.wyd.serial.server.IPayService;
/**
 * The service class for the TabExtensionUser entity.
 */
public class PayService extends UniversalManagerImpl implements IPayService {
    /**
     * The dao instance injected by Spring.
     */
    private IPayDao      dao;
    /**
     * The service Spring bean id, used in the applicationContext.xml file.
     */
    private static final String SERVICE_BEAN_ID = "PayService";

    public PayService() {
        super();
    }

    /**
     * Returns the singleton <code>IExtensionUserService</code> instance.
     */
    public static IPayService getInstance(ApplicationContext context) {
        return (IPayService) context.getBean(SERVICE_BEAN_ID);
    }

    /**
     * Called by Spring using the injection rules specified in the Spring beans file "applicationContext.xml".
     */
    public void setDao(IPayDao dao) {
        super.setDao(dao);
        this.dao = dao;
    }

    public IPayDao getDao() {
        return this.dao;
    }

    @Override
    public App getAppByNameEnglish(String nameEnglish) {
        return this.dao.getAppByNameEnglish(nameEnglish);
    }
}