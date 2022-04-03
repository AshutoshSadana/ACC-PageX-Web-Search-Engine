package Caching;

import java.util.Deque;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
class Cache {
    int key;
    String value;
    Cache(int key, String value) {
        this.key = key;
        this.value = value;
    }
}
public class LRUCache {
    static Deque < Integer > q = new LinkedList < > ();
    static LRUCache cache = new LRUCache();
    static Map < Integer, Cache > map = new HashMap < > ();
    int CACHE_CAPACITY = 4;
    static List < String > cached_file_names = new ArrayList < String > ();
    public String getElementFromCache(int key) {
        if (map.containsKey(key)) {
            Cache current = map.get(key);
            q.remove(current.key);
            q.addFirst(current.key);
            return current.value;
        }
        return "Not exist";
    }
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
    public void populate_cache_pages() {
        System.out.println(q);


        for (Integer item: q) {
            Cache current = map.get(item);
            //         System.out.println(current.value);
            cached_file_names.add(current.value);
        }
        System.out.println("cached_file_names");
        for (String fruit: cached_file_names)
            System.out.println(fruit);

    }
    public void copy_files(String Source, String Destination) {

        File source = new File(Source);
        File dest = new File(Destination);
        try {
            FileUtils.copyFile(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void add_cached_pages(HashMap < String, String > urlDict) {
        String file_source = "./htmlToTextPages/";
        String File_destination = "./cached_files/";
        for (Entry < String, String > entry_1: urlDict.entrySet()) {

            // if give value is equal to value from entry
            // print the corresponding key
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
