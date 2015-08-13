package com.wyd.service.server.factory;

import com.app.db.service.UniversalManager;
import com.wyd.service.bean.Player;
import com.wyd.service.bean.PlayerDIYTitle;
import com.wyd.service.bean.PlayerTaskTitle;
import com.wyd.service.bean.PlayerVO;
import com.wyd.service.bean.Title;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface ITitleService extends UniversalManager {

	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	public static final String SERVICE_BEAN_ID = "TitleService";

	

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
	public String guildTitle(PlayerVO player);
	
	/**
    * 根据称号ID获取称号对象
    * @param titleId 称号ID
    * @return
    */
   public Title getTitleById(int titleId);
   
   /**
    *  取得玩家称号列表
    * @param playerId
    * @return
    */
   public PlayerTaskTitle getPlayerTaskTitle(int playerId);
   /**
    *  取玩家选择的自定义称号
    * @return
    */
   public PlayerDIYTitle getSelDIYTitle(int playerId);

}