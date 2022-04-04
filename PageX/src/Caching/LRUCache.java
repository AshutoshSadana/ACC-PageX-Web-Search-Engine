package Caching;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * This class implements a Least Recently Used (LRU) cache.
 *  The cache is implemented as a hash map.
 *  The cache is limited to a maximum size.
 *  The cache is ordered by the time of the last access.
 *  The least recently used element is the first element in the list.
 *  The most recently used element is the last element in the list.
 *
 * @author piyushmehta
 */
class Cache {
    int key;
    String value;
    Cache(int key, String value) {
        this.key = key;
        this.value = value;
    }
}

/**
 * This class implements a Least Recently Used (LRU) cache.
 * The cache is implemented as a hash map.
 *
 * @author piyushmehta
 */
public class LRUCache {
    static Deque <Integer> q = new LinkedList<>();
    static Map <Integer, Cache> map = new HashMap<>();
    int CACHE_CAPACITY = 4;
    static List <String> cached_file_names = new ArrayList<String>();

    /**
     * This method adds an element to the cache.
     * If the cache is full, the least recently used element is removed.
     * @author piyushmehta
     * @param key
     * @param value
     */
    public void putElementInCache(int key, String value) {
        if (map.containsKey(key)) {
            Cache curr = map.get(key);
            q.remove(curr.key);
        } else {
            if (q.size() == CACHE_CAPACITY) {
                int temp = q.removeLast();
                map.remove(temp);
            }
        }
        Cache newItem = new Cache(key, value);
        q.addFirst(newItem.key);
        map.put(key, newItem);
    }

    /**
     *  This method prints the contents of the cache.
     *  The cache is printed in the order of the time of the last access.
     *
     * @author piyushmehta
     */
    public void populate_cache_pages() {
        for (Integer item: q) {
            Cache current = map.get(item);
            cached_file_names.add(current.value);
        }
        System.out.println("cached_file_names");
        for (String fruit: cached_file_names)
            System.out.println(fruit);

    }

    /**
     * This method prints the contents of the cache.
     * @param Source
     * @param Destination
     * @throws IOException
     * @author piyushmehta
     */
    public void copy_files(String Source, String Destination) {

        File source = new File(Source);
        File dest = new File(Destination);
        try {
            FileUtils.copyFile(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method add the contents of the cache.
     *
     * @param urlDict
     * @author piyushmehta
     */
    public void add_cached_pages(HashMap < String, String > urlDict) {
        String file_source = "./htmlToTextPages/";
        String File_destination = "./cached_files/";
        for (Entry < String, String > entry_1: urlDict.entrySet()) {

            for (String element: cached_file_names) {

                if (entry_1.getValue() == element) {
                    System.out.println("The key for value " + entry_1.getKey());
                    String s_file_name = file_source + entry_1.getKey();
                    String d_file_name = File_destination + entry_1.getKey();
                    copy_files(s_file_name, d_file_name);
                    break;

                }
            }
        }

    }
}
