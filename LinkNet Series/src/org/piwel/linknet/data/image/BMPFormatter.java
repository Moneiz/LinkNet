package org.piwel.linknet.data.image;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.opengl.PixelFormat;
import org.piwel.linknet.data.util.LittleEndianDataInputStream;

public class BMPFormatter {
	private int fileSize;
	private int offsetBMP;
	private int sizeHeader;
	private int width;
	private int height;
	private short bitPerPixel;
	
	private int compression;
	private int imageSize;
	private int xResolution;
	private int yResolution;
	private int numberColor;
	private int impColor;
	
	private byte[] imagesPixels;
	
	public BMPFormatter(String path) {
		try {
			LittleEndianDataInputStream dataInput = new LittleEndianDataInputStream(
					new BufferedInputStream(
					new FileInputStream(path)));
			boolean eof = false;
			dataInput.mark(0);
			dataInput.skipBytes(2);					// BM
			fileSize = dataInput.readLittleInt();	// File size
			dataInput.skipBytes(4);					// Reserved values
			offsetBMP = dataInput.readLittleInt();	// Offset begin image
			sizeHeader = dataInput.readLittleInt();	// Header size
			width = dataInput.readLittleInt();		// Width image
			height = dataInput.readLittleInt();		// Heigth image
			bitPerPixel = dataInput.readLittleShort();// Bits per pixel
			compression = dataInput.readLittleInt();// Compression bytes
			imageSize = dataInput.readLittleInt();	// Image size
			xResolution = dataInput.readLittleInt();// Xresolution
			yResolution = dataInput.readLittleInt();// Yresolution
			numberColor = dataInput.readLittleInt();// Number of color
			impColor = dataInput.readLittleInt();	// Important colors number
			
			loadPixel(dataInput);
			
			dataInput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void loadPixel(LittleEndianDataInputStream a) {
		
		imagesPixels = new byte[width * height * bitPerPixel * 3];
		
		try {
			a.reset();
			a.skipBytes(offsetBMP);
			int i = 0;
			while(true) {
				imagesPixels[i++] = a.readByte();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
