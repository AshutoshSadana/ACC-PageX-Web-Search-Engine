
import java.io.File;

import java.io.IOException;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import java.util.*;
import tf_idf.Count_heap;
import Caching.LRUCache;
import org.apache.commons.io.FileUtils;
public class SearchEngineRunner {

	public static void main(String[] args) throws IOException {
		List<String>ranking_list=new ArrayList<String>();
		
		Arrays.stream(new File("./htmlpages/").listFiles()).forEach(File::delete);
		Arrays.stream(new File("./htmlToTextPages/").listFiles()).forEach(File::delete);
		Arrays.stream(new File("./cached_files/").listFiles()).forEach(File::delete);
		System.out.println("Done");
		
		HashMap<String, String> urlDict=Crawler.webCrawl(1,5,"https://en.wikipedia.org/wiki/Computer",new ArrayList<String>());
		System.out.println(Arrays.asList(urlDict)); 
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the word to be searched");
		String word=sc.nextLine();
		ranking_list=SearchingWord.searchWord(word,"./htmlToTextPages/");
		

		System.out.println("rankingList");
		for(String fruit:ranking_list)  
			  System.out.println(fruit);  
		LRUCache cache = new LRUCache();
		System.out.println("Enter number of pages for caching purpose which should be less than   "+ranking_list.size());
		String total_cach_pages=sc.nextLine();
		
		for(int i=0;i<Integer.parseInt(total_cach_pages);i++) {
			String index=sc.nextLine();
			cache.putElementInCache(Integer.parseInt(index), ranking_list.get(Integer.parseInt(index)));
			
			
		}
		
		cache.populate_cache_pages();
		cache.add_cached_pages(urlDict);
		System.out.println("Enter word to be searched in cache memory");
		String cache_word=sc.nextLine();
		System.out.println("rankingList");
		ranking_list=SearchingWord.searchWord(cache_word,"./cached_files/");
		 
	}
	
	
		
	}


