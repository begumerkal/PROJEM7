package com.wyd.channel.dao.impl;

import com.wyd.channel.dao.IThirdConfigDao;
import com.wyd.db.mysql.dao.impl.UniversalDaoHibernate;

/**
 * 第三方渠道配置信息数据库操作实现类
 * @author doter
 */
public class ThirdConfigDao extends UniversalDaoHibernate implements IThirdConfigDao {
	public ThirdConfigDao() {
		super();
	}
}