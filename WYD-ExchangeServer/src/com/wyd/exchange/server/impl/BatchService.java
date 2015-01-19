package com.wyd.exchange.server.impl;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.wyd.exchange.bean.CodeExchange;
import com.wyd.exchange.bean.CreateInfo;
import com.wyd.exchange.server.factory.ServiceManager;
import com.wyd.exchange.utils.CryptionUtil;
public class BatchService implements Runnable {
    private List<CreateInfo> createInfoList = new ArrayList<CreateInfo>();

    public BatchService() {
        this.start();
    }

    private void start() {
        Thread thread = new Thread(this);
        thread.setName("BatchService-Thread");
        thread.start();
    }

    public void run() {
        while (true) {
            try {
                synchronized (this) {
                    if (0 == createInfoList.size()) {
                        this.wait();
                    }
                }
                CreateInfo createInfo = createInfoList.get(0);
                createInfoList.remove(0);
                String[] createNums = createInfo.getCreateNum().split(",");
                String[] channels = createInfo.getChannel().split(",");
                String[] valids = createInfo.getValid().split(",");
                for (int index = 0; index < createNums.length; index++) {
                    int createNum = Integer.parseInt(createNums[index]);
                    String channel = channels[index];
                    int valid = Integer.parseInt(valids[index]);
                    for (int i = 0; i < createNum; i++) {
                        CodeExchange codeExchange = new CodeExchange();
                        codeExchange.setCreatetime(new Date());
                        codeExchange.setCreator(createInfo.getCreator());
                        codeExchange.setUsed(0);
                        codeExchange.setExtended(0);
                        codeExchange.setUseful_life(valid);
                        if (0 == createInfo.getRewardType()) {
                            codeExchange.setContent(createInfo.getContent()[0]);
                        } else {
                            codeExchange.setContent(createInfo.getContent()[i]);
                        }
                        codeExchange.setDescribe(createInfo.getDescribe());
                        codeExchange.setChannel_id(channel);
                        codeExchange.setBatchNum(createInfo.getBatchNum());
                        codeExchange.setMinlevel(createInfo.getMinlevel());
                        codeExchange.setMaxlevel(createInfo.getMaxlevel());
                        codeExchange.setRewardType(createInfo.getRewardType());
                        codeExchange = (CodeExchange) ServiceManager.getManager().getExchangeService().save(codeExchange);
                        int id = codeExchange.getId();
                        ByteArrayOutputStream boutput = new ByteArrayOutputStream();
                        DataOutputStream doutput = new DataOutputStream(boutput);
                        doutput.writeInt(id);
                        doutput.writeShort(createInfo.getGroupId());
                        byte[] codeByte = boutput.toByteArray();
                        doutput.close();
                        codeByte = CryptionUtil.Encrypt(codeByte, ServiceManager.getManager().getConfiguration().getString("key1"));
                        codeByte = CryptionUtil.Encrypt(codeByte, ServiceManager.getManager().getConfiguration().getString("key2"));
                        String code = CryptionUtil.bytesToCodeString(codeByte);
                        codeExchange.setCode(code);
                        ServiceManager.getManager().getExchangeService().update(codeExchange);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void add(CreateInfo createInfo) {
        synchronized (this) {
            createInfoList.add(createInfo);
            this.notify();
        }
    }
}
