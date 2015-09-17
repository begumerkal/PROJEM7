package com.app.dispatch;
import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class SimpleWYDEncoder extends MessageToByteEncoder<ByteBuffer> {
	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuffer msg, ByteBuf out) throws Exception {
		out.writeBytes(msg);
		ctx.flush();
	}
}