import java.util.ArrayList;
import java.util.Scanner;

public class offsetCalc {

	/*
	 * Christopher R. Fischer Freedom High School 05/28/2017 - Built in
	 * Washington DC. Built for calculating offsets for fuzzing. Based off:
	 * https://projects.jason-rush.com/tools/buffer-overflow-eip-offset-string-
	 * generator/ Credit to Jon Skeet on Stack Exchange for ReverseHex
	 * (shamelessly stolen); see function for reference.
	 */

	static char[] alph1 = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	static char[] alph2 = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
	static char[] alph3 = "1234567890-=[];',./`~!@#$%^&*()_+{}|:<>?".toCharArray();
	// Max Length is 20280 with alph, alph, and 1234567890.
	// New Max length is 81120.
	// System.out.println("L"+out.length());

	static ArrayList<Byte> byteOut = new ArrayList<Byte>();
	// String that will be initialized with the alphabet we will be inserting.
	static String outString = "";

	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		System.out.print("Requested number of characters\n>> ");
		// Number of characters to output
		int outNum = stdin.nextInt();
		for (int i = 0; i < alph1.length; i++) {
			for (int j = 0; j < alph2.length; j++) {
				for (int k = 0; k < alph3.length; k++) {
					outString += (alph1[i] + "" + alph2[j] + "" + alph3[k] + "");
					/*
					 * for (Byte e : (alph1[i] + "" + alph2[j] + "" + alph3[k] +
					 * "").getBytes()) { byteOut.add(e);
					 * System.out.print(e.intValue()+"1"); }
					 */
					if (outString.length() >= outNum) {
						break;
					}
				}
			}
		}
		System.out.println();
		System.out.println(outString.substring(0, outNum));
		// Big Ordering Creation
		String BEOrder = "";
		for (Byte t : outString.substring(0, outNum).getBytes()) {
			BEOrder += ("" + Integer.toHexString(t));
		}
		// Little Ordering Creation
		BEOrder = BEOrder.toLowerCase();
		String LEOrder = "";
		for (Byte t : outString.substring(0, outNum).getBytes()) {
			LEOrder += (Integer.toHexString(t));
		}
		// Convert from BE to LE.
		LEOrder = reverseHex(LEOrder).toLowerCase();
		
		System.out.print("\nEnter the EIP pointer. \n>> ");
		stdin.nextLine();
		String EIP = stdin.nextLine().toLowerCase();
		// Turns out, Java loves being useful for once in its life.
		System.out.println("Little-Endian Offset  " + ((outNum - LEOrder.indexOf(EIP) / 2) - 4));
		System.out.println("Big-Endian Offset     " + ((outNum - BEOrder.indexOf(EIP) / 2) - 4));
		stdin.close();
	}

	public static String reverseHex(String originalHex) {
		// TODO: Validation that the length is even
		// Shamelessly stolen from StackExchange :P
		// https://stackoverflow.com/questions/32693406/java-convert-big-endian-to-little-endian
		// Shoutout to Jon Skeet, you da boy.
		int lengthInBytes = originalHex.length() / 2;
		char[] chars = new char[lengthInBytes * 2];
		for (int index = 0; index < lengthInBytes; index++) {
			int reversedIndex = lengthInBytes - 1 - index;
			chars[reversedIndex * 2] = originalHex.charAt(index * 2);
			chars[reversedIndex * 2 + 1] = originalHex.charAt(index * 2 + 1);
		}
		return new String(chars);
	}

}
