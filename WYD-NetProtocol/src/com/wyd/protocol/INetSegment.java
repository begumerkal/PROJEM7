package com.wyd.protocol;
public abstract interface INetSegment {
    public static final byte[] HEAD         = { 'H', 'O', 'G', 'P'};
    public static final byte[] EMTPY_PACKET = { 'H', 'O', 'G', 'P', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 18, 0, 0, 0};

    public abstract byte getType();

    public abstract int getSessionId();

    public abstract void setSessionId(int paramInt);

    public abstract byte getNumOfParameter();

    public abstract int getSerial();

    public abstract void setSerial(int paramInt);

    public abstract void write(byte paramByte);

    public abstract void write(byte[] paramArrayOfByte);

    public abstract void writeBoolean(boolean paramBoolean);

    public abstract void writeBooleans(boolean[] paramArrayOfBoolean);

    public abstract void writeShort(short paramShort);

    public abstract void writeShorts(short[] paramArrayOfShort);

    public abstract void writeInt(int paramInt);

    public abstract void writeInts(int[] paramArrayOfInt);

    public abstract void writeLong(long paramLong);

    public abstract void writeLongs(long[] paramArrayOfLong);

    public abstract void writeString(String paramString);

    public abstract void writeStrings(String[] paramArrayOfString);

    public abstract int size();

    public abstract byte[] getByteArray();

    public abstract byte getFlag();

    public abstract byte getSubType();

    public abstract byte[] getPacketByteArray();
}
