package org.piwel.linknet.data.util;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LittleEndianDataInputStream extends InputStream implements DataInput{

	private DataInputStream dataInStream;
	private InputStream inStream;
	private byte byteBuffer[];
	
	public LittleEndianDataInputStream(InputStream in) {
		inStream = in;
		dataInStream = new DataInputStream(in);
		byteBuffer = new byte[8];
	}

	@Override
	public void readFully(byte[] b) throws IOException {
		dataInStream.readFully(b,0,b.length);
	}

	@Override
	public void readFully(byte[] b, int off, int len) throws IOException {
		dataInStream.readFully(b,off,len);
	}

	@Override
	public int skipBytes(int n) throws IOException {
		return dataInStream.skipBytes(n);
	}

	@Override
	public boolean readBoolean() throws IOException {
		return dataInStream.readBoolean();
	}

	@Override
	public byte readByte() throws IOException {
		return dataInStream.readByte();
	}

	@Override
	public int readUnsignedByte() throws IOException {
		return dataInStream.readUnsignedByte();
	}

	@Override
	public short readShort() throws IOException {
		return dataInStream.readShort();
	}

	@Override
	public int readUnsignedShort() throws IOException {
		return dataInStream.readUnsignedShort();
	}

	@Override
	public char readChar() throws IOException {
		return dataInStream.readChar();
	}

	@Override
	public int readInt() throws IOException {
		return dataInStream.readInt();
	}

	@Override
	public long readLong() throws IOException {
		return dataInStream.readLong();
	}

	@Override
	public float readFloat() throws IOException {
		return Float.intBitsToFloat(readInt());
	}

	@Override
	public double readDouble() throws IOException {
		return Double.longBitsToDouble(readLong());
	}

	@Override
	@Deprecated
	public String readLine() throws IOException {
		return dataInStream.readLine();
	}

	@Override
	public String readUTF() throws IOException {
		return dataInStream.readUTF();
	}

	@Override
	public int read() throws IOException {
		return inStream.read();
	}
	
	public final int readLittleInt() throws IOException {
		dataInStream.readFully(byteBuffer, 0, 4);
		return (byteBuffer[3]) << 24 | (byteBuffer[2] & 0xff) << 16 |
		(byteBuffer[1] & 0xff) << 8 | (byteBuffer[0] & 0xff);
	}
	
	public final short readLittleShort() throws IOException {
		dataInStream.readFully(byteBuffer, 0, 2);
		return (short)((byteBuffer[1] & 0xff) << 8 | (byteBuffer[0] & 0xff));
	}
}
