import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {

	private static ArrayList<String> documents;
	private static final String folderPath = "src/folder";
	private static ArrayList<FileContentReader> filesContent;
	private static Set<String> words;
	
	public static void main(String[] args) {
		documents = new ArrayList<>();
		filesContent = new ArrayList<>();
		words = new HashSet<>();
		createSet();
	}

	private static void createSet() {
		getDocumentsName();
		readFiles();
		deleteRepeated();
		createMatrix();
		
	}

	private static void getDocumentsName() {
		File[] listOfFiles = new File(folderPath).listFiles();
		Arrays.sort(listOfFiles);
	    for (File file : listOfFiles) {
	      if (file.isFile()) {
	    	  documents.add(file.getName());	        
	      }
	    }
	}

	private static void readFiles() {
		for(int index =0 ;index< documents.size(); index++) {
			FileContentReader contentReader = new FileContentReader(index);
			contentReader.readFile(folderPath+"/"+documents.get(index));
			filesContent.add(contentReader);
		}
	}

	private static void deleteRepeated() {
		for (FileContentReader reader : filesContent) {
			words.addAll(reader.getWords());
		}
	}
	
	private static void createMatrix() {
		System.out.print("\t");
		for (String word : words) {
			System.out.print(word+"\t");
		}
		System.out.println();
		for (FileContentReader reader : filesContent) {
			System.out.print(documents.get(reader.getFileNumber())+"\t");
			for (String word : words) {
				if(reader.exists(word)!=-1)
					System.out.print("1\t");
				else
					System.out.print("0\t");
			}
			System.out.println("");
		}
	}
	
}
