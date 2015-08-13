package com.app.empire.world.impl;

import java.io.IOException;

/**
 * 接口<code>IToClientBytes</code>，定义输出到客户端的二进制数据流方法名称。
 * 
 * @see IToClientBytes
 * @author mazheng
 * 
 */
public abstract interface IToClientBytes {
	public abstract byte[] toClientBytes() throws IOException;
}
