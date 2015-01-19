package com.wyd.empire.gameaccount.handler.server;
import org.apache.log4j.Logger;
import com.wyd.empire.gameaccount.service.factory.ServiceFactory;
import com.wyd.empire.protocol.data.server.SetClientInfo;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 类 <code>GetPlayerAreaHandler</code>Protocol.SERVER_GetPlayerAreaHandler 设置用户信息
 * @since JDK 1.6
 */
public class SetClientInfoHandler implements IDataHandler {
    private Logger log = Logger.getLogger(SetClientInfoHandler.class);

    public void handle(AbstractData message) {
        SetClientInfo setClientInfo = (SetClientInfo) message;
        try {
            int accountId = setClientInfo.getAccountId();
            String clientModel = setClientInfo.getClientModel();
            String systemName = setClientInfo.getSystemName();
            String systemVersion = setClientInfo.getSystemVersion();
            ServiceFactory.getFactory().getEmpireaccountService().setClientInfo(accountId, clientModel, systemName, systemVersion);
        } catch (Throwable e) {
            log.error(e);
        }
    }
}
