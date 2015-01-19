package com.wyd.rolequery.server;

import java.util.List;
import com.wyd.db.service.UniversalManager;
import com.wyd.rolequery.bean.Player;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IWorldService extends UniversalManager{
    /**
     * 根据帐号id获取角色信息
     * @param accountIds
     * @return
     */
    public List<Player> getPlayerList(String accountIds);
}