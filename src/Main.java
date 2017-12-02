import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {

	static ArrayList<String> documents;
	static final String folderPath = "src/folder";
	static ArrayList<FileContentReader> filesContent;
	static ArrayList<String> words;
	static int matrix[][];
	static final String[] stopWords = {
			"and",
			"not",
			"or"
	};
	
	public static void main(String[] args) {
		documents = new ArrayList<>();
		filesContent = new ArrayList<>();
		words = new ArrayList<>();
		createSet();
		String query = "information and retrieval";
		getOperation(query);
	}

	private static void getOperation(String query) {
		String []info = query.split("\\s+");
		int size= info.length;
		int[] binaries = new int[size];
		for(int x=0; x<size; x++) {
			int index;
			String currentToken =info[x];
			if((index=exists(currentToken))==-5) {
				index=words.indexOf(currentToken);
			}
			binaries[x]=index;
		}
		for(int in=0;in<binaries.length;in++) {
			int column =binaries[in];
			if(column > -1 ) {
				int temp =0;
				for(int row=0;row<documents.size();row++) {
					temp *=10;
					temp+= matrix[row][column];
				}
				binaries[in] = temp;
			}
			else if(column==-1)
				binaries[in]=0;
		}
		solveBinary(binaries);
	}

	private static void solveBinary(int[] binaries) {
		/*
		  	code to solve the array.
		  	-2 means and
		  	-3 means not
		  	-4 means or		  
		 */
	}

	private static int exists(String string) {
		string = string.toLowerCase();
		if(string.equals(stopWords[0]))
			return -2;
		else if(string.equals(stopWords[1]))
			return -3;
		else if(string.equals(stopWords[2]))
			return -4;
		return -5;
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
		Set<String> temp= new HashSet<>();
		for (FileContentReader reader : filesContent) {
			temp.addAll(reader.getWords());
		}
		words.addAll(temp);
	}
	
	private static void createMatrix() {
		int rowL =documents.size();
		int colL = words.size();
		matrix = new int[rowL][colL];
		for (int row=0;row < rowL; row++) {
			FileContentReader reader = filesContent.get(row);
			for (int col=0; col< colL;col++) {
				if(reader.exists(words.get(col))!=-1)
					matrix[row][col] = 1;
				else
					matrix[row][col] = 0;
			}
		}
	}
	
	
}
