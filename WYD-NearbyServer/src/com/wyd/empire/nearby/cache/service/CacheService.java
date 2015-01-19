package com.wyd.empire.nearby.cache.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;
import com.wyd.empire.nearby.bean.BaseBean;
import com.wyd.empire.nearby.nenum.BeanStatus;
import com.wyd.empire.nearby.service.factory.ServiceManager;
/**
 * 缓存服务基类
 * @author zguoqiu
 */
public class CacheService implements Runnable {
	private static final Logger log = Logger.getLogger(CacheService.class);
	Map<Integer, BaseBean> objectCache = new ConcurrentHashMap<Integer, BaseBean>();
	List<BaseBean> updateList = new ArrayList<BaseBean>();
	List<BaseBean> deleteList = new ArrayList<BaseBean>();
	private long sleepTime=60000;
	
	/**
	 * 设置bean的状态
	 * @param bean
	 * @param beanStatus
	 */
	public void setBeanStatus(BaseBean bean, int beanStatus){
		switch (beanStatus) {
		case BeanStatus.Initial:
			objectCache.put(bean.getId(), bean);
			break;
		case BeanStatus.Create:
			objectCache.put(bean.getId(), bean);
		case BeanStatus.Update:
			if(!updateList.contains(bean)) updateList.add(bean);
			break;
		case BeanStatus.Delete:
			if(objectCache.containsKey(bean.getId())) objectCache.remove(bean.getId());
			if(updateList.contains(bean)) updateList.remove(bean);
			if(!deleteList.contains(bean)) deleteList.add(bean);
			break;
		}
	}
	
	/**
	 * 根据id获取指定的bean，如该bean不在缓存则加入缓存
	 * @param bean
	 */
	public BaseBean getBean(Class<?> clazz, int id) {
		BaseBean bean = objectCache.get(id);
		if (null == bean) {
			bean = (BaseBean) ServiceManager.getManager().getNearbyService().get(clazz, id);
			if (null != bean) {
				setBeanStatus(bean, BeanStatus.Initial);
			} else {
				return null;
			}
		}
		return bean;
	}
	
	/**
	 * 将bean从缓存中移除
	 * @param id
	 */
	public void removeBean(BaseBean baseBean){
	    objectCache.remove(baseBean);
	}
	
    /**
     * 自动同步数据库时间(单位毫秒)
     * @param sleepTime
     */
    public void start(long sleepTime) {
        this.sleepTime = sleepTime;
        Thread t = new Thread(this);
        t.setName("CacheService-Thread");
        t.start();
    }

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(this.sleepTime);
				int size = updateList.size();
				for (int index = size - 1; index >= 0; index--) {
					BaseBean entity = updateList.remove(index);
					ServiceManager.getManager().getNearbyService().save(entity);
				}
				size = deleteList.size();
				for (int index = size - 1; index >= 0; index--) {
					BaseBean entity = deleteList.remove(index);
					ServiceManager.getManager().getNearbyService().remove(entity);
				}
			} catch (Throwable e) {
				e.printStackTrace();
				log.error(e, e);
			}
		}
	}
}
