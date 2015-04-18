package com.wyd.empire.gameaccount.handler.server;
import org.apache.log4j.Logger;

import com.wyd.empire.gameaccount.bean.Account;
import com.wyd.empire.gameaccount.service.factory.ServiceFactory;
import com.wyd.empire.gameaccount.session.AcceptSession;
import com.wyd.empire.protocol.data.server.LegacyModifyPassword;
import com.wyd.empire.protocol.data.server.LegacyModifyPasswordOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 玩家修改密码
 * 
 * @since JDK 1.6
 */
public class LegacyModifyPasswordHandler implements IDataHandler {
    private Logger log = Logger.getLogger(LegacyModifyPasswordHandler.class);

    public void handle(AbstractData data) {
        LegacyModifyPassword legacyModifyPassword = (LegacyModifyPassword) data;
        AcceptSession session = (AcceptSession) data.getSource();
        try {
            int accountId = legacyModifyPassword.getAccountId();
            int playerId = legacyModifyPassword.getPlayerId();
            String oldPassWorld = legacyModifyPassword.getOldPassWold();
            String newPassWorld = legacyModifyPassword.getNewPassWord();
            LegacyModifyPasswordOk legacyModifyPasswordOk = new LegacyModifyPasswordOk(data.getSessionId(), data.getSerial());
            legacyModifyPasswordOk.setPlayerId(playerId);
            Account account = (Account) ServiceFactory.getFactory().getAccountService().get(Account.class, accountId);
            if (null != account) {
                if (oldPassWorld.equals(account.getPassword())) {
                    account.setPassword(newPassWorld);
                    ServiceFactory.getFactory().getAccountService().update(account);
                    legacyModifyPasswordOk.setStatus(0);
                } else {
                    legacyModifyPasswordOk.setStatus(1);
                }
            } else {
                legacyModifyPasswordOk.setStatus(2);
            }
            session.send(legacyModifyPasswordOk);
        } catch (Exception e) {
            log.error(e, e);
        }
    }
}
