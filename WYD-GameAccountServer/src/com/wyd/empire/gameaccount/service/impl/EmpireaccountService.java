package com.wyd.empire.gameaccount.service.impl;
import java.util.Date;
import java.util.List;
import org.springframework.context.ApplicationContext;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.gameaccount.bean.Empireaccount;
import com.wyd.empire.gameaccount.dao.IEmpireaccountDao;
import com.wyd.empire.gameaccount.exception.CreateGameAccountException;
import com.wyd.empire.gameaccount.service.IEmpireaccountService;
public class EmpireaccountService extends UniversalManagerImpl implements IEmpireaccountService {
//    private Cache             name2account = null;
//    private Cache             id2name      = null;
    /**
     * The dao instance injected by Spring.
     */
    private IEmpireaccountDao empireaccountDao;

    public EmpireaccountService() {
        super();
    }

    public void init() {
//        CacheManager manager = CacheManager.create();
//        this.name2account = new Cache("name2account", 3000, false, true, 0L, 0L);
//        this.id2name = new Cache("id2name", 8000, false, true, 0L, 0L);
//        manager.addCache(this.id2name);
//        manager.addCache(this.name2account);
    }
    /**
     * The service Spring bean id, used in the applicationContext.xml file.
     */
    private static final String SERVICE_BEAN_ID = "EmpireaccountService";

    /**
     * Returns the singleton <code>EmpireaccountService</code> instance.
     */
    public static IEmpireaccountService getInstance(ApplicationContext context) {
        return (IEmpireaccountService) context.getBean(SERVICE_BEAN_ID);
    }

    public IEmpireaccountDao getEmpireaccountDao() {
        return empireaccountDao;
    }

    public void setEmpireaccountDao(IEmpireaccountDao empireaccountDao) {
        this.empireaccountDao = empireaccountDao;
        super.setDao(empireaccountDao);
    }
    
    /**
     * 创建用户角色账号
     * 
     * @param accountId
     *            账号id
     * @param name
     *            用户名
     * @param clientModel
     *            注册客户端机型
     * @param version
     *            游戏版本
     * @param time
     *            创建时间，登录时间
     * @param ipAddress
     *            玩家最后一次登录ip
     * @param serverid
     *            serverid
     */
    public Empireaccount createGameAccount(int accountId, String name, String clientModel, String version, Date time, String ipAddress, String serverid, int channel) throws CreateGameAccountException {
        try {
        	if(null!=this.empireaccountDao.getGameAccountByAccountId(accountId, serverid)){
        		throw new CreateGameAccountException("GameAccount already exists");
        	}
            if (accountId < 0) throw new CreateGameAccountException("AsccountID can not be null");
            if (name == null) throw new CreateGameAccountException("Name can not be null");
            Empireaccount account = new Empireaccount();
            account.setAccountId(accountId);
            account.setName(name);
            account.setClientModel(clientModel);
            account.setVersion(version);
            account.setCreateTime(new java.sql.Timestamp(time.getTime()));
            account.setTotalLoginTimes(1);
            account.setLastLoginTime(new java.sql.Timestamp(time.getTime()));
            account.setIpAddress(ipAddress);
            account.setServerid(serverid);
            account.setStatus(EMPIREACCOUNT_STATUS_NORMAL);
            account.setOnLineTime(0);
            account.setMaxLevel(1);
            account.setMachinecode(Integer.parseInt(serverid.substring(serverid.indexOf("_")+1)));
            account.setChannel(channel);
            Empireaccount temp = this.empireaccountDao.getGameAccountByAccountId(accountId, serverid);
            if (temp == null) {
                account = this.empireaccountDao.create(account);
            }
            return account;
        } catch (CreateGameAccountException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据用户名获取<tt>GameAccount</tt>信息
     * 
     * @param name
     *            用户名
     * @param sessionId
     *            sessionId
     */
    public Empireaccount login(int accountId, String serverId) {
        synchronized (this) {
            Empireaccount account = this.empireaccountDao.getGameAccountByAccountId(accountId, serverId);
            if (account == null) return null;
            if ((account.getServerid() != null) && (account.getServerid().equals(serverId))) {
                return account;
            }
            return null;
        }
    }

    /**
     * 更新Empireaccount数据
     * 
     * @param account
     */
    public void updateGameAccount(Empireaccount account) {
        this.empireaccountDao.update(account);
    }

    /**
     * 根据id编号，获取<tt>GameAccount</tt>游戏账号
     * 
     * @param id
     *            id编号
     */
    public Empireaccount getGameAccount(int id) {
            Empireaccount account = null;
            account = this.empireaccountDao.getGameAccountById(id);
            return account;
    }
    
    /**
     * 根据角色名称，取得角色账号信息
     * 
     * @param name
     *            角色名称
     * @return
     */
    public List<Empireaccount> getGameAccount(String name){
        return this.empireaccountDao.getGameAccountByName(name);
    }
    
    public void setClientInfo(int accountId, String clientModel, String systemName, String systemVersion){
        this.empireaccountDao.setClientInfo(accountId, clientModel, systemName, systemVersion);
    }
}
