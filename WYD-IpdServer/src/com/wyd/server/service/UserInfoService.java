package com.wyd.server.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.wyd.server.util.CryptionUtil;
/**
 * @author doter
 */
public class UserInfoService {

	/**
	 * 获取服务器线中人数最少的一条链接消息给前端
	 * 
	 * @param serverInfo
	 * @param version
	 * @return
	 */
	public Map<String, Object> getLineInfo(ServerInfo serverInfo, String version, String channel) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		LineInfo lineInfo = null;

		// 分配线 （用户量少的）
		Map<Integer, LineInfo> lineMap = serverInfo.getLineMap();
		for (LineInfo info : lineMap.values()) {
			if (info.getMaintance())// 维护
				continue;
			if (info.getVersion().equals(version)) {
				if (lineInfo == null || info.getCurrOnline() < lineInfo.getCurrOnline())
					lineInfo = info;
			} else {
				if (lineInfo == null || info.getCurrOnline() < lineInfo.getCurrOnline())
					lineInfo = info;
			}
		}

		if (null != lineInfo) {
			dataMap.put("address", lineInfo.getAddress());
			dataMap.put("version", lineInfo.getVersion());
			dataMap.put("area", lineInfo.getArea());
			dataMap.put("openudid", serverInfo.getConfig().getOpenudid());
			dataMap.put("group", lineInfo.getGroup());
			dataMap.put("serverId", lineInfo.getServerId());
			
			List<String>  appendConfig = ServiceManager.getManager().getConfigService().getConfigList();
			dataMap.put("append", appendConfig);
			
		} else {
			dataMap.put("bulletin", serverInfo.getConfig().getBulletin());
		}
		return dataMap;
	}
}
