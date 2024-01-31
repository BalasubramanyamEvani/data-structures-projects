/**
* @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
* Course: 95-771 A
* HW - 5
*/
package edu.cmu.andrew.bevani;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

import edu.cmu.andrew.bevani.prevhw.SinglyLinkedList;

/*
* This class is primary class which contains the
* blueprint of the LZWCompression scheme.
* 
* It has public methods:
* 
* compress -> which compresses the input stream and outputs
* result to the output stream passed to the method
* 
* decompress -> which decompresses the input stream and outputs
* result to the output stream passed to the method
*
* Class invariants:
* 
* dictSize -> The max size of the dictionary which will used in LZW,
* in case of overflow it'll be reset
* 
* compressMap -> The map which would contain the codewords for the input. used 
* during compression
* 
* decompressMap -> This map will be used during decompression and builds in the
* same the compressMap was built but this time it'd codeword to actual value
* 
* buffer -> This will be used to convert 8bits (1 byte) to 12 bits representation
* 
* bufferSize -> this will be used to track the size of buffer at time t
* 
* bytesRead -> to track number of bytes read during compression/decompression
* 
* bytesWritten -> to track number of bytes written during compression/decompression
* 
* codeword -> the list of codewords which would be constructed during decompression
* 
* readFinished -> singaling that no more bytes are left to be read, during decompression
*/
public class LZWCompressionUtil {
	
	/**
	 * Max Dictionary Size for the LZW Compression and
	 * Decompression scheme
	 * 
	 * 12 bits support - max 2^12 - 1
	 * 
	 * static and final across all instances
	 */
	private static final int dictSize = (1 << 12) - 1;
	
	// Class Invariants
	private MyMap<String, Integer> compressMap;
	
	private MyMap<Integer, String> decompressMap;
	
	private byte[] buffer;
	
	private int bufferSize;	

	private long bytesRead;
	
	private long bytesWritten;
	
	private SinglyLinkedList<Integer> codeword;
	
	private boolean readFinished = false;
	
	/**
	 * Non-parameterized constructor
	 * for initialization
	 */
	public LZWCompressionUtil() {
		buffer = new byte[3];
		bufferSize = 0;
		bytesRead = 0;
		bytesWritten = 0;
		codeword = new SinglyLinkedList<>();
	}
	
	/**
	 * 
	 * This is the primary method for compression
	 * 
	 * 1. Initially, a single byte is read
	 * 
	 * 2. The input stream is then read by byte. each time a
	 * byte is read, it's appended to the prior and then checked
	 * if it's present in the map, if it is then read more bytes
	 * 
	 * 3. In the case when the value is not in the map, we get the last
	 * value which was present in the map and output it, then the new
	 * value is stored in the map and the current size of the map is incremented
	 * 
	 * 4. Since we are using 12 bit codewords, when we hit the max capacity, flush out
	 * all values and reinitialize the map with it's initial 255 entries filled
	 * 
	 * 5. The write here actually fills the buffer. Once the buffer reaches it's
	 * 3 bytes capacity we write it to the output stream
	 * 
	 * 6. In the event there are still values left in the buffer (which is tracked using
	 * bufferSize variable). Flush out the value to the file and then finally return
	 * 
	 * @param input
	 * The input stream of the file which will be compressed
	 * 
	 * @param output
	 * The output stream of the file which is the compressed file
	 * 
	 * @param verbose
	 * signaling whether we need to print verbose output
	 * 
	 * @throws IOException
	 * Thrown in case an exception happens while reading files, it's handled
	 * in the main and respective streams are closed
	 */
	public void compress(DataInputStream input, DataOutputStream output, boolean verbose) throws IOException {
		init(true);
		char c;
		StringBuilder s = new StringBuilder();
		String tmp;
		int currSize = 256;
		bytesRead = 0;
		bytesWritten = 0;
		try {
			s.append(sioReadChar(input.readByte()));
			++bytesRead;
			while (true) {
				c = sioReadChar(input.readByte());
				++bytesRead;
				s.append(c);
				tmp = s.toString();
				if (compressMap.containsKey(tmp)) {
					continue;
				} else {
					int codeword = compressMap.getValue(s.substring(0, s.length() - 1));
					write(codeword, output);
					if (currSize <= dictSize) {
						compressMap.put(s.toString(), currSize++);
					} else {
						init(true);
						currSize = 256;
					}
					s = new StringBuilder(String.valueOf(c));
				}
			}
		} catch (IOException e) {
			input.close();
		}
		if (s.length() > 0) {
			int codeword = compressMap.getValue(s.toString());
			write(codeword, output);
		}
		if (bufferSize != 0) {
			writeBufferToFile(output, 2);
			bufferSize = 0;
		}
		if (verbose) {
			System.out.println(String.format("bytes read = %s, bytes written = %s", 
					String.valueOf(bytesRead), String.valueOf(bytesWritten)));
		}
	}
	
	/**
	 * 
	 * This is the primary method for decompression
	 * 
	 * 1. Initially data is read in form of 3 bytes from the input stream
	 * 
	 * 2. Now, till we have completely read and processed all the codewords (from
	 * the compressed file). we build an identical map (decompressMap) similar to the compress Map.
	 * And similar to compression, we reset the map once max capacity is reached
	 * 
	 * 3. During decompression, the decompressor would always one step behind w.r.t compressor.
	 * So, there might be a case when we encounter a value which might not be in the table because
	 * it would be inserted at the current step, so to deal with this, we extend the current code with
	 * first character from the prior.
	 * 
	 * @param input
	 * The input stream of the compressed file
	 * 
	 * @param output
	 * The output stream of the compressed file
	 * 
	 * @param verbose
	 * the variable which decided whether to print verbose output
	 * 
	 * @throws IOException
	 * thrown when unable to read bytes, handled in main and respective streams are closed
	 */
	public void decompress(DataInputStream input, DataOutputStream output, boolean verbose) throws IOException {
		init(false);
		int currSize = 256;
		bytesRead = 0;
		bytesWritten = 0;
		codeword = new SinglyLinkedList<>();
		readFinished = false;
		
		read(input);
		if (codeword.countNodes() != 0) {
			int prior = codeword.removeFirst();
			writeStringToFile(decompressMap.getValue(prior), output);
			while (codeword.countNodes() != 0) {
                int curr = codeword.removeFirst();
                if (decompressMap.containsKey(curr)) {
                    if (currSize <= dictSize) {
                        decompressMap.put(currSize++, decompressMap.getValue(prior) + decompressMap.getValue(curr).charAt(0));
                    } else {
                        init(false);
                        currSize = 256;
                    }
                    writeStringToFile(decompressMap.getValue(curr), output);

                } else {
                    String newCodeword = decompressMap.getValue(prior)
                            + decompressMap.getValue(prior).charAt(0);
                    if (currSize <= dictSize) {
                        decompressMap.put(currSize++, newCodeword);
                    } else {
                        init(false);
                        currSize = 256;
                    }
                    writeStringToFile(newCodeword, output);
                }
                prior = curr;
                read(input);
            }
        }
		if (verbose) {
			System.out.println(String.format("bytes read = %s, bytes written = %s", 
					String.valueOf(bytesRead), String.valueOf(bytesWritten)));
		}
	}
	
	/**
	 * 
	 * This methods writes the string value to the output data stream
	 * 
	 * @param val
	 * The value to be written to file
	 * 
	 * @param output
	 * The data output stream of the outgoing file
	 * 
	 * @throws IOException
	 * When unable to write a byte
	 */
	private void writeStringToFile(String val, DataOutputStream output) throws IOException {
		for (char ch: val.toCharArray()) {
			output.writeByte(ch);
			bytesWritten += 1;
		}
	}
	
	/**
	 * This is a utility method to read data from the input
	 * stream 3 bytes at a time during decompression
	 * 
	 * @param input
	 * the input stream of the compressed file
	 * 
	 * Once an EOFException is thrown, it marks that we have read
	 * all the bytes in the file. When this happen, further calls
	 * to the method will be ignored
	 * 
	 * @throws IOException
	 */
	private void read(DataInputStream input) throws IOException {
		if (!readFinished) {
			try {
				int first = sioReadChar(input.readByte());
				int second = sioReadChar(input.readByte());
				int b = (first << 4) | (second >>> 4);
				codeword.addAtEndNode(b);
				bytesRead += 2;
				int third = sioReadChar(input.readByte());
				codeword.addAtEndNode(((second & 0x0f) << 8) | third);
				bytesRead += 1;
			} catch (EOFException e) {
				readFinished = true;
			}
		}
	}
	
	/**
	 * Helper method to initialize respective maps in case of 
	 * overflow i.e. when the map reaches it's maximum capacity
	 * 
	 * @param compress
	 * Decided which map to initialize, during compression, compressMap is
	 * initialized and during decompression decompressMap is initialized
	 * 
	 */
	private void init(boolean compress) {
		if (compress) {
			compressMap = new MyMap<>();
			for (int i = 0; i < 256; ++i) {
				compressMap.put(String.valueOf((char) i), i);
			}
		} else {
			decompressMap = new MyMap<>();
			for (int i = 0; i < 256; ++i) {
				decompressMap.put(i, String.valueOf((char) i));
			}
		}
	}
	
	/**
	 * Utility method to safely read byte without
	 * unwanted sign extension
	 * 
	 * @param b
	 * the byte b which needs to be converted to a char value
	 * 
	 * @return
	 * char value of the byte
	 */
	private char sioReadChar(byte b) {
		return (char) (b & 0xff);
	}
	
	/**
	 * This method is used to write the codeword to the buffer. Once
	 * max limit of the buffer is reached we flush out the values to the outputstream.
	 * 
	 * Used in compression
	 * 
	 * @param val
	 * The value to be written during compression
	 * 
	 * @param output
	 * The output stream of the compressed file
	 * 
	 * @throws IOException
	 */
	private void write(int val, DataOutputStream output) throws IOException {
		if (bufferSize == 0) {
			buffer[0] = (byte) (val >>> 4);
			buffer[1] = (byte) (val << 4);
			bufferSize = 2;
		} else {
			buffer[1] = (byte) (buffer[1] | val >>> 8); 
			buffer[2] = (byte) val;
			writeBufferToFile(output, 3);
			bufferSize = 0;
		}
	}
	
	/**
	 * 
	 * This method is used to write the buffer contents to the
	 * data output stream. len value decided how much of the buffer
	 * needs to be written
	 * 
	 * @param output
	 * The output stream of the file to which the byte buffer contents
	 * will be written
	 * 
	 * @param len
	 * the parameter which decides how much of the buffer contents needs
	 * to be written
	 * 
	 * @throws IOException
	 * While writing byte to output stream
	 */
	private void writeBufferToFile(DataOutputStream output, int len) throws IOException {
		bytesWritten += len;
		for (int i = 0; i < len; ++i) {
			output.writeByte(buffer[i]);
		}
	}
}
