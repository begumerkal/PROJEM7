package com.wyd.rolequery.dao;
import java.util.List;
import com.wyd.db.dao.UniversalDao;
import com.wyd.rolequery.bean.Player;
/**
 * The DAO interface for the TabConsortiaright entity.
 */
public interface IWorldDao extends UniversalDao {
    /**
     * 根据帐号id获取角色信息
     * @param accountIds
     * @return
     */
    public List<Player> getPlayerList(String accountIds);
}