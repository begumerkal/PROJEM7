package com.wyd.rolequery.dao.impl;
import java.util.List;
import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.rolequery.bean.Player;
import com.wyd.rolequery.dao.IWorldDao;
/**
 * The DAO class for the TabConsortiaright entity.
 */
public class WorldDao extends UniversalDaoHibernate implements IWorldDao {
    public WorldDao() {
        super();
    }

    @SuppressWarnings("unchecked")
    public List<Player> getPlayerList(String accountIds) {
        return getList("from Player where accountId in (" + accountIds + ")", new Object[] {});
    }
}