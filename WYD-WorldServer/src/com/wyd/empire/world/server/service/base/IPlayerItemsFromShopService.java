package com.wyd.empire.world.server.service.base;

import java.util.List;
import java.util.Map;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.Exchange;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.VipRate;
import com.wyd.empire.world.player.WorldPlayer;

/**
 * The service interface for the TabPlayeritemsfromshop entity.
 */
public interface IPlayerItemsFromShopService extends UniversalManager {
	/**
	 * 加载玩家装备
	 * 
	 * @param playerId
	 */
	public void loadPlayerItem(int playerId);

	/**
	 * 重新加载玩家装备
	 * 
	 * @param playerId
	 * @param itemId
	 */
	public void reloadPlayerItem(int playerId, int itemId);

	/**
	 * 卸载玩家装备
	 * 
	 * @param playerId
	 */
	public void unLoadPlayerItem(int playerId);

	/**
	 * 玩家新增物品
	 * 
	 * @param pifs
	 */
	public PlayerItemsFromShop savePlayerItemsFromShop(PlayerItemsFromShop pifs);

	/**
	 * 玩家删除物品
	 * 
	 * @param pifs
	 */
	public void deletePlayerItem(PlayerItemsFromShop pifs);

	/**
	 * 玩家获得物品方法
	 * 
	 * @param playerId
	 *            玩家id
	 * @param itemId
	 *            物品id
	 * @param priceId
	 *            物品价格id，如果是购买商品priceId为相应物品的价格id，如果是系统赠送物品则该参数为-1
	 * @param days
	 *            物品使用天数,如果是购买商品，该参数传0
	 * @param userNum
	 *            物品使用次数,如果是购买商品，该参数传0
	 * @param getway
	 *            详见：saveGetItemRecord
	 * @param remark
	 *            GM工具物品给予时做备注说明
	 * @param useDiamond
	 *            消耗钻石
	 * @param useGold
	 *            消耗金币
	 * @param useBadge
	 *            消耗金币
	 */
	public PlayerItemsFromShop playerGetItem(int playerId, int itemId, int priceId, int days, int useNum, int getway, String remark,
			int useDiamond, int useGold, int useBadge);

	/**
	 * 玩家获得物品方法(简化) 根据useType来决定该物品是使用天数还是个数
	 * 
	 * @author zengxc
	 */
	public PlayerItemsFromShop playerGetItem(int playerId, int itemId, int dayOrCount, int getway, String remark, int useDiamond,
			int useGold, int useBadge);

	/**
	 * vip升级奖励
	 * 
	 * @param playerId
	 * @param oLevel
	 * @param nLevel
	 */
	public void vipLevelUpReward(int playerId, int oLevel, int nLevel);

	/**
	 * 根据玩家id获得对象
	 * 
	 * @param playerId
	 * @param
	 * @return
	 */
	public PageList getPlayerItemsFromShopByPlayerId(int playerId, int pageNum, int pageSize);

	/**
	 * 根据玩家ID获取玩家装备列表（其中装备必须是剩余数量不为0，剩余时间不为0）
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 玩家装备列表
	 */
	public List<PlayerItemsFromShop> getPlayerItemsFromShopByPlayerId(int playerId);

	/**
	 * 获取玩家指定物品的数量
	 * 
	 * @param playerId
	 * @param itemId
	 * @return
	 */
	public int getPlayerItemNum(int playerId, int itemId);

	/**
	 * 更新玩家指定物品的数量
	 * 
	 * @param playerId
	 * @param itemId
	 * @param speakerCount
	 */
	public void updatePlayerItemNum(int playerId, int itemId, int speakerCount);

	/**
	 * 根据玩家ID获得特殊效果对象（Vip，双倍卡）
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 特殊效果对象（Vip，双倍卡）
	 */
	public List<PlayerItemsFromShop> getSpecialEffectId(int playerId);

	/**
	 * 物品试用期到了，删除可删除物品或修改物品状态
	 */
	public boolean dispearItemAtOverTime(WorldPlayer player);

	/**
	 * 根据玩家角色ID和物品ID获得玩家指定物品对象
	 * 
	 * @param playerId
	 *            玩家角色ID
	 * @param itemId
	 *            物品ID
	 * @return 玩家指定物品对象
	 */
	public List<PlayerItemsFromShop> getPlayerItemByItemId(int playerId, int itemId);

	/**
	 * 获取玩家指定的物品(唯一)
	 * 
	 * @param playerId
	 * @param itemId
	 * @return
	 */
	public PlayerItemsFromShop uniquePlayerItem(int playerId, int itemId);

	/**
	 * 玩家角色ID和物品ID获得玩家勋章数量
	 * 
	 * @param playerId
	 *            玩家角色ID
	 * @param itemId
	 *            物品ID
	 * @return 玩家勋章数量
	 */
	public PlayerItemsFromShop getBadgeByPlayerId(int playerId);

	/**
	 * 更新玩家武器熟练度
	 * 
	 * @param playerId
	 * @param itemId
	 */
	public void updateItmeSkillful(WorldPlayer player);

	/**
	 * 检测装备第N天后过期
	 * 
	 * @param num
	 *            num为0时，表示当天过期
	 * @return 过期物品玩家ID和物品ID
	 */
	public List<Object> checkEquipmentOverTimeAfterNDays(int playerId, Long num);

	/**
	 * 玩家脱下装备,并换上新装备
	 * 
	 * @param oldItem
	 * @param newItem
	 */
	public void takeOffEquipment(PlayerItemsFromShop oldItem, PlayerItemsFromShop newItem);

	/**
	 * 根据玩家角色Id，获取所拥有的可以强化或合成的物品列表
	 * 
	 * @param playerId
	 *            玩家角色Id
	 * @return 可以强化或合成的物品列表
	 */
	public List<PlayerItemsFromShop> getStrengthenShopItemList(int playerId);

	/**
	 * 获得兑换商品列表
	 * 
	 * @param player
	 * @return
	 */
	public List<Exchange> checkExchangeList(WorldPlayer player);

	/**
	 * 刷新兑换列表
	 * 
	 * @param player
	 */
	public void refreshExchange(WorldPlayer player, long timeStart);

	/**
	 * GM工具-根据玩家ID查询出玩家物品
	 * 
	 * @param key
	 *            玩家ID
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少条
	 * @return
	 */
	public PageList findAllByPlayerId(String key, int pageIndex, int pageSize);

	/**
	 * 更换玩家身上物品的使用状态
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param shopId
	 *            商品ID
	 * @param isInUse
	 *            是否正在使用
	 */
	public void updateInUseByShopId(int playerId, int shopId, boolean isInUse);

	/**
	 * 清除过期记录(每隔N天清理一次)
	 */
	public void deleteOverDateRecord(int days);

	/**
	 * 保存物品的进销记录
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param itemId
	 *            物品ID
	 * @param days
	 *            天数
	 * @param countNum
	 *            数量
	 * @param getway
	 *            获得途径
	 *            -10系统赠送新手装备，-8喇叭，-7结婚，-6洗练，-5强化合成消耗，-4兑换勋章消耗，-3改名笔，-2公会构造，
	 *            -1失败清零，0商城， 1兑换，2任务，4副本奖励，5是礼包，6合成，7签到，8砸蛋，9VIP，10结婚
	 *            11排位赛，12战斗，13兑换码，14爱心许愿，15GM工具，16技能锁定， 17打孔，18拆卸，19镶嵌，
	 *            20计费点购买,
	 *            21抽奖系统,22抽奖额外奖励,23挑战赛奖励,24FB好友邀请,25世界BOSS,26活跃度奖励,27玩家在线奖励
	 *            28BM付费包奖励,29单人副本掉落,30星魂系统点亮星点消耗
	 *            ,31卡牌碎片合成,32卡牌熔炼，33修炼系统消耗，34激活修炼系统消耗
	 *            ,35卡牌兑换,36登录奖励获得,37等级奖励获得,38在线抽奖获得
	 * @param mark
	 *            添加还是使用（0添加，1使用）
	 * @param remark
	 *            GM工具物品给予时做备注说明
	 */
	public void saveGetItemRecord(int playerId, int itemId, int days, int countNum, int getWay, int mark, String remark);

	/**
	 * 获取玩家物品的最高强化等级
	 * 
	 * @param playerId
	 * @return
	 */
	public int getTopLevelForItemByPlayer(int playerId);

	/**
	 * 发送N天后过期的装备提醒
	 */
	public void checkOvertimeAfterNDay(int playerId);

	/**
	 * 保存物品进销记录
	 * 
	 * @param pifs
	 * @param days
	 * @param countNum
	 * @param getWay
	 * @param mark
	 */
	public void saveAndUpdatePifs(PlayerItemsFromShop pifs, int days, int countNum, int getWay, int mark);

	/**
	 * 改变装备属性
	 * 
	 * @param pifs
	 * @param stoneId
	 *            宝石ID
	 * @param mark
	 *            1表示添加属性，0表示删减属性
	 * @return
	 */
	public PlayerItemsFromShop changeParm(PlayerItemsFromShop pifs, int stoneId, int mark);

	/**
	 * 初始化数据
	 */
	public void initData();

	/**
	 * 根据ID获得viprate
	 * 
	 * @param id
	 * @return
	 */
	public VipRate getVipRateById(int id);

	/**
	 * 获取玩家的装备信息
	 * 
	 * @param playerId
	 * @param itemIds
	 * @return
	 */
	public List<PlayerItemsFromShop> getPlayerItem(Integer playerId, List<Integer> itemIds);

	/**
	 * 获得玩家物品MAP
	 * 
	 * @param playerId
	 * @param itemIds
	 * @return
	 */
	public Map<Integer, PlayerItemsFromShop> getPlayerItemMap(int playerId, int[] itemIds);

	/**
	 * 推送玩家物品列表到客户端
	 */
	public void sendPlayerItem(WorldPlayer player);

	/**
	 * 推送玩家物品更新信息到客户端
	 */
	public void sendUpdateItem(WorldPlayer player, int playerItemId, Map<String, String> info);

	/**
	 * 消耗物品
	 * 
	 * @param player
	 * @param playerItem
	 */
	public void useItem(WorldPlayer player, PlayerItemsFromShop playerItem);

	/**
	 * 删除物品
	 * 
	 * @param player
	 * @param playerItemId
	 * @param itemId
	 */
	public void removeItem(WorldPlayer player, int[] playerItemId, int[] itemId);

	/**
	 * 强化
	 * 
	 * @param player
	 * @param playerItem
	 */
	public void StrongItem(WorldPlayer player, PlayerItemsFromShop playerItem);

	/**
	 * 转移
	 * 
	 * @param player
	 * @param playerItem
	 */
	public void changeAttribute(WorldPlayer player, PlayerItemsFromShop source, PlayerItemsFromShop target);

	/**
	 * 镶嵌
	 * 
	 * @param player
	 * @param playerItem
	 */
	public void MosaicItem(WorldPlayer player, PlayerItemsFromShop playerItem);

	/**
	 * 打孔
	 * 
	 * @param player
	 * @param playerItem
	 */
	public void PunchItem(WorldPlayer player, PlayerItemsFromShop playerItem);

	public void sendAddItem(WorldPlayer player, PlayerItemsFromShop playerItem);

	/**
	 * 玩家卡牌
	 */
	public List<PlayerItemsFromShop> getPlayerCardList(int playerId);

	/**
	 * 玩家卡牌数量
	 * 
	 * @param playerId
	 * @return
	 */
	public int getPlayerCardCount(int playerId);

	/**
	 * 按品质取碎片
	 * 
	 * @return
	 */
	public List<PlayerItemsFromShop> getPlayerDebrisByLevel(int playerId, int level);

	/**
	 * 变更卡阵
	 * 
	 * @param oldCard
	 *            旧卡
	 * @param newCard
	 *            新卡
	 */
	public void changeCards(WorldPlayer player, PlayerItemsFromShop oldCard, PlayerItemsFromShop newCard);

	/**
	 * 升星
	 * 
	 * @param player
	 * @param playerItem
	 */
	public void upstarItem(WorldPlayer player, PlayerItemsFromShop playerItem);

	/**
	 * 根据id查找玩家物品
	 * 
	 * @param playerId
	 * @param id
	 * @return
	 */
	public PlayerItemsFromShop getPlayerItemById(int playerId, int id);

	/**
	 * 获取玩家物品最高星级
	 * 
	 * @param playerId
	 */
	public int getTopStarForPlayer(int playerId);

	/**
	 * 获取玩家在使用的物品
	 * 
	 * @param playerId
	 * @return
	 */
	public List<PlayerItemsFromShop> getInUseItem(int playerId);

	/**
	 * 置换玩家石头
	 * 
	 * @param player
	 */
	public void checkPlayerStone(WorldPlayer player);
}