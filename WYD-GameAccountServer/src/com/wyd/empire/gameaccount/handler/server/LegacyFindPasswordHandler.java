package com.wyd.empire.gameaccount.handler.server;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.gameaccount.bean.Account;
import com.wyd.empire.gameaccount.service.factory.ServiceFactory;
import com.wyd.empire.gameaccount.session.AcceptSession;
import com.wyd.empire.protocol.data.server.LegacyFindPassword;
import com.wyd.empire.protocol.data.server.LegacyFindPasswordOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 找回密码
 * 
 * @since JDK 1.6
 */
public class LegacyFindPasswordHandler implements IDataHandler {
    private Logger log = Logger.getLogger(LegacyFindPasswordHandler.class);

    public void handle(AbstractData data) {
        LegacyFindPassword legacyFindPassword = (LegacyFindPassword) data;
        AcceptSession session = (AcceptSession) data.getSource();
        try {
            String email = legacyFindPassword.getEmail();
            List<Account> accountList = ServiceFactory.getFactory().getAccountService().getAccountByEmail(email);
            String[] accountName = new String[accountList.size()];
            String[] password = new String[accountList.size()];
            int i=0;
            for(Account account:accountList){
                accountName[i] = account.getUsername();
                password[i] = account.getPassword();
                i++;
            }
            LegacyFindPasswordOk legacyFindPasswordOk = new LegacyFindPasswordOk(data.getSessionId(), data.getSerial());
            legacyFindPasswordOk.setPlayerId(legacyFindPassword.getPlayerId());
            legacyFindPasswordOk.setEmail(email);
            legacyFindPasswordOk.setAccount(accountName);
            legacyFindPasswordOk.setPassword(password);
            session.send(legacyFindPasswordOk);
        } catch (Exception e) {
            log.error(e, e);
        }
    }
}
