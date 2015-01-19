package com.wyd.rolequery.server.impl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.context.ApplicationContext;
import com.wyd.rolequery.bean.Player;
import com.wyd.rolequery.bean.PlayerBill;
import com.wyd.rolequery.bean.PlayerOnline;
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
    
    /**
     * 从id大于startId开始查limitNumber条数据玩家数据
     * @param startId
     * @param limitNumber
     * @return
     */
    public List<Player> getPlayerListAll(int startId, int limitNumber){
    	return this.dao.getPlayerListAll(startId, limitNumber);
    }

    /**
     * 从id大于startId开始查limitNumber条accountId数据
     * @param startId
     * @param limitNumber
     * @return
     */
    public List<Integer> getPlayerListIdAll(int startId, int limitNumber){
    	return this.dao.getPlayerListIdAll(startId, limitNumber);
    }

    /**
     * 根据时间查询新的玩家数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Player> getPlayerListByTime(String startTime, String endTime, int type){
    	return this.dao.getPlayerListByTime(startTime, endTime, type);
    }
    

    /**
     * 从id大于startId开始查limitNumber条数据玩家登录数据
     * @param startId
     * @param limitNumber
     * @return
     */
    public List<PlayerOnline> getPlayerOnlineListAll(int startId, int limitNumber) {
        return this.dao.getPlayerOnlineListAll(startId, limitNumber);
    }
    
    /**
     * 根据时间查询新的玩家登录数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<PlayerOnline> getPlayerOnlineListByTime(String startTime, String endTime, int type) {
		return this.dao.getPlayerOnlineListByTime(startTime, endTime, type);
    }
    
    /**
     * 从id大于startId开始查limitNumber条数据玩家充值数据
     * @param startId
     * @param limitNumber
     * @return
     */
    public List<PlayerBill> getPlayerBillListAll(int startId, int limitNumber) {
        return this.dao.getPlayerBillListAll(startId, limitNumber);
    }
    
    /**
     * 根据时间查询新的玩家充值数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<PlayerBill> getPlayerBillListByTime(String startTime, String endTime, int type) {
		return this.dao.getPlayerBillListByTime(startTime, endTime, type);
    }
    
    /**
     * 从id大于startId开始查limitNumber条数据玩家数据
     * @param startId
     * @param limitNumber
     * @return
     */
    public List<Integer> getPlayerEIDListAll(int startId, int limitNumber) {
        return this.dao.getPlayerEIDListAll(startId, limitNumber);
    }
    
    /**
     * 根据时间查询新的玩家数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Integer> getPlayerEIDListByTime(String startTime, String endTime, int type) {
        return this.dao.getPlayerEIDListByTime(startTime, endTime, type);
    }
    
    /**
     * 从id大于startId开始查limitNumber条数据玩家登录数据
     * @param startId
     * @param limitNumber
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getPlayerOnlineEIDListAll(int startId, int limitNumber) {
        return this.dao.getPlayerOnlineEIDListAll(startId, limitNumber);
    }
    
    /**
     * 根据时间查询新的玩家登录数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Integer> getPlayerOnlineEIDListByTime(String startTime, String endTime, int type) {
        return this.dao.getPlayerOnlineEIDListByTime(startTime, endTime, type);
    }
    
    /**
     * 从id大于startId开始查limitNumber条数据玩家充值数据
     * @param startId
     * @param limitNumber
     * @return
     */
    public List<Integer> getPlayerBillEIDListAll(int startId, int limitNumber) {
        return this.dao.getPlayerBillEIDListAll(startId, limitNumber);
    }
    
    /**
     * 根据时间查询新的玩家充值数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Integer> getPlayerBillEIDListByTime(String startTime, String endTime, int type) {
        return this.dao.getPlayerBillEIDListByTime(startTime, endTime, type);
    }
    
}