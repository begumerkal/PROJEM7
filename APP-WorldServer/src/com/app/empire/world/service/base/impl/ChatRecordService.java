package com.app.empire.world.service.base.impl;
//package com.app.empire.world.service.base.impl;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Service;
//
//import com.app.db.service.impl.UniversalManagerImpl;
//import com.app.empire.world.dao.mysql.IChatRecordDao;
//import com.app.empire.world.entity.mysql.ChatRecord;
//import com.app.empire.world.service.base.IChatRecordService;
//
///**
// * The service class for the TabChatRecord entity.
// */
//@Service
//public class ChatRecordService extends UniversalManagerImpl implements IChatRecordService {
//	/**
//	 * The dao instance injected by Spring.
//	 */
//	private IChatRecordDao dao;
//	/**
//	 * The service Spring bean id, used in the applicationContext.xml file.
//	 */
//	private static final String SERVICE_BEAN_ID = "ChatRecordService";
//
//	public ChatRecordService() {
//		super();
//	}
//
//	/**
//	 * Returns the singleton <code>IChatRecordService</code> instance.
//	 */
//	public static IChatRecordService getInstance(ApplicationContext context) {
//		return (IChatRecordService) context.getBean(SERVICE_BEAN_ID);
//	}
//
//	/**
//	 * Called by Spring using the injection rules specified in the Spring beans
//	 * file "applicationContext.xml".
//	 */
//	public void setDao(IChatRecordDao dao) {
//		super.setDao(dao);
//		this.dao = dao;
//	}
//
//	public IChatRecordDao getDao() {
//		return this.dao;
//	}
//
//	/**
//	 * 保存聊天记录
//	 * 
//	 * @param sendName
//	 * @param reveName
//	 * @param channel
//	 * @param message
//	 */
//	public void saveChatLog(String sendName, String reveName, int channel, String message) {
//		ChatRecord chatRecord = new ChatRecord();
//		chatRecord.setSendName(sendName);
//		chatRecord.setReveName(reveName);
//		chatRecord.setChannel(channel);
//		chatRecord.setMessage(message);
//		chatRecord.setCreateTime(new Date());
//		this.dao.save(chatRecord);
//	}
//
//	/**
//	 * 查询
//	 * 
//	 * @param stime
//	 * @param etime
//	 * @param key
//	 * @return
//	 * @throws ParseException
//	 */
//	@SuppressWarnings("unchecked")
//	public List<ChatRecord> getChatRecord(String stime, String etime, String key) throws ParseException {
//		List<Object> p = new ArrayList<Object>();
//		String hql = "from ChatRecord where createTime between ? and ?";
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		p.add(sdf.parse(stime));
//		p.add(sdf.parse(etime));
//		if (null != key && key.length() > 0) {
//			hql += " and ( sendName like ? or reveName like ? or message like ?)";
//			key = "%" + key + "%";
//			p.add(key);
//			p.add(key);
//			p.add(key);
//		}
//		return this.dao.getList(hql, p.toArray());
//	}
//}