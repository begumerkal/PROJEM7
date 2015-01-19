package com.wyd.empire.gameaccount.handler.server;
import org.apache.log4j.Logger;
import com.wyd.empire.gameaccount.bean.Account;
import com.wyd.empire.gameaccount.service.factory.ServiceFactory;
import com.wyd.empire.protocol.data.server.LegacyVerification;
import com.wyd.empire.protocol.data.server.LegacyVerificationResult;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
/**
 * 玩家修改密码
 * 
 * @since JDK 1.6
 */
public class LegacyVerificationHandler implements IDataHandler {
    private Logger log = Logger.getLogger(LegacyVerificationHandler.class);

    public void handle(AbstractData data) {
        LegacyVerification legacyVerification = (LegacyVerification) data;
        AcceptSession session = (AcceptSession) data.getSource();
        try {
            int playerId = legacyVerification.getPlayerId();
            String accountName = legacyVerification.getAccountName();
            String passWord = legacyVerification.getPassWord();
            Account account = ServiceFactory.getFactory().getAccountService().getAccountByName(accountName);
            LegacyVerificationResult legacyVerificationResult = new LegacyVerificationResult(data.getSessionId(), data.getSerial());
            legacyVerificationResult.setPlayerId(playerId);
            if(null!=account&&account.getPassword().equals(passWord)){
                legacyVerificationResult.setStatus(0);
            }else{
                legacyVerificationResult.setStatus(1);
            }
            session.send(legacyVerificationResult);
        } catch (Exception e) {
            log.error(e, e);
        }
    }
}
