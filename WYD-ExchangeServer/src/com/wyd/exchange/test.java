package com.wyd.exchange;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import net.sf.json.JSONObject;
import com.wyd.exchange.bean.ExchangeInfo;
import com.wyd.exchange.bean.GroupExchange;
import com.wyd.exchange.server.factory.ServiceManager;
import com.wyd.exchange.utils.CryptionUtil;
public class test {
    /**
     * @param args
     * @throws IOException
     */
    @SuppressWarnings({ "static-access", "unused"})
    public static void main(String[] args) throws IOException {
        String dataString = "{\"channel\":1064,\"code\":\"6F767-36C7D-49369-4F507\",\"level\":22,\"playerId\":700530,\"serviceId\":\"12\"}";
        JSONObject jsonObject = JSONObject.fromObject(dataString);
        ExchangeInfo exchangeInfo = (ExchangeInfo) jsonObject.toBean(jsonObject, ExchangeInfo.class);
        byte[] codeByte = CryptionUtil.codeStringTobytes(exchangeInfo.getCode());
        codeByte = CryptionUtil.Decrypt(codeByte, ServiceManager.getManager().getConfiguration().getString("key2"));
        codeByte = CryptionUtil.Decrypt(codeByte, ServiceManager.getManager().getConfiguration().getString("key1"));
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(codeByte));
        int id = inputStream.readInt();
        int serviceid = inputStream.readShort();
        inputStream.close();
        GroupExchange groupExchange = (GroupExchange) ServiceManager.getManager().getExchangeService().get(GroupExchange.class, serviceid);
        boolean check = false;
        if (null != groupExchange) {
            String[] sids = groupExchange.getServices().split(",");
            for (String sid : sids) {
                if (sid.equals(exchangeInfo.getServiceId())) {
                    check = true;
                    break;
                }
            }
        }
    }
}
