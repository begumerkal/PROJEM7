package com.wyd.empire.gameaccount.handler.server;
import java.util.Date;
import org.hibernate.SessionFactory;
import com.wyd.empire.gameaccount.bean.Empireaccount;
import com.wyd.empire.gameaccount.service.IEmpireaccountService;
import com.wyd.empire.gameaccount.service.factory.ServiceFactory;
import com.wyd.empire.protocol.data.server.PlayerLogout;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
public class PlayerLogoutHandler implements IDataHandler {
    protected SessionFactory sf;

    public PlayerLogoutHandler() {
    }

    public void handle(AbstractData message) throws Exception {
        PlayerLogout playerLogout = (PlayerLogout) message;
        Empireaccount account;
        try {
            IEmpireaccountService empireaccountService = ServiceFactory.getFactory().getEmpireaccountService();
            account = empireaccountService.getGameAccount(playerLogout.getAccountId());
            if (account == null) {
                return;
            }
            Date lastLoginTime = account.getLastLoginTime();
            long between = (new Date().getTime() - lastLoginTime.getTime()) / 1000;// 除以1000是为了转换成秒
            int minutes = (int) between % 3600 / 60;
            account.setOnLineTime(minutes);
            empireaccountService.updateGameAccount(account);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
