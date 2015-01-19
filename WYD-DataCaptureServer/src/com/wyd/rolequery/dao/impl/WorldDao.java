package com.wyd.rolequery.dao.impl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.rolequery.bean.Player;
import com.wyd.rolequery.bean.PlayerBill;
import com.wyd.rolequery.bean.PlayerOnline;
import com.wyd.rolequery.dao.IWorldDao;
/**
 * The DAO class for the TabConsortiaright entity.
 */
public class WorldDao extends UniversalDaoHibernate implements IWorldDao {
    public WorldDao() {
        super();
    }
    /**
     * 根据帐号id获取角色信息
     * @param accountIds
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Player> getPlayerList(String accountIds) {
        return getList("from Player where accountId in (" + accountIds + ")", new Object[] {});
    }
    
    /**
     * 从id大于startId开始查limitNumber条数据玩家数据
     * @param startId
     * @param limitNumber
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Player> getPlayerListAll(int startId, int limitNumber) {
        return getList("From Player where id > ? ORDER BY id ASC", new Object[] {startId}, limitNumber);
    }
   
    
    /**
     * 根据时间查询新的玩家数据
     * @param startTime
     * @param endTime
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Player> getPlayerListByTime(String startTime, String endTime, int type) {
        try {
        	String str = "yyyy-MM-dd HH:mm:ss";
        	if (type == 1) {
				str = "yyyy-MM-dd HH:mm";
			}
			return getList("From Player where createTime BETWEEN ? and ? ORDER BY id ASC", new Object[] {new SimpleDateFormat(str).parse(startTime), new SimpleDateFormat(str).parse(endTime)});
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

    
    
    /**
     * 从id大于startId开始查limitNumber条accountId数据
     * @param startId
     * @param limitNumber
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getPlayerListIdAll(int startId, int limitNumber) {
        return getList("SELECT accountId From Player where id > ? ORDER BY id ASC", new Object[] {startId}, limitNumber);
    }
    
    /**
     * 从id大于startId开始查limitNumber条数据玩家登录数据
     * @param startId
     * @param limitNumber
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<PlayerOnline> getPlayerOnlineListAll(int startId, int limitNumber) {
        return getList("From PlayerOnline where id > ? ORDER BY id ASC", new Object[] {startId}, limitNumber);
    }
    
    
    
    
    
    /**
     * 根据时间查询新的玩家登录数据
     * @param startTime
     * @param endTime
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<PlayerOnline> getPlayerOnlineListByTime(String startTime, String endTime, int type) {
        try {
            String str = "yyyy-MM-dd HH:mm:ss";
            if (type == 1) {
                str = "yyyy-MM-dd HH:mm";
            }
            return getList("From PlayerOnline where onTime BETWEEN ? and ? ORDER BY id ASC", new Object[] {new SimpleDateFormat(str).parse(startTime), new SimpleDateFormat(str).parse(endTime)});
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 从id大于startId开始查limitNumber条数据玩家充值数据
     * @param startId
     * @param limitNumber
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<PlayerBill> getPlayerBillListAll(int startId, int limitNumber) {
        return getList("From PlayerBill where id > ? AND origin = 1 ORDER BY id ASC", new Object[] {startId}, limitNumber);
    }
    
   
    
    /**
     * 根据时间查询新的玩家充值数据
     * @param startTime
     * @param endTime
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<PlayerBill> getPlayerBillListByTime(String startTime, String endTime, int type) {
        try {
        	String str = "yyyy-MM-dd HH:mm:ss";
        	if (type == 1) {
				str = "yyyy-MM-dd HH:mm";
			}
			return getList("From PlayerBill where createTime BETWEEN ? and ? AND origin = 1 ORDER BY id ASC", new Object[] {new SimpleDateFormat(str).parse(startTime), new SimpleDateFormat(str).parse(endTime)});
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    
    
    
    /**
     * 从id大于startId开始查limitNumber条数据玩家数据
     * @param startId
     * @param limitNumber
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getPlayerEIDListAll(int startId, int limitNumber) {
        return getList("SELECT accountId From Player where id > ? ORDER BY id ASC", new Object[] {startId}, limitNumber);
    }
    
    /**
     * 根据时间查询新的玩家数据
     * @param startTime
     * @param endTime
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getPlayerEIDListByTime(String startTime, String endTime, int type) {
        try {
            String str = "yyyy-MM-dd HH:mm:ss";
            if (type == 1) {
                str = "yyyy-MM-dd HH:mm";
            }
            return getList("SELECT accountId From Player where createTime BETWEEN ? and ? ORDER BY id ASC", new Object[] {new SimpleDateFormat(str).parse(startTime), new SimpleDateFormat(str).parse(endTime)});
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 从id大于startId开始查limitNumber条数据玩家登录数据
     * @param startId
     * @param limitNumber
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getPlayerOnlineEIDListAll(int startId, int limitNumber) {
        return getList("SELECT p.player.accountId From PlayerOnline p where p.id > ? ORDER BY id ASC", new Object[] {startId}, limitNumber);
    }
    
    /**
     * 根据时间查询新的玩家登录数据
     * @param startTime
     * @param endTime
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getPlayerOnlineEIDListByTime(String startTime, String endTime, int type) {
        try {
            String str = "yyyy-MM-dd HH:mm:ss";
            if (type == 1) {
                str = "yyyy-MM-dd HH:mm";
            }
            return getList("SELECT p.player.accountId From PlayerOnline  p where onTime BETWEEN ? and ? ORDER BY id ASC", new Object[] {new SimpleDateFormat(str).parse(startTime), new SimpleDateFormat(str).parse(endTime)});
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 从id大于startId开始查limitNumber条数据玩家充值数据
     * @param startId
     * @param limitNumber
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getPlayerBillEIDListAll(int startId, int limitNumber) {
        return getList("SELECT p.player.accountId From PlayerBill p where id > ? AND origin = 1 ORDER BY id ASC", new Object[] {startId}, limitNumber);
    }
    
    /**
     * 根据时间查询新的玩家充值数据
     * @param startTime
     * @param endTime
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getPlayerBillEIDListByTime(String startTime, String endTime, int type) {
        try {
            String str = "yyyy-MM-dd HH:mm:ss";
            if (type == 1) {
                str = "yyyy-MM-dd HH:mm";
            }
            return getList("SELECT p.player.accountId From PlayerBill p where createTime BETWEEN ? and ? AND origin = 1 ORDER BY id ASC", new Object[] {new SimpleDateFormat(str).parse(startTime), new SimpleDateFormat(str).parse(endTime)});
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}