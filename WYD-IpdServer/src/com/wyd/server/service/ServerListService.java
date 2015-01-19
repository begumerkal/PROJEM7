package com.wyd.server.service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 登录服务器列表维护
 * 
 * @since JDK 1.7
 */
public class ServerListService {
    private Map<String, Map<String, Map<Integer, ServerInfo>>> serverInfoMap = new ConcurrentHashMap<String, Map<String, Map<Integer, ServerInfo>>>();
    private Map<Integer, LineInfo>               lineInfoMap   = new ConcurrentHashMap<Integer, LineInfo>();

    public ServerListService(){
    	this.serverInfoMap = ServiceManager.getManager().getConfigService().getServerListMap();
    }

    /**
     * 根据随机率  随机出一个分区下的一台服务器
     * @param area
     */
    public ServerInfo getServerInfo(String area, String group) {
        Map<String, Map<Integer, ServerInfo>> groupMap = serverInfoMap.get(area);
        if (null != groupMap) {
            Map<Integer, ServerInfo> machineMap = groupMap.get(group);
            if (null != machineMap) {
            	int totalRandom = 0;
            	for (ServerInfo serverInfo : machineMap.values()) {
            		totalRandom += serverInfo.getConfig().getRandom();
				}
            	int random = (int)(Math.random() * totalRandom);
            	int i = 0;
            	for (ServerInfo serverInfo : machineMap.values()) {
            		i += serverInfo.getConfig().getRandom();
            		if(i >= random)
            			return serverInfo;
            	}
            }
        }
        return null;
    }

    /**
     * 获取一个测试分区
     * @param area
     */
    public ServerInfo getTestServerInfo(String area, String group) {
        Map<String, Map<Integer, ServerInfo>> groupMap = serverInfoMap.get(area);
        if (null != groupMap) {
            Map<Integer, ServerInfo> machineMap = groupMap.get(group);
            for (ServerInfo serverInfo : machineMap.values()) {
            	if (1 == serverInfo.getConfig().getIstest()) {
            		return serverInfo;
            	}
            }
        }
        return null;
    }

    /**
     * 获取指定的dispatch连接
     * 
     * @param id
     * @return
     */
    public LineInfo getLineInfoById(int id) {
        return lineInfoMap.get(id);
    }

    /**
     * 将新加登录服务器信息增加到服务器列表中
     * 
     * @param id       dispatch 编号
     * @param area     服务器所在分区名称
     * @param addressInfo   服务器地址端口信息
     * @param companyCode  公司码
     * @param machineCode   服务器码
     * @param version   版本号 
     * @param  serverid 
     */
    public synchronized void addServer(int id, String area, String group,int serverId, String version,
    		String updateurl,String appraisal,String bulletin
    		) {
        Map<String, Map<Integer, ServerInfo>> gMap = serverInfoMap.get(area);
        if (null != gMap) {
            Map<Integer, ServerInfo> sMap = gMap.get(group);
            if (null != sMap) {
                ServerInfo serverInfo = sMap.get(serverId);
                if (null != serverInfo) {
                    Map<Integer, LineInfo> lineMap = serverInfo.getLineMap();
                    LineInfo info;
                    if (lineMap.containsKey(id)) {
                        info = lineMap.get(id);
                    } else {
                        info = new LineInfo();
                        lineMap.put(id, info);
                        lineInfoMap.put(id, info);
                    }
                    info.setId(id);
                    info.setArea(area);
                    info.setGroup(group);
                    info.setServerId(serverId);
                    info.setVersion(version);
                    info.setUpdateurl(updateurl);
                    info.setAppraisal(appraisal);
                    serverInfo.getConfig().setBulletin(bulletin);
                    serverInfo.getConfig().setServerId(serverId);

                    System.out.println("AddServer 地区: " + area + "serverId: " + serverId + "线:" + id);
                }
            }
        }
    }

    /**
     * 移除 dispatch 连接
     * 
     * @param area
     * @param machine
     * @param id
     */
    public void removeServer(String area, String group, int machine, int id) {
        if (serverInfoMap.containsKey(area) && serverInfoMap.get(area).containsKey(group) 
        		&& serverInfoMap.get(area).get(group).containsKey(machine) 
        		&& serverInfoMap.get(area).get(group).get(machine).getLineMap().containsKey(id)) {
            serverInfoMap.get(area).get(group).get(machine).getLineMap().remove(id);
            lineInfoMap.remove(id);
            
            ServiceManager.getManager().getLineService().removeAll(id);
        }
    }

    /**
     * 获取服务器信息列表
     * 
     * @return
     */
    public Map<String, Map<String, Map<Integer, ServerInfo>>> getServerInfoMap() {
        return serverInfoMap;
    }
}
