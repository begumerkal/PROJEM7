package com.wyd.empire.world.server.service.base;

import java.text.ParseException;
import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.ChatRecord;

/**
 * The service interface for the TabChatRecord entity.
 */
public interface IChatRecordService extends UniversalManager {
	/**
	 * 保存聊天记录
	 * 
	 * @param sendName
	 * @param reveName
	 * @param channel
	 * @param message
	 */
	public void saveChatLog(String sendName, String reveName, int channel, String message);

	/**
	 * 查询
	 * 
	 * @param stime
	 * @param etime
	 * @param key
	 * @return
	 */
	public List<ChatRecord> getChatRecord(String stime, String etime, String key) throws ParseException;
}