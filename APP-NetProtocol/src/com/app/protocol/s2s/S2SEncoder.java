package com.app.protocol.s2s;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.apache.log4j.Logger;

import com.app.protocol.INetSegment;

/**
 * 类 <code>S2SEncoder</code>对服务器间的数据进行编码
 * 
 * @see org.apache.mina.filter.codec.ProtocolEncoderAdapter
 * @since JDK 1.6
 */
public class S2SEncoder extends MessageToByteEncoder<Object> {
	private static final Logger log = Logger.getLogger(S2SEncoder.class);

	/**
	 * 对服务器间的数据进行编码
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out)
			throws Exception {
		INetSegment segment = (INetSegment) msg;
		out.writeBytes(INetSegment.HEAD);// 4
		out.writeInt(segment.getSessionId());// 4
		out.writeInt(segment.getSerial());// 4
		out.writeInt(18 + segment.size());// 4
		out.writeShort(1);// 2
		out.writeBytes(segment.getByteArray());
		out.writeByte((byte) 0);// 1
		ctx.flush();
		if ((segment.getFlag() & 0x1) != 0)
			log.debug("***Send Error: " + segment.getType() + "."
					+ segment.getSubType());
		else
			log.debug("***Send Protocol: " + segment.getType() + "."
					+ segment.getSubType());
	}
}
