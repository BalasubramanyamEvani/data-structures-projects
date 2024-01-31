/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 5
*/
package edu.cmu.andrew.bevani;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
* This class is the main driver program with the main
* method to compress and decompress files.
* 
* Command To compress:
* 
* -c -v path_to_original_file path_to_compressed_file
* 
* Command To decompress:
* 
* -d -v path_to_compressed_file path_to_decompressed_file
* 
* -v -> verbose output would show bytes written and read
* 
*/
public class LZWCompression {
	
	/**
	 * Main method which accepts the passed in command line
	 * arguments
	 * 
	 * @param args
	 * String array
	 */
	public static void main(String[] args) {
        if(args[0].equals("-c")){
        	if (args.length == 4) {
        		if (args[1].equals("-v")) {
        			lzw(args[2], args[3], true, true);
        		} else {
        			System.out.println("Invalid argument encountered: " + args[1]);
        		}
        	} else {
        		lzw(args[1], args[2], true, false);
        	}
        } else if (args[0].equals("-d")) {
        	if (args.length == 4) {
        		if (args[1].equals("-v")) {
        			lzw(args[2], args[3], false, true);
        		} else {
        			System.out.println("Invalid argument encountered: " + args[1]);
        		}
        	} else {
        		lzw(args[1], args[2], false, false);
        	}
        } else {
        	System.out.println("Invalid Args encountered: " + args[0]);
        }
	}
	
	/**
	 * This is a static helper method which initializes the LZWCompression
	 * class and based on the passed in arguments, decides whether to run
	 * the compression logic or the decompression logic
	 * 
	 * @param inputFile
	 * The input file path which will be used during compression/decompression
	 * 
	 * @param outputFile
	 * The output file path which will be used during compression/decompression
	 * 
	 * @param compress
	 * boolean variable, true -> compression, false -> decompression
	 * 
	 * @param verbose
	 * boolean variable, true-> read and written bytes are printed
	 * 
	 * Degree of compression Obtained:
	 * ----------------------------------
	 * 
	 * 		File					Original			  Compressed
	 * 1. words.html			24,93,869 bytes		 	10,70,507 bytes  	near about 43%
	 * 2. CrimeLatLonXY.csv		26,08,940 bytes		 	12,84,017 bytes		near about 49%
	 * 3. 01_Overview.mp4		2,50,08,838 bytes		3,37,69,127 bytes	near about 135% (no compression)
	 * 
	 */
	private static void lzw(String inputFile, String outputFile, boolean compress, boolean verbose) {
		try (DataInputStream input =
                new DataInputStream(
                        new BufferedInputStream(
                                new FileInputStream(inputFile)));
        DataOutputStream output =
                new DataOutputStream(
                        new BufferedOutputStream(
                                new FileOutputStream(outputFile)))) {
			try {
				LZWCompressionUtil lzw = new LZWCompressionUtil();
				if (compress) {
					System.out.println("Compressing " + inputFile);
					lzw.compress(input, output, verbose);
					System.out.println("Compression Done");
				} else {
					System.out.println("De-Compressing " + inputFile);
					lzw.decompress(input, output, verbose);
					System.out.println("De-Compression Done");
				}
			} catch (EOFException e) {
				e.printStackTrace();
				System.out.println("EOF Reached");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
