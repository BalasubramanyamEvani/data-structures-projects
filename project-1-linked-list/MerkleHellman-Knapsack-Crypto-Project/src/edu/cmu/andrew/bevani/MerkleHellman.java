/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 1, part - 2
 */
package edu.cmu.andrew.bevani;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Scanner;

public class MerkleHellman {
	
	// will be static - memory allocated once for all instances
	private static final int MAX_NODES = 640;
	private static final int SUPER_INCREASING_SEQUENCE_START = 7;
	
	// Invariant of the MerkleHellman class:
	
	private PrivateKey privateKey;
	
	private PublicKey publicKey;
	
	private BigInteger rInverse;
	
	/**
	 * @note
	 * 	Constructor - calls the keyGen() function
	 * 	to create public-private key pair
	 */
	public MerkleHellman() {
		keyGen();
	}
	
	/**
	 * @note
	 * 	Inside the keyGen() method
	 * 	1. w is generated using init values, step = 1, val = 7 which
	 * 	   would result in {1, 7, 49, ...}
	 * 	2. Sum of w is calculated simultaneously while we generate w
	 * 	3. q is found by doing +10 since q > sum of wi
	 * 	4. since r and q need to be a co prime pair, any consecutive term will
	 * 	   suffice. Here r is obtained by subtracting 1 from q
	 *  5. Now, we have w,r,q we can calculate b (which is rwi)
	 *  6. Finally storing the public, private and modular inverse of r modulo q
	 */
	private void keyGen() {
		SinglyLinkedList w = new SinglyLinkedList();
		
		BigInteger step = BigInteger.ONE;
		BigInteger val = BigInteger.valueOf(SUPER_INCREASING_SEQUENCE_START);
		
		BigInteger sum = BigInteger.ZERO;
		
		for (int i = 0; i < MAX_NODES; ++i) {
			w.addAtEndNode(step);
			sum = sum.add(step);
			step = step.multiply(val);
		}
		
		BigInteger q = sum.add(BigInteger.valueOf(10));
		BigInteger r = q.subtract(BigInteger.ONE);
		
		SinglyLinkedList b = new SinglyLinkedList();
		for (int i = 0; i < MAX_NODES; ++i) {
			BigInteger rwi = r.multiply((BigInteger) w.getObjectAt(i)).mod(q);
			b.addAtEndNode(rwi);
		}
		
		publicKey = new PublicKey(b);
		privateKey = new PrivateKey(w, q, r);
		rInverse = r.modInverse(q);
	}
	
	/**
	 * @precondition
	 * 	1. The message needs to be UTF-8 encoded, though we have placed
	 * 	   a check inside the method, it's best to call only when it's true
	 *  2. Message length > 0 and <= 80. Check has been placed. It's better to
	 *     not call the method or else one needs to handle the runtime exception
	 * @postcondition
	 * 	Returns the encoded message as String
	 *  
	 * @param message
	 * 
	 * @note
	 * 	The encoded BigInteger is created by selected each bi where
	 * 	mi (message bit) is not 0 and then adding them
	 * 
	 * @return
	 * 	Returns the encrypted message
	 * 
	 * @throws UnsupportedEncodingException
	 * 	When not UTF-8 Encoded
	 */
	public String encrypt(String message) throws UnsupportedEncodingException {
		if (message.isEmpty()) {
			throw new IllegalArgumentException("String arg cannot be empty");
		}
		
		if (message.length() > 80) {
			throw new IllegalArgumentException("String arg length cannot exceed: 80");
		}

		byte[] bytes = message.getBytes("UTF-8");
		BigInteger c = BigInteger.ZERO;
		int index = 0;
		
		for (byte b: bytes) {
			int val = b;
			for (int i = 0; i < 8; ++i) {
				if((val & 128) != 0) {
					c = c.add((BigInteger) publicKey.getB().getObjectAt(index));
				}
				val = val << 1;
				++index;
			}
		}
		
		return c.toString();
	}
	
	/**
	 * @precondition
	 *  1. Up to the user to make sure the passed cipherText is made from a 
	 *     message which was at max 80 chars in length
	 *     
	 * @postcondition
	 * 	Returns the decoded message as String
	 *  
	 * @param cipherText
	 * @return
	 * 	Returns the decoded value of the cipher
	 */
	public String decrypt(String cipherText) {
		BigInteger c = new BigInteger(cipherText);
		BigInteger cprime = c.multiply(rInverse).mod(privateKey.getQ());
		
		BigInteger zero = BigInteger.ZERO;
		int index = MAX_NODES - 1;
		SinglyLinkedList x = new SinglyLinkedList();
		
		while (cprime.compareTo(zero) > 0 && index >=0) {
			BigInteger wj = (BigInteger) privateKey.getW().getObjectAt(index);
			if (wj.compareTo(cprime) <= 0) {
				cprime = cprime.subtract(wj);
				x.addAtFrontNode('1');
			} else {
				x.addAtFrontNode('0');
			}
			--index;
		}
		return new String(new BigInteger(x.toString(), 2).toByteArray());
	}
	
	// Driver
	public static void main(String[] args) {
		MerkleHellman merkleHellman = new MerkleHellman();
		Scanner sc = new Scanner(System.in);
		String input;
		while (true) {
			System.out.println("Enter a string and I will encrypt it as single large integer.");
			input = sc.nextLine();
			if (input.isEmpty() || input.length() > 80) {
				System.out.println("Invalid String. Entered string length should be greater than 0 "
						+ "and less than equal to 80, but it was: " + input.length());
				break;
			}
			System.out.println("Clear Text: ");
			System.out.println(input);
			System.out.println("Number of clear text bytes: " + input.length());
			String cipherText;
			try {
				cipherText = merkleHellman.encrypt(input);
				System.out.println(input + " is encrypted as: ");
				System.out.println(cipherText);
				System.out.println("Result of decryption: " + merkleHellman.decrypt(cipherText));
			} catch (UnsupportedEncodingException e) {
				System.out.println("Only UTF-8 encoded message supported.");
			}
		}
		sc.close();
	}
}
