package com.wyd.protocol.data;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.wyd.protocol.INetSegment;
import com.wyd.protocol.ProtocolManager;
public class DataBeanEncoder {
    Logger log;
    //SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:SSSS");
    public DataBeanEncoder() {
        this.log = Logger.getLogger(DataBeanEncoder.class);
    }
    
    public INetSegment encode(AbstractData data) throws Exception {
        INetSegment segment = ProtocolManager.getNetSegmentInstance(data.getType(), data.getSubType(), data.getSessionId(), data.getSerial(), data.getFlag());
        Field[] fs = data.getClass().getDeclaredFields();
        for (Field f : fs) {
            String ftype = f.getType().getSimpleName();
            Object value = PropertyUtils.getProperty(data, f.getName());
            if (null == value) {
                System.out.println("type:" + data.getType() + "------------SubType:" + data.getSubType() + " PropertyName:" + f.getName() + " value is null");
            }
            else if(value instanceof String []){
            	for(String str:(String [])value){            		
            		if(str==null){
            			System.out.println("type:" + data.getType() + "------------SubType:" + data.getSubType() + " PropertyName:" + f.getName() + " value is null");
            		}
            	}
            }
            setValue(segment, f.getName(), ftype, value);
        }
        
        //System.out.println("type:" + data.getType() + "------------SubType:" + data.getSubType());
        //this.log.info("Send Msg —————— " + data.getClass().getSimpleName() + " " + data.getType() + "," + data.getSubType());
        //System.out.println(sdf.format((new Date()))+"Send Msg —————— " + data.getClass().getSimpleName());
        return segment;
    }

    private void setValue(INetSegment data, String fieldName, String type, Object value) throws IllegalAccessException {
        if (type.equals("byte")) {
            data.write(((Byte) value).byteValue());
        } else if (type.equals("byte[]")) {
            data.write((byte[]) (byte[]) value);
        } else if (type.equals("short")) {
            data.writeShort(((Short) value).shortValue());
        } else if (type.equals("short[]")) {
            data.writeShorts((short[]) (short[]) value);
        } else if (type.equals("int")) {
            data.writeInt(((Integer) value).intValue());
        } else if (type.equals("int[]")) {
            data.writeInts((int[]) (int[]) value);
        } else if (type.equals("long")) {
            data.writeLong(((Long) value).longValue());
        } else if (type.equals("long[]")) {
            data.writeLongs((long[]) (long[]) value);
        } else if (type.equals("boolean")) {
            data.writeBoolean(((Boolean) value).booleanValue());
        } else if (type.equals("boolean[]")) {
            data.writeBooleans((boolean[]) (boolean[]) value);
        } else if (type.endsWith("String")) {
            data.writeString((String) value);
        } else if (type.endsWith("String[]")) {
            data.writeStrings((String[]) (String[]) value);
        } else if (type.endsWith("List")) {
            @SuppressWarnings("rawtypes")
            List list = (List) value;
            byte size = (byte) list.size();
            data.write(size);
            for (int i = 0; i < size; ++i)
                data.write((byte[]) list.get(i));
        } else {
            throw new IllegalAccessException();
        }
    }
}
