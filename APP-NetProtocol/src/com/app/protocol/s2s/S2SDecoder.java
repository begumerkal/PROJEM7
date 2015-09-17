package com.app.protocol.s2s;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.protocol.INetData;
import com.app.protocol.INetSegment;
import com.app.protocol.NetPacket;

/**
 * 类 <code>S2SDecoder</code>对服务器间的数据进行解码
 * 
 * @see org.apache.mina.filter.codec.ProtocolDecoderAdapter
 * @since JDK 1.6
 */
public class S2SDecoder extends ByteToMessageDecoder {
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
			byte minBytes = 7;
			byte flag = 0;
			INetData datas[] = new INetData[num];
			for (int i = 0; i < num; i++) {
				if (in.readableBytes() < minBytes) {
					in.clear();
					throw new IOException("error protocol 3");
				}
				in.markReaderIndex();
				flag = in.readByte();// 1
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
				datas[i] = new S2SData(data, serial, sessionId, false);
			}
			in.readByte();// 尾
			if ((flag & 1) != 0)
				log.debug((new StringBuilder()).append("***Recv Error Msg:").append(datas[0].getType()).append(".").append(datas[0].getSubType()).toString());
			else
				log.debug((new StringBuilder()).append("***Recv Protocol:").append(datas[0].getType()).append(".").append(datas[0].getSubType()).toString());
			NetPacket packet = new NetPacket();
			packet.datas = datas;
			in.discardReadBytes();
			out.add(packet);
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