package com.wyd.server.service;
import java.util.HashMap;
import java.util.Map;

//import com.wyd.server.util.CryptionUtil;
/**
 * @author doter
 */
public class UserInfoService {

	/**
	 * 将服务器信息转换为 字符串
	 * 
	 * @param serverInfo
	 * @param version
	 * @return
	 */
	public Map<String, Object> infoToString(ServerInfo serverInfo, String version, String channel) {
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
			dataMap.put("version", version);
			dataMap.put("area", lineInfo.getArea());
			dataMap.put("openudid", serverInfo.getConfig().getOpenudid());
			for (String value : ServiceManager.getManager().getConfigService().getConfigList()) {
				dataMap.put(value, value);
			}
		} else {
			dataMap.put("bulletin", serverInfo.getConfig().getBulletin());
		}
		return dataMap;
	}
}
