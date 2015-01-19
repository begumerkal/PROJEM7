package com.wyd.empire.world.server.service.base.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.protocol.data.wedding.ExtWeddingOk;
import com.wyd.empire.protocol.data.wedding.WeddingOver;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.bean.MarryRecord;
import com.wyd.empire.world.bean.WeddingHall;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.IMarryDao;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WeddingRoom;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IMarryService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * The service class for the TabConsortiaright entity.
 */
public class MarryService extends UniversalManagerImpl implements IMarryService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IMarryDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "MarryService";

	public static Map<String, WeddingRoom> weddingMap = new HashMap<String, WeddingRoom>();

	public static List<WeddingRoom> weddingList = new ArrayList<WeddingRoom>();

	public MarryService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IMarryService getInstance(ApplicationContext context) {
		return (IMarryService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IMarryDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IMarryDao getDao() {
		return this.dao;
	}

	public void saveMr(MarryRecord mr) {
		dao.save(mr);
	}

	/**
	 * 根据playerId获得结婚记录
	 * 
	 * @param playerId
	 * @return
	 */
	public List<MarryRecord> getMarryRecordByPlayerId(int sexmark, int playerId, int status) {
		return dao.getMarryRecordByPlayerId(sexmark, playerId, status);
	}

	/**
	 * 根据playerId获得单个结婚记录
	 * 
	 * @param playerId
	 * @param sexmark
	 *            性别标记
	 * @return
	 */
	public MarryRecord getSingleMarryRecordByPlayerId(int sexmark, int playerId, int status) {
		return dao.getSingleMarryRecordByPlayerId(sexmark, playerId, status);
	}

	/**
	 * 根据婚姻的两个人获得对象
	 * 
	 * @param manId
	 * @param womanId
	 * @return
	 */
	public MarryRecord getMarryRecordByTWOPlayerId(int manId, int womanId) {
		return dao.getMarryRecordByTWOPlayerId(manId, womanId);
	}

	/**
	 * 删除多余的记录
	 * 
	 * @param playerId
	 * @return
	 */
	public void deleteMarryRecord(int sexmark, int playerId, int status) {
		dao.deleteMarryRecord(sexmark, playerId, status);
	}

	/**
	 * 删除过期的求婚记录
	 * 
	 * @return
	 */
	public void deleteMarryRecordByCreateTime() {
		dao.deleteMarryRecordByCreateTime();
	}

	/**
	 * 离婚删除buff
	 * 
	 * @return
	 */
	public void deleteBuffRecord(int playerId) {
		dao.deleteBuffRecord(playerId);
	}

	/**
	 * 判断用户是否已经结婚
	 * 
	 * @param playerId
	 * @return
	 */
	public boolean checkPlayerMarriage(int playerId) {
		return dao.checkPlayerMarriage(playerId);
	}

	/**
	 * 批量获取结婚记录
	 */
	@Override
	public void getSpouseId(List<Properties> list) {
		List<Integer> ids = new ArrayList<Integer>();
		for (Properties p : list) {
			ids.add((Integer) p.get("id"));
		}
		List<MarryRecord> marryRecordList = dao.getMarryRecordByPlayerIds(ids);
		for (Properties p : list) {
			int id = (Integer) p.get("id");
			for (MarryRecord marryRecord : marryRecordList) {
				if (marryRecord.getManId() == id) {
					p.put("spouseId", marryRecord.getWomanId());
					break;
				} else if (marryRecord.getWomanId() == id) {
					p.put("spouseId", marryRecord.getManId());
					break;
				}
			}
			if (!p.containsKey("spouseId")) {
				p.put("spouseId", 0);
			}
		}
	}

	/**
	 * 检查结婚礼堂
	 */
	public void checkWeddingRoom() {
		String wedNum = "";
		List<WeddingRoom> removeList = new ArrayList<WeddingRoom>();
		for (WeddingRoom wr : weddingList) {
			if (wr.getWedHall().getEndTime().before(new Date())) {
				int bgId = 0, bgLevel = 0, bId = 0, bLevel = 0;
				WeddingOver weddingOver = new WeddingOver();
				// 给宾客发送
				for (WorldPlayer wp : wr.getPlayerList()) {
					wp.sendData(weddingOver);
				}
				// 给新郎发送
				WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(wr.getWedHall().getManId());
				if (null != worldPlayer) {
					bgId = worldPlayer.getId();
					bgLevel = worldPlayer.getLevel();
					worldPlayer.sendData(weddingOver);
				}
				// 给新娘发送
				worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(wr.getWedHall().getWomanId());
				if (null != worldPlayer) {
					bId = worldPlayer.getId();
					bLevel = worldPlayer.getLevel();
					worldPlayer.sendData(weddingOver);
				}
				wedNum = wr.getWedHall().getManId() + "" + wr.getWedHall().getWomanId();
				weddingMap.remove(wedNum);
				removeList.add(wr);
				if (null != wr.getWedHall() && null != wr.getWedHall().getId()) {
					remove(wr.getWedHall());
				}
				GameLogService.marry(bgId, bgLevel, bId, bLevel, wr.getWedHall().getWedtype(), wr.getUseDiamond(), wr.getBgGifts(),
						wr.getbGifts(), wr.getBgTime(), wr.getbTime());
			}
		}
		weddingList.removeAll(removeList);
	}

	/**
	 * 初始化婚礼殿堂
	 */
	public void initWeddingRoom() {
		weddingMap.clear();
		weddingList.clear();
		List<WeddingHall> list = dao.getWeddingHallByAreaId();
		for (WeddingHall wh : list) {
			WeddingRoom wr = new WeddingRoom();
			wr.setWedHall(wh);
			addWeddingRoom(wr);
		}

		checkWeddingRoom();
	}

	/**
	 * 添加婚礼房间
	 * 
	 * @param wedRoom
	 */
	public void addWeddingRoom(WeddingRoom wedRoom) {
		weddingMap.put(wedRoom.getWedHall().getManId() + "" + wedRoom.getWedHall().getWomanId(), wedRoom);
		weddingList.add(wedRoom);
	}

	/**
	 * 根据playerId获得结婚礼堂
	 * 
	 * @param playerId
	 * @param sexmark
	 *            性别标记
	 * @return
	 */
	public WeddingHall getWeddingHallByPlayerId(int sexmark, int playerId) {
		return dao.getWeddingHallByPlayerId(sexmark, playerId);
	}

	/**
	 * 创建婚礼房间
	 * 
	 * @param mr
	 */
	public void createWeddingHall(MarryRecord mr, int timeId, int useDiamond) {
		if (null != weddingMap.get(mr.getManId() + "" + mr.getWomanId())) {
			weddingList.remove(weddingMap.get(mr.getManId() + "" + mr.getWomanId()));
			weddingMap.remove(mr.getManId() + "" + mr.getWomanId());
		}
		// 保存婚礼
		WeddingHall wh = new WeddingHall();
		wh.setAreaId(Server.config.getMachineCode());
		String timeString = ServiceManager.getManager().getVersionService().getWedTimeById(timeId);
		if (timeString == null || timeString.length() == 0) {
			wh.setEndTime(DateUtil.parseDate("23:59:59"));
			wh.setStartTime(DateUtil.parseDate("22:00:00"));
		} else {
			String[] time = timeString.split("-");
			if (DateUtil.parseDate(time[0]).after(new Date())) {
				wh.setEndTime(DateUtil.parseDate(time[1]));
				wh.setStartTime(DateUtil.parseDate(time[0]));
			} else {
				// 如果时间已过，则推后一天举行
				wh.setEndTime(DateUtil.addMillisecond(DateUtil.parseDate(time[1]), 24 * 60 * 60 * 1000));
				wh.setStartTime(DateUtil.addMillisecond(DateUtil.parseDate(time[0]), 24 * 60 * 60 * 1000));
			}
		}
		wh.setManId(mr.getManId());
		wh.setWomanId(mr.getWomanId());
		wh.setRewardGoldNum(0);
		wh.setRewardNum(0);
		wh.setWedtype(mr.getType());
		wh = (WeddingHall) save(wh);
		// 创建婚礼房间
		WeddingRoom wr = new WeddingRoom();
		wr.setWedHall(wh);
		wr.setUseDiamond(useDiamond);
		addWeddingRoom(wr);
	}

	/**
	 * 获得红包金币数
	 * 
	 * @param wr
	 * @return
	 */
	public int givePeopleReward(WeddingRoom wr) {
		int avggold = wr.getWedHall().getAvgGoldNum();
		int othergold = ServiceUtils.getRandomNum(0, wr.getWedHall().getOtherGoldNum());
		int goldHigh = wr.getWedHall().getRewardGoldNum()
				* ServiceManager.getManager().getVersionService().getWedConfigByKey("singlereward") / 100;

		if (avggold + othergold > goldHigh) {
			return goldHigh;
		} else {
			return avggold + othergold;
		}
	}

	/**
	 * 删除婚礼
	 * 
	 * @param mr
	 */
	public void deleteWeddingHall(MarryRecord mr) {
		dao.deleteWeddingHall(mr);
	}

	/**
	 * 根据玩家参加的婚礼编号删除玩家
	 * 
	 * @param wedNum
	 */
	public void deleteWeddingRoomPlayer(WorldPlayer player) {
		WeddingRoom wr = weddingMap.get(player.getWedNum());
		if (null != wr) {
			wr.getPlayerList().remove(player);

			ExtWeddingOk extWeddingOk = new ExtWeddingOk();
			extWeddingOk.setPlayerId(player.getId());
			// 宾客退出则给现场每个人都刷新
			if (player.getId() != wr.getWedHall().getManId() && player.getId() != wr.getWedHall().getWomanId()) {
				// 给宾客发送
				for (WorldPlayer wp : wr.getPlayerList()) {
					wp.sendData(extWeddingOk);
				}
				// 给新郎发送
				WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(wr.getWedHall().getManId());
				worldPlayer.sendData(extWeddingOk);
				// 给新娘发送
				worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(wr.getWedHall().getWomanId());
				worldPlayer.sendData(extWeddingOk);
			}
		}
	}
}