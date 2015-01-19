package com.wyd.empire.nearby.vo;

import java.util.ArrayList;
import java.util.List;

import com.wyd.empire.nearby.bean.PlayerInfo;
import com.wyd.empire.nearby.service.factory.ServiceManager;

public class PlayerVo {
	private PlayerInfo myInfo ;
	private List<PlayerInfoVo> friendList = new ArrayList<PlayerInfoVo>();
	private List<PlayerInfoVo> nearbyList = null;
	
	private int threshold;
	private int maxresults;
	
	private long nearbyListUpdateTime = 0;
	private long updatetime = 0;
	
	public PlayerVo(PlayerInfo myInfo) {
		this.myInfo = myInfo;
		this.threshold = Integer.parseInt(ServiceManager.getManager().getConfiguration().getValue("threshold"));
		this.maxresults = Integer.parseInt(ServiceManager.getManager().getConfiguration().getValue("maxresults"));
		this.updatetime = Integer.parseInt(ServiceManager.getManager().getConfiguration().getValue("updatetime"));
		initfriendList();
	} 
	
	public PlayerInfo getMyInfo() {
        return myInfo;
    }
	
    public void initfriendList(){
		String friendIdString = myInfo.getFriendId();
		List<PlayerInfoVo> playerList = new ArrayList<PlayerInfoVo>();
		if(friendIdString.length()>0){
			String[] friendIds = friendIdString.split(",");
			for(String friendId : friendIds){
				PlayerInfo playerInfo = (PlayerInfo)ServiceManager.getManager().getPlayerInfoManager().getBean(PlayerInfo.class, Integer.parseInt(friendId));
				if(null!=playerInfo){
					playerList.add(new PlayerInfoVo(playerInfo));
				}
			}
		}
        this.friendList = playerList;
	}
	
	public void initNearbyList() {
		if (System.currentTimeMillis() - nearbyListUpdateTime > updatetime) {
			List<PlayerInfo> playerInfoList = ServiceManager.getManager().getNearbyService().getNearbyPlayer(this.myInfo.getLongitude(), this.myInfo.getLatitude(), threshold, maxresults);
			List<PlayerInfoVo> playerList = new ArrayList<PlayerInfoVo>();
            for (PlayerInfo playerInfo : playerInfoList) {
                if (playerInfo.getId().intValue() != myInfo.getId().intValue()) playerList.add(new PlayerInfoVo(playerInfo));
            }
			this.nearbyList = playerList;
			this.nearbyListUpdateTime = System.currentTimeMillis();
		}
	}
	
	/**
	 * 获取玩家附近好友列表
	 * @return
	 */
	public List<PlayerInfoVo> getFriendList() {
		return friendList;
	}

	/**
	 * 获取附近玩家列表
	 * @return
	 */
	public List<PlayerInfoVo> getNearbyList() {
        initNearbyList();
		return nearbyList;
	}



	public enum GaussSphere{Beijing54,Xian80,WGS84} 

	/**
	 * 查询两点之间的距离
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @param gs
	 * @return 距离，单位（米）
	 */
	public static double DistanceOfTwoPoints(double lng1, double lat1, double lng2, double lat2, GaussSphere gs) {
		double radLat1 = Rad(lat1);
		double radLat2 = Rad(lat2);
		double a = radLat1 - radLat2;
		double b = Rad(lng1) - Rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * (gs == GaussSphere.WGS84 ? 6378137.0 : (gs == GaussSphere.Xian80 ? 6378140.0 : 6378245.0));
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	private static double Rad(double d) {
		return d * Math.PI / 180.0;
	}
	
	public class PlayerInfoVo {
		private PlayerInfo playerInfo;

		public PlayerInfoVo(PlayerInfo playerInfo) {
			this.playerInfo = playerInfo;
		}

		public PlayerInfo getPlayerInfo() {
            return playerInfo;
        }

        public int getDistance() {
            double mylon = myInfo.getLongitude()/1000000d;
            double mylat = myInfo.getLatitude()/1000000d;
            double otlon = playerInfo.getLongitude()/1000000d;
            double otlat = playerInfo.getLatitude()/1000000d;
			return (int) DistanceOfTwoPoints(mylon, mylat, otlon, otlat, GaussSphere.Beijing54);
		}

		public int getMailCount() {
			return ServiceManager.getManager().getMailManager().getPlayerNewSendMailCount(myInfo.getId(), playerInfo.getId());
		}

        public boolean isFriend() {
            String friendString = "," + myInfo.getFriendId() + ",";
            return friendString.indexOf("," + this.playerInfo.getId() + ",") > -1;
        }
	}
}
