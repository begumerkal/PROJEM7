package com.app.dispatch;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.protocol.INetSegment;
import com.app.protocol.s2s.S2SData;

public class ServerWYDDecoder extends ByteToMessageDecoder {
	private Logger log = Logger.getLogger(ServerWYDDecoder.class);
	/** 解码 worldServer 发送过来的数据 */
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
			throw new IOException("error protocol 1");
		}
		int sessionId = in.readInt();
		int serial = in.readInt();
		int len = in.readInt(); // 包长度
		int num = in.readShort(); // 包个数
		if (len > 0x19000) {// 100kb
			in.clear();
			throw new IOException("error protocol 2");
		}
		if (len + 1 <= readableBytes) {// 够一个包长度
			if (sessionId == -1) {// 后端处理
				byte minBytes = 7;
				for (int i = 0; i < num; i++) {
					if (in.readableBytes() < minBytes) {
						in.clear();
						throw new IOException("error protocol 3");
					}
					in.markReaderIndex();
					byte flag = in.readByte();// 1
					byte maintype = in.readByte();// 主协议号//1
					byte subtype = in.readByte(); // 从协议号//1
					int dataLen = in.readInt();// 数据长度//4
					if (dataLen < 0 || dataLen - minBytes >= in.readableBytes()) {
						in.clear();
						throw new IOException("error protocol 4");
					}
					byte data[] = new byte[dataLen];
					in.resetReaderIndex();
					in.readBytes(data);
					in.readByte();// 尾
					Packet packet = new Packet(new S2SData(data, serial, sessionId, false));
					out.add(packet);
				}
			} else {// 发送前端的
				byte[] data = new byte[len + 1];
				in.resetReaderIndex();
				in.readBytes(data);
				Packet packet = new Packet(ByteBuffer.wrap(data), sessionId);
				out.add(packet);
			}
			in.discardReadBytes();
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