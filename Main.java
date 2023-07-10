package letterMutation;

import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;

/**
 * Main.java
 * driver method of letter mutation program
 * @author Blair Wang
 * @version 1.0: December 16, 2021
 */
public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		String fileName = JOptionPane.showInputDialog("Enter the file name (With .txt):" + '\n' + "(Download and use dictionary.txt from this folder)");
		File file = new File("C:\\Users\\hocke\\OneDrive\\Documents\\dictionary.txt");
		Scanner fileIn = new Scanner(file);
		Set<String> dictionary = new HashSet<>();
		while(fileIn.hasNextLine()) {
			dictionary.add(fileIn.nextLine().toUpperCase());
		}
		fileIn.close();
		String start = JOptionPane.showInputDialog("Enter the starting word: ").toUpperCase();
		String end = JOptionPane.showInputDialog("Enter the ending word: ").toUpperCase();
		
		LetterMutation letterMutation = new LetterMutation(start, end, dictionary);
		letterMutation.pathSearch();
		JOptionPane.showMessageDialog(null, "Results Printed in Console");
	}

}
