import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.apache.commons.io.FilenameUtils;

public class Crawler {
	public static final int maxDepth=2;
	public static HashMap<String, String> urlDict = new HashMap<String, String>(); // This will store url to filename mapping
	public static void webCrawl(int depth,int count, String url,ArrayList<String> visited) {
		
		if (depth <= maxDepth && count>=0) {
			Document doc = request(url,visited);
			
			if(doc!=null && count>=0) {
			for(Element link : doc.select("a[href]")) {
				String nextLink = link.absUrl("href");
				if(visited.contains(nextLink) == false && count>=0) {
					count--;
					//System.out.println(count);
					webCrawl(depth++,count,nextLink,visited);
					}
			}
			}
		}

			
	}
	private static Document request(String url,ArrayList<String>v) {         
		try {
			Connection con = Jsoup.connect(url);
			Document doc = con.get();
			
			if(con.response().statusCode()==200) {
				System.out.println("Link: "+url);
				System.out.println(doc.title());
				
				System.out.println();
				v.add(url);
				int i = v.indexOf(url);
				String fileNumber = Integer.toString(i);
				String fileLink = url;
				saveUrl(fileNumber, fileLink);
				addToDictionary(fileNumber, fileLink);
				return doc;
			}
			return null;
		}
		catch(IOException e) {
			return null;
		}
	}
	public static void addToDictionary(final String filenumber, final String fileLink){
		String temp = (filenumber+".txt");
		urlDict.put(temp,fileLink);
//		System.out.println(urlDict);
		
	}
	
	public static void saveUrl(final String filename, final String urlString) throws IOException {

		{
			
			try {

				// Create myURL object
				URL url = new URL(urlString);
				BufferedReader my_readr = new BufferedReader(new InputStreamReader(url.openStream()));

				// Enter filename in which you want to download
				String str = filename + ".html";

				BufferedWriter my_writer = new BufferedWriter(
						new FileWriter("./htmlpages/" + str));
				

				// read each line from stream till end
				String line;
				while ((line = my_readr.readLine()) != null) {
					my_writer.write(line);
				}

				my_readr.close();
				my_writer.close();
				System.out.println("Successfully Downloaded.");
				//Converts Html files to text
				htmlToText();	
			}

			// Exceptions
			
			catch (IOException e) {
				System.out.println("IOException raised");
			}
		}
	}
	private static void htmlToText() {
	    String filePath = "./htmlpages/";
        File file = new File(filePath);
        File[] file_Array = file.listFiles();
        int size = file_Array.length;
		 try {
		            	//Reference https://stackoverflow.com/questions/924394/how-to-get-the-filename-without-the-extension-in-java
			 			for(int i=0;i<size;i++) {
		            	String filenameWithoutExt = FilenameUtils.removeExtension(file_Array[i].getName());
		            	Document textDocument = Jsoup.parse(file_Array[i], "UTF-8");
		            	BufferedWriter bw = new BufferedWriter(new FileWriter("./htmlToTextPages/"+filenameWithoutExt+".txt"));
		            	bw.write(textDocument.text());					
		            	bw.close();            	
			 			}
	           
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}
	

}
