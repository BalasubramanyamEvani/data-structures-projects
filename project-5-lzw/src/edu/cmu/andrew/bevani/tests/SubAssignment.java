/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 5 Sub Assignment
*/
package edu.cmu.andrew.bevani.tests;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SubAssignment {
	
	private static byte[] buffer = new byte[3];
	private static int bufferSize = 0;
	
	private static final String dataStore = "text_ds";
	private static final String orig = "shortwords.txt";
	private static final String inputFile = "./" + dataStore + "/" + orig;
	private static final String compressedFile = "./" + "compressed_" + orig;
	private static final String decompressedFile = "./" + "decompressed_" + orig;
	
	public static void main(String[] args) {
		compress();
		decompress();
	}
	
	private static void decompress() {
		try (DataInputStream input = 
				new DataInputStream(new BufferedInputStream(new FileInputStream(compressedFile)));
				
			DataOutputStream output =
		                new DataOutputStream(
		                        new BufferedOutputStream(
		                                new FileOutputStream(decompressedFile)))) {
			try {
				while (true) {
					badDeCompress(input, output);
				}
			} catch (EOFException e) {
				System.out.println("EOF Reached");
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private static void compress() {
		try (DataInputStream input =
                new DataInputStream(
                        new BufferedInputStream(
                                new FileInputStream(inputFile)));
        DataOutputStream output =
                new DataOutputStream(
                        new BufferedOutputStream(
                                new FileOutputStream(compressedFile)))) {
			try {
				char byteIn;
				while (true) {
					byteIn = (char) input.readByte();
					byteIn = (char) (byteIn & 0xff);
					int val = byteIn;
					badCompress(val, output);
				}
			} catch (EOFException e) {
				System.out.println("EOF Reached");
			}
			if (bufferSize != 0) {
				writeBufferToFile(output, 2);
				bufferSize = 0;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void badCompress(int val, DataOutputStream output) throws IOException {
		if (bufferSize == 0) {
			buffer[0] = (byte) (val >>> 4);
			buffer[1] = (byte) (val << 4);
			bufferSize = 2;
		} else {
			buffer[2] = (byte) val; 
			writeBufferToFile(output, 3);
			bufferSize = 0;
		}
	}
	
	private static void badDeCompress(DataInputStream input, DataOutputStream output) throws IOException {
		int first = input.readByte() & 0xff;
		int second = input.readByte() & 0xff;
		int b = (first << 4) | (second >>> 4);
		output.write(b);
		int third = input.readByte() & 0xff;
		output.write(third);
	}
	
	private static void writeBufferToFile(DataOutputStream output, int len) throws IOException {
		for (int i = 0; i < len; ++i){
            output.writeByte(buffer[i]);
        }
	}
}
