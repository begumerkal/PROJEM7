package com.wyd.protocol.s2s;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import com.wyd.protocol.INetSegment;
/**
 * 类 <code>S2SEncoder</code>对服务器间的数据进行编码
 * 
 * @see org.apache.mina.filter.codec.ProtocolEncoderAdapter
 * @since JDK 1.6
 */
public class S2SEncoder extends ProtocolEncoderAdapter {
    private static final Logger log = Logger.getLogger(S2SEncoder.class);

    /**
     * 对服务器间的数据进行编码
     */
    public void encode(IoSession session, Object data, ProtocolEncoderOutput out) throws Exception {
        INetSegment segment = (INetSegment) data;
        IoBuffer buffer = IoBuffer.allocate(128);
        buffer.setAutoExpand(true);
        buffer.put(INetSegment.HEAD);
        buffer.putInt(segment.getSessionId());
        buffer.putInt(segment.getSerial());
        buffer.putInt(18 + segment.size());
        buffer.putShort((short) 1);
        buffer.put(segment.getByteArray());
        buffer.put((byte) 0);
        buffer.flip();
        out.write(buffer);
        if ((segment.getFlag() & 0x1) != 0)
            log.debug("***Send Error: " + segment.getType() + "." + segment.getSubType());
        else
            log.debug("***Send Protocol: " + segment.getType() + "." + segment.getSubType());
    }
}
