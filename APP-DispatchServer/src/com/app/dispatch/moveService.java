package com.app.dispatch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import org.apache.log4j.Logger;
import com.app.dispatch.vo.Map;

/***
 * 玩家移动服务(同屏)
 * 
 * @author doter
 *
 */
public class moveService {
	private HashMap<Integer, Map> mapConfig = new HashMap<Integer, Map>();
	private static final Logger log = Logger.getLogger(moveService.class);

	public moveService() {
		try {
			this.loadMapConfig("map.txt");
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
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
}
