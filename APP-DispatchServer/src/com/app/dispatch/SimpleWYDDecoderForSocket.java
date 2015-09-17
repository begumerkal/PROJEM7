package com.app.dispatch;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.protocol.INetData;
import com.app.protocol.INetSegment;
import com.app.protocol.s2s.S2SDecoder;

public class SimpleWYDDecoderForSocket extends ByteToMessageDecoder {
	private Logger log = Logger.getLogger(S2SDecoder.class);

	/**
	 * 对服务器间数据进行解码
	 * 
	 * @param session
	 * @param in
	 * @param out
	 */
	@SuppressWarnings("unused")
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int readableBytes = in.readableBytes();
		if (readableBytes <= 19)
			return;
		in.markReaderIndex();
		byte head[] = new byte[4];
		in.readBytes(head);
		int version = checkVersion(head);
		if (version == -1) {
			in.clear();
			throw new IOException("error protocol_1");
		}
		int sessionId = in.readInt();
		int serial = in.readInt();
		int len = in.readInt(); // 包长度
		int num = in.readShort(); // 包个数
		if (len > 0x19000) {// 100kb
			in.clear();
			throw new IOException("error protocol_2");
		}
		if (num < 1) {
			in.clear();
			throw new IOException("error protocol_3");
		}
		if (len + 1 <= readableBytes) {// 够一个包长度
			byte minBytes = 7;
			byte flag = 0;
			INetData datas[] = new INetData[num];
			for (int i = 0; i < num; i++) {
				if (in.readableBytes() < minBytes) {
					in.clear();
					throw new IOException("error protocol_4");
				}
				flag = in.readByte();// 1
				byte maintype = in.readByte();// 主协议号//1
				byte subtype = in.readByte(); // 从协议号//1
				int dataLen = in.readInt();// 数据长度//4
				if (dataLen < 0 || dataLen - minBytes >= in.readableBytes()) {
					in.clear();
					throw new IOException("error protocol_5");
				}
				in.resetReaderIndex();
				byte data[] = new byte[len + 1];
				in.readBytes(data);
				in.discardReadBytes();
				out.add(ByteBuffer.wrap(data));
			}
		} else {
			in.resetReaderIndex();
		}
	}

	public int checkVersion(byte head[]) {
		for (int i = 0; i < INetSegment.HEAD.length - 1; i++) {
			if (INetSegment.HEAD[i] != head[i]) {
				return -1;
			}
		}
		return (int) (((head[0] & 0xFF) << 24) | ((head[1] & 0xFF) << 16) | ((head[2] & 0xFF) << 8) | (head[3] & 0xFF));
	}
}