package com.wyd.empire.world.server.service.base.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.DrawItem;
import com.wyd.empire.world.bean.DrawRate;
import com.wyd.empire.world.bean.DrawType;
import com.wyd.empire.world.bean.PlayerDraw;
import com.wyd.empire.world.dao.IDrawDao;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IDrawService;

public class DrawService extends UniversalManagerImpl implements IDrawService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IDrawDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "ConsortiarightService";

	public DrawService() {
		super();
	}

	/**
	 * Returns the singleton <code>IDrawService</code> instance.
	 */
	public static IDrawService getInstance(ApplicationContext context) {
		return (IDrawService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IDrawDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IDrawDao getDao() {
		return this.dao;
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
		dao.initData();
	}

	/**
	 * 获得玩家的奖励物品
	 * 
	 * @param playerId
	 * @return
	 */
	public List<DrawItem> toGetRewardByPlayerId(WorldPlayer player, int type) {
		return dao.getRewardByPlayerId(player.getId(), type);
	}

	/**
	 * 根据typeId获得抽奖类型
	 * 
	 * @param typeId
	 * @return
	 */
	public DrawType getDrawItemByItemId(int typeId) {
		return dao.getDrawItemByItemId(typeId);
	}

	/**
	 * 刷新抽奖列表
	 * 
	 * @param player
	 * @param typeId
	 *            抽奖类型
	 * @param refreshMark
	 *            刷新标识，0表示类型刷新，1表示所有物品刷新
	 */
	public List<DrawItem> refreshDrawItem(int playerId, int typeId, int refreshMark) {
		return dao.refreshDrawItem(playerId, typeId, refreshMark);
	}

	/**
	 * 获得玩家奖励物品
	 * 
	 * @param playerId
	 * @param starNum
	 * @param typeId
	 * @return
	 */
	public DrawItem getDrawRewardByPlayerIdAndStarNum(int playerId, int starNum, int typeId) {
		return dao.getDrawRewardByPlayerIdAndStarNum(playerId, starNum, typeId);
	}

	public DrawRate getDrawRateById(int id) {
		return dao.getDrawRateById(id);
	}

	/**
	 * 更新已抽中的物品
	 * 
	 * @param playerId
	 * @param typeId
	 * @param starNum
	 */
	public void updateDrawItem(int playerId, int typeId, int starNum, DrawItem di) {
		dao.updateDrawItem(playerId, typeId, starNum, di);
	}

	/**
	 * 获得玩家抽奖的记录
	 * 
	 * @param playerId
	 * @return
	 */
	public PlayerDraw checkPlayerDrawByPlayerId(int playerId) {
		PlayerDraw pd = dao.getPlayerDrawByPlayerId(playerId);
		if (null == pd) {
			pd = new PlayerDraw();
			pd.setPlayerId(playerId);
			pd.setDrawFailNum("");
			pd.setDrawNum("");
			pd.setRefreshNum("");
			pd.setStarNum("");
			pd = (PlayerDraw) save(pd);
		}
		return pd;
	}

	/**
	 * 保存抽奖记录
	 * 
	 * @param player
	 */
	@SuppressWarnings("rawtypes")
	public void savePlayerDraw(WorldPlayer player) {
		PlayerDraw pd = checkPlayerDrawByPlayerId(player.getId());

		Map<Integer, Integer> map = player.getDrawFailNumMap();
		Iterator it = map.entrySet().iterator();
		int index = 0;
		String markStr = "";
		Map.Entry entry;
		// 遍历四个MAP集，组装成string，保存到数据库
		while (it.hasNext()) {
			entry = (Map.Entry) it.next();
			String key = (String) entry.getKey().toString();
			String value = (String) entry.getValue().toString();
			index++;
			if (index < map.size()) {
				markStr += key + "=" + value + ",";
			} else {
				markStr += key + "=" + value;
			}
		}
		pd.setDrawFailNum(markStr);

		index = 0;
		markStr = "";

		map = player.getDrawNumMap();
		it = map.entrySet().iterator();

		while (it.hasNext()) {
			entry = (Map.Entry) it.next();
			String key = (String) entry.getKey().toString();
			String value = (String) entry.getValue().toString();
			index++;
			if (index < map.size()) {
				markStr += key + "=" + value + ",";
			} else {
				markStr += key + "=" + value;
			}
		}

		pd.setDrawNum(markStr);
		index = 0;
		markStr = "";

		map = player.getStarNumMap();
		it = map.entrySet().iterator();

		while (it.hasNext()) {
			entry = (Map.Entry) it.next();
			String key = (String) entry.getKey().toString();
			String value = (String) entry.getValue().toString();
			index++;
			if (index < map.size()) {
				markStr += key + "=" + value + ",";
			} else {
				markStr += key + "=" + value;
			}
		}

		pd.setStarNum(markStr);
		index = 0;
		markStr = "";

		map = player.getRefreshNumMap();
		it = map.entrySet().iterator();

		while (it.hasNext()) {
			entry = (Map.Entry) it.next();
			String key = (String) entry.getKey().toString();
			String value = (String) entry.getValue().toString();
			index++;
			if (index < map.size()) {
				markStr += key + "=" + value + ",";
			} else {
				markStr += key + "=" + value;
			}
		}
		pd.setRefreshNum(markStr);

		update(pd);
	}
}