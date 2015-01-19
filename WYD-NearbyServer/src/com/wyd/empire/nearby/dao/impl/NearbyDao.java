package com.wyd.empire.nearby.dao.impl;
import java.util.ArrayList;
import java.util.List;
import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.nearby.bean.Mail;
import com.wyd.empire.nearby.bean.PlayerInfo;
import com.wyd.empire.nearby.dao.INearbyDao;
import com.wyd.empire.nearby.service.factory.ServiceManager;
/**
 * The DAO class for the Mail entity.
 */
public class NearbyDao extends UniversalDaoHibernate implements INearbyDao {
    public NearbyDao() {
        super();
    }
    
	public int getMaxId(Class<?> clazz) {
		Object obj = this.getClassObj("select max(id) from " + clazz.getSimpleName(), new Object[] {});
		return null == obj ? 1 : Integer.parseInt(obj.toString()) + 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlayerInfo> getNearbyPlayer(int lon, int lat, int threshold, int maxResults) {
		int minLon = lon - threshold;
		int maxLon = lon + threshold;
		int minLat = lat - threshold;
		int maxLat = lat + threshold;
		StringBuffer hql = new StringBuffer();
		hql.append("select id from ");
		hql.append(PlayerInfo.class.getSimpleName());
		hql.append(" where longitude >= ? and longitude <= ? and latitude >= ? and latitude <= ?");
		List<Object> idList = this.getList(hql.toString(), new Object[] { minLon, maxLon, minLat, maxLat}, maxResults);
		List<PlayerInfo> playerInfoList = new ArrayList<PlayerInfo>();
		for(Object id:idList){
		    PlayerInfo playerInfo = (PlayerInfo)ServiceManager.getManager().getPlayerInfoManager().getBean(PlayerInfo.class, Integer.parseInt(id.toString()));
		    playerInfoList.add(playerInfo);
		}
		return playerInfoList;
	}
	
	@SuppressWarnings("unchecked")
    public List<Mail> getSendMailList(int sendId) {
        StringBuffer hql = new StringBuffer();
        hql.append("select id from ");
        hql.append(Mail.class.getSimpleName());
        hql.append(" where sendId=? and deleteMark<2");
        List<Object> idList = this.getList(hql.toString(), new Object[] { sendId});
        List<Mail> mailList = new ArrayList<Mail>();
        for (Object id : idList) {
            Mail mail = (Mail) ServiceManager.getManager().getMailManager().getBean(Mail.class, Integer.parseInt(id.toString()));
            mailList.add(mail);
        }
        return mailList;
    }
	
	@SuppressWarnings("unchecked")
    public List<Mail> getReceivedMailList(int receivedId) {
        StringBuffer hql = new StringBuffer();
        hql.append("select id from ");
        hql.append(Mail.class.getSimpleName());
        hql.append(" where receivedId=? and deleteMark!=1 and deleteMark!=3");
        List<Object> idList = this.getList(hql.toString(), new Object[] { receivedId});
        List<Mail> mailList = new ArrayList<Mail>();
        for (Object id : idList) {
            Mail mail = (Mail) ServiceManager.getManager().getMailManager().getBean(Mail.class, Integer.parseInt(id.toString()));
            mailList.add(mail);
        }
        return mailList;
    }
}