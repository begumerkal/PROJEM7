package com.wyd.service.dao.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.service.bean.PlayerItemsFromShop;
import com.wyd.service.dao.IPlayerItemsFromShopDao;
/**
 * The DAO class for the TabPlayeritemsfromshop entity.
 */
public class PlayerItemsFromShopDao extends UniversalDaoHibernate implements IPlayerItemsFromShopDao {
	
	public PlayerItemsFromShopDao() {
        super();
    }

	
	/**
     * 根据玩家ID和物品ID获取玩家指定商品列表
     * 
     * @param playerId
     *            根据玩家ID
     * @param itemId
     *            物品ID
     * @return 玩家指定商品列表
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PlayerItemsFromShop> checkPlayerHaveItem(int playerId, int itemId) {
        StringBuilder hql = new StringBuilder();
        List<Object> values = new ArrayList<Object>();
        hql.append("FROM " ).append( PlayerItemsFromShop.class.getSimpleName() ).append( " WHERE 1 = 1 ");
        hql.append(" AND player.id = ? ");
        values.add(playerId);
        hql.append(" AND shopItem.id = ? ");
        values.add(itemId);
        return getList(hql.toString(), values.toArray());
    }

	@Override
	public PlayerItemsFromShop getPlayerItemsFromShopByPlayerIdAndItemId(int playerId, int itemId) {
		StringBuilder hql = new StringBuilder();
        List<Object> values = new Vector<Object>();
        hql.append("FROM " + PlayerItemsFromShop.class.getSimpleName() + " WHERE 1 = 1 ");
        hql.append(" AND player.id = ? ");
        values.add(playerId);
        hql.append(" AND shopItem.id = ? ");
        values.add(itemId);
        @SuppressWarnings("unchecked")
		List<PlayerItemsFromShop> list = getList(hql.toString(), values.toArray());
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
	}
	
}