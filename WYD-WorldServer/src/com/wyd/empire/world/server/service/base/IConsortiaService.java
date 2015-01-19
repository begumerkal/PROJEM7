package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.BuffRecord;
import com.wyd.empire.world.bean.Consortia;
import com.wyd.empire.world.bean.ConsortiaSkill;
import com.wyd.empire.world.consortia.MaxPrestigeVo;
import com.wyd.empire.world.player.WorldPlayer;

/**
 * The service interface for the TabConsortia entity.
 */
public interface IConsortiaService extends UniversalManager {

	/**
	 * 获得公会列表
	 * 
	 * @return
	 */
	public PageList getConsortiaList(int pageNum, int mark);

	/**
	 * 根据威信大小进行排序，获得公会列表
	 * 
	 * @return 公会列表
	 */
	public List<Consortia> getConsortiaList();

	/**
	 * 根据公会名称获得公会对象
	 * 
	 * @param name
	 *            公会名称
	 * @return 公会对象
	 */
	public Consortia getConsortiaByName(String name);

	/**
	 * 创建公会
	 * 
	 * @param communityName
	 * @param playerId
	 * @return
	 */
	public Integer createCommunity(String communityName, int playerId);

	/**
	 * 更改新的会长的职位（会长让位）
	 * 
	 * @param consortiaId
	 *            公会ID号
	 * @param newPId
	 *            新会长玩家ID号
	 */
	public void changePresident(int newPId, int oldPId);

	/**
	 * 退出公会
	 * 
	 * @param playerId
	 * @param executorPlayer
	 *            操作人
	 */
	public void exitCommunity(int playerId, WorldPlayer executorPlayer);

	/**
	 * 根据公会ID查询对应公会排名
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @param mark
	 *            0 威望，1胜利
	 * @return 对应公会排名
	 */
	public Integer getRankNum(int consortiaId, int mark);

	/**
	 * 更新公会排名
	 */
	public void updateCommunityRank();

	/**
	 * 申请入会
	 * 
	 * @param consortiaId
	 * @param playerId
	 */
	public void applyJoinCommunity(int consortiaId, int playerId);

	/**
	 * 根据公会ID获得此ID以外的所有公会列表
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @return 所有公会列表
	 */
	public List<Consortia> getConsortiaOthers(int consortiaId);

	/**
	 * 根据玩家Id获取公会对象
	 * 
	 * @param playerId
	 *            玩家Id
	 * @return 公会对象
	 */
	public Consortia getConsortiaByPlayerId(int playerId);

	/**
	 * 根据玩家Id获取公会名称
	 * 
	 * @param playerId
	 *            玩家Id
	 * @return 公会名称,ID
	 */
	public Object[] getConsortiaNameAndIdByPlayerId(int playerId);

	/**
	 * 根据公会ID，搜索公会信息
	 * 
	 * @param consortiaId
	 *            公司ID
	 * @return 对应公会信息
	 */
	public Consortia getConsortiaById(int consortiaId);

	/**
	 * 获得玩家的buff记录
	 * 
	 * @param playerId
	 * @return
	 */
	public List<BuffRecord> getBuffRecordByPlayerId(int playerId);

	/**
	 * 删除过期的玩家的buff记录
	 * 
	 * @param playerId
	 * @return
	 */
	public void deleteBuffRecordOverTime(int playerId);

	/**
	 * 将每日贡献度清零
	 */
	public void updateEverydayAddToZero();

	/**
	 * 获取敌对工会列表
	 */
	public List<Consortia> getHosConsortia(int hosId, int conId);

	/**
	 * 获得当日工会战参数
	 * 
	 * @param mark
	 * @return
	 */
	public int getTodayConsortiaBattle(int mark);

	/**
	 * 判断两个公会是否是敌对工会
	 * 
	 * @param consortiaId1
	 * @param consortiaId2
	 * @return
	 */
	public boolean checkEnemyConsortia(int consortiaId1, int consortiaId2);

	/**
	 * 保存公会战日志
	 * 
	 * @param playerId
	 * @param status
	 * @param vsType
	 */
	public void saveConsortiaBattleLog(WorldPlayer player, boolean status, int vsType);

	/**
	 * 记录公会战斗情况
	 * 
	 * @param consortiaId
	 * @param num
	 */
	public void updateConsortiaBattleNum(int consortiaId, int num);

	/**
	 * 获得公会技能列表
	 * 
	 * @param communityId
	 * @return
	 */
	public List<ConsortiaSkill> getConsortiaSkill();

	/**
	 * 添加一条公会技能数据
	 * 
	 * @param skill
	 */
	public void addConsortiaSkill(ConsortiaSkill skill);

	/**
	 * 更新公会技能
	 * 
	 * @param skill
	 */
	public void updateConsortiaSkill(ConsortiaSkill skill);

	/**
	 * 获取公会技能分页列表
	 * 
	 * @param pageNum
	 * @return
	 */
	public PageList getConsortiaListSkill(int pageNum, int pageSize);

	/**
	 * 获取公会列表
	 * 
	 * @return 公会列表
	 */
	public PageList getConsortiaListByPage(int pageNum, int pageSize);

	/**
	 * 根据公会id或名称查询公会
	 * 
	 * @param key
	 * @return
	 */
	public List<Consortia> getConsortiaByKey(String key);

	/**
	 * 获取当前服威望最高的公会及会长
	 */
	public MaxPrestigeVo getMaxPrestige();

}