package com.wyd.empire.world.server.service.base.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.KeywordsUtil;
import com.wyd.empire.world.dao.IRandomNameDao;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.server.service.base.IRandomNameService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * The service class for the TabConsortiaright entity.
 */
public class RandomNameService extends UniversalManagerImpl implements IRandomNameService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IRandomNameDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "RandomNameService";

	public RandomNameService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IRandomNameService getInstance(ApplicationContext context) {
		return (IRandomNameService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IRandomNameDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IRandomNameDao getDao() {
		return this.dao;
	}

	/**
	 * 是否为日文服
	 * 
	 * @return
	 */
	private boolean isJapan() {
		String serverId = WorldServer.config.getAreaId().toUpperCase();
		return serverId.startsWith("JA") || serverId.startsWith("JP");
	}

	/**
	 * 获取随机名称
	 * 
	 * @param sex
	 *            角色性别
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getName(int sex) throws Exception {
		String sql = "SELECT name FROM tab_randomname AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM tab_randomname)-(SELECT MIN(id) FROM tab_randomname))+(SELECT MIN(id) FROM tab_randomname)) AS id) AS t2 WHERE t1.id >= t2.id and type=2 or type=? order by RAND() LIMIT 2";
		int i = 0;
		String name = "";
		int nameLeng = 8;
		while (name.length() <= 0 || name.length() > nameLeng) {
			i++;
			if (i > 30) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.RANDOMNAME_FAIL_MESSAGE);
			}
			List<Object> rNameList = dao.getListBySql(sql, new Object[]{sex});
			if (rNameList.size() > 1) {
				if (isJapan()) {
					// 日本服是姓+名(type=3为姓氏)
					List<Object> surnames = dao.getListBySql(sql, new Object[]{3});
					if (surnames != null && surnames.size() > 0) {
						name = surnames.get(0).toString() + rNameList.get(0).toString();
					} else {
						System.out.println("姓氏为空！");
					}
				} else {
					name = rNameList.get(0).toString() + rNameList.get(1).toString();
				}
				// 限制名字长度，英文16个字符非英文8个字符
				if (name.matches("[a-zA-Z]+")) {
					nameLeng = 16;
				} else {
					nameLeng = 8;
				}
				if (!ServiceManager.getManager().getPlayerService().getService().checkName(name)) {
					name = "";
				}
				// 非法字符判断
				if (KeywordsUtil.isInvalidName(name)) {
					name = "";
				}
			}
		}
		return name;
	}
}