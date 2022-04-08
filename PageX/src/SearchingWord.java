import textprocessing.*;
import tf_idf.tf_idf_new;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
public class SearchingWord {
	
	public static HashMap<String, Integer> urlOccurrences = new HashMap<String, Integer>(); // This will tell no. of occurences of a word
	public static HashMap<String, Integer> urlWordCount = new HashMap<String, Integer>(); // This will tell no. of words in a link.
	public static HashMap<String,Double> tf_idf_score = new HashMap<String,Double>();
	public static List<String>ranking_list=new ArrayList<String>();
	
	public static List<String> searchWord(String word,String folder_address) throws IOException {
		System.out.println(folder_address);
		System.out.println(word);
		File folder = new File(folder_address);
		Integer documents_containing_key_word =0;
		File[] allFiles = folder.listFiles();
		System.out.println("length of files");
		System.out.println(allFiles.length);
		for(int i=0;i<allFiles.length;i++) {
	        int count=wordSearch(allFiles[i],word);
	        System.out.println(Crawler.urlDict.get(allFiles[i].getName())+" number of occurences: "+ count);
			urlOccurrences.put(Crawler.urlDict.get(allFiles[i].getName()),count);
			if (count>=1) {
				documents_containing_key_word=documents_containing_key_word+1;				
			}
			
		}
		
		tf_idf_score=tf_idf_new.compute_tf_idf(urlOccurrences,urlWordCount,documents_containing_key_word);
		ranking_list=tf_idf_new.populate_heap(tf_idf_score);
		urlOccurrences.clear();
		urlWordCount.clear();
		tf_idf_score.clear();
		
		return ranking_list;
		
	       
		
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
