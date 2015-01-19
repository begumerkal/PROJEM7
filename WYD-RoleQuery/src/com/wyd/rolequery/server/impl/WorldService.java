package com.wyd.rolequery.server.impl;
import java.util.List;
import org.springframework.context.ApplicationContext;
import com.wyd.rolequery.bean.Player;
import com.wyd.rolequery.dao.IWorldDao;
import com.wyd.rolequery.server.IWorldService;
import com.wyd.db.service.impl.UniversalManagerImpl;
/**
 * The service class for the TabExtensionUser entity.
 */
public class WorldService extends UniversalManagerImpl implements IWorldService {
    /**
     * The dao instance injected by Spring.
     */
    private IWorldDao      dao;
    /**
     * The service Spring bean id, used in the applicationContext.xml file.
     */
    private static final String SERVICE_BEAN_ID = "WorldService";

    public WorldService() {
        super();
    }

    /**
     * Returns the singleton <code>IExtensionUserService</code> instance.
     */
    public static IWorldService getInstance(ApplicationContext context) {
        return (IWorldService) context.getBean(SERVICE_BEAN_ID);
    }

    /**
     * Called by Spring using the injection rules specified in the Spring beans file "applicationContext.xml".
     */
    public void setDao(IWorldDao dao) {
        super.setDao(dao);
        this.dao = dao;
    }

    public IWorldDao getDao() {
        return this.dao;
    }

    @Override
    public List<Player> getPlayerList(String accountIds) {
        return this.dao.getPlayerList(accountIds);
    }

}