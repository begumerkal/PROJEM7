package com.wyd.service.server.factory;
import java.util.List;

import com.app.db.page.PageList;
import com.app.db.service.UniversalManager;
import com.wyd.service.bean.Player;
/**
 * The service interface for the TabPlayeritemsfromshop entity.
 */
public interface IPlayerService extends UniversalManager {
	/**
     * The service Spring bean id, used in the applicationContext.xml file.
     */
	public static final String     SERVICE_BEAN_ID = "PlayerService";
	public Player getById(int playerId);
	public List<Player> getByAccountId(int accountId);
	public PageList findPageList(int areaId,int index,int size);
	
}