package com.wyd.service.server.impl;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.service.bean.Mail;
import com.wyd.service.bean.Player;
import com.wyd.service.dao.IMailDao;
import com.wyd.service.server.factory.IMailService;
/**
 * The service class for the TabConsortia entity.
 */
public class MailService extends UniversalManagerImpl implements IMailService {
    /**
     * The dao instance injected by Spring.
     */
    private IMailDao            dao;
    /**
     * The service Spring bean id, used in the applicationContext.xml file.
     */
    
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public MailService() {
        super();
    }

    /**
     * Returns the singleton <code>IConsortiaService</code> instance.
     */
    public static IMailService getInstance(ApplicationContext context) {
        return (IMailService) context.getBean(SERVICE_BEAN_ID);
    }

    /**
     * Called by Spring using the injection rules specified in the Spring beans file "applicationContext.xml".
     */
    public void setDao(IMailDao dao) {
        super.setDao(dao);
        this.dao = dao;
    }

    public IMailDao getDao() {
        return this.dao;
    }

    /**
     * 保存邮件
     * 
     * @param mail
     */
    public void saveMail(Player player,String title,String content) {
        try {
        	Mail mail = new Mail();
            mail.setBlackMail(false);
            mail.setContent(content);
            mail.setIsRead(false);
            mail.setReceivedId(player.getId());
            mail.setReceivedName(player.getName());
            mail.setSendId(0);
            mail.setSendName("系統");
            mail.setSendTime(new Date());
            mail.setTheme(title);
            mail.setType(1);
            mail.setIsStick(0);
        	mail=(Mail)dao.save(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}