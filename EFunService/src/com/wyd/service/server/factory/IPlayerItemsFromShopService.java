package com.wyd.service.server.factory;
import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.service.bean.Player;
import com.wyd.service.bean.PlayerItemsFromShop;
/**
 * The service interface for the TabPlayeritemsfromshop entity.
 */
public interface IPlayerItemsFromShopService extends UniversalManager {
	 /**
     * The service Spring bean id, used in the applicationContext.xml file.
     */
	public static final String     SERVICE_BEAN_ID = "PlayerItemsFromShopService";
	/**
     * 玩家获得物品方法
     * @param playerId 玩家id
     * @param itemId 物品id
     * @param priceId 物品价格id，如果是购买商品priceId为相应物品的价格id，如果是系统赠送物品则该参数为-1
     * @param days 物品使用天数,如果是购买商品，该参数传0
     * @param userNum 物品使用次数,如果是购买商品，该参数传0
     * @param getway 获得途径 -8喇叭，-7结婚，-6洗练，-5强化合成消耗，-4兑换勋章消耗，-3改名笔，-2公会构造，-1失败清零，0商城，
     * 1兑换，2任务，4副本奖励，5是礼包，6合成，7签到，8砸蛋，9VIP，10结婚 11排位赛，12战斗，13兑换码，14爱心许愿，15GM工具，16技能锁定，
     * 17打孔，18拆卸，19镶嵌， 20计费点购买,21抽奖系统,22抽奖额外奖励,23挑战赛奖励,24FB好友邀请,25世界BOSS
     * @param remark  GM工具物品给予时做备注说明  
     */
    public void playerGetItem(int playerId, int itemId, int priceId, int days, int useNum, int getway,String remark);

    public void saveGiftRecord(int playerId,int itemId,int num,String code);
    /**
     * 判断code是否已存在	
     * @param code
     * @return
     */
    public boolean isCodeInGiftRecord(String code);
    /**
     * 获取玩家装备的物品属性表
     * @param player
     * @param itemIds
     * @return
     */
    public List<PlayerItemsFromShop> playerFindItem(Integer playerId, List<Integer> itemIds, int sex);
    /**
     * 根据玩家角色ID和物品ID获得玩家指定物品对象
     * @param playerId 玩家角色ID
     * @param itemId 物品ID
     * @return 玩家指定物品对象
     */
    public PlayerItemsFromShop getPlayerItemsFromShopByPlayerIdAndItemId(Player player, int itemId);
}