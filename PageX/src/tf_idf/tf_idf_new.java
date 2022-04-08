package tf_idf;

import java.util.HashMap;
import java.util.*;

import java.util.List;
import java.util.Map.Entry;

import tf_idf.Count_heap.Node;

public class tf_idf_new {
	static int normalizing_factor = 100;

	/**
	 * This method populates heap key is file_name and value is tf_idf_score
	 *
	 * @param tf_idf_score
	 * @author AchyuthBalaji
	 */

	public static List<String> populateHeap(HashMap<String, Double> tf_idf_score) {
		List<String> rankingList = new ArrayList<String>();
		System.out.println("Size of the map is : " + tf_idf_score.size());
		Node priorityQueue = Count_heap.newNode("sample", 0.0);
		for (Entry<String, Double> entry : tf_idf_score.entrySet()) {
			if (entry.getValue() > 0) {
				priorityQueue = Count_heap.push(priorityQueue, entry.getKey(), entry.getValue());

			}
		}
		while (Count_heap.isEmpty(priorityQueue) == 0) {

			rankingList.add(Count_heap.peek(priorityQueue));
			priorityQueue = Count_heap.pop(priorityQueue);

		}

		return rankingList;

	}

	/**
	 * This method computes Term Frequency score
	 *
	 * @param key_word_count
	 * @param total_number_of_words_count
	 * @author AchyuthBalaji
	 */

	public static double tfScoring(int key_word_count, int total_number_of_words_count) {

		key_word_count = key_word_count * normalizing_factor;

		double tf_score = ((double) key_word_count / (double) total_number_of_words_count);

		return tf_score;
	}

	/**
	 * This method computes IDF score
	 *
	 * @param documents_containing_key_word
	 * @param number_of_files
	 * @author AchyuthBalaji
	 */

	public static double idfScoring(Integer documents_containing_key_word, Integer number_of_files) {
		double idf_score = 0.0;
		if (documents_containing_key_word != 0) {
			idf_score = 1 + Math.log(number_of_files + 1 / documents_containing_key_word + 1);
		}
		return idf_score;

	}

	/**
	 * This method computes tf_idf score and is stored in Hash Map
	 *
	 * @param key_word_count
	 * @param complete_word_count
	 * @param documents_containing_key_word
	 * @author AchyuthBalaji
	 */
	public static HashMap<String, Double> computeTFIDF(HashMap<String, Integer> key_word_count,
			HashMap<String, Integer> complete_word_count, Integer documents_containing_key_word) {

		double _idf_score = 0.0;
		HashMap<String, Double> tf_idf_score = new HashMap<String, Double>();

		_idf_score = idfScoring(documents_containing_key_word, complete_word_count.size());
		for (String file_name : key_word_count.keySet()) {
			double _tf_score = tfScoring(key_word_count.get(file_name), complete_word_count.get(file_name));
			if (_tf_score * _idf_score != 0) {

			}
			tf_idf_score.put(file_name, _tf_score * _idf_score);

			System.out.println("File Name / URL: " + file_name);
			System.out.printf("Score: " + _tf_score * _idf_score + "\n");
			System.out.println("==================================");
		}

		return tf_idf_score;
	}

}
