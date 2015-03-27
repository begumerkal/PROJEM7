package com.wyd.protocol.data;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.wyd.net.ProtocolFactory;
import com.wyd.protocol.INetData;
public class DataBeanDecoder {
    Logger log;

    public DataBeanDecoder() {
        this.log = Logger.getLogger(DataBeanDecoder.class);
    }

    public AbstractData decode(INetData data) throws Exception {
        byte type = data.getType();
        byte subType = data.getSubType();
        byte flag = data.getFlag();
        AbstractData d = null;
        if ((flag & 0x1) != 0) {
            d = ProtocolFactory.getProtocolDataBean((byte) -1, (byte) -1);
            d.setType(type);
            d.setSubType(subType);
            this.log.debug("***数据异常:" + ProtocolFactory.getProtocolDataBean(type, subType).getClass().getName());
        } else {
            d = ProtocolFactory.getProtocolDataBean(type, subType);
            if (d == null)
                this.log.warn("***未定义的类型：" + Integer.toHexString(type) + ".0x" + Integer.toHexString(subType));
            else {
                this.log.debug("***接收消息： " + d.getClass().getName());
            }
        }
        if (d != null) {
            try {
                d.setSerial(data.getSerial());
                d.setSessionId(data.getSessionId());
                Field[] fs = d.getClass().getDeclaredFields();
                for (Field f : fs) {
                    String ftype = f.getType().getSimpleName();
                    PropertyUtils.setProperty(d, f.getName(), getValue(data, f.getName(), ftype));
                }
                System.out.println("收到数据："+data.toString());
            } catch (Exception ex) {
                System.out.println("type:" + type + "------------SubType:" + subType);
                this.log.error("***Recv Error type:" + type + "------------SubType:" + subType);
                throw new Exception(ex);
            }
        }
        return d;
    }

    private Object getValue(INetData data, String fieldName, String type) throws IllegalAccessException {
        if (type.equals("byte")) return Byte.valueOf(data.readByte());
        if (type.equals("byte[]")) return data.readBytes();
        if (type.equals("short")) return Short.valueOf(data.readShort());
        if (type.equals("short[]")) return data.readShorts();
        if (type.equals("int")) return Integer.valueOf(data.readInt());
        if (type.equals("int[]")) return data.readInts();
        if (type.equals("long")) return Long.valueOf(data.readLong());
        if (type.equals("long[]")) return data.readLongs();
        if (type.equals("boolean")) return Boolean.valueOf(data.readBoolean());
        if (type.equals("boolean[]")) return data.readBooleans();
        if (type.endsWith("String")) return data.readString();
        if (type.endsWith("String[]")) return data.readStrings();
        if (type.endsWith("List")) {
            byte len = data.readByte();
            List<byte[]> list = new ArrayList<byte[]>();
            for (int i = 0; i < len; ++i) {
                list.add(data.readBytes());
            }
            return list;
        }
        throw new IllegalAccessException();
    }
}
