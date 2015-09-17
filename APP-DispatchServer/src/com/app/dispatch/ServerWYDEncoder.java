package com.app.dispatch;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.ByteBuffer;

public class ServerWYDEncoder extends MessageToByteEncoder<ByteBuffer> {
	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuffer msg, ByteBuf out) throws Exception {
		out.writeBytes((ByteBuffer) msg);
		ctx.flush();
	}
}