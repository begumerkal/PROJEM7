package com.app.dispatch;

import io.netty.channel.Channel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.app.dispatch.vo.ClientInfo;
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
	private final int width = 10;// 区块宽
	private final int high = 10;// 区块高
	private HashMap<Integer, Map> mapConfig = new HashMap<Integer, Map>();
	private static final Logger log = Logger.getLogger(SyncService.class);
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
	 * @param toWidth
	 * @param toHigh
	 */
	public void broadcastingMove(ClientInfo clientInfo, int toWidth, int toHigh) {
		Player player = clientInfo.getPlayer();
		ArrayList<String> viewList = this.getPlayerView(player.getMapId(), player.getWidth(), player.getHigh());// 获取视野范围
		ConcurrentHashMap<String, Vector<ClientInfo>> map = mapPieceToPlayer.get(player.getMapId());
		if (map != null) {
			Move move = new Move();
			move.setPlayerId(player.getPlayerId());
			move.setDirection(player.getDirection());
			move.setWidth(toWidth);
			move.setHigh(toHigh);
			for (String string : viewList) {
				Vector<ClientInfo> vec = map.get(string);
				if (vec != null) {
					for (ClientInfo client : vec) {
						Channel channel = client.getChannel();
						ProtocolManager.makeSegment(move).getPacketByteArray();
						channel.write(ByteBuffer.wrap(ProtocolManager.makeSegment(move).getPacketByteArray()));
					}
				}
			}
		}
	}

	/***
	 * 客户端报告位置转发新视野
	 * 
	 * @param clientInfo
	 * @param nowWidth
	 * @param nowHigh
	 */
	public void reportPlace(ClientInfo clientInfo, int nowWidth, int nowHigh) {
		Player player = clientInfo.getPlayer();
		int mapId = player.getMapId();
		ArrayList<String> oldViewList = this.getPlayerView(mapId, player.getWidth(), player.getHigh());// 获取视野范围
		ArrayList<String> newViewList = this.getPlayerView(mapId, nowWidth, nowHigh);// 新的视野范围
		ArrayList<String> viewList = new ArrayList<String>();// 获取新增视野范围
		viewList.addAll(newViewList);
		viewList.removeAll(oldViewList);
		ConcurrentHashMap<String, Vector<ClientInfo>> map = mapPieceToPlayer.get(player.getMapId());
		if (map != null) {
			Move move = new Move();
			move.setPlayerId(player.getPlayerId());
			move.setDirection(player.getDirection());
			move.setWidth(player.getToWidth());
			move.setHigh(player.getToHigh());
			for (String string : viewList) {
				Vector<ClientInfo> vec = map.get(string);
				if (vec != null) {
					for (ClientInfo client : vec) {
						Channel channel = client.getChannel();
						ProtocolManager.makeSegment(move).getPacketByteArray();
						channel.write(ByteBuffer.wrap(ProtocolManager.makeSegment(move).getPacketByteArray()));
					}
				}
			}
			// 说明移动到了新的区块中
			if (viewList.size() > 0) {
				// map 区块中删除用户
				this.delPlayerSessionToMapPiece(mapId, player.getWidth(), player.getHigh(), clientInfo);
				// 把用户增加到地图所在的块中
				this.addPlayerSessionToMapPiece(mapId, nowWidth, nowHigh, clientInfo);
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


		// map 区块中删除用户
		this.delPlayerSessionToMapPiece(player.getMapId(), randWidth, randHigh, clientInfo);
		// 把用户增加到地图所在的块中
		this.addPlayerSessionToMapPiece(mapId, randWidth, randHigh, clientInfo);
		
		player.setMapId(mapId);
		player.setWidth(randWidth);
		player.setHigh(randHigh);
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

		int widthNum = (int) Math.ceil((double) width / this.width);// 玩家宽度格子数
		int highNum = (int) Math.ceil((double) high / this.high);// 玩家高度格子数

		ConcurrentHashMap<String, Vector<ClientInfo>> map = this.mapPieceToPlayer.get(mapId);
		if (map != null) {
			Vector<ClientInfo> vec = map.get(widthNum + "-" + highNum);
			if (vec != null) {
				vec.add(clientInfo);
			} else {
				Vector<ClientInfo> newVec = new Vector<ClientInfo>();
				newVec.add(clientInfo);
				map.put(widthNum + "-" + highNum, newVec);
			}
		} else {
			ConcurrentHashMap<String, Vector<ClientInfo>> newMap = new ConcurrentHashMap<String, Vector<ClientInfo>>();
			Vector<ClientInfo> vec = new Vector<ClientInfo>();
			vec.add(clientInfo);
			newMap.put(widthNum + "-" + highNum, vec);
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
		int widthNum = (int) Math.ceil((double) width / this.width);// 玩家宽度格子数
		int highNum = (int) Math.ceil((double) high / this.high);// 玩家高度格子数

		if (map != null) {
			Vector<ClientInfo> vec = map.get(widthNum + "-" + highNum);
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

		System.out.println(arr);
	}

}
