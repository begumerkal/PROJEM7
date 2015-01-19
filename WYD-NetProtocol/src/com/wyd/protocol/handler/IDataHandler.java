package com.wyd.protocol.handler;
import com.wyd.protocol.data.AbstractData;
/**
 * 接口 <code>IDataHandler</code>，定义逻辑处理接口，内部定义了handle方法。
 * 
 * @since JDK 1.7
 * @author doter
 */

public abstract interface IDataHandler {
    public abstract AbstractData handle(AbstractData paramAbstractData) throws Exception;
}
