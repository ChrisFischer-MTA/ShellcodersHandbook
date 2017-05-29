import java.io.IOException;
import java.util.Scanner;

public class injectableGenerator {
	/*
	 * Christopher R. Fischer 05/28/2017 Intermediate program to convert hex
	 * codes to proper ediden and put them in a python print statement. Made in
	 * lieu of of something that prints itself (because Java prints raw bytes
	 * weird).
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner stdin = new Scanner(System.in);
		System.out.print("Input the 8 digit hex codes seperated by spaces. \n>>");
		// Split each into 8 digits, as provided by user.
		String[] r = stdin.nextLine().trim().toLowerCase().split(" ");

		// Initialize variables!
		String original;
		String outputStatement;
		int wordCount = 0;

		System.out.print("print \"");
		for (String curr : r) {
			outputStatement = "";
			// For each word, put in correct order.
			original = reverse8(curr);
			// Increment wordCounter.
			wordCount++;
			// For every two letters, insert \x for hex notation.
			for (int i = 0; i < original.length(); i++) {
				if (i % 2 == 0) {
					outputStatement += "\\x";
				}
				outputStatement += original.charAt(i);
			}
			System.out.print(outputStatement);

		}
		System.out.print("\"\n");
		System.out.println("Characters to deduct: " + wordCount);
		stdin.close();
	}

	public static String reverse8(String a) {
		// Method for taking in 8 bytes and outputting it.
		char[] b = a.toCharArray();
		// Hardcoded values because I'm too lazy to do otherwise and the simple
		// solutions sometimes are the best.
		return (b[6] + "" + b[7] + "" + b[4] + "" + b[5] + "" + b[2] + "" + b[3] + "" + b[0] + "" + b[1]);

	}
}
