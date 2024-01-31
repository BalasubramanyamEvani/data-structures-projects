/**
 * @author Balasubramanyam Evani (bevani@andrew.cmu.edu)
 * Course: 95-771 A
 * HW - 1, part - 3
 */
package edu.cmu.andrew.bevani;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import edu.colorado.nodes.ObjectNode;

public class MerkleTree {
	
	// Invariant of the MerkleTree class:
	private SinglyLinkedList res;
	
	/**
	 * @note
	 * 	Constructs the MerkleTree
	 * 	where the res list points to list of the individula levels of the tree
	 * 	The last list in res would be the MerkleRoot
	 * 
	 * @param lines
	 * @throws NoSuchAlgorithmException
	 */
	public MerkleTree(SinglyLinkedList lines) throws NoSuchAlgorithmException {
		if (lines.countNodes() < 1) {
			throw new IllegalArgumentException("Empty lines.");
		}
		res = new SinglyLinkedList();
		
		if (isOdd(lines.countNodes())) {
			lines.addAtEndNode(lines.getLast());
		}
		res.addAtEndNode(lines);
		SinglyLinkedList hashedLines = transform(lines);
		res.addAtEndNode(hashedLines);
		
		SinglyLinkedList curr = (SinglyLinkedList) res.getLast();
		
		while (curr.countNodes() > 1) {
			SinglyLinkedList tmpList = new SinglyLinkedList();
            curr.reset();
            while (curr.hasNext()) {
                String h1 = (String) curr.next();
                String h2 = (String) curr.next();
                tmpList.addAtEndNode(h(h1 + h2));
            }
			if (tmpList.countNodes() > 1 && isOdd(tmpList.countNodes())) {
				tmpList.addAtEndNode(tmpList.getLast());
			}
			res.addAtEndNode(tmpList);
			curr = (SinglyLinkedList) res.getLast();
		}
	}
	
	/**
	 * Routine Complexity: Θ(1)
	 * 
	 * @return
	 * 	Returns the final list which has just one node (root of the Merkle Tree)
	 */
	public String getRoot() {
		return (String) (((SinglyLinkedList) res.getLast()).getHead().getData());
	}
	
	/**
	 * Routine Complexity: Θ(1)
	 * 
	 * @param num
	 * @return
	 * 	Returns true if num is odd
	 */
	private boolean isOdd(int num) {
		return (num & 1) == 1;
	}
	
	/**
	 * Routine Complexity: Θ(n)
	 * 
	 * @param list
	 * @return
	 * 	Returns the 1st level transformation in MerkleTree where each node's string needs
	 * 	to be hashed
	 * @throws NoSuchAlgorithmException
	 */
	private SinglyLinkedList transform(SinglyLinkedList list) throws NoSuchAlgorithmException {
		SinglyLinkedList res = new SinglyLinkedList();
		ObjectNode ptr = list.getHead();
		while (ptr != null) {
			String content = (String) ptr.getData();
			res.addAtEndNode(h(content));
			ptr = ptr.getLink();
		}
		return res;
	}
	
	/**
	 * 
	 * @param text
	 * @return
	 * 	Returns the hash of the passed text (String)
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public static String h(String text) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i <= 31; i++) {
			byte b = hash[i];
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}
	
	// Driver
	// 
	// All Merkle Roots:
	//
	// **CrimeLatLonXY.csv -> A5A74A770E0C3922362202DAD62A97655F8652064CCCBE7D3EA2B588C7E07B58
	// CrimeLatLonXY1990_Size2.csv -> DDD49991D04273A7300EF24CFAD21E2706C145001483D161D53937D90F76C001
	// CrimeLatLonXY1990_Size3.csv -> 313A2AD830ED85B5203C8C2A9895ADFA521CD4ABB74B83C25DA2C6A47AE08818
	// smallFile.txt -> A4E10610B30C40CA608058C521AD3D9EEC42C1892688903984580C56D3CF8A7D
	//
	// **This is the Merkle Root we seek
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String fileName = "";
		while (true) {
			System.out.print("Enter file name: ");
			fileName = sc.nextLine();
			try(Scanner fileScanner = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
				SinglyLinkedList list = new SinglyLinkedList();
				while (fileScanner.hasNext()) {
				    list.addAtEndNode(fileScanner.nextLine());
				}
				MerkleTree merkleTree = new MerkleTree(list);
				System.out.println("Merkle Root is: " + merkleTree.getRoot());
			} catch (NoSuchAlgorithmException e) {
				System.out.println("No such algorithm exception: " + e.getMessage());
				break;
			} catch (IOException e1) {
				System.out.println("IO Exception: " + e1.getMessage());
				break;
			}
		}
		sc.close();
	}
}
