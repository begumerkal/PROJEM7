package com.app.dispatch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.app.dispatch.SocketDispatcher.ClientInfo;
import com.app.dispatch.vo.Map;
import com.app.dispatch.vo.Player;
import com.app.empire.protocol.data.account.Move;
import com.app.protocol.ProtocolManager;

/***
 * 玩家同屏同步服务
 * 
 * @author doter
 *
 */
public class SyncService {
	private final int width = 6;// 区块宽
	private final int high = 6;// 区块高
	private HashMap<Integer, Map> mapConfig = new HashMap<Integer, Map>();
	private static final Logger log = Logger.getLogger(SyncService.class);
	// /*** mapid-sessionid-ioSession map 中的所有用户 */
	// private ConcurrentHashMap<String, ConcurrentHashMap<Integer, IoSession>>
	// mapToPlayer = new ConcurrentHashMap<String, ConcurrentHashMap<Integer,
	// IoSession>>();
	/*** map　中区块的所有用户 地图id->块id->ioSession ****/
	private ConcurrentHashMap<Integer, ConcurrentHashMap<String, Vector<ClientInfo>>> mapPieceToPlayer = new ConcurrentHashMap<Integer, ConcurrentHashMap<String, Vector<ClientInfo>>>();

	public SyncService() {
		try {
			this.loadMapConfig("map.txt");
			this.initMap();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/***
	 * 广播移动数据到此玩家附近的玩家
	 * 
	 * @param clientInfo
	 */
	public void broadcastingMove(ClientInfo clientInfo) {
		Player player = clientInfo.getPlayer();
		ArrayList<String> viewlist = this.getPlayerView(player.getMapId(), player.getWidth(), player.getHigh());// 获取视野范围
		ConcurrentHashMap<String, Vector<ClientInfo>> map = mapPieceToPlayer.get(player.getMapId());
		if (map != null) {
			Move move = new Move();
			move.setPlayerId(player.getPlayerId());
			move.setDirection(player.getDirection());
			move.setWidth(player.getWidth());
			move.setHigh(player.getHigh());
			for (String string : viewlist) {
				Vector<ClientInfo> vec = map.get(string);
				if (vec != null) {
					for (ClientInfo client : vec) {
						IoSession iosession = client.getIoSession();
						ProtocolManager.makeSegment(move).getPacketByteArray();
						iosession.write(IoBuffer.wrap(ProtocolManager.makeSegment(move).getPacketByteArray()));
					}
				}
			}
		}
	}
	/**
	 * 广播玩家属性等数据到附近的玩家
	 */
	public void broadcastingProperty(ClientInfo clientInfo) {

	}
	
	
	

	/*** 跳地图 */
	public void jumpMap(ClientInfo clientInfo, int mapId) {
		Player player = clientInfo.getPlayer();
		// 随机生产着落点
		int randWidth = (int) (Math.random() * 500);
		int randHigh = (int) (Math.random() * 300);
		player.setMapId(mapId);
		player.setWidth(randWidth);
		player.setHigh(randHigh);

		// map 区块中删除用户
		this.delPlayerSessionToMapPiece(mapId, randWidth, randHigh, clientInfo);
		// 把用户增加到地图所在的块中
		this.addPlayerSessionToMapPiece(mapId, randWidth, randHigh, clientInfo);
	}

	/***
	 * map 区块中添加用户
	 * 
	 * @param ioSession
	 */
	public void addPlayerSessionToMapPiece(Integer mapId, int width, int high, ClientInfo clientInfo) {
		Player player = clientInfo.getPlayer();
		player.setWidth(width);
		player.setHigh(high);

		ConcurrentHashMap<String, Vector<ClientInfo>> map = this.mapPieceToPlayer.get(mapId);
		if (map != null) {
			Vector<ClientInfo> vec = map.get(width + "-" + high);
			if (vec != null) {
				vec.add(clientInfo);
			} else {
				Vector<ClientInfo> newVec = new Vector<ClientInfo>();
				newVec.add(clientInfo);
				map.put(width + "-" + high, newVec);
			}
		} else {
			ConcurrentHashMap<String, Vector<ClientInfo>> newMap = new ConcurrentHashMap<String, Vector<ClientInfo>>();
			Vector<ClientInfo> vec = new Vector<ClientInfo>();
			vec.add(clientInfo);
			newMap.put(width + "-" + high, vec);
			this.mapPieceToPlayer.put(mapId, newMap);
		}
	}

	/***
	 * map 区块中删除用户
	 * 
	 * @param ioSession
	 */
	public void delPlayerSessionToMapPiece(Integer mapId, int width, int high, ClientInfo clientInfo) {
		ConcurrentHashMap<String, Vector<ClientInfo>> map = this.mapPieceToPlayer.get(mapId);
		if (map != null) {
			Vector<ClientInfo> vec = map.get(width + "-" + high);
			if (vec != null) {
				vec.remove(clientInfo);
			}
		}
	}

	/****
	 * 获取玩家所在的九宫格
	 * 
	 * @param width
	 *            玩家所在的高度
	 * @param high
	 *            　　玩家所在的宽度
	 * @return
	 */
	public ArrayList<String> getPlayerView(int mapId, int width, int high) {
		ArrayList<String> view = new ArrayList<String>();
		int widthNum = (int) Math.ceil((double) width / this.width);// 玩家宽度格子数
		int highNum = (int) Math.ceil((double) high / this.high);// 玩家高度格子数

		Map map = mapConfig.get(mapId);
		if (map == null) {
			return view;
		}

		int mapWidthNum = map.getWidthNum();
		int mapHighNum = map.getHighNum();

		int i = -1;
		int rWidth = 0;
		int rhigh = 0;
		for (int x = 0; x < 3; x++) {
			rWidth = x + i + widthNum;
			if (rWidth < 1 || rWidth > mapWidthNum)
				continue;
			for (int y = 0; y < 3; y++) {
				rhigh = y + i + highNum;
				if (rhigh < 1 || rhigh > mapHighNum)
					continue;
				view.add(rWidth + "-" + rhigh);
			}
		}
		return view;
	}

	/***
	 * 加载地图配置
	 * 
	 * @param fname
	 * @throws Exception
	 */
	private void loadMapConfig(String fname) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(Thread.currentThread().getContextClassLoader().getResource(fname)
				.getPath()));
		while (true) {
			String line = reader.readLine();
			if (line == null)
				break;
			String secs[] = line.split("-");
			if (secs.length == 4) {
				Map map = new Map();
				map.setId(Integer.valueOf(secs[0]));
				map.setWidth(Integer.valueOf(secs[1]));
				map.setHigh(Integer.valueOf(secs[2]));
				map.setMaxPlayer(Integer.valueOf(secs[3]));
				mapConfig.put(map.getId(), map);
			}
		}
		reader.close();
	}
	/***
	 * 初始化地图（分块）
	 */
	private void initMap() {
		for (Entry<Integer, Map> entry : mapConfig.entrySet()) {
			Map mapObj = entry.getValue();
			int mapWidth = mapObj.getWidth();
			int mapHigh = mapObj.getHigh();
			int widthNum = (int) Math.ceil((double) mapWidth / this.width);// 宽度格子数
			int highNum = (int) Math.ceil((double) mapHigh / this.high);// 高度格子数
			mapObj.setWidthNum(widthNum);
			mapObj.setHighNum(highNum);
		}
	}

	public static void main(String[] args) {
		SyncService sync = new SyncService();
		ArrayList<String> arr = sync.getPlayerView(1, 100, 100);

		System.out.println(sync.mapConfig.get(1).getHighNum());
		System.out.println(arr);
	}

}
