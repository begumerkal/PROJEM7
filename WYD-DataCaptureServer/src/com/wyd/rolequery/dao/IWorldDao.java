package com.wyd.rolequery.dao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import com.wyd.db.dao.UniversalDao;
import com.wyd.rolequery.bean.Player;
import com.wyd.rolequery.bean.PlayerBill;
import com.wyd.rolequery.bean.PlayerOnline;
/**
 * The DAO interface for the TabConsortiaright entity.
 */
public interface IWorldDao extends UniversalDao {
    /**
     * 根据帐号id获取角色信息
     * @param accountIds
     * @return
     */
    public List<Player> getPlayerList(String accountIds);

    /**
     * 从id大于startId开始查limitNumber条数据玩家数据
     * @param startId
     * @param limitNumber
     * @return
     */
    public List<Player> getPlayerListAll(int startId, int limitNumber);

    /**
     * 从id大于startId开始查limitNumber条accountId数据
     * @param startId
     * @param limitNumber
     * @return
     */
    public List<Integer> getPlayerListIdAll(int startId, int limitNumber);
    
    /**
     * 根据时间查询新的玩家数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Player> getPlayerListByTime(String startTime, String endTime, int type);
    

    /**
     * 从id大于startId开始查limitNumber条数据玩家登录数据
     * @param startId
     * @param limitNumber
     * @return
     */
    public List<PlayerOnline> getPlayerOnlineListAll(int startId, int limitNumber);
    
    /**
     * 根据时间查询新的玩家登录数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<PlayerOnline> getPlayerOnlineListByTime(String startTime, String endTime, int type);
    
    /**
     * 从id大于startId开始查limitNumber条数据玩家充值数据
     * @param startId
     * @param limitNumber
     * @return
     */
    public List<PlayerBill> getPlayerBillListAll(int startId, int limitNumber);
    
    /**
     * 根据时间查询新的玩家充值数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<PlayerBill> getPlayerBillListByTime(String startTime, String endTime, int type);
    
    /**
     * 从id大于startId开始查limitNumber条数据玩家数据
     * @param startId
     * @param limitNumber
     * @return
     */
    public List<Integer> getPlayerEIDListAll(int startId, int limitNumber);
    
    /**
     * 根据时间查询新的玩家数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Integer> getPlayerEIDListByTime(String startTime, String endTime, int type);
    
    /**
     * 从id大于startId开始查limitNumber条数据玩家登录数据
     * @param startId
     * @param limitNumber
     * @return
     */
    public List<Integer> getPlayerOnlineEIDListAll(int startId, int limitNumber);
    
    /**
     * 根据时间查询新的玩家登录数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Integer> getPlayerOnlineEIDListByTime(String startTime, String endTime, int type);
    
    /**
     * 从id大于startId开始查limitNumber条数据玩家充值数据
     * @param startId
     * @param limitNumber
     * @return
     */
    public List<Integer> getPlayerBillEIDListAll(int startId, int limitNumber);
    
    /**
     * 根据时间查询新的玩家充值数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Integer> getPlayerBillEIDListByTime(String startTime, String endTime, int type);
}