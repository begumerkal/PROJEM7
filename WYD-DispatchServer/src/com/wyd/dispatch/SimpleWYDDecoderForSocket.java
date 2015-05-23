package com.wyd.dispatch;
import java.io.IOException;
import java.util.Arrays;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import com.wyd.protocol.INetSegment;

public class SimpleWYDDecoderForSocket extends ProtocolDecoderAdapter {
	protected final AttributeKey CURRENT_DECODER = new AttributeKey(getClass(), "decoder");
	public static int companyCode = -1;
	public static int machineCode = -1;

	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		byte remains[] = (byte[]) session.getAttribute(CURRENT_DECODER);
		IoBuffer buffer = null;
		if (remains != null) {
			buffer = IoBuffer.wrap(remains);
			buffer.setAutoExpand(true);
			buffer.position(remains.length);
			buffer.put(in);
			buffer.flip();
		} else {
			buffer = in;
		}
		while (buffer.hasRemaining()) {
			buffer.mark();
			int size = buffer.remaining();
			if (size > 18) {
				byte[] head = new byte[4];
				buffer.get(head);
				int version = compareHead(head);
				if (version == -1) {
					session.setAttribute(CURRENT_DECODER, null);
					throw new IOException("error protocol");
				}
				buffer.skip(8);
				int len = buffer.getInt();
				int segNum = buffer.getShort();
				if (segNum < 1) {
					session.setAttribute(CURRENT_DECODER, null);
					throw new IOException("error protocol");
				}
				if ((len > 102400) || (len < 18)) {
					session.setAttribute(CURRENT_DECODER, null);
					throw new IOException("error protocol");
				}
				if (size>=len + 1) {
					byte[] data = new byte[len + 1];
					buffer.reset();
					buffer.get(data);
					byte verifyCode = data[data.length - 1];
					byte[] value = Arrays.copyOfRange(data, 18, data.length - 1);
					value = CryptionUtil.Decrypt(value, DisServer.configuration.getConfiguration().getString("deckey"));//解密
					len = value.length + 18;
					data = Arrays.copyOf(data, len + 1);
					System.arraycopy(value, 0, data, 18, value.length);
					data[data.length - 1] = verifyCode;
					IoBuffer buffer2 = IoBuffer.wrap(data);
					buffer2.putInt(12, len);
					buffer2.skip(18);
					if (buffer2.remaining() < 7) {
						session.setAttribute(CURRENT_DECODER, null);
						throw new IOException("error protocol");
					}
					buffer2.skip(3);
					int dataLen = buffer2.getInt();
					if ((dataLen < 0) || (dataLen - 7 >= buffer2.remaining())) {
						session.setAttribute(CURRENT_DECODER, null);
						throw new IOException("error protocol");
					}
					out.write(IoBuffer.wrap(data));
					session.setAttribute(CURRENT_DECODER, null);
				} else {
					buffer.reset();
					byte[] bytes = new byte[size];
					buffer.get(bytes);
					session.setAttribute(CURRENT_DECODER, bytes);
				}
			} else if (size > 0) {
				buffer.reset();
				byte[] bytes = new byte[size];
				buffer.get(bytes);
				session.setAttribute(CURRENT_DECODER, bytes);
			}
		}
	}

	public int compareHead(byte[] head) {
		for (int i = 0; i < INetSegment.HEAD.length - 1; ++i) {
			if (INetSegment.HEAD[i] != head[i])
				return -1;
		}
		int version = head[3] - 48;
		return version;
	}
}