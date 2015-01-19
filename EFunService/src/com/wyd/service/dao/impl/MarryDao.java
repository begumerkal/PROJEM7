package com.wyd.service.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.service.bean.MarryRecord;
import com.wyd.service.dao.IMarryDao;

/**
 * The DAO class for the TabPlayeritemsfromshop entity.
 */
public class MarryDao extends UniversalDaoHibernate implements IMarryDao {
	public MarryDao() {
		super();
	}

	/**
     * 根据playerId获得单个结婚记录
     * @param playerId
     * @param sexmark 性别标记
     * @param status 状态
     * @return
     */
	@SuppressWarnings("unchecked")
    public MarryRecord getSingleMarryRecordByPlayerId(int sexmark,int playerId,int status){
    	 List<Object> values = new ArrayList<Object>();
         StringBuffer hsql = new StringBuffer();
         hsql.append("from MarryRecord where 1=1 ");
         if(sexmark==0){
        	 hsql.append(" and manId = ?"); 
         }else{
        	 hsql.append(" and womanId = ?"); 
         }
         if(status==1){
        	 hsql.append(" and statusMode > 0");
         }
         values.add(playerId);
//         return (MarryRecord) this.getUniqueResult(hsql.toString(), values.toArray());
       //getUniqueResult 转 getList
         List<MarryRecord> list = this.getList(hsql.toString(), values.toArray());
         if(null!= list&&list.size()!=0){
         	return list.get(0);
         }
         return null;
    }
	
	

}