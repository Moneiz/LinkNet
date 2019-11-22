package org.piwel.linknet.data.util;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LittleEndianDataOutputStream extends OutputStream implements DataOutput {

	private DataOutputStream dataOutStream;
	private OutputStream outStream;
	private byte byteBuffer[];
	
	public LittleEndianDataOutputStream(OutputStream out) {
		outStream = out;
		dataOutStream = new DataOutputStream(out);
		byteBuffer = new byte[8];
	}
	
	@Override
	public void writeBoolean(boolean v) throws IOException {
		dataOutStream.writeBoolean(v);
	}

	@Override
	public void writeByte(int v) throws IOException {
		dataOutStream.writeByte(v);
	}

	@Override
	public void writeBytes(String s) throws IOException {
		dataOutStream.writeBytes(s);
	}

	@Override
	public void writeChar(int v) throws IOException {
		dataOutStream.writeChar(v);
	}

	@Override
	public void writeChars(String s) throws IOException {
		dataOutStream.writeChars(s);
	}

	@Override
	public void writeDouble(double v) throws IOException {
		dataOutStream.writeDouble(v);
	}

	@Override
	public void writeFloat(float v) throws IOException {
		dataOutStream.writeFloat(v);
	}

	@Override
	public void writeInt(int v) throws IOException {
		dataOutStream.writeInt(v);
	}

	@Override
	public void writeLong(long v) throws IOException {
		dataOutStream.writeLong(v);
	}

	@Override
	public void writeShort(int v) throws IOException {
		dataOutStream.writeShort(v);
	}

	@Override
	public void writeUTF(String s) throws IOException {
		dataOutStream.writeUTF(s);
	}

	@Override
	public void write(int b) throws IOException {
		outStream.write(b);
	}

	public final int writeLittleInt() throws IOException {
		dataOutStream.write(byteBuffer, 0, 4);
		return (byteBuffer[0] & 0xff) | (byteBuffer[1] & 0xff) >> 8 |
				(byteBuffer[2] & 0xff) >> 16 |(byteBuffer[3]) >> 24 
		;
	}
	
	public final short writeLittleShort() throws IOException {
		dataOutStream.write(byteBuffer, 0, 2);
		return (short)((byteBuffer[0] & 0xff) | (byteBuffer[1] & 0xff) >> 8);
	}
	
}
