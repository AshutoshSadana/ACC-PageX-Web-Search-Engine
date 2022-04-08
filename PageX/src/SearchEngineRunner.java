import java.io.File;

import java.io.IOException;

import java.util.*;

import Caching.LRUCache;
import yodafy.YodaMode;


public class SearchEngineRunner {

    public static List < String > search_function(String search_word, List < String > ranking_list, String folder_name) throws IOException {
        ranking_list = SearchingWord.searchWord(search_word, folder_name);
        System.out.println("");
        System.out.println("List of files in order of Score");
        if (ranking_list.size() > 1) {
            for (String page: ranking_list) {
                if (page != "sample") {
                    System.out.printf("Occurance found in Page: " + page + "\n");
                }
            }
        }
        System.out.printf("\n");

        if (ranking_list.size() <= 1) {
            System.out.println("word not found");

        }
        return ranking_list;

    }

    public static void main(String[] args) throws IOException {
        List < String > listOfPagesWithRank = new ArrayList < String > ();
        List < String > cachedRankingList = new ArrayList < String > ();
        List < String > ranking_list_3 = new ArrayList < String > ();

        Arrays.stream(new File("./htmlpages/").listFiles()).forEach(File::delete);
        Arrays.stream(new File("./htmlToTextPages/").listFiles()).forEach(File::delete);
        Arrays.stream(new File("./cached_files/").listFiles()).forEach(File::delete);
        System.out.println("Previous files cleared!");

        HashMap < String, String > urlDict = Crawler.webCrawl(1, 10, "https://piyushmehta.com", new ArrayList < String > ());
        try (Scanner sc = new Scanner(System.in)) {
			System.out.println("====================Enter your option====================");
			System.out.println("1 for searching word");
			System.out.println("2 for searching word in cache");
			System.out.println("3 for providing sugesstions to word");
			System.out.println("4 for checking how yoda will speak your sentence *experimental* *STAR WARS ALERT*");

			Integer choice = sc.nextInt();
			int exitStatus = 0;
			do {
			    switch (choice) {
			        case 1:
			            System.out.println("Enter the word to be searched");
			            Scanner sc_1 = new Scanner(System.in);
			            String search_word = sc_1.nextLine();
			            listOfPagesWithRank = search_function(search_word.toLowerCase(), listOfPagesWithRank, "./htmlToTextPages/");
			            break;
			            /**
			             * Case 2 caching by Piyush
			             * @author piyushmehta
			             */
			        case 2:
			            for (String page: listOfPagesWithRank) {
			                //					Printing everything except sample just for testing
			                if (page != "sample")
			                    System.out.println(page);
			            }

			            LRUCache lruCache = new LRUCache();
			            System.out.println(listOfPagesWithRank.size() <= 0 ?
			            		"Please first search :)" :"Enter number of pages for caching purpose which should be less than "
			            		+ ((listOfPagesWithRank.size()) - 1));

			            Scanner integerInput = new Scanner(System.in);

			            Integer totalPagesToBeCached = integerInput.nextInt();

			            for (int i = 0; i < totalPagesToBeCached; i++) {
			                System.out.println("Enter index of ranked list page");
			                Integer index = integerInput.nextInt();
			                lruCache.putElementInCache(index, listOfPagesWithRank.get(index));
			            }

			            
			            lruCache.getFileNameFromCache();

			            lruCache.storeToFolder(urlDict);

			            Scanner wordScanner = new Scanner(System.in);

			            System.out.println("Enter word to be searched in cache memory");

			            String cache_word = wordScanner.nextLine();

			            cachedRankingList = search_function(cache_word.toLowerCase(), cachedRankingList, "./cached_files/");

			            int rankingListSize = cachedRankingList.size();

			            if (rankingListSize <= 1) {
			                System.out.println("word not found in cache so searching in main html page folder");
			                search_function(cache_word, ranking_list_3, "./htmlToTextPages/");

			            }

			            break;
			            
			        case 3:
			            System.out.println("Enter a word to find alternate suggesions for it");
			            Scanner sc_3 = new Scanner(System.in);
			            String sugesstion_word = sc_3.nextLine();
			            WordSuggestions.suggestWord(sugesstion_word);
			            break;
			            
			        case 4:
			            System.out.println("Enter a sentance to find how yoda will speak it");
						  Scanner sc_4=new Scanner(System.in);
						  String input_word=sc_4.nextLine();
					      YodaMode lem = new YodaMode();
					      System.out.println(lem.lemmatize(input_word));
					      break;
					      
			        default:
			            exitStatus = 1;
			            break;


			    }
			    
			    System.out.println("====================Enter your option====================");
			    System.out.println("1 for searching word");
			    System.out.println("2 for searching word in cache");
			    System.out.println("3 for providing sugesstions to word");
			    System.out.println("4 for checking how yoda will speak your sentence *experimental* *STAR WARS ALERT*");
			    choice = sc.nextInt();
			    
			} while (exitStatus == 0);
		}


    }



}