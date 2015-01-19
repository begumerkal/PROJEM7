package com.wyd.exchange.dao.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.exchange.bean.CodeExchange;
import com.wyd.exchange.bean.SelectInfo;
import com.wyd.exchange.bean.UpdateInfo;
import com.wyd.exchange.dao.ICodeExchangeDao;
/**
 * The DAO class for the TabConsortiaright entity.
 */
public class CodeExchangeDao extends UniversalDaoHibernate implements ICodeExchangeDao {
    public CodeExchangeDao() {
        super();
    }

    public PageList getItemList(SelectInfo selectInfo) {
        StringBuffer hsql = new StringBuffer();
        List<Object> values = new ArrayList<Object>();
        hsql.append(" FROM  " + CodeExchange.class.getSimpleName() + " WHERE 1 = 1 ");
        if (null != selectInfo.getCode() && selectInfo.getCode().length() > 0) {
            hsql.append(" and code = ? ");
            values.add(selectInfo.getCode());
        }
        if (null != selectInfo.getCreator() && selectInfo.getCreator().length() > 0) {
            hsql.append(" and creator = ? ");
            values.add(selectInfo.getCreator());
        }
        if (-1 != selectInfo.getUsed()) {
            hsql.append(" and used = ? ");
            values.add(selectInfo.getUsed());
        }
        if (-1 != selectInfo.getExtended()) {
            hsql.append(" and extended = ? ");
            values.add(selectInfo.getExtended());
        }
        if (-1 != selectInfo.getChannelId()) {
            hsql.append(" and channel_id = ? ");
            values.add(selectInfo.getChannelId() + "");
        }
        if (selectInfo.getStartTime() > 0 && selectInfo.getEndTime() > 0) {
            hsql.append(" and (extendtime between ? and ?) ");
            values.add(new Date((long) selectInfo.getStartTime() * 60 * 1000));
            values.add(new Date((long) selectInfo.getEndTime() * 60 * 1000));
        }
        if (null != selectInfo.getBatchNum() && selectInfo.getBatchNum().length() > 0) {
            hsql.append(" and batchNum = ? ");
            values.add(selectInfo.getBatchNum());
        }
        String hqlc = "SELECT COUNT(*) " + hsql.toString();
        hsql.append(" order by createtime desc");
        return getPageList(hsql.toString(), hqlc, values.toArray(), selectInfo.getPageIndex(), selectInfo.getPageSize());
    }

    public void extend(String batchNum) {
        String hsql = "update " + CodeExchange.class.getSimpleName() + " set extended=1, extendtime=?  WHERE batchNum=?";
        this.execute(hsql, new Object[] { new Date(), batchNum});
    }

    public void updateExchange(UpdateInfo updateInfo) {
        int[] ids = updateInfo.getIds();
        int[] useds = updateInfo.getUseds();
        int[] valid = updateInfo.getValid();
        for (int i = 0; i < ids.length; i++) {
            String hsql = "update " + CodeExchange.class.getSimpleName() + " set used=" + useds[i] + ",useful_life=" + valid[i] + " WHERE id = " + ids[i] + "";
            this.execute(hsql, new Object[] {});
        }
    }

    /**
     * 获取某个用户某个批次的兑换数
     * 
     * @param playerId
     * @param batchNum
     */
    public int getExchangeCount(int playerId, String serviceId, String batchNum) {
        return Integer.parseInt(this.getClassObj("select count(*) from " + CodeExchange.class.getSimpleName() + " where getter_playerid=? and getter_severid=? and batchNum=?", new Object[] { playerId, serviceId, batchNum}).toString());
    }
}