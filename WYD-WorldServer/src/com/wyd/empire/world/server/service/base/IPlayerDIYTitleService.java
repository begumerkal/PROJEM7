package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.PlayerDIYTitle;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IPlayerDIYTitleService extends UniversalManager {
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	static final String SERVICE_BEAN_ID = "PlayerDIYTitleService";

	/**
	 * 取玩家没过期的称号
	 * 
	 * @param playerId
	 * @return
	 */
	public List<PlayerDIYTitle> getDIYTitles(int playerId);

	/**
	 * 取所有玩家没过期的称号
	 * 
	 * @return
	 */
	public List<PlayerDIYTitle> getDIYTitles();

	/**
	 * 取玩家已选择的称号
	 * 
	 * @param playerId
	 * @return
	 */
	public PlayerDIYTitle getSelDIYTitle(int playerId);

	/**
	 * 选择称号 把传过来的ID更新为选择，其它的更新为未选择。
	 * 
	 * @param titleId
	 * @return
	 */
	public String selectTitle(int playerId, int titleId);

	/**
	 * 检查称号是否已存在
	 * 
	 * @param playerId
	 * @param title
	 * @return
	 */
	public PlayerDIYTitle getTitleByName(int playerId, String title);

}