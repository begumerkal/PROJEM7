package com.wyd.empire.gameaccount.service;
import java.util.Date;
import java.util.List;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.gameaccount.bean.Empireaccount;
import com.wyd.empire.gameaccount.exception.CreateGameAccountException;
public interface IEmpireaccountService extends UniversalManager {
    public static final byte EMPIREACCOUNT_STATUS_INVALID = 0;
    public static final byte EMPIREACCOUNT_STATUS_NORMAL  = 1;
    public static final byte EMPIREACCOUNT_STATUS_FREEZE  = 2;

    public void init();

    public Empireaccount createGameAccount(int accountId, String name, String clientModel, String version, Date time, String ipAddress, String serverid, int channel) throws CreateGameAccountException;

    public Empireaccount login(int accountId, String serverId);
    
    public void updateGameAccount(Empireaccount account);
    
    public Empireaccount getGameAccount(int id);
    
    /**
     * 根据角色名称，取得角色账号信息
     * 
     * @param name
     *            角色名称
     * @return
     */
    public List<Empireaccount> getGameAccount(String name);
    
    /**
     * 设置客户端信息
     * @param accountId gameaccount的id
     * @param clientModel 客户端硬件信息
     * @param systemName 客户端系统名称
     * @param systemVersion 客户端系统版本
     */
    public void setClientInfo(int accountId, String clientModel, String systemName, String systemVersion);
}
