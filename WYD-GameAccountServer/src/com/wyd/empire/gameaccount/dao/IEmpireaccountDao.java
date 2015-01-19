package com.wyd.empire.gameaccount.dao;
import java.util.List;
import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.gameaccount.bean.Empireaccount;
public interface IEmpireaccountDao extends UniversalDao {
    /**
     * 根据角色id，取得角色账号信息
     * 
     * @param accountId
     *            角色id
     * @return
     */
    public Empireaccount getGameAccountByAccountId(int accountId, String serverId);

    /**
     * 根据角色名称，取得角色账号信息
     * 
     * @param name
     *            角色名称
     * @return
     */
    public Empireaccount getGameAccountByName(String name, String serverId);

    /**
     * 保存游戏角色账号信息
     * 
     * @param account
     *            游戏角色账号对象
     */
    public Empireaccount create(Empireaccount account);

    /**
     * 更新游戏角色账号信息
     * 
     * @param account
     *            游戏角色账号对象
     */
    public Empireaccount update(Empireaccount account);

    /**
     * 根据角色账号id，取得游戏角色账号用户名
     * 
     * @param id
     *            角色账号id
     * @return <tt>GameAccountName</tt>游戏角色账号用户名
     */
    public String getNameByAccountId(int accountId);

    /**
     * 根据id，取得游戏角色账号
     * 
     * @param id
     * @return
     */
    public Empireaccount getGameAccountById(int id);
    
    /**
     * 根据角色名称，取得角色账号信息
     * 
     * @param name
     *            角色名称
     * @return
     */
    public List<Empireaccount> getGameAccountByName(String name);
    
    /**
     * 设置客户端信息
     * @param accountId gameaccount的id
     * @param clientModel 客户端硬件信息
     * @param systemName 客户端系统名称
     * @param systemVersion 客户端系统版本
     */
    public void setClientInfo(int accountId, String clientModel, String systemName, String systemVersion);
}
