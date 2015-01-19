package com.wyd.empire.world.server.service.base;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.StoneRate;
import com.wyd.empire.world.bean.WeapSkill;
import com.wyd.empire.world.player.WorldPlayer;

public interface IStrengthenService extends UniversalManager {

	/**
	 * 根据随机数获得洗练对象
	 * 
	 * @param randomNum
	 * @return
	 */
	public WeapSkill getWeapSkillByRandom(int randomNum);

	/**
	 * 玩家强化获得洗练技能
	 * 
	 * @param strongeLevel
	 * @param startLevel
	 * @param pifs
	 */
	public PlayerItemsFromShop giveWeapSkillToPlayer(int strongeLevel, PlayerItemsFromShop pifs);

	/**
	 * 强化失败清除洗练技能
	 * 
	 * @param pifs
	 */
	public void deleteWeapSkill(PlayerItemsFromShop pifs);

	/**
	 * 根据类型和数量获得相应石头的概率
	 * 
	 * @param type
	 * @param num
	 * @return
	 */
	public StoneRate getStoneRateByNumAndType(int type, int num);

	/**
	 * 初始化数据
	 */
	public void initData();

	public WeapSkill getWeapSkillById(int itemId);

	/**
	 * 获取武器被动技能分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageList getWeapSkillList(int pageNum, int pageSize);

	/**
	 * 获得强化加成数据
	 * 
	 * @param item
	 *            物品
	 * @param level
	 *            强化等级
	 * @param type
	 *            属性类型 1攻击，2血量，3防御 ，4暴击，5免暴,6破防
	 * @return
	 */
	public int getStrengthenAddition(ShopItem item, int level, int type);

	public void sendRate(WorldPlayer player);

}