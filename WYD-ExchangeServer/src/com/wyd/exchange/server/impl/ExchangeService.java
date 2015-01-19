package com.wyd.exchange.server.impl;

import org.springframework.context.ApplicationContext;
import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.exchange.bean.SelectInfo;
import com.wyd.exchange.bean.UpdateInfo;
import com.wyd.exchange.dao.ICodeExchangeDao;
import com.wyd.exchange.server.ICodeExchangeService;

/**
 * The service class for the TabExtensionUser entity.
 */
public class ExchangeService extends UniversalManagerImpl implements ICodeExchangeService {
	/**
	 * The dao instance injected by Spring.
	 */
	private ICodeExchangeDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "ExchangeService";
	
	public ExchangeService() {
		super();
	}
	/**
	 * Returns the singleton <code>IExtensionUserService</code> instance.
	 */
	public static ICodeExchangeService getInstance(ApplicationContext context) {
		return (ICodeExchangeService)context.getBean(SERVICE_BEAN_ID);
	}
	/**
	 * Called by Spring using the injection rules specified in 
	 * the Spring beans file "applicationContext.xml".
	 */
	public void setDao(ICodeExchangeDao dao) {
        super.setDao(dao);
        this.dao = dao;
	}
	public ICodeExchangeDao getDao() {
		return this.dao;
	}
	
    /**
     * 查询兑换码信息
     * @param selectInfo
     * @return
     */
    public PageList getItemList(SelectInfo selectInfo){
        return this.dao.getItemList(selectInfo);
    }
    
    /**
     * 发放兑换码
     * @param ids
     */
    public void extend(String ids){
        dao.extend(ids);
    }
    
    /**
     * 编辑信息
     * @param updateInfo
     */
    public void updateExchange(UpdateInfo updateInfo){
        dao.updateExchange(updateInfo);
    }
    /**
     * 获取某个用户某个批次的兑换数
     * 
     * @param playerId
     * @param batchNum
     */
    public int getExchangeCount(int playerId, String serviceId, String batchNum){
        return dao.getExchangeCount(playerId, serviceId, batchNum);
    }
}