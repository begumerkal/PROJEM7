package com.wyd.service.server.factory;


import com.app.db.service.UniversalManager;
import com.wyd.service.bean.Player;

/**
 * The service interface for the TabConsortia entity.
 */
public interface IMailService extends UniversalManager{
	public static final String SERVICE_BEAN_ID = "MailService";
	/**
    * 保存邮件
    * 
    * @param mail
    */
   public void saveMail(Player player,String title,String content) ;
}