
import java.io.File;

import java.io.IOException;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import java.util.*;
import tf_idf.Count_heap;
import Caching.LRUCache;
import org.apache.commons.io.FileUtils;


public class SearchEngineRunner {
	
	public static List<String> search_function(String search_word,List<String> ranking_list,String folder_name) throws IOException {
		ranking_list=SearchingWord.searchWord(search_word,folder_name);
		System.out.println("rankingList");
		if (ranking_list.size()>1) {
		for(String page:ranking_list)
		{
			if(page!="sample")
			  System.out.println(page);
		}
		}
		if(ranking_list.size()<=1) {
			System.out.println("word not found");
			
		}
		return ranking_list;
		
	}

	public static void main(String[] args) throws IOException {
		List<String> ranking_list_1=new ArrayList<String>();
		List<String> ranking_list_2=new ArrayList<String>();
		List<String> ranking_list_3=new ArrayList<String>();
		
		Arrays.stream(new File("./htmlpages/").listFiles()).forEach(File::delete);
		Arrays.stream(new File("./htmlToTextPages/").listFiles()).forEach(File::delete);
		Arrays.stream(new File("./cached_files/").listFiles()).forEach(File::delete);
		System.out.println("Done");
		
		HashMap<String, String> urlDict=Crawler.webCrawl(1,5,"https://www.geeksforgeeks.org/",new ArrayList<String>());
		System.out.println(Arrays.asList(urlDict)); 
		Scanner sc=new Scanner(System.in);
		System.out.println("option 1 for searching word");
		System.out.println("option 2 for searching word in cache");
		System.out.println("option 3 for providing sugesstions to word");
		System.out.println("option 4 for finding root word");
		System.out.println("Enter your option");
		Integer input_case = sc.nextInt();
		int end_loop=0;
		
		while(end_loop==0){
		switch (input_case) {
		  case 1:
			  	System.out.println("Enter the word to be searched");
			  	Scanner sc_1=new Scanner(System.in);
			  	String search_word=sc_1.nextLine();
			  	ranking_list_1=search_function(search_word,ranking_list_1,"./htmlToTextPages/");
				break;
		  case 2:
			  System.out.println("hiii");
			  for(String page:ranking_list_1)
				{System.out.println("hii");
					if(page!="sample")
					  System.out.println(page);
				}
				
			  	LRUCache cache = new LRUCache();
			  	System.out.println("Enter number of pages for caching purpose which should be less than   "+ranking_list_1.size());
			  	Scanner sc_2=new Scanner(System.in);
			  	Integer total_cach_pages=sc_2.nextInt();
				for(int i=0;i<total_cach_pages;i++) {
					System.out.println("Enter index of ranked list page");
					Integer index=sc_2.nextInt();
					cache.putElementInCache(index, ranking_list_1.get(index));
					}
				cache.populate_cache_pages();
				cache.add_cached_pages(urlDict);
				Scanner sc_5=new Scanner(System.in);
				System.out.println("Enter word to be searched in cache memory");
				String cache_word=sc_5.nextLine();
				ranking_list_2=search_function(cache_word,ranking_list_2,"./cached_files/");
				int size_of_ranking_list=ranking_list_2.size();
				if (size_of_ranking_list<=1) {
					System.out.println("word not found in cache so searching in main html page folder");
					search_function(cache_word,ranking_list_3,"./htmlToTextPages/");
					
				}
				
				break;
		  case 3:
			  	System.out.println("Enter a word to find alternate suggesions for it");
			  	Scanner sc_3=new Scanner(System.in);
				String sugesstion_word=sc_3.nextLine();
				WordSuggestions.suggestWord(sugesstion_word);
				break;
		  case 4:
			  System.out.println("Enter a sentance to find its root words");
			  Scanner sc_4=new Scanner(System.in);
			  String input_word=sc_4.nextLine();
		      lemetization slem = new lemetization();
		      System.out.println(slem.lemmatize(input_word));
			  break;
			  	
		}
		System.out.println("Enter your option");
		input_case = sc.nextInt();
		}

	}
	
	
		
	}


