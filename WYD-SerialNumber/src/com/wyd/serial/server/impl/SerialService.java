package com.wyd.serial.server.impl;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.serial.bean.App;
import com.wyd.serial.bean.Payment;
import com.wyd.serial.commons.Status;
import com.wyd.serial.info.SerialInfo;
import com.wyd.serial.server.factory.ServiceManager;
/**
 * @author zguoqiu
 * @version 创建时间：2013-8-16 上午9:50:35 类说明
 */
public class SerialService implements Runnable {
    private String filePath;
    private RandomAccessFile raf;
    private Logger log = Logger.getLogger(SerialService.class);
    
    public final static SimpleDateFormat YYYY_MM_DD_SDF = new SimpleDateFormat("yyyy-MM-dd");
    public final static SimpleDateFormat DD_SDF = new SimpleDateFormat("dd");
    
    private List<SerialInfo> serialInfoList = new ArrayList<SerialInfo>();
    private int serial;
    private Date today;
    
    public SerialService() throws IOException{
        URL url = Thread.currentThread().getContextClassLoader().getResource("lastserial.txt");
        if(null!=url){
            filePath = url.getPath();
        }else{
            filePath = Thread.currentThread().getContextClassLoader().getResource("config.properties").getPath();
            filePath = filePath.replace("config.properties", "lastserial.txt");
            File file = new File(filePath);
            file.createNewFile();
        }
        FileReader fr = new FileReader(filePath);
        serial = 0;
        today = new Date();
        BufferedReader br = new BufferedReader(fr);
        try {
            String row = br.readLine();
            if (null != row) {
                int mak = Integer.parseInt(row);
                long td = (Integer.parseInt(DD_SDF.format(today)) % 15) + 1;
                if (td == ((mak >> 24) & 0xf)) {
                    serial = (mak + 1) & 0xffffff;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                br.close();
            }
            if (null != fr) {
                fr.close();
            }
        }
        File serialFile = new File(filePath);
        raf = new RandomAccessFile(serialFile, "rw");
    }
    
    public void start() {
        Thread t = new Thread(this);
        t.setName("SerialService-Thread");
        t.start();
    }

    BufferedWriter bufferedWriter = null;
    public void run() {
        while (true) {
            try {
                synchronized (SerialService.this) {
                    if (0 == serialInfoList.size()) {
                        this.wait();
                    }
                }
                SerialInfo serialInfo = serialInfoList.remove(0);
                String serialNum = getSerialNum();
                serialInfo.setSerialNum(serialNum);
                serialInfo.setOrderNum(serialInfo.getPlayerId() + "-" + serialInfo.getSerialNum() + "-" + System.currentTimeMillis());
//                serialInfo.notifyAll();
                saveOrder(serialInfo);
                raf.seek(0);
                raf.writeBytes(serialNum);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addQueue(SerialInfo serialInfo) {
        synchronized (SerialService.this) {
            serialInfoList.add(serialInfo);
            this.notifyAll();
        }
    }
    
    private String getSerialNum() {
        Date now = new Date();
        // StringBuffer serialStr = new StringBuffer("100000000");
        if (!isSameDate(today, now)) {
            today = now;
            serial = 0;
        }
        long td = (Integer.parseInt(DD_SDF.format(now)) % 15) + 1;
        td <<= 24;
        td |= serial & 0xFFFFFF;
        serial++;
        serial = serial & 0xFFFFFF;
        // String s = serial++ + "";
        // serialStr.replace(1, 3, DD_SDF.format(now));
        // serialStr.replace(9 - s.length(), 9, s);
        return td + "";
    }
    
    
    /**
     * 判断两个日期是否为同一天
     * @param date
     * @param otherDate
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        String strDateOne = YYYY_MM_DD_SDF.format(date1);;
        String strDateTwo = YYYY_MM_DD_SDF.format(date2);;
        if (strDateOne.equals(strDateTwo)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 玩家退出游戏前保存玩家的称号状态
     * @param player
     */
    public void saveOrder(SerialInfo serialInfo){
        ServiceManager.getManager().getSimpleThreadPool().execute(createTask(serialInfo));
    }
    
    private Runnable createTask(SerialInfo serialInfo) {
        return new SerialThread(serialInfo);
    }
    
    public class SerialThread implements Runnable {
        private SerialInfo serialInfo;
        
        public SerialThread(SerialInfo serialInfo) {
            this.serialInfo = serialInfo;
        }

        @Override
        public void run() {
            App app = ServiceManager.getManager().getPayService().getAppByNameEnglish(serialInfo.getAppId());
            Payment payment = new Payment();
            if (app == null) {
                log.info("订单找不到对应应用，订单序列：" + serialInfo.getSerialNum() + "-------订单APPKEY:" + serialInfo.getAppId() + "-------订单回调IP:" + serialInfo.getServiceIp() + "-------订单回调端口:" + serialInfo.getServicePort());
                return;
            }
            payment.setApp(app);
            payment.setOrderToken(serialInfo.getOrderNum()); // 定单号
            payment.setApproach("NORMAL");
            payment.setExecutionCount(Status.STATUS_INIT);
            payment.setPriority(Status.STATUS_INIT);
            payment.setProviderId(1);
            payment.setStatusCallback(Status.STATUS_INIT);
            payment.setStatusNotify(Status.STATUS_INIT);
            payment.setStatusResubmit(Status.STATUS_SUCCESS);
            payment.setRealAmt(0L);
            payment.setUserToken(serialInfo.getSerialNum()); // 定单序列号
            payment.setCardMedium("");
            payment.setPayAmt(0l);
            payment.setCardAmt(0l);
            payment.setCardNo("");
            payment.setCardPwd("");
            payment.setAgent("");
            payment.setChannel("");
            payment.setClientFromHost(serialInfo.getServiceIp());
            payment.setClientReturnPort(serialInfo.getServicePort());
            payment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            payment.setReturnCode(Status.STATUS_INIT + "");
            payment.setReturnMessage(""); 
            payment.setRealAmt(0l);
            payment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            payment.setStatusCallback(Status.STATUS_INIT);
            ServiceManager.getManager().getPayService().save(payment);
        }
    }
}
