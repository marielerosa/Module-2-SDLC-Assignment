import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class TextAnalyzer {
	
	/** First of all, the main will analyze the web site, not considering HTML tags,  text before the poem's title and  all text after the end of the poem.
	 * After that, it will add the words to an ArrayList, after that it will send the list to a HashMap, and display the final result.
	 */
	public static void main(String[] args) {
		
		try {
			
			boolean matchFound = false; 
			URL url = new URL("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
																								 
			ArrayList<String> wordCollections = new ArrayList<String>();

			String line;
			while ((line = reader.readLine()) != null) {

				if ((line.length() > 0) && (line.contains("[ #45484 ]") || matchFound == true)) { 
					line = line.toLowerCase().replaceAll("\\<[^>]*>", "").replace("[ #45484 ]", "") 
							.replaceAll(",", "").replace("&mdash", "").replace(";", " ")
							.replace("!", "").replace(".", "").replace("?", "")
							.replaceAll("â€?", "").replaceAll("™", "’").replaceAll("œ", "")
							.replace("˜", ""); 
					matchFound = true;

					// words into the ArrayList
					if (line.length() > 2) {
						for (String word : line.split(" ")) {
							wordCollections.add(word.replaceAll("[^a-zA-Z0-9-’ ]", ""));
						}
					}

					if ((line.length() > 0) && (line.contains("be lifted"))) {
						matchFound = false;
					}
				}
			}
			reader.close();
			

			// Convert to HashMap using countWords method
			Map<String, Integer> words = new HashMap<String, Integer>();
			countWords(wordCollections, words);
			
			// Most frequently used word
			List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(words.entrySet());
			Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
			  public int compare( Map.Entry<String, Integer> entry1,
					  			  Map.Entry<String, Integer> entry2) {
			    return entry2.getValue().compareTo(entry1.getValue());
			  }
			});
			
			// Output the final result 
			
			System.out.println(entries);
			
			// Output result of top 20 most used words
			
			for(int i = 0; i < 20; i++) {
			System.out.println(entries.get(i));
			}
			
			
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("I/O Error: " + e.getMessage());
		}
	}
	
	/** This method counts the occurrence of words in the ArrayList,
	 */
	
	static Map<String, Integer> countWords (ArrayList<String> Collection, Map<String, Integer> words) {
		for(int i = 0; i < Collection.size(); i++) {
			String word = Collection.get(i);
			Integer count = words.get(word);
			if (count != null) {
				count++;
			} else
				count = 1;
			words.put(word, count);
		}
		return words;
	}
}