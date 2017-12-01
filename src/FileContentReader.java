import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileContentReader {
	private int fileNumber;
	private ArrayList<String> words;
	
	public FileContentReader(int fileNumber) {
		this.fileNumber = fileNumber;
		this.words = new ArrayList<>();
	}
	public int getFileNumber() {
		return this.fileNumber;
	}
	
	public ArrayList<String> getWords() {
		return this.words;
	}
	
	public void readFile(String path) {

		try {
			Scanner cin = new Scanner(new File(path));
			while (cin.hasNext()) {
				this.words.add(cin.next());
			}
			cin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public int exists(String word) {
		return this.words.indexOf(word);
	}
	
}
