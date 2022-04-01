
import java.io.IOException;
import java.util.*;
public class SearchEngineRunner {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Crawler.webCrawl(1,30,"https://www.geeksforgeeks.org/",new ArrayList<String>());
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the word to be searched");
		String word=sc.nextLine();
		SearchingWord.searchWord(word);
	}

}
