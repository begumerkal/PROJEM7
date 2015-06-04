package com.wyd.empire.gameaccount.handler.server;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.wyd.empire.gameaccount.bean.Account;
import com.wyd.empire.gameaccount.bean.Empireaccount;
import com.wyd.empire.gameaccount.service.factory.ServiceFactory;
import com.wyd.empire.gameaccount.session.AcceptSession;
import com.wyd.empire.protocol.data.server.UpdateAccountInfo;
import com.wyd.empire.protocol.data.server.UpdateAccountInfoOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
public class UpdateAccountInfoHandler implements IDataHandler {
    private Logger log = Logger.getLogger(UpdateAccountInfoHandler.class);

    public AbstractData handle(AbstractData data) throws Exception {
        UpdateAccountInfo updateAccount = (UpdateAccountInfo) data;
        AcceptSession session = (AcceptSession) data.getSource();
        UpdateAccountInfoOk updateAccountInfoOk = new UpdateAccountInfoOk(data.getSessionId(), data.getSerial());
        Properties properties = new Properties();
        int updateType = updateAccount.getUpdateType();
        try {
            Empireaccount empireaccount = (Empireaccount) ServiceFactory.getFactory().getEmpireaccountService().get(Empireaccount.class, updateAccount.getAccountId());
            updateAccount.setAccountId(empireaccount.getAccountId());
            switch (updateType) {
            case 0:
                if (("true").equals(updateAccount.getValues()[5])) {
                    if (ServiceFactory.getFactory().getAccountService().checkName(updateAccount.getValues()[3])) {
                        Account account = (Account) ServiceFactory.getFactory().getAccountService().get(Account.class, updateAccount.getAccountId());
                        account.setPassword(updateAccount.getValues()[0]);
                        account.setAddress(updateAccount.getValues()[1]);
                        account.setUdid(updateAccount.getValues()[2]);
                        account.setUsername(updateAccount.getValues()[3]);
                        account.setStatus(Integer.parseInt(updateAccount.getValues()[4]));
                        ServiceFactory.getFactory().getAccountService().update(account);
                        properties.put("success", true);
                    } else {
                        properties.put("success", false);
                    }
                } else {
                    Account account = (Account) ServiceFactory.getFactory().getAccountService().get(Account.class, updateAccount.getAccountId());
                    account.setPassword(updateAccount.getValues()[0]);
                    account.setAddress(updateAccount.getValues()[1]);
                    account.setUdid(updateAccount.getValues()[2]);
                    account.setUsername(updateAccount.getValues()[3]);
                    account.setStatus(Integer.parseInt(updateAccount.getValues()[4]));
                    ServiceFactory.getFactory().getAccountService().update(account);
                    properties.put("success", true);
                }
                break;
            case 2:
                updateAccount.setValues(new String[] { empireaccount.getClientModel(), empireaccount.getChannel() + "", empireaccount.getSystemName(), empireaccount.getSystemVersion()});
                Account currentAccount = (Account) ServiceFactory.getFactory().getAccountService().get(Account.class, updateAccount.getAccountId());
                properties.put("password", currentAccount.getPassword() == null ? "" : currentAccount.getPassword());
                properties.put("email", currentAccount.getAddress() == null ? "" : currentAccount.getAddress());
                properties.put("udid", currentAccount.getUdid() == null ? "" : currentAccount.getUdid());
                properties.put("username", currentAccount.getUsername() == null ? "" : currentAccount.getUsername());
                properties.put("status", currentAccount.getStatus() == null ? "" : currentAccount.getStatus());
                properties.put("clientModel", updateAccount.getValues()[0] == null ? "" : updateAccount.getValues()[0]);
                properties.put("channel", updateAccount.getValues()[1] == null ? "" : updateAccount.getValues()[1]);
                properties.put("systemName", updateAccount.getValues()[2] == null ? "" : updateAccount.getValues()[2]);
                properties.put("systemVersion", updateAccount.getValues()[3] == null ? "" : updateAccount.getValues()[3]);
                properties.put("success", true);
                break;
            default:
                break;
            }
        } catch (Exception e) {
            log.info("UpdateAccountHandlerException:" + e.getMessage());
            properties.put("success", false);
        }
        updateAccountInfoOk.setContent(JSONObject.fromObject(properties).toString());
        session.send(updateAccountInfoOk);
        return null;
    }
}
