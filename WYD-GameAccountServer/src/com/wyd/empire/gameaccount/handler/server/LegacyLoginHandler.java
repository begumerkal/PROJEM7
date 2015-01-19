package com.wyd.empire.gameaccount.handler.server;
import java.util.Date;
import org.apache.log4j.Logger;
import com.wyd.empire.gameaccount.bean.Account;
import com.wyd.empire.gameaccount.bean.Empireaccount;
import com.wyd.empire.gameaccount.service.IEmpireaccountService;
import com.wyd.empire.gameaccount.service.factory.ServiceFactory;
import com.wyd.empire.gameaccount.service.impl.AccountService;
import com.wyd.empire.protocol.data.server.LegacyLogin;
import com.wyd.empire.protocol.data.server.LegacyLoginOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
/**
 * 类 <code>LegacyLoginHandler</code>Protocol.SERVER_LegacyLogin 角色重新登录协议处理
 * @since JDK 1.6
 */
public class LegacyLoginHandler implements IDataHandler {
    private Logger log = Logger.getLogger(LegacyLoginHandler.class);

    public void handle(AbstractData data) {
        LegacyLogin login = (LegacyLogin) data;
        AcceptSession session = (AcceptSession) data.getSource();
        String udid = login.getUdid();
        String name = login.getName();
        String pwd = login.getPassword();
        int isChannelLogon = login.getIsChannelLogon();
        String oldUdid = login.getOldUdid();
        Account account = null;
        try {
            LegacyLoginOk loginOk = new LegacyLoginOk(data.getSessionId(), data.getSerial());
            if (0 == isChannelLogon) {// 非渠道登录
                if (udid.equals(name)) {// 用udid登录
                    account = ServiceFactory.getFactory().getAccountService().getAccountByName(udid);
                    if (null == account) {
                        account = ServiceFactory.getFactory().getAccountService().getAccountByName(oldUdid);
                        if (null != account) {
                            account.setUdid(udid);
                            account.setUsername(udid);
                            ServiceFactory.getFactory().getAccountService().update(account);
                        }
                    }
                    if (null == account) {
                        account = ServiceFactory.getFactory().getAccountService().createAccount(udid, udid, udid);
                    }
                } else {// 帐号密码登录
                    account = ServiceFactory.getFactory().getAccountService().getAccountByName(name);
                    if (null == account || !account.getPassword().equals(pwd)) {
                        account = null;
                    }
                }
            } else {// 渠道登录
                name = name.replace("Duoku_", "BD_").replace("UcGame_", "UC_").replace("DL_", "DJ_").replace("qiekenao_", "Qiekenao_").replace("JiFeng_", "gfan_").replace("top_", "Top173_");
                account = ServiceFactory.getFactory().getAccountService().getAccountByName(name);
                if (null == account) {
                    account = ServiceFactory.getFactory().getAccountService().getAccountByName(oldUdid);
                    if (null == account) {
                        account = ServiceFactory.getFactory().getAccountService().createAccount(name, pwd, udid);
                    } else {
                        account.setUsername(name);
                        account.setUdid(udid);
                        account.setPassword(pwd);
                        ServiceFactory.getFactory().getAccountService().update(account);
                    }
                }
            }
            if (null != account) {
                loginOk.setAccountId(account.getId());
                loginOk.setUdid(account.getUdid());
                loginOk.setName(account.getUsername());
                loginOk.setPassword(account.getPassword());
                loginOk.setChannel(login.getChannel());
                if (AccountService.ACCOUNT_STATUS_NORMAL != account.getStatus()) {
                    loginOk.setStatus(2);
                } else {
                    IEmpireaccountService empireaccountService = ServiceFactory.getFactory().getEmpireaccountService();
                    Empireaccount gameAccount = empireaccountService.login(loginOk.getAccountId(), login.getSource().getId());
                    if (gameAccount == null) {
                        String model = "Default";
                        String version = "1.0";
                        gameAccount = ServiceFactory.getFactory().getEmpireaccountService().createGameAccount(loginOk.getAccountId(), loginOk.getName(), model, version, new Date(), "", session.getId(), loginOk.getChannel());
                    }
                    gameAccount.setLastLoginTime(new java.sql.Timestamp(new Date().getTime()));
                    gameAccount.setTotalLoginTimes(gameAccount.getTotalLoginTimes() + 1);
                    gameAccount.setName(loginOk.getName());
                    empireaccountService.updateGameAccount(gameAccount);
                    loginOk.setGameAccountId(gameAccount.getId());
                }
            } else {
                loginOk.setUdid("");
                loginOk.setName("");
                loginOk.setPassword("");
                loginOk.setStatus(1);
            }
            session.send(loginOk);
        } catch (Exception e) {
            log.error(e, e);
            e.printStackTrace();
            LegacyLoginOk loginOk = new LegacyLoginOk(data.getSessionId(), data.getSerial());
            loginOk.setUdid("");
            loginOk.setName("");
            loginOk.setPassword("");
            loginOk.setStatus(2);
            session.send(loginOk);
        }
    }
}
