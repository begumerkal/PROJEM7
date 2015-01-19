package com.wyd.empire.world.server.service.base;

import java.util.List;
import java.util.Properties;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.MarryRecord;
import com.wyd.empire.world.bean.WeddingHall;
import com.wyd.empire.world.player.WeddingRoom;
import com.wyd.empire.world.player.WorldPlayer;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IMarryService extends UniversalManager {

	public void saveMr(MarryRecord mr);

	/**
	 * 根据playerId获得结婚记录
	 * 
	 * @param playerId
	 * @return
	 */
	public List<MarryRecord> getMarryRecordByPlayerId(int sexmark, int playerId, int status);

	/**
	 * 根据playerId获得单个结婚记录
	 * 
	 * @param playerId
	 * @param sexmark
	 *            性别标记
	 * @return
	 */
	public MarryRecord getSingleMarryRecordByPlayerId(int sexmark, int playerId, int status);

	/**
	 * 根据婚姻的两个人获得对象
	 * 
	 * @param manId
	 * @param womanId
	 * @return
	 */
	public MarryRecord getMarryRecordByTWOPlayerId(int manId, int womanId);

	/**
	 * 删除多余的记录
	 * 
	 * @param playerId
	 * @return
	 */
	public void deleteMarryRecord(int sexmark, int playerId, int status);

	/**
	 * 删除过期的求婚记录
	 * 
	 * @return
	 */
	public void deleteMarryRecordByCreateTime();

	/**
	 * 离婚删除buff
	 * 
	 * @return
	 */
	public void deleteBuffRecord(int playerId);

	/**
	 * 判断用户是否已经结婚
	 * 
	 * @param playerId
	 * @return
	 */
	public boolean checkPlayerMarriage(int playerId);

	/**
	 * 批量获取结婚记录
	 */
	public void getSpouseId(List<Properties> list);

	/**
	 * 根据playerId获得结婚礼堂
	 * 
	 * @param playerId
	 * @param sexmark
	 *            性别标记
	 * @return
	 */
	public WeddingHall getWeddingHallByPlayerId(int sexmark, int playerId);

	/**
	 * 创建婚礼房间
	 * 
	 * @param mr
	 */
	public void createWeddingHall(MarryRecord mr, int timeId, int useDiamond);

	/**
	 * 获得红包金币数
	 * 
	 * @param wr
	 * @return
	 */
	public int givePeopleReward(WeddingRoom wr);

	/**
	 * 检查结婚礼堂
	 */
	public void checkWeddingRoom();

	/**
	 * 删除婚礼
	 * 
	 * @param mr
	 */
	public void deleteWeddingHall(MarryRecord mr);

	/**
	 * 初始化婚礼殿堂
	 */
	public void initWeddingRoom();

	/**
	 * 根据玩家参加的婚礼编号删除玩家
	 * 
	 * @param wedNum
	 */
	public void deleteWeddingRoomPlayer(WorldPlayer player);
}