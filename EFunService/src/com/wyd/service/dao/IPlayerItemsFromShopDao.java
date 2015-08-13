package com.wyd.service.dao;
import java.util.List;

import com.app.db.dao.UniversalDao;
import com.wyd.service.bean.PlayerItemsFromShop;
/**
 * The DAO interface for the TabPlayeritemsfromshop entity.
 */
public interface IPlayerItemsFromShopDao extends UniversalDao {

    /**
     * 根据玩家ID和物品ID获取玩家指定商品列表
     * 
     * @param playerId
     *            根据玩家ID
     * @param itemId
     *            物品ID
     * @return 玩家指定商品列表
     */
    public List<PlayerItemsFromShop> checkPlayerHaveItem(int playerId, int itemId);





    /**
     * 根据玩家角色ID和物品ID获得玩家指定物品对象
     * 
     * @param playerId
     *            玩家角色ID
     * @param itemId
     *            物品ID
     * @return 玩家指定物品对象
     */
    public PlayerItemsFromShop getPlayerItemsFromShopByPlayerIdAndItemId(int playerId, int itemId);


   

   

   


  


 
    
  
    
   
    
   
}