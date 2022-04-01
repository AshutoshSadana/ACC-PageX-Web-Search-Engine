import textprocessing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class SearchingWord {
	
	public static HashMap<String, Integer> urlOccurrences = new HashMap<String, Integer>(); // This will tell no. of occurences of a word
	public static HashMap<String, Integer> urlWordCount = new HashMap<String, Integer>(); // This will tell no. of words in a link.

	public static void searchWord(String word) throws IOException {
		File folder = new File("./htmlToTextPages/");
		
		File[] allFiles = folder.listFiles();
		//System.out.println(allFiles.length);
		for(int i=0;i<allFiles.length;i++) {
	        
	        int count=wordSearch(allFiles[i],word);
	        System.out.println(Crawler.urlDict.get(allFiles[i].getName())+" number of occurences: "+ count);
			urlOccurrences.put(Crawler.urlDict.get(allFiles[i].getName()),count);
		}
		
		
	       
		
	}
	public static int searchBruteForce(String word, String pattern) {

		int offset=BruteForceMatch.search1(word, pattern);
		return offset;
	}
	public static int wordSearch(File filePath, String word)
	{
		int count=0;
		String data="";
		try
		{
			BufferedReader Object = new BufferedReader(new FileReader(filePath));
			String line = null;
			
			while ((line = Object.readLine()) != null){
				data= data+line;
			}
			Object.close();
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		
		String t = data.toLowerCase(); 
		// To make the searching case insensitive
		//System.out.println(t.length());
		String link=Crawler.urlDict.get(filePath.getName());
		urlWordCount.put(link, t.length());
	
		
		int offsetw = 0;
		
		for (int loc = 0; loc <= t.length(); loc += offsetw + word.length()) 
		{	//System.out.println(t.substring(loc));
		
			offsetw = searchBruteForce(word, t.substring(loc)); 
			
			//System.out.println("offset "+ offsetw);
			if ((offsetw + loc) < t.length()) {
				count++;
			}
		}
		return count;
	}

}
