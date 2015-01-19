package com.wyd.empire.world.server.service.base;

import java.util.List;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.Title;
import com.wyd.empire.world.bean.Tools;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.title.PlayerTitleInfo;
import com.wyd.empire.world.title.PlayerTitleVo;
import com.wyd.empire.world.title.TitleIng;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface ITitleService extends UniversalManager {
	/**
	 * 初始化称号基础数据
	 */
	public void initData();

	/**
	 * 获取玩家正经进行的成就任务
	 * 
	 * @param playerTitleVoList
	 * @return
	 */
	public List<TitleIng> createTitleByPlayer(List<PlayerTitleVo> playerTitleVoList);

	/**
	 * 检查战斗任务
	 * 
	 * @param battleTeam
	 * @return
	 */
	public void battleTask(BattleTeam battleTeam, int winCamp);

	/**
	 * 更新战斗中使用道具的任务
	 * 
	 * @param combat
	 * @param toolsList
	 */
	public void battleToolsTask(Combat combat, List<Tools> toolsList);

	/**
	 * 更新击退玩家的任务
	 * 
	 * @param combat
	 * @param combatList
	 * @param killType
	 *            0击杀，1坑杀
	 */
	public void battleBeatTask(Combat combat, Combat dead, int killType);

	/**
	 * 更新击退玩家的任务
	 * 
	 * @param player
	 * @param shootSex
	 * @param shootCamp
	 * @param deadPlayerId
	 * @param deadSex
	 * @param deadCamp
	 * @param killType
	 */
	public void battleBeatTask(WorldPlayer player, int shootSex, int shootCamp, int[] deadPlayerId, int[] deadSex, int[] deadCamp,
			int killType);

	/**
	 * 添加好友
	 * 
	 * @param player
	 */
	public void addFriend(WorldPlayer player);

	/**
	 * 使用钻石
	 * 
	 * @param player
	 * @param itemId
	 */
	public void useTicket(WorldPlayer player, int count);

	/**
	 * 使用物品
	 * 
	 * @param player
	 * @param itemId
	 */
	public void useSomething(WorldPlayer player, int itemId);

	/**
	 * 使用飞行技能
	 */
	public void flySkill(WorldPlayer player);

	/**
	 * 玩家升级
	 * 
	 * @param player
	 */
	public void upLevel(WorldPlayer player, int level);

	/**
	 * 玩家使用大招
	 * 
	 * @param player
	 */
	public void useBigSkill(WorldPlayer player);

	/**
	 * 完成任务
	 * 
	 * @param player
	 */
	public void completeTask(WorldPlayer player);

	/**
	 * 合成
	 * 
	 * @param player
	 */
	public void synthesis(WorldPlayer player);

	/**
	 * 强化
	 * 
	 * @param player
	 */
	public void strengthen(WorldPlayer player, int level);

	/**
	 * 击杀怪物
	 * 
	 * @param player
	 * @param gId
	 */
	public void killGuai(WorldPlayer player, int gId);

	/**
	 * 通关副本
	 * 
	 * @param player
	 * @param fbId
	 */
	public void tgfb(WorldPlayer player, int fbId);

	/**
	 * 复活
	 * 
	 * @param player
	 */
	public void fuHuo(WorldPlayer player);

	/**
	 * 更新创建或加入公会的任务
	 * 
	 * @param player
	 */
	public void joinConsortiaTask(WorldPlayer player);

	/**
	 * 更新创建或加入公会的任务
	 * 
	 * @param player
	 */
	public void leaveConsortiaTask(WorldPlayer player);

	/**
	 * 订婚
	 * 
	 * @param player
	 */
	public void marryTask(WorldPlayer player);

	/**
	 * 离婚
	 * 
	 * @param player
	 */
	public void divorce(WorldPlayer player);

	/**
	 * 获取婚姻称号
	 * 
	 * @param player
	 * @return
	 */
	public String getMarryTitle(Player player);

	/**
	 * 获取公会称号
	 * 
	 * @return
	 */
	public String guildTitle(WorldPlayer player);

	/**
	 * 根据称号ID获取称号对象
	 * 
	 * @param titleId
	 *            称号ID
	 * @return
	 */
	public Title getTitleById(int titleId);

	/**
	 * 设置玩家成就完成情况列表
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 玩家成就完成情况列表
	 */
	public List<PlayerTitleInfo> getPlayerTitleInfoList(int playerId);

	/**
	 * 更新玩家称号信息
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param titleIndex
	 *            成就列表索引
	 * @param titleId
	 *            成就ID
	 * @param titleTargetStatus
	 *            成就任务完成情况
	 * @param titleStatus
	 *            成就完成状态
	 * @param isNew
	 *            是否标记为新
	 * @return 是否更新成功
	 */
	public boolean updateTitleByGm(int playerId, int titleIndex, int titleId, String titleTargetStatus, byte titleStatus, boolean isNew);

	/**
	 * 开通会员
	 * 
	 * @param player
	 */
	public void openVIP(WorldPlayer player);

	/**
	 * 转生
	 * 
	 * @param player
	 */
	public void rebirth(WorldPlayer player);

	/**
	 * 升星
	 * 
	 * @param player
	 */
	public void upgrade(WorldPlayer player, int star);

	/**
	 * 排位赛第一名
	 */
	public void rankFirst(WorldPlayer player);

	/**
	 * 宠物升级
	 * 
	 * @param player
	 */
	public void upPetLevel(WorldPlayer player, int level);

	/**
	 * 弹王挑战赛第一名
	 */
	public void inteRankFirst(WorldPlayer player);

	/**
	 * 巨龙遗迹探险
	 * 
	 * @param player
	 */
	public void exploreDragon(WorldPlayer player, int star);

	/**
	 * 黄金森林探险
	 * 
	 * @param player
	 */
	public void exploreGold(WorldPlayer player, int star);

	/**
	 * 海贼宝藏探险
	 * 
	 * @param player
	 */
	public void explorePirates(WorldPlayer player, int star);

	/**
	 * 同步新增加的称号列表
	 * 
	 * @param player
	 *            玩家信息
	 * @return 增加称号数量
	 */
	public int synchronousTitle(WorldPlayer worldPlayer);

	/**
	 * 活跃值
	 * 
	 * @param player
	 * @param val
	 */
	public void activity(WorldPlayer player, int val);

}