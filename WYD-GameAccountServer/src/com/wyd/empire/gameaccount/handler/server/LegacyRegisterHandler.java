package com.wyd.empire.gameaccount.handler.server;
import org.apache.log4j.Logger;

import com.wyd.empire.gameaccount.bean.Account;
import com.wyd.empire.gameaccount.service.factory.ServiceFactory;
import com.wyd.empire.gameaccount.session.AcceptSession;
import com.wyd.empire.protocol.data.server.LegacyRegister;
import com.wyd.empire.protocol.data.server.LegacyRegisterOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 玩家注册
 * 
 * @since JDK 1.6
 */
public class LegacyRegisterHandler implements IDataHandler {
    private Logger log = Logger.getLogger(LegacyRegisterHandler.class);

    public AbstractData handle(AbstractData data) {
        LegacyRegister legacyRegister = (LegacyRegister) data;
        AcceptSession session = (AcceptSession) data.getSource();
        try {
            int accountId = legacyRegister.getAccountId();
            int playerId = legacyRegister.getPlayerId();
            String accountName = legacyRegister.getAccountName(); // 帐号（加密后的字符串）
            String passWord = legacyRegister.getPassWord(); // 密码（加密后的字符串）
            String email = legacyRegister.getEmail(); // 邮箱地址（加密后的字符串）
            LegacyRegisterOk legacyRegisterOk = new LegacyRegisterOk(data.getSessionId(), data.getSerial());
            legacyRegisterOk.setPlayerId(playerId);
            if (ServiceFactory.getFactory().getAccountService().checkName(accountName)) {
                Account account = (Account) ServiceFactory.getFactory().getAccountService().get(Account.class, accountId);
                if (null != account) {
                    account.setUdid("");
                    account.setUsername(accountName);
                    account.setPassword(passWord);
                    account.setAddress(email);
                    ServiceFactory.getFactory().getAccountService().update(account);
                    legacyRegisterOk.setStatus(0);
                } else {
                    legacyRegisterOk.setStatus(2);
                }
            } else {
                legacyRegisterOk.setStatus(1);
            }
            session.send(legacyRegisterOk);
        } catch (Exception e) {
            log.error(e, e);
        }
        
        return null;
    }
}
