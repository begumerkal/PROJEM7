package com.wyd.empire.world.server.service.base.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.bean.Bulletin;
import com.wyd.empire.world.bean.Push;
import com.wyd.empire.world.dao.IBulletinDao;
import com.wyd.empire.world.server.service.base.IBulletinService;

/**
 * The service class for the TabGuai entity.
 */
public class BulletinService extends UniversalManagerImpl implements IBulletinService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IBulletinDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "BulletinService";

	public BulletinService() {
		super();
	}

	/**
	 * Returns the singleton <code>IGuaiService</code> instance.
	 */
	public static IBulletinService getInstance(ApplicationContext context) {
		return (IBulletinService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IBulletinDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IBulletinDao getDao() {
		return this.dao;
	}

	public List<Bulletin> getBulletinList() {
		return dao.getBulletinList();
	}

	public List<Bulletin> getAllBulletinList() {
		return dao.getAllBulletinList();
	}

	/**
	 * 根据多个ID值删除公告
	 * 
	 * @param ids
	 *            多个公告ID，中间以,分割
	 */
	public void deleteBulletin(String ids) {
		dao.deleteBulletin(ids);
	}

	/**
	 * 增加一条push规则
	 * 
	 * @param pushId
	 * @param lostTime
	 * @param message
	 * @param isRepeat
	 */
	public void addPush(int lostTime, String message, int isRepeat) {
		Push push = new Push();
		push.setAreaId(WorldServer.config.getAreaId());
		push.setLostTime(lostTime);
		push.setMessage(message);
		push.setIsRepeat(isRepeat);
		push.setIsActivation(1);
		dao.save(push);
	}

	/**
	 * 移除一条push规则
	 * 
	 * @param pushId
	 */
	public void removePush(int pushId) {
		dao.remove(Push.class, pushId);
	}

	/**
	 * 更新一条push规则
	 * 
	 * @param push
	 */
	public void updatePush(Push push) {
		push.setAreaId(WorldServer.config.getAreaId());
		dao.update(push);
	}

	/**
	 * 获取Push规则列表
	 * 
	 * @return
	 */
	public List<Push> getAllPush() {
		return dao.getAllPushList();
	}

	/**
	 * 查询push规则
	 * 
	 * @param pushId
	 * @return
	 */
	public Push getPush(String pushId) {
		return (Push) dao.get(Push.class, pushId);
	}
}