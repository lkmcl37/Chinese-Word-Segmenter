package test_performance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import training_statistics.Statistic;
import wordsegm.WordSegm;

public class Test {
	public static void main(String[] args) {

		long a = System.currentTimeMillis();
		long timeCount = 0;
		String inputLine1 = null;
		String inputLine2 = null;
		BufferedReader br1 = null;
		BufferedReader br2 = null;

		int wordCount = 0;
		int wordCountOri = 0;
		try {
			Statistic info = new Statistic();
			HashMap<String, Integer> wordFreq = info.getWordFreq(new File(
					".//data//wordfreq.temp"));
			HashMap<String, HashMap<String, Integer>> markovChain = info
					.getMarkovChain(new File(".//data//markovChain.temp"));
			WordSegm wordSegm = new WordSegm(wordFreq, markovChain);

			br1 = new BufferedReader(new InputStreamReader(new FileInputStream(
					".//data//standard-text.txt"), "utf-8"));
			br2 = new BufferedReader(new InputStreamReader(new FileInputStream(
					".//data//test-text.txt"), "utf-8"));

			inputLine1 = br1.readLine();
			inputLine2 = br2.readLine();

			ArrayList<String> words = new ArrayList<String>();
			while (inputLine1 != null) {

				long start = System.nanoTime();
				words = wordSegm.segment(inputLine2);
				timeCount += System.nanoTime() - start;

				String[] strArr = inputLine1.trim().split("[ ]+");
				wordCountOri += strArr.length;

				for (int count1 = 0; count1 < words.size(); count1++) {
					for (int count2 = 0; count2 < strArr.length; count2++) {
						if (words.get(count1).equals(strArr[count2])) {
							wordCount++;
							break;
						}
					}
				}
				inputLine1 = br1.readLine();
				inputLine2 = br2.readLine();
			}
			System.out.println("Accurate word count:" + wordCount
					+ "  Total Word Count:" + wordCountOri);
			System.out.println("Accuracy rate:"
					+ ((double) wordCount / wordCountOri));

		} catch (Exception e) {
			e.printStackTrace();
			try {
				br1.close();
				br2.close();
			} catch (Exception e2) {
			}
		}

		System.out.println("Time:" + (timeCount / 1000000));
		System.out.println("Efficiency:"
				+ (28374490 / (timeCount / 1000000000))
				+ "characters / millisecond");
		System.out.println("Total running time:"
				+ (System.currentTimeMillis() - a));

	}
}
