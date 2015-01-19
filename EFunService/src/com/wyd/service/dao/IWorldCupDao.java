package com.wyd.service.dao;
import com.wyd.db.dao.UniversalDao;
import com.wyd.service.bean.WorldCupCode;
import com.wyd.service.bean.WorldCupPoints;
/**
 * The DAO interface for the TabPlayeritemsfromshop entity.
 */
public interface IWorldCupDao extends UniversalDao {
	
	public WorldCupPoints getByAccountId(String accountId);
	
	public WorldCupCode getCode(int goals);
}